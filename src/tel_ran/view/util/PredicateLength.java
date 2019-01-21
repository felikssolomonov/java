package tel_ran.view.util;

import java.util.function.Predicate;

public class PredicateLength implements Predicate<String> {
int minLength;
int maxLength;
	@Override
	public boolean test(String t) {
		int length=t.length();
		return length >= minLength && length < maxLength;
	}
	public PredicateLength(int minLength, int maxLength) {
		super();
		this.minLength = minLength;
		this.maxLength = maxLength;
	}

}
