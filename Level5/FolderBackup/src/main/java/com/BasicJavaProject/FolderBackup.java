package com.BasicJavaProject;

import java.io.*;
import java.util.*;

public class FolderBackup {

    static int totalFiles = 0;
    static int totalFolders = 0;
    static long totalSize = 0;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter source folder path: ");
        String sourcePath = sc.nextLine();

        System.out.print("Enter destination folder path: ");
        String destPath = sc.nextLine();

        File sourceFolder = new File(sourcePath);

        if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
            System.out.println("Source folder not found");
            return;
        }

        String backupFolderPath = destPath + File.separator + "Backup";
        File backupFolder = new File(backupFolderPath);
        backupFolder.mkdirs();

        System.out.println("Starting backup...");

        long startTime = System.currentTimeMillis();

        copyFolder(sourceFolder, backupFolder);

        long endTime = System.currentTimeMillis();

        System.out.println("Backup completed");
        System.out.println("Backup location: " + backupFolderPath);
        System.out.println("Files copied: " + totalFiles);
        System.out.println("Folders created: " + totalFolders);
        System.out.println("Total size: " + formatSize(totalSize));
        System.out.println("Time taken: " + ((endTime - startTime) / 1000) + " sec");

        sc.close();
    }

    static void copyFolder(File source, File destination) {

        File[] files = source.listFiles();
        if (files == null)
            return;

        for (File file : files) {

            File destFile = new File(destination, file.getName());

            if (file.isDirectory()) {
                destFile.mkdir();
                totalFolders++;
                copyFolder(file, destFile);
            }
            else {
                copyFile(file, destFile);
                totalFiles++;
                totalSize += file.length();
            }
        }
    }

    static void copyFile(File source, File destination) {

        try {
            FileInputStream fis = new FileInputStream(source);
            FileOutputStream fos = new FileOutputStream(destination);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = fis.read(buffer)) > 0)
                fos.write(buffer, 0, length);

            fis.close();
            fos.close();

        }
        catch (IOException e) {
            System.out.println("Error copying file: " + source.getName());
        }
    }

    static String formatSize(long bytes) {
        if (bytes >= 1024 * 1024 * 1024)
            return (bytes / (1024.0 * 1024 * 1024)) + " GB";
        else if (bytes >= 1024 * 1024)
            return (bytes / (1024.0 * 1024)) + " MB";
        else if (bytes >= 1024)
            return (bytes / 1024.0) + " KB";
        else
            return bytes + " B";
    }
}