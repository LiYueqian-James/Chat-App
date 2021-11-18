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
	 * 
	 */
	private static final long serialVersionUID = 8859587780223926553L;
	private ReceiverDataPacket<?> dataPacket;
	private String errMessage;

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
