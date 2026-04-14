import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

class Log {

    String timestamp;  
    String level;      
    String message;   

    Log(String timestamp, String level, String message) {
        this.timestamp = timestamp;
        this.level     = level;
        this.message   = message;
    }

    void display(int index) {
        System.out.println("  [" + index + "] " + timestamp +
                           "  [" + level + "]  " + message);
    }

    String toFileFormat() {
        return timestamp + " | " + level + " | " + message;
    }
}

public class LogGenerator {

    static ArrayList<Log> logs = new ArrayList<>();
    static final String FILE_NAME = "app_logs.txt";

    static DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        loadFromFile();

        System.out.println("      📝 Timestamped Log Generator        ");
        System.out.println("==========================================");

        while (true) {

            System.out.println("\n--- MENU ---");
            System.out.println("1. Add Log Entry");
            System.out.println("2. View All Logs");
            System.out.println("3. Filter Logs by Level");
            System.out.println("4. Search Logs by Keyword");
            System.out.println("5. Clear All Logs");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if(choice == 1) 
                addLog(sc);
            else if (choice == 2) 
                viewAllLogs();
            else if (choice == 3) 
                filterByLevel(sc);
            else if (choice == 4) 
                searchLogs(sc);
            else if (choice == 5) 
                clearLogs();
            else if (choice == 6) {
                System.out.println("\nLogs saved. Goodbye!");
                break;
            } 
            else 
                System.out.println("Invalid option!");
        }

        sc.close();
    }

    // ADD a new log entry
    static void addLog(Scanner sc) {

        System.out.println("\n--- Add Log Entry ---");

        System.out.println("Select Log Level:");
        System.out.println("  1. INFO     2. WARNING     3. ERROR");
        System.out.print("Enter choice: ");
        int lvl = sc.nextInt();
        sc.nextLine();

        String level;
        if(lvl == 1) 
            level = "INFO   ";
        else if (lvl == 2) 
            level = "WARNING";
        else               
            level = "ERROR  ";

        System.out.print("Enter log message: ");
        String message = sc.nextLine();

        // Auto-generate timestamp from current date and time
        String timestamp = LocalDateTime.now().format(formatter);

        Log log = new Log(timestamp, level, message);
        logs.add(log);
        saveToFile();

        System.out.println("Log entry added at " + timestamp);
    }

    // VIEW all logs
    static void viewAllLogs() {

        System.out.println("\n--- All Logs (" + logs.size() + ") ---");

        if (logs.isEmpty()) {
            System.out.println("No logs found.");
            return;
        }

        System.out.println("----------------------------------------------------------");
        for (int i = 0; i < logs.size(); i++) 
            logs.get(i).display(i + 1);

        System.out.println("----------------------------------------------------------");
        System.out.println("Total: " + logs.size() + " log(s)");
    }

    // FILTER logs by level (INFO / WARNING / ERROR)
    static void filterByLevel(Scanner sc) {

        System.out.println("\n--- Filter by Level ---");
        System.out.println("  1. INFO     2. WARNING     3. ERROR");
        System.out.print("Enter choice: ");
        int lvl = sc.nextInt();
        sc.nextLine();

        String level;
        if(lvl == 1) 
            level = "INFO   ";
        else if (lvl == 2) 
            level = "WARNING";
        else               
            level = "ERROR  ";

        boolean found = false;
        int index = 1;

        System.out.println("----------------------------------------------------------");

        for (Log log : logs) {
            if (log.level.equals(level)) {
                log.display(index);
                found = true;
            }
            index++;
        }

        System.out.println("----------------------------------------------------------");

        if (!found) 
            System.out.println("No logs found for level: " + level.trim());
    }

    // SEARCH logs by keyword in message
    static void searchLogs(Scanner sc) {

        System.out.print("\nEnter keyword to search: ");
        String keyword = sc.nextLine().toLowerCase();

        boolean found = false;
        int index = 1;

        System.out.println("----------------------------------------------------------");

        for (Log log : logs) {
            if (log.message.toLowerCase().contains(keyword)) {
                log.display(index);
                found = true;
            }
            index++;
        }

        System.out.println("----------------------------------------------------------");

        if (!found) 
            System.out.println("No logs found for: " + keyword);
    }

    // CLEAR all logs
    static void clearLogs() {
        logs.clear();   
        saveToFile();   
        System.out.println("All logs cleared!");
    }

    // SAVE logs to file
    static void saveToFile() {
        try {
            FileWriter fw = new FileWriter(FILE_NAME);
            for (Log log : logs) {
                fw.write(log.toFileFormat() + "\n");
            }
            fw.close();
        } 
        catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    // LOAD logs from file
    static void loadFromFile() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                // Split by " | " to get 3 parts
                String[] p = line.split(" \\| ");
                if (p.length == 3) {
                    logs.add(new Log(p[0], p[1], p[2]));
                }
            }

            br.close();
            System.out.println(logs.size() + " log(s) loaded.");

        } catch (IOException e) {
            System.out.println("Error loading: " + e.getMessage());
        }
    }
}