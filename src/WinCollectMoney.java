import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WinCollectMoney extends JDialog {
	private JTextField tfMoney;
	private JTextField tfEtc;
	private JTextField tfJumin;
	private JTable table;
	private JButton btnInput;
	private JLabel lblTotalMoney;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinCollectMoney dialog = new WinCollectMoney();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public WinCollectMoney() {
		setBounds(100, 100, 450, 466);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("금액 :");
		lblNewLabel.setBounds(23, 41, 57, 15);
		getContentPane().add(lblNewLabel);
		
		tfMoney = new JTextField();
		tfMoney.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_F1) {
					String temp = tfMoney.getText();
					temp = temp + "0000";
					tfMoney.setText(temp);
				}else if(e.getKeyCode() == KeyEvent.VK_F2) {
					String temp = tfMoney.getText();
					temp = temp + "000";
					tfMoney.setText(temp);
				}
			}
		});
		tfMoney.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			
				if(e.getClickCount() == 1) {
				tfMoney.setSelectionStart(0);
				tfMoney.setSelectionEnd(tfMoney.getText().length());
			}else {
				tfMoney.requestFocus();
			}
			
			}
		});
		tfMoney.setHorizontalAlignment(SwingConstants.RIGHT);
		tfMoney.setBounds(92, 38, 116, 21);
		getContentPane().add(tfMoney);
		tfMoney.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("비고 :");
		lblNewLabel_1.setBounds(23, 69, 57, 15);
		getContentPane().add(lblNewLabel_1);
		
		tfEtc = new JTextField();
		tfEtc.setHorizontalAlignment(SwingConstants.LEFT);
		tfEtc.setColumns(10);
		tfEtc.setBounds(92, 66, 290, 21);
		getContentPane().add(tfEtc);
		
		JLabel lblNewLabel_2 = new JLabel("주민번호 :");
		lblNewLabel_2.setBounds(23, 100, 57, 15);
		getContentPane().add(lblNewLabel_2);
		
		tfJumin = new JTextField();
		tfJumin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {					
						showPersnel();					
				}
				
			}
		});
		tfJumin.setHorizontalAlignment(SwingConstants.LEFT);
		tfJumin.setColumns(10);
		tfJumin.setBounds(92, 97, 191, 21);
		getContentPane().add(tfJumin);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 143, 399, 274);
		getContentPane().add(scrollPane);
		
		String columnNames[] = {"번호","입금날짜","금액","비고"};
		DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);				
		table = new JTable(dtm);
		scrollPane.setViewportView(table);
		
		btnInput = new JButton("입금");
		btnInput.setEnabled(false);
		btnInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				InsertResult();  // 입금정보 넣기
				resultResult(); // 입금내역
								
			}
		});
		btnInput.setBounds(295, 96, 97, 23);
		getContentPane().add(btnInput);
		
		lblTotalMoney = new JLabel("총회비:");
		lblTotalMoney.setFont(new Font("굴림", Font.PLAIN, 16));
		lblTotalMoney.setBounds(241, 10, 141, 46);
		getContentPane().add(lblTotalMoney);

		resultResult();  // 입금 정보 내역 확인
	}

	
	protected void showPersnel() {
		DefaultTableModel dtm = (DefaultTableModel)table.getModel();
		dtm.setRowCount(0);  // 테이블 행의 수를 0을 만들어라. 싹 지우기
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root", "12345");
			Statement stmt = con.createStatement();	
			String sql = "SELECT * FROM moneytbl WHERE jumin='"+ tfJumin.getText() +"'";
			ResultSet rs = stmt.executeQuery(sql);
			
				if(rs.next()) {					
				sql = "SELECT * FROM moneytbl WHERE jumin='"+ tfJumin.getText() +"'";
				rs = stmt.executeQuery(sql);				
				int cnt=0;
				int total = 0;
				String record[] = new String[5];
					while(rs.next()) {
						record[0] = Integer.toString(++cnt);
						record[1] = rs.getString("mdate");	
						record[2] = rs.getString("money");
						record[3] = rs.getString("etc");
						dtm.addRow(record);
						total = total + rs.getInt("money");
					}
					lblTotalMoney.setText("총 회비: "+total);
					btnInput.setEnabled(true);
				}else {
					
					JOptionPane.showMessageDialog(null, "주민번호가 없는 정보입니다. 다시 확인해주세요.");
				}	
		} catch (Exception e1) {	
			e1.printStackTrace();
		}
		
	}
		


	protected void InsertResult() { // 입금 정보 넣기		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root", "12345");
			Statement stmt = con.createStatement();	
			String sql = "INSERT INTO moneyTBL(jumin,mdate,money,etc) VALUES ('"+ tfJumin.getText() +"', curdate(),"+tfMoney.getText()+",'"+tfEtc.getText()+"')";
						
			if(stmt.executeUpdate(sql)>0) {				
				JOptionPane.showMessageDialog(null, "입금이 완료되었습니다.");						
								
			} else {
				JOptionPane.showMessageDialog(null, "다시 입금해주세요");				
			}
			
		} catch (Exception e1) {	
			e1.printStackTrace();
		}
		
	}
	
	protected void resultResult() {  // 입금 정보 기록보기
		
		DefaultTableModel dtm = (DefaultTableModel)table.getModel();
		dtm.setRowCount(0);  // 테이블 행의 수를 0을 만들어라. 싹 지우기
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root", "12345");
			Statement stmt = con.createStatement();	
			String sql = "SELECT * FROM moneytbl";
			ResultSet rs = stmt.executeQuery(sql);
			
			int cnt=0;
			String record[] = new String[5];			
			int total = 0;
			while(rs.next()) {
				record[0] = Integer.toString(++cnt);
				record[1] = rs.getString("mdate");	
				record[2] = rs.getString("money");
				record[3] = rs.getString("etc");
				dtm.addRow(record);
						
				total = total + rs.getInt("money");
					
			}
			lblTotalMoney.setText("총 회비: "+ total);
			
		} catch (Exception e1) {	
			e1.printStackTrace();
		}
		
	}
		
}
	
	

