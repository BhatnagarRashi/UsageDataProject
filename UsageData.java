public class UsageData {
    private long mobileNumber;
    private int data4G = 0;
    private int data5G = 0;
    private int data4GRoaming = 0;
    private int data5GRoaming = 0;

    public UsageData(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void addUsage(int data4G, int data5G, boolean isRoaming) {
        if (isRoaming) {
            this.data4GRoaming += data4G;
            this.data5GRoaming += data5G;
        } else {
            this.data4G += data4G;
            this.data5G += data5G;
        }
    }

    public double computeCost() {
        double cost = 0;
        cost += data4G * AppConfig.RATE_4G;
        cost += data5G * AppConfig.RATE_5G;
        cost += data4GRoaming * AppConfig.RATE_4G_ROAMING;
        cost += data5GRoaming * AppConfig.RATE_5G_ROAMING;

        int totalData = data4G + data5G + data4GRoaming + data5GRoaming;
        if (totalData > AppConfig.USAGE_THRESHOLD) {
            cost += cost * AppConfig.SURCHARGE_PERCENT;
        }
        return Math.round(cost);
    }

    public String getReportLine() {
        return mobileNumber + "|" + data4G + "|" + data5G + "|" + data4GRoaming + "|" + data5GRoaming + "|" + (int) computeCost();
    }

    public int getData4G() {
        return data4G;
    }

    public int getData5G() {
        return data5G;
    }

    public int getData4GRoaming() {
        return data4GRoaming;
    }

    public int getData5GRoaming() {
        return data5GRoaming;
    }
}
