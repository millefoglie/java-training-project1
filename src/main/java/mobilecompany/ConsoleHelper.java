package mobilecompany;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import tariffs.ContractTariff;
import tariffs.PrepaidTariff;
import tariffs.Tariff;

/**
 * A class that contains auxiliary methods for working with console of a
 * mobile company instance.
 * @author orange
 *
 */
public class ConsoleHelper {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    private static ConsoleHelper helper = new ConsoleHelper();
    
    /**
     * Commands which can be used to filter tariffs list. Basically,
     * these are the names of {@code Tariff} and its subclasses' fields.
     */
    private List<String> filterCommands;

    /**
     * The pattern to validate correct filter syntax.
     * E. g.: monthly 15.5 100
     */
    private String filterCmdPattern = "\\s*([a-zA-Z]+)\\s+([-\\+]?\\d*\\.?\\d+)"
            + "\\s+([-\\+]?\\d*.?\\d+)\\s*";
    
    /**
     * The pattern to match getter methods names
     */
    private static String getterPattern = "get([A-Z])([a-zA-Z]+)";
    
    
    private ConsoleHelper() {}
    
    /**
     * Gets the single instance of ConsoleHelper.
     *
     * @return single instance of ConsoleHelper
     */
    public static ConsoleHelper getInstance() {
	return helper;
    }

    /**
     * Initialise the list of console commands which can be used to filter
     * the tariffs list. Basically, get a list of all fields in {@code
     * Tariffs} class and its subclasses.
     */
    public void initFilterCommands() {
        filterCommands = new ArrayList<>();
        String cmd;

        for (Method m : Tariff.class.getDeclaredMethods()) {
            cmd = getterToCmd(m.getName());
            if (cmd.length() > 1) {
                filterCommands.add(cmd);
            }
        }
        
        for (Method m : PrepaidTariff.class.getDeclaredMethods()) {
            cmd = getterToCmd(m.getName());
            if (cmd.length() > 1) {
                filterCommands.add(cmd);
            }
        }
        
        for (Method m : ContractTariff.class.getDeclaredMethods()) {
            cmd = getterToCmd(m.getName());
            if (cmd.length() > 1) {
                filterCommands.add(cmd);
            }
        }
    }

    /**
     * Check if a given string is a valid filter command.
     * @param str a string to be a filter command with min max values
     * @return true if {@code str} is a valid filter command
     */
    public boolean checkCommand(String str) {
        if (!str.matches(filterCmdPattern)) {
            return false;
        }

        String cmd = extractCommand(str);
        return filterCommands.contains(cmd);
    }

    /**
     * Extract the command name from the filter command string.
     * <br>
     * E. g.: sms 10 20  --&gt;  sms.
     * <br>
     * The command name is some field name of {@code Tariff} or its subclass.
     * @param str a filter command string.
     * @return the command name.
     */
    public String extractCommand(String str) {
	
        // assert checkCommand is true
        return str.replaceAll(filterCmdPattern, "$1");
    }

    /**
     * Extract the lower boundary from the filter command string.
     * @param str a filter command string.
     * @return the lower boundary for the corresponding field.
     */
    public double extractMin(String str) {
	
        // assert checkCommand is true
        return Double.parseDouble(str.replaceAll(filterCmdPattern, "$2"));
    }

    /**
     * Extract the upper boundary from the filter command string.
     * @param str a filter command string.
     * @return the upper boundary for the field.
     */
    public double extractMax(String str) {
	
        // assert checkCommand is true
        return Double.parseDouble(str.replaceAll(filterCmdPattern, "$3"));
    }

    /**
     * Convert a field name (a filter command) to its getter name.
     * @param cmd a field name (a filter command).
     * @return the getter name.
     */
    public String cmdToGetter(String cmd) {
        if (cmd.length() == 0) {
            return "";
        }
        
        return "get" + cmd.substring(0,1).toUpperCase() + cmd.substring(1);
    }

