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
    private sokoban.structures.Sequence<sokoban.Modele.Coup> solutionEnCours = null;
    private int delaiSolution = 0;
    private static final int DELAI_ENTRE_COUPS = 300;
    private NiveauGraphique niveauGraphique;

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

    public void jouerSolution(sokoban.structures.Sequence<sokoban.Modele.Coup> solution) {
        this.solutionEnCours = solution;
        this.delaiSolution = 0;
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

    private JPanel creeBarreLaterale(NiveauGraphique ng) {
        Box boite = Box.createVerticalBox();

        JLabel titre = new JLabel("Sokoban");
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        titre.setFont(new Font("Arial", Font.BOLD, 18));
        boite.add(titre);
        boite.add(Box.createGlue());

        labelPas = new JLabel("Pas : 0");
        labelPas.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelPoussees = new JLabel("Poussées : 0");
        labelPoussees.setAlignmentX(Component.CENTER_ALIGNMENT);
        boite.add(labelPas);
        boite.add(labelPoussees);
        boite.add(Box.createGlue());

        btnIA = new JToggleButton("IA");
        btnIA.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIA.setFocusable(false);
        btnIA.addActionListener(e -> toggleIA());
        boite.add(btnIA);

        btnAnimation = new JToggleButton("Animation");
        btnAnimation.setSelected(true);
        btnAnimation.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAnimation.setFocusable(false);
        btnAnimation.addActionListener(e -> toggleAnimations());
        boite.add(btnAnimation);
        boite.add(Box.createGlue());

        JButton btnProchain = new JButton("Prochain");
        btnProchain.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnProchain.setFocusable(false);
        btnProchain.addActionListener(e -> {
            if (!jeu.prochainNiveau()) System.exit(0);
            ng.repaint();
            miseAJour();
        });
        boite.add(btnProchain);
        boite.add(Box.createGlue());

        JPanel panelAR = new JPanel();
        btnAnnuler = new JButton("<");
        btnRefaire = new JButton(">");
        btnAnnuler.setFocusable(false);
        btnRefaire.setFocusable(false);
        btnAnnuler.addActionListener(e -> {
            jeu.niveau().annuler();
            ng.repaint();
            miseAJour();
        });
        btnRefaire.addActionListener(e -> {
            jeu.niveau().refaire();
            ng.repaint();
            miseAJour();
        });
        panelAR.add(btnAnnuler);
        panelAR.add(btnRefaire);
        panelAR.setAlignmentX(Component.CENTER_ALIGNMENT);
        boite.add(panelAR);
        boite.add(Box.createGlue());

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
        niveauGraphique = new NiveauGraphique(jeu);

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
            if (solutionEnCours != null && !solutionEnCours.estVide()) {
                delaiSolution += 16;
                if (delaiSolution >= DELAI_ENTRE_COUPS) {
                    delaiSolution = 0;
                    sokoban.Modele.Coup coup = solutionEnCours.extraitTete();
                    coup.executer(jeu.niveau());
                }
            }
            niveauGraphique.repaint();
            miseAJour();
        });
        timer.start();
    }
}