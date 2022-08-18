package graphics;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;

import cittadini.CentroVaccinaleServiceStubCittadino;
import common.CentriVaccinaliNonEsistenti;
import common.CentroVaccinaleNonEsistente;
import common.CittadinoNonVaccinatoNelCentro;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

public class FreeArea_Frame implements ActionListener, MouseListener {

	private JFrame frmCercaCentroVaccinale;
	private JTextField nomeCentroField;
	private JTextField comuneField;
	private JLabel back;
	private JTextArea visualizzaArea;
	private JComboBox tipologiaBox;
	private JLabel label;
	private JButton search1Button;
	private JComboBox centriBox;
	private JButton ricercaButton;
	private JButton search2Button;
	private boolean access;

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
		frmCercaCentroVaccinale.setBounds(600, 250, 800, 600);
		frmCercaCentroVaccinale.setLocationRelativeTo(null);
		frmCercaCentroVaccinale.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		label = new JLabel(
				"<html>Inserisci informazioni richieste per visualizzare un centro vaccinale e le statistiche relative agli eventi avversi riscontrati in quel dato centro");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.BOLD, 12));

		//all'interno della JTextArea saranno visualizzati i dati relativi al centro vaccinale cercato
		visualizzaArea = new JTextArea();
		visualizzaArea.setEditable(false);
		visualizzaArea.setBorder(new RoundedBorder(3));

		JLabel lblNewLabel_1 = new JLabel("Nome centro: ");

		JLabel lblNewLabel_2 = new JLabel("Comune: ");

		// valore nome per cercare il centro vaccinale
		nomeCentroField = new JTextField();
		nomeCentroField.setColumns(10);
		nomeCentroField.setBorder(new RoundedBorder(2));

		// valore comune per cercare il centro vaccinale
		comuneField = new JTextField();
		comuneField.setColumns(10);
		comuneField.setBorder(new RoundedBorder(2));

		JLabel lblNewLabel_3 = new JLabel("Tipologia: ");

		// valore tipologia per cercare il centro vaccinale
		tipologiaBox = new JComboBox();
		tipologiaBox.setModel(new DefaultComboBoxModel(new String[] { "Aziendale", "Ospedaliero", "Hub" }));
		tipologiaBox.setSelectedIndex(0);

		// button per cercare il centro tramite nome
		search1Button = new JButton("CERCA CENTRO");
		search1Button.setFont(new Font("Gadugi", Font.BOLD, 12));
		search1Button.setBackground(new Color(173, 216, 230));
		search1Button.setBorder(new RoundedBorder(6));
		search1Button.addActionListener(this);

		// button per cercare i centri tramite comune e tipologia
		ricercaButton = new JButton("AVVIA RICERCA");
		ricercaButton.setBackground(new Color(173, 216, 230));
		ricercaButton.setFont(new Font("Gadugi", Font.BOLD, 12));
		ricercaButton.setBorder(new RoundedBorder(6));
		ricercaButton.addActionListener(this);

		// label per tornare indietro
		back = new JLabel("Torna indietro");
		back.setFont(new Font("Tahoma", Font.BOLD, 12));
		back.addMouseListener(this);

		JLabel lblNewLabel = new JLabel("Dopo aver inserito comune e tipologia, scegliere centro dal menu: ");

		// box in cui verranno inseriti dinamicamente i nomi dei centri con comune e
		// tipologia inseriti
		centriBox = new JComboBox();

		// button per effettuare la ricerca per la visualizzazione dei dati di un
		// determinato centro
		search2Button = new JButton("CERCA CENTRO");
		search2Button.setBackground(new Color(173, 216, 230));
		search2Button.setFont(new Font("Gadugi", Font.BOLD, 12));
		search2Button.setBorder(new RoundedBorder(6));
		search2Button.addActionListener(this);

		GroupLayout groupLayout = new GroupLayout(frmCercaCentroVaccinale.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap(656, Short.MAX_VALUE).addComponent(back)
						.addGap(41))
				.addGroup(groupLayout.createSequentialGroup().addGap(43).addGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addComponent(lblNewLabel).addGap(27)
								.addComponent(centriBox, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
								.addGap(18).addComponent(search2Button))
						.addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout
								.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addComponent(lblNewLabel_1).addGap(43)
										.addComponent(nomeCentroField, GroupLayout.PREFERRED_SIZE, 185,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup().addComponent(lblNewLabel_2).addGap(27)
										.addComponent(comuneField, GroupLayout.PREFERRED_SIZE, 133,
												GroupLayout.PREFERRED_SIZE)
										.addGap(27).addComponent(lblNewLabel_3)))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup().addGap(13)
												.addComponent(tipologiaBox, GroupLayout.PREFERRED_SIZE, 158,
														GroupLayout.PREFERRED_SIZE)
												.addGap(32).addComponent(ricercaButton, GroupLayout.PREFERRED_SIZE, 111,
														GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup().addGap(39)
												.addComponent(search1Button)))))
						.addContainerGap(128, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup().addGap(35)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 604, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(145, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup().addGap(61)
						.addComponent(visualizzaArea, GroupLayout.PREFERRED_SIZE, 655, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(68, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(21).addComponent(label)
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
						.createSequentialGroup().addGap(32)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_1)
								.addComponent(nomeCentroField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(search1Button))
						.addGap(26)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_2)
								.addComponent(comuneField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_3).addComponent(tipologiaBox, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel)
								.addComponent(centriBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(search2Button))
						.addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(ricercaButton).addGap(60)))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(visualizzaArea, GroupLayout.PREFERRED_SIZE, 292, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(back).addContainerGap()));
		frmCercaCentroVaccinale.getContentPane().setLayout(groupLayout);
		frmCercaCentroVaccinale.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == back && access == false) {
			frmCercaCentroVaccinale.dispose();
			HomeCittadino_Frame myFrame = new HomeCittadino_Frame();
		}

		if (e.getSource() == back && access == true) {
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
		if (e.getSource() == back) {
			back.setBackground(Color.white);
			back.setForeground(Color.blue);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == back) {
			back.setBackground(Color.white);
			back.setForeground(Color.black);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		LinkedList<String[]> centriLinkedList;
		CentroVaccinaleServiceStubCittadino c; //oggetto centrovaccinaleservicestubcittadino dichiarato 
		LinkedList<String> nomiCentri = new LinkedList<String>();
		
		// button premuto per cercare centro tramite nome
		if (e.getSource() == search1Button) {
			
			try {
				visualizzaArea.setText("");
				visualizzaArea.setForeground(Color.BLACK);
				c = new CentroVaccinaleServiceStubCittadino(); //istanziato oggetto centrovaccinaleservicestubcittadino
				visualizzaArea //chiamo metodo per visualizzare dati del centro e lo inserisco direttamente come testo della JTextArea
						.setText(c.VisualizzaCentro(false, null, nomeCentroField.getText(), null, null, -1, null)); 
			} catch (IOException | SQLException | CentroVaccinaleNonEsistente | CentriVaccinaliNonEsistenti
					| CittadinoNonVaccinatoNelCentro e1) { //varie eccezioni sollevate
				visualizzaArea.setText(e1.getMessage());
				visualizzaArea.setForeground(Color.RED);
				e1.printStackTrace();
			}

		}

		// button premuto per cercare centri da aggiungere dinamicamente al menù a tendina
		if (e.getSource() == ricercaButton) {

			try {
				c = new CentroVaccinaleServiceStubCittadino(); //istanziato oggetto centrovaccinaleservicestubcittadino
				centriLinkedList = c.RicercaCentroComuneTipologia(comuneField.getText(),
						tipologiaBox.getSelectedItem().toString()); //il metodo per cercare un centro tramite comune e tipologia ritorna una linkedlist

				for (String[] s : centriLinkedList)
					nomiCentri.add(s[0]); //scorro la lista e inserisco i nomi dei centri trovati nella linkedlist nomeCentri

				centriBox.setModel(new DefaultComboBoxModel(nomiCentri.toArray(new String[0]))); //inserisco nella JComboBox i nomi del vettore convertito

			} catch (IOException | SQLException e1) {
				e1.printStackTrace();
			}

		}

		// button premuto per visualizzare informazioni di un centro selezionato dal
		// menù a tendina
		if (e.getSource() == search2Button) {
			try {
				visualizzaArea.setText("");
				visualizzaArea.setForeground(Color.BLACK);
				c = new CentroVaccinaleServiceStubCittadino(); //istanziato oggetto centrovaccinaleservicestubcittadino
				visualizzaArea.setText( //chiamo metodo per cercare dati del centro selezionato nella JComboBox
						c.VisualizzaCentro(false, null, centriBox.getSelectedItem().toString(), null, null, -1, null));
			} catch (IOException | SQLException | CentroVaccinaleNonEsistente | CentriVaccinaliNonEsistenti
					| CittadinoNonVaccinatoNelCentro e1) { //varie eccezioni sollevate
				visualizzaArea.setText(e1.getMessage());
				visualizzaArea.setForeground(Color.RED);
				e1.printStackTrace();
			}
		}
	}
}
