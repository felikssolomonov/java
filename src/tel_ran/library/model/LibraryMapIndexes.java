package tel_ran.library.model;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import tel_ran.library.entities.Book;
import tel_ran.library.entities.BookRecord;
import tel_ran.library.entities.Reader;
import tel_ran.library.util.BookRecordId;
import tel_ran.library.util.PredicateIsbnRecord;

public class LibraryMapIndexes extends LibraryMap {
	HashMap<Long, List<BookRecord>> isbnRecords;
	TreeMap<LocalDate, List<BookRecord>> nonreturnedRecords;

	public LibraryMapIndexes(int pickPeriod) {
		super(pickPeriod);
		books = new HashMap<>();
		records = new HashMap<>();
		readers = new HashMap<>();
		isbnRecords = new HashMap<>();
		nonreturnedRecords = new TreeMap<>();
	}

	//
	// @Override
	// public boolean removeBook(long isbn) {
	// if(!isCheckRemove(isbn))
	// return false;
	// //books.remove(isbn);
	// //List<BookRecord> recordsToRemove=getRecordsToRemove(isbn);
	// //removeRecords(recordsToRemove);
	// //isbnRecords.remove(isbn);
	// //return true;
	// List<BookRecord> recordsToRemove=getRecordsToRemove(isbn);
	// recordsToRemove.stream()
	// .filter(x->x.equals(isbnRecords));
	// recordsToRemove.removeIf(x->x.equals(isbnRecords));
	//
	// return true;
	//
	//
	//
	// }
	//
	//
	//
	// private void removeRecords(List<BookRecord> recordsToRemove) {
	// for(BookRecord record:recordsToRemove)
	// records.remove
	// (new BookRecordId(record.getIsbn(), record.getReaderId()));
	//
	// }
	//
	//
	// private List<BookRecord> getRecordsToRemove(long isbn) {
	//
	// return isbnRecords.get(isbn);
	// }
	//
	//
	// private boolean isCheckRemove(long isbn) {
	// Book book=books.get(isbn);
	// return book != null && book.getAmountInUse()==0;
	// }
	// @Override
	// public boolean pickBook(long isbn, LocalDate pickDate, int readerId) {
	// if(!isCheckPick(isbn,readerId))
	// return false;
	// BookRecord record=new BookRecord(isbn, pickDate, readerId);
	// if(records.putIfAbsent
	// (new BookRecordId(isbn,readerId), record)!=null){
	// return false;
	// }
	// addIsbnRecords(record);
	// addNonReturned(record);
	// updateBookInfo(books.get(isbn));
	// return true;
	// }
	// private void addNonReturned(BookRecord record) {
	// LocalDate pickDate=record.getPickDate();
	// List<BookRecord> records=nonreturnedRecords.get(pickDate);
	// if(records==null){
	// records=new LinkedList<>();
	// nonreturnedRecords.put(pickDate, records);
	//
	// }
	// records.add(record);
	//
	// }
	// private void updateBookInfo(Book book) {
	// book.setAmountInUse(book.getAmountInUse()+1);
	// book.setPicksOverall(book.getPicksOverall()+1);
	//
	// }
	// private void addIsbnRecords(BookRecord record) {
	// List<BookRecord> records=isbnRecords.get(record.getIsbn());
	// if(records==null){
	// records=new LinkedList<>();
	// isbnRecords.put(record.getIsbn(),records);
	// }
	// records.add(record);
	// }
	// private boolean isCheckPick(long isbn, int readerId) {
	// Book book = books.get(isbn);
	// return book != null && book.getAmount()>book.getAmountInUse()
	// && readers.containsKey(readerId);
	// }
	// @Override
	// public boolean returnBook(long isbn, int readerId, LocalDate returnDate)
	// {
	//
	// BookRecord record=records.get(new BookRecordId(isbn,readerId));
	// if(record==null || record.getReturnDate()!=null){
	// return false;
	// }
	// record.setReturnDate(returnDate);
	// removeNonreturnedRecord(record);
	// updateBookInfo(books.get(isbn));
	// return true;
	//
	// }
	//
	// private void removeNonreturnedRecord(BookRecord record) {
	// List<BookRecord> records=nonreturnedRecords.get(record.getPickDate());
	// records.remove(record);
	//
	// }
	// @Override
	// public Iterable<Book> getNonReturnedBooks() {// to do whith stream
	// HashSet<Book> nonreturnedBook=new HashSet<>();
	// for(List<BookRecord> list:nonreturnedRecords.values()){
	// for(BookRecord bookRecord:list){
	// Book book=books.get(bookRecord.getIsbn());
	// nonreturnedBook.add(book);
	// }
	// }
	//
	// return nonreturnedBook;
	// }
	// @Override
	// public Iterable<BookRecord> getNonReturnedBookRecords() {
	// HashSet<BookRecord> nonreturnedBookRecords=new HashSet<>();
	//
	// for(List<BookRecord> bookRecord:nonreturnedRecords.values()){
	// nonreturnedBookRecords.addAll(bookRecord);
	// }
	// return nonreturnedBookRecords;
	// }
	// @Override
	// public Iterable<BookRecord> getDelayedBookRecords(LocalDate currentDate)
	// {
	// if(currentDate==null)
	// return null;
	// LocalDate key=currentDate.minusDays(getPickPeriod());
	// ArrayDeque<BookRecord> delayedBookRecords=new ArrayDeque<>();
	// for(List<BookRecord>
	// bookRecord:nonreturnedRecords.headMap(key).values()){
	// delayedBookRecords.addAll(bookRecord);
	// }
	// return delayedBookRecords;
	// }
	// @Override
	// public Iterable<Reader> getReadersDelayedBooks(LocalDate currentDate, int
	// byDays) {
	// if (currentDate == null || byDays < 0) return null;
	//
	// LocalDate pickPeriod=currentDate.minusDays(byDays);
	//
	// HashSet<Reader> readersDelayedBooks = new HashSet<>();
	// for(BookRecord bookRecord:getDelayedBookRecords(pickPeriod)){
	// readersDelayedBooks.add(getReader(bookRecord.getReaderId()));
	// }
	// return readersDelayedBooks;
	// }
	// @Override
	// public Iterable<Reader> getReaders(long isbn) {
	// if(isbn<0)
	// return null;
	// HashSet<Reader> readers=new HashSet<>();
	// for(BookRecord bookRecord:isbnRecords.get(isbn)){
	// readers.add(getReader(bookRecord.getReaderId()));
	// }
	//
	// return readers;
	// }
	// }
	@Override
	public boolean removeBook(long isbn) {
		if (!isCheckRemove(isbn))
			return false;
		books.remove(isbn);
		List<BookRecord> recordsToRemove = getRecordsToRemove(isbn);
		removeRecords(recordsToRemove);
		isbnRecords.remove(isbn);
		return true;
	}

