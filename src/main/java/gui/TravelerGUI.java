package gui;

import javax.swing.*;
import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.ResourceBundle;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TravelerGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonBookRides = null;
	private JButton jButtonAlertakKudeatu;
	private JButton jButtonDiruaKudeatu = null;
	private JButton jButtonEgoera = null;
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	protected JLabel jLabelSelectOption;
	private JButton bellButton = null;

	private static BLFacade appFacadeInterface;

	public static BLFacade getBusinessLogic() {
		return appFacadeInterface;
	}

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	public TravelerGUI(String username) {

		TravelerGUI.setBussinessLogic(LoginGUI.getBusinessLogic());

		this.setSize(600, 400);
		jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.Traveler"));
		jLabelSelectOption.setBounds(179, 44, 240, 36);
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);

		jButtonBookRides = new JButton();
		jButtonBookRides.setBounds(39, 123, 240, 50);
		jButtonBookRides.setText(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.BookRides"));
		jButtonBookRides.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new BookGUI(username);
				a.setVisible(true);
			}
		});

		jButtonAlertakKudeatu = new JButton();
		jButtonAlertakKudeatu.setBounds(319, 123, 240, 50);
		jButtonAlertakKudeatu.setText(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.ManageAlerts"));
		jButtonAlertakKudeatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new AlertakKudeatuGUI(username);
				a.setVisible(true);
			}
		});

		jButtonDiruaKudeatu = new JButton();
		jButtonDiruaKudeatu.setBounds(39, 203, 240, 50);
		jButtonDiruaKudeatu.setText(ResourceBundle.getBundle("Etiquetas").getString("UserGUI.ManageMoney"));
		jButtonDiruaKudeatu.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new MoneyGUI(username);
				a.setVisible(true);
			}
		});

		jButtonEgoera = new JButton();
		jButtonEgoera.setBounds(319, 203, 240, 50);
		jButtonEgoera.setText(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.BookManager"));
		jButtonEgoera.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new EgoeraGUI(username);
				a.setVisible(true);
			}
		});

		jButtonClose.setBounds(249, 292, 100, 30);
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		bellButton = new JButton();
		ImageIcon bellIcon = new ImageIcon("irudia/bell_icon.png");
		Image img = bellIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		bellButton.setIcon(new ImageIcon(img));
		bellButton.setBounds(504, 0, 80, 80);
		bellButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame a = new AlertaAurkituakGUI(username);
				a.setVisible(true);
			}
		});
		bellButton.setEnabled(false);
		bellButton.setVisible(false);

		boolean erakutsiBotoia = false;
		erakutsiBotoia = appFacadeInterface.updateAlertaAurkituak(username);
		if (erakutsiBotoia) {
			bellButton.setEnabled(true);
			bellButton.setVisible(true);
		}

		jContentPane = new JPanel();
		jContentPane.setLayout(null);
		jContentPane.add(jLabelSelectOption);
		jContentPane.add(jButtonBookRides);
		jContentPane.add(jButtonAlertakKudeatu);
		jContentPane.add(jButtonDiruaKudeatu);
		jContentPane.add(jButtonEgoera);
		jContentPane.add(jButtonClose);
		jContentPane.add(bellButton);
		setContentPane(jContentPane);

		setTitle(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Traveler"));
		setResizable(false);

	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
} // @jve:decl-index=0:visual-constraint="0,0"
