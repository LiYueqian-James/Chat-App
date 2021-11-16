/**
 * 
 */
package hpn2_yl176.msg.receiverMsgCmd;
import java.rmi.RemoteException;

import common.adapter.ICmd2ModelAdapter;
import common.receiver.AReceiverDataPacketAlgoCmd;
import common.receiver.ReceiverDataPacket;
import common.receiver.ReceiverDataPacketAlgo;
import common.receiver.messages.ICommandMsg;
import common.receiver.messages.ICommandRequestMsg;
import hpn2_yl176.mini_mvc.model.IMini2ViewAdptr;import hpn2_yl176.msg.receiverMsgImpl.CommandMsg;
import provided.datapacket.IDataPacketID;

/**
 * @author hungnguyen
 *
 */
public class CommandRequestMsgCmd extends AReceiverDataPacketAlgoCmd<ICommandRequestMsg>{

	private IMini2ViewAdptr adptr;
	private ReceiverDataPacketAlgo receiverVisitor;
	
	public CommandRequestMsgCmd(IMini2ViewAdptr adptr, ReceiverDataPacketAlgo receiverVisitor) {
		this.adptr = adptr;
		this.receiverVisitor = receiverVisitor;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -452405725602635887L;

//	@Override
//	public Void apply(IDataPacketID index, ReceiverDataPacket<ICommandRequestMsg> host, Void... params) {
//		// TODO Auto-generated method stub
//		IDataPacketID cmdId = host.getData().getID();
//		Thread thread = new Thread(() -> {
//			host.getSender().sendMessage(host)
//		});
//		host.getSender().sendMessage(host);
//		thread.start();
//		return null;
//	}
		

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Void apply(IDataPacketID index, ReceiverDataPacket<ICommandRequestMsg> host, Void... params) {
		// TODO Auto-generated method stub
		IDataPacketID cmdId = host.getData().getID();
		AReceiverDataPacketAlgoCmd<?> cmd = (AReceiverDataPacketAlgoCmd<?>) receiverVisitor.getCmd(cmdId);
		Thread thread = new Thread(() -> {
			try {
				host.getSender().sendMessage(new ReceiverDataPacket<ICommandMsg>(
						new CommandMsg(cmd, cmdId), host.getSender()));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		thread.start();
		return null;
	}

}
