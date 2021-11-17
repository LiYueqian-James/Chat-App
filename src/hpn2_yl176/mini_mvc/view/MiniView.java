/**
 * 
 */
package hpn2_yl176.mini_mvc.view;

import javax.swing.JPanel;

import java.awt.Component;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.FlowLayout;
import javax.swing.SwingConstants;

import common.receiver.INamedReceiver;

import java.awt.Font;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.util.Set;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicSliderUI.ComponentHandler;

/**
 * @author James Li
 *
 */
public class MiniView extends JPanel{
	
	/**
	 * The adapter for the model.
	 */
	private IView2MiniAdptr adptr;

	/**
	 * Serialized version UID.
	 */
	private static final long serialVersionUID = 1707042914688785298L;
	/**
	 * The message panel.
	 */
	private final JPanel Msg = new JPanel();
	/**
	 * The control panel.
	 */
	private final JSplitPane control = new JSplitPane();
	/**
	 * Panel that split users from msg and status.
	 */
	private final JSplitPane splitPane = new JSplitPane();
	/**
	 * Text field to put message to be sent.
	 */
	private final JTextField textField = new JTextField();
	/**
	 * Send message button.
	 */
	private final JButton sendMsg = new JButton("Send Msg");
	/**
	 * Send BallWolrd button.
	 */
	private final JButton sendBallWorld = new JButton("send Ball World");
	/**
	 * List of users panel.
	 */
	private final JPanel users = new JPanel();
	/**
	 * Button to exit the room.
	 */
	private final JButton exit = new JButton("Exit Room");
	/**
	 * List of users
	 */
	private final JScrollPane usersPanel = new JScrollPane();
	/**
	 * A tab for each task
	 */
	private final JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
	/**
	 * textArea for msg.
	 */
	private final JTextArea msgArea = new JTextArea();

	private Set<String> roomRoster;
	private final JTextArea memberList = new JTextArea();
	private final JPanel panel = new JPanel();
	private final JPanel statusPanel = new JPanel();
	private final JTextArea statusArea = new JTextArea();
	
	/**
	 * Constructor for the view
	 * @param adptr the adapter.
	 */
	public MiniView(IView2MiniAdptr adptr) {
		this.adptr = adptr;
		textField.setToolTipText("The string message to be sent");
		textField.setColumns(30);
		roomRoster = adptr.getRoomRoster();
		this.adptr = adptr;
		initGUI();
	}
	
	/**
	 * Initialize the gui. 
	 */
	private void initGUI() {
		setLayout(new BorderLayout(0, 0));
		Msg.setToolTipText("The Panel for sending all messages");
		
		add(Msg, BorderLayout.SOUTH);
		Msg.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Msg.add(textField);
		sendMsg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = textField.getText();
				adptr.sendMsg(msg);
			}
		});
		sendMsg.setToolTipText("Button to send string message");
		
		Msg.add(sendMsg);
		sendBallWorld.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adptr.sendBallWorld();
			}
		});
		sendBallWorld.setToolTipText("button to send ball world message");
		
		Msg.add(sendBallWorld);
		control.setToolTipText("Controlling the chat app");
		control.setResizeWeight(0.05);
		
		add(control, BorderLayout.CENTER);
		splitPane.setToolTipText("Messages and their status");
		splitPane.setResizeWeight(0.8);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		control.setRightComponent(splitPane);
		tabs.setToolTipText("tabs for tasks");
		
		splitPane.setLeftComponent(tabs);
		msgArea.setToolTipText("Display messages");
		
		tabs.addTab("Messages", null, msgArea, null);
		
		tabs.addTab("New tab", null, panel, null);
		statusPanel.setBorder(new TitledBorder(null, "Status", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		splitPane.setRightComponent(statusPanel);
		statusPanel.setLayout(new BorderLayout(0, 0));
		statusArea.setToolTipText("place to display status msg");
		
		statusPanel.add(statusArea, BorderLayout.CENTER);
		users.setToolTipText("List of users");
		
		control.setLeftComponent(users);
		users.setLayout(new BorderLayout(0, 0));
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adptr.leave();
			}
		});
		exit.setToolTipText("Leave this room");
		exit.setFont(new Font("SimSun", Font.PLAIN, 8));
		
		users.add(exit, BorderLayout.SOUTH);
		usersPanel.setToolTipText("The users");
		
		users.add(usersPanel, BorderLayout.CENTER);
		
		usersPanel.setViewportView(memberList);

	}
	
	/**
	 * Start the view.
	 */
	public void start() {
		setVisible(true);
	}
	
	/**
	 * Append a message (along with the user) to the message pane
	 * @param user who sent the msg
	 * @param msg the msg
	 */
	public void appendMessage(String msg) {
		System.out.println("TEXTING " + msg+"\n");
		msgArea.append(msg);
	}
	
	/**
	 * Append a status message to the status pane
	 * @param status the status msg
	 */
	public void appendStatus(String status) {
		statusArea.append(status+"\n");
	}
	
	public void updateRoomRoster(Set<String> roomRoster) {
		this.roomRoster = roomRoster;
		this.memberList.setText("");
		for (String member: roomRoster) {
			memberList.append(member + '\n');	
		}
	}
	
	public void addFixedComponent(Component component, String name) {
		((JComponent) component).setToolTipText(name);
//		panel.add(component);
	}
	
	public void addScrollingComponent(Component component, String name) {
//		panel.add(component);
	}
}
