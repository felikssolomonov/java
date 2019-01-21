package tel_ran.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;


public class RandomInputConsoleOutput implements InputOutput {

private static final int MIN_LENGTH = 0;
private static final int MAX_LENGTH = 0;
boolean fl_print=true;
private Random gen=new Random();
public boolean isFl_print() {
	return fl_print;
}



public void setFl_print(boolean fl_print) {
	this.fl_print = fl_print;
}




	@Override
	public String getString(String prompt) {
		
		return getString(prompt,MIN_LENGTH,MAX_LENGTH);
	}
	@Override 
	public String getString(String prompt, int minLength, int maxLength){
		//String contains only lower case ascii letters (no digits, no other symbols)
		put(prompt);
		if(maxLength <= minLength)
			return "";
		int length=getRandomInteger(minLength, maxLength);
		byte[]characters=new byte[length];
		for(int i=0; i<length; i++){
			characters[i]=getRandomInteger((int)'a', 'z'+1).byteValue();
		}
		String res=new String(characters);
		put(res);
		return res;
		
	}
	private Integer getRandomInteger(int min, int max) {
		return min + gen.nextInt(max-min);
	}



	@Override 
	public Integer getInteger(String prompt) {
		
	return getInteger(prompt,0,Integer.MAX_VALUE);
		
	}
	@Override
	public Integer getInteger(String prompt,Integer min, Integer max){
		put(prompt);
		Integer res=getRandomInteger(min,max);
		put(res);
		return res;
	}
	@Override
	public Long getLong(String prompt,Long min, Long max){
		put(prompt);
		Long res=getRandomLong(min,max);
		put(res);
		return res;
	}
	public Double getRandomDouble(Double min, Double max){
		return min + gen.nextDouble()*(max-min);
	}
	public Float getRandomFloat(Float min, Float max){
		return min + gen.nextFloat()*(max-min);
	}
	public Long getRandomLong(Long min, Long max){
		return getRandomDouble(min.doubleValue(),max.doubleValue()).longValue();
	}
	



	@Override
	public Long getLong(String prompt){
		return getLong(prompt,0l,Long.MAX_VALUE);
	}
	@Override
	public void put(Object object) {
		if(fl_print)
			System.out.println(object);

	}
	public LocalDate getRandomDate(LocalDate from, LocalDate to){
		int period=gen.nextInt((int)ChronoUnit.DAYS.between(from, to));
		return from.plusDays(period);
	}
	@Override
	public LocalDate getDate(String prompt,String format,LocalDate from, LocalDate to){
		put(prompt);
		LocalDate res=getRandomDate(from,to);
		put(res.format(DateTimeFormatter.ofPattern(format)));
		return res;
	}
	@Override
	public LocalDate getDate(String prompt, String format){
		return getDate(prompt,format,LocalDate.ofYearDay(1900, 1),LocalDate.now());
	}
	@Override
	public String getString(String prompt,String[]from){
		put(prompt);
		int ind=getRandomInteger(0, from.length);
		put(from[ind]);
		return from[ind];
	}

}
