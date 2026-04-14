import java.util.*;
import java.io.*;

class Student {

    String studentId;
    String name;
    String department;
    double mathMarks;
    double scienceMarks;
    double englishMarks;
    double computerMarks;

    Student(String studentId, String name, String department,
            double mathMarks, double scienceMarks,
            double englishMarks, double computerMarks) {

        this.studentId     = studentId;
        this.name          = name;
        this.department    = department;
        this.mathMarks     = mathMarks;
        this.scienceMarks  = scienceMarks;
        this.englishMarks  = englishMarks;
        this.computerMarks = computerMarks;
    }

    double getAverage() {
        return (mathMarks + scienceMarks + englishMarks + computerMarks) / 4;
    }

    String getGrade() {
        double avg = getAverage();
        if(avg >= 90) 
            return "A+";
        else if (avg >= 80) 
            return "A";
        else if (avg >= 70)
            return "B";
        else if (avg >= 60)
            return "C";
        else if (avg >= 50) 
            return "D";
        else                
            return "F";
    }

    String getStatus() {
        return getAverage() >= 50 ? "PASS" : "FAIL";
    }

    void displayReport(int index) {
        System.out.println("============================================");
        System.out.println("  [" + index + "] Student ID  : " + studentId);
        System.out.println("      Name        : " + name);
        System.out.println("      Department  : " + department);
        System.out.println("--------------------------------------------");
        System.out.printf ("      Math        : %.1f%n", mathMarks);
        System.out.printf ("      Science     : %.1f%n", scienceMarks);
        System.out.printf ("      English     : %.1f%n", englishMarks);
        System.out.printf ("      Computer    : %.1f%n", computerMarks);
        System.out.println("--------------------------------------------");
        System.out.printf ("      Average     : %.2f%n", getAverage());
        System.out.println("      Grade       : " + getGrade());
        System.out.println("      Status      : " + getStatus());
    }

    void displayShort(int index) {
        System.out.printf("%-5d %-12s %-20s %-12s %8.2f  %s%n",
            index,
            studentId,
            name,
            department,
            getAverage(),
            getGrade());
    }

    String toFileFormat() {
        return studentId + "," + name + "," + department + "," +
               mathMarks + "," + scienceMarks + "," +
               englishMarks + "," + computerMarks;
    }
}

public class StudentRecordSystem {

    static ArrayList<Student> students = new ArrayList<>();
    static final String FILE_NAME = "students.txt";
    static int idCounter = 1001;  

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        loadFromFile();

        System.out.println("============================================");
        System.out.println("        Student Record Management System    ");
        System.out.println("============================================");

        while (true) {

            System.out.println("\n--- MENU ---");
            System.out.println("1. Add New Student");
            System.out.println("2. View All Students");
            System.out.println("3. View Student Full Report");
            System.out.println("4. Search Student by Name/ID");
            System.out.println("5. Update Student Marks");
            System.out.println("6. Delete Student Record");
            System.out.println("7. View Class Summary");
            System.out.println("8. View Topper & Lowest Scorer");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if(choice == 1) 
                addStudent(sc);
            else if (choice == 2) 
                viewAllStudents();
            else if (choice == 3) 
                viewFullReport(sc);
            else if (choice == 4) 
                searchStudent(sc);
            else if (choice == 5) 
                updateMarks(sc);
            else if (choice == 6) 
                deleteStudent(sc);
            else if (choice == 7) 
                viewClassSummary();
            else if (choice == 8) 
                viewTopperAndLowest();
            else if (choice == 9) {
                System.out.println("\nRecords saved. Goodbye!");
                break;
            } else 
                System.out.println("Invalid option! Choose 1 to 9.");
        }

