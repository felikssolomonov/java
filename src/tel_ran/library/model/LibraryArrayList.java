package tel_ran.library.model;

import java.util.ArrayList;

import tel_ran.library.entities.*;

public class LibraryArrayList extends LibraryList {

	public LibraryArrayList(int pickPeriod) {
		super(pickPeriod > 0 ? pickPeriod : 20);
		super.books = new ArrayList<Book>();
		super.records = new ArrayList<BookRecord>();
		super.readers = new ArrayList<Reader>();
	}

}
