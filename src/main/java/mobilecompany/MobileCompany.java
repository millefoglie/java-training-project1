package mobilecompany;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tariffs.ContractTariff;
import tariffs.PrepaidTariff;
import tariffs.Tariff;

/**
 * A class representing a mobile company. The current functionality allows to
 * read clients and tariffs entries from respective files, which replace a db,
 * and filter tariffs using some predicates. It also provides a command-line
 * interface to work with tariffs.
 */
public class MobileCompany {

    /** The logger. */
    private static final Logger LOGGER = LogManager.getLogger();
    
    /** The mobile company name. */
    private String name;
    
    /** The set of mobile company clients. */ 
    private Set<Client> clients;
    
    /** The list of mobile company tariffs. */
    private List<Tariff> tariffs;
    
    /** The file that contains clients information. */
    private File clientsFile;
    
    /** The file that contains tariffs information. */
    private File tariffsFile;

    /**
     * Instantiates a new mobile company.
     *
     * @param name the name
     */
    public MobileCompany(String name) {
        this.name = name;
        clients = new HashSet<>();
        tariffs = new ArrayList<>();
        clientsFile = new File("");
        tariffsFile = new File("");
    }

    /**
     * Gets the mobile company name.
     *
     * @return the mobile company name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the mobile company name.
     *
     * @param name the new mobile company name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the clients file.
     *
     * @return the clients file
     */
    public File getClientsFile() {
        return clientsFile;
    }

    /**
     * Sets the clients file.
     *
     * @param clientsFile the new clients file
     */
    public void setClientsFile(File clientsFile) {
        this.clientsFile = clientsFile;
    }
    
    /**
     * Sets the clients file.
     *
     * @param pathToClientsFile the path to the clients file
     */
    public void setClientsFile(String pathToClientsFile) {
        this.clientsFile = new File(pathToClientsFile);
    }

    /**
     * Gets the tariffs file.
     *
     * @return the tariffs file
     */
    public File getTariffsFile() {
        return tariffsFile;
    }

    /**
     * Sets the tariffs file.
     *
     * @param tariffsFile the new tariffs file
     */
    public void setTariffsFile(File tariffsFile) {
        this.tariffsFile = tariffsFile;
    }
    
    /**
     * Sets the tariffs file.
     *
     * @param pathToTariffsFile the path to the tariffs file
     */
    public void setTariffsFile(String pathToTariffsFile) {
        this.tariffsFile = new File(pathToTariffsFile);
    }

