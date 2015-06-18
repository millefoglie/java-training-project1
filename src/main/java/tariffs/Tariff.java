package tariffs;

/** A generic tariff class. It contains a number of fields that represent
 * rates for specific services, respective getters and setters and the
 * builder class used for more convenient initialisation of tariffs.
 */
public class Tariff{

    /** The tariff id. */
    protected final int id;
    
    /** The tariff name. */
    protected String name;

    /** The monthly pay rate. */
    protected double monthly;

    /** The rate for calls within the mobile company network. */
    protected double innerCalls;

    /** The rate for calls beyond the mobile company network. */
    protected double outerCalls;

    /** The rate for calls to landlines. */
    protected double landlines;

    /** The rate for sending an sms. */
    protected double sms;

    /** The rate for internet usage, e. g. for 10 KB */
    protected double internet;

    /** The number of free sms per month. */
    protected int smsPackage;

    /** The number of free MB per month. */
    protected int dataPackage;

    /**
     * Instantiates a new tariff.
     *
     * @param builder the builder
     */
    protected Tariff(Builder<? extends Builder<?>> builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.monthly = builder.monthly;
        this.innerCalls = builder.innerCalls;
        this.outerCalls = builder.outerCalls;
        this.landlines = builder.landlines;
        this.sms = builder.sms;
        this.internet = builder.internet;
        this.smsPackage = builder.smsPackage;
        this.dataPackage = builder.dataPackage;
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
     * Gets the monthly rate.
     *
     * @return the monthly rate
     */
    public double getMonthly() {
        return monthly;
    }

    /**
     * Sets the monthly rate.
     *
     * @param monthly the new monthly rate
     */
    public void setMonthly(double monthly) {
        this.monthly = monthly;
    }

    /**
     * Gets the inner calls rate.
     *
     * @return the inner calls rate
     */
    public double getInnerCalls() {
        return innerCalls;
    }

    /**
     * Sets the inner calls rate.
     *
     * @param innerCalls the new inner calls rate
     */
    public void setInnerCalls(double innerCalls) {
        this.innerCalls = innerCalls;
    }

    /**
     * Gets the outer calls rate.
     *
     * @return the outer calls rate
     */
    public double getOuterCalls() {
        return outerCalls;
    }

    /**
     * Sets the outer calls rate.
     *
     * @param outerCalls the new outer calls rate
     */
    public void setOuterCalls(double outerCalls) {
        this.outerCalls = outerCalls;
    }

    /**
     * Gets the landlines rate.
     *
     * @return the landlines rate
     */
    public double getLandlines() {
        return landlines;
    }

    /**
     * Sets the landlines rate.
     *
     * @param landlines the new landlines rate
     */
    public void setLandlines(double landlines) {
        this.landlines = landlines;
    }

    /**
     * Gets the sms rate.
     *
     * @return the sms rate
     */
    public double getSms() {
        return sms;
    }

    /**
     * Sets the sms rate.
     *
     * @param sms the new sms rate
     */
    public void setSms(double sms) {
        this.sms = sms;
    }

    /**
     * Gets the internet rate.
     *
     * @return the internet rate
     */
    public double getInternet() {
        return internet;
    }

    /**
     * Sets the internet rate.
     *
     * @param internet the new internet rate
     */
    public void setInternet(double internet) {
        this.internet = internet;
    }

    /**
     * Gets the size of the sms package.
     *
     * @return the size of the sms package
     */
    public int getSmsPackage() {
        return smsPackage;
    }

    /**
     * Sets the size of the sms package.
     *
     * @param smsPackage the new size of the sms package
     */
    public void setSmsPackage(int smsPackage) {
        this.smsPackage = smsPackage;
    }

    /**
     * Gets the data package size.
     *
     * @return the data package size
     */
    public int getDataPackage() {
        return dataPackage;
    }

    /**
     * Sets the data package size.
     *
     * @param dataPackage the new data package size
     */
    public void setDataPackage(int dataPackage) {
        this.dataPackage = dataPackage;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return name + " (id: " + id + ")\n"
                + "monthly: " + monthly + "\n"
                + "inner calls: " + innerCalls + "\n"
                + "outer calls: " + outerCalls + "\n"
                + "landlines: " + landlines + "\n"
                + "sms: " + sms + "\n"
                + "internet: " + internet + "\n"
                + "sms package: " + smsPackage + "\n"
                + "data package: " + dataPackage + "\n";
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
        
        Tariff other = (Tariff) that;
        
        return (this.id == other.id)
        	&& (this.name.equals(other.name))
        	&& (this.monthly == other.monthly)
        	&& (this.innerCalls == other.innerCalls)
        	&& (this.outerCalls == other.outerCalls)
        	&& (this.landlines == other.landlines)
        	&& (this.sms == other.sms)
        	&& (this.internet == other.internet)
        	&& (this.smsPackage == other.smsPackage)
        	&& (this.dataPackage == other.dataPackage);
    }

    /**
     * A tariff builder class. It is used for more convenient intitialisation of
     * tariffs. 
     *
     * @param <T> some class that extends {@code Builder}. It is used to enable
     * proper inheritance of this class by {@code Tariff} subclasses' builders.
     */
    @SuppressWarnings("unchecked")
    public static class Builder<T extends Builder<?>> {
        
        /** The id. */
        private int id;
        
        /** The name. */
        private String name;
        
        /** The monthly. */
        private double monthly;
        
        /** The inner calls. */
        private double innerCalls;
        
        /** The outer calls. */
        private double outerCalls;
        
        /** The landlines. */
        private double landlines;
        
        /** The sms. */
        private double sms;
        
        /** The internet. */
        private double internet;
        
        /** The sms package. */
        private int smsPackage;
        
        /** The data package. */
        private int dataPackage;

        /**
     * Instantiates a new builder.
     */
        public Builder() {}

	public T id(int id) {
            this.id = id;
            return (T) this;
        }

        public T name(String name) {
            this.name = name;
            return (T) this;
        }

        public T monthly(double monthly) {
            this.monthly = monthly;
            return (T) this;
        }

        public T innerCalls(double innerCalls) {
            this.innerCalls = innerCalls;
            return (T) this;
        }

        public T outerCalls(double outerCalls) {
            this.outerCalls = outerCalls;
            return (T) this;
        }

        public T landlines(double landlines) {
            this.landlines = landlines;
            return (T) this;
        }

        public T sms(double sms) {
            this.sms = sms;
            return (T) this;
        }

        public T internet(double internet) {
            this.internet = internet;
            return (T) this;
        }

        public T smsPackage(int smsPackage) {
            this.smsPackage = smsPackage;
            return (T) this;
        }

        public T dataPackage(int dataPackage) {
            this.dataPackage = dataPackage;
            return (T) this;
        }

        public Tariff build() {
            return new Tariff((T) this);
        }
    }
}
