package loginpagePackage;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class AccountCreatePage extends JFrame {

	private JPanel contentPane;
	private JTextField txtTypeNewUsername;
	private JPasswordField passwordField_1;

//	run GUI in main
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AccountCreatePage frame = new AccountCreatePage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AccountCreatePage() {

//		Create GUI
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 710, 572);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(47, 44, 615, 462);
		contentPane.add(panel);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(118, 21, 378, 125);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel = new JLabel("Create Username");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Apple SD Gothic Neo", Font.PLAIN, 24));
		lblNewLabel.setBounds(76, 10, 225, 26);
		panel_1.add(lblNewLabel);

		JLabel lblNewLabel_3 = new JLabel("Username  Already Exists");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setForeground(Color.RED);
		lblNewLabel_3.setBounds(109, 48, 159, 16);
		panel_1.add(lblNewLabel_3);
		lblNewLabel_3.setVisible(false);

		txtTypeNewUsername = new JTextField();
		txtTypeNewUsername.setHorizontalAlignment(SwingConstants.CENTER);
		txtTypeNewUsername.setBounds(6, 80, 366, 26);
		panel_1.add(txtTypeNewUsername);
		txtTypeNewUsername.setText("Type New Username here ...");
		txtTypeNewUsername.setColumns(10);

		JPanel panel_1_1_1 = new JPanel();
		panel_1_1_1.setBounds(118, 313, 378, 125);
		panel.add(panel_1_1_1);
		panel_1_1_1.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("Confirm Password");
		lblNewLabel_2.setBounds(132, 6, 114, 16);
		panel_1_1_1.add(lblNewLabel_2);

		JButton btnNewButton_1 = new JButton("Login Page");
		btnNewButton_1.setBounds(6, 89, 112, 29);
		panel_1_1_1.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {

//      Open login page window , close create account page window 

			@Override
			public void actionPerformed(ActionEvent e) {
				LoginPageFrame H = new LoginPageFrame();
				H.setVisible(true);

				dispose();
			}
		});

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(6, 28, 366, 26);
		panel_1_1_1.add(passwordField_1);
		passwordField_1.setToolTipText("");

		JCheckBox chckbxNewCheckBox = new JCheckBox("I Am Not A Robot");
		chckbxNewCheckBox.setBounds(118, 60, 142, 23);
		panel_1_1_1.add(chckbxNewCheckBox);

		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(118, 167, 378, 125);
		panel.add(panel_1_1);
		panel_1_1.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Create Password");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Apple SD Gothic Neo", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(116, 6, 146, 16);
		panel_1_1.add(lblNewLabel_1);

		JLabel lblNewLabel_4 = new JLabel("Password Must Contain At least 6 Characters");
		lblNewLabel_4.setForeground(Color.RED);
		lblNewLabel_4.setBounds(48, 28, 282, 16);
		panel_1_1.add(lblNewLabel_4);
		lblNewLabel_4.setVisible(false);

		JLabel lblNewLabel_5 = new JLabel("one Uppercase + Lowercase + number excluding spaces");
		lblNewLabel_5.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setForeground(Color.RED);
		lblNewLabel_5.setBounds(11, 50, 355, 16);
		panel_1_1.add(lblNewLabel_5);
		lblNewLabel_5.setVisible(false);

		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(6, 98, 366, 26);
		panel_1_1.add(passwordField);

		JButton btnNewButton = new JButton("Create Account");
		btnNewButton.setBounds(232, 89, 140, 29);
		panel_1_1_1.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

//				 passwords appear in character array , we need to make sure they match
//				check passwords for length , special characters and upper/lower case differences.
//				
//				turning password into String is slightly worse security wise as strings are immutable,
//				plus its easier to accidently print password if its a string than a char array

				String uname = txtTypeNewUsername.getText();
				char[] pass1 = passwordField.getPassword();
				char[] pass2 = passwordField_1.getPassword();
				String PassString1 = new String(pass1);
				new String(pass2);

