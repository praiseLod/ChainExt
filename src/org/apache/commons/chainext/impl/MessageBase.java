package org.apache.commons.chainext.impl;

import org.apache.commons.chainext.Message;
import org.apache.commons.chainext.exception.BusizessException;
import org.apache.commons.chainext.type.MessageType;

class MessageBase implements Message {
	
	private static final long serialVersionUID = 1567565798622599372L;
	
	private final String message;
	
	private final MessageType type;
	
	public MessageBase(MessageType type) {
		this(null,type);
	}

	public MessageBase(String message, MessageType type) {
		super();
		this.message = message;
		this.type = type;
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
	public String toJSONString() {
		 String _message = message==null?"":message;
		return String.format("{message:%s,type:%s}", _message,this.type);
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
		return "MessageBase [message=" + message + ", type=" + type + "]";
	}
	
}
