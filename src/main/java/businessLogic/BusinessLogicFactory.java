package businessLogic;

import configuration.ConfigXML;

public interface BusinessLogicFactory {
	public BLFacade create(ConfigXML c);
}
