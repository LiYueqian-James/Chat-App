/**
 * 
 */
package hpn2_yl176.mini_mvc.view;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTabbedPane;

/**
 * @author James Li
 *
 */
public class chatRoomView extends JPanel{
	public chatRoomView() {
		textField.setToolTipText("The string message to be sent");
		textField.setColumns(30);
		initGUI();
	}
	private void initGUI() {
		setLayout(new BorderLayout(0, 0));
		Msg.setToolTipText("The Panel for sending all messages");
		
		add(Msg, BorderLayout.SOUTH);
		Msg.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Msg.add(textField);
		sendMsg.setToolTipText("Button to send string message");
		
		Msg.add(sendMsg);
		sendBallWorld.setToolTipText("button to send ball world message");
		
		Msg.add(sendBallWorld);
		control.setToolTipText("Controlling the chat app");
		control.setResizeWeight(0.05);
		
		add(control, BorderLayout.CENTER);
		splitPane.setToolTipText("Messages and their status");
		splitPane.setResizeWeight(0.8);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		control.setRightComponent(splitPane);
		status.setToolTipText("The status of the app");
		
		splitPane.setRightComponent(status);
		
		splitPane.setLeftComponent(tabbedPane);
		
		tabbedPane.addTab("Messages", null, Messages, null);
		users.setToolTipText("List of users");
		
		control.setLeftComponent(users);
		users.setLayout(new BorderLayout(0, 0));
		btnNewButton.setToolTipText("Leave this room");
		btnNewButton.setFont(new Font("SimSun", Font.PLAIN, 8));
		
		users.add(btnNewButton, BorderLayout.SOUTH);
		scrollPane.setToolTipText("The users");
		
		users.add(scrollPane, BorderLayout.CENTER);
	}

	/**
	 * Serialized version UID.
	 */
	private static final long serialVersionUID = 1707042914688785298L;
	private final JPanel Msg = new JPanel();
	private final JSplitPane control = new JSplitPane();
	private final JSplitPane splitPane = new JSplitPane();
	private final JScrollPane status = new JScrollPane();
	private final JTextField textField = new JTextField();
	private final JButton sendMsg = new JButton("Send Msg");
	private final JButton sendBallWorld = new JButton("send Ball World");
	private final JPanel users = new JPanel();
	private final JButton btnNewButton = new JButton("Exit Room");
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private final JPanel Messages = new JPanel();

}
