package frame;


import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import common.Administrator;
import common.User;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class mainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenu useradmin;
	private JMenu selfinfo;
	private JMenu fileadmin;
	private JMenuItem changeuser;
	private JMenuItem deluser;
	private JMenuItem adduser;
	private JMenuItem fileupload;
	private JMenuItem filedownload;
	private JMenuItem changeinfo;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainFrame frame = new mainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public mainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 2000, 1500);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		useradmin = new JMenu("\u7528\u6237\u7BA1\u7406");
		menuBar.add(useradmin);
		
	    changeuser = new JMenuItem("\u4FEE\u6539\u7528\u6237");
		useradmin.add(changeuser);
		changeuser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					userFrame window = new userFrame();
					window.setVisible(true);
					window.select_change();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
	    deluser = new JMenuItem("\u5220\u9664\u7528\u6237");
		useradmin.add(deluser);
		deluser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					userFrame window = new userFrame();
					window.setVisible(true);
					window.select_delete();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		
		adduser = new JMenuItem("\u65B0\u589E\u7528\u6237");
		useradmin.add(adduser);
		adduser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					userFrame window = new userFrame();
					window.setVisible(true);
					window.select_add();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
			
		fileadmin = new JMenu("\u6863\u6848\u7BA1\u7406");
		menuBar.add(fileadmin);
		
		fileupload = new JMenuItem("\u6863\u6848\u4E0A\u4F20");
		fileupload.setHorizontalAlignment(SwingConstants.CENTER);
		fileadmin.add(fileupload);
		fileupload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame fileframe=new fileFrame();
				fileframe.setVisible(true);
			}
		});
		
		filedownload = new JMenuItem("\u6863\u6848\u4E0B\u8F7D");
		filedownload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame fileframe=new fileFrame();
				fileframe.setVisible(true);
			}
			
		});
		fileadmin.add(filedownload);
		
		
		
		selfinfo = new JMenu("\u4E2A\u4EBA\u4FE1\u606F\u7BA1\u7406");
		menuBar.add(selfinfo);
		
		changeinfo = new JMenuItem("\u4FE1\u606F\u4FEE\u6539");
		selfinfo.add(changeinfo);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		changeinfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selfFrame window = new selfFrame();
				window.setVisible(true);
			}
		});
	}
	
	public void bropermission() {
		this.setTitle("浏览员界面");
		useradmin.setEnabled(false);
		fileupload.setEnabled(false);
	}

	public void opepermission() {
		// TODO Auto-generated method stub
		this.setTitle("录入员界面");
		useradmin.setEnabled(false); 
	}
	
	public void  adopermission() {
		this.setTitle("管理员界面");
	}
}
