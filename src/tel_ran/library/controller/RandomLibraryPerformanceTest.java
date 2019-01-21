package tel_ran.library.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import tel_ran.library.entities.*;
import tel_ran.library.model.*;
import tel_ran.view.*;

public class RandomLibraryPerformanceTest {

	private static final int PICK_PERIOD = 20;
	private static final int N_BOOKS = 10000;
	private static final Integer MIN_AMOUNT = 15;
	private static final Integer MAX_AMOUNT = 30;
	private static final Integer N_AUTHOR = 100;
	private static final int N_READERS = 1000;
	private static final long N_YEARS = 1;
	private static final Integer MIN_PICKS_BOOKS = 10;
	private static final Integer MAX_PICKS_BOOKS = 20;
	private static final Integer MIN_PERCENT_RETURN_BOOKS = 10;
	private static final Integer MAX_PERCENT_RETURN_BOOKS = 15;
	private static final int N_QUERY_RUN = 100;
	private static final int N_METHOD_RUN = 10000;

	interface LibraryTest {
		public void perform(Library library, InputOutput inputOutput);
	}

	static class NamedLibraryTest {
		public String getTestName() {
			return testName;
		}

		public LibraryTest getTestFunction() {
			return testFunction;
		}

		public NamedLibraryTest(String testName, LibraryTest testFunction) {
			super();
			this.testName = testName;
			this.testFunction = testFunction;
		}

		String testName;
		LibraryTest testFunction;
	}

	// only for example of instance reference below
	static class MyTest { 
		public void testCreateLibrary(Library library, InputOutput inputOutput) {
			createLibrary(library, inputOutput);
		}
	}

	public static void main(String[] args) {

		Library[] librarys = { 
				new LibraryArrayList(PICK_PERIOD), 
				new LibraryLinkedList(PICK_PERIOD),
				new LibraryHashSet(PICK_PERIOD), 
				new LibraryTreeSet(PICK_PERIOD),
				new LibraryHashMap(PICK_PERIOD), 
				new LibraryTreeMap(PICK_PERIOD)
		};
		
		// only for example of instance reference below
		MyTest myTestObject = new MyTest(); 
		
		NamedLibraryTest[] tests = {
				// 1st test as Anonymous Class
				new NamedLibraryTest("Create Library1", new LibraryTest(){
					@Override
					public void perform(Library library, InputOutput inout) {
						createLibrary(library, inout);		
					}				
				}),
				// same test as lambda		
				new NamedLibraryTest("Create Library2", (library, inout) -> createLibrary(library, inout)),
				// same test as static method reference
				new NamedLibraryTest("Create Library3", RandomLibraryPerformanceTest::createLibrary),
				// same test as instance method reference
				new NamedLibraryTest("Create Library4", myTestObject::testCreateLibrary),

				// --- rest of tests as lambdas

				new NamedLibraryTest("Imitate Library", RandomLibraryPerformanceTest::imitateLibraryWork),

				new NamedLibraryTest("Imitate Menu", (library, inout) -> {
					Item mostPopularAuthor = new MostPopularAuthorsItem(library, inout);
					Item mostPopularBook = new MostPopularBooksItem(library, inout);
					Item readersDelayedBooks = new DelayedReadersItem(library, inout, N_YEARS);
					for (int j = 0; j < N_QUERY_RUN; j++) {
						mostPopularAuthor.perform();
						mostPopularBook.perform();
						readersDelayedBooks.perform();
					}
				}),

				new NamedLibraryTest("Readers By Book", (library, inout) -> {
					for (int j = 1; j < N_BOOKS; j++) {
						library.getReaders(j);
					}
				}),
				new NamedLibraryTest("Reader By Id", (library, inout) -> {
					for (int j = 1; j < N_READERS; j++) {
						library.getReader(j);
					}
				}),
				new NamedLibraryTest("Book By Id", (library, inout) -> {
					for (int j = 1; j < N_BOOKS; j++) {
						library.getBookItem(j);
					}
				}),
				new NamedLibraryTest("Add Books", (library, inout) -> {
					for (int j = 0; j < N_METHOD_RUN; j++) {
						Book testBook = new Book(10000 + j, "title#" + j, "author" + j, 2);
						library.addBookItem(testBook);
					}
				}),
				new NamedLibraryTest("Add Readers", (library, inout) -> {
					for (int j = 0; j < N_METHOD_RUN; j++) {
						Reader testReader = new Reader(10000 + j, "name#" + j, "phone#" + j, LocalDate.now().minusDays(j));
						library.addReader(testReader);
					}
				})
		};
		
		InputOutput inputOutput = new RandomInputConsoleOutput();
		// flag print = false
		((RandomInputConsoleOutput) inputOutput).setFl_print(false);

		for (NamedLibraryTest test: tests){
			System.out.println(test.getTestName());
			for (Library library: librarys){
				LocalTime t1 = LocalTime.now();
				test.getTestFunction().perform(library, inputOutput);
				LocalTime t2 = LocalTime.now();
				System.out.println(ChronoUnit.MILLIS.between(t1, t2) + " - " + library.getClass().getSimpleName());
			}
		}
	}
	// end main

