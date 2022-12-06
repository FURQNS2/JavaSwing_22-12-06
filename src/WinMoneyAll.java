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
import javax.swing.JRadioButton;
import javax.swing.JPopupMenu;
import java.awt.Component;
import javax.swing.JMenuItem;

public class WinMoneyAll extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfName;
	private JTable table;
	private JTextField tfmobile;
	private JPopupMenu popupMenu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			WinMoneyAll dialog = new WinMoneyAll();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public WinMoneyAll() {
		setTitle("전체회비내역");
		setBounds(100, 100, 768, 343);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
				
		{	
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				String columnNames[] = {"번호","이름","금액","날짜","비고"};
				DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
				
				table = new JTable(dtm);
				table.addMouseListener(new MouseAdapter() {					
					@Override
					public void mouseReleased(MouseEvent e) {
						if(e.getButton() == 3) {  //1-횐쪽버튼, 2-가운데버튼, 3-오른쪽 버튼
							int row = table.rowAtPoint(e.getPoint());
							int col = table.columnAtPoint(e.getPoint());
							if(!table.isRowSelected(row))  // ! - ~가 아니라면 
								table.changeSelection(row, col, false, false);							
								popupMenu.show(e.getComponent(), e.getX(), e.getY());
							
						}
					}
				});
				{
					popupMenu = new JPopupMenu();
					addPopup(table, popupMenu);
					
					{
						JMenuItem mnDeleteMember = new JMenuItem("회원정보 추가");
						mnDeleteMember.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {							
						
									setVisible(false);
									WinInsertMember winInsertMember = new WinInsertMember();									
									winInsertMember.setModal(true);
									winInsertMember.setVisible(true);
								
							}
						});
						popupMenu.add(mnDeleteMember);
					}
					
					{
						JMenuItem mnUpdateMember = new JMenuItem("회원정보 변경");
						mnUpdateMember.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {							
								int row = table.getSelectedRow();								
								
								if(row != -1) { // row가 석택되었을 때(선택이 안되었을 때 -1이 된다.)
									String sJumin = table.getValueAt(row, 1).toString();
									setVisible(false);
									WinUpdateMember winUpdateMember = new WinUpdateMember(sJumin);									
									winUpdateMember.setModal(true);
									winUpdateMember.setVisible(true);
									
									
								}
								
								
							}
						});
						popupMenu.add(mnUpdateMember);
					}
					{
						JMenuItem mnDeleteMember = new JMenuItem("회원정보삭제");
						mnDeleteMember.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
								int row = table.getSelectedRow();											
								
								if(row != -1) { // row가 석택되었을 때(선택이 안되었을 때 -1이 된다.)
									String sJumin = table.getValueAt(row, 1).toString();
									setVisible(false);
									WinDeleteMember winDeleteMember = new WinDeleteMember(sJumin);																		
									winDeleteMember.setModal(true);
									winDeleteMember.setVisible(true);
									
									
								}
								
							}
						});
						popupMenu.add(mnDeleteMember);
					}
				}
				
				scrollPane.setViewportView(table);
				
				showResult();
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
			String sql = "SELECT name, money, mdate, etc FROM addresstbl A, moneytbl M WHERE A.jumin = M.jumin";
			ResultSet rs = stmt.executeQuery(sql);
			
			int cnt=0;
			String record[] = new String[5];			
			
			while(rs.next()) {
				record[0] = Integer.toString(++cnt);
				record[1] = rs.getString("name");	
				record[2] = rs.getString("money");
				record[3] = rs.getString("mdate");
				record[4] = rs.getString("etc");
				
				dtm.addRow(record);
			}			
			
			
		} catch (Exception e1) {	
			e1.printStackTrace();
		}		
	}

	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