    /**
     * Convert a getter name to the respective field name (the filter command).
     * @param getter a getter name.
     * @return the field name (the filter command).
     */
    public String getterToCmd(String getter) {
        if ((getter.length() == 0) 
        	|| !getter.matches(getterPattern)) {
            return "";
        }

        String firstLetter = getter.replaceAll(getterPattern, "$1")
                                   .toLowerCase();
        
        return getter.replaceAll(getterPattern, firstLetter + "$2");
    }

    /**
     * Show help on the supported console commands.
     */
    public void showHelp() {
	String shortFormat = "%16s -- %-20s %n";
	String longFormat = "%16s -- %-40s %n";
        System.out.printf(shortFormat, "ls", "list matched tariffs");
        System.out.printf(shortFormat, "ls all", "list all tariffs");
        System.out.printf(shortFormat, "clear", "reset filters");
        System.out.printf(shortFormat, "exit", "exit the program");
        System.out.printf(longFormat, "field min max",
                          "filter tariffs with field having"
                                  + "value between min and max");
        System.out.println("\nList of available fields:\n");
        filterCommands.stream().forEach(System.out::println);
    }
    
    /**
     * Construct a predicate to filter out tariffs.
     * A field name should have values in min..max inclusively.
     * @param cmd a field name (a filter command).
     * @param min the lower boundary for the field.
     * @param max the upper boundary for the field.
     * @return a predicate for filtering.
     */
    public Predicate<Tariff> getPredicate(
            String cmd, double min, double max) {
        return new FilterPredicate(cmd, min, max);
    }
    
    
    /**
     * A class for filter predicates used to filter tariffs.
     */
    private static class FilterPredicate implements Predicate<Tariff> {
	
	/** command name ({@code Tariff} field name) */
	private String cmd;
	
	/** Lower boundary for the respective {@code Tariff} field (inclusive)*/
	private double min;
	
	/** Upper boundary for the respective {@code Tariff} field (inclusive)*/
	private double max;
	

	/**
	 * Instantiates a new filter predicate.
	 *
	 * @param cmd command name ({@code Tariff} field name)
	 * @param min lower boundary for the field values (inclusive)
	 * @param max upper boundary for the field values (inclusive)
	 */
	public FilterPredicate(String cmd, double min, double max) {
	    this.cmd = cmd;
	    this.min = min;
	    this.max = max;
	}

	/* (non-Javadoc)
	 * @see java.util.function.Predicate#test(java.lang.Object)
	 */
	@Override
	public boolean test(Tariff t) {
	    ConsoleHelper h = ConsoleHelper.getInstance();
	    String methodName = h.cmdToGetter(cmd);
	    Method method = null;
	    double val = 0;

	    // check if there is such a method for current tariff object
	    boolean isMethodExists = Arrays
		    .stream(t.getClass().getMethods())
		    .map(m -> m.getName())
		    .anyMatch(methodName::equals);

	    /*
	     * if no such method is found, we have a wrong class and
	     * the tariff should not be included in the final query result
	     */
	    if (!isMethodExists) {
		return false;
	    }

	    // if such a method is found, we proceed
	    try {
		method = t.getClass().getMethod(methodName);
	    } catch (NoSuchMethodException | SecurityException e) {
		LOGGER.error(e);
		return false;
	    }

	    /*
	     * method can return Double or Integer, so we need to provide
	     * specific casting for each case
	     */
	    try {
		switch (method.getReturnType().getName()) {
		case "double":
		    val = (double) method.invoke(t);
		    break;
		case "int":
		    val = (int) method.invoke(t);
		    break;
		default:
		    String msg = "Could not invoke method " 
			    + method.getReturnType().getName() 
			    + method.getName();
		    LOGGER.error(msg);
		}
	    } catch (IllegalAccessException | IllegalArgumentException
		    | InvocationTargetException e) {
		LOGGER.error(e);
		return false;
	    }

	    return (val >= min) && (val <= max);
	}
    }
}
