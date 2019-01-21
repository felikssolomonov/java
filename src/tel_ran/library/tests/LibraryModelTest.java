package tel_ran.library.tests;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import tel_ran.library.entities.Book;
import tel_ran.library.entities.BookRecord;
import tel_ran.library.entities.Reader;
import tel_ran.library.model.Library;
import tel_ran.library.model.LibraryTreeMap;

public class LibraryModelTest {
	private static final int AMOUNT1 = 10;
	private static final int AMOUNT2 = 1;
	private static final int PICK_PERIOD = 20;
	private static final long ISBN1 = 123;
	private static final long ISBN2 = 124;
	private static final int READER_ID1 = 123;
	private static final int READER_ID2 = 124;
	private static final LocalDate PICK_DATE = LocalDate.parse("2017-04-30");
	private static final LocalDate CURRENT_DATE_DELAY = LocalDate
			.parse("2017-05-30");
	private static final LocalDate CURRENT_DATE_NONDELAY = LocalDate
			.parse("2017-05-10");
	private static final BookRecord RECORD_ISBN1 = new BookRecord(ISBN1, null,
			READER_ID1);
	private static final BookRecord RECORD_ISBN2 = new BookRecord(ISBN2, null,
			READER_ID2);
	private static final int BY_DAYS_DELAY = 10;
	Book b1 = new Book(ISBN1, "book1", "author1", AMOUNT1);
	Book b2 = new Book(ISBN2, "book2", "author2", AMOUNT2);
	Reader r1 = new Reader(READER_ID1, "r1", "1111", LocalDate.now());
	Reader r2 = new Reader(READER_ID2, "r2", "2222", LocalDate.now());
	Library library;

	@Before
	public void setUp() {
		library = new LibraryTreeMap(PICK_PERIOD);
		library.addBookItem(b1);
		library.addBookItem(b2);
		library.addReader(r1);
		library.addReader(r2);
		library.pickBook(ISBN1, PICK_DATE, READER_ID1);
		library.pickBook(ISBN2, PICK_DATE, READER_ID2);
		library.returnBook(ISBN1, READER_ID1, CURRENT_DATE_NONDELAY);

	}

	@Test
	public void testAddBookItem() {
		Book[] books = new Book[2];
		int ind = 0;
		for (Book book : library)
			books[ind++] = book;
		List<Book> listBooks = Arrays.asList(books);
		assertEquals(2, ind);// two books
		assertTrue(listBooks.contains(b1));
		assertTrue(listBooks.contains(b2));
		assertEquals(false, library.addBookItem(b1));
		assertEquals(true,
				library.addBookItem(new Book(128, "hhh", "author", 2)));
		assertFalse(library.addBookItem(null));
	}

	@Test
	public void testAddExemplar() {
		Book book = library.getBookItem(ISBN2);
		library.addBookExemplar(ISBN2);
		assertEquals(AMOUNT2 + 1, book.getAmount());

	}

	@Test
	public void testAddReader() {
		Reader[] readers = new Reader[2];
		int ind = 0;
		for (Reader reader : library.getAllReaders())
			readers[ind++] = reader;
		List<Reader> listReaders = Arrays.asList(readers);
		assertEquals(2, ind);// two readers ind=2
		assertTrue(listReaders.contains(r1));
		assertTrue(listReaders.contains(r2));
		assertEquals(false, library.addReader(r1));
		assertEquals(true, library.addReader(new Reader(128, "name", "phone",
				LocalDate.now())));
	}

	@Test
	public void testPickBook() {
		List<BookRecord> records = Arrays.asList(RECORD_ISBN1, RECORD_ISBN2);
		int ind = 0;
		for (BookRecord record : library.getAllRecords()) {
			ind++;
			assertTrue(records.contains(record));
		}
		assertEquals(2, ind); // two records
		assertEquals(1, b1.getPicksOverall());
		assertEquals(1, b2.getPicksOverall());
		assertEquals(1, b2.getAmountInUse());
	}

	@Test
	public void testReturnBook() {
		int ind = 0;
		Book[] books = new Book[1];
		for (Book book : library.getNonReturnedBooks()) {
			books[ind++] = book;
		}
		assertEquals(b2, books[0]);
		assertEquals(0, b1.getAmountInUse());
	}

	@Test
	public void testReturnBookRecords() {
		List<BookRecord> records = Arrays.asList(new BookRecord(ISBN2, null,
				READER_ID2));
		int ind = 0;
		for (BookRecord record : library.getNonReturnedBookRecords()) {
			ind++;
			assertTrue(records.contains(record));
		}
		assertEquals(1, ind); // two records
	}

	@Test
	public void testRemoveBook() {
		assertEquals(false, library.removeBook(ISBN2));
		assertEquals(true, library.removeBook(ISBN1));
		assertEquals(true, library.addBookItem(b1));
		for (BookRecord record : library.getAllRecords()) {
			assertEquals(RECORD_ISBN2, record);
		}
	}

	@Test
	public void testDelayedRecords() {
		assertEquals(false, library
				.getDelayedBookRecords(CURRENT_DATE_NONDELAY).iterator()
				.hasNext());
		for (BookRecord record : library
				.getDelayedBookRecords(CURRENT_DATE_DELAY))
			assertEquals(RECORD_ISBN2, record);
	}

	@Test
	public void testDelayedReaders() {
		assertEquals(
				false,
				library.getReadersDelayedBooks(CURRENT_DATE_DELAY,
						BY_DAYS_DELAY + 1).iterator().hasNext());
		for (Reader reader : library.getReadersDelayedBooks(CURRENT_DATE_DELAY,
				BY_DAYS_DELAY - 1))
			assertEquals(r2, reader);
	}
}
