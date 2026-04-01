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

    // Labels mis à jour dynamiquement
    private JLabel labelPas;
    private JLabel labelPoussees;
    private JToggleButton btnIA;
    private JToggleButton btnAnimation;
    private JButton btnAnnuler;
    private JButton btnRefaire;

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
        if (btnAnimation != null) {
            btnAnimation.setSelected(animationsActives);
        }
    }

    public void toggleIA() {
        animationIA.toggleActive();
        if (btnIA != null) {
            btnIA.setSelected(animationIA.isActive());
        }
    }

    public void miseAJour() {
        if (labelPas != null)
            labelPas.setText("Pas : " + jeu.getNbPas());
        if (labelPoussees != null)
            labelPoussees.setText("Poussées : " + jeu.getNbPoussees());
        if (btnAnnuler != null)
            btnAnnuler.setEnabled(jeu.niveau().peutAnnuler());
        if (btnRefaire != null)
            btnRefaire.setEnabled(jeu.niveau().peutRefaire());
    }

    private JPanel creeBarreLaterale(NiveauGraphique niveauGraphique) {
        Box boite = Box.createVerticalBox();

        // Titre
        JLabel titre = new JLabel("Sokoban");
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        titre.setFont(new Font("Arial", Font.BOLD, 18));
        boite.add(titre);
        boite.add(Box.createGlue());

        // Compteurs
        labelPas = new JLabel("Pas : 0");
        labelPas.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelPoussees = new JLabel("Poussées : 0");
        labelPoussees.setAlignmentX(Component.CENTER_ALIGNMENT);
        boite.add(labelPas);
        boite.add(labelPoussees);
        boite.add(Box.createGlue());

        // Bouton IA
        btnIA = new JToggleButton("IA");
        btnIA.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIA.setFocusable(false);
        btnIA.addActionListener(e -> toggleIA());
        boite.add(btnIA);

        // Bouton Animation
        btnAnimation = new JToggleButton("Animation");
        btnAnimation.setSelected(true);
        btnAnimation.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAnimation.setFocusable(false);
        btnAnimation.addActionListener(e -> toggleAnimations());
        boite.add(btnAnimation);
        boite.add(Box.createGlue());

        // Bouton Prochain niveau
        JButton btnProchain = new JButton("Prochain");
        btnProchain.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnProchain.setFocusable(false);
        btnProchain.addActionListener(e -> {
            if (!jeu.prochainNiveau()) System.exit(0);
            niveauGraphique.repaint();
            miseAJour();
        });
        boite.add(btnProchain);
        boite.add(Box.createGlue());

        // Boutons Annuler / Refaire
        JPanel panelAR = new JPanel();
        btnAnnuler = new JButton("<");
        btnRefaire = new JButton(">");
        btnAnnuler.setFocusable(false);
        btnRefaire.setFocusable(false);
        btnAnnuler.addActionListener(e -> {
            jeu.niveau().annuler();
            niveauGraphique.repaint();
            miseAJour();
        });
        btnRefaire.addActionListener(e -> {
            jeu.niveau().refaire();
            niveauGraphique.repaint();
            miseAJour();
        });
        panelAR.add(btnAnnuler);
        panelAR.add(btnRefaire);
        panelAR.setAlignmentX(Component.CENTER_ALIGNMENT);
        boite.add(panelAR);
        boite.add(Box.createGlue());

        // Copyright
        JLabel copyright = new JLabel("Copyright G. Huard, 2018");
        copyright.setAlignmentX(Component.CENTER_ALIGNMENT);
        copyright.setFont(new Font("Arial", Font.ITALIC, 10));
        boite.add(copyright);

        JPanel panel = new JPanel();
        panel.add(boite);
        return panel;
    }

    @Override
    public void run() {
        frame = new JFrame("Sokoban");
        NiveauGraphique niveauGraphique = new NiveauGraphique(jeu);

        animationIA = new AnimationJeuAutomatique(jeu, niveauGraphique);
        animationPousseur = new AnimationPousseur(niveauGraphique);

        frame.add(niveauGraphique, BorderLayout.CENTER);
        frame.add(creeBarreLaterale(niveauGraphique), BorderLayout.EAST);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(new EcouteurClavier(
                jeu,
                niveauGraphique,
                this,
                animationIA,
                animationPousseur
        ));
        frame.setSize(700, 500);
        frame.setVisible(true);

        Timer timer = new Timer(16, e -> {
            animationPousseur.avance();
            animationIA.avance();
            niveauGraphique.repaint();
            miseAJour();
        });
        timer.start();
    }
}