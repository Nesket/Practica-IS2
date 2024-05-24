package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import businessLogic.BLFacade;
import domain.Alert;

import java.awt.*;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class AlertakKudeatuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private static BLFacade appFacadeInterface;
	private JTable table;
	private JButton addButton;
	private JButton deleteButton;
	private JButton activateButton;
	private JButton deactivateButton;
	private JButton closeButton;
	private JLabel statusLabel;
	private JPanel buttonPanel;

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	public AlertakKudeatuGUI(String username) {

		setBussinessLogic(TravelerGUI.getBusinessLogic());
		this.setSize(new Dimension(600, 400));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("AlertGUI.Alert"));
		this.setResizable(false);
		getContentPane().setLayout(new BorderLayout(0, 0));

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		List<Alert> alertList = appFacadeInterface.getAlertsByUsername(username);
		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		table.getTableHeader().setReorderingAllowed(false);
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);
		table.setDefaultEditor(Object.class, null);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

		String[] columnNames = { ResourceBundle.getBundle("Etiquetas").getString("AlertGUI.Zenbakia"),
				ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.LeavingFrom"),
				ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.GoingTo"),
				ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideDate"),
				ResourceBundle.getBundle("Etiquetas").getString("AlertGUI.Aurkitua"),
				ResourceBundle.getBundle("Etiquetas").getString("AlertGUI.Aktibo") };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		if (alertList != null) {
			for (Alert alert : alertList) {
				String formattedDate = dateFormat.format(alert.getDate());
				Object[] rowData = { alert.getAlertNumber(), alert.getFrom(), alert.getTo(), formattedDate,
						alert.isFound(), alert.isActive() };
				model.addRow(rowData);
			}
		}
		table.setModel(model);

		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setWidth(0);

		statusLabel = new JLabel();
		getContentPane().add(statusLabel, BorderLayout.NORTH);

		addButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AlertGUI.AddAlert"));
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new AlertaSortuGUI(username);
				a.setVisible(true);
				closeButton_actionPerformed(e);
			}
		});
		buttonPanel.add(addButton);

		deleteButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AlertGUI.DeleteAlert"));
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					int alertNumber = (int) table.getValueAt(selectedRow, 0);
					boolean deleted = appFacadeInterface.deleteAlert(alertNumber);
					if (deleted) {
						refreshTable(username);
					} else {
						statusLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("AlertGUI.DeleteAlertError"));
					}
				} else {
					statusLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("AlertGUI.SelectAlert"));
				}
			}
		});
		buttonPanel.add(deleteButton);

		activateButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AlertGUI.ActivateAlert"));
		activateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					int modelRow = table.convertRowIndexToModel(row);
					int alertNumber = (int) table.getModel().getValueAt(modelRow, 0);
					Alert al = appFacadeInterface.getAlert(alertNumber);
					if (al.isActive()) {
						statusLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("AlertGUI.AlertActive"));
					} else {
						statusLabel.setText("");
						al.setActive(true);
						appFacadeInterface.updateAlert(al);
						refreshTable(username);
					}
				}
			}
		});
		buttonPanel.add(activateButton);

		deactivateButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AlertGUI.DeactivateAlert"));
		deactivateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					int modelRow = table.convertRowIndexToModel(row);
					int alertNumber = (int) table.getModel().getValueAt(modelRow, 0);
					Alert al = appFacadeInterface.getAlert(alertNumber);
					if (!al.isActive()) {
						statusLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("AlertGUI.AlertInactive"));
					} else {
						statusLabel.setText("");
						al.setActive(false);
						appFacadeInterface.updateAlert(al);
						refreshTable(username);
					}
				}
			}
		});
		buttonPanel.add(deactivateButton);

		closeButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeButton_actionPerformed(e);
			}
		});
		buttonPanel.add(closeButton);

	}

	private void closeButton_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	private void refreshTable(String username) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		List<Alert> alertList = appFacadeInterface.getAlertsByUsername(username);
		if (alertList != null) {
			for (Alert alert : alertList) {
				String formattedDate = dateFormat.format(alert.getDate());
				Object[] rowData = { alert.getAlertNumber(), alert.getFrom(), alert.getTo(), formattedDate,
						alert.isFound(), alert.isActive() };
				model.addRow(rowData);
			}
		}
	}

}
