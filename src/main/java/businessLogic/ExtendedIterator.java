package businessLogic;

import java.util.Iterator;

public interface ExtendedIterator<Object> extends Iterator<Object> {
	public Object previous();
	
	public boolean hasPrevious();
	
	public void goFirst();

	public void goLast();
}
