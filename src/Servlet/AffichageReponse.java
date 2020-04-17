package Servlet;

import mypackage.CalculDistanceService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AffichageReponse extends HttpServlet {
    private static double LonA, LonB, LatA, LatB;
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        LonA = Double.valueOf(request.getParameter("LongitudeA"));
        LatA = Double.valueOf(request.getParameter("LatitudeA"));
        LonB = Double.valueOf(request.getParameter("LongitudeB"));
        LatB = Double.valueOf(request.getParameter("LatitudeB"));

        mypackage.CalculDistance service = new CalculDistanceService().getCalculDistancePort();
        out.print(service.retourneDistance(LonA, LonB, LatA, LatB));
    }
}
