package com.xp.commonpart.countjob;

import java.util.List;
/**
 * 公式转换后的模型
 * @author jptong
 *
 */
public class Modle {
	private boolean flag;
	private List<Formula> formulas;
	private String LinkFlag;//或 并
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public List<Formula> getFormulas() {
		return formulas;
	}
	public void setFormulas(List<Formula> formulas) {
		this.formulas = formulas;
	}
	public String getLinkFlag() {
		return LinkFlag;
	}
	public void setLinkFlag(String linkFlag) {
		LinkFlag = linkFlag;
	}
	
	
}
