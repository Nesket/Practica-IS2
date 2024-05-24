package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;
import com.toedter.calendar.JCalendar;
import domain.Discount;
import domain.Ride;
import domain.Traveler;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class BookGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JComboBox<String> jComboBoxOrigin = new JComboBox<String>();
	DefaultComboBoxModel<String> originLocations = new DefaultComboBoxModel<String>();

	private JComboBox<String> jComboBoxDestination = new JComboBox<String>();
	DefaultComboBoxModel<String> destinationCities = new DefaultComboBoxModel<String>();

	private JLabel jLabelOrigin = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.LeavingFrom"));
	private JLabel jLabelDestination = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.GoingTo"));
	private final JLabel jLabelEventDate = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideDate"));
	private final JLabel jLabelEvents = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.Rides"));
	private final JLabel lblEmaitza = new JLabel();
	private final JLabel lblDesk = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DeskontuaGUI.Izena"));
	private JTextArea lbldekera = new JTextArea();
	private final JTextField txtDesk = new JTextField();

	private JButton jButtonBook = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Book"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();

	private List<Date> datesWithRidesCurrentMonth = new Vector<Date>();

	private JTable tableRides = new JTable();

	private DefaultTableModel tableModelRides;

	private String[] columnNamesRides = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.Driver"),
			ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.NPlaces"),
			ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.Price"),
			ResourceBundle.getBundle("Etiquetas").getString("Balorazioa") };

	private final JLabel lblEserlekuak = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.NPlaces"));
	private JComboBox<Integer> comboBoxSeats = new JComboBox<Integer>();
	private final JLabel jLabelSaldoa = new JLabel();

	double desk = 0;

	private static BLFacade appFacadeInterface;

	public static BLFacade getBusinessLogic() {
		return appFacadeInterface;
	}

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	private DecimalFormat decimalFormat = new DecimalFormat("#.##");

	public BookGUI(String username) {

		BookGUI.setBussinessLogic(TravelerGUI.getBusinessLogic());

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.BookRides"));

		jLabelEventDate.setBounds(new Rectangle(457, 6, 140, 25));
		jLabelEvents.setBounds(285, 223, 346, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelEvents);

		jButtonClose.setBounds(new Rectangle(501, 419, 130, 30));

		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton2_actionPerformed(e);
			}
		});

		List<String> origins = appFacadeInterface.getDepartCities();

		for (String location : origins)
			originLocations.addElement(location);

		jLabelOrigin.setBounds(new Rectangle(6, 56, 92, 20));
		jLabelDestination.setBounds(6, 81, 61, 16);
		getContentPane().add(jLabelOrigin);

		lblDesk.setBounds(20, 262, 159, 13);
		getContentPane().add(lblDesk);

		getContentPane().add(jLabelDestination);

		jComboBoxOrigin.setModel(originLocations);
		jComboBoxOrigin.setBounds(new Rectangle(103, 50, 172, 20));

		List<String> aCities = appFacadeInterface.getDestinationCities((String) jComboBoxOrigin.getSelectedItem());
		for (String aciti : aCities) {
			destinationCities.addElement(aciti);
		}

		jComboBoxOrigin.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				destinationCities.removeAllElements();
				BLFacade facade = MainGUI.getBusinessLogic();
				List<String> aCities = facade.getDestinationCities((String) jComboBoxOrigin.getSelectedItem());
				for (String aciti : aCities) {
					destinationCities.addElement(aciti);
				}
				tableModelRides.getDataVector().removeAllElements();
				tableModelRides.fireTableDataChanged();
			}
		});

		jComboBoxDestination.setModel(destinationCities);
		jComboBoxDestination.setBounds(new Rectangle(103, 80, 172, 20));
		jComboBoxDestination.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				paintDaysWithEvents(jCalendar1, datesWithRidesCurrentMonth, new Color(210, 228, 238));

				BLFacade facade = MainGUI.getBusinessLogic();

				datesWithRidesCurrentMonth = facade.getThisMonthDatesWithRides(
						(String) jComboBoxOrigin.getSelectedItem(), (String) jComboBoxDestination.getSelectedItem(),
						jCalendar1.getDate());
				paintDaysWithEvents(jCalendar1, datesWithRidesCurrentMonth, Color.CYAN);

			}
		});

		jButtonBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tableRides.getSelectedRow();
				if (selectedRow != -1) {
					Ride selectedRide = (Ride) tableModelRides.getValueAt(selectedRow, 4);

					int seatsRequested;
					try {
						seatsRequested = (int) comboBoxSeats.getSelectedItem();
					} catch (NumberFormatException ex) {
						lblEmaitza.setText(ResourceBundle.getBundle("Etiquetas").getString("BookGUI.EserlekuError"));
						lblEmaitza.setForeground(Color.RED);
						return;
					}

					double ridePriceDesk = (selectedRide.getPrice() - desk) * seatsRequested;
					double availableBalance = appFacadeInterface.getActualMoney(username);
					if (availableBalance < ridePriceDesk) {
						lblEmaitza.setText(ResourceBundle.getBundle("Etiquetas").getString("BookGUI.PriceError"));
						lblEmaitza.setForeground(Color.RED);
						return;
					}

					boolean bookingSuccess = appFacadeInterface.bookRide(username, selectedRide, seatsRequested, desk);
					if (bookingSuccess) {
						Traveler traveler = appFacadeInterface.getTraveler(username);
						appFacadeInterface.addMovement(traveler, "BookFreeze", ridePriceDesk);
						double newBalance = appFacadeInterface.getActualMoney(username);
						jLabelSaldoa.setText(ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.Erabilgarri")
								+ newBalance + "\u20AC");
						int availableSeats = selectedRide.getnPlaces();
						tableModelRides.setValueAt(availableSeats, selectedRow, 1);
						lblEmaitza.setText(ResourceBundle.getBundle("Etiquetas").getString("BookGUI.Booked"));
						lblEmaitza.setForeground(Color.BLACK);
						desk = 0;
						txtDesk.setText("");
						lbldekera.setText("");
						tableModelRides.setValueAt(selectedRide.getPrice(), selectedRow, 2);
						comboBoxSeats.removeAllItems();
						for (int i = 1; i <= selectedRide.getnPlaces(); i++) {
							comboBoxSeats.addItem(i);
						}
						if (selectedRide.getnPlaces() <= 0)
							jButtonBook.setEnabled(false);
					} else {
						lblEmaitza.setText(ResourceBundle.getBundle("Etiquetas").getString("BookGUI.BookingError"));
						lblEmaitza.setForeground(Color.RED);
					}
				} else {
					lblEmaitza.setText(ResourceBundle.getBundle("Etiquetas").getString("BookGUI.NoRide"));
					lblEmaitza.setForeground(Color.RED);
				}
			}
		});

		jButtonBook.setBounds(new Rectangle(274, 419, 130, 30));
		jButtonBook.setBounds(285, 419, 130, 30);
		this.getContentPane().add(jButtonBook);
		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jComboBoxOrigin, null);

		this.getContentPane().add(jComboBoxDestination, null);

		jCalendar1.setBounds(new Rectangle(300, 50, 225, 150));

		// Code for JCalendar
		jCalendar1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {

				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();

					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);

					if (monthAct != monthAnt) {
						if (monthAct == monthAnt + 2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2
							// de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt + 1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}

						jCalendar1.setCalendar(calendarAct);

					}

					try {
						desk = 0;
						txtDesk.setText("");
						lbldekera.setText("");
						tableModelRides.setDataVector(null, columnNamesRides);
						tableModelRides.setColumnCount(5); // another column added to allocate ride objects

						BLFacade facade = MainGUI.getBusinessLogic();
						List<domain.Ride> rides = facade.getRides((String) jComboBoxOrigin.getSelectedItem(),
								(String) jComboBoxDestination.getSelectedItem(), UtilDate.trim(jCalendar1.getDate()));

						if (rides.isEmpty())
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.NoRides")
									+ ": " + dateformat1.format(calendarAct.getTime()));
						else
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.Rides")
									+ ": " + dateformat1.format(calendarAct.getTime()));
						for (domain.Ride ride : rides) {
							Vector<Object> row = new Vector<Object>();
							row.add(ride.getDriver().getUsername());
							row.add(ride.getnPlaces());
							row.add(ride.getPrice());
							row.add(decimalFormat
									.format(ride.getDriver().getBalorazioa() / ride.getDriver().getBalkop()));
							row.add(ride); // ev object added in order to obtain it with
											// tableModelEvents.getValueAt(i,4)
							tableModelRides.addRow(row);
						}
						datesWithRidesCurrentMonth = facade.getThisMonthDatesWithRides(
								(String) jComboBoxOrigin.getSelectedItem(),
								(String) jComboBoxDestination.getSelectedItem(), jCalendar1.getDate());
						paintDaysWithEvents(jCalendar1, datesWithRidesCurrentMonth, Color.CYAN);

					} catch (Exception e1) {

						e1.printStackTrace();
					}
					tableRides.getColumnModel().getColumn(0).setPreferredWidth(50);
					tableRides.getColumnModel().getColumn(1).setPreferredWidth(50);
					tableRides.getColumnModel().getColumn(2).setPreferredWidth(100);
					tableRides.getColumnModel().getColumn(3).setPreferredWidth(50);
					tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(4)); // not shown in
					tableRides.getTableHeader().setReorderingAllowed(false);
					tableRides.setColumnSelectionAllowed(false);
					tableRides.setRowSelectionAllowed(true);
					tableRides.setDefaultEditor(Object.class, null);

				}
			}

		});

		this.getContentPane().add(jCalendar1, null);

		scrollPaneEvents.setBounds(new Rectangle(285, 255, 346, 150));

		scrollPaneEvents.setViewportView(tableRides);
		tableModelRides = new DefaultTableModel(null, columnNamesRides);

		tableRides.setModel(tableModelRides);

		tableModelRides.setDataVector(null, columnNamesRides);
		tableModelRides.setColumnCount(5); // another column added to allocate ride objects

		tableRides.getColumnModel().getColumn(0).setPreferredWidth(50);
		tableRides.getColumnModel().getColumn(1).setPreferredWidth(50);
		tableRides.getColumnModel().getColumn(2).setPreferredWidth(100);
		tableRides.getColumnModel().getColumn(3).setPreferredWidth(50);

		tableRides.getTableHeader().setReorderingAllowed(false);
		tableRides.setColumnSelectionAllowed(false);
		tableRides.setRowSelectionAllowed(true);
		tableRides.setDefaultEditor(Object.class, null);

		tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(4)); // not shown in JTable

		tableRides.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (!event.getValueIsAdjusting()) {
					int selectedRow = tableRides.getSelectedRow();
					if (selectedRow != -1) {
						Ride selectedRide = (Ride) tableModelRides.getValueAt(selectedRow, 4);
						int availableSeats = selectedRide.getnPlaces();
						if (availableSeats > 0) {
							jButtonBook.setEnabled(true);
						} else {
							jButtonBook.setEnabled(false);
						}
					}
				}
			}
		});

		tableRides.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = tableRides.getSelectedRow();
				if (selectedRow != -1) {
					Ride selectedRide = (Ride) tableModelRides.getValueAt(selectedRow, 4);
					desk = 0;
					txtDesk.setText("");
					lbldekera.setText("");
					comboBoxSeats.removeAllItems();
					for (int i = 1; i <= selectedRide.getnPlaces(); i++) {
						comboBoxSeats.addItem(i);
					}
				}
			}
		});

		this.getContentPane().add(scrollPaneEvents, null);
		datesWithRidesCurrentMonth = appFacadeInterface.getThisMonthDatesWithRides(
				(String) jComboBoxOrigin.getSelectedItem(), (String) jComboBoxDestination.getSelectedItem(),
				jCalendar1.getDate());
		paintDaysWithEvents(jCalendar1, datesWithRidesCurrentMonth, Color.CYAN);
		lblEserlekuak.setBounds(6, 130, 140, 16);

		getContentPane().add(lblEserlekuak);

		comboBoxSeats.setBounds(103, 129, 100, 18);
		getContentPane().add(comboBoxSeats);
		double diruKop = appFacadeInterface.getActualMoney(username);
		jLabelSaldoa
				.setText(ResourceBundle.getBundle("Etiquetas").getString("MoneyGUI.Erabilgarri") + diruKop + "\u20AC");
		jLabelSaldoa.setHorizontalAlignment(SwingConstants.LEFT);
		jLabelSaldoa.setBounds(10, 177, 265, 20);

		getContentPane().add(jLabelSaldoa);
		lblEmaitza.setBounds(172, 208, 259, 16);

		getContentPane().add(lblEmaitza);

		txtDesk.setBounds(20, 285, 209, 19);
		txtDesk.setColumns(10);

		getContentPane().add(txtDesk);
		lbldekera.setBackground(new Color(240, 240, 240));

		lbldekera.setBounds(20, 345, 209, 108);
		lbldekera.setEditable(false);
		lbldekera.setLineWrap(true);
		lbldekera.setWrapStyleWord(true);
		getContentPane().add(lbldekera);

		JButton jButtonDesk = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DeskontuaGUI.Aplikatu"));
		jButtonDesk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pos = tableRides.getSelectedRow();
				if (pos != -1) {
					if (txtDesk.getText() != null) {
						Discount dis = appFacadeInterface.getDiscount(txtDesk.getText());
						if (dis != null && dis.isActive()) {
							lbldekera.setText(
									ResourceBundle.getBundle("Etiquetas").getString("DeskontuaGUI.AplikatuDa"));
							double deskontua = dis.getPortzentaia();
							Ride selectedRide = (Ride) tableModelRides.getValueAt(pos, 4);
							desk = ((selectedRide.getPrice()) * deskontua);

							tableModelRides.setValueAt(selectedRide.getPrice() - desk, pos, 2);
						} else {
							lbldekera.setText(ResourceBundle.getBundle("Etiquetas").getString("DeskontuaGUI.Error"));
						}

					}
				} else {
					lbldekera.setText(ResourceBundle.getBundle("Etiquetas").getString("BookGUI.NoRide"));
				}

			}
		});
		jButtonDesk.setBounds(20, 314, 209, 21);
		getContentPane().add(jButtonDesk);

	}

	public static void paintDaysWithEvents(JCalendar jCalendar, List<Date> datesWithEventsCurrentMonth, Color color) {
		// // For each day with events in current month, the background color for that
		// day is changed to cyan.

		Calendar calendar = jCalendar.getCalendar();

		int month = calendar.get(Calendar.MONTH);
		int today = calendar.get(Calendar.DAY_OF_MONTH);
		int year = calendar.get(Calendar.YEAR);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);
		offset += 5;

		for (Date d : datesWithEventsCurrentMonth) {

			calendar.setTime(d);

			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish
			// Component o=(Component)
			// jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);;
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(color);
		}

		calendar.set(Calendar.DAY_OF_MONTH, today);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);

	}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

}
