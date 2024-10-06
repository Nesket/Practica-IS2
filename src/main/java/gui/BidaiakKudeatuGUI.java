package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import businessLogic.BLFacade;
import domain.Ride;

import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class BidaiakKudeatuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private static BLFacade appFacadeInterface;
	private JTable taula;
	private JButton jButtonCancel;
	private JButton jButtonClose;
	private JLabel lblErrorea;
	private JScrollPane scrollPane;

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	public BidaiakKudeatuGUI(String username) {

		setBussinessLogic(DriverGUI.getBusinessLogic());
		this.setSize(new Dimension(600, 537));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.RideManager"));
		this.setResizable(false);
		getContentPane().setLayout(new BorderLayout(0, 0));

		List<Ride> rideList = appFacadeInterface.getRidesByDriver(username);
		taula = new JTable();
		scrollPane = new JScrollPane(taula);
		getContentPane().add(scrollPane, BorderLayout.NORTH);

		String[] columnNames = { ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.LeavingFrom"),
				ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.GoingTo"),
				ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideDate"),
				ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.Price") };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		if (rideList != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			for (Ride ride : rideList) {
				if (ride.getDate() != null) {
					Object[] rowData = { ride.getFrom(), ride.getTo(), dateFormat.format(ride.getDate()),
							ride.getPrice() };
					model.addRow(rowData);
				}
			}
		}
		taula.setModel(model);

		taula.getTableHeader().setReorderingAllowed(false);
		taula.setColumnSelectionAllowed(false);
		taula.setRowSelectionAllowed(true);
		taula.setDefaultEditor(Object.class, null);

		lblErrorea = new JLabel();
		getContentPane().add(lblErrorea, BorderLayout.CENTER);

		jButtonCancel = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BidaiakKudeatuGUI.CancelRide"));
		jButtonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pos = taula.getSelectedRow();
				if (pos != -1) {
					Ride ride = rideList.get(pos);
					Date today = new Date();

					if (ride.getDate().after(today)) {
						appFacadeInterface.cancelRide(ride);
						lblErrorea.setText(
								ResourceBundle.getBundle("Etiquetas").getString("BidaiakKudeatuGUI.RideCanceled"));
						lblErrorea.setForeground(Color.BLACK);
						model.removeRow(pos);
					} else {
						lblErrorea.setText(
								ResourceBundle.getBundle("Etiquetas").getString("BidaiakKudeatuGUI.RideNotCancelable"));
						lblErrorea.setForeground(Color.RED);
					}
				} else {
					lblErrorea
							.setText(ResourceBundle.getBundle("Etiquetas").getString("BidaiakKudeatuGUI.SelectOther"));
					lblErrorea.setForeground(Color.RED);
				}
			}
		});

		getContentPane().add(jButtonCancel, BorderLayout.WEST);

		jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EgoeraGUI.Close"));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed();
			}
		});
		getContentPane().add(jButtonClose, BorderLayout.SOUTH);

	}

	private void jButtonClose_actionPerformed() {
		this.setVisible(false);
	}

}
