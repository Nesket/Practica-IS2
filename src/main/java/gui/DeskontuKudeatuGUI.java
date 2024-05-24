package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Discount;

public class DeskontuKudeatuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private static BLFacade appFacadeInterface;
	private JTable taula;
	private JButton jButtonEzabatu;
	private JButton jButtonEditatu;
	private JButton jButtonClose;
	private JScrollPane scrollPane;

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	public DeskontuKudeatuGUI(String username) {
		setBussinessLogic(LoginGUI.getBusinessLogic());
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("AdminGUI.Kudea"));
		this.setSize(new Dimension(600, 537));
		this.setResizable(false);
		getContentPane().setLayout(new BorderLayout(0, 0));

		// Lista
		taula = new JTable();
		List<Discount> Desklist = appFacadeInterface.getAllDiscounts();
		scrollPane = new JScrollPane(taula);
		getContentPane().add(scrollPane, BorderLayout.NORTH);

		taula.getTableHeader().setReorderingAllowed(false);
		taula.setColumnSelectionAllowed(false);
		taula.setRowSelectionAllowed(true);
		taula.setDefaultEditor(Object.class, null);

		String[] columnNames = { ResourceBundle.getBundle("Etiquetas").getString("Code"),
				ResourceBundle.getBundle("Etiquetas").getString("Percentage"),
				ResourceBundle.getBundle("Etiquetas").getString("Active") };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		for (Discount dis : Desklist) {
			Object[] rowData = { dis.getKodea(), dis.getPortzentaia(), dis.isActive() };
			model.addRow(rowData);
		}
		taula.setModel(model);

		// Erroreen textua
		JLabel lblErrorea = new JLabel();
		this.getContentPane().add(lblErrorea, BorderLayout.CENTER);

		jButtonEzabatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Delete"));
		jButtonEzabatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pos = taula.getSelectedRow();
				if (pos != -1) {
					Discount dis = Desklist.get(pos);
					appFacadeInterface.deleteDiscount(dis);
					model.removeRow(pos);
				} else {
					lblErrorea.setForeground(Color.RED);
					lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("DeskontuaGUI.Erroraukera"));
				}
			}
		});
		this.getContentPane().add(jButtonEzabatu, BorderLayout.WEST);

		jButtonEditatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DeskontuaGUI.Edit"));
		jButtonEditatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pos = taula.getSelectedRow();
				if (pos != -1) {
					Discount dis = Desklist.get(pos);
					boolean egber;
					if (dis.isActive()) {
						dis.setActive(false);
						egber = false;
					} else {
						dis.setActive(true);
						egber = true;
					}
					appFacadeInterface.updateDiscount(dis);
					model.setValueAt(egber, pos, 2);
				} else {
					lblErrorea.setForeground(Color.RED);
					lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("DeskontuaGUI.Erroraukera"));
				}
			}
		});
		this.getContentPane().add(jButtonEditatu, BorderLayout.EAST);

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
