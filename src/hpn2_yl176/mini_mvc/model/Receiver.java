/**
 * 
 */
package hpn2_yl176.mini_mvc.model;

import java.rmi.RemoteException;

import common.receiver.IReceiver;
import common.receiver.ReceiverDataPacket;
import common.receiver.ReceiverDataPacketAlgo;

/**
 * @author James Li
 *
 */
public class Receiver implements IReceiver {

	private ReceiverDataPacketAlgo receiverVisitor;

	public Receiver(ReceiverDataPacketAlgo receiverVisitor) {
		this.receiverVisitor = receiverVisitor;
	}

	@Override
	public void sendMessage(ReceiverDataPacket<?> packet) throws RemoteException {
		packet.execute(this.receiverVisitor, null);

	}

}
