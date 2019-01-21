package tel_ran.view.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

public class PredicateDate implements Predicate<String> {
String format;
	@Override
	public boolean test(String t) {
		try {
			LocalDate.parse(t, DateTimeFormatter.ofPattern(format));
		}catch (Exception e){
			return false;
		}
		return true;
	}
	public PredicateDate(String format) {
		super();
		this.format = format;
	}

}
