/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package stimulatednewssection;

/**
 *
 * @author Angie
 */
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StimulatedNewsSection {

    public static void main(String[] args) {
        String filePath = "NewsSample.txt";
        try {
            List<String> lines=readLinesFromFile(filePath);
            List<String> natureHeadlines=processAndFilterNews(lines);
            Top5News(natureHeadlines,5);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> readLinesFromFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))) {
            String line;
            while ((line=reader.readLine())!= null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private static List<String> processAndFilterNews(List<String> lines) {
        List<String> natureHeadlines = new ArrayList<>();

        for (int i=0;i<lines.size();i+=3) {
            String title=lines.get(i);
            String url=lines.get(i+1);
            String date=lines.get(i+2);

            if (title.toLowerCase().contains("nature")&&isDateBeforeToday(date)) {
                natureHeadlines.add("[" + (natureHeadlines.size()+1)+"] "+title+"\n"+url+"\n"+date);
            }
        }
        sortNewsByDate(natureHeadlines);
        return natureHeadlines;
    }
    private static void sortNewsByDate(List<String> newsList) {
        for (int i = 0; i < newsList.size() - 1; i++) {
            for (int j = i + 1; j < newsList.size(); j++) {
                String date1 = extractDate(newsList.get(i));
                String date2 = extractDate(newsList.get(j));

                if (compareDates(date1, date2) < 0) {
                    Collections.swap(newsList, i, j);
                }
            }
        }
    }
    private static String extractDate(String news) {
   
    int startIndex = news.lastIndexOf("\n") + 1;
    return news.substring(startIndex);
}
    private static boolean isDateBeforeToday(String date){
        String[] parts = date.split(" ");
        int day = Integer.parseInt(parts[0]);
        String month = parts[1].toLowerCase();
        int year = Integer.parseInt(parts[2]);
        java.time.LocalDate currentDate = java.time.LocalDate.now();
    
        return year < currentDate.getYear() ||
                (year == currentDate.getYear() && monthToNumber(month) < currentDate.getMonthValue()) ||
                (year == currentDate.getYear() && monthToNumber(month) == currentDate.getMonthValue() && day <= currentDate.getDayOfMonth());
    }
    
       private static int monthToNumber(String month) {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};
        for (int i = 0; i < months.length; i++) {
            if (months[i].equals(month)) {
                return i + 1;
            }
        }
        return -1; 
    }  
    
    private static int compareDates(String date1, String date2) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
    try {
        Date d1 = sdf.parse(date1);
        Date d2 = sdf.parse(date2);
        return d1.compareTo(d2);
    } catch (ParseException e) {
        e.printStackTrace();
        return 0;
    }
    }
    private static void Top5News(List<String> natureHeadlines,int count) {
        System.out.println("Top 5 News about Nature");
        for (int i=0; i<Math.min(5,natureHeadlines.size()); i++) {
            System.out.println(natureHeadlines.get(i) + "\n");
        }
    }
}
