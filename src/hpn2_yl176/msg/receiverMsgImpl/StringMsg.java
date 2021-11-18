/**
 * 
 */
package hpn2_yl176.msg.receiverMsgImpl;

import common.receiver.messages.IStringMsg;

/**
 * @author hungnguyen
 *
 */
public class StringMsg implements IStringMsg {

	/**
	 * Serialization purpose.
	 */
	private static final long serialVersionUID = -2985318814182875494L;

	/**
	 * The actual text msg contained.
	 */
	private String text;

	/**
	 * Constructor.
	 * @param text the txt msg.
	 */
	public StringMsg(String text) {
		this.text = text;
	}

	@Override
	public String getString() {
		return this.text;
	}

}
