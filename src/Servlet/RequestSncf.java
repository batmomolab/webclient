package Servlet;


import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class RequestSncf {
    private static String TOKEN = "a168d3ba-f28b-4b61-afcd-1991230cde6b";
    public static JSONArray reqCoordonees(String gare) {
        JSONArray arrayCoordinates = new JSONArray();
        try {
            URL url = new URL("https://data.sncf.com/api/records/1.0/search/?dataset=referentiel-gares-voyageurs&q="+gare);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "text/plain");
            conn.setRequestProperty("Authorization", TOKEN);
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output = br.readLine();
            conn.disconnect();

            //Extraction des coordonnée GPS
            JSONObject jObj = new JSONObject(output);
            JSONArray arrayRecords = jObj.getJSONArray("records");
            JSONObject jsonRecords = arrayRecords.getJSONObject(0);
            JSONObject jsonGeometry = jsonRecords.getJSONObject("geometry");
            arrayCoordinates = jsonGeometry.getJSONArray("coordinates");

            // DEBUG
            /*System.out.println(jObj);
            System.out.println(arrayRecords);
            System.out.println(jsonRecords);
            System.out.println(jsonGeometry);
            System.out.println(arrayCoordinates);*/

            return arrayCoordinates;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayCoordinates;
    }

    public static int reqUic(String gare) {
        int uic = 0;
        try {
            URL url = new URL("https://data.sncf.com/api/records/1.0/search/?dataset=referentiel-gares-voyageurs&q="+gare);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "text/plain");
            conn.setRequestProperty("Authorization", TOKEN);
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output = br.readLine();
            conn.disconnect();

            //Extraction UIC
            JSONObject jObj = new JSONObject(output);
            JSONArray arrayRecords = jObj.getJSONArray("records");
            JSONObject jsonRecords = arrayRecords.getJSONObject(0);
            JSONObject jsonFields = jsonRecords.getJSONObject("fields");
            uic = jsonFields.getInt("pltf_uic_code");

            // DEBUG
            /*System.out.println(jObj);
            System.out.println(arrayRecords);
            System.out.println(jsonRecords);
            System.out.println(jsonFields);
            System.out.println(uic);*/

            return uic;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uic;
    }

    public static String[] reqTrajet(int uicDepart, int uicArrivee){
        String date[] = new String[2];
        try {
            URL url = new URL("https://api.sncf.com/v1/coverage/sncf/journeys?from=stop_area:OCE:SA:"+uicDepart+"&to=stop_area:OCE:SA:"+uicArrivee);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "text/plain");
            conn.setRequestProperty("Authorization", TOKEN);
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output = br.readLine();
            conn.disconnect();

            //Extraction dates
            JSONObject jObj = new JSONObject(output);
            JSONArray arrayJourneys = jObj.getJSONArray("journeys");
            JSONObject jsonJourneys = arrayJourneys.getJSONObject(0);
            date[0] = jsonJourneys.getString("departure_date_time");
            date[1] = jsonJourneys.getString("arrival_date_time");

            // DEBUG
            /*System.out.println(jObj);
            System.out.println(arrayRecords);
            System.out.println(jsonRecords);
            System.out.println(jsonFields);
            System.out.println(date[0] +"   "+ date[1]);*/

            return date;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return date;
    }
}
