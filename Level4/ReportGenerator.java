import java.util.*;
import java.io.*;

class Student {

    String name;
    String rollNo;
    double maths, science, english, computer;

    Student(String name, String rollNo, double maths,
            double science, double english, double computer) {

        this.name = name;
        this.rollNo = rollNo;
        this.maths = maths;
        this.science = science;
        this.english = english;
        this.computer = computer;
    }

    double getAverage() {
        return (maths + science + english + computer) / 4;
    }

    double getTotal() {
        return maths + science + english + computer;
    }

    String getGrade() {
        double avg = getAverage();

        if (avg >= 90) 
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

    String getResult() {
        return (maths >= 35 && science >= 35 &&
                english >= 35 && computer >= 35) ? "PASS" : "FAIL";
    }

    String getRemark() {
        double avg = getAverage();

        if (avg >= 85) 
            return "Excellent performance";
        else if (avg >= 70) 
            return "Good job";
        else if (avg >= 50) 
            return "Can improve";
        else 
            return "Needs serious improvement";
    }
}

public class ReportGenerator {

    static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("        Student Report Generator");
        System.out.println("==========================================");

        while (true) {

            System.out.println("\nMenu:");
            System.out.println("1. Add Student");
            System.out.println("2. View All Reports");
            System.out.println("3. View Single Student Report");
            System.out.println("4. View Class Summary");
            System.out.println("5. Save Report to File");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: 
                    addStudent(sc); 
                    break;
                case 2: 
                    viewAllReports(); 
                    break;
                case 3: 
                    viewSingleReport(sc); 
                    break;
                case 4: 
                    viewClassSummary(); 
                    break;
                case 5: 
                    saveReportToFile(); 
                    break;
                case 6:
                    System.out.println("Exiting... Goodbye.");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void addStudent(Scanner sc) {

        System.out.println("\n--- Add Student ---");

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Roll No: ");
        String rollNo = sc.nextLine();

        System.out.println("Enter marks (0 - 100):");

        System.out.print("Maths: ");
        double maths = sc.nextDouble();

        System.out.print("Science: ");
        double science = sc.nextDouble();

        System.out.print("English: ");
        double english = sc.nextDouble();

        System.out.print("Computer: ");
        double computer = sc.nextDouble();
        sc.nextLine();

        if (maths < 0 || maths > 100 || science < 0 || science > 100 ||
            english < 0 || english > 100 || computer < 0 || computer > 100) {

            System.out.println("Marks must be between 0 and 100.");
            return;
        }

        students.add(new Student(name, rollNo, maths, science, english, computer));
        System.out.println("Student added successfully.");
    }

    static void printStudentReport(Student s, int index) {

        System.out.println("\n------------------------------------------");
        System.out.println("Report Card #" + index);
        System.out.println("------------------------------------------");

        System.out.println("Name     : " + s.name);
        System.out.println("Roll No  : " + s.rollNo);

        System.out.println("\nMarks:");
        System.out.println("  Maths     : " + s.maths);
        System.out.println("  Science   : " + s.science);
        System.out.println("  English   : " + s.english);
        System.out.println("  Computer  : " + s.computer);

        System.out.println("\nPerformance:");
        System.out.println("  Total     : " + s.getTotal() + " / 400");
        System.out.println("  Average   : " + String.format("%.2f", s.getAverage()) + "%");
        System.out.println("  Grade     : " + s.getGrade());
        System.out.println("  Result    : " + s.getResult());
        System.out.println("  Remark    : " + s.getRemark());

        System.out.println("------------------------------------------");
    }

    static void viewAllReports() {

        if (students.isEmpty()) {
            System.out.println("No students added yet.");
            return;
        }

        for (int i = 0; i < students.size(); i++)
            printStudentReport(students.get(i), i + 1);
    }

    static void viewSingleReport(Scanner sc) {

        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        System.out.println("\nStudent List:");
        for (int i = 0; i < students.size(); i++)
            System.out.println((i + 1) + ". " + students.get(i).name +
                    " (" + students.get(i).rollNo + ")");

        System.out.print("Select student number: ");
        int num = sc.nextInt();
        sc.nextLine();

        if (num < 1 || num > students.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        printStudentReport(students.get(num - 1), num);
    }

    static void viewClassSummary() {

        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        int pass = 0, fail = 0;
        double totalAvg = 0;

        Student topper = students.get(0);
        Student lowest = students.get(0);

        for (Student s : students) {

            if (s.getResult().equals("PASS")) 
                pass++;
            else 
                fail++;

            totalAvg += s.getAverage();

            if (s.getAverage() > topper.getAverage()) 
                topper = s;
            if (s.getAverage() < lowest.getAverage()) 
                lowest = s;
        }

        double classAvg = totalAvg / students.size();

        System.out.println("\n==========================================");
        System.out.println("Class Performance Summary");
        System.out.println("==========================================");

        System.out.println("Total Students : " + students.size());
        System.out.println("Class Average  : " + String.format("%.2f", classAvg) + "%");

        System.out.println("\nResult Overview:");
        System.out.println("  Passed : " + pass);
        System.out.println("  Failed : " + fail);

        System.out.println("\nHighlights:");
        System.out.println("  Top Performer     : " + topper.name +
                " (" + String.format("%.2f", topper.getAverage()) + "%)");
        System.out.println("  Needs Attention   : " + lowest.name +
                " (" + String.format("%.2f", lowest.getAverage()) + "%)");

        System.out.println("\nStudent-wise Performance:");
        System.out.println("------------------------------------------");

        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);

            System.out.println((i + 1) + ". " + s.name +
                    " | Avg: " + String.format("%.2f", s.getAverage()) +
                    "% | Grade: " + s.getGrade() +
                    " | " + s.getResult());
        }

    }

    static void saveReportToFile() {

        if (students.isEmpty()) {
            System.out.println("No data to save.");
            return;
        }

        String fileName = "student_report.txt";

        try {
            FileWriter fw = new FileWriter(fileName);

            fw.write("Student Performance Report\n");
            fw.write("==========================================\n");

            for (Student s : students) {

                fw.write("\nName     : " + s.name + "\n");
                fw.write("Roll No  : " + s.rollNo + "\n");

                fw.write("Marks:\n");
                fw.write("  Maths     : " + s.maths + "\n");
                fw.write("  Science   : " + s.science + "\n");
                fw.write("  English   : " + s.english + "\n");
                fw.write("  Computer  : " + s.computer + "\n");

                fw.write("Performance:\n");
                fw.write("  Total     : " + s.getTotal() + " / 400\n");
                fw.write("  Average   : " + String.format("%.2f", s.getAverage()) + "%\n");
                fw.write("  Grade     : " + s.getGrade() + "\n");
                fw.write("  Result    : " + s.getResult() + "\n");
                fw.write("  Remark    : " + s.getRemark() + "\n");

            }

            fw.close();
            System.out.println("Report saved to " + fileName);

        } 
        catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}