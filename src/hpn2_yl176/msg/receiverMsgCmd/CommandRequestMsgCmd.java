/**
 * 
 */
package hpn2_yl176.msg.receiverMsgCmd;
import java.rmi.RemoteException;

import common.adapter.ICmd2ModelAdapter;
import common.receiver.AReceiverDataPacketAlgoCmd;
import common.receiver.INamedReceiver;
import common.receiver.ReceiverDataPacket;
import common.receiver.ReceiverDataPacketAlgo;

import common.receiver.messages.IReceiverMsg;
import hpn2_yl176.mini_mvc.model.IMini2ViewAdptr;import hpn2_yl176.msg.receiverMsgImpl.CommandMsg;
import provided.datapacket.DataPacketIDFactory;
import provided.datapacket.IDataPacketID;

/**
 * @author hungnguyen
 *
 */
public class CommandRequestMsgCmd extends AReceiverDataPacketAlgoCmd<IReceiverMsg>{

	private ReceiverDataPacketAlgo receiverVisitor;
	private INamedReceiver me;

	public CommandRequestMsgCmd(INamedReceiver me, ReceiverDataPacketAlgo receiverVisitor) {
		this.receiverVisitor = receiverVisitor;
		this.me = me;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -452405725602635887L;

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub

	}

	@Override
	public Void apply(IDataPacketID index, ReceiverDataPacket<IReceiverMsg> host, Void... params) {
		// TODO Auto-generated method stub
		IDataPacketID cmdId = host.getData().getID();
		AReceiverDataPacketAlgoCmd<?> cmd = (AReceiverDataPacketAlgoCmd<?>) receiverVisitor.getCmd(cmdId);
		Thread thread = new Thread(() -> {
			try {
				host.getSender().sendMessage(new ReceiverDataPacket<IReceiverMsg>(
						new CommandMsg(cmd, cmdId), me));
			} catch (RemoteException e) {

			}
		});
		thread.start();
		return null;
	}

}
