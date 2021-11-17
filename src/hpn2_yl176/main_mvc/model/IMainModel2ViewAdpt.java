/**
 * 
 */
package hpn2_yl176.main_mvc.model;

import java.util.Set;
import java.util.UUID;

import javax.swing.JPanel;

import common.connector.INamedConnector;
import hpn2_yl176.main_mvc.IMain2MiniAdptr;
import provided.pubsubsync.IPubSubSyncManager;

/**
 * @author hungnguyen
 *
 */
public interface IMainModel2ViewAdpt {
//	void makeMiniController();
	
	/**
	 * The main view will just display some status message.
	 * @param msg the status message.
	 */
	public void displayStatusMsg(String msg);
	
//	/**
//	 * Add a tab to the main view
//	 * @param Panel the panel of a chat room
//	 */
//	public void addComponent(JPanel Panel);
	
	/**
	 * Make a new chat room.
	 * @param pubSubManager the manager.
	 * @return an IMain2MiniAdptr to interact with it
	 */
	public IMain2MiniAdptr makeNewRoom(IPubSubSyncManager pubSubManager, String roomName);
	
	/**
	 * Join a chat room.
	 * @param roomID the room ID.
	 * @param pubSubManager the manager.
	 * @return the adapter so that the main can add the IRecevier to the room roster.
	 */
	public IMain2MiniAdptr join(UUID roomID, IPubSubSyncManager pubSubManager);
	
//	/**
//	 * Get the user name of the chat app instance from the view.
//	 * @return the chat app user name.
//	 */
//	public String getUserName();
	
	/**
	 * Tell the view to update its list of contacts.
	 * @param stubs a set of stubs.
	 */
	public void updateContacts(Set<INamedConnector> stubs);
}
