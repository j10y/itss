package com.xp.commonpart.countjob;

import java.text.DecimalFormat;
import java.util.Stack;

/**
 * ��ʽ���м���
 * @author jptong
 *
 */
public class CalStr {
	public CalStr() {

	}
	/* ������ */
	private double operation(double n1, double n2, String expression) {
		if (expression.equals("+")) {
			return n1 + n2;
		} else if (expression.equals("-")) {
			return n1 - n2;
		} else if (expression.equals("*")) {
			return n1 * n2;
		} else if (expression.equals("/")) {
			return n1 / n2;
		}
		return 0;
	}
	/* ���ȼ� */
	private int priority(String expression) {
		if (expression.equals("+") || expression.equals("-")) {
			return 0;
		} else if (expression.equals("*") || expression.equals("/")) {
			return 1;
		} else {
			return -1;
		}
	}
	/**
	 * �ж��Ƿ�Ϊһ�����������
	 * 
	 * @param expression
	 *            ��ʽ�����
	 * @return
	 */
	private boolean isExpression(String expression) {
		return expression.equals("+") || expression.equals("-")
				|| expression.equals("*") || expression.equals("/");
	}
	/**
	 * �������
	 * 
	 * @param numModel
	 *            ��ʽ
	 * @return ���
	 */
	public double doAccount(String numModel) {
		int endIndex = 0;
		int startIndex = numModel.indexOf("(", endIndex);
		while (startIndex != -1) {
			endIndex = numModel.indexOf(")", startIndex) + 1;
			String segment = numModel.substring(startIndex, endIndex);
			segment=segment.substring(segment.lastIndexOf("("),segment.length());
			double tResult = account(segment.replace("(", "").replace(")", ""));
			numModel = numModel.replace(segment, String.valueOf(tResult));
			startIndex = numModel.indexOf("(", 0);
		}
		DecimalFormat format = new DecimalFormat("0.##");
		return Double.parseDouble(format.format(account(numModel)));
	}
	/**
	 * �����㷨�߼�
	 * 
	 * @param numModel
	 *            ��ʽ
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private double account(String numModel) {
		String operators[] = { "\\+", "\\-", "\\*", "\\/", "\\(", "\\)" };
		numModel=numModel.trim();
		for (String operator : operators) {
			numModel = numModel.replaceAll(operator, " " + operator + " ");
		}
		Stack operStack = new Stack();
		String numModels[] = numModel.split("\\s+");
		if(numModels.length<=1){
			return Double.parseDouble(String.valueOf(numModel));
		}
		String preEle = "";
		for (int i = 0; i < numModels.length; i++) {
			if (isExpression(numModels[i])) {
				if (preEle == null || preEle.equals("")) {
					preEle = numModels[i];
				} else {
					if (priority(preEle) >= priority(numModels[i])) {
						double d1 = Double.parseDouble(String.valueOf(operStack
								.pop()));
						double d2 = Double.parseDouble(String.valueOf(operStack
								.pop()));
						operStack.push(operation(d2, d1, preEle));
						preEle = numModels[i];
					} else if (priority(preEle) < priority(numModels[i])) {
						double d1 = Double.parseDouble(String.valueOf(operStack
								.pop()));
						double d2 = Double.parseDouble(numModels[i + 1]);
						operStack.push(operation(d1, d2, numModels[i]));
						i++;
					}
				}
			} else {
				operStack.push(numModels[i]);
			}
		}
		double d1 = Double.parseDouble(String.valueOf(operStack.pop()));
		double d2 = Double.parseDouble(String.valueOf(operStack.pop()));
		return operation(d2, d1, preEle);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String numModel = "((100+100)*2/(8-1)-1)*1-4+3";
		System.out.println(new CalStr().doAccount(numModel));
	}
}
