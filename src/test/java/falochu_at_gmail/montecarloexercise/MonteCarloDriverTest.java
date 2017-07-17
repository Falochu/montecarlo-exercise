package falochu_at_gmail.montecarloexercise;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Supplier;

import org.junit.Test;

public class MonteCarloDriverTest {

    /** Test for {@link MonteCarloDriver#runSimulation(Supplier, double, double)}. */
    @Test
    public void testRunSimulation() {
        Supplier<Double> notRandom = () -> 1.0;
        assertEquals(new BigDecimal("4434584.93"),
                MonteCarloDriver.runSimulation(notRandom, Constants.AGGRESSIVE_RETURN, Constants.AGGRESSIVE_RISK)
                        .setScale(2, RoundingMode.HALF_EVEN));
    }

    /** Test for {@link MonteCarloDriver#getNthPercentile(double[], double)}. */
    @Test
    public void testGetNthPercentile() {
        BigDecimal[] sorted = new BigDecimal[] { new BigDecimal(1.0), new BigDecimal(2.0), new BigDecimal(3.0) };
        assertEquals(new BigDecimal(1.0), MonteCarloDriver.getNthPercentile(sorted, 0.1));
        assertEquals(new BigDecimal(2.0), MonteCarloDriver.getNthPercentile(sorted, 0.5));
        assertEquals(new BigDecimal(3.0), MonteCarloDriver.getNthPercentile(sorted, 0.9));

        sorted = new BigDecimal[] { new BigDecimal(5.0), new BigDecimal(6.0) };
        assertEquals(new BigDecimal(5.5), MonteCarloDriver.getNthPercentile(sorted, 0.5));
    }
}
