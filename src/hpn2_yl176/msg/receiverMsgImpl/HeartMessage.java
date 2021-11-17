package hpn2_yl176.msg.receiverMsgImpl;

import java.io.File;

import common.adapter.ICmd2ModelAdapter;
import common.adapter.IComponentFactory;
import common.receiver.AReceiverDataPacketAlgoCmd;
import common.receiver.INamedReceiver;
import common.receiver.ReceiverDataPacket;
import common.receiver.messages.ICommandMsg;
import common.receiver.messages.IReceiverMsg;
import provided.datapacket.IDataPacketID;
import provided.mixedData.MixedDataKey;

public class HeartMessage extends CommandMsg{

	public HeartMessage(AReceiverDataPacketAlgoCmd<?> cmd, IDataPacketID cmdId) {
		super(cmd, cmdId);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -9187981649706816326L;

	@Override
	public AReceiverDataPacketAlgoCmd<?> getCmd() {
		// TODO Auto-generated method stub
		AReceiverDataPacketAlgoCmd<IReceiverMsg> cmd = new AReceiverDataPacketAlgoCmd<IReceiverMsg>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Void apply(IDataPacketID index, ReceiverDataPacket<IReceiverMsg> host, Void... params) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
				// TODO Auto-generated method stub
				 cmd2ModelAdpt.broadcast(null);
			}
			
		};
		cmd.setCmd2ModelAdpt(new ICmd2ModelAdapter() {
			
			@Override
			public <T extends IReceiverMsg> void send(T data, INamedReceiver recv) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public File saveFile(String defaultName) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <T> T put(MixedDataKey<T> key, T value) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getRoomName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getInstanceName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public File getFile() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <T> T get(MixedDataKey<T> key) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean containsKey(MixedDataKey<?> key) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void buildScrollingComponent(IComponentFactory fac, String name) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void buildFixedComponent(IComponentFactory fac, String name) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public <T extends IReceiverMsg> void broadcast(T msg) {
				// TODO Auto-generated method stub
				
			}
		})
	}

	@Override
	public IDataPacketID getCmdID() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
