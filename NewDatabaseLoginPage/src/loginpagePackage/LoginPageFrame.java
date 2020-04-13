package loginpagePackage;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class LoginPageFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtTypeUsernameHere;
	private JPasswordField passwordField;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPageFrame frame = new LoginPageFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

//	login page GUI
	public LoginPageFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 549);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(53, 71, 593, 321);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(261, 39, 71, 16);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("Username Not In Use");
		lblNewLabel_2.setForeground(Color.RED);
		lblNewLabel_2.setBounds(226, 67, 140, 16);
		panel.add(lblNewLabel_2);
		lblNewLabel_2.setVisible(false);

		txtTypeUsernameHere = new JTextField();
		txtTypeUsernameHere.setBounds(181, 95, 231, 29);
		panel.add(txtTypeUsernameHere);
		txtTypeUsernameHere.setText("Type Username Here ...");
		txtTypeUsernameHere.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(266, 163, 61, 16);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_3 = new JLabel("Incorrect Password");
		lblNewLabel_3.setForeground(Color.RED);
		lblNewLabel_3.setBounds(237, 191, 119, 16);
		panel.add(lblNewLabel_3);
		lblNewLabel_3.setVisible(false);

		passwordField = new JPasswordField();
		passwordField.setBounds(181, 219, 231, 26);
		panel.add(passwordField);

		JButton btnNewButton_1 = new JButton("Create Account");
		btnNewButton_1.setBounds(272, 266, 140, 29);
		panel.add(btnNewButton_1);
		btnNewButton_1.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_1.addActionListener(new ActionListener() {

//			open account create page window, close login page window
			@Override
			public void actionPerformed(ActionEvent e) {
				AccountCreatePage H = new AccountCreatePage();
				H.setVisible(true);
				dispose();
			}
		});

		JButton btnNewButton = new JButton("Login");
		btnNewButton.setBounds(181, 266, 79, 29);
		panel.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

//				future: only use char[] for security reasons
				String uname = txtTypeUsernameHere.getText();
				char[] pass1 = passwordField.getPassword();
				String PassString1 = new String(pass1);

//			    reset pop-up windows 
				lblNewLabel_3.setVisible(false);
				lblNewLabel_2.setVisible(false);
				boolean usernameExists = false;
				boolean passwordExists = false;

				try {

//					access database , implement checks 
//					- does user-name exist
//					- does password match 

					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
					java.sql.Statement stat = con.createStatement();
					String sql = "SELECT * FROM user";
					ResultSet rs = stat.executeQuery(sql);
//					select all database, cycle through all user-name until user-name matches, 

					while (rs.next()) {
						String Hashed = rs.getString("Password");

						if (uname.equals(rs.getString("Username"))) {
							usernameExists = true;
//							user-name correct

							if (BCrypt.checkpw(PassString1, Hashed)) {
								passwordExists = true;
//								User-name and Password correct
							} else {
//								User-name correct Password incorrect
							}
						} else {
//							"User-name incorrect Password incorrect

						}
					}

				} catch (SQLException e1) {
					System.out.println("SQL Exception: " + e1.toString());
				} catch (ClassNotFoundException cE) {
					System.out.println("Class Not Found Exception: " + cE.toString());
				}

//				if user-name and login are correct login 
				if ((usernameExists == true) && (passwordExists == true)) {

					lblNewLabel_2.setVisible(false);
					lblNewLabel_3.setVisible(false);
					System.out.println("thisworks2");
					JOptionPane.showMessageDialog(lblNewLabel_2, "well done " + uname + " you have logged in");

				}
//				if only username is correct, display invalid password
				if (usernameExists == true) {
					lblNewLabel_2.setVisible(false);
					lblNewLabel_3.setVisible(true);

//			    if user-name is incorrect display user-name invalid

				} else {
					lblNewLabel_2.setVisible(true);
					lblNewLabel_3.setVisible(false);

				}

			}
		}); // action listener end
	}
}
