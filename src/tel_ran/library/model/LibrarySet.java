package tel_ran.library.model;

import java.util.Collection;
import java.util.HashSet;

import tel_ran.library.entities.Book;
import tel_ran.library.entities.BookRecord;
import tel_ran.library.entities.Reader;

public abstract class LibrarySet extends LibraryCollections {

	public LibrarySet(int pickPeriod) {
		super(pickPeriod);
	}
	
	@Override
	public boolean addBookItem(Book book) {
		return book != null ? books.add(book) : false;
	}
	
	@Override
	public boolean addReader(Reader reader) {
		return reader != null ? readers.add(reader) : false;
	}
	
	@Override
	public Iterable<Reader> getReaders(long isbn) {
		if (isbn < 0) return null;
		Collection<Reader> readers = new HashSet<Reader>();
		for (BookRecord bookRecord : records) {
			if (bookRecord.getIsbn() == isbn) {
				Reader reader = getReader(bookRecord.getReaderId());
					readers.add(reader);
			}
		}
		return readers;
	}
	
}
