package Servlet;

import Format.Format;
import mypackage.CalculDistanceService;
import org.json.JSONArray;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//Cette classe contient toute les interactions entre le client et les différents services rest et soap

public class AffichageReponse extends HttpServlet {
    private static String devise, dateDepart, dateArrivee, horaire, prix;
    private static String[] trajet;
    private static int uicDepart, uicArrivee;
    private static double distance;
    private static JSONArray coordDepart, coordArrivee;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        mypackage.CalculDistance service = new CalculDistanceService().getCalculDistancePort();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        //Récupération des coordonées GPS des gares
        coordDepart = RequestSncf.reqCoordonees(Format.formatGare(request.getParameter("depart")));
        coordArrivee = RequestSncf.reqCoordonees(Format.formatGare(request.getParameter("arrivee")));

        //Récupération de la monnaie voulue à partir du formulaire
        devise = request.getParameter("devise");

        //Récupération des UIC des gares
        uicDepart = RequestSncf.reqUic(request.getParameter("depart"));
        uicArrivee = RequestSncf.reqUic(request.getParameter("arrivee"));

        //On formate la date entrée par le client pour qu'elles soient exploitable dans une requête
        horaire=Format.formatDateReq(request.getParameter("datedepart"),request.getParameter("heuredepart"));

        //On effectue la requête du trajet
        trajet = RequestSncf.reqTrajet(uicDepart,uicArrivee,horaire);

        //On formate les dates obtenues
        dateDepart = Format.formatDate(trajet[0]);
        dateArrivee = Format.formatDate(trajet[1]);

        //On interroge notre service Soap pour qu'il nous renvoi la distance à vol d'oiseau entre les deux gares
        distance = service.retourneDistance(coordDepart.getDouble(1), coordDepart.getDouble(0), coordArrivee.getDouble(1), coordArrivee.getDouble(0));

        //On interroge notre service Rest pour qu'il nous renvoi un prix de notre trajet en fonction de la monnaie
        prix = CalculPrixClient.CalculPrix(distance, devise);

        //On affiche le résumé du trajet trouvé sur la page de résultat
        out.print("Trajet "+trajet[2]+" > "+trajet[3]+ "</br>Départ du train "+dateDepart+"</br>Arrivée du train "+dateArrivee+"</br>");
        if (devise.equals("Euro")){
            out.print("Prix du voyage : "+prix+"Euros</br>");
        } else if (devise.equals("Dollar")){
            out.print("Prix du voyage : "+prix+"Dollars</br>");
        } else if (devise.equals("Yen")){
            out.print("Prix du voyage : "+prix+"Yens</br>");
        } else {
            out.print("erreur devise</br>");
        }
        out.print("Ce train est un "+trajet[5]+" à destination de "+trajet[4]+".");
        out.print("<form action=\"https://trouve-ton-train-labeaume.herokuapp.com/\"><input type=\"submit\" value=\"Faire une autre recherche\" /></form>");
    }
}
