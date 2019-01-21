package tel_ran.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

import tel_ran.view.util.PredicateArray;
import tel_ran.view.util.PredicateDate;
import tel_ran.view.util.PredicateInt;
import tel_ran.view.util.PredicateRange;
import tel_ran.view.util.PredicateRangeLong;
import tel_ran.view.util.PredicateLength;
import tel_ran.view.util.PredicateLong;

public interface InputOutput {

String WRONG_INPUT = "Wrong input please try again";
String getString(String prompt);
default String getString(String prompt,int minLength, int maxLength){
	return getString(prompt,new PredicateLength(minLength,maxLength));
}
default Integer getInteger(String prompt){
	String resStr=getString(prompt,new PredicateInt());
	
	return Integer.parseInt(resStr);
}
default String getString(String prompt, Predicate<String>predicate){
	String line="";
	do {
		line=getString(prompt);
		prompt=WRONG_INPUT;
	}while(!predicate.test(line));
	return line;
}
default String getString(String prompt, String[]from){
	return getString(prompt,new PredicateArray(from));
}
default Integer getInteger(String prompt, Integer minValue,Integer maxValue){
	return getInteger(prompt,new PredicateRange(minValue,maxValue));
}
default Integer getInteger(String prompt,Predicate<Integer>predicate){
	Integer res=0;
	do {
		res=getInteger(prompt);
		prompt=WRONG_INPUT;
	}while(!predicate.test(res));
	return res;
}
default Long getLong(String prompt){
String resStr=getString(prompt,new PredicateLong());
	
	return Long.parseLong(resStr);
}
default Long getLong(String prompt, Long minValue,Long maxValue){
	return  getLong(prompt,new PredicateRangeLong(minValue,maxValue));
}
default Long getLong(String prompt,Predicate<Long>predicate){
	Long res=0l;
	do {
		res=getLong(prompt);
		prompt=WRONG_INPUT;
	}while(!predicate.test(res));
	return res;
}
default LocalDate getDate(String prompt,String format){
	String strDate=getString(prompt+" "+"in format "+format,
			new PredicateDate(format));
	return LocalDate.parse(strDate,DateTimeFormatter.ofPattern(format));
}
default LocalDate getDate(String prompt,String format,LocalDate fromInclusive,LocalDate toExclusive){
	LocalDate res=null;
	do {
		res=getDate(prompt,format);
		prompt=WRONG_INPUT;
	}while(res.isBefore(fromInclusive) || res.isAfter(toExclusive));
	return res;
}
void put(Object object);
}
