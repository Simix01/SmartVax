import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class HomeOperatore_Frame implements ActionListener, MouseListener{

	private JFrame frmHomeOperatore;
	private JButton nuovoCentroButton;
	private JButton nuovoVaccinatoButton;
	JLabel backToHome;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeOperatore_Frame window = new HomeOperatore_Frame();
					window.frmHomeOperatore.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HomeOperatore_Frame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHomeOperatore = new JFrame();
		frmHomeOperatore.setTitle("SmartVax - Home operatore");
		frmHomeOperatore.setResizable(false);
		frmHomeOperatore.getContentPane().setBackground(new Color(175, 238, 238));
		frmHomeOperatore.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		frmHomeOperatore.setBounds(500, 250, 700, 600);
		frmHomeOperatore.setLocationRelativeTo(null);
		frmHomeOperatore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHomeOperatore.setVisible(true);
		
		//aggiungo la label per tornare alla pagina precedente
		backToHome = new JLabel("torna alla homepage");
		backToHome.addMouseListener(this);
		backToHome.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblNewLabel = new JLabel("Per inserire un centro vaccinale nel database clicca qui: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		//button per accedere alla schermata di aggiunta nuovi centri vaccinali
		nuovoCentroButton = new JButton("AGGIUNGI CENTRO VACCINALE");
		nuovoCentroButton.setBackground(new Color(173, 216, 230));
		nuovoCentroButton.setFont(new Font("Gadugi", Font.BOLD, 12));
		nuovoCentroButton.addActionListener(this);
		nuovoCentroButton.setBorder(new RoundedBorder(10));
		
		JLabel lblNewLabel_1 = new JLabel("Per inserire dati relativi ad una nuova vaccinazione clicca qui: ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		//button per accedere alla schermata di aggiunta vaccinazioni
		nuovoVaccinatoButton = new JButton("AGGIUNGI DATI NUOVA VACCINAZIONE");
		nuovoVaccinatoButton.setBackground(new Color(173, 216, 230));
		nuovoVaccinatoButton.setFont(new Font("Gadugi", Font.BOLD, 12));
		nuovoVaccinatoButton.addActionListener(this);
		nuovoVaccinatoButton.setBorder(new RoundedBorder(10));
		
		GroupLayout groupLayout = new GroupLayout(frmHomeOperatore.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(231)
							.addComponent(nuovoCentroButton, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(169)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(lblNewLabel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(534)
							.addComponent(backToHome, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(10, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(215, Short.MAX_VALUE)
					.addComponent(nuovoVaccinatoButton, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)
					.addGap(205))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(112)
					.addComponent(lblNewLabel)
					.addGap(27)
					.addComponent(nuovoCentroButton, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
					.addGap(96)
					.addComponent(lblNewLabel_1)
					.addGap(29)
					.addComponent(nuovoVaccinatoButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addGap(153)
					.addComponent(backToHome, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addGap(9))
		);
		frmHomeOperatore.getContentPane().setLayout(groupLayout);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == backToHome) {
			frmHomeOperatore.dispose();
			Homepage_Frame myFrame = new Homepage_Frame();
		}	
	}
	
	//ACTION EVENTS PER I BUTTON
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == nuovoCentroButton) {
			frmHomeOperatore.dispose();
			AddCentro_Frame myFrame = new AddCentro_Frame();
		}
		
		if(e.getSource() == nuovoVaccinatoButton) {
			frmHomeOperatore.dispose();
			AddVaccinato_Frame myFrame = new AddVaccinato_Frame();
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
}
