package businessLogic;

public class BLMain {
	public static void main(String[] args) {
		// the BL is local
		boolean isLocal = true;
		BLFactory blf = new BLFactory();
		BLFacade blFacade = blf.getBusinessLogicFactory(isLocal);
		
		ExtendedIterator<Object> i = blFacade.getDepartingCitiesIterator();

		String c;
		System.out.println("_____________________");
		System.out.println("FROM LAST TO FIRST");
		i.goLast(); // Go to last element
		while (i.hasPrevious()) {
			c = (String) i.previous();
			System.out.println(c);
		}
		System.out.println();
		System.out.println("_____________________");
		System.out.println("FROM FIRST TO LAST");
		i.goFirst(); // Go to first element
		while (i.hasNext()) {
			c = (String) i.next();
			System.out.println(c);
		}
	}
}
