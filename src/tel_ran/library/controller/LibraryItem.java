package tel_ran.library.controller;

import tel_ran.library.model.*;
import tel_ran.view.*;

public abstract class LibraryItem implements Item {
	protected Library library;
	protected InputOutput inputOutput;

	public LibraryItem(Library library, InputOutput inputOutput) {
		super();
		this.library = library;
		this.inputOutput = inputOutput;
	}

	@Override
	public boolean isExit() {
		return false;
	}

	protected void display(Iterable<?> objects) {
		if (objects == null)
			return;
		for (Object obj : objects)
			inputOutput.put(obj);

	}
}
