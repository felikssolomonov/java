package tel_ran.library.controller;

import java.time.LocalDate;

import tel_ran.library.model.Library;
import tel_ran.view.InputOutput;

public class DelayedReadersItem extends LibraryItem {

	private long nYears;

	public DelayedReadersItem(Library library, InputOutput inputOutput, long nYears) {
		super(library, inputOutput);
		this.nYears = nYears;
	}

	@Override
	public String displayedName() {
		return "Readers delaying books";
	}

	@Override
	public void perform() {
		displayReadersDelayed();
	}

	private void displayReadersDelayed() {

		LocalDate current = LocalDate.now().plusYears(nYears);
		int byDays = inputOutput.getInteger("enter number of delay days");
		inputOutput.put("Readers delaying books more than by " + byDays + " days");
		display(library.getReadersDelayedBooks(current, byDays));

	}

}