//				create encrypted version of password using BCrypt. 
//				if i wanted to make this more secure i would never turn the Char[] into a string .
//				i would then change the elements of Char[] to remove the chance of being memory hacked.

				String hashedpassword = BCrypt.hashpw(PassString1, BCrypt.gensalt());

//				now there are multiple checks to display different messages 
//				-if password contains a upper and lowercase
//				- password contains number
//				- password is longer than 6 char
//				- password doesnt contain spaces
//				passwords match

//				later
//				-if username already exists

				boolean capitalFlag = true;
				boolean lowerCaseFlag = true;
				boolean numberFlag = true;
				boolean lengthFlag = true;
				boolean spaceFlag = true;

				lblNewLabel_5.setVisible(false);
				lblNewLabel_4.setVisible(false);

				boolean noerrors = true;
				boolean noerrors1 = true;
				boolean noerrors2 = true;

				boolean usernameExists = false;

//				 Check password Strength ( this should be char[] )
				if (!PassString1.matches(".*[A-Z].*"))
					capitalFlag = false;

				if (!PassString1.matches(".*[a-z].*"))
					lowerCaseFlag = false;

				if (!PassString1.matches(".*\\d.*"))
					numberFlag = false;

				if (PassString1.length() < 6)
					lengthFlag = false;

				if (PassString1.matches("[\\s]"))
					spaceFlag = false;

				if (!(numberFlag && capitalFlag && lowerCaseFlag && lengthFlag && spaceFlag)) {
					lblNewLabel_5.setVisible(true);
					lblNewLabel_4.setVisible(true);
					noerrors = false;
				} else {
					lblNewLabel_5.setVisible(false);
					lblNewLabel_4.setVisible(false);
					noerrors = true;
				}

//				check passwords match
				if (!Arrays.equals(pass1, pass2)) {
					JOptionPane.showMessageDialog(passwordField, "Passwords Don't Match");
					noerrors1 = false;

				} else {
					noerrors1 = true;
				}

//				check if robot box is ticked ( this should be replaced with a CAPTCHA)
				if (!(chckbxNewCheckBox.isSelected())) {

					JOptionPane.showMessageDialog(chckbxNewCheckBox, "Are You A Robot?");
					noerrors2 = false;
				} else {
					noerrors2 = true;
				}

//				if it passes all checks and ready,only now are we going to access database,
//				on a large scale this would help with speed and connection issues as less 
//				people are accessing the database the least amount of times

//				now check user-name already exists 
//				make a boolean check to process afterwards
				if ((noerrors && noerrors1 && noerrors2) == true) {

					try {

						Class.forName("com.mysql.jdbc.Driver");

//						access website, login, password   ( for better security create a user-name and password)
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
						java.sql.Statement stat = con.createStatement();

//						what to select/find in query (select all from database "user")
						String sql = "SELECT * FROM user";

//					    executeQuery (withdraw information)
						ResultSet rs = stat.executeQuery(sql);

//						while there is information in row, cycle through column compare user-name to input/proposed user-name
						while (rs.next()) {

							if (uname.equals(rs.getString("Username"))) {
								usernameExists = true;

							}
						}

					} catch (SQLException e1) {
						System.out.println("SQL Exception: " + e1.toString());
					} catch (ClassNotFoundException cE) {
						System.out.println("Class Not Found Exception: " + cE.toString());
					}

//					if user-name exists don't carry on and open dialog box  
					if (usernameExists == true) {
						lblNewLabel_3.setVisible(true);

					} else {

//						create account, go to database and add hashed-password and user-name to table
						JOptionPane.showMessageDialog(chckbxNewCheckBox, "Account Created");

						try {
							Class.forName("com.mysql.jdbc.Driver");
							Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root",
									"");
							PreparedStatement ps = conn
									.prepareStatement("insert into user(Username,Password)values(?,?)");
							ps.setString(1, txtTypeNewUsername.getText());
							ps.setString(2, hashedpassword);
							ps.executeUpdate();
//						    executeUpdate (Update/add information)

						} catch (Exception e1) {
							System.out.println(e1);

						}

					}

				}

			} // End of Action listener

		});

	}

}
