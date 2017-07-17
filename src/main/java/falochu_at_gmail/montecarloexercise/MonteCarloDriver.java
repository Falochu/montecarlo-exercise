package falochu_at_gmail.montecarloexercise;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Main class for PersonalCapital coding exercise.
 * @author ryank
 */
public class MonteCarloDriver {

    private static final BigDecimal INITIAL_PORTFOLIO_VALUE = new BigDecimal("100000");
    private static final int NUM_YEARS = 20;
    private static final int NUM_SIMULATIONS = 10000;
    private static final BigDecimal ANNUAL_INFLATION = new BigDecimal("0.035");

    public static void main(String[] args) {
        Random r = new Random();
        BigDecimal[] values = new BigDecimal[NUM_SIMULATIONS];

        for (int i = 0; i < NUM_SIMULATIONS; i++) {
            values[i] = runSimulation(r::nextGaussian, Constants.AGGRESSIVE_RETURN, Constants.AGGRESSIVE_RISK);
        }
        System.out.println("\"Aggressive\" portfolio");
        System.out.println(getResults(values));

        values = new BigDecimal[NUM_SIMULATIONS];
        for (int i = 0; i < NUM_SIMULATIONS; i++) {
            values[i] = runSimulation(r::nextGaussian, Constants.VERY_CONSERVATIVE_RETURN,
                    Constants.VERY_CONSERVATIVE_RISK);
        }
        System.out.println("\n\"Very Convervative\" portfolio");
        System.out.println(getResults(values));
    }

    /**
     * Use random numbers to reach a possible FV (inflation-adjusted).
     * 
     * @param randomSupplier
     *            function producing a new random on a normal distribution when called
     * @param historicalReturn
     *            median annual return throughout the history of this fund
     * @param historicalRisk
     *            standard deviation throughout the history of this fund
     * @return value after NUM_YEARS
     */
    public static BigDecimal runSimulation(Supplier<Double> randomSupplier, double historicalReturn,
            double historicalRisk) {
        BigDecimal value = INITIAL_PORTFOLIO_VALUE;
        for (int i = 1; i <= NUM_YEARS; i++) {
            double annualReturn = (randomSupplier.get() * historicalRisk + historicalReturn) / 100;
            value = value.multiply(new BigDecimal(1 + annualReturn));
        }
        // adjust for inflation
        value = value.divide((ANNUAL_INFLATION.add(BigDecimal.ONE)).pow(NUM_YEARS), RoundingMode.HALF_EVEN);

        return value;
    }

    /**
     * Calculates and returns the 10th, 50th (median) and 90th percentile of the given data.
     * 
     * @param values
     *            list to partition
     * @return simple POJO wrapper around the three values
     */
    public static Results getResults(BigDecimal[] values) {
        Arrays.sort(values);
        Results results = new Results();
        int len = values.length;
        if (len % 2 == 0) {
            results.setMedian(getNthPercentile(values, Constants.MEDIAN));
            results.setTenthPercentile(getNthPercentile(values, Constants.TENTH_PERCENTILE));
            results.setNinetiethPercentile(getNthPercentile(values, Constants.NINETIETH_PERCENTILE));
        }
        return results;
    }

    /**
     * Find the nth percentile out of a sorted list of numbers; where the requested percentile falls between elements,
     * this method interpolates to give a value that is between the two.
     * 
     * @param sorted
     *            Precondition: assumed to be in sorted order
     * @param n
     *            percentile to return from the given list, as a decimal (i.e. for 10%, pass 0.1)
     * @return
     */
    public static BigDecimal getNthPercentile(BigDecimal[] sorted, double n) {
        if (n > 1.0 || n < 0.01) {
            throw new IllegalArgumentException(n * 100 + "th Percentile is undefined");
        }
        double index = (sorted.length + 1) * n - 1;
        // There is no lower value to interpolate with, so must use the lowest value
        if (index <= 0) {
            return sorted[0];
        }
        // There is no higher value to interpolate with, so must use the highest value
        else if (index >= sorted.length - 1) {
            return sorted[sorted.length - 1];
        }

        int whole = (int) index;
        double fractional = index - whole;
        if (fractional == 0.0) {
            return sorted[whole];
        }

        // Interpolate two nearest positions
        return sorted[whole + 1].subtract(sorted[whole]).multiply(new BigDecimal(fractional)).add(sorted[whole]);
    }
}
