package falochu_at_gmail.montecarloexercise;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * POJO for storing and printing the results of a MonteCarlo simulation.
 * 
 * @author ryank
 */
public class Results {

    private static final int PRECISION_ROUND_TO_HUNDREDS = -2;

    private BigDecimal median;
    private BigDecimal ninetiethPercentile;
    private BigDecimal tenthPercentile;

    public BigDecimal getMedian() {
        return median;
    }

    public void setMedian(BigDecimal median) {
        this.median = median;
    }

    public BigDecimal getNinetiethPercentile() {
        return ninetiethPercentile;
    }

    public void setNinetiethPercentile(BigDecimal ninetiethPercentile) {
        this.ninetiethPercentile = ninetiethPercentile;
    }

    public BigDecimal getTenthPercentile() {
        return tenthPercentile;
    }

    public void setTenthPercentile(BigDecimal tenthPercentile) {
        this.tenthPercentile = tenthPercentile;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Median value:" + NumberFormat.getCurrencyInstance()
                .format(median.setScale(PRECISION_ROUND_TO_HUNDREDS, RoundingMode.UP)));
        sb.append("\n90th percentile:" + NumberFormat.getCurrencyInstance()
                .format(ninetiethPercentile.setScale(PRECISION_ROUND_TO_HUNDREDS, RoundingMode.UP)));
        sb.append("\n10th percentile:" + NumberFormat.getCurrencyInstance()
                .format(tenthPercentile.setScale(PRECISION_ROUND_TO_HUNDREDS, RoundingMode.UP)));
        return sb.toString();
    }
}
