package net.shopin.alipay.exception;

public class AlipayException extends RuntimeException {
	
	public static final String ALIPAY_EX_OUTOFTOTALFEE = "ALIPAY_EX_OUTOFTOTALFEE";

	public AlipayException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AlipayException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public AlipayException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public AlipayException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
