package tel_ran.library.util;

import java.util.function.Predicate;

import tel_ran.library.entities.BookRecord;

public class PredicateIsbnRecord implements Predicate<BookRecord> {
long isbn;

	public PredicateIsbnRecord(long isbn) {
	super();
	this.isbn = isbn;
}

	@Override
	public boolean test(BookRecord t) {
		return t.getIsbn()==isbn;
	}

}
