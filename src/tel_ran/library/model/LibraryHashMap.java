package tel_ran.library.model;

import java.util.HashMap;

public class LibraryHashMap extends LibraryMap {

	public LibraryHashMap(int pickPeriod) {
		super(pickPeriod > 0 ? pickPeriod : 20);
		super.books = new HashMap<>();
		super.records = new HashMap<>();
		super.readers = new HashMap<>();
	}
	
}
