package graphics;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultStyledDocument;

import cittadini.CentroVaccinaleServiceStubCittadino;
import common.CentriVaccinaliNonEsistenti;
import common.CentroVaccinaleNonEsistente;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;

public class InsertEvent_Frame implements ActionListener, MouseListener {

	private JFrame frmInserisciEventoAvverso;
	private JComboBox eventBox;
	private JSpinner gravitaField;
	private DefaultStyledDocument doc;
	private JTextArea commentArea;
	private JTextField centroField;
	private JLabel backLabel;
	private JButton confirmButton;
	private JLabel resultLabel;
	private boolean login;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public InsertEvent_Frame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmInserisciEventoAvverso = new JFrame();
		frmInserisciEventoAvverso.setTitle("SmartVax - Inserisci evento avverso");
		frmInserisciEventoAvverso.setResizable(false);
		frmInserisciEventoAvverso.getContentPane().setBackground(new Color(175, 238, 238));
		frmInserisciEventoAvverso.setBounds(500, 250, 700, 600);
		frmInserisciEventoAvverso.setLocationRelativeTo(null);
		frmInserisciEventoAvverso.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInserisciEventoAvverso.setVisible(true);

		JLabel lblNewLabel = new JLabel(
				"<html>In questa sezione è possibile inserire eventi avversi riscontrati post vaccinazione;<br>Questi eventi verranno catalogati e suddivisi per centro vaccinale.");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// campo per prelevare tipologia evento avverso
		eventBox = new JComboBox();
		eventBox.setModel(new DefaultComboBoxModel(new String[] { "Febbre", "Mal di testa", "Mal di gola",
				"Spossatezza", "Dolori intestinali", "Dolori muscolari", "Tosse", "Perdita del gusto o dell'olfatto",
				"Diarrea", "Difficoltà respiratoria" }));

		JLabel lblNewLabel_1 = new JLabel("Evento avverso: ");

		JLabel lblNewLabel_2 = new JLabel("Gravita'�: ");

		// campo per prelevare valore gravità
		gravitaField = new JSpinner();
		gravitaField.setModel(new SpinnerNumberModel(1, 1, 5, 1));

		JLabel lblNewLabel_3 = new JLabel("Commento (facoltativo)");

		// campo per prelevare commento facoltativo
		commentArea = new JTextArea();
		commentArea.setLineWrap(true);
		commentArea.setBorder(new RoundedBorder(3));

		// button per confermare e inserire l'evento avverso nel db
		confirmButton = new JButton("SEGNALA EVENTO");
		confirmButton.setBackground(new Color(135, 206, 235));
		confirmButton.setFont(new Font("Gadugi", Font.BOLD, 12));
		confirmButton.addActionListener(this);
		confirmButton.setBorder(new RoundedBorder(10));

		// label per tornare alla scheda precedente
		backLabel = new JLabel("Torna indietro");
		backLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		backLabel.addMouseListener(this);

		JLabel lblNewLabel_4 = new JLabel("Nome centro: ");

		// campo per prelevare nome del centro su cui fare una ricerca per verificare
		// che la persona si sia vaccinata nel centro vaccinale
		centroField = new JTextField();
		centroField.setColumns(10);
		centroField.setBorder(new RoundedBorder(3));

		resultLabel = new JLabel("result");
		resultLabel.setVisible(false);

		GroupLayout groupLayout = new GroupLayout(frmInserisciEventoAvverso.getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
						.createSequentialGroup().addGap(26).addGroup(
								groupLayout.createParallelGroup(Alignment.TRAILING).addComponent(backLabel)
										.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 627,
												GroupLayout.PREFERRED_SIZE))
						.addContainerGap(31, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup().addGap(84)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblNewLabel_1)
										.addComponent(lblNewLabel_2).addComponent(lblNewLabel_3)
										.addComponent(lblNewLabel_4))
								.addGap(26)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(centroField)
										.addComponent(gravitaField, GroupLayout.PREFERRED_SIZE, 43,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(eventBox, 0, 305, Short.MAX_VALUE).addComponent(commentArea))
								.addGap(156))
						.addGroup(groupLayout.createSequentialGroup().addGap(166)
								.addComponent(resultLabel, GroupLayout.PREFERRED_SIZE, 414, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(104, Short.MAX_VALUE))
						.addGroup(
								Alignment.TRAILING, groupLayout.createSequentialGroup()
										.addContainerGap(281, Short.MAX_VALUE).addComponent(confirmButton,
												GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)
										.addGap(217)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(22).addComponent(lblNewLabel)
				.addPreferredGap(ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_4).addComponent(
						centroField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(43)
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
						.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_2)
								.addComponent(gravitaField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(43))
						.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNewLabel_1).addComponent(eventBox, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(100)))
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_3)
						.addComponent(commentArea, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
				.addGap(37).addComponent(confirmButton, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
				.addGap(26).addComponent(resultLabel).addGap(28).addComponent(backLabel).addContainerGap()));
		frmInserisciEventoAvverso.getContentPane().setLayout(groupLayout);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == backLabel) {
			frmInserisciEventoAvverso.dispose();
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
		if (e.getSource() == confirmButton) {

			resultLabel.setText("");

			try {
				CentroVaccinaleServiceStubCittadino c = new CentroVaccinaleServiceStubCittadino();
				c.VisualizzaCentro(true, eventBox.getSelectedItem().toString(), centroField.getText(), null, null, (Integer) gravitaField.getValue(), commentArea.getText());
			} catch (IOException | SQLException | CentroVaccinaleNonEsistente | CentriVaccinaliNonEsistenti e1) {
				resultLabel.setText(e1.getMessage());
				resultLabel.setForeground(Color.red);
				resultLabel.setVisible(true);
				e1.printStackTrace();
			} finally {

				if (resultLabel.getText().isBlank()) {
					resultLabel.setText("L'inserimento e' andato a buon fine");
					resultLabel.setForeground(Color.BLACK);
					resultLabel.setVisible(true);
				}
			}

		}
	}
}
