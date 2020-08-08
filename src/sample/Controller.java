package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable {

    @FXML
    private Label label, cases, deaths, recovered;

    @FXML
    private TextField text;

    @FXML
    private Button button;

    public void getCases() throws IOException {
        Main main = new Main();

        if (!(text.getText() == null)) {
            if (main.getRecovered(text.getText()).equals("N/A")) {
                print("Unknown country");
                print("Resetting to default values");
                recovered.setText("N/A");
                deaths.setText("N/A");
                cases.setText("N/A");
                label.setText("CHOOSE COUNTRY");
                text.clear();
            } else {
                String s = text.getText();
                print("Getting recovered cases for \"" + s + "\"");
                Double rec = Double.parseDouble(main.getRecovered(text.getText()));
                print("Successfully retrieved recovered cases for \"" + s + "\" (" + rec + ")");
                print("Getting deaths for \"" + s + "\"");
                Double det = Double.parseDouble(main.getDeaths(text.getText()));
                print("Successfully retrieved deaths for \"" + s + "\" (" + det + ")");
                print("Getting cases for \"" + s + "\"");
                Double cas = Double.parseDouble(main.getCases(text.getText()));
                print("Successfully retrieved cases for \"" + s + "\" (" + cas + ")");
                DecimalFormat formatter = new DecimalFormat("#, ###");

                recovered.setText(formatter.format(rec));
                deaths.setText(formatter.format(det));
                cases.setText(formatter.format(cas));
                label.setText(text.getText().toUpperCase());
                print("Successfully output to user");
                text.clear();
            }

        }

    }

    @Override
    public void initialize(URL urls, ResourceBundle resourceBundle) {


    }

    public void print(String text) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        PrintWriter out = new PrintWriter(new FileWriter("printlog.txt", true));
        out.println(dtf.format(now) + ": " + text);
        System.out.println("\u001b[31;1m\u001b[1m[COVID-19 TRACKER] \u001b[0m\u001b[32m " + text);
        out.close();
    }
}
