package tel_ran.library.util;

import tel_ran.library.entities.BookRecord;

public class BookRecordId extends Pair<Long, Integer> {

	public BookRecordId(Long isbn, Integer readerId) {
		super(isbn, readerId);
	}

	public BookRecordId(BookRecord br) {
		super(br.getIsbn(), br.getReaderId());
	}

	Long getIsbn() {
		return this.getLeft();
	}
	
	Integer getReaderId() {
		return this.getRight();
	}
}
