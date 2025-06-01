import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppConfig {
    public static final double RATE_4G = 0.01;
    public static final double RATE_5G = 0.02;
    public static final double RATE_4G_ROAMING = RATE_4G * 1.10;
    public static final double RATE_5G_ROAMING = RATE_5G * 1.15;

    // Add these variables as per your usage in UsageData.java
    public static int USAGE_THRESHOLD = 100000; // default threshold if loading fails
    public static final double SURCHARGE_PERCENT = 0.05; // 5% surcharge

    static {
        Properties props = new Properties();
        try {
            FileInputStream fis = new FileInputStream("config.properties");
            props.load(fis);
            USAGE_THRESHOLD = Integer.parseInt(props.getProperty("threshold", "100000"));
            fis.close();
        } catch (IOException | NumberFormatException e) {
            System.out.println("Warning: Could not load config.properties, using default threshold = " + USAGE_THRESHOLD);
        }
    }
}
