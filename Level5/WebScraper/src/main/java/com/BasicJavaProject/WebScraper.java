package com.BasicJavaProject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.util.*;

public class WebScraper {

    static String lastScrapedContent = "";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\nMenu:");
            System.out.println("1. Get title and description");
            System.out.println("2. Get headings");
            System.out.println("3. Get links");
            System.out.println("4. Wikipedia summary");
            System.out.println("5. Save to file");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) scrapeTitle(sc);
            else if (choice == 2) scrapeHeadings(sc);
            else if (choice == 3) scrapeLinks(sc);
            else if (choice == 4) scrapeWikipedia(sc);
            else if (choice == 5) saveToFile();
            else if (choice == 6) break;
            else System.out.println("Invalid choice");
        }

        sc.close();
    }

    static void scrapeTitle(Scanner sc) {

        System.out.print("Enter URL: ");
        String url = sc.nextLine();

        try {
            Document doc = Jsoup.connect(url).get();

            String title = doc.title();
            String desc = doc.select("meta[name=description]").attr("content");

            if (desc.isEmpty()) desc = "No description";

            System.out.println("Title: " + title);
            System.out.println("Description: " + desc);

            lastScrapedContent = "Title: " + title + "\nDescription: " + desc;

        } catch (IOException e) {
            System.out.println("Error fetching data");
        }
    }

    static void scrapeHeadings(Scanner sc) {

        System.out.print("Enter URL: ");
        String url = sc.nextLine();

        try {
            Document doc = Jsoup.connect(url).get();

            Elements h1 = doc.select("h1");
            Elements h2 = doc.select("h2");
            Elements h3 = doc.select("h3");

            StringBuilder sb = new StringBuilder();

            for (Element e : h1) sb.append("H1: ").append(e.text()).append("\n");
            for (Element e : h2) sb.append("H2: ").append(e.text()).append("\n");
            for (Element e : h3) sb.append("H3: ").append(e.text()).append("\n");

            System.out.println(sb.toString());
            lastScrapedContent = sb.toString();

        } catch (IOException e) {
            System.out.println("Error fetching headings");
        }
    }

    static void scrapeLinks(Scanner sc) {

        System.out.print("Enter URL: ");
        String url = sc.nextLine();

        try {
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("a[href]");

            StringBuilder sb = new StringBuilder();

            for (Element link : links) {
                String text = link.text();
                String href = link.attr("abs:href");

                if (!text.isEmpty())
                    sb.append(text).append(" -> ").append(href).append("\n");
            }

            System.out.println(sb.toString());
            lastScrapedContent = sb.toString();

        } catch (IOException e) {
            System.out.println("Error fetching links");
        }
    }

    static void scrapeWikipedia(Scanner sc) {

        System.out.print("Enter topic: ");
        String topic = sc.nextLine().replace(" ", "_");

        String url = "https://en.wikipedia.org/wiki/" + topic;

        try {
            Document doc = Jsoup.connect(url).get();

            Elements p = doc.select("p");

            StringBuilder sb = new StringBuilder();

            int count = 0;
            for (Element e : p) {
                String text = e.text();

                if (!text.isEmpty()) {
                    sb.append(text).append("\n\n");
                    count++;
                    if (count == 3) break;
                }
            }

            System.out.println(sb.toString());
            lastScrapedContent = sb.toString();

        } catch (IOException e) {
            System.out.println("Error fetching Wikipedia data");
        }
    }

    static void saveToFile() {

        if (lastScrapedContent.isEmpty()) {
            System.out.println("No data to save");
            return;
        }

        try {
            FileWriter fw = new FileWriter("output.txt");
            fw.write(lastScrapedContent);
            fw.close();

            System.out.println("Saved to file");

        } catch (IOException e) {
            System.out.println("Error saving file");
        }
    }
}