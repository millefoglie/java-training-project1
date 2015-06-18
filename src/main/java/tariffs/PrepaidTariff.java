package tariffs;

/**
 * A {@code Tariff} subclass that represents prepaid mobile tariffs. It contains
 * an additional field 'activation' and its getters/setters.
 */
public class PrepaidTariff extends Tariff {

    /** price of a start package activation */
    protected double activation;

    /**
     * Instantiates a new prepaid tariff.
     *
     * @param builder the builder
     */
    protected PrepaidTariff(Builder builder) {
        super(builder);
        this.activation = builder.activation;
    }

    /**
     * Gets the activation rate.
     *
     * @return the activation rate
     */
    public double getActivation() {
        return activation;
    }

    /**
     * Sets the activation rate.
     *
     * @param activation the new activation rate
     */
    public void setActivation(double activation) {
        this.activation = activation;
    }

    /* (non-Javadoc)
     * @see tariffs.Tariff#toString()
     */
    @Override
    public String toString() {
        return super.toString() + "activation: " + activation + "\n";
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
        
        PrepaidTariff other = (PrepaidTariff) that;
        
        return super.equals((Tariff) other)
                && (this.activation == other.activation);
    }

    /**
     * A builder class for prepaid tariffs.
     * @author orange
     *
     */
    public static class Builder extends Tariff.Builder<Builder> {

        private double activation;

        public Builder() {}

        public Builder activation(double activation) {
            this.activation = activation;
            return this;
        }

        public Tariff build() {
            return new PrepaidTariff(this);
        }
    }
}
