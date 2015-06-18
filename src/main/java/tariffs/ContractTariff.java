package tariffs;

/**
 * A {@code Tariff} subclass that represents corporate tariffs. It contains
 * additional fields 'dailyMinutes' and 'abroadCalls' and their
 * getters/setters.
 */
public class ContractTariff extends Tariff {

    /** a number of free minutes per day */
    protected int dailyMinutes;

    /** rate of calls to foreign countries */
    protected double abroadCalls;

    /**
     * Instantiates a new contract tariff.
     *
     * @param builder the builder
     */
    protected ContractTariff(Builder builder) {
        super(builder);
        this.dailyMinutes = builder.dailyMinutes;
        this.abroadCalls = builder.abroadCalls;
    }

    /**
     * Gets the number of daily minutes.
     *
     * @return the number of daily minutes
     */
    public int getDailyMinutes() {
        return dailyMinutes;
    }

    /**
     * Sets the number of daily minutes.
     *
     * @param dailyMinutes the new number of daily minutes
     */
    public void setDailyMinutes(int dailyMinutes) {
        this.dailyMinutes = dailyMinutes;
    }

    /**
     * Gets the abroad calls rate.
     *
     * @return the abroad calls rate
     */
    public double getAbroadCalls() {
        return abroadCalls;
    }

    /**
     * Sets the abroad calls rate.
     *
     * @param abroadCalls the new abroad calls rate 
     */
    public void setAbroadCalls(double abroadCalls) {
        this.abroadCalls = abroadCalls;
    }

    /* (non-Javadoc)
     * @see tariffs.Tariff#toString()
     */
    @Override
    public String toString() {
        return super.toString()
                + "abroadCalls: " + abroadCalls + "\n"
                + "dailyMinutes: " + dailyMinutes + "\n";
    }

    /* (non-Javadoc)
     * @see tariffs.Tariff#hashCode()
     */
    @Override
    public int hashCode() {
	return super.hashCode();
    }

    /* (non-Javadoc)
     * @see tariffs.Tariff#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object that) {
        if (that == this) {
            return true;
        }
        
        if ((that == null) || (that.getClass() != this.getClass())) {
            return false;
        }
        
        ContractTariff other = (ContractTariff) that;
        return super.equals((Tariff) other)
                && (this.abroadCalls == other.abroadCalls)
                && (this.dailyMinutes == other.dailyMinutes);
    }

    /**
     * A builder class for contract tariffs.
     */
    public static class Builder extends Tariff.Builder<Builder> {
        private int dailyMinutes;
        private double abroadCalls;

        public Builder() {}
    
        public Builder abroadCalls(double abroadCalls) {
            this.abroadCalls = abroadCalls;
            return this;
        }
    
        public Builder dailyMinutes(int dailyMinutes) {
            this.dailyMinutes = dailyMinutes;
            return this;
        }
    
        public Tariff build() {
            return new ContractTariff(this);
        }
    }
}
