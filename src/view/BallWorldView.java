package view;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Container;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;

/**
 * JFrame GUI to display Ballworld.
 * 
 * @author James Li yl176, Manan Bajaj
 * @param <TDropListItem> the type of item to be added to the drop down
 */
public class BallWorldView<TDropListItem, TDropListItem2> extends JFrame {
	/**
	 * Unique serial version UID
	 */
	private static final long serialVersionUID = -5244957808585376629L;
	/**
	 * Root panel that all of the sub-GUI components will be contained on.
	 */
	private JPanel contentPane;
	/**
	 * Panel containing controls
	 */
	private final JPanel controlBar = new JPanel();
	/**
	 * Panel to display balls onto
	 */
	private final JPanel ballWorldPanel = new JPanel() {
		/**
		 * Unique serial version UID
		 */
		private static final long serialVersionUID = 8303517520311377718L;

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			// use view2model paint adapter to paint balls
			view2ModelPaintAdptr.paintBalls(g);
		}
	};

	/**
	 * Adapter to control model - initialized to null.
	 */
	private IView2ModelCtrlAdapter<TDropListItem, TDropListItem2> view2ModelCtrlAdptr = new IView2ModelCtrlAdapter<>() {

		@Override
		public void clearBalls() {
		}

		@Override
		public void loadBall(Object strategyFac, Object strategyFac2) {
		}

		@Override
		public void updateSwitcherStrategy(TDropListItem newStrategy) {
			// TODO Auto-generated method stub

		}

		@Override
		public TDropListItem combineStrategyFac(TDropListItem s1, TDropListItem s2) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public TDropListItem loadStrategyFac(String strategyName) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public TDropListItem2 loadPaintStrategyFac(String paintName) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void makeSwitcher(TDropListItem2 paintFac) {
			// TODO Auto-generated method stub

		}
	};
	/**
	 * Adapter to paint model
	 */
	private IView2ModelPaintAdapter view2ModelPaintAdptr = IView2ModelPaintAdapter.NULL;
	/**
	 * JPanel containing text field, and button to add given strategies to the list.
	 */
	private final JPanel add = new JPanel();
	/**
	 * Button to add the typed strategy to the lists
	 */
	private final JButton addButton = new JButton("Add to Lists");
	/**
	 * Textfield to type desired strategy into
	 */
	private final JTextField txtBasicball = new JTextField();
	/**
	 * JPanel containing the dropdown menus, make, and combine buttons
	 */
	private final JPanel dropDown = new JPanel();
	/**
	 * Button to make the ball chosen in the upper dropdown box
	 */
	private final JButton make = new JButton("Make Selected Ball");
	/**
	 * Upper dropdown list that decides which strategy the "Make Selected Ball" button and
	 * switcher controls use
	 */
	private final JComboBox<TDropListItem> Ball1 = new JComboBox<>();
	/**
	 * Lower dropdown list that can be set to be combined with the upper dropdown list
	 */
	private final JComboBox<TDropListItem> Ball2 = new JComboBox<>();
	/**
	 * Button to combine the two strategies in the two dropdown lists
	 */
	private final JButton combine = new JButton("Combine!");
	/**
	 * JPanel containing the switcher ball buttons, and the clear screen button
	 */
	private final JPanel switchControl = new JPanel();
	/**
	 * Button that will create a switcher ball
	 */
	private final JButton makeSwitch = new JButton("Make Switcher");
	/**
	 * Button that will switch the strategies of all switcher balls
	 */
	private final JButton switchButton = new JButton("Switch!");
	/**
	 * Button to clear the screen of all balls
	 */
	private final JButton clear = new JButton("Clear All");
	private final JPanel paintControls = new JPanel();
	private final JComboBox<TDropListItem2> paintStrategies = new JComboBox();
	private final JButton addPaintButton = new JButton("Add Paint Strategy");
	private final JTextField txtPaintStrategy = new JTextField();

	/**
	 * Create the frame.
	 * @param view2ModelCtrlAdptr adapter to the model for control functions (i.e. loading and clearing balls)
	 * @param view2ModelPaintAdptr  adapter to the model for painting function
	 */
	public BallWorldView(IView2ModelCtrlAdapter<TDropListItem, TDropListItem2> view2ModelCtrlAdptr,
			IView2ModelPaintAdapter view2ModelPaintAdptr) {
		txtPaintStrategy.setColumns(10);
		txtBasicball.setToolTipText("Enter the type of ball you want...");
		txtBasicball.setText("Straight");
		txtBasicball.setColumns(10);
		this.view2ModelCtrlAdptr = view2ModelCtrlAdptr;
		this.view2ModelPaintAdptr = view2ModelPaintAdptr;
		initGUI();
	}

	/**
	 * Initializes the GUI and the frame.
	 */
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/**
		 * Root GUI content pane
		 */
		setBounds(100, 100, 700, 550);
		contentPane = new JPanel();
		contentPane.setToolTipText("root panel that houses all of the ballworld GUI components.");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		/**
		 * BallWorld display panel
		 */
		ballWorldPanel.setToolTipText("panel that the balls will be displayed onto");
		ballWorldPanel.setBackground(Color.WHITE);
		contentPane.add(ballWorldPanel, BorderLayout.CENTER);
		contentPane.add(controlBar, BorderLayout.NORTH);
		controlBar.setToolTipText(
				"panel that acts as the control bar at the top of the frame that houses the control text field and buttons.");
		controlBar.setBackground(new Color(204, 153, 204));
		controlBar.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		add.setToolTipText("New Stuff Panel");

		controlBar.add(add);
		add.setLayout(new GridLayout(2, 1, 0, 1));
		add.setLayout(new GridLayout(4, 1, 2, 1));
		add.add(txtBasicball);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = BallWorldView.this.txtBasicball.getText();
				if (text.equals("")) {
					return;
				}
				BallWorldView.this.Ball1.addItem(BallWorldView.this.view2ModelCtrlAdptr.loadStrategyFac(text));
				BallWorldView.this.Ball2.addItem(BallWorldView.this.view2ModelCtrlAdptr.loadStrategyFac(text));
			}
		});
		addButton.setToolTipText("Add to Lists");

		add.add(addButton);

		controlBar.add(dropDown);
		dropDown.setLayout(new GridLayout(4, 1, 0, 3));
		make.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				TDropListItem fac = (TDropListItem) BallWorldView.this.Ball1.getSelectedItem();
				TDropListItem2 paintFac = (TDropListItem2) BallWorldView.this.paintStrategies.getSelectedItem();
				if (fac == null) {
					return;
				}
				BallWorldView.this.view2ModelCtrlAdptr.loadBall(fac, paintFac);
			}
		});
		make.setToolTipText("Click to make the ball selected");

		dropDown.add(make);
		Ball1.setToolTipText("Type to be combined");

		dropDown.add(Ball1);
		Ball2.setToolTipText("Type to be combined");

		dropDown.add(Ball2);
		combine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				TDropListItem fac1 = (TDropListItem) BallWorldView.this.Ball1.getSelectedItem();
				@SuppressWarnings("unchecked")
				TDropListItem fac2 = (TDropListItem) BallWorldView.this.Ball2.getSelectedItem();
				TDropListItem facCombined = BallWorldView.this.view2ModelCtrlAdptr.combineStrategyFac(fac1, fac2);
				BallWorldView.this.Ball1.addItem(facCombined);
				BallWorldView.this.Ball2.addItem(facCombined);
			}
		});
		combine.setToolTipText("combine the two types");

		dropDown.add(combine);

		controlBar.add(switchControl);
		switchControl.setLayout(new GridLayout(2, 0, 3, 0));
		makeSwitch.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				BallWorldView.this.view2ModelCtrlAdptr
						.makeSwitcher((TDropListItem2) BallWorldView.this.paintStrategies.getSelectedItem());
			}
		});
		makeSwitch.setToolTipText("Make a switcher");

		switchControl.add(makeSwitch);
		switchButton.setToolTipText("Switch");
		switchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				TDropListItem fac = (TDropListItem) BallWorldView.this.Ball1.getSelectedItem();
				BallWorldView.this.view2ModelCtrlAdptr.updateSwitcherStrategy(fac);
			}
		});

		switchControl.add(switchButton);

		/**
		 * Clear Screen Button
		 */
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BallWorldView.this.view2ModelCtrlAdptr.clearBalls();
			}
		});
		clear.setToolTipText("Clear everything on the screen");

		controlBar.add(clear);

		controlBar.add(paintControls);
		paintControls.setLayout(new GridLayout(3, 0, 3, 2));

		paintControls.add(txtPaintStrategy);
		addPaintButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = BallWorldView.this.txtPaintStrategy.getText();
				if (text.equals("")) {
					return;
				}
				BallWorldView.this.paintStrategies
						.addItem(BallWorldView.this.view2ModelCtrlAdptr.loadPaintStrategyFac(text));
			}
		});
		paintControls.add(addPaintButton);

		paintControls.add(paintStrategies);
	}

	/**
	 * Start the GUI, i.e. make it visible.
	 */
	public void start() {
		this.setVisible(true);
	}

	/**
	 * Repaint the drawing canvas
	 */
	public void update() {
		getCanvas().repaint();
	}

	/**
	 * Get Container object that is the panel the balls are drawn on
	 * @return canvas balls are painted on as 
	 */
	public Container getCanvas() {
		return ballWorldPanel;
	}

}
