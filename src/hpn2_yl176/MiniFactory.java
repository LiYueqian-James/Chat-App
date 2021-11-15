/**
 * 
 */
package hpn2_yl176;
import hpn2_yl176.mini_mvc.controller.IMini2MainAdptr;
import hpn2_yl176.mini_mvc.controller.MiniController;
/**
 * @author James Li
 *
 */
public class MiniFactory {
	/**
	 * A static field to make a new mini controller.
	 */
	public static MiniFactory Singleton = new MiniFactory();
	
	/**
	 * @return a started miniController
	 */
	public MiniController make() {
		MiniController miniController = new MiniController(new IMini2MainAdptr() {

			@Override
			public void removeRoom() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public String getUserName() {
				// TODO Auto-generated method stub
				return null;
			}
			
		});
		miniController.start();
		return miniController;
	}
}
