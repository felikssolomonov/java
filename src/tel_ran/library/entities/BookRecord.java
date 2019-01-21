package tel_ran.library.entities;

import java.time.LocalDate;

public class BookRecord implements Comparable<BookRecord> {

	private long isbn;
	private int readerId;
	private LocalDate pickDate;
	private LocalDate returnDate;

	// / Constructor

	public BookRecord(long isbn, LocalDate pickDate, int readerId) {
		if (isbn >= 0)
			this.isbn = isbn;
		if (pickDate != null)
			this.pickDate = pickDate;
		if (readerId > 0)
			this.readerId = readerId;
	}

	// / Get & Set

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		if (returnDate != null)
			this.returnDate = returnDate;
	}

	public long getIsbn() {
		return isbn;
	}

	public LocalDate getPickDate() {
		return pickDate;
	}

	public int getReaderId() {
		return readerId;
	}

	// / hashCode & equals (per isbn & readerId)

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (isbn ^ (isbn >>> 32));
		result = prime * result + readerId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookRecord other = (BookRecord) obj;
		if (isbn != other.isbn)
			return false;
		if (readerId != other.readerId)
			return false;
		return true;
	}

	// / toString

	@Override
	public String toString() {
		return "BookRecord [isbn=" + isbn + ", pickDate=" + pickDate
				+ ", returnDate=" + returnDate + ", readerId=" + readerId + "]";
	}

	// / Comparable

	@Override
	public int compareTo(BookRecord br) {
		int res = Long.compare(isbn, br.isbn);
		if (res != 0) {
			return res;
		}
		return Integer.compare(readerId, br.readerId);
	}

}
