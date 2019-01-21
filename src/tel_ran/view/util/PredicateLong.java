package tel_ran.view.util;

import java.util.function.Predicate;

public class PredicateLong implements Predicate<String> {

	@Override
	public boolean test(String t) {
		try {
			Long.parseLong(t);
			return true;
		}catch(Exception e){
			return false;
		}
	}

}
