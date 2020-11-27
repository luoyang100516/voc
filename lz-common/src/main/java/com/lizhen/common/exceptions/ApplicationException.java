package com.lizhen.common.exceptions;

/**
 * 统一异常
 * @author xujian
 */
@SuppressWarnings("serial")
public class ApplicationException extends RuntimeException{

	/**
	 * 异常编码
	 */
	private int code;

	/**
	 * 异常消息
	 */
	private String msg;

	public ApplicationException() {
	}

	public ApplicationException(int code, String message) {
		super();
		this.code = code;
		this.msg = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return msg;
	}

	public void setMessage(String message) {
		this.msg = message;
	}

}
