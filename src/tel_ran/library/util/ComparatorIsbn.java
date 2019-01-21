package tel_ran.library.util;

import java.util.Comparator;

import tel_ran.library.entities.BookRecord;

public class ComparatorIsbn implements Comparator<BookRecord> {

	@Override
	public int compare(BookRecord o1, BookRecord o2) {
		
		return Long.compare(o1.getIsbn(), o2.getIsbn());
	}

}
