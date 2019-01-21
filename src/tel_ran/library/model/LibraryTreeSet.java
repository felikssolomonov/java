package tel_ran.library.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

import tel_ran.library.entities.*;

public class LibraryTreeSet extends LibrarySet {
	
	public LibraryTreeSet(int pickPeriod) {
		super(pickPeriod > 0 ? pickPeriod : 20);
		super.books = new TreeSet<Book>();
		super.records = new TreeSet<BookRecord>();
		super.readers = new TreeSet<Reader>();
	}
	
	@Override
	public boolean returnBook(long isbn, int readerId, LocalDate returnDate) {
		if (returnDate == null) return false;
		BookRecord wantedBR = new BookRecord(isbn, null, readerId);
		BookRecord res = ((TreeSet<BookRecord>)records).ceiling(wantedBR);
		if (res != null && res.compareTo(wantedBR) == 0) {
			res.setReturnDate(returnDate);
			Book book = getBookItem(isbn);
			book.setAmountInUse(book.getAmountInUse() - 1);
			return true;
		}
		return false;
	}
	
	@Override
	public Reader getReader(int readerId) {
		Reader wantedReader = new Reader(readerId, "", "", null);
		Reader res = ((TreeSet<Reader>) readers).ceiling(wantedReader);
		return (res != null && res.compareTo(wantedReader) == 0) ? res : null;
	}
	
	@Override
	public Book getBookItem(long isbn) {
		Book wantedBook = new Book(isbn, "", "", 0);
		Book res = ((TreeSet<Book>) books).ceiling(wantedBook);
		return (res!= null && res.compareTo(wantedBook) == 0) ? res : null;
	}
	
	@Override
	public Iterable<Reader> getReaders(long isbn) {
		if (isbn < 0) return null;
		Collection<Reader> readers = new HashSet<Reader>();
		SortedSet<BookRecord> subSet = ((SortedSet<BookRecord>)records).subSet(
				new BookRecord(isbn, null, 0), 
				new BookRecord(isbn+1, null, 0));
		for (BookRecord bookRecord : subSet) {
			//if (bookRecord.getIsbn() == isbn) {
				Reader reader = getReader(bookRecord.getReaderId());
					readers.add(reader);
			//}
		}
		return readers;
	}
}
