package graphics;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class HomeCittadino_Frame implements ActionListener, MouseListener{

	private JFrame frmSmartvaxHome;
	private JLabel backToHome;
	private JLabel userLabel;
	private JLabel pwLabel;
	private JTextField userField;
	private JPasswordField passwordField;
	private JButton freeArea;
	private JButton loginButton;
	private JLabel lblNewLabel_1;
	private JButton registerButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeCittadino_Frame window = new HomeCittadino_Frame();
					window.frmSmartvaxHome.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HomeCittadino_Frame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSmartvaxHome = new JFrame("Home cittadino");
		frmSmartvaxHome.setTitle("SmartVax - Home cittadino");
		frmSmartvaxHome.setResizable(false);
		frmSmartvaxHome.getContentPane().setBackground(new Color(175, 238, 238));
		frmSmartvaxHome.setBounds(500, 250, 700, 600);
		frmSmartvaxHome.setLocationRelativeTo(null);
		frmSmartvaxHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSmartvaxHome.setVisible(true);
		
		//aggiungo label per tornare alla homepage
		backToHome = new JLabel("Torna alla homepage");
		backToHome.setFont(new Font("Tahoma", Font.BOLD, 12));
		backToHome.addMouseListener(this);
		
		//aggiungo label per inserire immagine user
		userLabel = new JLabel("");
		userLabel.setSize(40, 40);
		ImageIcon userIcon = new ImageIcon(getClass().getClassLoader().getResource("user.png")); //new ImageIcon("icons\\user.png");
		Image userImg = userIcon.getImage();
		Image userScaling = userImg.getScaledInstance(userLabel.getWidth(), userLabel.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon userScaled = new ImageIcon(userScaling);
		userLabel.setIcon(userScaled);
		
		//aggiungo label per inserire immagine password
		pwLabel = new JLabel("");
		pwLabel.setSize(40, 40);
		ImageIcon pwIcon = new ImageIcon(getClass().getClassLoader().getResource("pw.png")); //new ImageIcon("icons\\pw.png");
		Image pwImg = pwIcon.getImage();
		Image pwScaling = pwImg.getScaledInstance(pwLabel.getWidth(), pwLabel.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon pwScaled = new ImageIcon(pwScaling);
		pwLabel.setIcon(pwScaled);
		
		//aggiungo campo per inserire username
		userField = new JTextField();
		userField.setColumns(10);
		userField.setBorder(new RoundedBorder(3));
		
		//aggiungo campo per inserire password
		passwordField = new JPasswordField();
		passwordField.setBorder(new RoundedBorder(3));
		
		//aggiungo button area ad accesso libero
		freeArea = new JButton("Area ad accesso libero");
		freeArea.setFont(new Font("Gadugi", Font.BOLD, 12));
		freeArea.setBackground(new Color(135, 206, 235));
		freeArea.addActionListener(this);
		freeArea.setBorder(new RoundedBorder(10));
		
		JLabel lblNewLabel = new JLabel("<html>Utilizza quest'area per accedere alle informazioni<br>relative ai centri vaccinali, senza aver bisogno di registrarti");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		//aggiungo button login
		loginButton = new JButton("LOGIN");
		loginButton.setBackground(new Color(173, 216, 230));
		loginButton.setFont(new Font("Gadugi", Font.BOLD, 12));
		loginButton.setBorder(new RoundedBorder(10));
		ImageIcon loginIcon = new ImageIcon(getClass().getClassLoader().getResource("login.png")); //new ImageIcon("icons\\login.png");
		Image loginImg = loginIcon.getImage();
		Image loginScaling = loginImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon loginScaled = new ImageIcon(loginScaling);
		loginButton.setIcon(loginScaled);
		loginButton.addActionListener(this);
		
		lblNewLabel_1 = new JLabel("Non sei ancora registrato? Registrati ora!");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		//aggiungo button per accedere al form di registrazione
		registerButton = new JButton("REGISTRATI ORA");
		registerButton.setFont(new Font("Gadugi", Font.BOLD, 12));
		registerButton.setBackground(new Color(240, 230, 140));
		registerButton.setBorder(new RoundedBorder(10));
		registerButton.addActionListener(this);
		
		
		GroupLayout groupLayout = new GroupLayout(frmSmartvaxHome.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(107)
					.addComponent(freeArea, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
					.addGap(27)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 344, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(41, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(142)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(pwLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(userLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE))
					.addGap(14)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(userField, GroupLayout.PREFERRED_SIZE, 248, GroupLayout.PREFERRED_SIZE)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(230, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(529, Short.MAX_VALUE)
					.addComponent(backToHome, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(211)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(registerButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblNewLabel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(loginButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE))
					.addContainerGap(240, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(49)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(freeArea, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(userLabel, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
							.addGap(18))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(userField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(33)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(pwLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(8)
							.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(28)
					.addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addGap(62)
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(registerButton, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(55)
					.addComponent(backToHome, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		frmSmartvaxHome.getContentPane().setLayout(groupLayout);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == backToHome) {
			frmSmartvaxHome.dispose();
			Homepage_Frame myFrame = new Homepage_Frame();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() == backToHome) {
			backToHome.setBackground(Color.white);
			backToHome.setForeground(Color.blue);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() == backToHome) {
			backToHome.setBackground(Color.white);
			backToHome.setForeground(Color.black);
		}	
	}

	//AGGIUNGO GLI ACTION EVENTS AI BUTTON
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == registerButton) {
			frmSmartvaxHome.dispose();
			Register_Frame myFrame = new Register_Frame();
		}
		
		if(e.getSource() == freeArea) {
			frmSmartvaxHome.dispose();
			FreeArea_Frame myFrame = new FreeArea_Frame(false);
		}
		
		if(e.getSource() == loginButton) {
			//FARE QUERY PER CONTROLLARE CHE L'UTENTE SI SIA LOGGATO CON I DATI CORRETTI
			frmSmartvaxHome.dispose();
			LoggedIn_Frame myFrame = new LoggedIn_Frame();
		}
	}
}
