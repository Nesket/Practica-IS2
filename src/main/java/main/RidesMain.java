package main;

import businessLogic.BLFacade;
import businessLogic.BLFactory;
import domain.Driver;
import domain.DriverTable;

public class RidesMain {

	public static void main(String[] args) {
		//	the BL is local
		boolean isLocal =	true;
		BLFacade	blFacade =	new BLFactory().getBusinessLogicFactory(isLocal);
		Driver	d= blFacade. getDriver("Urtzi");
		DriverTable	dt=new	DriverTable(d);
		dt.setVisible(true);
	}

}
