package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import businessLogic.BLFacade;
import domain.Discount;

import java.util.ResourceBundle;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DeskontuaGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField txtizena;
	private JTextField txtzenb;
	private JLabel lblDeskKodea;
	private JLabel lblDeskPortz;
	private JButton btnItxi;
	private JLabel lblErrore;

	private static BLFacade appFacadeInterface;

	public static void setBusinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	public DeskontuaGUI(String username) {

		DeskontuaGUI.setBusinessLogic(AdminGUI.getBusinessLogic());

		getContentPane().setLayout(null);
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("AdminGUI.Deskontua"));
		this.setSize(495, 290);

		lblDeskKodea = new JLabel();
		lblDeskKodea.setText(ResourceBundle.getBundle("Etiquetas").getString("DeskontuaGUI.Izena"));
		lblDeskKodea.setBounds(59, 29, 181, 13);
		getContentPane().add(lblDeskKodea);

		txtizena = new JTextField();
		txtizena.setBounds(250, 25, 96, 19);
		getContentPane().add(txtizena);
		txtizena.setColumns(10);

		lblDeskPortz = new JLabel("New label");
		lblDeskPortz.setText(ResourceBundle.getBundle("Etiquetas").getString("DeskontuaGUI.Kop"));
		lblDeskPortz.setBounds(59, 79, 169, 13);
		getContentPane().add(lblDeskPortz);

		txtzenb = new JTextField();
		txtzenb.setBounds(250, 75, 96, 19);
		getContentPane().add(txtzenb);
		txtzenb.setColumns(10);

		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtizena.getText() != null && txtzenb.getText() != null) {
					try {
						double desk = Double.parseDouble(txtzenb.getText());
						if (desk >= 1 || desk<=0) {
							lblErrore.setText(ResourceBundle.getBundle("Etiquetas").getString("DeskontuaGUI.Zenb"));
						} else {
							Discount di = new Discount(txtizena.getText(), desk, true);
							appFacadeInterface.createDiscount(di);
							txtzenb.setText("");
							txtizena.setText("");
							lblErrore.setText(ResourceBundle.getBundle("Etiquetas").getString("DeskontuaGUI.SortuDa"));
						}
					} catch (NumberFormatException e1) {
						lblErrore.setText(ResourceBundle.getBundle("Etiquetas").getString("DeskontuaGUI.Zenb"));
					}

				} else {
					lblErrore.setText(ResourceBundle.getBundle("Etiquetas").getString("DeskontuaGUI.Sortu"));
				}
			}
		});
		btnNewButton.setText(ResourceBundle.getBundle("Etiquetas").getString("DeskontuaGUI.Sortu"));
		btnNewButton.setBounds(85, 194, 107, 34);
		getContentPane().add(btnNewButton);

		btnItxi = new JButton((String) null);
		btnItxi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		btnItxi.setText(ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.Close"));
		btnItxi.setBounds(268, 194, 107, 34);
		getContentPane().add(btnItxi);

		lblErrore = new JLabel();
		lblErrore.setBounds(47, 121, 379, 64);
		getContentPane().add(lblErrore);
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
