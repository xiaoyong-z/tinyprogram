package frame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import common.DataProcessing;
import common.User;
import common.loginUser;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.awt.Font;

public class loginFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	private void showerr() {
		//JOptionPane optionpane=new JOptionPane();
		JOptionPane.showConfirmDialog(this, "用户名或密码错误","确认对话框",JOptionPane.OK_CANCEL_OPTION);
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginFrame frame = new loginFrame();
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
	public loginFrame() {
		setTitle("\u7528\u6237\u767B\u5F55");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u7528\u6237\u540D");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(79, 63, 72, 18);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u5BC6\u7801");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(79, 115, 72, 18);
		contentPane.add(label_1);
		
		textField = new JTextField();
		textField.setBounds(165, 60, 151, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("宋体", Font.PLAIN, 12));
		passwordField.setBounds(165, 112, 151, 24);
		contentPane.add(passwordField);
		
		JButton button = new JButton("\u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name=textField.getText();
				//String password=passwordField.getPassword().toString();
				@SuppressWarnings("deprecation")
				String password=String.valueOf(passwordField.getPassword());
				try {
					User user=DataProcessing.searchUserDB(name, password);
					if(user==null)
						showerr();
					else{
						loginUser.setname(user.getName());
						loginUser.setpassword(user.getPassword());
						loginUser.setrole(user.getRole());
						user.showMenu();
						setVisible(false);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button.setBounds(79, 175, 72, 27);
		contentPane.add(button);
		
		JButton btnNewButton = new JButton("\u53D6\u6D88");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnNewButton.setBounds(244, 175, 72, 27);
		contentPane.add(btnNewButton);
	}

}
