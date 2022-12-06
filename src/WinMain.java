import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WinMain extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			WinMain dialog = new WinMain();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public WinMain() {
		setTitle("ICI 주소록");
		setBounds(100, 100, 315, 421);
		getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
		{
			JButton btnInsertMember = new JButton("");
			btnInsertMember.addActionListener(new ActionListener() {   //회원추가
				public void actionPerformed(ActionEvent e) {
					
					WinInsertMember winInsertMember = new WinInsertMember();
					setVisible(false);
					winInsertMember.setModal(true);
					winInsertMember.setVisible(true);
					
					
				}
			});
			btnInsertMember.setToolTipText("회원추가");
			btnInsertMember.setIcon(new ImageIcon("E:\\img\\add.png"));
			getContentPane().add(btnInsertMember);
		}
		{
			JButton btnUpdateMember = new JButton("");
			btnUpdateMember.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { // 회원정보 수정
				
					WinSearch winSearch = new WinSearch();
					winSearch.setModal(true);
					winSearch.setVisible(true);
					
					
				}
			});
			btnUpdateMember.setToolTipText("회원정보변경");
			btnUpdateMember.setIcon(new ImageIcon("E:\\img\\show.png"));
			getContentPane().add(btnUpdateMember);
		}
		{
			JButton btnDeleteMember = new JButton("");
			btnDeleteMember.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { // 회원정보 지우기
					
					WinSearch winSearch = new WinSearch();
					winSearch.setModal(true);
					winSearch.setVisible(true);
				}
			});
			btnDeleteMember.setToolTipText("회원삭제");
			btnDeleteMember.setIcon(new ImageIcon("E:\\img\\del.png"));
			getContentPane().add(btnDeleteMember);
		}
		{
			JButton btnSearchMember = new JButton("");
			btnSearchMember.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					WinSearchAll winSearchAll = new WinSearchAll();
					winSearchAll.setModal(true);
					winSearchAll.setVisible(true);
					
				}
			});
			btnSearchMember.setToolTipText("회원검색");
			btnSearchMember.setIcon(new ImageIcon("E:\\img\\search.png"));
			getContentPane().add(btnSearchMember);
		}
		{
			JButton btnMoney = new JButton("");
			btnMoney.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {					
					WinCollectMoney winCollectMoney = new WinCollectMoney();
					winCollectMoney.setModal(true);
					winCollectMoney.setVisible(true);					
				}
			});
			btnMoney.setIcon(new ImageIcon("E:\\img\\money.png"));
			getContentPane().add(btnMoney);
		}
		{
			JButton btnAllMoney = new JButton("");
			btnAllMoney.setToolTipText("회비내역");
			btnAllMoney.setIcon(new ImageIcon("E:\\img\\execute.png"));
			btnAllMoney.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WinMoneyAll winMoneyAll = new WinMoneyAll();
					winMoneyAll.setModal(true);
					winMoneyAll.setVisible(true);
				}
			});
			getContentPane().add(btnAllMoney);
		}
	}

}
