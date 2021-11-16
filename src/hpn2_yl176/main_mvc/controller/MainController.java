/**
 * 
 */
package hpn2_yl176.main_mvc.controller;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.text.View;

import common.connector.ConnectorDataPacket;
import common.connector.IConnector;
import common.connector.INamedConnector;
import common.connector.messages.IInviteMsg;
import common.receiver.INamedReceiver;
import common.receiver.IReceiver;
import common.receiver.ReceiverDataPacket;
import common.receiver.ReceiverDataPacketAlgo;
import hpn2_yl176.main_mvc.IMain2MiniAdptr;
import hpn2_yl176.main_mvc.model.ChatAppConfig;
import hpn2_yl176.main_mvc.model.IMainModel2ViewAdpt;
import hpn2_yl176.main_mvc.model.MainModel;
import hpn2_yl176.main_mvc.view.IMainViewToModelAdapter;
import hpn2_yl176.main_mvc.view.MainView;
import hpn2_yl176.mini_mvc.controller.IMini2MainAdptr;
import hpn2_yl176.mini_mvc.controller.MiniController;
import hpn2_yl176.msg.connectorMsgImpl.InviteMsg;
import provided.discovery.IEndPointData;
import provided.discovery.impl.model.DiscoveryModel;
import provided.discovery.impl.view.DiscoveryPanel;
import provided.discovery.impl.view.IDiscoveryPanelAdapter;
import provided.discovery.impl.model.IDiscoveryModelToViewAdapter;
import provided.logger.ILogger;
import provided.logger.ILoggerControl;
import provided.logger.LogLevel;
import provided.logger.impl.Logger;
import provided.pubsubsync.IPubSubSyncChannelUpdate;
import provided.pubsubsync.IPubSubSyncManager;
import provided.pubsubsync.IPubSubSyncUpdater;
import provided.rmiUtils.IRMI_Defs;

/**
 * @author James Li
 *
 */
/**
 * @author hungnguyen
 *
 */
public class MainController {
	
	/**
	 * The Discovery server UI panel for the view
	 */
	private DiscoveryPanel<IEndPointData> discPnl;

	/**
	 * A self-contained model to handle the discovery server.   
	 * MUST be started AFTER the main model as it needs the IRMIUtils from the main model! 
	 */
	private DiscoveryModel<IConnector> discModel;
	
	/**
	 * The view of the chat app instance.
	 */
	private MainView<INamedConnector> mainView;
	
	
	/**
	 * The model of the chat app instance.
	 */
	private MainModel mainModel;
	
	/**
	 * The system logger to use. Change and/or customize this logger as desired.
	 */
	private ILogger sysLogger = ILoggerControl.getSharedLogger();
	
