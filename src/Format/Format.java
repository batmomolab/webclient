package Format;


//Cette classe contient les méthodes utile pour formater différente données
public class Format {
    public static String formatDate(String date){ //Formate la date dans une forme facilement lisible par l'humain
        String annee, jour, mois, heure, minute;
        annee = date.substring(0,4);
        mois = date.substring(4,6);
        jour = date.substring(6,8);
        heure = date.substring(9,11);
        minute = date.substring(11,13);
        return "le " + jour + "/" + mois + "/" + annee + " à " + heure + "h" + minute;
    }

    public static String formatGare(String gare){ //Formate le nom d'une gare de façon à ce qu'il soit exploitable par une requête
        gare.replaceAll(" ","-");
        return gare;
    }

    public static String formatDateReq(String date, String heure){ //Formate la date de façon à ce qu'elle soit exploitable par une requête
        return date.substring(0,4)+date.substring(5,7)+date.substring(8,10)+"T"+heure.substring(0,2)+heure.substring(3,5)+"00";
    }
}
