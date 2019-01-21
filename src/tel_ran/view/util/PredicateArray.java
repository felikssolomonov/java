package tel_ran.view.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Predicate;

public class PredicateArray implements Predicate<String> {
HashSet<String> strings;
public PredicateArray(String[]strings){
	this.strings=new HashSet<>(Arrays.asList(strings));
}
	@Override
	public boolean test(String t) {
		return strings.contains(t);
	}

}
