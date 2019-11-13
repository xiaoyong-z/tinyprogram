package frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;

import common.Administrator;
import common.DataProcessing;
import common.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.*;
import javax.swing.border.CompoundBorder;
import javax.swing.UIManager;
public class userFrame extends JFrame {

	private JTabbedPane tabbedPane;
	private JPanel panel_del;
	private JPanel panel_mod;
	private JPanel panel_add;
	
	
	private JTextField add_name_text;
	private JPasswordField add_password_text;
	private String add_role;
	private JComboBox add_role_select;
	
	private String change_role;
	private String change_name;
	private JComboBox change_role_select;
	private JPasswordField change_password_text;
	private JComboBox change_name_select;
	
	private JTable table;
	private JScrollPane pane;
	private DefaultTableModel tableModel;
	private Enumeration<User> enumer;
	private Vector<String> name_record;
	private static userFrame userframe;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					userframe = new userFrame();
					userframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private void change_user() {
		try {
			Administrator.changeUserInfoDB2(change_name, change_password_text.getText(), change_role);
			int select_row = name_record.indexOf(change_name);
			tableModel.setValueAt(change_password_text.getText(), select_row, 1);
			tableModel.setValueAt(change_role.toString(), select_row, 2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void delete_user() {
		int row = table.getSelectedRow();
		String s = table.getValueAt(row, 0).toString();
		if(!Administrator.delUserDB(s)) {
			JOptionPane.showMessageDialog(null, "用户删除失败!");
		}
		else {
			tableModel.removeRow(row);
			name_record.remove(row);
		 
		try {
			Administrator.changeUserInfoDB2(change_name, change_password_text.getText(), change_role);
			int select_row = name_record.indexOf(change_name);
			tableModel.setValueAt(change_password_text.getText(), select_row, 1);
			tableModel.setValueAt(change_role.toString(), select_row, 2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	private void add_user() {
		if(!Administrator.addUserDB(add_name_text.getText(), add_password_text.getText(), add_role)) {
			JOptionPane.showMessageDialog(null, "用户添加失败!");	
		}else {
			Vector<Object> newRow = new Vector<Object>();
	        newRow.addElement(add_name_text.getText());
	        newRow.addElement(add_password_text.getText());
	        newRow.addElement(add_role);
	        change_name_select.addItem(add_name_text.getText());
	        tableModel.addRow(newRow);
	        name_record.add(add_name_text.getText());
		}
	}
	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public userFrame() throws SQLException {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(456, 100, 450, 300);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		panel_add = new JPanel(); //新增用户卡片
		tabbedPane.addTab("新增用户", panel_add);
		panel_add.setLayout(null);
		
		panel_mod = new JPanel(); //修改用户卡片
		tabbedPane.addTab("修改用户", panel_mod);
		panel_mod.setLayout(null);
		
		panel_del = new JPanel(); //删除用户卡片
		tabbedPane.addTab("删除用户", panel_del);
		panel_del.setLayout(null);
		
		JLabel label = new JLabel("\u53E3\u4EE4");
		label.setBounds(47, 91, 72, 18);
		panel_mod.add(label);
		
		JLabel label_1 = new JLabel("\u7528\u6237\u540D");
		label_1.setBounds(47, 47, 72, 18);
		panel_mod.add(label_1);
		
		JLabel label_2 = new JLabel("\u89D2\u8272");
		label_2.setBounds(47, 135, 72, 18);
		panel_mod.add(label_2);
		
		
		JLabel lblNewLabel = new JLabel("\u7528\u6237\u540D");
		lblNewLabel.setBounds(39, 40, 72, 18);
		panel_add.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u53E3\u4EE4");
		lblNewLabel_1.setBounds(39, 81, 72, 18);
		panel_add.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u89D2\u8272");
		lblNewLabel_2.setBounds(39, 123, 72, 18);
		panel_add.add(lblNewLabel_2);
		
	
		

		
		change_name_select = new JComboBox();
		change_name_select.setBounds(119, 44, 181, 24);
		panel_mod.add(change_name_select);
		
		//add组件
		add_name_text = new JTextField();
		add_name_text.setBounds(114, 37, 181, 24);
		panel_add.add(add_name_text);
		add_name_text.setColumns(10);
		
		add_password_text = new JPasswordField();
		add_password_text.setBounds(114, 78, 181, 24);
		panel_add.add(add_password_text);
		
		
		
		add_role_select = new JComboBox();
		add_role_select.setBounds(114, 120, 181, 24);
		panel_add.add(add_role_select);
		add_role_select.addItem("administrator");
		add_role_select.addItem("browser");
	    add_role_select.addItem("operator");
	    add_role_select.insertItemAt("请选择", 0);
	    add_role_select.setSelectedIndex(0);
	    add_role_select.addItemListener(new ItemListener() {
	    	public void itemStateChanged(ItemEvent e) {
	    		if(e.getStateChange()==ItemEvent.SELECTED){
	    			add_role = e.getItem().toString();
	    		}
			}
		});
	   
	
	    JButton add_ok = new JButton("\u589E\u52A0");
	    add_ok.setBounds(118, 170, 72, 27);
	    panel_add.add(add_ok);
	    add_ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				add_user();
			}
		});
	    
	    JButton add_reset = new JButton("\u8FD4\u56DE");
	    add_reset.setBounds(214, 170, 63, 27);
	    panel_add.add(add_reset);
	    add_reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
	    

		

		//change组件
		JButton change_ok = new JButton("\u4FEE\u6539");
		change_ok.setBounds(102, 181, 72, 27);
		panel_mod.add(change_ok);
		change_ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				change_user();
			}
		});
		
		JButton change_reset = new JButton("\u8FD4\u56DE");
		change_reset.setBounds(192, 181, 63, 27);
		panel_mod.add(change_reset);
		
		
		change_password_text = new JPasswordField();
		change_password_text.setBounds(119, 88, 181, 24);
		panel_mod.add(change_password_text);
		change_password_text.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				/*
				change_name_select.setSelectedIndex(0);
				change_password_text.setText("");
				change_name_select.setSelectedIndex(0);
				*/
			}
		});

		Enumeration<User> e = DataProcessing.getAllUser();
		User user;
		while(e.hasMoreElements()) {
			user = e.nextElement();
			change_name_select.addItem(user.getName());
		}
		
		change_name_select.insertItemAt("请选择", 0);
	    change_name_select.setSelectedIndex(0);
	    change_name_select.addItemListener(new ItemListener() {
	    	public void itemStateChanged(ItemEvent e) {
	    		if(e.getStateChange()==ItemEvent.SELECTED){
	    			change_name = e.getItem().toString();
	    		}
			}
		});
	    
		change_role_select = new JComboBox();
		change_role_select.setBounds(119, 132, 181, 24);
		panel_mod.add(change_role_select);
		change_role_select.addItem("administrator");
		change_role_select.addItem("browser");
	    change_role_select.addItem("operator");
	    change_role_select.insertItemAt("请选择", 0);
	    change_role_select.setSelectedIndex(0);
	    change_role_select.addItemListener(new ItemListener() {
	    	public void itemStateChanged(ItemEvent e) {
	    		if(e.getStateChange()==ItemEvent.SELECTED){
	    			change_role = e.getItem().toString();
	    		}
			}
		});
	    

		
	    
	    //delete组件
	    showUserInfoToTable();
		
		JButton del_ok = new JButton("\u5220\u9664");
		del_ok.setBounds(76, 181, 113, 27);
		panel_del.add(del_ok);
		del_ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				delete_user();
			}
		});
		
		JButton del_return = new JButton("\u8FD4\u56DE");
		del_return.setBounds(224, 181, 113, 27);
		panel_del.add(del_return);
		del_return.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
	}
	

	
    
     
	public void showUserInfoToTable() {
		try {
			table = new JTable();
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			name_record = new Vector<String>();
			String[] colName = {"用户名","口令","角色"};
			tableModel = new DefaultTableModel(colName, 0) {};
			
			Enumeration<User> e = DataProcessing.getAllUser();
			User user;
			while (e.hasMoreElements()) { 
					user = e.nextElement();
			    	String name = user.getName();	
			    	String password = user.getPassword();
			        String role = user.getRole(); 
			   	    Object[] str_row = { name,password, role };     //新建一行记录
			   	    tableModel.addRow(str_row);      //向模型添加一行记录
			   	    name_record.add(name);
			}
			table.setModel(tableModel);
			table.setSize(413, 150);
			pane=new JScrollPane(table); //添加滚动条
			pane.setLocation(0, 0);
			pane.setSize(413, 150);
			panel_del.add(pane);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void select_add() {
		tabbedPane.setSelectedIndex(0);
	}
	
	public void select_change() {
		tabbedPane.setSelectedIndex(1);
	}
	
	public void select_delete() {
		tabbedPane.setSelectedIndex(2);
	}
}
