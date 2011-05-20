package com.zsgj.info.framework.workflow.tag;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.xpath.DefaultXPath;
import org.jbpm.JbpmContext;
import org.jbpm.file.def.FileDefinition;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.zsgj.info.framework.workflow.base.JbpmContextFactory;

public class ProcessImageTag extends TagSupport
{

	private static final long serialVersionUID = 1L;
	private long taskInstanceId;
	private long tokenInstanceId;
	private byte gpdBytes[];
	private byte imageBytes[];
	private Token currentToken;
	private ProcessDefinition processDefinition;
	static String currentTokenColor = "red";
	static String childTokenColor = "blue";
	static String tokenNameColor = "blue";

	public ProcessImageTag()
	{
		taskInstanceId = -1L;
		tokenInstanceId = -1L;
		gpdBytes = null;
		imageBytes = null;
		currentToken = null;
		processDefinition = null;
	}

	public void release()
	{
		taskInstanceId = -1L;
		gpdBytes = null;
		imageBytes = null;
		currentToken = null;
	}

	public int doEndTag()
		throws JspException
	{
		try
		{
			initialize();
			retrieveByteArrays();
			if (gpdBytes != null && imageBytes != null)
				writeTable();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw new JspException("table couldn't be displayed", e);
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
			throw new JspException("table couldn't be displayed", e);
		}
		release();
		JbpmContextFactory.closeJbpmContext();
		return 6;
	}

