package sokoban.Vue;

import sokoban.Modele.Jeu;
import sokoban.Controleur.EcouteurClavier;
import javax.swing.*;
import java.awt.*;

public class InterfaceGraphique implements Runnable {
    Jeu jeu;
    private JFrame frame;
    private boolean maximized = false;

    public InterfaceGraphique(Jeu jeu) {
        this.jeu = jeu;
    }

    public void toggleFullscreen() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        if (maximized) {
            device.setFullScreenWindow(null);
            maximized = false;
        } else {
            device.setFullScreenWindow(frame);
            maximized = true;
        }
    }

    public void run() {
        frame = new JFrame("Sokoban");
        NiveauGraphique niveauGraphique = new NiveauGraphique(jeu);
        frame.add(niveauGraphique);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Le clavier s'attache à la fenêtre car elle a le focus
        frame.addKeyListener(new EcouteurClavier(jeu, niveauGraphique, this));
        frame.setSize(500, 300);
        frame.setVisible(true);
        toggleFullscreen();
    }
}