package tel_ran.library.entities;

public class Book implements Comparable<Book>{
	
	private long isbn;
	private String title;
	private String author;
	private int amount;
	private int amountInUse;
	private int picksOverall;
	
	/// Constructor
	
	public Book(long isbn, String title, String author, int amount) {
		if (isbn > 0L) this.isbn = isbn;
		if (title != null) this.title = title;
		if (author != null) this.author = author;
		setAmount(amount);
		amountInUse = 0;
		picksOverall = 0;
	}
	
	/// Get & Set
	
	public int getAmount() {return amount;}
	public void setAmount(int amount) {
		if (amount >= 0) this.amount = amount;
	}

	public int getAmountInUse() {return amountInUse;}
	public void setAmountInUse(int amountInUse) {
		if (amountInUse >= 0) this.amountInUse = amountInUse;
	}

	public int getPicksOverall() {return picksOverall;}
	public void setPicksOverall(int picksOverall) {
		if (picksOverall > this.picksOverall) this.picksOverall = picksOverall;
	}

	public long getIsbn() {return isbn;}

	public String getTitle() {return title;}

	public String getAuthor() {return author;}
	
	/// hashCode & equals (per isbn)
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (isbn ^ (isbn >>> 32));
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
		Book other = (Book) obj;
		if (isbn != other.isbn)
			return false;
		return true;
	}
	
	/// toString
	
	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", title=" + title + ", author=" + author + ", amount=" + amount
				+ ", amountInUse=" + amountInUse + ", picksOverall=" + picksOverall + "]";
	}
	
	/// Comparable
	
	@Override
	public int compareTo(Book b) {
		return Long.compare(this.isbn,b.isbn);
	}
	
}
