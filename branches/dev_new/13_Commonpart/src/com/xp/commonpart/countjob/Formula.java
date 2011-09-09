package com.xp.commonpart.countjob;

import java.util.List;
/**
 * 公式
 * @author jptong
 *
 */
public class Formula {
	private String leftFm;//左边公式
	private String compail;//比较符号>  =  >=  <  <=
	private String rightFm;//右边公式
	private Modle childModle;//当以上三个参数没值的时候，这个参数表示有嵌套的公式
	public String getLeftFm() {
		return leftFm;
	}
	public void setLeftFm(String leftFm) {
		this.leftFm = leftFm;
	}
	public String getCompail() {
		return compail;
	}
	public void setCompail(String compail) {
		this.compail = compail;
	}
	public String getRightFm() {
		return rightFm;
	}
	public void setRightFm(String rightFm) {
		this.rightFm = rightFm;
	}
	public Modle getChildModle() {
		return childModle;
	}
	public void setChildModle(Modle childModle) {
		this.childModle = childModle;
	}
	
	
}
