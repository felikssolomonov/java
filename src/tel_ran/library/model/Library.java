package tel_ran.library.model;

import java.time.LocalDate;

import tel_ran.library.entities.*;

public abstract class Library implements Iterable<Book> {
	protected int pickPeriod;

	public Library(int pickPeriod) {
		this.pickPeriod = pickPeriod;
	}

	public abstract boolean addBookItem(Book book);

	abstract public boolean addBookExemplar(long isbn);

	abstract public boolean removeBook(long isbn); // with all records. only if
													// the book exists and not
													// in use.

	abstract public boolean pickBook(long isbn, LocalDate pickDate, int readerId);

	abstract public boolean returnBook(long isbn, int readerId,
			LocalDate returnDate);

	abstract public Iterable<Book> getNonReturnedBooks();

	abstract public Iterable<BookRecord> getNonReturnedBookRecords();

	abstract public Iterable<BookRecord> getDelayedBookRecords(
			LocalDate currentDate);

	// getting info about the readers who have been delaying books for
	// more than the given byDays value
	abstract public Iterable<Reader> getReadersDelayedBooks(
			LocalDate currentDate, int byDays);

	abstract public boolean addReader(Reader reader);

	abstract public Iterable<Reader> getReaders(long isbn);

	abstract public Reader getReader(int readerId);

	abstract public Book getBookItem(long isbn);

	abstract public Iterable<BookRecord> getAllRecords();

	abstract public Iterable<Reader> getAllReaders();

	public int getPickPeriod() {
		return pickPeriod;
	}

	public void setPickPeriod(int pickPeriod) {
		this.pickPeriod = pickPeriod;
	}

}
