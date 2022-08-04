import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

public class FreeArea_Frame implements ActionListener, MouseListener{

	private JFrame frmCercaCentroVaccinale;
	private JTextField nomeCentroField;
	private JTextField comuneField;
	private JLabel back;
	private JTextArea visualizzaArea;
	private JComboBox tipologiaBox;
	private JLabel label;
	private JButton search1Button;
	private JButton search2Button;
	private boolean access;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FreeArea_Frame window = new FreeArea_Frame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public FreeArea_Frame(boolean access) {
		initialize(access);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(boolean access) {
		this.access = access;
		frmCercaCentroVaccinale = new JFrame();
		frmCercaCentroVaccinale.setTitle("SmartVax - Cerca centro vaccinale");
		frmCercaCentroVaccinale.setResizable(false);
		frmCercaCentroVaccinale.getContentPane().setBackground(new Color(175, 238, 238));
		frmCercaCentroVaccinale.setBounds(500, 250, 700, 600);
		frmCercaCentroVaccinale.setLocationRelativeTo(null);
		frmCercaCentroVaccinale.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		label = new JLabel("<html>Inserisci informazioni richieste per visualizzare un centro vaccinale e le statistiche relative agli eventi avversi riscontrati in quel dato centro");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		visualizzaArea = new JTextArea();
		visualizzaArea.setEditable(false);
		visualizzaArea.setBorder(new RoundedBorder(3));
		
		JLabel lblNewLabel_1 = new JLabel("Nome centro: ");
		
		JLabel lblNewLabel_2 = new JLabel("Comune: ");
		
		//valore nome per cercare il centro vaccinale
		nomeCentroField = new JTextField();
		nomeCentroField.setColumns(10);
		nomeCentroField.setBorder(new RoundedBorder(2));
		
		//valore comune per cercare il centro vaccinale
		comuneField = new JTextField();
		comuneField.setColumns(10);
		comuneField.setBorder(new RoundedBorder(2));
		
		JLabel lblNewLabel_3 = new JLabel("Tipologia: ");
		
		//valore tipologia per cercare il centro vaccinale
		tipologiaBox = new JComboBox();
		tipologiaBox.setModel(new DefaultComboBoxModel(new String[] {"Aziendale", "Ospedaliero", "Hub"}));
		tipologiaBox.setSelectedIndex(0);
		
		//button per cercare il centro tramite nome
		search1Button = new JButton("CERCA CENTRO");
		search1Button.setFont(new Font("Gadugi", Font.BOLD, 12));
		search1Button.setBackground(new Color(173, 216, 230));
		search1Button.setBorder(new RoundedBorder(10));
		
		//button per cercare il centro tramite comune e tipologia
		search2Button = new JButton("CERCA CENTRO");
		search2Button.setBackground(new Color(173, 216, 230));
		search2Button.setFont(new Font("Gadugi", Font.BOLD, 12));
		search2Button.setBorder(new RoundedBorder(8));

		//label per tornare indietro
		back = new JLabel("Torna indietro");
		back.setFont(new Font("Tahoma", Font.BOLD, 12));
		back.addMouseListener(this);
		
		GroupLayout groupLayout = new GroupLayout(frmCercaCentroVaccinale.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(visualizzaArea, GroupLayout.PREFERRED_SIZE, 655, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(19, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(556, Short.MAX_VALUE)
					.addComponent(back)
					.addGap(41))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(43)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_2)
							.addGap(26)
							.addComponent(comuneField, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_1)
							.addGap(26)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNewLabel_3)
								.addComponent(nomeCentroField, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(18)
									.addComponent(tipologiaBox, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(39)
									.addComponent(search1Button)))))
					.addGap(47)
					.addComponent(search2Button, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(28, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(35)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 604, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(45, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(21)
					.addComponent(label)
					.addGap(32)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(nomeCentroField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(search1Button))
					.addGap(33)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(comuneField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3)
						.addComponent(tipologiaBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(search2Button))
					.addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
					.addComponent(visualizzaArea, GroupLayout.PREFERRED_SIZE, 322, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(back)
					.addContainerGap())
		);
		frmCercaCentroVaccinale.getContentPane().setLayout(groupLayout);
		frmCercaCentroVaccinale.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == back && access == false) {
			frmCercaCentroVaccinale.dispose();
			HomeCittadino_Frame myFrame = new HomeCittadino_Frame();
		}
		
		if(e.getSource() == back && access == true) {
			frmCercaCentroVaccinale.dispose();
			LoggedIn_Frame myFrame = new LoggedIn_Frame();
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
		if(e.getSource() == back) {
			back.setBackground(Color.white);
			back.setForeground(Color.blue);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() == back) {
			back.setBackground(Color.white);
			back.setForeground(Color.black);
		}	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//AGGIUNGERE EVENT PER LA RICERCA DI UN CENTRO VACCINALE NEI DUE MODI
		//BUTTON CERCA CENTRO TRAMITE NOME --> search1Button
		//BUTTON CERCA CENTRO TRAMITE COMUNE E TIPOLOGIA --> search2Button
		
	}
}
