package sokoban.io;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import sokoban.Modele.Niveau;

public class LecteurNiveaux {
    private Scanner scanner;

    public LecteurNiveaux(InputStream input) {
        this.scanner = new Scanner(input);
    }

    public Niveau lisProchainNiveau() {
        ArrayList<String> lignes = new ArrayList<>();
        String nomNiveau = "";

        // Lecture jusqu'à ligne vide ou fin fichier
        while (scanner.hasNextLine()) {
            String ligne = scanner.nextLine();
            if (ligne.trim().isEmpty()) {
                if (!lignes.isEmpty()) break;
            } else if (ligne.trim().startsWith(";")) {
                nomNiveau = ligne.substring(1).trim();
            } else {
                lignes.add(ligne);
            }
        }

        if (lignes.isEmpty()) return null;

        // Calcul dimensions
        int nbLignes = lignes.size();
        int nbColonnes = 0;
        for (String l : lignes) {
            if (l.length() > nbColonnes) nbColonnes = l.length();
        }

        Niveau niveau = new Niveau(nbLignes, nbColonnes);
        niveau.fixeNom(nomNiveau);

        // Remplissage
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                char c = j < lignes.get(i).length() ? lignes.get(i).charAt(j) : ' ';
                switch (c) {
                    case '#': niveau.ajouteMur(i, j);      break;
                    case '$': niveau.ajouteCaisse(i, j);   break;
                    case '*': niveau.ajouteBut(i, j);
                              niveau.ajouteCaisse(i, j);   break;
                    case '+': niveau.ajouteBut(i, j);
                              niveau.ajoutePousseur(i, j); break;
                    case '.': niveau.ajouteBut(i, j);      break;
                    case '@': niveau.ajoutePousseur(i, j); break;
                    default:  niveau.videCase(i, j);
                }
            }
        }
        return niveau;
    }
}