package Servlet;

import mypackage.CalculDistanceService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AffichageReponse extends HttpServlet {
    private static double LonA, LonB, LatA, LatB;
    private static String devise;
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        LonA = Double.valueOf(request.getParameter("LongitudeA"));
        LatA = Double.valueOf(request.getParameter("LatitudeA"));
        LonB = Double.valueOf(request.getParameter("LongitudeB"));
        LatB = Double.valueOf(request.getParameter("LatitudeB"));
        devise = request.getParameter("devise");

        mypackage.CalculDistance service = new CalculDistanceService().getCalculDistancePort();
        double distance = service.retourneDistance(LonA, LonB, LatA, LatB);
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
    }
}
