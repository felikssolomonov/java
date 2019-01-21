package tel_ran.library.entities;

import java.time.LocalDate;

public class Reader implements Comparable<Reader>{
	
	private int readerId;
	private String name;
	private String phone;
	private LocalDate birthDate;
	
	/// Constructor
	
	public Reader(int readerId, String name, String phone, LocalDate birthDate) {
		if (readerId > 0) this.readerId = readerId;
		if (name != null) this.name = name;
		setPhone(phone);
		if (birthDate != null) this.birthDate = birthDate;
	}
	
	/// Get & Set
	
	public String getPhone() {return phone;}
	public void setPhone(String phone) {
		if (phone != null) this.phone = phone;
	}

	public int getReaderId() {return readerId;}

	public String getName() {return name;}

	public LocalDate getBirthDate() {return birthDate;}
	
	/// hashCode & equals (per readerId)
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Reader other = (Reader) obj;
		if (readerId != other.readerId)
			return false;
		return true;
	}
	
	/// toString
	
	@Override
	public String toString() {
		return "Reader [readerId=" + readerId + ", name=" + name + ", phone=" + phone + ", birthDate=" + birthDate
				+ "]";
	}
	
	/// Comparable
	
	@Override
	public int compareTo(Reader r) {
		return this.readerId - r.readerId;
	}
	
}
