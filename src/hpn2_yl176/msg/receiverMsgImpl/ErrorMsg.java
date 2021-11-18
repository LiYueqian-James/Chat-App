/**
 * 
 */
package hpn2_yl176.msg.receiverMsgImpl;

import common.receiver.ReceiverDataPacket;
import common.receiver.messages.IReceiverErrMsg;

/**
 * @author hungnguyen
 *
 */
public class ErrorMsg implements IReceiverErrMsg {

	/**
	 * serialization purpose.
	 */
	private static final long serialVersionUID = 8859587780223926553L;
	/**
	 * data
	 */
	private ReceiverDataPacket<?> dataPacket;
	/**
	 * The error msg.
	 */
	private String errMessage;

	/**
	 * Constructor
	 * @param dataPacket data
	 * @param errMessage msg
	 */
	public ErrorMsg(ReceiverDataPacket<?> dataPacket, String errMessage) {
		this.dataPacket = dataPacket;
		this.errMessage = errMessage;
	}

	@Override
	public ReceiverDataPacket<?> getPacket() {
		// TODO Auto-generated method stub
		return dataPacket;
	}

	@Override
	public String getError() {
		// TODO Auto-generated method stub
		return errMessage;
	}

}
