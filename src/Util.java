
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
	public static String inside(String str, String start, String end) {
		try {
			return str.substring(str.indexOf(start) + start.length(), str.indexOf(end));
		} catch (Exception e) { return ""; }
	}
	
	public static String insideTag(String str) {
		try {
			return str.substring(str.indexOf(">") + 1, str.lastIndexOf("<"));
		} catch (Exception e) { return ""; }
	}
	
	//Repeat and Pad
	public static String repeat(String str, int times) {
		String returnString = "";
		for (int i = 0; i < times; i++)
			returnString += str;
		return returnString;
	}
	public static String pad(String str, int toSize) {
		return repeat(" ", (int) Math.ceil((toSize - str.length()) / 2.0)) + str + repeat(" ", (int) Math.floor((toSize - str.length()) / 2.0));
	}
	
	//Min, max, and clamp
	public static int min(int num, int min) {
		return num < min ? min : num;
	}
	public static int max(int num, int max) {
		return num > max ? max : num;
	}
	public static int clamp(int num, int min, int max) {
		return max(min(num, min), max);
	}
}