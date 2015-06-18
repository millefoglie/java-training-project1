package mobilecompany;

/**
 * A class for mobile company clients.
 * @author orange
 *
 */
class Client {

    /** The client's name. */
    private String name;
    
    /** The client's surname. */
    private String surname;
    
    /** The id. */
    private final int id;
    
    /** The tariff id. */
    private int tariffId;

    /**
     * Instantiates a new client.
     *
     * @param name the name
     * @param surname the surname
     * @param id the id
     * @param tariffId the tariff id
     */
    public Client(String name, String surname, int id, int tariffId) {
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.tariffId = tariffId;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname.
     *
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname.
     *
     * @param surname the new surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the tariff id.
     *
     * @return the tariff id
     */
    public int getTariffId() {
        return tariffId;
    }

    /**
     * Sets the tariff id.
     *
     * @param tariffId the new tariff id
     */
    public void setTariffId(int tariffId) {
        this.tariffId = tariffId;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return id + ": " + name + " " + surname;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return id;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object that) {
        if (that == this) {
            return true;
        }
        
        if ((that == null) || (that.getClass() != this.getClass())) {
            return false;
        }
        
        Client other = (Client) that;
        
        return this.name.equals(other.name)
                && (this.surname.equals(other.surname))
                && (this.tariffId == other.tariffId);
    }

}
