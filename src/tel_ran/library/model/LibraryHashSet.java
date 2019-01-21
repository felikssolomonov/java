package tel_ran.library.model;

import java.util.HashSet;

public class LibraryHashSet extends LibrarySet {

	public LibraryHashSet(int pickPeriod) {
		super(pickPeriod > 0 ? pickPeriod : 20);
		super.books = new HashSet<>();
		super.records = new HashSet<>();
		super.readers = new HashSet<>();
	}
	
}
