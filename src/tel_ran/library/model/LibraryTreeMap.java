package tel_ran.library.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.SortedMap;
import java.util.TreeMap;

import tel_ran.library.entities.*;
import tel_ran.library.util.BookRecordId;

public class LibraryTreeMap extends LibraryMap {
	
	private Integer lastReaderId;
	
	public LibraryTreeMap(int pickPeriod) {
		super(pickPeriod);
		super.books = new TreeMap<Long, Book>();
		super.records = new TreeMap<BookRecordId, BookRecord>();
		super.readers = new TreeMap<Integer, Reader>();
		lastReaderId = !readers.isEmpty() ? ((TreeMap<Integer, Reader>)readers).lastKey() : 0;
	}
	
	@Override
	public boolean removeBook(long isbn) {
		Book book = getBookItem(isbn);
		if (book == null || book.getAmountInUse() != 0) return false;
		
		if (books.remove(isbn) == null) return false;
		getBookRecordOnIsbn(isbn).clear();
		return true;
	}
	
	@Override
	public Iterable<Reader> getReaders(long isbn) {
		if (isbn < 0) return null;
		Collection<Reader> readers = new HashSet<Reader>();
		for (BookRecord bookRecord : getBookRecordOnIsbn(isbn).values()) {
			if (bookRecord.getIsbn() == isbn) {
				readers.add(getReader(bookRecord.getReaderId()));
			}
		}
		return readers;
	}
	
	@Override
	public boolean addReader(Reader reader) {
		if (reader == null) return false;
		
		Reader associatedReader = readers.put(reader.getReaderId(), reader);
		if (associatedReader != null) {
			readers.put(associatedReader.getReaderId(), associatedReader);
			return false;
		}
		lastReaderId = ((TreeMap<Integer, Reader>)readers).lastKey();
		return true;
	}
	
	// utility method
	private SortedMap<BookRecordId, BookRecord> getBookRecordOnIsbn(long isbn) {
		SortedMap<BookRecordId, BookRecord> BookRecordOnIsbn = 
				((TreeMap<BookRecordId, BookRecord>)records).subMap(
						new BookRecordId(isbn, 0), 
						new BookRecordId(isbn, lastReaderId + 1));
		return BookRecordOnIsbn;
	}
	
}
