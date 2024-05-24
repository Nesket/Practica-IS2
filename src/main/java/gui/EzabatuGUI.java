package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.User;

public class EzabatuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private static BLFacade appFacadeInterface;
	private JTable taula;
	private JButton jButtonEzabatu;
	private JButton jButtonClose;
	private JScrollPane scrollPane;
	DecimalFormat forma = new DecimalFormat("#.##");

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	public EzabatuGUI() {
		setBussinessLogic(LoginGUI.getBusinessLogic());
		this.setSize(new Dimension(600, 537));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("AdminGUI.Ezab"));
		this.setResizable(false);
		getContentPane().setLayout(new BorderLayout(0, 0));

		// Lista
		taula = new JTable();
		List<User> UserList = appFacadeInterface.getUserList();
		scrollPane = new JScrollPane(taula);
		getContentPane().add(scrollPane, BorderLayout.NORTH);

		taula.getTableHeader().setReorderingAllowed(false);
		taula.setColumnSelectionAllowed(false);
		taula.setRowSelectionAllowed(true);
		taula.setDefaultEditor(Object.class, null);

		String[] columnNames = { ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Username"),
				ResourceBundle.getBundle("Etiquetas").getString("Balorazioa")+ " (X/5)",
				ResourceBundle.getBundle("Etiquetas").getString("EzabatuGUI.ErreklamazioKop") };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		for (User us : UserList) {
			Object[] rowData = { us.getUsername(), forma.format(us.getBalorazioa() / us.getBalkop()), us.getErreklamaKop() };
			model.addRow(rowData);
		}
		taula.setModel(model);

		// Erroreen textua
		JLabel lblErrorea = new JLabel();
		this.getContentPane().add(lblErrorea, BorderLayout.CENTER);

		jButtonEzabatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Delete"));
		jButtonEzabatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (taula.getSelectedRow() != -1) {
					List<User> userLista = appFacadeInterface.getUserList();
					User us = userLista.get(taula.getSelectedRow());
					appFacadeInterface.deleteUser(us);
					model.removeRow(taula.getSelectedRow());
				} else {
					lblErrorea.setForeground(Color.RED);
					lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.Erroraukera"));
				}
			}
		});
		this.getContentPane().add(jButtonEzabatu, BorderLayout.WEST);

		// Itxi botoia
		jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.Close"));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		this.getContentPane().add(jButtonClose, BorderLayout.SOUTH);
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
