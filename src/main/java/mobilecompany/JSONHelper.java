package mobilecompany;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that contains some auxiliary methods for working with JSON
 * which were excluded from the main class to make the code less complicated.
 */
public class JSONHelper {

    private static JSONHelper helper = new JSONHelper();

    /**
     * A pattern for a valid JSON string.
     */
    private String jsonPattern = "\\{\\s*(\"\\w+\"\\s*:\\s*"
    	+ "(\"[^\"]*\"|\\d+\\.?\\d*)\\s*,\\s*)*" +
            "\"\\w+\"\\s*:\\s*(\"[^\"]*\"|\\d+\\.?\\d*)\\s*\\}";

    private JSONHelper() {}
    
    /**
     * Gets the single instance of JSONHelper.
     *
     * @return single instance of JSONHelper
     */
    public static JSONHelper getInstance() {
	return helper;
    }

    /**
     * Check if a given string is a valid JSON string.
     * @param str string to be checked.
     * @return true if {@code str} is a valid JSON string.
     */
    public boolean isJSONEntry(String str) {
        return str.matches(jsonPattern);
    }

    /**
     * A matcher for looking up JSON objects within a string.
     * @param str a string that may contain JSON objects.
     * @return matcher for looking up JSON objects in {@code str}.
     */
    public Matcher getJSONMatcher(String str) {
        Pattern pattern = Pattern.compile(jsonPattern);
        return pattern.matcher(str);
    }
}
