package businessLogic;

import java.util.List;

public class DepartingCitiesIterator implements ExtendedIterator<Object> {
	
	private List<String> departingCities;
	int position;
	
	public DepartingCitiesIterator(List<String> departingCities) {
		this.departingCities = departingCities;
		this.position = 0;
	}
	
	public boolean hasNext() {
		return this.position < this.departingCities.size();
	}

	@Override
	public String next() {
		String dCity = this.departingCities.get(this.position);
		this.position++;
		return dCity;
	}

	@Override
	public String previous() {
		this.position--;
		String dCity = this.departingCities.get(this.position);
		return dCity;
	}

	@Override
	public boolean hasPrevious() {
		return position > 0;
	}

	@Override
	public void goFirst() {
		this.position = 0;
	}

	@Override
	public void goLast() {
		this.position = departingCities.size();
	}

}