	private ChatAppConfig appConfig0;

	
	/**
	 * Instantiate the view, model, discovery server, and the mini mvc.
	 */
	public MainController() {
		this.appConfig0 = new ChatAppConfig("App1", IRMI_Defs.STUB_PORT_CLIENT, IRMI_Defs.CLASS_SERVER_PORT_CLIENT);
//		ChatAppConfig appConfig1 = new ChatAppConfig("App2", IRMI_Defs.STUB_PORT_SERVER);
//		ChatAppConfig appConfig2 = new ChatAppConfig("App3", IRMI_Defs.STUB_PORT_EXTRA);
		discPnl = new DiscoveryPanel<IEndPointData>(new IDiscoveryPanelAdapter<IEndPointData>() {

			@Override
			public void connectToDiscoveryServer(String category, boolean watchOnly,
					Consumer<Iterable<IEndPointData>> endPtsUpdateFn) {
				discModel.connectToDiscoveryServer(category, false, endPtsUpdateFn);
			}

			@Override
			public void connectToEndPoint(IEndPointData selectedEndPt) {
				discModel.connectToEndPoint(selectedEndPt);
			}

		}, true, true);
		
		discModel = new DiscoveryModel<IConnector>(this.sysLogger, new IDiscoveryModelToViewAdapter<IConnector>() {

			@Override
			public void addStub(IConnector stub) {
				INamedConnector namedConnector;
				try {
					namedConnector = stub.makeNamedConnector();
					mainModel.addContact(namedConnector);
				} catch (RemoteException e) {
					sysLogger.log(LogLevel.DEBUG, "Failed to make Named connector");
					e.printStackTrace();
				}
			}
		}
		);

		
		mainView = new MainView<INamedConnector>(new IMainViewToModelAdapter<INamedConnector>() {
			/**
			 * Allows the view to set up a connection with the remote IP
			 * @param remoteIP the IP address we are connecting to
			 */
			public String connectTo(String remoteIP, String boundName) {
				return mainModel.connectTo(remoteIP, boundName);
			};

			/**
			 * Terminate the program
			 */
			public void quit() {
				mainModel.quit(0);
			};
			

			public void invite(INamedConnector receiver) {
				IMain2MiniAdptr currentChatRoom = mainModel.getPanel2RoomMap().get(mainView.getCurrentChatRoom());
				ConnectorDataPacket<IInviteMsg> msg = new ConnectorDataPacket<IInviteMsg>(
						new InviteMsg(currentChatRoom.getChatRoomID(), currentChatRoom.getRoomName()), 
						receiver);
				Thread t = new Thread(() -> {
					try {
						receiver.sendMessage(msg);
					} catch (RemoteException e) {
						sysLogger.log(LogLevel.DEBUG, "Failed to send message "+msg.toString());
						e.printStackTrace();
					}
				}); 	
			}

			@Override
			public void start() {
				// start the main model.  THE MODEL MUST BE STARTED _BEFORE_  model.getRMIUtils() IS CALLED!!
				mainModel.start(); // starts the internal IRMIUtils instance too.
				
				// start the discovery model using the already started IRMIUtils instance.
				discModel.start(mainModel.getRMIUtils(), mainView.getServerName(), appConfig0.getName()); 
				
			}
		}, appConfig0);
		
		mainModel = new MainModel(sysLogger, new IMainModel2ViewAdpt() {

			@Override
			public void displayStatusMsg(String msg) {
				mainView.appendStatus(msg);
				
			}

			@Override
			public void addComponent(JPanel Panel) {
				//toDO: add a new JPanel
				
			}

			@Override
			public IMain2MiniAdptr makeNewRoom(String roomName, IPubSubSyncManager pubSubSyncManager) {
				
				// empty room roster - nobody is in the room yet
				HashSet<INamedReceiver> roster = new HashSet<>();
				
				/*
				 * The (reference to the) roster will be passed to the mini controller so that it gets updated whenever 
				 * the data channel changes.
				 */
				IPubSubSyncChannelUpdate<HashSet<INamedReceiver>> chatRoom = pubSubSyncManager.createChannel(roomName, roster, 
						(pubSubSyncData) -> {
							// roster.clear();
							roster.addAll(pubSubSyncData.getData());
						},
						(statusMessage) -> {
							sysLogger.log(LogLevel.DEBUG, "room " + roomName +" has been made sucessfully.");
						});
				
				UUID chatRoomID = chatRoom.getChannelID();
				/*
				 * Instantiate the Mini-controller
				 * */
				MiniController miniController = new MiniController(new IMini2MainAdptr() {

					@Override
					public IPubSubSyncManager getPubSubSyncManager() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public void removeRoom() {
						// TODO Auto-generated method stub
						
					}

					@Override
					public ILogger getLogger() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public Set<INamedReceiver> getRoomRoster() {
						return roster;
					}
					
				}, chatRoomID);
				
				
				/*
				 * Instantiate the adapter, get information needed from the mini-controller above!
				 */
				IMain2MiniAdptr chatRoomAdptr = new IMain2MiniAdptr() {

					@Override
					public INamedReceiver getNamedReceiver() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public JPanel getRoomPanel() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public void start() {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void removeParticipant(INamedReceiver person) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void quit() {
						// TODO Auto-generated method stub
						
					}

					@Override
					public ReceiverDataPacketAlgo getReceiverMsgAlgo() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public UUID getChatRoomID() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public String getRoomName() {
						// TODO Auto-generated method stub
						return null;
					}
					
				};
				
				
//				 add the host of the room to the data channel.
				INamedReceiver host = new INamedReceiver() {

					/**
					 * Serialization purpose.
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public String getName() {
						return mainModel.getUserName();
					}

					@Override
					public INamedConnector getConnector() {
						return mainModel.getNamedConnector();
					}

					@Override
					public IReceiver getStub() {
						return new IReceiver() {

							@Override
							public void sendMessage(ReceiverDataPacket<?> packet) throws RemoteException {
								packet.execute(chatRoomAdptr.getReceiverMsgAlgo(), null);
								
							}
							
						};
					}
					
				};
				chatRoom.update(IPubSubSyncUpdater.makeSetAddFn(host));
				return chatRoomAdptr;
			}

			@Override
			public IMain2MiniAdptr join(UUID roomID, IPubSubSyncManager pubSubManager) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getUserName() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void removeStub(INamedConnector stub) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateContacts(Set<INamedConnector> stubs) {
				mainView.updateConnectedHosts(stubs);
				
			}}, appConfig0);
	}
	
	/**
	 * Starts the view then the model plus the discovery panel and model.  The view needs to be started first so that it can display 
	 * the model status updates as it starts.   The discovery panel is added to the main view after the discovery model starts. 
	 */
	public void start() {
//		System.out.println("bruh!");


		discPnl.start(); // start the discovery panel	
		
		mainView.addCtrlComponent(discPnl); // Add the discovery panel to the view's "control" panel.

		// start the main view.  Starting the view here will keep the view from showing before the discovery panel is installed.
		mainView.start();

	}
	
	/**
	 * Run the app.
	 * @param args Not used
	 */
	public static void main(String[] args) {
//		System.out.println("bruh!");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				(new MainController()).start();
			}
		});
	}
	
}
