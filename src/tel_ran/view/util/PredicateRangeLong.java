package tel_ran.view.util;

import java.util.function.Predicate;

public class PredicateRangeLong implements Predicate<Long> {
long minValue;
long maxValue;
	public PredicateRangeLong(long minValue, long maxValue) {
	super();
	this.minValue = minValue;
	this.maxValue = maxValue;
}
	@Override
	public boolean test(Long t) {
		long value=t.longValue();
		
		return value>=minValue && value<=maxValue;
	}

}
