package sokoban.view;

import sokoban.model.Jeu;
import javax.swing.*;

public class InterfaceGraphique implements Runnable {
    Jeu jeu;

    public InterfaceGraphique(Jeu jeu) {
        this.jeu = jeu;
    }

    public void run() {
        JFrame frame = new JFrame("Ma fenetre a moi :) ");
        frame.add(new NiveauGraphique(jeu));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setVisible(true);
    }
}
