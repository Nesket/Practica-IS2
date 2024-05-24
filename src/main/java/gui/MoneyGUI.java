package gui;

import javax.swing.*;
import businessLogic.BLFacade;
import domain.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class MoneyGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextField fieldDiru = new JTextField();

	private JLabel jLabelOraingoa = new JLabel();
	private JLabel lblEmaitza = new JLabel();

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JButton jButtonGauzatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.Deposit"));
	private JButton jButtonMugimenduak = new JButton(
			ResourceBundle.getBundle("Etiquetas").getString("MovementsGUI.Movements"));

	private static BLFacade appFacadeInterface;

	public static BLFacade getBusinessLogic() {
		return appFacadeInterface;
	}

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	private boolean isNumber(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public MoneyGUI(String username) {

		MoneyGUI.setBussinessLogic(LoginGUI.getBusinessLogic());

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(400, 260));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.Money"));
		this.setResizable(false);

		double diruKop = appFacadeInterface.getActualMoney(username);

		jLabelOraingoa.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelOraingoa.setText(ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.Erabilgarri") + diruKop + "\u20AC");

		fieldDiru.setBounds(230, 52, 100, 30);
		jLabelOraingoa.setBounds(0, 21, 394, 20);
		jButtonClose.setBounds(230, 180, 100, 30);
		jButtonGauzatu.setBounds(69, 131, 261, 30);
		jButtonMugimenduak.setBounds(69, 180, 120, 30);

		this.getContentPane().add(jLabelOraingoa);
		this.getContentPane().add(fieldDiru);
		this.getContentPane().add(jButtonClose);
		this.getContentPane().add(jButtonGauzatu);
		this.getContentPane().add(jButtonMugimenduak);

		JComboBox<String> comboBoxEragiketa = new JComboBox<String>();
		comboBoxEragiketa.setBounds(69, 52, 120, 30);
		comboBoxEragiketa.addItem(ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.Deposit"));
		comboBoxEragiketa.addItem(ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.Withdraw"));
		getContentPane().add(comboBoxEragiketa);
		lblEmaitza.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmaitza.setBounds(0, 93, 394, 27);

		getContentPane().add(lblEmaitza);
		comboBoxEragiketa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedOption = (String) comboBoxEragiketa.getSelectedItem();
				jButtonGauzatu.setText(selectedOption);
			}
		});

		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		jButtonMugimenduak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new MovementsGUI(username);
				a.setVisible(true);
				jButtonClose_actionPerformed(e);
			}
		});

		jButtonGauzatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedOption = (String) comboBoxEragiketa.getSelectedItem();
				String diruText = fieldDiru.getText();
				boolean ondo;

				if (diruText.isEmpty() || !isNumber(diruText)) {
					lblEmaitza.setText(ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.Empty"));
					lblEmaitza.setForeground(Color.RED);
					return;
				}

				double kop = Double.parseDouble(diruText);
				User user = appFacadeInterface.getUser(username);
				
				if (selectedOption.equals(ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.Deposit"))) {
					ondo = appFacadeInterface.gauzatuEragiketa(username, kop, true);
					appFacadeInterface.addMovement(user, "Deposit", kop);
				} else {
					ondo = appFacadeInterface.gauzatuEragiketa(username, kop, false);
					appFacadeInterface.addMovement(user, "Withdrawal", kop);

				}

				if (ondo) {
					double diruBerria = appFacadeInterface.getActualMoney(username);
					jLabelOraingoa.setText(
							ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.Erabilgarri") + diruBerria + "\u20AC");
					lblEmaitza.setText(ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.Ondo"));
					lblEmaitza.setForeground(Color.BLACK);
				} else {
					lblEmaitza.setText(ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.Gaizki"));
					lblEmaitza.setForeground(Color.RED);
				}

				fieldDiru.setText("");

			}
		});
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}