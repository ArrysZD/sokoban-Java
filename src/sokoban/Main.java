package sokoban;

import java.io.FileInputStream;
import sokoban.io.LecteurNiveaux;
import sokoban.model.Niveau;
import sokoban.view.RedacteurNiveau;

public class Main {
    public static void main(String[] args) throws Exception {
        LecteurNiveaux lecteur = new LecteurNiveaux(new FileInputStream("levels/level1.txt"));
        RedacteurNiveau redacteur = new RedacteurNiveau(System.out);
        Niveau niveau;
        while ((niveau = lecteur.lisProchainNiveau()) != null) {
            redacteur.ecrisNiveau(niveau);
        }
    }
}