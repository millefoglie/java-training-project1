package mobilecompany;

/**
 * A demo class. 
 *
 */
public class MobileCompanyDemo {
    
    private MobileCompanyDemo() {}

    public static void main(String[] args) {
        MobileCompany company = new MobileCompany("Blau.de");
        
        company.setClientsFile("data/clients.txt");
        company.setTariffsFile("data/tariffs.txt");
        
        company.loadClients();
        company.loadTariffs();
        company.sortTariffsByMonthly();
        company.startConsole();
    }
}
