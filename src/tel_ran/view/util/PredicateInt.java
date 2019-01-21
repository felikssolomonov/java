package tel_ran.view.util;

import java.util.function.Predicate;

public class PredicateInt implements Predicate<String> {

	@Override
	public boolean test(String t) {
		try {
			Integer.parseInt(t);
		}catch (Exception e){
			return false;
		}
		return true;
	}

}
