package com.lizhen.common.response;

import java.io.Serializable;

/**
 * @author xujian
 * 通用客户端响应
 * @param <T>
 */
public class DataResponse<T>  implements Serializable {

	//总页数
	private int count;


	private Boolean success;
	
	/**
	 * 响应编码
	 */
	private int code;

	/**
	 * 响应消息
	 */
	private String message;


	/**

	 * 响应体
	 */
	private T data;

	public DataResponse() {
		super();
		this.success = true;
		this.code = 200;
		this.message = "操作成功";
		this.count = 0;
	}

	public Boolean getSuccess() {
		return success;
	}

	public DataResponse setSuccess(Boolean success) {
		this.success = success;
		return this;
	}

	public DataResponse( Boolean success,String message ,int count ) {
		super();
		this.success = success;
		this.message = message;
		this.count = count;
	}

	public DataResponse(Boolean success, int code, String message) {
		this.success = success;
		this.code = code;
		this.message = message;

	}

	public DataResponse(Boolean success, int code, String message,T data) {
		super();
		this.success = success;
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public DataResponse<T> setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMessage() {
		return message;
	}
	
	public DataResponse<T> setMessageAndSuccess(Boolean success, String message) {
		this.success = success;
		this.message = message;
		this.code =200;
		return this;
	}

	public DataResponse setResponse(Boolean success,String message, int code){
		this.success = success;
		this.message = message;
		this.code = code;
		return this;
	}

	public DataResponse<T> setMessage(String message) {
		this.message = message;
		return this;
	}

	public T getData() {
		return data;
	}

	public DataResponse<T> setData(T data) {
		this.data = data;
		return this;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
