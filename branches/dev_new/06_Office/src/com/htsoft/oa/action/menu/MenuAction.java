package com.htsoft.oa.action.menu;

import com.google.gson.Gson;
import com.htsoft.core.util.AppUtil;
import com.htsoft.core.util.ContextUtil;
import com.htsoft.core.util.XmlUtil;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.oa.model.system.AppUser;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

public class MenuAction extends BaseAction {
	/* 37 */private String id = null;
	private static final String USER_MENU_DOC = "_USER_MENU_DOC";

	public String getId() {
		/* 40 */return this.id;
	}

	public void setId(String id) {
		/* 44 */this.id = id;
	}

	private Document getCurDocument() {
		/* 55 */HttpSession session = getSession();
		/* 56 */Document userDoc = (Document) session
				.getAttribute("_USER_MENU_DOC");
		/* 57 */if (userDoc != null) {
			/* 58 */return userDoc;
		}

		/* 61 */Document doc = AppUtil.getLeftMenuDocument();
		/* 62 */Set rights = ContextUtil.getCurrentUser().getRights();
		//System.out.println(doc.getPath());
		/* 64 */if (rights.contains("__ALL")) {
			/* 65 */return doc;
		}

		/* 69 */rights.addAll(AppUtil.getPublicMenuIds());

		/* 71 */Document newDoc = DocumentHelper.createDocument();
		/* 72 */Element root = newDoc.addElement("Menus");

		/* 74 */createSubMenus(rights, doc.getRootElement(), root);

		/* 76 */session.setAttribute("_USER_MENU_DOC", newDoc);
		/* 77 */return newDoc;
	}

	private Document getModuleDocument() {
		/* 82 */String topMenuId = getRequest().getParameter("topMenuId");
		/* 83 */if (StringUtils.isEmpty(topMenuId)) {
			/* 84 */topMenuId = "oa";
		}

		/* 87 */String menuXmlPath = AppUtil.getAppAbsolutePath() + "/js/menu-"
				+ topMenuId + ".xml";

		/* 89 */Document doc = XmlUtil.load(menuXmlPath);

		/* 91 */return doc;
	}

	private void createSubMenus(Set<String> rights, Element curNodes,
			Element newCurNodes) {
		/* 96 */List els = curNodes.elements();
		/* 97 */if (els.size() == 0)
			return;

		/* 99 */for (int i = 0; i < els.size(); i++) {
			/* 100 */Element el = (Element) els.get(i);
			/* 101 */Attribute id = el.attribute("id");
			/* 102 */if (id != null) {
				/* 103 */String idVal = id.getValue();
				/* 104 */if ((rights.contains(idVal)) || (idVal == null)) {
					/* 105 */Element newNodes = newCurNodes.addElement(el
							.getName());
					/* 106 */Iterator it = el.attributeIterator();

					/* 108 */while (it.hasNext()) {
						/* 109 */Attribute at = (Attribute) it.next();
						/* 110 */newNodes.addAttribute(at.getName(),
								at.getValue());
					}
					/* 112 */createSubMenus(rights, el, newNodes);
				}
			}
		}
	}

	/** @deprecated */
	public String items() {
		/* 126 */Document doc = getCurDocument();

		/* 128 */if (doc != null) {
			/* 130 */StringBuffer sb = new StringBuffer(
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r");

			/* 132 */Element el = doc.getRootElement();

			/* 134 */List nodes = el.selectNodes("/Menus/Items[@id='" + this.id
					+ "']/*");

			/* 136 */sb.append("<Menus>\r");
			/* 137 */for (int i = 0; i < nodes.size(); i++) {
				/* 139 */Node node = (Node) nodes.get(i);
				/* 140 */sb.append(node.asXML());
			}

			/* 143 */sb.append("\r</Menus>\r");
			/* 144 */setJsonString(sb.toString());
		}

		/* 147 */return "success";
	}

	/** @deprecated */
	public String models() {
		/* 156 */Document doc = getCurDocument();
		/* 157 */StringBuffer sb = new StringBuffer("[");

		/* 159 */if (doc != null) {
			/* 160 */Element root = doc.getRootElement();
			/* 161 */List els = root.elements();

			/* 163 */for (int i = 0; i < els.size(); i++) {
				/* 164 */Element el = (Element) els.get(i);

				/* 166 */Attribute id = el.attribute("id");
				/* 167 */Attribute text = el.attribute("text");
				/* 168 */Attribute iconCls = el.attribute("iconCls");

				/* 170 */sb.append("{id:'")
						.append(id == null ? "" : id.getValue()).append("',");
				/* 171 */sb.append("text:'")
						.append(text == null ? "" : text.getValue())
						.append("',");
				/* 172 */sb.append("iconCls:'")
						.append(iconCls == null ? "" : iconCls.getValue())
						.append("'},");
			}

			/* 175 */if (els.size() > 0) {
				/* 176 */sb.deleteCharAt(sb.length() - 1);
			}
		}

		/* 180 */sb.append("]");
		/* 181 */setJsonString(sb.toString());
		/* 182 */return "success";
	}

	public String panelTree() {
		/* 191 */Gson gson = new Gson();
		/* 192 */Document doc = getCurDocument();

		/* 196 */StringBuffer sb = new StringBuffer("[");

		/* 198 */if (doc != null) {
			/* 199 */Element root = doc.getRootElement();
			/* 200 */List els = root.elements();

			/* 202 */for (int i = 0; i < els.size(); i++) {
				/* 203 */Element el = (Element) els.get(i);

				/* 205 */Attribute id = el.attribute("id");
				/* 206 */Attribute text = el.attribute("text");
				/* 207 */Attribute iconCls = el.attribute("iconCls");

				/* 209 */sb.append("{id:'")
						.append(id == null ? "" : id.getValue()).append("',");
				/* 210 */sb.append("text:'")
						.append(text == null ? "" : text.getValue())
						.append("',");
				/* 211 */sb.append("iconCls:'")
						.append(iconCls == null ? "" : iconCls.getValue())
						.append("',");
				/* 212 */sb.append("subXml:")
						.append(gson.toJson(getModelXml(doc, id.getValue())))
						/* 213 */.append("},");
			}

			/* 216 */if (els.size() > 0) {
				/* 217 */sb.deleteCharAt(sb.length() - 1);
			}
		}

		/* 221 */sb.append("]");
		/* 222 */setJsonString(sb.toString());

		/* 224 */return "success";
	}

	protected String getModelXml(Document doc, String modelId) {
		/* 228 */StringBuffer sb = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r");

		/* 230 */Element el = doc.getRootElement();

		/* 232 */List nodes = el.selectNodes("/Menus/Items[@id='" + modelId
				+ "']/*");

		/* 234 */sb.append("<Menus>\r");
		/* 235 */for (int i = 0; i < nodes.size(); i++) {
			/* 236 */Node node = (Node) nodes.get(i);
			/* 237 */sb.append(node.asXML());
		}
		/* 239 */sb.append("\r</Menus>\r");

		/* 241 */return sb.toString();
	}
}

/*
 * Location:
 * C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6
 * -joffice\webapps\joffice1.3.1\WEB-INF\classes\ Qualified Name:
 * com.htsoft.oa.action.menu.MenuAction JD-Core Version: 0.6.0
 */