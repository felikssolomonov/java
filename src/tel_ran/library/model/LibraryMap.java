package tel_ran.library.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import tel_ran.library.entities.Book;
import tel_ran.library.entities.BookRecord;
import tel_ran.library.entities.Reader;
import tel_ran.library.util.BookRecordId;
import tel_ran.library.util.PredicateIsbnRecord;

abstract public class LibraryMap extends Library {
	
	protected Map<Long, Book> books; // books by isbn
	protected Map<Integer, Reader> readers; // readers by readerId
	protected Map<BookRecordId, BookRecord> records; // isbn + readerId
	
	/// Constructor
	
	public LibraryMap(int pickPeriod) {
		super(pickPeriod);
	}
	
	/// Iterator
	
	@Override
	public Iterator<Book> iterator() {
		return books.values().iterator();
	}
	
	/// Library methods
	
	@Override
	public boolean addBookItem(Book book) {
		if(book==null)
			return false;
		return books.putIfAbsent(book.getIsbn(), book)==null;
	}

	@Override
	public boolean addBookExemplar(long isbn) {
		Book associatedBook = getBookItem(isbn);
		if (associatedBook != null) {
			associatedBook.setAmount(associatedBook.getAmount() + 1);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeBook(long isbn) {
		Book book = getBookItem(isbn);
		if (book == null || book.getAmountInUse() != 0) return false;
		
		if (books.remove(isbn) == null) return false;
		records.values().removeIf(new PredicateIsbnRecord(isbn));
		return true;
	}

	@Override
	public boolean pickBook(long isbn, LocalDate pickDate, int readerId) {
		Book bookExemplar = getBookItem(isbn);
		if (bookExemplar == null) return false;
		if (!readers.containsKey(readerId)) return false;
		if (pickDate == null) return false;
		
		BookRecord bookRecord = new BookRecord(isbn, pickDate, readerId);
		BookRecordId bookRecordId = new BookRecordId(bookRecord);
		BookRecord associatedBookRecord = records.put(bookRecordId, bookRecord);
		if (associatedBookRecord != null) {
			records.put(bookRecordId, associatedBookRecord);
			return false;
		}
		
		bookExemplar.setAmountInUse(bookExemplar.getAmountInUse() + 1);
		bookExemplar.setPicksOverall(bookExemplar.getPicksOverall() + 1);
		return true;
	}

	@Override
	public boolean returnBook(long isbn, int readerId, LocalDate returnDate) {
		if (returnDate == null) return false;
		
		BookRecord associatedBookRecord = records.get(new BookRecordId(isbn, readerId));
		if (associatedBookRecord != null) {
			associatedBookRecord.setReturnDate(returnDate);
			Book book = getBookItem(isbn);
			if (book != null) {
				book.setAmountInUse(book.getAmountInUse() - 1);
			}
			return true;
		}
		return false;
	}
	
	@Override
	public Iterable<Book> getNonReturnedBooks() {
		Collection<Book> nonReturnedBooks = new HashSet<Book>();
		for (Book book : books.values()) {
			if (book.getAmountInUse() != 0) {
				nonReturnedBooks.add(book);
			}
		}
		return nonReturnedBooks;
	}

	@Override
	public Iterable<BookRecord> getNonReturnedBookRecords() {
		Collection<BookRecord> nonReturnedBookRecords = new HashSet<BookRecord>();
		for (BookRecord bookRecord : records.values()) {
			if (bookRecord.getReturnDate() == null) {
				nonReturnedBookRecords.add(bookRecord);
			}
		}
		return nonReturnedBookRecords;
	}

	@Override
	public Iterable<BookRecord> getDelayedBookRecords(LocalDate currentDate) {
		if (currentDate == null) return null;
		
		int pickPeriod = getPickPeriod();
		Collection<BookRecord> delayedBookRecords = new HashSet<BookRecord>();
		for (BookRecord bookRecord : records.values()) {
			if (bookRecord.getReturnDate() == null &&
					bookRecord.getPickDate().plusDays(pickPeriod).isBefore(currentDate)) {
				delayedBookRecords.add(bookRecord);
			}
		}
		return delayedBookRecords;
	}

	@Override
	public Iterable<Reader> getReadersDelayedBooks(LocalDate currentDate, int byDays) {
		if (currentDate == null || byDays < 0) return null;
		
		int pickPeriod = getPickPeriod() + byDays;
		Collection<Reader> readersDelayedBooks = new HashSet<Reader>();
		for (BookRecord bookRecord : records.values()) {
			if (bookRecord.getReturnDate() == null &&
					bookRecord.getPickDate().plusDays(pickPeriod).isBefore(currentDate)) {
				readersDelayedBooks.add(getReader(bookRecord.getReaderId()));
			}
		}
		return readersDelayedBooks;
	}

	@Override
	public boolean addReader(Reader reader) {
		return readers.putIfAbsent(reader.getReaderId(), reader)==null;
	}

	@Override
	public Iterable<Reader> getReaders(long isbn) {
		if (isbn < 0) return null;
		Collection<Reader> readers = new HashSet<Reader>();
		for (BookRecord bookRecord : records.values()) {
			if (bookRecord.getIsbn() == isbn) {
				readers.add(getReader(bookRecord.getReaderId()));
			}
		}
		return readers;
	}

	@Override
	public Reader getReader(int readerId) {
		return readers.get(readerId);
	}

	@Override
	public Book getBookItem(long isbn) {
		return books.get(isbn);
	}

	@Override
	public Iterable<BookRecord> getAllRecords() {
		return records.values();
	}

	@Override
	public Iterable<Reader> getAllReaders() {
		return readers.values();
	}
	
}