	//---- imitate library

	private static void imitateLibraryWork(Library library,
			InputOutput inputOutput) {
		LocalDate lastWorkDay = LocalDate.now();
		LocalDate current = lastWorkDay.minusYears(N_YEARS);
		while (current.isBefore(lastWorkDay)) {
			pickBooks(library, inputOutput, current);
			returnBooks(library, inputOutput, current);
			current = current.plusDays(1);
		}
	}

	private static void pickBooks(Library library, InputOutput inputOutput,
			LocalDate current) {
		int nPicksBook = inputOutput.getInteger("enter num pick book",
				MIN_PICKS_BOOKS, MAX_PICKS_BOOKS);
		for (int i = 0; i < nPicksBook; i++) {
			long isbn = inputOutput.getLong("enter pick isbn", 1L,
					(long) N_BOOKS);
			int readerId = inputOutput.getInteger("enter pick readerId", 1,
					N_READERS);
			library.pickBook(isbn, current, readerId);
		}
	}

	private static void returnBooks(Library library, InputOutput inputOutput,
			LocalDate current) {
		int percentReturnBooks = inputOutput.getInteger(
				"enter percent return books", MIN_PERCENT_RETURN_BOOKS,
				MAX_PERCENT_RETURN_BOOKS);
		for (BookRecord bookRecord : library.getNonReturnedBookRecords()) {
			int probabilityReturnBook = inputOutput.getInteger(
					"enter probability return book", 1, 101);
			if (probabilityReturnBook < percentReturnBooks) {
				long isbn = bookRecord.getIsbn();
				int readerId = bookRecord.getReaderId();
				library.returnBook(isbn, readerId, current);
			}
		}
	}

	//--- create library

	private static void createLibrary(Library library, InputOutput inputOutput) {
		createBooks(library, inputOutput);
		createReaders(library, inputOutput);
	}

	// create readers

	private static void createReaders(Library library, InputOutput inputOutput) {
		for (int readerId = 1; readerId <= N_READERS; readerId++) {
			library.addReader(createReader(readerId, inputOutput));
		}
	}

	private static Reader createReader(int readerId, InputOutput inputOutput) {
		String name = "name" + readerId;
		String phone = "058" + readerId;
		LocalDate birthDate = createRandomBirthDay(inputOutput);
		return new Reader(readerId, name, phone, birthDate);
	}

	private static LocalDate createRandomBirthDay(InputOutput inputOutput) {
		LocalDate fromInclusive = LocalDate.now().minusYears(100);
		LocalDate toExclusive = LocalDate.now().minusYears(18);
		return inputOutput.getDate("enter random birth date", "yyyy-MM-dd",
				fromInclusive, toExclusive);
	}

	// create books

	private static void createBooks(Library library, InputOutput inputOutput) {
		for (int isbn = 1; isbn <= N_BOOKS; isbn++) {
			library.addBookItem(createBook(isbn, inputOutput));
		}
	}

	private static Book createBook(long isbn, InputOutput inputOutput) {
		String title = "title#" + isbn;
		String author = getRandomAuthor(inputOutput);
		int amount = inputOutput.getInteger("enter amount", MIN_AMOUNT,
				MAX_AMOUNT);
		return new Book(isbn, title, author, amount);
	}

	private static String getRandomAuthor(InputOutput inputOutput) {
		return "author#"
				+ inputOutput.getInteger("enter author number", 1, N_AUTHOR);
	}

}
