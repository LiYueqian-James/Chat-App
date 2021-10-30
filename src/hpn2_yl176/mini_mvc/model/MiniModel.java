/**
 * 
 */
package hpn2_yl176.mini_mvc.model;
import controller.BallWorldController;
/**
 * @author James Li
 *
 */
public class MiniModel {
	
	private IModel2ViewAdptr adptr;
	
	/**
	 * @param adptr the adapter
	 */
	public MiniModel(IModel2ViewAdptr adptr) {
		this.adptr = adptr;
	}
	
	/**
	 * start the model.
	 */
	public void start() {
	}
	
	/**
	 * Send a message to the chat room.
	 * @param msg the message to be sent
	 */
	public void sendMsg(String msg) {
		try {
			adptr.displayStatus("Message " + msg + "successfully sent!");
		}
		catch (Exception e){
			adptr.displayStatus("Exception occured: "+e.toString());
		}
	}
	
	/**
	 * Send a ballworld instance to the chat room
	 */
	public void sendBallWorld() {
		
		//toDo: determine what data is needed to send a ball world
		// just the controller, all everything(model,view, controller?)
		
		try {
			BallWorldController controller = new BallWorldController();
			adptr.displayStatus("A Ball World program has been successfully sent!");
		}
		catch (Exception e) {
			adptr.displayStatus("Exception occured: "+e.toString());
		}
	}
	

}
