package frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import common.Administrator;
import common.User;
import common.loginUser;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class selfFrame extends JFrame {

	private JPanel contentPane;
	private JTextField name_field;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField_2;
	private JTextField role_field;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					selfFrame frame = new selfFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void change_pass() throws HeadlessException, SQLException {
		String init_pass = passwordField.getText();
		String new_pass = passwordField_1.getText();
		String new_pass_2 = passwordField_2.getText();
		if(!init_pass.equals(loginUser.getpassword())) {
			showerr();
		}
		else if(!new_pass.equals(new_pass_2)){
			showerr2();
		}
		else{
			if(!Administrator.changeUserInfoDB(loginUser.getname(), new_pass))
				JOptionPane.showMessageDialog(null, "修改失败!");	
			else {
				loginUser.setpassword(new_pass);
				JOptionPane.showMessageDialog(null, "修改成功!");	
			}
		}
	}
	
	
	private void showerr() {
		JOptionPane.showConfirmDialog(this, "旧密码错误","确认对话框",JOptionPane.OK_CANCEL_OPTION);
	}
	
	private void showerr2() {
		JOptionPane.showConfirmDialog(this, "新密码不一致","确认对话框",JOptionPane.OK_CANCEL_OPTION);
	}
	/**
	 * Create the frame.
	 */
	@SuppressWarnings("deprecation")
	public selfFrame() {
		super.setTitle("个人信息管理");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u7528\u6237\u540D");
		lblNewLabel.setBounds(37, 42, 72, 18);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u539F\u53E3\u4EE4");
		lblNewLabel_1.setBounds(37, 73, 72, 18);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u65B0\u53E3\u4EE4");
		lblNewLabel_2.setBounds(37, 104, 72, 18);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("\u786E\u8BA4\u65B0\u53E3\u4EE4");
		lblNewLabel_3.setBounds(37, 135, 89, 18);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("\u89D2\u8272");
		lblNewLabel_4.setBounds(37, 172, 72, 18);
		contentPane.add(lblNewLabel_4);
		
		JButton ok = new JButton("\u4FEE\u6539");
		ok.setBounds(77, 213, 113, 27);
		contentPane.add(ok);
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					change_pass();
				} catch (HeadlessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		JButton return_button = new JButton("\u8FD4\u56DE");
		return_button.setBounds(192, 213, 113, 27);
		contentPane.add(return_button);
		return_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		name_field = new JTextField();
		name_field.setBounds(134, 39, 171, 24);
		contentPane.add(name_field);
		name_field.setColumns(10);
		name_field.setText(loginUser.getname());
		name_field.disable();
		
		passwordField = new JPasswordField();
		passwordField.setBounds(134, 70, 171, 24);
		contentPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(134, 101, 171, 24);
		contentPane.add(passwordField_1);
		
		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(134, 132, 171, 24);
		contentPane.add(passwordField_2);
		
		role_field = new JTextField();
		role_field.setColumns(10);
		role_field.setBounds(134, 169, 171, 24);
		contentPane.add(role_field);
		role_field.setText(loginUser.getrole());
		role_field.disable();
	}
}
