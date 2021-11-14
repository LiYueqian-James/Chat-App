/**
 * 
 */
package hpn2_yl176.main_mvc.controller;

import java.util.function.Consumer;

import javax.swing.text.View;

import common.connector.IConnector;
import common.connector.INamedConnector;
import hpn2_yl176.main_mvc.model.IMainModel2ViewAdpt;
import hpn2_yl176.main_mvc.model.MainModel;
import hpn2_yl176.main_mvc.view.IMainViewToModelAdapter;
import hpn2_yl176.main_mvc.view.MainView;
import hpn2_yl176.mini_mvc.controller.MiniController;
import provided.discovery.IEndPointData;
import provided.discovery.impl.model.DiscoveryModelWatchOnly;
import provided.discovery.impl.view.DiscoveryPanel;
import provided.discovery.impl.view.IDiscoveryPanelAdapter;
import provided.logger.impl.Logger;
import provided.remoteCompute.compute.ICompute;

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
	 * A self-contained model to handle the discovery server.   MUST be started AFTER the main model as it needs the IRMIUtils from the main model! 
	 */
	private DiscoveryModelWatchOnly discModel;
	
	private MainView mainView;
	
	private MainModel mainModel;
	
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

		mainModel = new MainModel(new Logger(), new IMainModel2ViewAdpt() {
			
			@Override
			public void makeMiniController() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void displayMsg(String msg) {
				// TODO Auto-generated method stub
				
			}
		});
		
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
				
			};
			
			public void invite(INamedConnector namedConnector) {
				
			}
			
			public void makeRoom(String roomName) {
				MiniController miniController = new MiniController();
				model.makeRoom();
			}
		});
	}
	
}
