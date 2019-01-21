package tel_ran.library.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import tel_ran.library.entities.Book;
import tel_ran.library.entities.BookRecord;
import tel_ran.library.entities.Reader;
import tel_ran.library.util.PredicateIsbnRecord;

public abstract class LibraryCollections extends Library {
	Collection<Book> books;
	Collection<BookRecord> records;
	Collection<Reader> readers;

	public LibraryCollections(int pickPeriod) {
		super(pickPeriod);
	}

	
	@Override
	public Iterator<Book> iterator() {
		return books.iterator();
	}

	@Override
	public boolean addBookItem(Book book) {
		if (getBookItem(book.getIsbn()) != null)
			return false;
		return books.add(book);
	}

	@Override
	public boolean addBookExemplar(long isbn) {
		Book book = getBookItem(isbn);
		if (book == null)
			return false;
		book.setAmount(book.getAmount() + 1);
		return true;
	}

	@Override
	public boolean removeBook(long isbn) {
		Book book = getBookItem(isbn);
		if (book == null || book.getAmountInUse() != 0)
			return false;
		books.remove(book);
		records.removeIf(new PredicateIsbnRecord(isbn));
		return true;
	}

	@Override
	public boolean pickBook(long isbn, LocalDate pickDate, int readerId) {
		Book book = getBookItem(isbn);
		if (book == null)
			return false;

		int amountInUse = book.getAmountInUse();
		if (amountInUse == book.getAmount())
			return false;
		if (getBookRecord(isbn, readerId) != null)
			return false;
		book.setPicksOverall(book.getPicksOverall() + 1);
		book.setAmountInUse(amountInUse + 1);
		return records.add(new BookRecord(isbn, pickDate, readerId));
	}

	private BookRecord getBookRecord(long isbn, int readerId) {
		for (BookRecord record : records) {
			if (record.getIsbn() == isbn && record.getReaderId() == readerId)
				return record;
		}
		return null;
	}

	@Override
	public boolean returnBook(long isbn, int readerId, LocalDate returnDate) {
		BookRecord record = getBookRecord(isbn, readerId);
		if (record == null)
			return false;
		record.setReturnDate(returnDate);
		Book book = getBookItem(isbn);
		book.setAmountInUse(book.getAmountInUse() - 1);
		return true;
	}

	@Override
	public Iterable<Book> getNonReturnedBooks() {
		List<Book> result = new ArrayList<>();
		for (BookRecord record : records) {
			if (record.getReturnDate() == null) {
				result.add(getBookItem(record.getIsbn()));
			}
		}
		return result;

	}

	@Override
	public Iterable<BookRecord> getNonReturnedBookRecords() {
		List<BookRecord> result = new ArrayList<>();
		for (BookRecord record : records) {
			if (record.getReturnDate() == null) {
				result.add(record);
			}
		}
		return result;
	}

	@Override
	public Iterable<BookRecord> getDelayedBookRecords(LocalDate currentDate) {
		List<BookRecord> result = new ArrayList<>();
		for (BookRecord record : records) {
			if (getDelayedPeriod(record, currentDate) > 0) {
				result.add(record);
			}
		}
		return result;
	}

	@Override
	public Iterable<Reader> getReadersDelayedBooks(LocalDate currentDate, int byDays) {
		List<Reader> result = new ArrayList<>();
		for (BookRecord record : records) {
			if (getDelayedPeriod(record, currentDate) > byDays) {
				result.add(getReader(record.getReaderId()));
			}
		}
		return result;
	}

	private int getDelayedPeriod(BookRecord record, LocalDate currentDate) {

		return (int) (record.getReturnDate() != null ? -1
				: ChronoUnit.DAYS.between(record.getPickDate().plusDays(pickPeriod), currentDate));
	}

	@Override
	public boolean addReader(Reader reader) {
		if (getReader(reader.getReaderId()) != null)
			return false;
		return readers.add(reader);
	}

	@Override
	public Iterable<Reader> getReaders(long isbn) {
		List<Reader> result = new ArrayList<>();
		for (BookRecord record : records) {
			if (record.getIsbn() == isbn) {
				result.add(getReader(record.getReaderId()));
			}
		}
		return result;
	}

	@Override
	public Reader getReader(int readerId) {
		for (Reader reader : readers) {
			if (reader.getReaderId() == readerId)
				return reader;
		}
		return null;
	}

	@Override
	public Book getBookItem(long isbn) {
		for (Book book : books) {
			if (book.getIsbn() == isbn)
				return book;
		}
		return null;
	}

	@Override
	public Iterable<BookRecord> getAllRecords() {
		return records;
	}

	@Override
	public Iterable<Reader> getAllReaders() {
		return readers;
	}

}
