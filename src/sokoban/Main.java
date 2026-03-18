package sokoban;
import sokoban.global.Configuration;
import sokoban.Modele.Jeu;
import sokoban.Vue.InterfaceGraphique;
import java.io.FileInputStream;
import javax.swing.*;
import java.io.InputStream;
public class Main {
    public static void main(String[] args) {
        try {
            InputStream file = Configuration.ouvre("levels/level1.txt");
            Jeu jeu = new Jeu(file);
            int n = Integer.parseInt(args[0]);
            for (int i = 0; i < n; i++) {
                jeu.prochainNiveau();
            }
            SwingUtilities.invokeLater(new InterfaceGraphique(jeu));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}