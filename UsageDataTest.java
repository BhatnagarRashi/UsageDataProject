import static org.junit.Assert.*;
import org.junit.Test;

public class UsageDataTest {

    @Test
    public void testAddUsageAndCost_NoRoaming() {
        UsageData usage = new UsageData(9000600600L);
        usage.addUsage(1000, 5000, false);  // Home data
        usage.addUsage(500, 0, true);       // Roaming data

        assertEquals(1000, usage.getData4G());
        assertEquals(5000, usage.getData5G());
        assertEquals(500, usage.getData4GRoaming());
        assertEquals(0, usage.getData5GRoaming());
        assertEquals(116, (int) usage.computeCost());
    }

    @Test
    public void testCostWithSurcharge() {
        UsageData usage = new UsageData(9000600602L);
        usage.addUsage(40000, 100000, false); // 140000 total usage
        double expectedCost = 40000 * AppConfig.RATE_4G +
                              100000 * AppConfig.RATE_5G;
        if (40000 + 100000 > AppConfig.USAGE_THRESHOLD) {
            expectedCost += expectedCost * AppConfig.SURCHARGE_PERCENT;
        }
        assertEquals(Math.round(expectedCost), Math.round(usage.computeCost()));
    }

    @Test
    public void testCostRounding() {
        UsageData usage = new UsageData(9000600603L);
        usage.addUsage(60000, 20000, false);  // Home
        usage.addUsage(10000, 20000, true);   // Roaming
        double expectedCost = 60000 * AppConfig.RATE_4G +
                              20000 * AppConfig.RATE_5G +
                              10000 * AppConfig.RATE_4G_ROAMING +
                              20000 * AppConfig.RATE_5G_ROAMING;
        if (60000 + 20000 + 10000 + 20000 > AppConfig.USAGE_THRESHOLD) {
            expectedCost += expectedCost * AppConfig.SURCHARGE_PERCENT;
        }
        assertEquals(Math.round(expectedCost), Math.round(usage.computeCost()));
    }
}
