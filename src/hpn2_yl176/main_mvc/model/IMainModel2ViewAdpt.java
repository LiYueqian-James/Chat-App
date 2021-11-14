/**
 * 
 */
package hpn2_yl176.main_mvc.model;

import javax.swing.JPanel;

import hpn2_yl176.main_mvc.IMain2MiniAdptr;

/**
 * @author hungnguyen
 *
 */
public interface IMainModel2ViewAdpt {
//	void makeMiniController();
	
	public void displayMsg(String msg);
	
	/**
	 * Add a tab to the main view
	 * @param Panel the panel of a chat room
	 */
	public void addComponent(JPanel Panel);
	
	/**
	 * Make a mini controller
	 * @return an IMain2MiniAdptr to interact with it
	 */
	public IMain2MiniAdptr make();
	
	/**
	 * Get the user name of the chat app instance from the view.
	 * @return the chat app user name.
	 */
	public String getUserName();
}
