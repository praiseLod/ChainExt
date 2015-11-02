package org.apache.commons.chainext.impl;

import org.apache.commons.chainext.Message;
import org.apache.commons.chainext.type.MessageType;
import org.apache.commons.lang.StringUtils;

/**
 * 消息工厂
 */
public class MessageFactory {
	
	public final static Message SUCCESS = new MessageBase("操作成功！",MessageType.SUCCESS);
	public final static Message INFO = new MessageBase("", MessageType.INFO);
	public final static Message ERROR = new MessageBase("操作失败！",MessageType.INFO);
	
	/**
	 * 返回<b>成功</b>类型的消息
	 */
	public static Message success(String message) {
		return new MessageBase(message, MessageType.SUCCESS);
	}

	/**
	 * 返回<b>失败</b>类型的消息
	 */
	public static Message error(String message) {
		return new MessageBase(message, MessageType.ERROR);
	}

	/**
	 * 返回<b>提示</b>类型的消息
	 */
	public static Message info(String message) {
		return new MessageBase(message, MessageType.INFO);
	}

	/**
	 * 当{@code value} > 0   时返回<b>成功</b>类型的消息，并且消息内容为{@code successMsg},<br>
	 * 当{@code value} <= 0  时返回<b>失败</b>类型的消息，并且消息内容为{@code errorMsg}.
	 * @param value
	 * @param successMsg
	 * @param errorMsg
	 */
	public static Message valueOf(int value, String successMsg, String errorMsg) {
		if (value <= 0) {
			return error(errorMsg);
		} else {
			return success(successMsg);
		}
	}

	/**
	 * 当{@code value} > 0   时返回{@link MessageFactory.SUCCESS} <br>
	 * 当{@code value} <= 0  时返回{@link MessageFactory.ERROR}
	 */
	public static Message valueOf(int value){
		if (value <= 0) {
			return ERROR;
		} else {
			return SUCCESS;
		}
	}
	
	/**
	 * 当{@code value} = true   时返回{@link MessageFactory.SUCCESS} <br>
	 * 当{@code value} = false  时返回{@link MessageFactory.ERROR}
	 */
	public static Message valueOf(boolean value){
		if(value){
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	
	/**
	 * 当 {@code errorMsg} 不为空时返回<b>失败</b>消息类型，并且消息内容为{@code errorMsg},<br>
	 * 否则就返回{@link MessageFactory.SUCCESS}
	 * @param errorMsg
	 */
	public static Message valueOf(String errorMsg){
		if(StringUtils.isNotBlank(errorMsg)){
			return error(errorMsg);
		}else{
			return SUCCESS;
		}
	}
	
}
