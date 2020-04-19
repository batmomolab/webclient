package Format;

public class Format {
    public static String formatDate(String date){
        String annee, jour, mois, heure, minute;
        annee = date.substring(0,4);
        mois = date.substring(4,6);
        jour = date.substring(6,8);
        heure = date.substring(9,11);
        minute = date.substring(11,13);
        return "le " + jour + "/" + mois + "/" + annee + " à " + heure + "h" + minute;
    }

    public static String formatGare(String gare){
        gare.replaceAll(" ","-");
        return gare;
    }
}
