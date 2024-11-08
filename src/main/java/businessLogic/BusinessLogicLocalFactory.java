package businessLogic;

import dataAccess.DataAccess;

public class BusinessLogicLocalFactory implements BusinessLogicFactoryInterface {
	@Override
	public BLFacade getBusinessLogicFactory() {
		DataAccess da = new DataAccess();
		return new BLFacadeImplementation(da);
	}
}
