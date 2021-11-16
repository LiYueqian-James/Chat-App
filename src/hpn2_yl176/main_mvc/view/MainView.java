/**
 * 
 */
package hpn2_yl176.main_mvc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import hpn2_yl176.main_mvc.model.ChatAppConfig;

import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author hungnguyen, James Li
 * @param <Stub> Stubs of the chat app instances that this chat app instance can connect to
 *
 */
public class MainView<Stub> extends JFrame {
	/**
	 * Holds the main content of the application
	 */
	private JPanel contentPane;
	
//	private 

	/**
	 * Displays client response to server connections
	 */
	private JScrollPane clientResponseDisp = new JScrollPane();

	/**
	 * The messages to inputted in client responses display
	 */
	private JTextArea clientTxt = new JTextArea();

	/**
	 * Quits the RMI once clicked
	 */
	JButton quitBtn = new JButton("Quit");

	/**
	 * The menu for the application, holds key buttons
	 */
	JPanel menuPanel = new JPanel();

	/**
	 * the view to model adapter 
	 */
	private IMainViewToModelAdapter viewToModelAdapter;

	/**
	 * Holds the send message options
	 */
	private final JPanel usernamePanel = new JPanel();

	/**
	 * Holds the send message options
	 */
	private final JPanel appStartupBtnPanel = new JPanel();
	
	/**
	 * Holds the server name to connect to
	 */
	private final JPanel servernamePanel = new JPanel();

	/**
	 * Receives input message to be sent to the server
	 */
	private final JTextField usernameInput = new JTextField();
	
	/**
	 * Receives server name
	 */
	private final JTextField servernameInput = new JTextField();

	/**
	 * Adds a task to the task menu
	 */
	private final JButton addBtn = new JButton("Add to lists");

	/**
	 * Runs task specified
	 */
	private final JButton runTaskBtn = new JButton("Run Task");

	/**
	 * combines the selected tasks and makes that a new list option in the menu
	 */
	private final JButton combineTasksBtn = new JButton("Combine Tasks");

	/**
	 * Creates username and server.
	 */
	private final JPanel appStartupPanel = new JPanel();
	
	/**
	 * Holds remote server connection utilities.
	 */
	private final JPanel remoteHostPanel = new JPanel();
	
	/**
	 * Holds remote server connection utilities.
	 */
	private final JPanel connectByIPPanel = new JPanel();
	
	/**
	 * connects client to the server
	 */
	private final JButton connectBtn = new JButton("Connect");
	
	/**
	 * A set of connected hosts.
	 */
	private Set<Stub> connectedHosts = new HashSet<>();

	private final JButton startButton = new JButton("Start");
	private final JTabbedPane chatroomTabPane = new JTabbedPane(JTabbedPane.TOP);
	private final JPanel statusPanel = new JPanel();
	private final JPanel panel_1 = new JPanel();
	private final JPanel boundNamePanel = new JPanel();
	private final JTextField boundName = new JTextField();
	private final JPanel panel_2 = new JPanel();
	private final JTextField hostIP = new JTextField();
	private final JScrollPane statusPane = new JScrollPane();
	private final JTextArea statusText = new JTextArea();
	
	private ChatAppConfig appConfig;
	private final JPanel makeJoinPanel = new JPanel();
	private final JPanel connectedHostsPanel = new JPanel();
	private final JComboBox<Stub> connectedHostsMenu = new JComboBox<Stub>();
	private final JButton inviteButton = new JButton("Invite");
	private final JPanel makeChatroomPnl = new JPanel();
	private final JTextField textField_1 = new JTextField();
	private final JButton makeChatroomBtn = new JButton("Make room!");
	/**
	 * Constructs the view
	 * @param adapter the view to model adapter specified in the controller
	 * @param chatAppConfig the chat app configuration.
	 */
	public MainView(IMainViewToModelAdapter adapter, ChatAppConfig chatAppConfig) {
		this.viewToModelAdapter = adapter;
		this.appConfig = chatAppConfig;
		setPreferredSize(new Dimension(1000, 700));
		initGUI();
	}
	
	/**
	 * @return the current component representing the chat room.
	 */
	public Component getCurrentChatRoom() {
		return chatroomTabPane.getSelectedComponent();
		
	}
	
	/**
	 * @param statusMsg a status message.
	 */
	public void appendStatus(String statusMsg) {
		this.statusText.append(statusMsg);
	}
	
