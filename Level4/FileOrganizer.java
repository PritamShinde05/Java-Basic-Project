import java.io.*;
import java.util.*;

public class FileOrganizer {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== Automated File Organizer ===");
        System.out.print("Enter folder path to organize: ");
        String folderPath = sc.nextLine();

        File folder = new File(folderPath);

        // Check if folder exists
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Invalid folder path!");
            sc.close();
            return;
        }

        File[] files = folder.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("No files found in the folder.");
            sc.close();
            return;
        }

        int moved = 0;

        for (File file : files) {

            // Skip folders, only process files
            if (file.isDirectory()) 
                continue;

            String name = file.getName();
            String ext  = getExtension(name);

            // Decide subfolder name based on extension
            String subfolderName = getCategoryFolder(ext);

            // Create subfolder if it doesn't exist
            File subfolder = new File(folderPath + File.separator + subfolderName);
            if (!subfolder.exists()) 
                subfolder.mkdir();

            // Move file into subfolder
            File destination = new File(subfolder + File.separator + name);
            if (file.renameTo(destination)) {
                System.out.println("Moved: " + name + "  →  " + subfolderName + "/");
                moved++;
            } else {
                System.out.println("Could not move: " + name);
            }
        }

        System.out.println("\nDone! " + moved + " file(s) organized.");
        sc.close();
    }

    // Get file extension from file name
    static String getExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex == -1) return "unknown";
        return filename.substring(dotIndex + 1).toLowerCase();
    }

    // Return folder name based on file extension
    static String getCategoryFolder(String ext) {
        switch (ext) {
            case "jpg": case "jpeg": case "png": case "gif": case "bmp":
                return "Images";
            case "mp4": case "avi": case "mkv": case "mov":
                return "Videos";
            case "mp3": case "wav": case "aac":
                return "Music";
            case "pdf": case "doc": case "docx": case "txt": case "pptx": case "xlsx":
                return "Documents";
            case "zip": case "rar": case "7z":
                return "Archives";
            case "java": case "py": case "js": case "html": case "css": case "cpp":
                return "Code";
            default:
                return "Others";
        }
    }
}