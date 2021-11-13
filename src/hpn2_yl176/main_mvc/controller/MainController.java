/**
 * 
 */
package hpn2_yl176.main_mvc.controller;

import javax.swing.text.View;

import common.connector.INamedConnector;
import hpn2_yl176.main_mvc.model.IMainModel2ViewAdpt;
import hpn2_yl176.main_mvc.model.MainModel;
import hpn2_yl176.main_mvc.view.IMainViewToModelAdapter;
import hpn2_yl176.main_mvc.view.MainView;
import hpn2_yl176.mini_mvc.controller.MiniController;
import provided.logger.impl.Logger;

/**
 * @author hungnguyen
 *
 */
public class MainController {
	private MainView mainView;
	
	private MainModel mainModel;
	
	public MainController() {
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
