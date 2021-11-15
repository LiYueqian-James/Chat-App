/**
 * 
 */
package hpn2_yl176.controller;

import java.util.HashSet;
import java.util.UUID;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.text.View;

import common.connector.IConnector;
import common.connector.INamedConnector;
import common.receiver.INamedReceiver;
import hpn2_yl176.main_mvc.IMain2MiniAdptr;
import hpn2_yl176.main_mvc.model.IMainModel2ViewAdpt;
import hpn2_yl176.main_mvc.model.MainModel;
import hpn2_yl176.main_mvc.view.IMainViewToModelAdapter;
import hpn2_yl176.main_mvc.view.MainView;
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
	
	/**
	 * Instantiate the view, model, discovery server, and the mini mvc.
	 */
	public MainController() {
		
		discPnl = new DiscoveryPanel<IEndPointData>(new IDiscoveryPanelAdapter<IEndPointData>() {

			@Override
			public void connectToDiscoveryServer(String category, boolean watchOnly,
					Consumer<Iterable<IEndPointData>> endPtsUpdateFn) {
				discModel.connectToDiscoveryServer(category, true, endPtsUpdateFn);
			}

			@Override
			public void connectToEndPoint(IEndPointData selectedEndPt) {
				discModel.connectToEndPoint(selectedEndPt);
			}

		}, false, true);
		
		discModel = new DiscoveryModel<IConnector>(this.sysLogger, new IDiscoveryModelToViewAdapter<IConnector>() {

			@Override
			public void addStub(IConnector stub) {
				INamedConnector namedConnector = stub.makeNamedConnector();
				mainModel.addContact(namedConnector);
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
				mainModel.quit();
			};
			
			public void invite(INamedConnector namedConnector) {
				mainModel.invite(namedConnector);
			}
			
			public void makeRoom(String roomName) {
				mainModel.makeRoom(roomName);
				
			}

			@Override
			public String connectTo(String remoteIP) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
		mainModel = new MainModel(sysLogger, new IMainModel2ViewAdpt() {

			@Override
			public void displayMsg(String msg) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void addComponent(JPanel Panel) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public IMain2MiniAdptr makeNewRoom(String roomName, IPubSubSyncManager pubSubSyncManager) {
				// empty room roster - nobody is in the room yet
				HashSet<INamedReceiver> roster = new HashSet<>();
				IPubSubSyncChannelUpdate<HashSet<INamedReceiver>> chatRoom = pubSubSyncManager.createChannel(roomName, roster, 
						null,
						(statusMessage) -> {
							sysLogger.log(LogLevel.DEBUG, "room " + roomName +" has been made sucessfully.");
						});
				
//				 add the host of the room to the data channel.
				chatRoom.update(IPubSubSyncUpdater.makeSetAddFn(host));
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
			}})
	}
	
}
