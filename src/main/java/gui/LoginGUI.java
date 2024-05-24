package gui;

import javax.swing.*;
import businessLogic.BLFacade;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class LoginGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextField fieldLog = new JTextField();
	private JPasswordField fieldPass = new JPasswordField();

	private JLabel jLabelLog = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Username"));
	private JLabel jLabelPass = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Pass"));

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JButton jButtonLogIn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Login"));

	private static BLFacade appFacadeInterface;

	public static BLFacade getBusinessLogic(){
   		return appFacadeInterface;
   	}
	
	public static void setBusinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	public LoginGUI() {

		LoginGUI.setBusinessLogic(MainGUI.getBusinessLogic());

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(400, 250));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Login"));
		this.setResizable(false);

		jLabelLog.setBounds(39, 35, 100, 20);
		fieldLog.setBounds(149, 32, 150, 26);
		jLabelPass.setBounds(39, 86, 100, 20);
		fieldPass.setBounds(149, 83, 150, 26);
		jButtonClose.setBounds(228, 162, 100, 30);
		jButtonLogIn.setBounds(67, 162, 120, 30);

		this.getContentPane().add(jLabelLog);
		this.getContentPane().add(fieldLog);
		this.getContentPane().add(jLabelPass);
		this.getContentPane().add(fieldPass);
		this.getContentPane().add(jButtonClose);
		getContentPane().add(jButtonLogIn);

		JLabel lblErrorea = new JLabel();
		lblErrorea.setBounds(47, 130, 281, 21);
		getContentPane().add(lblErrorea);

		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		jButtonLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String erab = fieldLog.getText();
				String passwd = new String(fieldPass.getPassword());
				boolean sartu = appFacadeInterface.isRegistered(erab, passwd);
				if (sartu) {
					String mota = appFacadeInterface.getMotaByUsername(erab);
					if (mota.equals("Driver")) {
						JFrame a = new DriverGUI(erab);
						a.setVisible(true);
					} else if (mota.equals("Traveler")) {
						JFrame a = new TravelerGUI(erab);
						a.setVisible(true);
					} else {
						JFrame a = new AdminGUI(erab);
						a.setVisible(true);

					}
					jButtonClose_actionPerformed(e);
				} else {
					lblErrorea.setForeground(Color.RED);
					lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.ErrorNotRegistered"));
				}
			}
		});
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}