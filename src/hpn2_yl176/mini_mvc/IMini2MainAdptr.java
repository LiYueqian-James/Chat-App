/**
 * 
 */
package hpn2_yl176.mini_mvc;

/**
 * @author James Li
 *
 */
public interface IMini2MainAdptr {

	/**
	 * Remove "this" chat room. This will be resolved using closure.
	 */
	public void removeRoom();
	
	/**
	 * @return the user name of the current chat app instance.
	 */
	public String getUserName();
}
