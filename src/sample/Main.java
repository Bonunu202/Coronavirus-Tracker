package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;

public class Main extends Application {

    public static Dictionary<Object, Object> casesbyCountry = new Hashtable<>();
    public static Dictionary<Object, Object> deathsbyCountry = new Hashtable<>();
    public static Dictionary<Object, Object> recoveredbyCountry = new Hashtable<>();

    @Override
    public void start(Stage primaryStage) throws IOException, ParseException {
        Controller c = new Controller();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Coronavirus");
        primaryStage.setScene(new Scene(root, 343, 369));
        primaryStage.setResizable(false);
        c.print("Finding COVID-19 API");
        URL url = new URL("https://api.covid19api.com/summary");
        c.print("Opening connection to COVID-19 API");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        c.print("Connecting to COVID-19 API");
        conn.setRequestMethod("GET");
        c.print("Successfully connected to COVID-19 API");
        String inline = "";
        conn.connect();
        int responsecode = conn.getResponseCode();

        Scanner sc = new Scanner(url.openStream());
        while (sc.hasNext()) {
            inline += sc.nextLine();
        }
        sc.close();

        JSONParser parse = new JSONParser();
        JSONObject jobj = (JSONObject) parse.parse(inline);
        JSONArray jsonarr_1 = (JSONArray) jobj.get("Countries");


        for (int i = 0; i < jsonarr_1.size(); i++) {
            JSONObject jsonobj_1 = (JSONObject) jsonarr_1.get(i);
            casesbyCountry.put(jsonobj_1.get("Country"), jsonobj_1.get("TotalConfirmed"));
            deathsbyCountry.put(jsonobj_1.get("Country"), jsonobj_1.get("TotalDeaths"));
            recoveredbyCountry.put(jsonobj_1.get("Country"), jsonobj_1.get("TotalRecovered"));
        }

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public String getRecovered(String country) {
        if (recoveredbyCountry.get(country) != null) {
            return "" + recoveredbyCountry.get(country);
        } else {
            return "N/A";
        }
    }

    public String getCases(String country) {
        if (casesbyCountry.get(country) != null) {
            return "" + casesbyCountry.get(country);
        } else {
            return "N/A";
        }
    }

    public String getDeaths(String country) {
        if (deathsbyCountry.get(country) != null) {
            return "" + deathsbyCountry.get(country);
        } else {
            return "N/A";
        }
    }

}
