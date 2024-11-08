package gui;

import java.util.Locale;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import configuration.ConfigXML;
import businessLogic.BLFacade;
import businessLogic.BLFactory;

public class ApplicationLauncher {

	public static void main(String[] args) {

		ConfigXML c = ConfigXML.getInstance();

		System.out.println(c.getLocale());

		Locale.setDefault(new Locale(c.getLocale()));

		System.out.println("Locale: " + Locale.getDefault());
		
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		boolean isLocal = c.isBusinessLogicLocal();
		BLFacade appFacadeInterface = new BLFactory().getBusinessLogicFactory(isLocal);

		MainGUI.setBussinessLogic(appFacadeInterface);
		MainGUI a = new MainGUI();
		a.setVisible(true);
	}

}
