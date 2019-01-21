package tel_ran.library.model;

import java.util.LinkedList;

import tel_ran.library.entities.*;

public class LibraryLinkedList extends LibraryList {

	public LibraryLinkedList(int pickPeriod) {
		super(pickPeriod > 0 ? pickPeriod : 20);
		super.books = new LinkedList<Book>();
		super.records = new LinkedList<BookRecord>();
		super.readers = new LinkedList<Reader>();
	}
	
}