    /**
     * Load all clients from a plain text file.
     * The file should have UTF-8 encoding. Clients data should be stored as
     * JSON objects. Currently, each JSON string has to take one line, i. e.
     * no line breaks are allowed within one entry, allowing for simpler code.
     */
    public void loadClients() {
	if (!clientsFile.exists()) {
	    LOGGER.error("Could not load clients: no file.");
	    return;
	}
	
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                	new FileInputStream(clientsFile), "UTF-8"))) {
            JSONHelper h = JSONHelper.getInstance();
            
            br.lines().forEach(s -> {
                if (h.isJSONEntry(s)) {
                    org.json.JSONObject obj = new JSONObject(s);
                    String clientName = obj.getString("name");
                    String clientSurname = obj.getString("surname");
                    int id = obj.getInt("id");
                    int tariffId = obj.getInt("tariff_id");

                    clients.add(new Client(
                	    clientName, clientSurname, id, tariffId));
                }
            });
        } catch (FileNotFoundException e) {
            LOGGER.error("Could not find clients db file.");
            LOGGER.error(e);
        } catch (IOException e) {
            LOGGER.error("Could not read from clients db file.");
            LOGGER.error(e);
        }
    }

    /**
     * Load all tariffs from a plain text file.
     * The file should have UTF-8 encoding. Tariffs data must be stored as
     * JSON objects. Line breaks are allowed in the file within each entry.
     */
    public void loadTariffs() {
	if (!clientsFile.exists()) {
	    LOGGER.error("Could not load tariffs: no file.");
	    return;
	}
	
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                	new FileInputStream(tariffsFile), "UTF-8"))) {
            JSONHelper h = JSONHelper.getInstance();
            StringBuilder sb = new StringBuilder();
            String str;
            Matcher matcher;
            Tariff t = null;

            // load the whole file in memory
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }

            // look up JSON strings in the loaded file
            matcher = h.getJSONMatcher(sb.toString());

            while (matcher.find()) {
                t = parseTariffFromJSON(
                        new JSONObject(matcher.group()));
                
                if (t != null) {
                    tariffs.add(t);
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("Could not find tariffs db file.");
            LOGGER.error(e);
        } catch (IOException e) {
            LOGGER.error("Could not read from tariffs db file.");
            LOGGER.error(e);
        }
    }

    /**
     * An auxiliary function that converts a tariff JSON object to a
     * respective {@code Tariff} object.
     * @param obj JSON object to be parsed.
     * @return {@code Tariff} object parsed from {@code obj}.
     */
    private Tariff parseTariffFromJSON(JSONObject obj) {
        Tariff t = null;
        Tariff.Builder<?> tb = null;

        // set up field specific for each Tariff subclass
        switch (obj.getString("type")) {
        case "prepaid":
            tb = new PrepaidTariff.Builder()
                    .activation(obj.getDouble("activation"));
            break;
        case "contract":
            tb = new ContractTariff.Builder()
                    .abroadCalls(obj.getDouble("abroadCalls"))
                    .dailyMinutes(obj.getInt("dailyMinutes"));
            break;
        default:
            LOGGER.warn("Could not parse tariffs correctly.\n"
                    + "Illegal tariff type: " + obj.getString("type"));
            return null;
        }

        // set up fields shared by each Tariff class/subclass
        t = tb.id(obj.getInt("id"))
                .name(obj.getString("name"))
                .monthly(obj.getDouble("monthly"))
                .innerCalls(obj.getDouble("innerCalls"))
                .outerCalls(obj.getDouble("outerCalls"))
                .landlines(obj.getDouble("landlines"))
                .sms(obj.getDouble("sms"))
                .internet(obj.getDouble("internet"))
                .smsPackage(obj.getInt("smsPackage"))
                .dataPackage(obj.getInt("dataPackage"))
                .build();
        return t;
    }

    /**
     * Count clients.
     *
     * @return the number of mobile company clients
     */
    public int countClients() {
        return clients.size();
    }

    /**
     * Count tariffs.
     *
     * @return the number of mobile company tariffs
     */
    public int countTariffs() {
        return tariffs.size();
    }

    /**
     * Sort tariffs by monthly rates.
     */
    public void sortTariffsByMonthly() {
        Collections.sort(tariffs, (Tariff a, Tariff b) 
        	-> Double.compare(a.getMonthly(), b.getMonthly()));
    }
    
    /**
     * Filter all available tariffs using a predicate. 
     * @param predicate a predicate to be used for filtering
     * @return a new list of filtered tariffs
     */
    public List<Tariff> filterTariffs(Predicate<? super Tariff> predicate) {
	return filterTariffs(tariffs, predicate);
    }
    
    /**
     * Filter a list of tariffs using a predicate.
     * @param coll a collection of tariffs
     * @param predicate a predicate to be used for filtering
     * @return a new list of filtered tariffs 
     */
    public List<Tariff> filterTariffs(Collection<? extends Tariff> coll,
	    Predicate<? super Tariff> predicate) {
	return coll.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
    
    /**
     * Set up and start the console for working with the mobile company.
     */
    public void startConsole() {
        ConsoleHelper h = ConsoleHelper.getInstance();
        Scanner scanner = new Scanner(System.in, "UTF-8");
        
        List<Tariff> queryResult = new ArrayList<>(tariffs);

        String input;
        String filter;
        double min;
        double max;

        // initialise available commands
        h.initFilterCommands();
        
        System.out.println(name);
        System.out.println("Number of clients: " + countClients());
        System.out.println("Number of tariffs: " + countTariffs());
        System.out.println("\n");
        System.out.println("Please, type 'help' to get the console help");
        System.out.println("\n");
        
        while (true) {
            System.out.print(">> ");
            input = scanner.nextLine();

            // exit the console (exit)
            if (input.matches("\\s*exit\\s*")) {
                break;
            }

            // show the filtered tariffs list (ls)
            if (input.matches("\\s*ls\\s*")) {
                if (queryResult.isEmpty()) {
                    System.out.println("Nothing to output.");
                } else {
		    queryResult.stream().forEach(System.out::println);
                    System.out.println("Found " + queryResult.size() 
                	    + " tariffs");
                }
                
                continue;
            }

            // show all of the tariffs (ls all)
            if (input.matches("\\s*ls\\s+all\\s*")) {
		tariffs.stream().forEach(System.out::println);
                continue;
            }

            // show help on console commands (help)
            if (input.matches("\\s*help\\s*")) {
                h.showHelp();
                continue;
            }

            // clear the tariff list filters (clear)
            if (input.matches("\\s*clear\\s*")) {
                queryResult.clear();
                queryResult.addAll(tariffs);
                continue;
            }

            // filter tariffs
            if (h.checkCommand(input)) {
        	filter = h.extractCommand(input);
                min = h.extractMin(input);
                max = h.extractMax(input);

                queryResult = filterTariffs(queryResult,
                	h.getPredicate(filter, min, max)); 
                continue;
            }
            
            // if nothing fits
            System.out.println("Invalid command");
            System.out.println("Enter 'help' to get a list of "
            	+ "available commands");
        }
        
        scanner.close();
    }
}