	private void retrieveByteArrays()
	{
		try
		{
			FileDefinition fileDefinition = processDefinition.getFileDefinition();
			gpdBytes = fileDefinition.getBytes("gpd.xml");
			imageBytes = fileDefinition.getBytes("processimage.jpg");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void writeTable()
		throws IOException, DocumentException
	{
		int borderWidth = 4;
		
		String strGpd = new String(gpdBytes);
		//在linux环境下文件解码方式不一样，需要转码
		if(System.getProperty("os.name").toUpperCase().indexOf("WINDOWS")==-1){//linux
			strGpd = new String(gpdBytes,"gbk");
		}
		//System.out.println("strGpd:"+strGpd);			
		Element rootDiagramElement = DocumentHelper.parseText(strGpd).getRootElement();
		
		int imageDimension[] = extractImageDimension(rootDiagramElement);
		String imageLink = pageContext.getServletContext().getContextPath()+"/processimage?definitionId=" + processDefinition.getId();
		JspWriter jspOut = pageContext.getOut();
		if (tokenInstanceId > 0L)
		{
			List allTokens = new ArrayList();
			walkTokens(currentToken, allTokens);
			jspOut.println("<div style='position:relative; background-image:url(" + imageLink + "); width: " + imageDimension[0] + "px; height: " + imageDimension[1] + "px;'>");
			for (int i = 0; i < allTokens.size(); i++)
			{
				Token token = (Token)allTokens.get(i);
				int offset = i;
				if (i > 0)
					for (; offset > 0 && ((Token)allTokens.get(offset - 1)).getParent().equals(token.getParent()); offset--);
				int boxConstraint[] = extractBoxConstraint(rootDiagramElement, token);
				jspOut.println("<div style='position:absolute; left: " + boxConstraint[0] + "px; top: " + boxConstraint[1] + "px; ");
				if (i == allTokens.size() - 1)
					jspOut.println("border: " + currentTokenColor);
				else
					jspOut.println("border: " + childTokenColor);
				jspOut.println(" " + borderWidth + "px groove; " + "width: " + boxConstraint[2] + "px; height: " + boxConstraint[3] + "px;'>");
				if (token.getName() != null)
					jspOut.println("<span style='color:" + tokenNameColor + ";font-style:italic;position:absolute;left:" + (boxConstraint[2] + 10) + "px;top:" + (i - offset) * 20 + ";'>&nbsp;" + token.getName() + "</span>");
				jspOut.println("</div>");
			}

			jspOut.println("</div>");
		} else
		{
			int boxConstraint[] = extractBoxConstraint(rootDiagramElement);
			jspOut.println("<table border=0 cellspacing=0 cellpadding=0 width=" + imageDimension[0] + " height=" + imageDimension[1] + ">");
			jspOut.println("  <tr>");
			jspOut.println("    <td width=" + imageDimension[0] + " height=" + imageDimension[1] + " style=\"background-image:url(" + imageLink + ")\" valign=top>");
			jspOut.println("      <table border=0 cellspacing=0 cellpadding=0>");
			jspOut.println("        <tr>");
			jspOut.println("          <td width=" + (boxConstraint[0] - borderWidth) + " height=" + (boxConstraint[1] - borderWidth) + " style=\"background-color:transparent;\"></td>");
			jspOut.println("        </tr>");
			jspOut.println("        <tr>");
			jspOut.println("          <td style=\"background-color:transparent;\"></td>");
			jspOut.println("          <td style=\"border-color:" + currentTokenColor + "; border-width:" + borderWidth + "px; border-style:groove; background-color:transparent;\" width=" + boxConstraint[2] + " height=" + (boxConstraint[3] + 2 * borderWidth) + ">&nbsp;</td>");
			jspOut.println("        </tr>");
			jspOut.println("      </table>");
			jspOut.println("    </td>");
			jspOut.println("  </tr>");
			jspOut.println("</table>");
		}
	}

	private int[] extractBoxConstraint(Element root)
	{
		int result[] = new int[4];
		String nodeName = currentToken.getNode().getName();

		System.out.println("*******nodeName:"+nodeName);
		XPath xPath = new DefaultXPath("//node[@name='" + nodeName + "']");
		Element node = (Element)xPath.selectSingleNode(root);

		//不知道为什么上面的语句不能返回任何结果，加入下面语句重新赋值 -yang
		if(node==null) {
			List lnode =  root.elements();
			for(int i=0;i<lnode.size();i++) {
				Element n = (Element)lnode.get(i);
				String nn = n.attribute("name").getText();
				System.out.println("*******nodeName in gdp0: "+nn);
				try {
					System.out.println("*******nodeName in gdp1: "+new String(nn.getBytes("iso-8859-1"),"gbk"));
					System.out.println("*******nodeName in gdp2: "+new String(nn.getBytes("iso-8859-1"),"utf-8"));
					System.out.println("*******nodeName in gdp3: "+new String(nn.getBytes("utf-8"),"gbk"));
					System.out.println("*******nodeName in gdp4: "+new String(nn.getBytes("utf-8"),"iso-8859-1"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(nodeName.equals(n.attribute("name").getText())) {
					node =  n;					
					break;
				}
			}
		}
		
		// -yang
		result[0] = Integer.valueOf(node.attribute("x").getValue()).intValue();
		result[1] = Integer.valueOf(node.attribute("y").getValue()).intValue();
		result[2] = Integer.valueOf(node.attribute("width").getValue()).intValue();
		result[3] = Integer.valueOf(node.attribute("height").getValue()).intValue();
		return result;
	}

	private int[] extractBoxConstraint(Element root, Token token)
	{
		int result[] = new int[4];
		String nodeName = token.getNode().getName();
		XPath xPath = new DefaultXPath("//node[@name='" + nodeName + "']");
		Element node = (Element)xPath.selectSingleNode(root);
		result[0] = Integer.valueOf(node.attribute("x").getValue()).intValue();
		result[1] = Integer.valueOf(node.attribute("y").getValue()).intValue();
		result[2] = Integer.valueOf(node.attribute("width").getValue()).intValue();
		result[3] = Integer.valueOf(node.attribute("height").getValue()).intValue();
		return result;
	}

	private int[] extractImageDimension(Element root)
	{
		int result[] = new int[2];
		result[0] = Integer.valueOf(root.attribute("width").getValue()).intValue();
		result[1] = Integer.valueOf(root.attribute("height").getValue()).intValue();
		return result;
	}

	private void initialize()
	{
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		if (taskInstanceId > 0L)
		{
			TaskInstance taskInstance = jbpmContext.getTaskMgmtSession().loadTaskInstance(taskInstanceId);
			currentToken = taskInstance.getToken();
		} else
		if (tokenInstanceId > 0L)
			currentToken = jbpmContext.getGraphSession().loadToken(tokenInstanceId);
		processDefinition = currentToken.getProcessInstance().getProcessDefinition();
		processDefinition.getId();

	}

	@SuppressWarnings("unchecked")
	private void walkTokens(Token parent, List allTokens)
	{
		Map children = parent.getChildren();
		if (children != null && children.size() > 0)
		{
			Collection childTokens = children.values();
			Token child;
			for (Iterator iterator = childTokens.iterator(); iterator.hasNext(); walkTokens(child, allTokens))
				child = (Token)iterator.next();

		}
		allTokens.add(parent);
	}

	public void setTask(long id)
	{
		taskInstanceId = id;
	}

	public void setToken(long id)
	{
		tokenInstanceId = id;
	}

}