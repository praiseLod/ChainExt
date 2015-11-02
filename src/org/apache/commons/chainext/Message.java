package org.apache.commons.chainext;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.chainext.type.MessageType;


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
	MessageType getType();
	Map<String, Object> getAttributes();
	String getTitle();
	
	/**
	 * 消息类型
	 */
	boolean isSuccess();
	boolean isInfo();
	boolean isError();
	
	/**
	 * 添加属性
	 */
	Message addAttr(String key,Object value);
	void addAttrs(Map<String, Object> attrs);
	Object getAttr(String key);
	
	/**
	 * 转成json字符串 
	 */
	String toJSONString();
}
