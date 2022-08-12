package graphics;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;

import centrivaccinaliClient.CentroVaccinaleServiceStubOperatore;
import common.CentroVaccinaleNonEsistente;

import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;

public class AddVaccinato_Frame implements ActionListener, MouseListener {

	private JFrame frmSmartvaxAddVaccinazione;
	private JTextField nomeCentroField;
	private JTextField codFiscaleField;
	private JTextField cognomeField;
	private JTextField nomeField;
	private JComboBox nomeVaccinoBox;
	private JButton addButton;
	private JDateChooser dataVaccinoChooser;
	private JLabel resultLabel;
	private JLabel backLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddVaccinato_Frame window = new AddVaccinato_Frame();
					window.frmSmartvaxAddVaccinazione.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddVaccinato_Frame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSmartvaxAddVaccinazione = new JFrame();
		frmSmartvaxAddVaccinazione.getContentPane().setBackground(new Color(175, 238, 238));

		JLabel lblNewLabel = new JLabel(
				"Inserisci dati richiesti per registrare la vaccinazione effettuata al cittadino ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNewLabel_1 = new JLabel("Nome: ");

		JLabel lblNewLabel_2 = new JLabel("Cognome: ");

		JLabel lblNewLabel_3 = new JLabel("Codice fiscale: ");

		JLabel lblNewLabel_4 = new JLabel("Nome vaccino: ");

		JLabel lblNewLabel_5 = new JLabel("Data vaccino: ");

		JLabel lblNewLabel_6 = new JLabel("Nome centro vaccinale: ");

		// field per prelevare nome del centro vaccinale in cui si è tenuta la
		// vaccinazione
		nomeCentroField = new JTextField();
		nomeCentroField.setColumns(10);
		nomeCentroField.setBorder(new RoundedBorder(3));

		// field per prelevare il codice fiscale del vaccinato
		codFiscaleField = new JTextField();
		codFiscaleField.setColumns(10);
		codFiscaleField.setBorder(new RoundedBorder(3));

		// field per prelevare il cognome del vaccinato
		cognomeField = new JTextField();
		cognomeField.setColumns(10);
		cognomeField.setBorder(new RoundedBorder(3));

		// field per prelevare il nome del vaccinato
		nomeField = new JTextField();
		nomeField.setColumns(10);
		nomeField.setBorder(new RoundedBorder(3));

		// box per prelevare il nome del vaccinato utilizzato per fare la vaccinazione
		nomeVaccinoBox = new JComboBox();
		nomeVaccinoBox.setModel(new DefaultComboBoxModel(new String[] { "Pfizer", "Moderna", "Astrazeneca", "J&J" }));

		// chooser per prelevare la data della vaccinazione nel formato utilizzato dal
		// db
		dataVaccinoChooser = new JDateChooser();
		dataVaccinoChooser.setDateFormatString("yyyy-MM-dd");

		// button per aggiungere la vaccinazione nel db
		addButton = new JButton("AGGIUNGI DATI VACCINAZIONE");
		addButton.setBackground(new Color(135, 206, 250));
		addButton.setFont(new Font("Gadugi", Font.BOLD, 12));
		addButton.addActionListener(this);
		addButton.setBorder(new RoundedBorder(3));

		// label per tornare al frame precedente
		backLabel = new JLabel("Torna indietro");
		backLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		backLabel.addMouseListener(this);

		// label per tornare indietro
		resultLabel = new JLabel("verifica inserimento centro");
		resultLabel.setVisible(false);

		GroupLayout groupLayout = new GroupLayout(frmSmartvaxAddVaccinazione.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
				.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addGap(43).addComponent(lblNewLabel,
								GroupLayout.PREFERRED_SIZE, 601, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup().addGap(75).addGroup(groupLayout
								.createParallelGroup(Alignment.TRAILING).addComponent(lblNewLabel_5)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblNewLabel_4)
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
												.addComponent(lblNewLabel_2).addComponent(lblNewLabel_3)
												.addComponent(lblNewLabel_1)))
								.addComponent(lblNewLabel_6)).addGap(28)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(nomeField).addComponent(cognomeField)
										.addComponent(codFiscaleField)
										.addComponent(dataVaccinoChooser, GroupLayout.PREFERRED_SIZE, 118,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(nomeCentroField, GroupLayout.PREFERRED_SIZE, 187,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(nomeVaccinoBox, GroupLayout.PREFERRED_SIZE, 155,
												GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup().addGap(228).addComponent(addButton,
								GroupLayout.PREFERRED_SIZE, 266, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(40, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup().addContainerGap(199, Short.MAX_VALUE)
						.addComponent(resultLabel, GroupLayout.PREFERRED_SIZE, 329, GroupLayout.PREFERRED_SIZE)
						.addGap(48).addComponent(backLabel).addGap(21)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(31).addComponent(lblNewLabel)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addGap(65)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNewLabel_1).addComponent(nomeField, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(33)
								.addGroup(
										groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_2)
												.addComponent(cognomeField, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(35)
								.addGroup(
										groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_3)
												.addComponent(codFiscaleField, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(34)
								.addGroup(
										groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_4)
												.addComponent(nomeVaccinoBox, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(31)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblNewLabel_5)
										.addComponent(dataVaccinoChooser, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(33)
								.addGroup(
										groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_6)
												.addComponent(nomeCentroField, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(47)
								.addComponent(addButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
								.addComponent(resultLabel).addGap(38))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(backLabel).addGap(24)))));
		frmSmartvaxAddVaccinazione.getContentPane().setLayout(groupLayout);
		frmSmartvaxAddVaccinazione.setResizable(false);
		frmSmartvaxAddVaccinazione.setTitle("SmartVax - Aggiungi vaccinazione");
		frmSmartvaxAddVaccinazione.setBounds(500, 250, 700, 600);
		frmSmartvaxAddVaccinazione.setLocationRelativeTo(null);
		frmSmartvaxAddVaccinazione.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSmartvaxAddVaccinazione.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == backLabel) {
			frmSmartvaxAddVaccinazione.dispose();
			HomeOperatore_Frame myFrame = new HomeOperatore_Frame();
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
		if (e.getSource() == backLabel) {
			backLabel.setBackground(Color.white);
			backLabel.setForeground(Color.blue);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == backLabel) {
			backLabel.setBackground(Color.white);
			backLabel.setForeground(Color.black);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addButton) {

			// QUERY DI CONTROLLO SE IL CENTRO VACCINALE è PRESENTE ALL'INTERNO DEL DB, SE
			// PRESENTE ALLORA ESEGUE:
			resultLabel.setText("");

			try {

				Date date = new Date(dataVaccinoChooser.getDate().getTime());

				CentroVaccinaleServiceStubOperatore c = new CentroVaccinaleServiceStubOperatore();
				c.registraVaccinato(nomeField.getText(), cognomeField.getText(), nomeCentroField.getText(),
						codFiscaleField.getText(), nomeVaccinoBox.getSelectedItem().toString(), date);

			} catch (IOException | SQLException e1) {
				resultLabel.setText("Error");
				resultLabel.setForeground(Color.red);
				resultLabel.setVisible(true);
				e1.printStackTrace();
			} catch (CentroVaccinaleNonEsistente e1) {
				resultLabel.setText(e1.getMessage());
				resultLabel.setForeground(Color.red);
				resultLabel.setVisible(true);
				e1.printStackTrace();
			}

			finally {

				if (resultLabel.getText().isBlank()) {
					resultLabel.setText("Inserimento della vaccinazione andato a buon fine");
					resultLabel.setForeground(Color.blue);
					resultLabel.setVisible(true);
				}
			}

			// ALTRIMENTI ESEGUE:
			/*
			 * resultLabel.
			 * setText("Il centro vaccinale inserito non è presente nel database");
			 * resultLabel.setForeground(Color.red); resultLabel.setVisible(true);
			 */
		}
	}
}
