package graphics;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.Font;

public class LoggedIn_Frame implements ActionListener{

	private JFrame frmLaTuaHome;
	private JLabel nameLabel;
	private JButton buttonSearch;
	private JButton eventButton;
	private JButton logoutButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoggedIn_Frame window = new LoggedIn_Frame();
					window.frmLaTuaHome.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoggedIn_Frame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLaTuaHome = new JFrame();
		frmLaTuaHome.setTitle("SmartVax - La tua home");
		frmLaTuaHome.setResizable(false);
		frmLaTuaHome.getContentPane().setBackground(new Color(175, 238, 238));
		frmLaTuaHome.setBounds(500, 250, 700, 600);
		frmLaTuaHome.setLocationRelativeTo(null);
		frmLaTuaHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLaTuaHome.setVisible(true);
		
		JLabel lblNewLabel = new JLabel("Benvenuto,");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		nameLabel = new JLabel("");
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		//SOSTITUIRE NOME E COGNOME CON LA QUERY PER TROVARE NOME E COGNOME DI CHI SI LOGGA
		String nome = "Simone";
		String cognome = "Bernaschina";
		nameLabel.setText(nome + " " + cognome);
		
		JLabel lblNewLabel_1 = new JLabel(", nella tua area personale, seleziona operazione da effettuare");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		//button per andare a cercare informazioni relative al centro vaccinale
		buttonSearch = new JButton("CERCA CENTRO VACCINALE");
		buttonSearch.setFont(new Font("Gadugi", Font.BOLD, 12));
		buttonSearch.setBackground(new Color(173, 216, 230));
		buttonSearch.addActionListener(this);
		buttonSearch.setBorder(new RoundedBorder(10));
		
		//button per andare ad inserire eventi avversi riscontrati
		eventButton = new JButton("INSERISCI EVENTO AVVERSO");
		eventButton.setFont(new Font("Gadugi", Font.BOLD, 12));
		eventButton.setBackground(new Color(218, 112, 214));
		eventButton.addActionListener(this);
		eventButton.setBorder(new RoundedBorder(10));
		
		//button per effettuare il logout
		logoutButton = new JButton("Effettua il Logout");
		logoutButton.setFont(new Font("Gadugi", Font.BOLD, 12));
		logoutButton.setBackground(new Color(240, 230, 140));
		logoutButton.addActionListener(this);
		logoutButton.setBorder(new RoundedBorder(10));
		
		GroupLayout groupLayout = new GroupLayout(frmLaTuaHome.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(87, Short.MAX_VALUE)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(nameLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_1)
					.addGap(61))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(201)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(logoutButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
							.addComponent(eventButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(buttonSearch, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)))
					.addContainerGap(209, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(29)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(nameLabel)
						.addComponent(lblNewLabel_1))
					.addGap(125)
					.addComponent(buttonSearch, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addGap(41)
					.addComponent(eventButton, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addGap(131)
					.addComponent(logoutButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(78, Short.MAX_VALUE))
		);
		frmLaTuaHome.getContentPane().setLayout(groupLayout);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == buttonSearch) {
			frmLaTuaHome.dispose();
			FreeArea_Frame myFrame = new FreeArea_Frame(true);
		}
		
		//L'utente effettua il logout tornando alla pagina home per i cittadini
		if(e.getSource() == logoutButton) {
			frmLaTuaHome.dispose();
			HomeCittadino_Frame myFrame = new HomeCittadino_Frame();
		}
		
		if(e.getSource() == eventButton) {
			frmLaTuaHome.dispose();
			InsertEvent_Frame myFrame = new InsertEvent_Frame();
		}
	}
}
