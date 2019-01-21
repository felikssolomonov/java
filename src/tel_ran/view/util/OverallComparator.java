package tel_ran.view.util;

import java.util.Comparator;

import tel_ran.library.entities.Book;

public class OverallComparator implements Comparator<Book> {

	@Override
	public int compare(Book o1, Book o2) {
		return o1.getPicksOverall()-o2.getPicksOverall();
	}

}
