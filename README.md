Telecom Data Aggregation and Cost Report Tool

This Java project reads one or more input files containing mobile usage logs, aggregates data usage (4G/5G, roaming/non-roaming), calculates costs, and generates a report.

📁 Files in the Project

- `UsageData.java` – Model class for user data usage and cost computation  
- `LogProcessor.java` – Helper class for file reading, data aggregation, and report formatting  
- `ReportGenerator.java` – Main class to execute the program  
- `UsageDataTest.java` – Unit tests using JUnit  
- `sample_input.txt` – Sample input file  
- `junit-4.13.2.jar` and `hamcrest-core-1.3.jar` – Libraries for testing  

✅ How to Run

1. Compile the Code
```bash
javac *.java

2. Run the program
   java ReportGenerator sample_input.txt

   You can provide multiple files like this:
   java ReportGenerator sample_input1.txt sample_input2.txt

To compile and run the JUnit test:

javac -cp .;junit-4.13.2.jar;hamcrest-core-1.3.jar UsageDataTest.java
java -cp .;junit-4.13.2.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore UsageDataTest


📤 Sample Input Format (sample_input.txt)

Mobile Number|User ID|4G Data|5G Data|Roaming
9000600600|InAir1234|0|13456|No
9000600601|InAir125|1345|0|Yes
9000600600|InAir1234|500|0|Yes
9000600600|InAir1234|1000|5000|No

📤 Sample Output

Mobile Number|4G|5G|4G Roaming|5G Roaming|Cost
9000600600|1000|18456|500|0|₹1163
9000600601|2680|0|0|0|₹268

📝 Notes
Invalid lines (wrong format, missing values, invalid roaming input) are skipped with error messages shown in the terminal.

Roaming logic is handled separately: 4G and 5G roaming data is tracked.


👤 Author
Rashi Bhatnagar
