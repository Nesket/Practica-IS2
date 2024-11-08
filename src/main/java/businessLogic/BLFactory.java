package businessLogic;

public class BLFactory {
	public BLFacade getBusinessLogicFactory(boolean isLocal) {
		BusinessLogicFactoryInterface blf;
		if(isLocal) blf = new BusinessLogicLocalFactory();
		else blf = new BusinessLogicRemoteFactory();
		
		return blf.getBusinessLogicFactory();
	}
}
