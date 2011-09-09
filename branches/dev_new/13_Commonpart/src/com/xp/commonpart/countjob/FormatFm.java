package com.xp.commonpart.countjob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 计算公式返回条件值
 * @author jptong
 *
 */
public class FormatFm {
	/**
	 * 格式化传入的自定义计算公式
	 * @param formuula
	 * @return
	 */
	protected final transient Log logger = LogFactory.getLog(getClass());
	public Modle doFormat(String formuula) {
		int index=0;
		int endIndex = 0;
		formuula="["+formuula+"]";
		//logger.info(formuula+"组装成："+formuula);
		int startIndex = formuula.indexOf("[", endIndex);
		Map moMap=new HashMap();
		Modle modlelast=new Modle();
		while (startIndex != -1) {
			endIndex = formuula.indexOf("]", startIndex) + 1;
			String segment = formuula.substring(startIndex, endIndex);
			segment=segment.substring(segment.lastIndexOf("["),segment.length());
			String sbuseg=segment.replace("[", "").replace("]", "");
			String[] ms=null;
			if(sbuseg.indexOf("或")!=-1){
				ms=sbuseg.split("或");
				Modle modle=new Modle();
				String[] s=new String[2];
				List formulas=new ArrayList();
				for(String m:ms){
					Formula f=new Formula();
					if(m.indexOf(">=")!=-1){
						s=m.split(">=");
						f.setLeftFm(s[0]);
						f.setCompail(">=");
						f.setRightFm(s[1]);
					}else if(m.indexOf("<=")!=-1){
						s=m.split("<=");
						f.setLeftFm(s[0]);
						f.setCompail("<=");
						f.setRightFm(s[1]);
					}else if(m.indexOf(">")!=-1){
						s=m.split(">");
						f.setLeftFm(s[0]);
						f.setCompail(">");
						f.setRightFm(s[1]);
					}else if(m.indexOf("<")!=-1){
						s=m.split("<");
						f.setLeftFm(s[0]);
						f.setCompail("<");
						f.setRightFm(s[1]);
					}else if(m.indexOf("==")!=-1){
						s=m.split("==");
						f.setLeftFm(s[0]);
						f.setCompail("==");
						f.setRightFm(s[1]);
					}else if(m.indexOf("=")!=-1){
						s=m.split("=");
						f.setLeftFm(s[0]);
						f.setCompail("=");
						f.setRightFm(s[1]);
					}else{
						Modle m1=(Modle) moMap.get(m.trim());
						f.setChildModle(m1);
					}
					formulas.add(f);
				}
				modle.setFormulas(formulas);
				modle.setLinkFlag("或");
				moMap.put("modle_"+index, modle);
				formuula=formuula.replace(segment, "modle_"+index);
				index++;
				modlelast=modle;
			}else if(sbuseg.indexOf("且")!=-1){
				ms=sbuseg.split("且");
				Modle modle=new Modle();
				String[] s=new String[2];
				List formulas=new ArrayList();
				for(String m:ms){
					Formula f=new Formula();
					if(m.indexOf(">=")!=-1){
						s=m.split(">=");
						f.setLeftFm(s[0]);
						f.setCompail(">=");
						f.setRightFm(s[1]);
					}else if(m.indexOf("<=")!=-1){
						s=m.split("<=");
						f.setLeftFm(s[0]);
						f.setCompail("<=");
						f.setRightFm(s[1]);
					}else if(m.indexOf(">")!=-1){
						s=m.split(">");
						f.setLeftFm(s[0]);
						f.setCompail(">");
						f.setRightFm(s[1]);
					}else if(m.indexOf("<")!=-1){
						s=m.split("<");
						f.setLeftFm(s[0]);
						f.setCompail("<");
						f.setRightFm(s[1]);
					}else if(m.indexOf("==")!=-1){
						s=m.split("==");
						f.setLeftFm(s[0]);
						f.setCompail("==");
						f.setRightFm(s[1]);
					}else if(m.indexOf("=")!=-1){
						s=m.split("=");
						f.setLeftFm(s[0]);
						f.setCompail("=");
						f.setRightFm(s[1]);
					}else{
						Modle m1=(Modle) moMap.get(m.trim());
						f.setChildModle(m1);
					}
					formulas.add(f);
				}
				modle.setFormulas(formulas);
				modle.setLinkFlag("且");
				moMap.put("modle_"+index, modle);
				formuula=formuula.replace(segment, "modle_"+index);
				index++;
				modlelast=modle;
			}else{
				String m=sbuseg;
				Modle modle=new Modle();
				String[] s=new String[2];
				List formulas=new ArrayList();
				Formula f=new Formula();
				if(m.indexOf(">=")!=-1){
					s=m.split(">=");
					f.setLeftFm(s[0]);
					f.setCompail(">=");
					f.setRightFm(s[1]);
				}else if(m.indexOf("<=")!=-1){
					s=m.split("<=");
					f.setLeftFm(s[0]);
					f.setCompail("<=");
					f.setRightFm(s[1]);
				}else if(m.indexOf(">")!=-1){
					s=m.split(">");
					f.setLeftFm(s[0]);
					f.setCompail(">");
					f.setRightFm(s[1]);
				}else if(m.indexOf("<")!=-1){
					s=m.split("<");
					f.setLeftFm(s[0]);
					f.setCompail("<");
					f.setRightFm(s[1]);
				}else if(m.indexOf("==")!=-1){
					s=m.split("==");
					f.setLeftFm(s[0]);
					f.setCompail("==");
					f.setRightFm(s[1]);
				}else if(m.indexOf("=")!=-1){
					s=m.split("=");
					f.setLeftFm(s[0]);
					f.setCompail("=");
					f.setRightFm(s[1]);
				}else{
					Modle m1=(Modle) moMap.get(m.trim());
					f.setChildModle(m1);
				}
				formulas.add(f);
				modle.setFormulas(formulas);
				moMap.put("modle_"+index, modle);
				formuula=formuula.replace(segment, "modle_"+index);
				index++;
				modlelast=modle;
			}
			startIndex = formuula.indexOf("[", 0);
		}
		return modlelast;
	}
	/**
	 * 对公司转换成的模型进行运算，返回布尔型的值
	 * @param m
	 * @return
	 */
	public boolean doFormula(Modle m){
		String LinkFlag=m.getLinkFlag();
		List<Formula> list=m.getFormulas();
		boolean flag=false;
		if(LinkFlag!=null&&LinkFlag.equals("且")){
			flag=true;
			for(Formula f:list){
				if(f.getChildModle()!=null){
					if(!this.doFormula(f.getChildModle())){
						flag=false;
					}
				}else{
					String left=f.getLeftFm();
					String right=f.getRightFm();
					String co=f.getCompail();
					if(!this.doCompali(left, right, co)){
						flag=false;
					}
				}
			}
		}else if(LinkFlag!=null&&LinkFlag.equals("或")){
			for(Formula f:list){
				if(f.getChildModle()!=null){
					if(this.doFormula(f.getChildModle())){
						flag=true;
					}
				}else{
					String left=f.getLeftFm();
					String right=f.getRightFm();
					String co=f.getCompail();
					if(this.doCompali(left, right, co)){
						flag=true;
					}
				}
			}
		}else {
			for(Formula f:list){
				if(f.getChildModle()!=null){
					if(this.doFormula(f.getChildModle())){
						flag=true;
					}
				}else{
					String left=f.getLeftFm();
					String right=f.getRightFm();
					String co=f.getCompail();
					if(this.doCompali(left, right, co)){
						flag=true;
					}
				}
			}
		}
		return flag;
	}
	/**
	 * 比较每一个子公式，返回布尔型
	 * @param left
	 * @param right
	 * @param co
	 * @return
	 */
	public boolean doCompali(String left,String right,String co){
		CalStr c=new CalStr();
		if(co.equals(">=")){
			return c.doAccount(left)>=c.doAccount(right);
		}else if(co.equals("<=")){
			return c.doAccount(left)<=c.doAccount(right);
		}else if(co.equals("=")||co.equals("==")){
			return c.doAccount(left)==c.doAccount(right);
		}else if(co.equals(">")){
			return c.doAccount(left)>c.doAccount(right);
		}else if(co.equals("<")){
			return c.doAccount(left)<c.doAccount(right);
		}
		return false;
	}
	public static void main(String [] arg){
		String f="[75.0>=100.0*(0.9+1)且75.0<100.0*1]";
		FormatFm ff=new FormatFm();
		Modle m=ff.doFormat(f);
		boolean flag=ff.doFormula(m);
		System.out.println(flag);
	}
}