	private void removeRecords(List<BookRecord> recordsToRemove) {
		for (BookRecord record : recordsToRemove)
			records.remove(new BookRecordId(record.getIsbn(), record
					.getReaderId()));
	}

	private List<BookRecord> getRecordsToRemove(long isbn) {
		return isbnRecords.get(isbn);
	}

	private boolean isCheckRemove(long isbn) {
		Book book = books.get(isbn);
		return book != null && book.getAmountInUse() == 0;
	}

	@Override
	public boolean pickBook(long isbn, LocalDate pickDate, int readerId) {
		if (!isCheckPick(isbn, readerId))
			return false;
		BookRecord record = new BookRecord(isbn, pickDate, readerId);
		if (records.putIfAbsent(new BookRecordId(isbn, readerId), record) != null) {
			return false;
		}
		addIsbnRecords(record);
		addNonReturned(record);
		updateBookInfo(books.get(isbn));
		return true;
	}

	private void addNonReturned(BookRecord record) {
		LocalDate pickDate = record.getPickDate();
		List<BookRecord> records = nonreturnedRecords.get(pickDate);
		if (records == null) {
			records = new LinkedList<>();
			nonreturnedRecords.put(pickDate, records);
		}
		records.add(record);

	}

	private void updateBookInfo(Book book) {
		book.setAmountInUse(book.getAmountInUse() + 1);
		book.setPicksOverall(book.getPicksOverall() + 1);

	}

