package org.apache.commons.chainext;

import java.io.Serializable;

/**
 * 职责处理的结果
 */
public interface  Message extends Serializable {
	
	/**
	 * 如果处理的结果是Erro就抛出异常
	 */
	void throwIfError();
	void throwIfError(String message);
	void throwIfError(String message,Object errorCode);
	
	/**
	 * 消息内容 
	 */
	String getMessage();
	void setMessage();
	
	/**
	 * 消息类型
	 */
	boolean isSuccess();
	boolean isInfo();
	boolean isError();
	
	/**
	 * 转成json字符串 
	 */
	String toJSONString();
}
