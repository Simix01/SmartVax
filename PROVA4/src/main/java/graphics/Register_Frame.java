package graphics;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class Register_Frame implements ActionListener, MouseListener{

	private JFrame frmSmartvaxRegistrati;
	private JTextField idVaccinazioneField;
	private JTextField nomeField;
	private JTextField cognomeField;
	private JTextField codFiscaleField;
	private JTextField mailField;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton registerButton;
	private JLabel resultLabel;
	private JLabel backLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register_Frame window = new Register_Frame();
					window.frmSmartvaxRegistrati.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Register_Frame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSmartvaxRegistrati = new JFrame();
		frmSmartvaxRegistrati.setTitle("SmartVax - Registrati");
		frmSmartvaxRegistrati.setResizable(false);
		frmSmartvaxRegistrati.getContentPane().setBackground(new Color(175, 238, 238));
		
		JLabel lblNewLabel = new JLabel("Registrati al sistema inserendo i campi richiesti, nota: tutti i campi sono obbligatori");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		//label per indicare all'utente cosa deve inserire
		JLabel lblNewLabel_1 = new JLabel("ID Vaccinazione: ");
		
		JLabel lblNewLabel_2 = new JLabel("Nome: ");
		
		JLabel lblNewLabel_3 = new JLabel("Cognome: ");
		
		JLabel lblNewLabel_4 = new JLabel("Codice fiscale: ");
		
		JLabel lblNewLabel_5 = new JLabel("E-Mail:");
		
		JLabel lblNewLabel_6 = new JLabel("USERNAME: ");
		
		JLabel lblNewLabel_7 = new JLabel("PASSWORD: ");
		
		//field per prelevare id vaccinazione inserito
		idVaccinazioneField = new JTextField();
		idVaccinazioneField.setColumns(10);
		idVaccinazioneField.setBorder(new RoundedBorder(1));
		
		//field per prelevare nome inserito
		nomeField = new JTextField();
		nomeField.setColumns(10);
		nomeField.setBorder(new RoundedBorder(1));
		
		//field per prelevare cognome inserito
		cognomeField = new JTextField();
		cognomeField.setColumns(10);
		cognomeField.setBorder(new RoundedBorder(1));
		
		//field per prelevare codice fiscale inserito
		codFiscaleField = new JTextField();
		codFiscaleField.setColumns(10);
		codFiscaleField.setBorder(new RoundedBorder(1));
		
		//field per prelevare mail inserita
		mailField = new JTextField();
		mailField.setColumns(10);
		mailField.setBorder(new RoundedBorder(1));
		
		//field per prelevare username inserito
		usernameField = new JTextField();
		usernameField.setColumns(10);
		usernameField.setBorder(new RoundedBorder(1));
		
		//field per prelevare password inserita
		passwordField = new JPasswordField();
		passwordField.setBorder(new RoundedBorder(1));
		
		//button per registrare l'utente nel db
		registerButton = new JButton("REGISTRATI");
		registerButton.setFont(new Font("Gadugi", Font.BOLD, 12));
		registerButton.setBackground(new Color(240, 230, 140));
		registerButton.addActionListener(this);
		registerButton.setBorder(new RoundedBorder(10));
		
		//label per visualizzare se la registrazione è andata a buon fine
		resultLabel = new JLabel("result");
		resultLabel.setVisible(false);
		
		//label per tornare indietro
		backLabel = new JLabel("Torna indietro");
		backLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		backLabel.addMouseListener(this);
		
		GroupLayout groupLayout = new GroupLayout(frmSmartvaxRegistrati.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 591, GroupLayout.PREFERRED_SIZE)
					.addGap(88))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(164)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1)
						.addComponent(lblNewLabel_6)
						.addComponent(lblNewLabel_2)
						.addComponent(lblNewLabel_3)
						.addComponent(lblNewLabel_4)
						.addComponent(lblNewLabel_5)
						.addComponent(lblNewLabel_7))
					.addGap(28)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
						.addComponent(usernameField, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
						.addComponent(cognomeField, 224, 224, 224)
						.addComponent(codFiscaleField, 224, 224, 224)
						.addComponent(nomeField, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
						.addComponent(mailField, 224, 224, 224)
						.addComponent(idVaccinazioneField, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE))
					.addContainerGap(191, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(567, Short.MAX_VALUE)
					.addComponent(backLabel, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
					.addGap(25))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(211)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(resultLabel, GroupLayout.PREFERRED_SIZE, 341, GroupLayout.PREFERRED_SIZE)
						.addComponent(registerButton, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(137, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(27)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(70)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(idVaccinazioneField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(nomeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_3)
						.addComponent(cognomeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_4)
						.addComponent(codFiscaleField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_5)
						.addComponent(mailField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_6)
						.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_7))
					.addGap(40)
					.addComponent(registerButton, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(resultLabel)
					.addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
					.addComponent(backLabel)
					.addContainerGap())
		);
		frmSmartvaxRegistrati.getContentPane().setLayout(groupLayout);
		frmSmartvaxRegistrati.setBounds(500, 250, 700, 600);
		frmSmartvaxRegistrati.setLocationRelativeTo(null);
		frmSmartvaxRegistrati.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSmartvaxRegistrati.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == registerButton) {
			//INSERIRE QUERY DI CONTROLLO, SE LE QUERY DANNO ESITO POSITIVO ALLORA ESEGUIRE:
			resultLabel.setText("La registrazione è andata a buon fine");
			resultLabel.setForeground(Color.blue);
			resultLabel.setVisible(true);
			
			//ALTRIMENTI ESEGUIRE:
			/*resultLabel.setText("Id vaccinazione già presente o codice fiscale invalido");
			resultLabel.setForeground(Color.red);
			resultLabel.setVisible(true);*/
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == backLabel) {
			frmSmartvaxRegistrati.dispose();
			HomeCittadino_Frame myFrame = new HomeCittadino_Frame();
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
		if(e.getSource() == backLabel) {
			backLabel.setBackground(Color.white);
			backLabel.setForeground(Color.blue);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() == backLabel) {
			backLabel.setBackground(Color.white);
			backLabel.setForeground(Color.black);
		}	
	}
}
