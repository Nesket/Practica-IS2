package gui;

import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Alert;
import domain.Traveler;

public class AlertaSortuGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextField fieldOrigin = new JTextField();
	private JTextField fieldDestination = new JTextField();

	private JLabel jLabelOrigin = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.LeavingFrom"));
	private JLabel jLabelDestination = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.GoingTo"));
	private JLabel jLabRideDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideDate"));

	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	@SuppressWarnings("unused")
	private List<Date> datesWithEventsCurrentMonth;

	private JScrollPane scrollPaneEvents = new JScrollPane();

	private JButton jButtonCreate = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AlertGUI.AddAlert"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelError = new JLabel();

	private Traveler traveler;

	private static BLFacade appFacadeInterface;

	public static BLFacade getBusinessLogic() {
		return appFacadeInterface;
	}

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	public AlertaSortuGUI(String username) {

		setBussinessLogic(TravelerGUI.getBusinessLogic());

		this.traveler = appFacadeInterface.getTraveler(username);
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("AlertGUI.AddAlert"));

		jLabelOrigin.setBounds(new Rectangle(33, 94, 92, 20));

		jCalendar.setBounds(new Rectangle(300, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jButtonCreate.setBounds(new Rectangle(100, 263, 130, 30));

		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jLabelMsg.setText("");
				String error = field_Errors();
				if (error != null)
					jLabelMsg.setText(error);
				else {
					Date selectedDate = jCalendar.getDate();
					Date currentDate = new Date();
					if (selectedDate.before(UtilDate.trim(currentDate))
							|| UtilDate.trim(selectedDate).equals(UtilDate.trim(currentDate))) {
						jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("AlertGUI.InvalidDate"));
					} else {
						Alert newAlert = new Alert(fieldOrigin.getText(), fieldDestination.getText(),
								UtilDate.trim(selectedDate), traveler);
						boolean success = appFacadeInterface.createAlert(newAlert);
						if (success) {
							jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("AlertGUI.AlertCreated"));
							traveler.addAlert(newAlert);
							JFrame a = new AlertakKudeatuGUI(username);
							a.setVisible(true);
							jButtonClose_actionPerformed(e);
						} else {
							jLabelMsg.setText(
									ResourceBundle.getBundle("Etiquetas").getString("AlertGUI.AlertCreateFail"));
						}
					}
				}
			}
		});
		jButtonClose.setBounds(new Rectangle(275, 263, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new AlertakKudeatuGUI(username);
				a.setVisible(true);
				jButtonClose_actionPerformed(e);
			}
		});

		jLabelMsg.setBounds(new Rectangle(275, 214, 305, 20));
		jLabelMsg.setForeground(Color.red);

		jLabelError.setBounds(new Rectangle(10, 232, 320, 20));
		jLabelError.setForeground(Color.red);

		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelError, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jButtonCreate, null);
		this.getContentPane().add(jLabelOrigin, null);

		this.getContentPane().add(jCalendar, null);

		datesWithEventsCurrentMonth = appFacadeInterface.getThisMonthDatesWithRides("a", "b", jCalendar.getDate());

		jLabRideDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabRideDate.setBounds(298, 16, 140, 25);
		getContentPane().add(jLabRideDate);

		jLabelDestination.setBounds(33, 119, 92, 20);
		getContentPane().add(jLabelDestination);

		fieldOrigin.setBounds(127, 91, 130, 26);
		getContentPane().add(fieldOrigin);
		fieldOrigin.setColumns(10);

		fieldDestination.setBounds(127, 119, 130, 26);
		getContentPane().add(fieldDestination);
		fieldDestination.setColumns(10);

		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					@SuppressWarnings("unused")
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct != monthAnt) {
						if (monthAct == monthAnt + 2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolverá 2 de
							// marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt + 1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}

						jCalendar.setCalendar(calendarAct);
					}
					jCalendar.setCalendar(calendarAct);
					int offset = jCalendar.getCalendar().get(Calendar.DAY_OF_WEEK);

					if (Locale.getDefault().equals(new Locale("es")))
						offset += 4;
					else
						offset += 5;
					@SuppressWarnings("unused")
					Component o = (Component) jCalendar.getDayChooser().getDayPanel()
							.getComponent(jCalendar.getCalendar().get(Calendar.DAY_OF_MONTH) + offset);
				}
			}
		});

	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	private String field_Errors() {
		try {
			if ((fieldOrigin.getText().length() == 0) || (fieldDestination.getText().length() == 0))
				return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorQuery");
			else
				return null;
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;

		}
	}
}
