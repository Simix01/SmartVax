import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.JButton;

public class AddCentro_Frame implements ActionListener, MouseListener, FocusListener{

	private JFrame frmSmartvaxAggiungi;
	private JTextField nomeCentroField;
	private JComboBox tipoLuogoBox;
	private JTextField nomeLuogoField;
	private JTextField siglaProvField;
	private JTextField numCivicoField;
	private JTextField comuneField;
	private JTextField capField;
	private JLabel lblNewLabel_2;
	private JComboBox tipologiaBox;
	private JButton addButton;
	private JLabel backLabel;
	private JLabel resultLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddCentro_Frame window = new AddCentro_Frame();
					window.frmSmartvaxAggiungi.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddCentro_Frame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSmartvaxAggiungi = new JFrame();
		frmSmartvaxAggiungi.getContentPane().setBackground(new Color(175, 238, 238));
		frmSmartvaxAggiungi.setResizable(false);
		frmSmartvaxAggiungi.setTitle("SmartVax - Aggiungi centro vaccinale");
		frmSmartvaxAggiungi.setBounds(500, 250, 700, 600);
		frmSmartvaxAggiungi.setLocationRelativeTo(null);
		frmSmartvaxAggiungi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSmartvaxAggiungi.setVisible(true);
		
		JLabel lblNewLabel = new JLabel("Nome centro: ");
		
		JLabel lblNewLabel_1 = new JLabel("Ubicazione: ");
		
		//field da cui prendere il nome del centro vaccinale, chiave primaria
		nomeCentroField = new JTextField();
		nomeCentroField.setColumns(10);
		nomeCentroField.setBorder(new RoundedBorder(3));
		
		//box da cui prendere il valore della tipologia di luogo tra via, viale e piazza
		tipoLuogoBox = new JComboBox();
		tipoLuogoBox.setModel(new DefaultComboBoxModel(new String[] {"Via", "Viale", "Piazza"}));
		
		//field da cui prendere il valore del nome del luogo
		nomeLuogoField = new JTextField();
		nomeLuogoField.setColumns(10);
		nomeLuogoField.setText("Nome via/viale/piazza");
		nomeLuogoField.setForeground(Color.gray);
		nomeLuogoField.addFocusListener(this);
		nomeLuogoField.setBorder(new RoundedBorder(3));
		
		//field da cui prendere la sigla della provincia di residenza
		siglaProvField = new JTextField();
		siglaProvField.setColumns(10);
		siglaProvField.setText("Sigla provincia");
		siglaProvField.setForeground(Color.gray);
		siglaProvField.addFocusListener(this);
		siglaProvField.setBorder(new RoundedBorder(3));
		
		//field da cui prendere il numero civico del luogo indicato
		numCivicoField = new JTextField();
		numCivicoField.setColumns(10);
		numCivicoField.setText("Numero civico");
		numCivicoField.setForeground(Color.gray);
		numCivicoField.addFocusListener(this);
		numCivicoField.setBorder(new RoundedBorder(3));
		
		//field da cui prendere il comune di appartenenza del centro vaccinale
		comuneField = new JTextField();
		comuneField.setColumns(10);
		comuneField.setText("Comune di appartenenza del centro");
		comuneField.setForeground(Color.gray);
		comuneField.addFocusListener(this);
		comuneField.setBorder(new RoundedBorder(3));
		
		//field da cui prendere il cap della provincia di appartenenza del centro vaccinale
		capField = new JTextField();
		capField.setColumns(10);
		capField.setText("CAP ubicazione");
		capField.setForeground(Color.gray);
		capField.addFocusListener(this);
		capField.setBorder(new RoundedBorder(3));
		
		lblNewLabel_2 = new JLabel("Tipologia: ");
		
		//box da cui prendere la tipologia del centro vaccinale tra aziendale, ospedaliero e hub
		tipologiaBox = new JComboBox();
		tipologiaBox.setModel(new DefaultComboBoxModel(new String[] {"Aziendale", "Ospedaliero", "Hub"}));
		
		//label per tornare alla pagina precedente
		backLabel = new JLabel("Torna indietro");
		backLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		backLabel.addMouseListener(this);
		
		//label invisibile che diventa visibile e fa vedere il result positivo o negativo dell'inserimento nel database
		resultLabel = new JLabel("result");
		resultLabel.setVisible(false);
		
