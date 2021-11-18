package hpn2_yl176.msg.receiverMsgCmd;

import java.awt.Component;

import javax.swing.JPanel;

import common.adapter.ICmd2ModelAdapter;
import common.adapter.IComponentFactory;
import common.receiver.AReceiverDataPacketAlgoCmd;
import common.receiver.ReceiverDataPacket;
import common.receiver.messages.ICommandMsg;
import provided.datapacket.IDataPacketID;

/**
 * @author James Li
 *
 */
public class TabMsgCmd extends AReceiverDataPacketAlgoCmd<ICommandMsg>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5834744522257653470L;
	private transient ICmd2ModelAdapter cmd2ModelAdapter;
	
	public TabMsgCmd(ICmd2ModelAdapter cmd2ModelAdapter) {
		// TODO Auto-generated constructor stub
		this.cmd2ModelAdapter = cmd2ModelAdapter;
	}
	
	
	@Override
	public Void apply(IDataPacketID index, ReceiverDataPacket<ICommandMsg> host, Void... params) {
		IComponentFactory fac = new IComponentFactory() {
			@Override
			public Component make() {
				return new JPanel();
			}
			
		};
		this.cmd2ModelAdapter.buildFixedComponent(fac, "Test");
		return null;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		this.cmd2ModelAdapter = cmd2ModelAdpt;
		
	}

}
