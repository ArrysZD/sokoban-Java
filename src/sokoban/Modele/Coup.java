package sokoban.Modele;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class Coup {
    public int ligneDepart, colonneDepart;
    public int ligneArrivee, colonneArrivee;

    public boolean caisseBougee;
    public int ligneDepartCaisse, colonneDepartCaisse;
    public int ligneArriveeCaisse, colonneArriveeCaisse;

    private Map<String, Color> marques = new HashMap<>();

    public Coup(int ld, int cd, int la, int ca) {
        this.ligneDepart = ld;
        this.colonneDepart = cd;
        this.ligneArrivee = la;
        this.colonneArrivee = ca;
        this.caisseBougee = false;
    }

    public void setCaisse(int ld, int cd, int la, int ca) {
        this.caisseBougee = true;
        this.ligneDepartCaisse = ld;
        this.colonneDepartCaisse = cd;
        this.ligneArriveeCaisse = la;
        this.colonneArriveeCaisse = ca;
    }

    public void ajouteMarque(int ligne, int colonne, Color couleur) {
        marques.put(ligne + "," + colonne, couleur);
    }

    public Map<String, Color> getMarques() {
        return marques;
    }
}