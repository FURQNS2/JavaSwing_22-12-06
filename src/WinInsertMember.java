import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.Color;

public class WinInsertMember extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfName;
	private JTextField tfJumin1;
	private JTextField tfMobile;
	private JTextField tfAddress;
	private JPasswordField tfJumin2;
	private JTextField tfCompany;
	private JComboBox cbGYear;
	private JLabel lblPic;
	private String picPath;  //그림 경로

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			WinInsertMember dialog = new WinInsertMember();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public WinInsertMember() {
		setTitle("동문회원 가입");
		setBounds(100, 100, 565, 312);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblPic = new JLabel("사진");
		lblPic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					JFileChooser chooser = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("그림파일", "jpg","gif","png");				
					
					chooser.setFileFilter(filter);
					chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					int ret = chooser.showOpenDialog(null);
						if(ret == JFileChooser.APPROVE_OPTION) {
							picPath = chooser.getSelectedFile().getPath();
							ImageIcon image = new ImageIcon(picPath);
							Image picImage = image.getImage();
							picImage = picImage.getScaledInstance(100, 120, Image.SCALE_SMOOTH);
							ImageIcon image2 =new ImageIcon(picImage);							
							lblPic.setIcon(image);
					}
				}
			}
		});
		
		lblPic.setBackground(new Color(255, 255, 128));
		lblPic.setForeground(new Color(0, 0, 0));
		lblPic.setOpaque(true);
		lblPic.setHorizontalAlignment(SwingConstants.CENTER);
		lblPic.setBounds(420, 10, 100, 120);
		contentPanel.add(lblPic);
		
		JLabel lblNewLabel_1 = new JLabel("주민번호");
		lblNewLabel_1.setBounds(12, 63, 57, 15);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("이름");
		lblNewLabel_2.setBounds(12, 28, 57, 15);
		contentPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("모바일");
		lblNewLabel_3.setBounds(12, 96, 57, 15);
		contentPanel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("주소");
		lblNewLabel_4.setBounds(12, 168, 57, 15);
		contentPanel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("졸업년도");
		lblNewLabel_5.setBounds(223, 136, 57, 15);
		contentPanel.add(lblNewLabel_5);
		
		tfName = new JTextField();
		tfName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfJumin1.requestFocus();
				}
			}
		});
		tfName.setBounds(95, 25, 116, 21);
		contentPanel.add(tfName);
		tfName.setColumns(10);
		
		tfJumin1 = new JTextField();
		tfJumin1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(tfJumin1.getText().length() == 5) {
					tfJumin2.requestFocus();
				}
			}
		});
		tfJumin1.setBounds(95, 60, 116, 21);
		contentPanel.add(tfJumin1);
		tfJumin1.setColumns(10);
		
		tfJumin2 = new JPasswordField();
		tfJumin2.setEchoChar('*');
		tfJumin2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String juminPass = String.valueOf(tfJumin2.getPassword());
				 if(juminPass.length() == 6) {
					tfMobile.requestFocus();
					tfMobile.setText("010");
				}
			}
		});
		tfJumin2.setBounds(223, 60, 116, 21);
		contentPanel.add(tfJumin2);
		
		tfMobile = new JTextField();		
		tfMobile.setToolTipText("\"-\" 없이 입력하세요");
		tfMobile.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String mobileNumber = tfMobile.getText();
				if(mobileNumber.length() == 10 ) {
					tfCompany.requestFocus();
				} 			
				
			}				
		});
		tfMobile.setBounds(95, 93, 116, 21);
		contentPanel.add(tfMobile);
		tfMobile.setColumns(10);
		
		tfAddress = new JTextField();
		tfAddress.setBounds(95, 165, 324, 21);
		contentPanel.add(tfAddress);
		tfAddress.setColumns(10);
		
		JButton btnDoro = new JButton("도로명 검색");
		btnDoro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				WinDoroSearch winDoroSearch = new WinDoroSearch(tfAddress.getText());
				winDoroSearch.setModal(true);
				winDoroSearch.setVisible(true);
				String sAddr = winDoroSearch.getAddress();
				tfAddress.setText(sAddr);
				tfAddress.requestFocus();
				
			}
		});
		btnDoro.setBounds(420, 164, 117, 23);
		contentPanel.add(btnDoro);
		
		JLabel lblNewLabel_2_1 = new JLabel("-");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1.setBounds(205, 63, 24, 15);
		contentPanel.add(lblNewLabel_2_1);
		
		
		
		JLabel lblNewLabel_5_1 = new JLabel("직장");
		lblNewLabel_5_1.setBounds(12, 136, 57, 15);
		contentPanel.add(lblNewLabel_5_1);
		
		tfCompany = new JTextField();
		tfCompany.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					cbGYear.requestFocus();
				}
			}
		});
		tfCompany.setColumns(10);
		tfCompany.setBounds(95, 133, 116, 21);
		contentPanel.add(tfCompany);
		
		cbGYear = new JComboBox();
		cbGYear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				tfAddress.requestFocus();
			}
		});
		cbGYear.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfAddress.requestFocus();
				}
			}
		});
		cbGYear.setBounds(292, 132, 73, 23);
		contentPanel.add(cbGYear);
		
		Calendar calendar = Calendar.getInstance();
		int curYear = calendar.get(Calendar.YEAR);
		
		for(int year=curYear-100; year<=curYear; year++) {
			cbGYear.addItem(year);
		}
		cbGYear.setSelectedItem(curYear);
		
		JButton btnInsert = new JButton("회원 가입");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				insertMember();
			}
		});
		btnInsert.setBounds(95, 216, 97, 23);
		contentPanel.add(btnInsert);
		
		JButton btnCancel = new JButton("취소");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				WinMain winMain = new WinMain();
				winMain.setModal(true);
				winMain.setVisible(true);
			}
		});
		btnCancel.setBounds(205, 216, 97, 23);
		contentPanel.add(btnCancel);
	}

	protected void insertMember() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root", "12345");
			Statement stmt = con.createStatement();	
			String sql = "INSERT INTO addressTBL(jumin,name,mobile,address,gyear,company,pic) VALUES ('"+tfJumin1.getText()+String.valueOf(tfJumin2.getPassword())+"','"+tfName.getText()+"','"+tfMobile.getText()+"','"+tfAddress.getText()+"',"+Integer.parseInt(cbGYear.getSelectedItem().toString())+",'"+ tfCompany.getText() +"','"+ picPath.replace("\\", "/")+"')";
						
			if(stmt.executeUpdate(sql)>0) {				
				JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다.");						
				tfName.setText("");
				tfJumin1.setText("");
				tfJumin2.setText("");
				tfMobile.setText("");
				tfCompany.setText("");
				tfAddress.setText("");
				setVisible(false);
				WinMain winMain = new WinMain();
				winMain.setModal(true);
				winMain.setVisible(true);				
				
			} else {
				JOptionPane.showMessageDialog(null, "다시 작성해주세요");
				tfName.setText("");
				tfJumin1.setText("");
				tfJumin2.setText("");
				tfMobile.setText("");
				tfCompany.setText("");
				tfAddress.setText("");
			}
			
		} catch (Exception e1) {	
			e1.printStackTrace();
		}
		
	}
}
