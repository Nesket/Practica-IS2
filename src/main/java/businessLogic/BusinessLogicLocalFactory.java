package businessLogic;

import configuration.ConfigXML;
import dataAccess.DataAccess;

public class BusinessLogicLocalFactory implements BusinessLogicFactory {
	public BLFacade create(ConfigXML c) {
		DataAccess da = new DataAccess();
		return new BLFacadeImplementation(da);
	}
}
