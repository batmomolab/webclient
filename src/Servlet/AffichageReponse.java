package Servlet;

import Format.Format;
import mypackage.CalculDistanceService;
import org.json.JSONArray;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AffichageReponse extends HttpServlet {
    private static double LonA, LonB, LatA, LatB;
    private static String devise;
    private static String[] date;
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        JSONArray coordDepart = RequestSncf.reqCoordonees(Format.formatGare(request.getParameter("depart")));
        JSONArray coordArrivee = RequestSncf.reqCoordonees(Format.formatGare(request.getParameter("arrivee")));
        int uicDepart = RequestSncf.reqUic(request.getParameter("depart"));
        int uicArrivee = RequestSncf.reqUic(request.getParameter("arrivee"));
        date = RequestSncf.reqTrajet(uicDepart,uicArrivee);
        String dateDepart = Format.formatDate(date[0]);
        String dateArrivee = Format.formatDate(date[1]);


        /* Ancien formulaire
        LonA = Double.valueOf(request.getParameter("LongitudeA"));
        LatA = Double.valueOf(request.getParameter("LatitudeA"));
        LonB = Double.valueOf(request.getParameter("LongitudeB"));
        LatB = Double.valueOf(request.getParameter("LatitudeB"));
         */
        devise = request.getParameter("devise");

        mypackage.CalculDistance service = new CalculDistanceService().getCalculDistancePort();
        double distance = service.retourneDistance(coordDepart.getDouble(1), coordDepart.getDouble(0), coordArrivee.getDouble(1), coordArrivee.getDouble(0));
        String prix = CalculPrixClient.CalculPrix(distance, devise);
        if (devise.equals("Euro")){
            out.print("La distance à parcourir est de "+distance+"km, ce voyage vous coutera "+prix+"Euros");
        } else if (devise.equals("Dollar")){
            out.print("La distance à parcourir est de "+distance+"km, ce voyage vous coutera "+prix+"Dollars");
        } else if (devise.equals("Yen")){
            out.print("La distance à parcourir est de "+distance+"km, ce voyage vous coutera "+prix+"Yens");
        } else {
            out.print("erreur avec le renvoi de devise frr :,(");
        }
        out.println("Départ du train "+dateDepart);
        out.println("Arrivée du train "+dateArrivee);
    }
}
