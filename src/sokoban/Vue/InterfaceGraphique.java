package sokoban.Vue;

import sokoban.Modele.Jeu;
import sokoban.Controleur.AnimationJeuAutomatique;
import sokoban.Controleur.AnimationPousseur;
import sokoban.Controleur.EcouteurClavier;

import javax.swing.*;
import java.awt.*;

public class InterfaceGraphique implements Runnable {
    private final Jeu jeu;
    private JFrame frame;
    private boolean maximized = false;

    private AnimationJeuAutomatique animationIA;
    private AnimationPousseur animationPousseur;
    private boolean animationsActives = true;

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

    public void toggleAnimations() {
        animationsActives = !animationsActives;
        if (animationPousseur != null) {
            animationPousseur.setActive(animationsActives);
        }
    }

    @Override
    public void run() {
        frame = new JFrame("Sokoban");
        NiveauGraphique niveauGraphique = new NiveauGraphique(jeu);

        animationIA = new AnimationJeuAutomatique(jeu, niveauGraphique);
        animationPousseur = new AnimationPousseur(niveauGraphique);

        frame.add(niveauGraphique);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(new EcouteurClavier(
                jeu,
                niveauGraphique,
                this,
                animationIA,
                animationPousseur
        ));
        frame.setSize(500, 300);
        frame.setVisible(true);
        toggleFullscreen();

        Timer timer = new Timer(16, e -> {
            animationPousseur.avance();
            animationIA.avance();
            niveauGraphique.repaint();
        });
        timer.start();
    }
}