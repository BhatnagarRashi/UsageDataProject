import java.io.*;
import java.util.*;

public class LogProcessor {
    public static Map<Long, UsageData> aggregateUsageFromFiles(String folderPath) {
    Map<Long, UsageData> usageMap = new HashMap<>();
    File folder = new File(folderPath);
    File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));

    if (files == null || files.length == 0) {
        System.out.println("No input files found in folder: " + folderPath);
        return usageMap;
    }

    for (File file : files) {
        System.out.println("Processing file: " + file.getName());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isHeader = true;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip header line
                }
                try {
                    String[] parts = line.split("\\|");
                    if (parts.length != 5) {
                        System.err.printf("Skipping malformed line %d in %s: Incorrect number of fields%n", lineNumber, file.getName());
                        continue;
                    }

                    long mobile = Long.parseLong(parts[0].trim());
                    int data4G = Integer.parseInt(parts[2].trim());
                    int data5G = Integer.parseInt(parts[3].trim());
                    String roamingStr = parts[4].trim();

                    boolean isRoaming;
                    if (roamingStr.equalsIgnoreCase("Yes")) {
                        isRoaming = true;
                    } else if (roamingStr.equalsIgnoreCase("No")) {
                        isRoaming = false;
                    } else {
                        System.err.printf("Skipping malformed line %d in %s: Invalid roaming value '%s'%n", lineNumber, file.getName(), roamingStr);
                        continue;
                    }

                    usageMap.putIfAbsent(mobile, new UsageData(mobile));
                    UsageData existing = usageMap.get(mobile);
                    existing.addUsage(
                        isRoaming ? 0 : data4G,
                        isRoaming ? 0 : data5G,
                        false
                    );
                    existing.addUsage(
                        isRoaming ? data4G : 0,
                        isRoaming ? data5G : 0,
                        true
                    );

                } catch (NumberFormatException e) {
                    System.err.printf("Skipping malformed line %d in %s: Number format error - %s%n", lineNumber, file.getName(), e.getMessage());
                } catch (Exception e) {
                    System.err.printf("Skipping malformed line %d in %s: %s%n", lineNumber, file.getName(), e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file " + file.getName() + ": " + e.getMessage());
        }
    }
    return usageMap;
}


    public static void printReport(Map<Long, UsageData> usageMap) {
        System.out.println("Mobile Number|4G|5G|4G Roaming|5G Roaming|Cost");
        for (UsageData data : usageMap.values()) {
            System.out.println(data.getReportLine());
        }
    }
}
