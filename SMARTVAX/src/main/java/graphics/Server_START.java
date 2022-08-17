package graphics;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import centrivaccinaliServer.Server;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class Server_START implements ActionListener {

	private JFrame frame;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton startButton;
	private JLabel resultLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server_START window = new Server_START();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Server_START() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(173, 216, 230));
		frame.setResizable(false);
		frame.setBounds(500, 250, 700, 600);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		JLabel lblNewLabel = new JLabel("Inserisci le credenziali per avviare il server: ");
		lblNewLabel.setFont(new Font("Gadugi", Font.BOLD, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNewLabel_1 = new JLabel("Username: ");
		lblNewLabel_1.setFont(new Font("Gadugi", Font.BOLD, 12));

		JLabel lblNewLabel_2 = new JLabel("Password: ");
		lblNewLabel_2.setFont(new Font("Gadugi", Font.BOLD, 12));

		usernameField = new JTextField();
		usernameField.setColumns(10);

		passwordField = new JPasswordField();

		startButton = new JButton("AVVIA IL SERVER");
		startButton.setBackground(new Color(224, 255, 255));
		startButton.setFont(new Font("Gadugi", Font.BOLD, 13));
		startButton.addActionListener(this);
		startButton.setBorder(new RoundedBorder(10));

		resultLabel = new JLabel("Result");
		resultLabel.setVisible(false);

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addGap(40).addComponent(lblNewLabel,
										GroupLayout.PREFERRED_SIZE, 601, GroupLayout.PREFERRED_SIZE))
								.addGroup(
										groupLayout.createSequentialGroup().addGap(121)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(lblNewLabel_1).addComponent(lblNewLabel_2))
												.addGap(26)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
														.addComponent(usernameField).addComponent(passwordField,
																GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)))
								.addGroup(groupLayout.createSequentialGroup().addGap(236).addComponent(startButton,
										GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup().addGap(223).addComponent(resultLabel,
										GroupLayout.PREFERRED_SIZE, 340, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(43, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(34).addComponent(lblNewLabel).addGap(108)
				.addGroup(groupLayout
						.createParallelGroup(Alignment.LEADING).addComponent(lblNewLabel_1).addComponent(usernameField,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(58)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_2).addComponent(
						passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE))
				.addGap(97).addComponent(startButton, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
				.addGap(38).addComponent(resultLabel).addContainerGap(109, Short.MAX_VALUE)));
		frame.getContentPane().setLayout(groupLayout);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// AGGIUNGERE ACTION QUANDO VIENE PREMUTO UN PULSANTE
		// SE LE CREDENZIALI SONO CORRETTE ALLORA ESEGUE:

		resultLabel.setText("");
		Server server = null;

		try {

			server = new Server(usernameField.getText(), String.valueOf(passwordField.getPassword()));

		} catch (IOException | SQLException e1) {

			e1.printStackTrace();
		} finally {
			try {
				server.Start();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
