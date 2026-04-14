import java.util.*;
import java.io.*;
import java.time.LocalDate;

class Task {

    String title;
    String priority;   // High, Medium, Low
    String dueDate;
    boolean isDone;

    Task(String title, String priority, String dueDate, boolean isDone) {
        this.title    = title;
        this.priority = priority;
        this.dueDate  = dueDate;
        this.isDone   = isDone;
    }

    void display(int index) {
        String status = isDone ? "Done" : "Pending";
        System.out.println("  [" + index + "] " + title);
        System.out.println("      Priority : " + priority);
        System.out.println("      Due Date : " + dueDate);
        System.out.println("      Status   : " + status);
        System.out.println("  ----------------------------------------");
    }

    String toFileFormat() {
        return title + "," + priority + "," + dueDate + "," + isDone;
    }
}

public class TaskManager {

    static ArrayList<Task> tasks = new ArrayList<>();
    static final String FILE_NAME = "tasks.txt";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        loadFromFile();

        System.out.println("==========================================");
        System.out.println("        📋 Daily Task Manager             ");
        System.out.println("==========================================");
        System.out.println("📅 Today : " + LocalDate.now());

        while (true) {

            System.out.println("\n--- MENU ---");
            System.out.println("1. Add Task");
            System.out.println("2. View All Tasks");
            System.out.println("3. Mark Task as Done");
            System.out.println("4. Delete Task");
            System.out.println("5. View Pending Tasks");
            System.out.println("6. View Completed Tasks");
            System.out.println("7. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if(choice == 1) 
                addTask(sc);
            else if (choice == 2) 
                viewAll();
            else if (choice == 3) 
                markDone(sc);
            else if (choice == 4) 
                deleteTask(sc);
            else if (choice == 5) 
                viewByStatus(false);
            else if (choice == 6) 
                viewByStatus(true);
            else if (choice == 7) {
                System.out.println("✅ Tasks saved. Goodbye! 👋");
                break;
            } 
            else 
                System.out.println("❌ Invalid option!");
        }

        sc.close();
    }

    static void addTask(Scanner sc) {

        System.out.println("\n--- Add New Task ---");

        System.out.print("Enter task title : ");
        String title = sc.nextLine();

        System.out.println("Select Priority  :");
        System.out.println("  1. High   2. Medium   3. Low");
        System.out.print("Enter choice     : ");
        int p = sc.nextInt();
        sc.nextLine();

        String priority;
        if(p == 1) 
            priority = "High";
        else if (p == 2) 
            priority = "Medium";
        else             
            priority = "Low";

        System.out.print("Enter due date (YYYY-MM-DD) or press Enter for today : ");
        String dueDate = sc.nextLine();
        if (dueDate.isEmpty()) 
            dueDate = LocalDate.now().toString();

        tasks.add(new Task(title, priority, dueDate, false));
        saveToFile();

        System.out.println("Task added successfully!");
    }

    static void viewAll() {

        System.out.println("\n--- All Tasks (" + tasks.size() + ") ---");

        if (tasks.isEmpty()) {
            System.out.println("📭 No tasks found.");
            return;
        }

        System.out.println("  ----------------------------------------");
        for (int i = 0; i < tasks.size(); i++) 
            tasks.get(i).display(i + 1);

        long pending   = tasks.stream().filter(t -> !t.isDone).count();
        long completed = tasks.stream().filter(t ->  t.isDone).count();

        System.out.println("Pending: " + pending + ",   Completed: " + completed);
    }

    static void markDone(Scanner sc) {

        viewAll();
        if (tasks.isEmpty()) 
            return;

        System.out.print("\nEnter task number to mark as done: ");
        int num = sc.nextInt();
        sc.nextLine();

        if (num < 1 || num > tasks.size()) {
            System.out.println("Invalid number!");
            return;
        }

        Task t = tasks.get(num - 1);

        if (t.isDone) 
            System.out.println("Task is already marked as done!");
        else {
            t.isDone = true;
            saveToFile();
            System.out.println("Task '" + t.title + "' marked as done!");
        }
    }

    static void deleteTask(Scanner sc) {

        viewAll();
        if (tasks.isEmpty()) return;

        System.out.print("\nEnter task number to delete: ");
        int num = sc.nextInt();
        sc.nextLine();

        if (num < 1 || num > tasks.size()) {
            System.out.println("Invalid number!");
            return;
        }

        String deleted = tasks.get(num - 1).title;
        tasks.remove(num - 1);
        saveToFile();

        System.out.println("Task '" + deleted + "' deleted!");
    }

    static void viewByStatus(boolean done) {

        String label = done ? "Completed" : "Pending";
        System.out.println("\n--- " + label + " Tasks ---");

        boolean found = false;
        int index = 1;

        System.out.println("  ----------------------------------------");
        for (Task t : tasks) {
            if (t.isDone == done) {
                t.display(index);
                found = true;
            }
            index++;
        }

        if (!found) 
            System.out.println("No " + label.toLowerCase() + " tasks.");
    }

    static void saveToFile() {
        try {
            FileWriter fw = new FileWriter(FILE_NAME);
            for (Task t : tasks) {
                fw.write(t.toFileFormat() + "\n");
            }
            fw.close();
        } 
        catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    static void loadFromFile() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) 
                return;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 4) 
                    tasks.add(new Task(p[0], p[1], p[2], Boolean.parseBoolean(p[3])));
            }

            br.close();
            System.out.println(tasks.size() + " task(s) loaded.");

        } 
        catch (IOException e) {
            System.out.println("Error loading: " + e.getMessage());
        }
    }
}