package org.apache.commons.chainext.generic;

import org.apache.commons.chainext.Message;
import org.apache.commons.chainext.exception.BusizessException;
import org.apache.commons.chainext.type.MessageType;

class MessageBase implements Message {
	
	private static final long serialVersionUID = 1567565798622599372L;
	
	private String message;
	
	private MessageType type;
	
	public MessageBase(MessageType type) {
		this(null,type);
	}

	public MessageBase(String message) {
		this(message,null);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMessage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toJSONString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSuccess() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInfo() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isError() {
		// TODO Auto-generated method stub
		return false;
	}

	
}
