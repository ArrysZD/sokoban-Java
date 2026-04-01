package sokoban.Modele;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class Coup implements Commande {
    public int ligneDepart;
    public int colonneDepart;
    public int ligneArrivee;
    public int colonneArrivee;

    public boolean caisseBougee;
    public int ligneDepartCaisse;
    public int colonneDepartCaisse;
    public int ligneArriveeCaisse;
    public int colonneArriveeCaisse;

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

    @Override
    public void executer(Niveau nv) {
        boolean butDepart = nv.aBut(ligneDepart, colonneDepart);
        nv.videCase(ligneDepart, colonneDepart);
        if (butDepart) {
            nv.ajouteBut(ligneDepart, colonneDepart);
        }

        nv.ajoutePousseur(ligneArrivee, colonneArrivee);

        if (caisseBougee) {
            boolean butDepartCaisse = nv.aBut(ligneDepartCaisse, colonneDepartCaisse);
            nv.videCase(ligneDepartCaisse, colonneDepartCaisse);
            if (butDepartCaisse) {
                nv.ajouteBut(ligneDepartCaisse, colonneDepartCaisse);
            }
            nv.ajouteCaisse(ligneArriveeCaisse, colonneArriveeCaisse);
        }
    }

    @Override
    public void desexecuter(Niveau nv) {
        boolean butArrivee = nv.aBut(ligneArrivee, colonneArrivee);
        nv.videCase(ligneArrivee, colonneArrivee);
        if (butArrivee) {
            nv.ajouteBut(ligneArrivee, colonneArrivee);
        }

        nv.ajoutePousseur(ligneDepart, colonneDepart);

        if (caisseBougee) {
            boolean butArriveeCaisse = nv.aBut(ligneArriveeCaisse, colonneArriveeCaisse);
            nv.videCase(ligneArriveeCaisse, colonneArriveeCaisse);
            if (butArriveeCaisse) {
                nv.ajouteBut(ligneArriveeCaisse, colonneArriveeCaisse);
            }
            nv.ajouteCaisse(ligneDepartCaisse, colonneDepartCaisse);
        }
    }

    public void annulerCoup(Niveau nv) {
        desexecuter(nv);
    }
}