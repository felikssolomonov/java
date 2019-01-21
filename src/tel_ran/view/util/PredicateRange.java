package tel_ran.view.util;

import java.util.function.Predicate;

public class PredicateRange implements Predicate<Integer> {
int minValue;
int maxValue;
	@Override
	public boolean test(Integer t) {
		return t>=minValue&&t<maxValue;
	}
	public PredicateRange(int minValue, int maxValue) {
		super();
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

}