		addButton = new JButton("AGGIUNGI CENTRO VACCINALE");
		addButton.setBackground(new Color(135, 206, 250));
		addButton.setFont(new Font("Gadugi", Font.BOLD, 12));
		addButton.addActionListener(this);
		addButton.setBorder(new RoundedBorder(10));
		
		
		GroupLayout groupLayout = new GroupLayout(frmSmartvaxAggiungi.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(109)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel)
						.addComponent(lblNewLabel_1)
						.addComponent(lblNewLabel_2))
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(tipologiaBox, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
							.addGap(330))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(addButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(capField, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(nomeLuogoField, Alignment.LEADING)
								.addComponent(nomeCentroField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
								.addComponent(tipoLuogoBox, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
								.addComponent(comuneField, Alignment.LEADING)
								.addGroup(Alignment.LEADING, groupLayout.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(numCivicoField, Alignment.LEADING)
									.addComponent(siglaProvField, Alignment.LEADING)))
							.addContainerGap(223, Short.MAX_VALUE))))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(156)
					.addComponent(resultLabel, GroupLayout.PREFERRED_SIZE, 371, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(157, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(568, Short.MAX_VALUE)
					.addComponent(backLabel)
					.addGap(29))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(40)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(nomeCentroField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(44)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(tipoLuogoBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(nomeLuogoField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(siglaProvField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(numCivicoField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(comuneField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(capField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(40)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(tipologiaBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(37)
					.addComponent(addButton, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addGap(27)
					.addComponent(resultLabel)
					.addPreferredGap(ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
					.addComponent(backLabel)
					.addGap(19))
		);
		frmSmartvaxAggiungi.getContentPane().setLayout(groupLayout);
	}

	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource() == nomeLuogoField && nomeLuogoField.getText().equals("Nome via/viale/piazza")) {
			nomeLuogoField.setText("");
			nomeLuogoField.setForeground(Color.black);
		}
		
		if(e.getSource() == siglaProvField && siglaProvField.getText().equals("Sigla provincia")) {
			siglaProvField.setText("");
			siglaProvField.setForeground(Color.black);
		}
		
		if(e.getSource() == numCivicoField && numCivicoField.getText().equals("Numero civico")) {
			numCivicoField.setText("");
			numCivicoField.setForeground(Color.black);
		}
		
		if(e.getSource() == comuneField && comuneField.getText().equals("Comune di appartenenza del centro")) {
			comuneField.setText("");
			comuneField.setForeground(Color.black);
		}
		
		if(e.getSource() == capField && capField.getText().equals("CAP ubicazione")) {
			capField.setText("");
			capField.setForeground(Color.black);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if(e.getSource() == nomeLuogoField && nomeLuogoField.getText().equals("")) {
			nomeLuogoField.setText("Nome via/viale/piazza");
			nomeLuogoField.setForeground(Color.gray);
		}
		
		if(e.getSource() == siglaProvField && siglaProvField.getText().equals("")) {
			siglaProvField.setText("Sigla provincia");
			siglaProvField.setForeground(Color.gray);
		}
		
		if(e.getSource() == numCivicoField && numCivicoField.getText().equals("")) {
			numCivicoField.setText("Numero civico");
			numCivicoField.setForeground(Color.gray);
		}
		
		if(e.getSource() == comuneField && comuneField.getText().equals("")) {
			comuneField.setText("Comune di appartenenza del centro");
			comuneField.setForeground(Color.gray);
		}
		
		if(e.getSource() == capField && capField.getText().equals("")) {
			capField.setText("CAP ubicazione");
			capField.setForeground(Color.gray);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource() == backLabel) {
			frmSmartvaxAggiungi.dispose();
			HomeOperatore_Frame myFrame = new HomeOperatore_Frame();
		}
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addButton) {
			//QUANDO VIENE PREMUTO IL PULSANTE VIENE CREATA UNA NUOVA TUPLA NEL DB CENTRI VACCINALI, CONTROLLARE CHE IL CENTRO INSERITO NON SIA GIA PRESENTE
			//SE IL CENTRO NON CE ALLORA ESEGUE QUESTO
			resultLabel.setText("I dati del centro vaccinale sono stati inseriti con successo");
			resultLabel.setForeground(Color.blue);
			resultLabel.setVisible(true);
			
			//ALTRIMENTI VIENE ESEGITO QUESTO
			/*resultLabel.setText("Errore nell'inserimento dei dati, il centro è già presente");
			resultLabel.setForeground(Color.red);
			resultLabel.setVisible(true);*/
		}
	}
}
