package tel_ran.library.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import tel_ran.library.entities.BookRecord;
import tel_ran.library.entities.Reader;
import tel_ran.library.model.Library;
import tel_ran.library.util.ComparatorIsbn;
import tel_ran.library.util.IsbnOccurrences;
import tel_ran.view.InputOutput;

public class MostPopularAuthorsItem extends LibraryItem {

	public MostPopularAuthorsItem(Library library, InputOutput inputOutput) {
		super(library, inputOutput);
	}

	@Override
	public String displayedName() {
		return "Most popular authors by readers's ages";
	}

	@Override
	public void perform() {
		displayMostPopularAuthors();

	}

	private void displayMostPopularAuthors() {
		inputOutput.put("displaying the most popular authors");
		int minAge = inputOutput.getInteger("Enter reader's age from");
		int maxAge = inputOutput.getInteger("Enter reader's age to");
		//List<BookRecord> records = getRecordsReadersByAge(minAge, maxAge);
		//records.sort(new ComparatorIsbn());		
		//List<IsbnOccurrences> isbnOccurrences = getListOccurrences(records);
		//Set<String> authors = getAuthorNames(isbnOccurrences);
		//display(authors);
							
		StreamSupport
		.stream(library.getAllRecords().spliterator(), false)     // Iterable<BookRecord> ==> Stream<BookRecord> 
		.filter(br -> matchesReaderAge(br,minAge, maxAge))        // filtered by age Stream<BookRecord>
		.map(br -> library.getBookItem(br.getIsbn()).getAuthor()) // Stream<String> each record converted to author name
		.collect(Collectors.groupingBy(Function.identity(), Collectors.counting())) // grouping by author name, counting quantity
		// we received Map<String, Long> author <-> number of records
        .entrySet()
		.stream()
		.sorted(Map.Entry.<String, Long> comparingByValue().reversed()) // Stream <Map.Entry<String, Long>> sorted by value in reverse order
		.map(Map.Entry::getKey)                                         // Stream<String> authors by popularity, beginning from most popular
		.limit(5)                                                       // take at most 5 of most popular authors
		.forEach(inputOutput::put);	                              // print using InputOutput console
	}
/*
	private Set<String> getAuthorNames(List<IsbnOccurrences> isbnOccurrences) {
		Set<String> result = new HashSet<>();
		for (IsbnOccurrences isbnOccurrence : isbnOccurrences)
			result.add(library.getBookItem(isbnOccurrence.isbn).getAuthor());
		return result;
	}

	private List<IsbnOccurrences> getListOccurrences(List<BookRecord> records) {
		List<IsbnOccurrences> result = new ArrayList<>();
		int maxOccurrences = 1;
		long isbn = 0;
		int occurrences = 0;
		for (BookRecord record : records) {
			long currentIsbn = record.getIsbn();
			if (currentIsbn != isbn) {

				if (occurrences >= maxOccurrences) {
					maxOccurrences = occurrences;
					updateResult(isbn, result, occurrences);
				}
				occurrences = 1;
				isbn = currentIsbn;
			} else
				occurrences++;
		}
		return result;
	}

	private void updateResult(long currentIsbn, List<IsbnOccurrences> result, int occurrences) {
		if (!result.isEmpty() && result.get(0).occurrences != occurrences)
			result.clear();
		result.add(new IsbnOccurrences(currentIsbn, occurrences));

	}

	private List<BookRecord> getRecordsReadersByAge(int minAge, int maxAge) {
		List<BookRecord> result = new ArrayList<>();
		for (BookRecord record : library.getAllRecords()) {
			if (matchesReaderAge(record, minAge, maxAge)) {
				result.add(record);
			}
		}
		
		
		return result;
	}
*/
	private boolean matchesReaderAge(BookRecord record, int minAge, int maxAge) {
		Reader reader = library.getReader(record.getReaderId());
		int age = getReaderAge(reader);
		return age >= minAge && age < maxAge;
	}

	private int getReaderAge(Reader reader) {
		LocalDate birthdate = reader.getBirthDate();
		LocalDate current = LocalDate.now();
		return (int) ChronoUnit.YEARS.between(birthdate, current);
	}

}