	/**
	 * Initializes the GUI
	 */
	private void initGUI() {
		setBounds(20, 25, 1000, 400);
		setTitle("ChatApp");
		usernameInput.setColumns(10);
		servernameInput.setColumns(10);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setToolTipText("The entire gui");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		menuPanel.setToolTipText("Here is the menu of the application");

		contentPane.add(menuPanel, BorderLayout.NORTH);
		quitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewToModelAdapter.quit();
			}
		});
		quitBtn.setToolTipText("Quits the Application");

		appStartupPanel.setToolTipText("Holds the info for remote hosts");
		appStartupPanel
				.setBorder(new TitledBorder(null, "App Startup", TitledBorder.LEADING, TitledBorder.TOP, null, null));


		appStartupPanel.setLayout(new GridLayout(0, 1, 0, 0));

		usernamePanel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Username", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		servernamePanel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Server name", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		appStartupPanel.add(usernamePanel);
		usernamePanel.add(usernameInput);
		
		servernamePanel.add(servernameInput);
		
		appStartupPanel.add(servernamePanel);
		
		appStartupBtnPanel.setLayout(new GridLayout(1, 2, 0, 0));
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewToModelAdapter.start();
			}
		});
		
		appStartupBtnPanel.add(startButton);
		appStartupBtnPanel.add(quitBtn);
		
		appStartupPanel.add(appStartupBtnPanel);
		
		remoteHostPanel.setLayout(new GridLayout(0, 1, 0, 0));
		addBtn.setToolTipText("Click to add new task name to task menus");
		
		menuPanel.add(appStartupPanel);
		combineTasksBtn.setToolTipText("Click to combine two tasks");
		menuPanel.add(remoteHostPanel);
		
		menuPanel.add(makeJoinPanel);
		makeJoinPanel.setLayout(new GridLayout(2, 1, 0, 0));
		makeChatroomPnl.setToolTipText("Makes a chatroom");
		makeChatroomPnl.setBorder(new TitledBorder(null, "Make Chat Room", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		makeJoinPanel.add(makeChatroomPnl);
		textField_1.setColumns(10);
		
		makeChatroomPnl.add(textField_1);
		
		makeChatroomPnl.add(makeChatroomBtn);
		connectedHostsPanel.setBorder(new TitledBorder(
						new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
						"Connected Hosts", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		makeJoinPanel.add(connectedHostsPanel);
		connectedHostsPanel.setLayout(new GridLayout(0, 2, 0, 0));
//		connectedHostsMenu.setSelectedIndex(0);
		
		connectedHostsPanel.add(connectedHostsMenu);
		
		connectedHostsPanel.add(inviteButton);
		menuPanel.add(connectByIPPanel);
		connectByIPPanel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Connect To...", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		connectByIPPanel.setLayout(new GridLayout(0, 1, 0, 0));
		panel_2.setBorder(new TitledBorder(null, "Host", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		
		connectByIPPanel.add(panel_2);
		hostIP.setColumns(10);
		
		panel_2.add(hostIP);
		boundNamePanel.setBorder(new TitledBorder(
						new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
						"Bound name", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		connectByIPPanel.add(boundNamePanel);
		boundName.setColumns(10);
		
		boundNamePanel.add(boundName);
		connectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewToModelAdapter.connectTo(hostIP.getText(), boundName.getText());
			}
		});
		connectByIPPanel.add(connectBtn);
		
		contentPane.add(chatroomTabPane, BorderLayout.CENTER);
		chatroomTabPane.setToolTipText("A chatroom's tab");
		
		chatroomTabPane.addTab("Info", null, statusPanel, null);
		statusPanel.setLayout(new BorderLayout(0, 0));
		
		statusPanel.add(statusPane, BorderLayout.CENTER);
		
		statusPane.setViewportView(statusText);
		
		chatroomTabPane.addTab("New tab", null, panel_1, null);
	}

	/**
	 * Adds a component to the display area of the client
	 * @param comp the component to be added
	 */
	public void addCtrlComponent(JComponent comp) {
		menuPanel.add(comp);
		validate();
//		pack();
	}
	
	/**
	 * Updates connected host lists.
	 * @param newHosts the set of new hosts.
	 */
	public void updateConnectedHosts(Set<Stub> newHosts) {
		this.connectedHosts = new HashSet<Stub>(newHosts);
		connectedHostsMenu.removeAllItems();
		for (Stub connectedHost: connectedHosts) {
			connectedHostsMenu.addItem(connectedHost);
		}
		connectedHostsMenu.setSelectedIndex(0);
	}
	
	/**
	 * @return the server name.
	 */
	public String getServerName() {
		return this.servernameInput.getText();
	}
	
	/**
	 * James: I don't think we need the name from the mini mvc.
	 * When we make a chat room, the view provides the chat room name
	 * When we join a chat room, the main model receive an invitation 
	 * From there we can retrieve the name/stub of the chatroom
	 */
//	private void installTab(IMain2MiniAdptr main2MiniAdapter) {
//		this.chatroomTabPane.addTab(main2MiniAdapter.getName(), null, main2MiniAdapter.getView(), null);
//		
//	}

	/**
	 * starts the client GUI
	 */
	public void start() {
		this.setVisible(true);
	}
}
