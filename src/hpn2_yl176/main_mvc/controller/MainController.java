/**
 * 
 */
package hpn2_yl176.main_mvc.controller;

import java.rmi.RemoteException;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import javax.swing.SwingUtilities;
import java.awt.Component;
import common.connector.ConnectorDataPacket;
import common.connector.IConnector;
import common.connector.INamedConnector;
import common.connector.messages.IInviteMsg;
import common.connector.messages.ISyncPeersMsg;
import common.receiver.INamedReceiver;
import hpn2_yl176.main_mvc.IMain2MiniAdptr;
import hpn2_yl176.main_mvc.model.ChatAppConfig;
import hpn2_yl176.main_mvc.model.IMainModel2ViewAdpt;
import hpn2_yl176.main_mvc.model.MainModel;
import hpn2_yl176.main_mvc.view.IMainViewToModelAdapter;
import hpn2_yl176.main_mvc.view.MainView;
import hpn2_yl176.mini_mvc.controller.IMini2MainAdptr;
import hpn2_yl176.mini_mvc.controller.MiniController;
import hpn2_yl176.msg.connectorMsgImpl.InviteMsg;
import hpn2_yl176.msg.connectorMsgImpl.SyncPeersMsg;
import provided.discovery.IEndPointData;
import provided.discovery.impl.model.DiscoveryModel;
import provided.discovery.impl.view.DiscoveryPanel;
import provided.discovery.impl.view.IDiscoveryPanelAdapter;
import provided.discovery.impl.model.IDiscoveryModelToViewAdapter;
import provided.logger.ILogger;
import provided.logger.ILoggerControl;
import provided.logger.LogLevel;
import provided.pubsubsync.IPubSubSyncManager;
import provided.rmiUtils.IRMIUtils;
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
	public MainController(ChatAppConfig appConfig) {
		this.appConfig0 = appConfig;
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
					namedConnector.sendMessage(new ConnectorDataPacket<ISyncPeersMsg>(new SyncPeersMsg(mainModel.getContacts()), namedConnector));
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
				IMain2MiniAdptr currentChatRoom = mainModel.getChatRooms().get(mainView.getCurrentChatRoom()-1);
				ConnectorDataPacket<IInviteMsg> msg = new ConnectorDataPacket<IInviteMsg>(
						new InviteMsg(currentChatRoom.getChatRoomID(), currentChatRoom.getRoomName()), 
						receiver);
				Thread t = new Thread(() -> {
					try {
						receiver.sendMessage(msg);
					} catch (RemoteException e) {
						sysLogger.log(LogLevel.ERROR, "Failed to send message "+msg.toString());
						e.printStackTrace();
					}
				});
				t.start();
			}

			@Override
			public void start() {
				// start the main model.  THE MODEL MUST BE STARTED _BEFORE_  model.getRMIUtils() IS CALLED!!
				mainModel.start(); // starts the internal IRMIUtils instance too.
				
				// start the discovery model using the already started IRMIUtils instance.
				discModel.start(mainModel.getRMIUtils(), mainView.getServerName(), appConfig0.getName()); 
				
			}

			@Override
			public void makeNewRoom(String roomName) {
				mainModel.makeRoom(roomName);
				
			}

			@Override
			public void removeRoom(int idx) {
				mainModel.removeRoom(idx-1);
				
			}
		}, appConfig0);
		
		mainModel = new MainModel(sysLogger, new IMainModel2ViewAdpt() {
			
			private MiniController makeController(String newRoomName) {
				MiniController miniController = new MiniController(newRoomName, new IMini2MainAdptr() {

					@Override
					public IPubSubSyncManager getPubSubSyncManager() {
						return mainModel.getpubSubSyncManager();
					};

					@Override
					public ILogger getLogger() {
						return sysLogger;
					}

					@Override
					public ChatAppConfig getConfig() {
						return appConfig0;
					}

					@Override
					public INamedConnector getNamedConnector() {
						return mainModel.getNamedConnector();
					}

					@Override
					public String getUserName() {
						return mainView.getUserName();
					}

					@Override
					public IRMIUtils getRmiUtils() {
						return mainModel.getRMIUtils();
					}

					@Override
					public void removeRoom() {
						mainView.removeCurrentRoomPanel();
//						mainModel.removeRoom(0);
						
					}				
				});
				miniController.start();
				return miniController;
			}
			@Override
			public void displayStatusMsg(String msg) {
				mainView.appendStatus(msg);
				
			}

			@Override
			public IMain2MiniAdptr makeNewRoom(String newRoomName) {	

				/*
					* Instantiate the Mini-controller
					* */
				MiniController miniController = this.makeController(newRoomName);
				miniController.makeNewRoom();
				mainView.addNewTab(miniController.getMyRoomPanel(), newRoomName);
				mainModel.getChatRooms().add(miniController.getRoomAdptr());
				return miniController.getRoomAdptr();
			}

			@Override
			public IMain2MiniAdptr join(UUID id, String newRoomName) {
				MiniController miniController = this.makeController(newRoomName);
				miniController.joinRoom(id);
				mainView.addNewTab(miniController.getMyRoomPanel(), newRoomName);
				mainModel.getChatRooms().add(miniController.getRoomAdptr());
				return miniController.getRoomAdptr();
			}


			@Override
			public void updateContacts(Set<INamedConnector> stubs) {
				mainView.updateConnectedHosts(stubs);
			}
		}, appConfig0);
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
		
		ChatAppConfig appConfig0 = new ChatAppConfig("James", IRMI_Defs.STUB_PORT_CLIENT, IRMI_Defs.CLASS_SERVER_PORT_CLIENT);
		ChatAppConfig appConfig1 = new ChatAppConfig("App2", IRMI_Defs.STUB_PORT_SERVER, IRMI_Defs.CLASS_SERVER_PORT_SERVER);
		ChatAppConfig appConfig2 = new ChatAppConfig("App3", IRMI_Defs.STUB_PORT_EXTRA, IRMI_Defs.CLASS_SERVER_PORT_EXTRA);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				(new MainController(appConfig0)).start();
				(new MainController(appConfig1)).start();
//				(new MainController(appConfig2)).start();
			}
		});
	}
	
}
