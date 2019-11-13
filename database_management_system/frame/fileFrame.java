package frame;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import common.Administrator;
import common.DataProcessing;
import common.Doc;
import common.Operator;
import common.User;
import common.loginUser;

public class fileFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private Enumeration<Doc> enumer;
	private DefaultTableModel tableModel;
	private JTable table;
	private JScrollPane pane;
	private JPanel download;
	private JFileChooser chooser;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					fileFrame frame = new fileFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void showsuccess() {
		JOptionPane.showConfirmDialog(this, "上传成功","确认对话框",JOptionPane.OK_CANCEL_OPTION);
	}
	
	private void showerr() {
		JOptionPane.showConfirmDialog(this, "上传失败","确认对话框",JOptionPane.OK_CANCEL_OPTION);
	}
	

	
	private void download_button(){
		try {
			chooser.showSaveDialog(null);
			String savepath = chooser.getSelectedFile().toString();
			
			int row = table.getSelectedRow();
			String filename = table.getValueAt(row, 3).toString();
			User.downloadFileFromDB(filename, savepath);	
		}catch (SQLException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "下载失败");	
		}catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "下载失败");	
		}
	}
	
	private void upload_button() {
		try {
			if(Operator.uploadFileDB(loginUser.getname(), textField_3.getText(), textField_1.getText(), textField_2.getText())) {
				showsuccess();
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				Vector<Object> newRow = new Vector<Object>();
				
				File tempfile = new File(textField_3.getText());
				String filename = tempfile.getName();

				newRow.addElement(textField_1.getText());
	            newRow.addElement(loginUser.getname());
	            newRow.addElement(timestamp.toString());
	            newRow.addElement(filename);
	            newRow.addElement(textField_2.getText());
	            tableModel.addRow(newRow);
			}else {
				showerr();
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Create the frame.
	 */
	public fileFrame() {
		chooser=new JFileChooser();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 432, 253);
		contentPane.add(tabbedPane);
		
		download = new JPanel();
		download.setLayout(null);
		tabbedPane.addTab("下载文件", null, download, null);
		
		JPanel upload = new JPanel();
		upload.setLayout(null);
		tabbedPane.addTab("上传文件", null, upload, null);
		showUserInfoToTable();
		

		
		JButton download_button = new JButton("\u4E0B\u8F7D");
		download_button.setBounds(118, 170, 72, 27);
		download.add(download_button);
		download_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
					download_button();
				}
		});
		
		
		
		
		JButton download_cancel = new JButton("\u8FD4\u56DE");
		download_cancel.setBounds(214, 170, 63, 27);
		download.add(download_cancel);
		download_cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		
		JLabel file_description_label = new JLabel("档案描述");
		file_description_label.setBounds(47, 68, 72, 18);
		upload.add(file_description_label);
		
		JLabel file_id_label = new JLabel("档案号");
		file_id_label.setBounds(47, 24, 72, 18);
		upload.add(file_id_label);
		
		JLabel file_name_label = new JLabel("档案文件名");
		file_name_label.setBounds(32, 135, 88, 18);
		upload.add(file_name_label);
		
		JButton upload_ok = new JButton("\u4E0A\u4F20");
		upload_ok.setBounds(102, 181, 72, 27);
		upload.add(upload_ok);
		upload_ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
					upload_button();
			}
		});
		
		
		JButton upload_cancel = new JButton("\u8FD4\u56DE");
		upload_cancel.setBounds(192, 181, 63, 27);
		upload.add(upload_cancel);
		upload_cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		
		chooser.setMultiSelectionEnabled(true);
		JButton open = new JButton("\u6253\u5F00");
		open.setBounds(314, 135, 63, 18);
		upload.add(open);
		
		textField_1 = new JTextField();
		textField_1.setBounds(119, 21, 181, 24);
		upload.add(textField_1);
		textField_1.setColumns(10);

		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(119, 65, 181, 57);
		upload.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(119, 132, 181, 24);
		upload.add(textField_3);
		
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				chooser.showOpenDialog(null);
				textField_3.setText(chooser.getSelectedFile().toString());
			}
		});
	}
	
	public void showUserInfoToTable() {
		try {       
			table = new JTable();
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			String[] colName = {"档案号","创建者","时间","文件名", "描述"};
			tableModel = new DefaultTableModel(colName, 0) { };
			String sql = "select * from doc_info";
			ResultSet rs = DataProcessing.executeDB(sql); 
			while (rs.next()) { 
	          	String id = rs.getString("Id");	
	          	String creator = rs.getString("creator");
		        String timestamp = rs.getString("timestamp");
		        String description = rs.getString("description");
		        String filename = rs.getString("filename");
	         	Object[] str_row = {id, creator, timestamp, filename,description};     //新建一行记录
	         	tableModel.addRow(str_row);      //向模型添加一行记录
	        }
			table.setModel(tableModel);  
			table.setSize(413, 150);
			pane=new JScrollPane(table); //添加滚动条
			pane.setLocation(0, 0);
			pane.setSize(413, 150);
			download.add(pane);
			/*
			String[][] tableValue = new String[1][5];
			enumer = DataProcessing.getAllDocs();
			Doc doc;
			int row = 0;
			while(enumer.hasMoreElements()) {
				doc = enumer.nextElement();
				tableValue[row][0] = doc.getID();
				tableValue[row][1] = doc.getCreator();
				tableValue[row][2] = doc.getTimestamp().toString();
				tableValue[row][3] = doc.getDescription();
				tableValue[row][4] = doc.getFilename();
				row++;
			}
			tableModel = new DefaultTableModel(tableValue, colName);
			table = new JTable(tableModel);
			table.setModel(tableModel);
			table.setSize(413, 150);
			pane=new JScrollPane(table); //添加滚动条
			pane.setLocation(0, 0);
			pane.setSize(413, 150);
			download.add(pane);
			*/
		} catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "加载不成功!"); 
		}
		
	}

}
