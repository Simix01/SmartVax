package graphics;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;

public class Homepage_Frame implements ActionListener{

	private JFrame frmSmartvax;
	JButton opButton;
	JButton citButton;
	private JLabel logoLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Homepage_Frame window = new Homepage_Frame();
					window.frmSmartvax.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Homepage_Frame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSmartvax = new JFrame();
		frmSmartvax.getContentPane().setFont(new Font("Impact", Font.PLAIN, 11));
		frmSmartvax.setTitle("SmartVax");
		frmSmartvax.setResizable(false);
		frmSmartvax.getContentPane().setBackground(new Color(175, 238, 238));
		frmSmartvax.setBounds(500, 250, 700, 600);
		frmSmartvax.setLocationRelativeTo(null);
		frmSmartvax.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSmartvax.setVisible(true);
		
		//creazione del button per gli operatori
		opButton = new JButton("OPERATORI");
		opButton.setFont(new Font("Gadugi", Font.BOLD, 18));
		opButton.setBackground(new Color(221, 160, 221));
		opButton.addActionListener(this);
		opButton.setBorder(new RoundedBorder(10));
		
		//creazione del button per i cittadini
		citButton = new JButton("CITTADINI");
		citButton.setFont(new Font("Gadugi", Font.BOLD, 18));
		citButton.setBackground(new Color(100, 149, 237));
		citButton.addActionListener(this);
		citButton.setBorder(new RoundedBorder(10));
		
		//label per inserire immagine logo
		logoLabel = new JLabel("");
		logoLabel.setSize(400,400);
		ImageIcon logoIcon = new ImageIcon(getClass().getClassLoader().getResource("SmartVaxLogo.png")); //new ImageIcon("icons\\SmartVaxLogo.png");
		Image logoImg = logoIcon.getImage();
		Image logoScaling = logoImg.getScaledInstance(logoLabel.getWidth(), logoLabel.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon logoScaled = new ImageIcon(logoScaling);
		logoLabel.setIcon(logoScaled);
		
		GroupLayout groupLayout = new GroupLayout(frmSmartvax.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(67)
							.addComponent(opButton, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE)
							.addGap(119)
							.addComponent(citButton, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(140)
							.addComponent(logoLabel, GroupLayout.PREFERRED_SIZE, 484, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(60, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(69)
					.addComponent(logoLabel, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
					.addGap(100)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(opButton, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
						.addComponent(citButton, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)))
		);
		frmSmartvax.getContentPane().setLayout(groupLayout);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == opButton) {
			frmSmartvax.dispose();
			HomeOperatore_Frame myWindow = new HomeOperatore_Frame();
		}
		
		if(e.getSource() == citButton) {
			frmSmartvax.dispose();
			HomeCittadino_Frame myWindow = new HomeCittadino_Frame();
		}
		
	}
}