	private void addIsbnRecords(BookRecord record) {
		List<BookRecord> records = isbnRecords.get(record.getIsbn());
		if (records == null) {
			records = new LinkedList<>();
			isbnRecords.put(record.getIsbn(), records);
		}
		records.add(record);
	}

	private boolean isCheckPick(long isbn, int readerId) {
		Book book = books.get(isbn);
		return book != null && book.getAmount() > book.getAmountInUse()
				&& readers.containsKey(readerId);
	}

	@Override
	public boolean returnBook(long isbn, int readerId, LocalDate returnDate) {
		BookRecord record = records.get(new BookRecordId(isbn, readerId));
		if (record == null || record.getReturnDate() != null)
			return false;
		Book book = books.get(isbn);
		if (book == null)
			throw new RuntimeException("record exists but the book doesn't");
		book.setAmountInUse(book.getAmountInUse() - 1);
		record.setReturnDate(returnDate);
		removeFromNonReturned(record);
		return true;
	}

	private void removeFromNonReturned(BookRecord record) {
		LocalDate pickDate = record.getPickDate();
		List<BookRecord> records = nonreturnedRecords.get(pickDate);
		if (records == null || !records.remove(record))
			throw new RuntimeException("discrepancy with non-returned records");
		if (records.isEmpty())
			nonreturnedRecords.remove(pickDate);

	}

	public Iterable<Book> getNonReturnedBooks() {
		// TODO with stream
		// List<Book> res = new LinkedList<>();
		// for(BookRecord record : getNonReturnedBookRecords())
		// res.add(books.get(record.getIsbn()));
		// return res;

		
		/*List<Book> res2 = nonreturnedRecords.values().stream()
				.flatMap(List::stream)
				.map(br -> books.get(br.getIsbn()))
				.collect(Collectors.toList());

		return res2;*/
		return nonreturnedRecords.values().stream()
				.flatMap(listBookRecords->listBookRecords.stream())
				.map(r->books.get(r.getIsbn())).distinct().collect(Collectors.toList());
	}

	@Override
	public Iterable<BookRecord> getNonReturnedBookRecords() {
		Collection<List<BookRecord>> bookRecordsLists = nonreturnedRecords
				.values();
		return getBookRecordsFromListsCollection(bookRecordsLists);
	}

	private Iterable<BookRecord> getBookRecordsFromListsCollection(
			Collection<List<BookRecord>> bookRecordsLists) {
		// TODO with stream
		// List<BookRecord> res=new LinkedList<>();
		// for(List<BookRecord> list:bookRecordsLists)
		// 		res.addAll(list);
		// return res;

		return bookRecordsLists
				.stream()
				.flatMap(List::stream)
				.collect(Collectors.toList());
	}
////////////
	@Override
	public Iterable<BookRecord> getDelayedBookRecords(LocalDate currentDate) {

		return getBookRecordsBefore(currentDate.minusDays(pickPeriod));
	}

	private Iterable<BookRecord> getBookRecordsBefore(LocalDate beforeDate) {
		Collection<List<BookRecord>> bookRecordsLists = nonreturnedRecords
				.headMap(beforeDate).values();
		return getBookRecordsFromListsCollection(bookRecordsLists);
	}

	@Override
	public Iterable<Reader> getReadersDelayedBooks(LocalDate currentDate,
			int byDays) {

		Iterable<BookRecord> records = getBookRecordsBefore(currentDate
				.minusDays(pickPeriod + byDays));
		return getReadersFromBookRecords(records);
	}

	private Iterable<Reader> getReadersFromBookRecords(	Iterable<BookRecord> records) {
		// TODO with stream
		// List<Reader> res=new ArrayList<>();
		// if(records!=null){
		//      for(BookRecord record:records){
		//          res.add(readers.get(record.getReaderId()));
		//      }
		// }
		// return res;
		if (records == null)
			return null;

		return StreamSupport.stream(records.spliterator(), false)
				.map(p -> readers.get(p.getReaderId()))
				.distinct().collect(Collectors.toList());
	}

	@Override
	public Iterable<Reader> getReaders(long isbn) {

		return getReadersFromBookRecords(isbnRecords.get(isbn));
	}
}