        sc.close();
    }

    // ADD a new student
    static void addStudent(Scanner sc) {

        System.out.println("\n--- Add New Student ---");

        String studentId = "STU" + idCounter++;

        System.out.print("Enter Student Name   : ");
        String name = sc.nextLine();

        System.out.println("Select Department:");
        System.out.println("  1. Science   2. Commerce");
        System.out.println("  3. Arts      4. Engineering");
        System.out.print("Enter choice: ");
        int deptChoice = sc.nextInt();
        sc.nextLine();

        String department;
        if(deptChoice == 1) 
            department = "Science";
        else if (deptChoice == 2) 
            department = "Commerce";
        else if (deptChoice == 3) 
            department = "Arts";
        else                      
            department = "Engineering";

        System.out.println("\nEnter Marks (out of 100):");

        System.out.print("  Math     : ");
        double math = sc.nextDouble();

        System.out.print("  Science  : ");
        double science = sc.nextDouble();

        System.out.print("  English  : ");
        double english = sc.nextDouble();

        System.out.print("  Computer : ");
        double computer = sc.nextDouble();
        sc.nextLine();

        if (math < 0 || math > 100 || science < 0 || science > 100 ||
            english < 0 || english > 100 || computer < 0 || computer > 100) {
            System.out.println("Marks must be between 0 and 100!");
            return;
        }

        Student student = new Student(studentId, name, department,
                                      math, science, english, computer);
        students.add(student);
        saveToFile();

        System.out.println("\nStudent added! Assigned ID: " + studentId);
    }

    // VIEW all students in short format
    static void viewAllStudents() {

        System.out.println("\n--- All Students (" + students.size() + ") ---");

        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        System.out.println("=================================================================");
        System.out.printf("%-5s %-12s %-20s %-12s %8s  %s%n",
            "No.", "ID", "Name", "Department", "Average", "Grade");
        System.out.println("=================================================================");

        for (int i = 0; i < students.size(); i++) 
            students.get(i).displayShort(i + 1);

        System.out.println("=================================================================");
    }

    // VIEW full detailed report of one student
    static void viewFullReport(Scanner sc) {

        viewAllStudents();
        if (students.isEmpty()) 
            return;

        System.out.print("\nEnter student number to view full report: ");
        int num = sc.nextInt();
        sc.nextLine();

        if (num < 1 || num > students.size()) {
            System.out.println("Invalid number!");
            return;
        }

        students.get(num - 1).displayReport(num);
    }

    // SEARCH student by name or ID
    static void searchStudent(Scanner sc) {

        System.out.println("\n--- Search Student ---");
        System.out.print("Enter name or student ID to search: ");
        String keyword = sc.nextLine().toLowerCase();

        boolean found = false;
        int index = 1;

        for (Student s : students) {   
            if (s.name.toLowerCase().contains(keyword) ||
                s.studentId.toLowerCase().contains(keyword)) {
                s.displayReport(index);
                found = true;
            }
            index++;
        }

        if (!found) 
            System.out.println("No student found matching: " + keyword);
    }

    // UPDATE student marks
    static void updateMarks(Scanner sc) {

        viewAllStudents();
        if (students.isEmpty()) 
            return;

        System.out.print("\nEnter student number to update marks: ");
        int num = sc.nextInt();
        sc.nextLine();

        if (num < 1 || num > students.size()) {
            System.out.println("Invalid number!");
            return;
        }

        Student s = students.get(num - 1);

        System.out.println("\nEnter new marks (Enter -1 to keep current value):");

        System.out.print("  Math     [" + s.mathMarks + "]: ");
        double math = sc.nextDouble();
        if (math != -1) 
            s.mathMarks = math;

        System.out.print("  Science  [" + s.scienceMarks + "]: ");
        double science = sc.nextDouble();
        if (science != -1) 
            s.scienceMarks = science;

        System.out.print("  English  [" + s.englishMarks + "]: ");
        double english = sc.nextDouble();
        if (english != -1) 
            s.englishMarks = english;

        System.out.print("  Computer [" + s.computerMarks + "]: ");
        double computer = sc.nextDouble();
        if (computer != -1) 
            s.computerMarks = computer;
        sc.nextLine();

        saveToFile();
        System.out.println("Marks updated successfully!");
        s.displayReport(num);  
    }

    // DELETE a student record
    static void deleteStudent(Scanner sc) {

        viewAllStudents();
        if (students.isEmpty()) return;

        System.out.print("\nEnter student number to delete: ");
        int num = sc.nextInt();
        sc.nextLine();

        if (num < 1 || num > students.size()) {
            System.out.println("Invalid number!");
            return;
        }

        String deletedName = students.get(num - 1).name;
        students.remove(num - 1);
        saveToFile();

        System.out.println("Record of '" + deletedName + "' deleted!");
    }

    // VIEW class summary (pass/fail/grade counts)
    static void viewClassSummary() {

        System.out.println("\n--- Class Summary ---");

        if (students.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        int passCount = 0, failCount = 0;
        int gradeAPlus = 0, gradeA = 0, gradeB = 0;
        int gradeC = 0, gradeD = 0, gradeF = 0;
        double totalAvg = 0;

        for (Student s : students) {
            double avg = s.getAverage();
            totalAvg += avg;

            if (avg >= 50) 
                passCount++; 
            else 
                failCount++;
            if(avg >= 90) 
                gradeAPlus++;
            else if (avg >= 80) 
                gradeA++;
            else if (avg >= 70) 
                gradeB++;
            else if (avg >= 60) 
                gradeC++;
            else if (avg >= 50) 
                gradeD++;
            else                
                gradeF++;
        }

        System.out.println("============================================");
        System.out.println("  Total Students  : " + students.size());
        System.out.printf ("  Class Average   : %.2f%n", totalAvg / students.size());
        System.out.println("--------------------------------------------");
        System.out.println(" Passed        : " + passCount);
        System.out.println(" Failed        : " + failCount);
        System.out.println("--------------------------------------------");
        System.out.println("  Grade A+  (90+) : " + gradeAPlus + " students");
        System.out.println("  Grade A   (80+) : " + gradeA     + " students");
        System.out.println("  Grade B   (70+) : " + gradeB     + " students");
        System.out.println("  Grade C   (60+) : " + gradeC     + " students");
        System.out.println("  Grade D   (50+) : " + gradeD     + " students");
        System.out.println("  Grade F  (<50)  : " + gradeF     + " students");
        System.out.println("============================================");
    }

    // VIEW topper and lowest scorer
    static void viewTopperAndLowest() {

        if (students.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        Student topper  = students.get(0);
        Student lowest  = students.get(0);

        for (Student s : students) {
            if (s.getAverage() > topper.getAverage())  
                topper = s;
            if (s.getAverage() < lowest.getAverage())  
                lowest = s;
        }

        System.out.println("\nCLASS TOPPER:");
        topper.displayReport(1);

        System.out.println("\nLOWEST SCORER:");
        lowest.displayReport(1);
    }

    // SAVE all students to file
    static void saveToFile() {
        try {
            FileWriter writer = new FileWriter(FILE_NAME);
            for (Student s : students) 
                writer.write(s.toFileFormat() + "\n");
            writer.close();
        } 
        catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    // LOAD students from file
    static void loadFromFile() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) 
                return;

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 7) {
                    students.add(new Student(
                        p[0], p[1], p[2],
                        Double.parseDouble(p[3]),
                        Double.parseDouble(p[4]),
                        Double.parseDouble(p[5]),
                        Double.parseDouble(p[6])
                    ));
                    idCounter++;
                }
            }

            reader.close();
            System.out.println(students.size() + " student record(s) loaded.");

        } 
        catch (IOException e) {
            System.out.println("Error loading: " + e.getMessage());
        }
    }
}