package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import businessLogic.BLFacade;
import domain.Car;
import domain.Driver;
import java.awt.*;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KotxeakKudeatuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private static BLFacade appFacadeInterface;
	private JTable taulaCars;
	private JButton jButtonEzabatu;
	private JButton jButtonGehitu;
	private JButton jButtonClose;
	private JLabel lblErrorea;
	private JScrollPane scrollPane;

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	public KotxeakKudeatuGUI(String username) {

		setBussinessLogic(DriverGUI.getBusinessLogic());
		this.setSize(new Dimension(600, 537));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("KotxeaGUI.CarManager"));
		this.setResizable(false);
		getContentPane().setLayout(new BorderLayout(0, 0));

		Driver d = appFacadeInterface.getDriver(username);
		List<Car> carList = d.getCars();
		taulaCars = new JTable();
		scrollPane = new JScrollPane(taulaCars);
		getContentPane().add(scrollPane, BorderLayout.NORTH);

		taulaCars.getTableHeader().setReorderingAllowed(false);
		taulaCars.setColumnSelectionAllowed(false);
		taulaCars.setRowSelectionAllowed(true);
		taulaCars.setDefaultEditor(Object.class, null);
		
		String[] columnNames = { ResourceBundle.getBundle("Etiquetas").getString("KotxeaGUI.Matrikula"),
				ResourceBundle.getBundle("Etiquetas").getString("KotxeaGUI.Modeloa"),
				ResourceBundle.getBundle("Etiquetas").getString("KotxeaGUI.Eserlekuak") };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		if (carList != null) {
			for (Car car : carList) {
				Object[] rowData = { car.getMatrikula(), car.getModeloa(), car.getEserlekuak() };
				model.addRow(rowData);
			}
		}
		taulaCars.setModel(model);

		lblErrorea = new JLabel();
		getContentPane().add(lblErrorea, BorderLayout.CENTER);

		jButtonEzabatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("KotxeaGUI.KotxeaEzabatu"));
		jButtonEzabatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pos = taulaCars.getSelectedRow();
				if (pos != -1) {
					Car car = carList.get(pos);
					appFacadeInterface.deleteCar(car);
					lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("KotxeaGUI.KotxeaEzabatu"));
					lblErrorea.setForeground(Color.BLACK);
					model.removeRow(pos);
				} else {
					lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("KotxeaGUI.Select"));
					lblErrorea.setForeground(Color.RED);
				}
			}
		});
		getContentPane().add(jButtonEzabatu, BorderLayout.WEST);

		jButtonGehitu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("KotxeaGUI.KotxeaGehitu"));
		jButtonGehitu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("KotxeaGUI.Eguneratu"));
				lblErrorea.setForeground(Color.BLACK);
				JFrame a = new KotxeaGehituGUI(username);
				a.setVisible(true);
			}
		});
		getContentPane().add(jButtonGehitu, BorderLayout.EAST);

		jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.Close"));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		getContentPane().add(jButtonClose, BorderLayout.SOUTH);

	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

}
