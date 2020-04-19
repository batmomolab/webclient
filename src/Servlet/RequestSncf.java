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

//Cette classe contient les différente requêtes faites à l'API SNCF

public class RequestSncf {
    private static String TOKEN = "a168d3ba-f28b-4b61-afcd-1991230cde6b"; //Token utilisé pour la connexion à l'api sncf

    public static JSONArray reqCoordonees(String gare) { //Cette méthode renvoi les coordonnées GPS de la gare en paramètre
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

            //Extraction des coordonnée GPS des gares à partir du JSON obtenue par la requête
            JSONObject jObj = new JSONObject(output);
            JSONArray arrayRecords = jObj.getJSONArray("records");
            JSONObject jsonRecords = arrayRecords.getJSONObject(0);
            JSONObject jsonGeometry = jsonRecords.getJSONObject("geometry");
            arrayCoordinates = jsonGeometry.getJSONArray("coordinates");

            return arrayCoordinates;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayCoordinates;
    }

    public static int reqUic(String gare) { //Cette méthode renvoi l'identifiant utilisé dans l'API sncf d'une gare
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

            //Extraction des UIC des gares à partir du JSON obtenue par la requête
            JSONObject jObj = new JSONObject(output);
            JSONArray arrayRecords = jObj.getJSONArray("records");
            JSONObject jsonRecords = arrayRecords.getJSONObject(0);
            JSONObject jsonFields = jsonRecords.getJSONObject("fields");
            uic = jsonFields.getInt("pltf_uic_code");

            return uic;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uic;
    }

    public static String[] reqTrajet(int uicDepart, int uicArrivee,String horaire){ //cette méthode renvoi les horaires d'un train, ainsi que la gare de départ, de destination, le type de train et le terminus
        String trajet[] = new String[6];
        try {
            URL url = new URL("https://api.sncf.com/v1/coverage/sncf/journeys?from=stop_area:OCE:SA:"+uicDepart+"&to=stop_area:OCE:SA:"+uicArrivee+"&datetime="+horaire);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "text/plain");
            conn.setRequestProperty("Authorization", TOKEN);

            if (conn.getResponseCode() != 200) {
                if (conn.getResponseCode() == 500){
                    throw new RuntimeException(("Trajet introuvable"));
                } else {
                    throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                }
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output = br.readLine();
            conn.disconnect();

            //Extraction des dates à partir du JSON obtenue par la requête
            JSONObject jObj = new JSONObject(output);
            JSONArray arrayJourneys = jObj.getJSONArray("journeys");
            JSONObject jsonJourneys = arrayJourneys.getJSONObject(0);
            trajet[0] = jsonJourneys.getString("departure_date_time");
            trajet[1] = jsonJourneys.getString("arrival_date_time");

            //Extraction du label gare à partir du JSON obtenue par la requête
            JSONArray arraySections = jsonJourneys.getJSONArray("sections");
            JSONObject jsonSections = arraySections.getJSONObject(1);
            JSONObject jsonFrom = jsonSections.getJSONObject("from");
            JSONObject jsonStopPoint = jsonFrom.getJSONObject("stop_point");
            trajet[2] = jsonStopPoint.getString("label");
            JSONObject jsonTo = jsonSections.getJSONObject("to");
            JSONObject jsonStopPoint2 = jsonTo.getJSONObject("stop_point");
            trajet[3] = jsonStopPoint2.getString("label");

            //Extraction des informations du train à partir du JSON obtenue par la requête
            JSONObject jsonDisplay = jsonSections.getJSONObject("display_informations");
            trajet[4] = jsonDisplay.getString("direction");
            trajet[5] = jsonDisplay.getString("commercial_mode");

            return trajet;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trajet;
    }
}
