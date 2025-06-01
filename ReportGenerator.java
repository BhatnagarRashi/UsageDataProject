import java.io.*;
import java.util.*;

public class ReportGenerator {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Please provide input file(s) as command line arguments.");
            System.exit(1);
        }

        // Map: MobileNumber -> UsageData object
        Map<Long, UsageData> usageMap = new HashMap<>();

        for (String fileName : args) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line;
                int lineNum = 0;

                while ((line = br.readLine()) != null) {
                    lineNum++;
                    // Skip header line (assumes first line of each file is header)
                    if (lineNum == 1 && line.startsWith("Mobile Number")) continue;

                    String[] parts = line.split("\\|");
                    if (parts.length != 5) {
                        System.err.printf("Skipping malformed line %d in %s: Incorrect number of fields%n", lineNum, fileName);
                        continue;
                    }

                    try {
                        long mobileNumber = Long.parseLong(parts[0].trim());
                        int data4G = Integer.parseInt(parts[2].trim());
                        int data5G = Integer.parseInt(parts[3].trim());
                        String roamingStr = parts[4].trim();
                        boolean isRoaming;

                        if (roamingStr.equalsIgnoreCase("Yes")) {
                            isRoaming = true;
                        } else if (roamingStr.equalsIgnoreCase("No")) {
                            isRoaming = false;
                        } else {
                            System.err.printf("Skipping malformed line %d in %s: Invalid roaming value '%s'%n", lineNum, fileName, roamingStr);
                            continue;
                        }

                        UsageData usage = usageMap.get(mobileNumber);
                        if (usage == null) {
                            usage = new UsageData(mobileNumber);
                            usageMap.put(mobileNumber, usage);
                        }

                        usage.addUsage(data4G, data5G, isRoaming);

                    } catch (NumberFormatException e) {
                        System.err.printf("Skipping malformed line %d in %s: Number format error - %s%n", lineNum, fileName, e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.err.printf("Error reading file %s: %s%n", fileName, e.getMessage());
            }
        }

        // Print report header
        System.out.println("Mobile Number|4G|5G|4G Roaming|5G Roaming|Cost");

        // Print usage data for each mobile number
        for (UsageData usage : usageMap.values()) {
            System.out.println(usage.getReportLine());
        }
    }
}
