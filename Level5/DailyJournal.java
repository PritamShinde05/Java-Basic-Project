import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class JournalEntry {

    String timestamp;
    String mood;
    String title;
    String content;

    JournalEntry(String timestamp, String mood, String title, String content) {
        this.timestamp = timestamp;
        this.mood = mood;
        this.title = title;
        this.content = content;
    }

    void display(int index) {
        System.out.println("----------------------------------------");
        System.out.println("Entry " + index);
        System.out.println("Title: " + title);
        System.out.println("Date: " + timestamp);
        System.out.println("Mood: " + mood);
        System.out.println("Content:");
        System.out.println(content);
        System.out.println("----------------------------------------");
    }

    void displayShort(int index) {
        System.out.println(index + " | " + timestamp.substring(0, 10) + " | " + title + " | " + mood);
    }

    String toFileFormat() {
        String safeContent = content.replace("\n", "<<NEWLINE>>");
        return timestamp + "|||" + mood + "|||" + title + "|||" + safeContent;
    }
}

public class DailyJournal {

    static ArrayList<JournalEntry> entries = new ArrayList<>();
    static final String FILE = "journal.txt";
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        loadFromFile();

        System.out.println("Daily Journal");
        System.out.println("Today: " + LocalDateTime.now().format(dtf));

        while (true) {

            System.out.println("\nMenu");
            System.out.println("1. Write Entry");
            System.out.println("2. View Entries");
            System.out.println("3. Read Entry");
            System.out.println("4. Search");
            System.out.println("5. Filter by Mood");
            System.out.println("6. Edit Entry");
            System.out.println("7. Delete Entry");
            System.out.println("8. Stats");
            System.out.println("9. Exit");
            System.out.print("Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: 
                    writeEntry(sc); 
                    break;
                case 2: 
                    viewAll(); 
                    break;
                case 3: 
                    readEntry(sc); 
                    break;
                case 4: 
                    searchEntries(sc); 
                    break;
                case 5: 
                    filterByMood(sc); 
                    break;
                case 6: 
                    editEntry(sc); 
                    break;
                case 7: 
                    deleteEntry(sc); 
                    break;
                case 8: 
                    showStats(); 
                    break;
                case 9:
                    System.out.println("Program ended.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static void writeEntry(Scanner sc) {

        System.out.print("Title: ");
        String title = sc.nextLine();

        System.out.print("Mood (Happy/Sad/Excited/Anxious/Calm/Neutral): ");
        String mood = sc.nextLine();

        System.out.println("Write content (press Enter twice to stop):");

        StringBuilder content = new StringBuilder();
        String line;

        while (!(line = sc.nextLine()).isEmpty()) 
            content.append(line).append("\n");

        if (content.toString().trim().isEmpty()) {
            System.out.println("Entry cannot be empty.");
            return;
        }

        String timestamp = LocalDateTime.now().format(dtf);
        entries.add(new JournalEntry(timestamp, mood, title, content.toString().trim()));

        saveToFile();
        System.out.println("Entry saved.");
    }

    static void viewAll() {

        if (entries.isEmpty()) {
            System.out.println("No entries available.");
            return;
        }

        for (int i = 0; i < entries.size(); i++) 
            entries.get(i).displayShort(i + 1);
    }

    static void readEntry(Scanner sc) {

        viewAll();
        if (entries.isEmpty()) 
            return;

        System.out.print("Enter entry number: ");
        int num = sc.nextInt();
        sc.nextLine();

        if (num < 1 || num > entries.size()) {
            System.out.println("Invalid number.");
            return;
        }

        entries.get(num - 1).display(num);
    }

    static void searchEntries(Scanner sc) {

        System.out.print("Enter keyword: ");
        String keyword = sc.nextLine().toLowerCase();

        boolean found = false;
        int index = 1;

        for (JournalEntry e : entries) {
            if (e.title.toLowerCase().contains(keyword) ||
                e.content.toLowerCase().contains(keyword)) {
                e.displayShort(index);
                found = true;
            }
            index++;
        }

        if (!found) 
            System.out.println("No matching entries found.");
    }

    static void filterByMood(Scanner sc) {

        System.out.print("Enter mood: ");
        String mood = sc.nextLine();

        boolean found = false;
        int index = 1;

        for (JournalEntry e : entries) {
            if (e.mood.equalsIgnoreCase(mood)) {
                e.displayShort(index);
                found = true;
            }
            index++;
        }

        if (!found) 
            System.out.println("No entries for this mood.");
    }

    static void editEntry(Scanner sc) {

        viewAll();
        if (entries.isEmpty()) 
            return;

        System.out.print("Enter entry number: ");
        int num = sc.nextInt();
        sc.nextLine();

        if (num < 1 || num > entries.size()) {
            System.out.println("Invalid number.");
            return;
        }

        JournalEntry e = entries.get(num - 1);

        System.out.print("New title (leave empty to keep): ");
        String newTitle = sc.nextLine();
        if (!newTitle.isEmpty()) 
            e.title = newTitle;

        System.out.println("Enter new content (or press Enter to skip):");
        String firstLine = sc.nextLine();

        if (!firstLine.isEmpty()) {
            StringBuilder content = new StringBuilder();
            content.append(firstLine).append("\n");

            String line;
            while (!(line = sc.nextLine()).isEmpty()) 
                content.append(line).append("\n");

            e.content = content.toString().trim();
        }

        saveToFile();
        System.out.println("Entry updated.");
    }

    static void deleteEntry(Scanner sc) {

        viewAll();
        if (entries.isEmpty()) 
            return;

        System.out.print("Enter entry number: ");
        int num = sc.nextInt();
        sc.nextLine();

        if (num < 1 || num > entries.size()) {
            System.out.println("Invalid number.");
            return;
        }

        entries.remove(num - 1);
        saveToFile();

        System.out.println("Entry deleted.");
    }

    static void showStats() {

        if (entries.isEmpty()) {
            System.out.println("No data available.");
            return;
        }

        int totalWords = 0;
        JournalEntry longest = entries.get(0);

        for (JournalEntry e : entries) {
            int words = e.content.split("\\s+").length;
            totalWords += words;

            if (words > longest.content.split("\\s+").length) 
                longest = e;
        }

        System.out.println("\nStatistics");
        System.out.println("Total Entries: " + entries.size());
        System.out.println("Total Words: " + totalWords);
        System.out.println("Average Words: " + (totalWords / entries.size()));
        System.out.println("Longest Entry: " + longest.title);
    }

    static void saveToFile() {
        try {
            FileWriter fw = new FileWriter(FILE);

            for (JournalEntry e : entries) 
                fw.write(e.toFileFormat() + "\n");

            fw.close();
        } 
        catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    static void loadFromFile() {
        try {
            File file = new File(FILE);
            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|\\|\\|");

                if (p.length == 4) {
                    String content = p[3].replace("<<NEWLINE>>", "\n");
                    entries.add(new JournalEntry(p[0], p[1], p[2], content));
                }
            }

            br.close();

        } 
        catch (IOException e) {
            System.out.println("Error loading file.");
        }
    }
}