
/**
 * 
 * @author Liam Snow
 */
public class Util {
	
	//Parse Safe
	/** Parses an integer without throwing an exception.
	 * If an integer cannot be parsed from the string the 
	 * specified default value will be used */
	public static int parseIntSafe(String str, int defaultValue) {
		try {
			return Integer.parseInt(str);
		}
		catch (Exception e) { return defaultValue; }
	}
	/** Parses an integer without throwing an exception.
	 * If an integer cannot be parsed from the string 
	 * 0 will be returned */
	public static int parseIntSafe(String str) {
		return parseIntSafe(str, 0);
	}
	/** Parses an double without throwing an exception.
	 * If an double cannot be parsed from the string the 
	 * specified default value will be used */
	public static double parseDoubleSafe(String str, double defaultValue) {
		try {
			if (str.indexOf("%") != -1)
				str = str.replaceAll("%", "");
			return Double.parseDouble(str);
		}
		catch (Exception e) { return defaultValue; }
	}
	/** Parses an double without throwing an exception.
	 * If an double cannot be parsed from the string 
	 * 0 will be returned */
	public static double parseDoubleSafe(String str) {
		return parseDoubleSafe(str, 0);
	}
	
	//String Between
	/** Returns the string inside the "start" and "end" */
	public static String inside(String str, String start, String end) {
		try {
			return str.substring(str.indexOf(start) + start.length(), str.indexOf(end));
		} catch (Exception e) { return ""; }
	}
	/** Returns the string inside an HTML tag (between the first ">" and last "<") */
	public static String insideTag(String str) {
		try {
			if (str.indexOf(">") == -1 || str.indexOf("<") == -1)
				return str;
			return str.substring(str.indexOf(">") + 1, str.lastIndexOf("<"));
		} catch (Exception e) { return ""; }
	}
	
	//Repeat and Pad
	/** Repeats a specific string n amount of times */
	public static String repeat(String str, int times) {
		String returnString = "";
		for (int i = 0; i < times; i++)
			returnString += str;
		return returnString;
	}
	/** Forces a string to a certain size, if the string is smaller is will add spaces
	 * on both sides, making the text centered */
	public static String pad(String str, int toSize) {
		return repeat(" ", (int) Math.ceil((toSize - str.length()) / 2.0)) + str + repeat(" ", (int) Math.floor((toSize - str.length()) / 2.0));
	}
	
	//String difference
	/** Finds the difference between two strings, where each character
	 * difference is counted as 1 */
	public static int difference(String str1, String str2) {
		str1 = str1.toLowerCase();
		str2 = str2.toLowerCase();
		int diff = 0;
		for (int i = 0; i < str1.length(); i++) {
			if (i >= str2.length() || str1.charAt(i) != str2.charAt(i))
				diff++;
		}
		diff += min(str2.length() - str1.length(), 0);
		return diff;
	}
	
	//Min, max, and clamp
	/** Keeps num to a min value, if num is less than min, min is returned,
	 * otherwise num is returned */
	public static int min(int num, int min) {
		return num < min ? min : num;
	}
	/** Keeps num to a max value, if num is greater than max, max is returned,
	 * otherwise num is returned */
	public static int max(int num, int max) {
		return num > max ? max : num;
	}
	/** Keeps num between a min and max value, uses the min() and max() methods from 
	 * this class */
	public static int clamp(int num, int min, int max) {
		return max(min(num, min), max);
	}
	
	//Abbreviations
	public static boolean isAbbreviationOf(String str, String str2) {
		String abr = getShorter(str, str2);
		String othr = abr.equals(str) ? str2 : str;
		if (!isAbbreviation(abr))
			return false;
		othr = othr.toUpperCase();
		abr = abr.toUpperCase();
		if (othr.length() == 0 || abr.length() == 0)
			return false;
		if (othr.charAt(0) != abr.charAt(0))
			return false;
		for (int i = 0; i < abr.length(); i++) {
			if (abr.charAt(i) == '.')
				continue;
			if (othr.indexOf(abr.charAt(i)) == -1)
				return false;
			othr = othr.substring(othr.indexOf(abr.charAt(i)));
		}
		return true;
	}
	public static boolean isAbbreviation(String str) {
		return str.equals(str.toUpperCase()) || str.indexOf(".") != -1;
	}
	
	//Shorter/Longer
	public static String getShorter(String str1, String str2) {
		if (str1.length() < str2.length())
			return str1;
		return str2;
	}
	public static String getLonger(String str1, String str2) {
		if (str1.length() > str2.length())
			return str1;
		return str2;
	}
	
	//Same Name
	public static boolean isSameName(String string1, String string2) {
		String[] words1 = string1.replaceAll("[()]", "").split(" ");
		String[] words2 = string2.replaceAll("[()]", "").split(" ");
		
		if (words1.length != words2.length)
			return false;
		
		//match each word
		for (int i = 0; i < words1.length; i++) {
			if (!(difference(words1[i], words2[i]) < 2 || isAbbreviationOf(words1[i], words2[i])))
				return false;
		}
		
		return true;
	}
	
	//Find Element
	public static String findElement(String str, String par) {
		if (str.indexOf(par) != -1) {
			int start = str.indexOf(par + ":") + par.length() + 1;
			int end = str.indexOf(":", start);
			
			if (end == -1) {
				return str.substring(start);
			}
			else {
				str = str.substring(start, end);
				return str.substring(0, str.lastIndexOf(" "));
			}
		}
		return null;
	}
	public static int findElementInt(String str, String par) {
		return parseIntSafe(findElement(str, par), -1);
	}
	public static double findElementDouble(String str, String par) {
		return parseDoubleSafe(findElement(str, par), -1);
	}
}