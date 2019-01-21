package tel_ran.library.model;

import java.util.ArrayList;

public class LibraryList extends LibraryCollections {

	public LibraryList(int pickPeriod) {
		super(pickPeriod);
		books=new ArrayList<>();
		records=new ArrayList<>();
		readers=new ArrayList<>();
	}

}
