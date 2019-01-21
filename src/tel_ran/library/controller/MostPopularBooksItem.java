package tel_ran.library.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.StreamSupport;

import tel_ran.library.entities.Book;
import tel_ran.library.model.Library;
import tel_ran.view.InputOutput;
import tel_ran.view.util.OverallComparator;

public  class MostPopularBooksItem extends LibraryItem {

	public MostPopularBooksItem(Library library, InputOutput inputOutput) {
		super(library, inputOutput);
		
	}

	@Override
	public String displayedName() {
		return "Getting most popular books";
	}

	@Override
	public void perform() {
		displayMostPopularBooks();

	}
	private  void displayMostPopularBooks() {
		inputOutput.put("The most popular books");
		//List<Book> books=getBooksCollection();
		//Collection<Book> mostPopularBooks = getMostPopularBooks(books);
		//display(mostPopularBooks);
		
		StreamSupport
				.stream(library.spliterator(), false)                               // Iterable<Book> ==> Stream<Book>
				.sorted((b1, b2) -> -(b1.getPicksOverall() - b2.getPicksOverall())) // sorted by picks in reverse order
				.limit(5)                    // take at most 5 of most popular books
                .forEach(inputOutput::put);  // print using InputOutput console
		
	}
	
	private  List<Book> getMostPopularBooks(Collection<Book> books) {
		if(books.isEmpty())
			return null;
		Book book=Collections.max(books, new OverallComparator());
		List<Book> bookPicksOverall=getBooksByPicksOverall(books,book.getPicksOverall());
		return bookPicksOverall;
	}

	private  List<Book> getBooksCollection() {
		List<Book>result=new ArrayList<>();
		
		for(Book book:library)
			result.add(book);
		return result;
	}
	private  List<Book> getBooksByPicksOverall
	(Collection<Book> books, int picksOverall) {
		ArrayList<Book> result=new ArrayList<Book>();
		for(Book book:books){
			if(book.getPicksOverall()==picksOverall)
				result.add(book);
		}
		return result;
	}
}
