import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WinDoroSearch extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfDoro;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			WinDoroSearch dialog = new WinDoroSearch("");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public WinDoroSearch(String address) {
		setTitle("도로명 검색");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.NORTH);
			{
				JLabel lblNewLabel = new JLabel("도로명 :");
				panel.add(lblNewLabel);
			}
			{
				tfDoro = new JTextField();
				tfDoro.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if(e.getKeyCode() == KeyEvent.VK_ENTER) {
							showResult(); //검색결과 테이블로 출력
						}
					}
				});
				panel.add(tfDoro);
				tfDoro.setColumns(10);
			}
			{
				JButton btnDoro = new JButton("검색");
				btnDoro.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						showResult();
					}
				});
				panel.add(btnDoro);
			}
		}
		{	
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				String columnNames[] = {"번호","도로명","시/도","구/군","동/면/읍"};
				DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
				
				table = new JTable(dtm);
				table.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						setVisible(false);
					}
				});
				
				scrollPane.setViewportView(table);
			}
		}
	}

	protected void showResult() {  // 검색 결과를 테이블에 출력
		DefaultTableModel dtm = (DefaultTableModel)table.getModel();
		dtm.setRowCount(0);  // 테이블 행의 수를 0을 만들어라. 싹 지우기
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root", "12345");
			Statement stmt = con.createStatement();	
			String sql = "SELECT * FROM addrdbtbl WHERE doro Like'"+tfDoro.getText()+"%'";
			ResultSet rs = stmt.executeQuery(sql);
			
			int cnt=0;
			String record[] = new String[5];			
			
			while(rs.next()) {
				record[0] = Integer.toString(++cnt);
					for(int i=1; i<record.length; i++) {
						record[i] = rs.getString(i);
					dtm.addRow(record);
				}
			}			
		} catch (Exception e1) {	
			e1.printStackTrace();
		}		
	}

	public String getAddress() { // 주소 전달하기		
		int row = table.getSelectedRow();		
		
			String temp = table.getValueAt(row, 2).toString() + " ";
			temp = temp + table.getValueAt(row, 3).toString() + " (";
			temp = temp + table.getValueAt(row, 4).toString() + ") ";
			temp = temp + table.getValueAt(row, 1).toString() + " ";
			
		return temp;
	}

}
