/**
 * 
 */
package hpn2_yl176.msg.receiverMsgImpl;

import common.receiver.messages.IStringMsg;

/**
 * @author hungnguyen
 *
 */
public class StringMsg implements IStringMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2985318814182875494L;
	private String text;
	public StringMsg(String text) {
		this.text = text;
	}
	
	@Override
	public String getString() {
		// TODO Auto-generated method stub
		return this.text;
	}
	
}
