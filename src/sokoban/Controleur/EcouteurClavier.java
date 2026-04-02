/*
 * Sokoban - Encore une nouvelle version (à but pédagogique) du célèbre jeu
 * Copyright (C) 2018 Guillaume Huard
 *
 * Ce programme est libre, vous pouvez le redistribuer et/ou le
 * modifier selon les termes de la Licence Publique Générale GNU publiée par la
 * Free Software Foundation (version 2 ou bien toute autre version ultérieure
 * choisie par vous).
 *
 * Ce programme est distribué car potentiellement utile, mais SANS
 * AUCUNE GARANTIE, ni explicite ni implicite, y compris les garanties de
 * commercialisation ou d'adaptation dans un but spécifique. Reportez-vous à la
 * Licence Publique Générale GNU pour plus de détails.
 *
 * Vous devez avoir reçu une copie de la Licence Publique Générale
 * GNU en même temps que ce programme ; si ce n'est pas le cas, écrivez à la Free
 * Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307,
 * États-Unis.
 *
 * Contact:
 *          Guillaume.Huard@imag.fr
 *          Laboratoire LIG
 *          700 avenue centrale
 *          Domaine universitaire
 *          38401 Saint Martin d'Hères
 */
package sokoban.Controleur;

import sokoban.Modele.Coup;
import sokoban.Modele.Jeu;
import sokoban.Vue.InterfaceGraphique;
import sokoban.Vue.NiveauGraphique;
import sokoban.Modele.IAAssistance;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EcouteurClavier extends KeyAdapter {
    private final Jeu jeu;
    private final NiveauGraphique vue;
    private final InterfaceGraphique ig;
    private final AnimationJeuAutomatique animationIA;
    private final AnimationPousseur animationPousseur;

    public EcouteurClavier(Jeu jeu, NiveauGraphique vue, InterfaceGraphique ig,
            AnimationJeuAutomatique animationIA, AnimationPousseur animationPousseur) {
        this.jeu = jeu;
        this.vue = vue;
        this.ig = ig;
        this.animationIA = animationIA;
        this.animationPousseur = animationPousseur;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int dl = 0;
        int dc = 0;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                dl = -1;
                animationPousseur.setDirection(0);
                break;
            case KeyEvent.VK_LEFT:
                dc = -1;
                animationPousseur.setDirection(1);
                break;
            case KeyEvent.VK_DOWN:
                dl = 1;
                animationPousseur.setDirection(2);
                break;
            case KeyEvent.VK_RIGHT:
                dc = 1;
                animationPousseur.setDirection(3);
                break;
            case KeyEvent.VK_I:
                animationIA.toggleActive();
                return;
            case KeyEvent.VK_P:
                ig.toggleAnimations();
                return;
            case KeyEvent.VK_U:
                if (jeu.niveau().peutAnnuler()) {
                    jeu.niveau().annuler();
                    vue.repaint();
                }
                return;
            case KeyEvent.VK_R:
                if (jeu.niveau().peutRefaire()) {
                    jeu.niveau().refaire();
                    vue.repaint();
                }
                return;
            case KeyEvent.VK_A:
            case KeyEvent.VK_Q:
                System.exit(0);
                return;
            case KeyEvent.VK_ESCAPE:
                ig.toggleFullscreen();
                return;
            case KeyEvent.VK_S:
                IAAssistance ia = new IAAssistance();
                sokoban.structures.Sequence<sokoban.Modele.Coup> solution = ia.joue(jeu.niveau());
                if (solution != null && !solution.estVide()) {
                    System.out.println("Solution trouvée !");
                    // Debug premier coup
                    sokoban.Modele.Coup premier = solution.extraitTete();
                    System.out.println("Premier coup: pousseur "
                        + premier.ligneDepart + "," + premier.colonneDepart
                        + " -> " + premier.ligneArrivee + "," + premier.colonneArrivee);
                    System.out.println("Pousseur actuel: "
                        + jeu.niveau().lignePousseur() + "," + jeu.niveau().colonnePousseur());
                    // Remettre le premier coup dans la séquence et jouer
                    solution.insereTete(premier);
                    ig.jouerSolution(solution);
                } else {
                    System.out.println("Pas de solution trouvée");
                }
                return;
            default:
                return;
        }

        Coup coup = jeu.deplaceAvecCoup(dl, dc);
        if (coup != null) {
            jeu.niveau().faire(coup);
            jeu.niveau().videMarques();
            if (jeu.niveau().estGagne()) {
                if (!jeu.prochainNiveau()) System.exit(0);
            }
        }
        vue.repaint();
    }
}