package geturlcontent;
import java.io.*;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.HttpStatusException;

public class GetURLContent {

    public static void main(String[] args) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        JSONObject item = new JSONObject();
        
        String[] country = new String[231];
        String[] year  = {"2018", "2019","2020"};
        
        try {
            String htmlurl = "https://www.timeanddate.com/holidays/turkey/";
            Document doc = Jsoup.connect(htmlurl).get();
            Elements countryElements = doc.select("select");
            Elements optionElements = countryElements.select("option");
            for (int i = 6; i < 236; i++) {
                Element row = optionElements.get(i);
                String tmp = row.getAllElements().val();
                country[i-6] = tmp;
            }
        } catch(IOException e) {
              System.out.println("firsttr: " + e.getMessage());
        }
        for (int j = 0; j < 230; j++) {
            for (int i = 0; i < year.length; i++) {

                String html = "https://www.timeanddate.com/holidays/" + country[j] + "/" + year[i];
                
                try {
                  Document doc = Jsoup.connect(html).get();
                  Elements tableElements = doc.select("table");
                  Elements tableRowElements = tableElements.select(":not(thead) tr");
                    for (int k = 2; k < tableRowElements.size(); k++) {
                        Element row = tableRowElements.get(k);
                        
                        Elements rowItems = row.select("th");
                        String date = rowItems.get(0).text();
                        date = datechange(date, year[i]);
                        item.put("date", date);
                        Elements rowItems2 = row.select("td");
                        item.put("day", rowItems2.get(0).text());
                        item.put("holiday_name", rowItems2.get(1).text());
                        item.put("holiday_type", rowItems2.get(2).text());
                        
                        array.put(item);
                        item = new JSONObject();
                    }
                    String countryFormatted = country[j].replace(" ", "-").toLowerCase();
                    json.put(countryFormatted, array);
                    
                } catch(IOException | JSONException e) {
                    System.out.println(e);
                }
            }
            array = new JSONArray();
        }
                    try {
                File file = new File("data/full.json");
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(json.toString());
                fileWriter.flush();
            } catch (IOException e) {
                System.out.println(e);
            }
    }
    public static String datechange(String gelenveri, String yil) {
        String Sonuc = "";
        if (gelenveri.length() == 5) {
            gelenveri = gelenveri.replace(" "," 0");
        }
        if (gelenveri.contains("Jan")) {
            Sonuc = yil + "-" + gelenveri.replace("Jan ", "01-");
        } else if(gelenveri.contains("Feb")) {
            Sonuc = yil + "-" + gelenveri.replace("Feb ", "02-");
        } else if(gelenveri.contains("Mar")) {
            Sonuc = yil + "-" + gelenveri.replace("Mar ", "03-");
        } else if(gelenveri.contains("Apr")) {
            Sonuc = yil + "-" + gelenveri.replace("Apr ", "04-");
        } else if(gelenveri.contains("May")) {
            Sonuc = yil + "-" + gelenveri.replace("May ", "05-");
        } else if(gelenveri.contains("Jun")) {
            Sonuc = yil + "-" + gelenveri.replace("Jun ", "06-");
        } else if(gelenveri.contains("Jul")) {
            Sonuc = yil + "-" + gelenveri.replace("Jul ", "07-");
        } else if(gelenveri.contains("Aug")) {
            Sonuc = yil + "-" + gelenveri.replace("Aug ", "08-");
        } else if(gelenveri.contains("Sep")) {
            Sonuc = yil + "-" + gelenveri.replace("Sep ", "09-");
        } else if(gelenveri.contains("Oct")) {
            Sonuc = yil + "-" + gelenveri.replace("Oct ", "10-");
        } else if(gelenveri.contains("Nov")) {
            Sonuc = yil + "-" + gelenveri.replace("Nov ", "11-");
        } else if(gelenveri.contains("Dec")) {
            Sonuc = yil + "-" + gelenveri.replace("Dec ", "12-");
        } 
        return Sonuc;
    }
}
