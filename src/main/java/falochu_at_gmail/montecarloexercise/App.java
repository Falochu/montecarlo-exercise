package falochu_at_gmail.montecarloexercise;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * Hello world!
 *
 */
public class App {

    private static final double INITIAL_PORTFOLIO_VALUE = 100000.0;
    private static final int NUM_YEARS = 20;

    private static final double AGGRESSIVE_RETURN = 9.4324;
    private static final double AGGRESSIVE_RISK = 15.675;
    private static final double VERY_CONSERVATIVE_RETURN = 6.189;
    private static final double VERY_CONSERVATIVE_RISK = 6.3438;

    private static final DecimalFormat PCT_FORMAT = new DecimalFormat("#.##%");
    private static final DecimalFormat DOLLAR_FORMAT = new DecimalFormat("¤###,###.##");
    static {
        PCT_FORMAT.setRoundingMode(RoundingMode.CEILING);
    }

    public static void main(String[] args) {
        Random r = new Random();
        double value = INITIAL_PORTFOLIO_VALUE;

        double total = 0;
        for (int j = 0; j < 10000; j++) {
            value = INITIAL_PORTFOLIO_VALUE;
            for (int i = 1; i <= NUM_YEARS; i++) {
              //  System.out.print("Year " + i + ": ");
                value = projectOneYear(value, r, VERY_CONSERVATIVE_RETURN, VERY_CONSERVATIVE_RISK);
             //   System.out.println("new total " + DOLLAR_FORMAT.format(value));
            }
            total += value;
        }
        System.out.println(DOLLAR_FORMAT.format(total / 10000));

        System.out.println("\n\n\n\n\n");
        value = INITIAL_PORTFOLIO_VALUE;

        for (int i = 1; i <= NUM_YEARS; i++) {
            System.out.print("Year " + i + ": ");
            value = projectOneYearStandard(value, r, VERY_CONSERVATIVE_RETURN);
            System.out.println("new total " + DOLLAR_FORMAT.format(value));
        }

    }

    private static double projectOneYear(double value, Random r, double historicalReturn, double historicalRisk) {
        // generate and apply a newly generated annual return
        double rand = (r.nextGaussian() * historicalRisk + historicalReturn) / 100;
     //   System.out.print("returned " + PCT_FORMAT.format(rand) + " ");
        value = value * (1 + rand);
        return value;
    }

    private static double projectOneYearStandard(double value, Random r, double historicalReturn) {
        // generate and apply a newly generated annual return
        System.out.print("returned " + PCT_FORMAT.format(historicalReturn/100) + " ");
        value = value * (1 + (historicalReturn / 100));
        return value;
    }
}
