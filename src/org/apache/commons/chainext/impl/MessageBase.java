package org.apache.commons.chainext.impl;

import java.util.HashMap;

import java.util.Map;

import org.apache.commons.chainext.Message;
import org.apache.commons.chainext.exception.BusizessException;
import org.apache.commons.chainext.type.MessageType;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

class MessageBase implements Message {
	
	private static final long serialVersionUID = 1567565798622599372L;
	
	private final String title;
	private final String message;
	private final MessageType type;
	private Map<String, Object> attributes;
	
	public MessageBase(MessageType type) {
		this(null,type);
	}

	public MessageBase(String message, MessageType type) {
		this("操作提示",message,type);
	}

	public MessageBase(String title, String message, MessageType type) {
		super();
		this.title = title;
		this.message = message;
		this.type = type;
		this.attributes = new HashMap<>();
	}

	@Override
	public void throwIfError() {
		this.throwIfError(null);
	}
	
	@Override
	public void throwIfError(String message) {
		this.throwIfError(message,null);
	}

	@Override
	public void throwIfError(String message, Object errorCode) {
		if(this.type==MessageType.ERROR){throw new BusizessException(message,errorCode);}
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public MessageType getType() {
		return this.type;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public String toJSONString() {
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter("attributes","title","type","message","success");
		return JSON.toJSONString(this,filter);
	}

	@Override
	public Message addAttr(String key, Object value) {
		this.attributes.put(key, value);
		return this;
	}

	@Override
	public void addAttrs(Map<String, Object> attrs) {
		this.attributes = attrs;
	}

	@Override
	public Object getAttr(String key) {
		return this.attributes.get(key);
	}

	@Override
	public boolean isSuccess() {
		return this.type==MessageType.SUCCESS;
	}

	@Override
	public boolean isInfo() {
		return this.type==MessageType.INFO;
	}

	@Override
	public boolean isError() {
		return this.type==MessageType.ERROR;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
