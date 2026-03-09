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
package sokoban.controller;

import sokoban.model.Jeu;
import sokoban.view.NiveauGraphique;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EcouteurSouris implements MouseListener {
    private Jeu jeu;
    private NiveauGraphique vue;

    public EcouteurSouris(Jeu jeu, NiveauGraphique vue) {
        this.jeu = jeu;
        this.vue = vue;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Calculer sur quelle case on a cliqué
        int col = e.getX() * jeu.niveau().colonnes() / vue.getWidth();
        int lig = e.getY() * jeu.niveau().lignes() / vue.getHeight();

        // Position du pousseur
        int lp = jeu.niveau().lignePousseur();
        int cp = jeu.niveau().colonnePousseur();

        // Calculer le déplacement
        int dl = lig - lp;
        int dc = col - cp;

        // Déplacement valide seulement si case adjacente
        if (Math.abs(dl) + Math.abs(dc) == 1) {
            jeu.deplace(dl, dc);
            // Vérifier si gagné
            if (jeu.niveau().estGagne()) {
                if (!jeu.prochainNiveau()) {
                    System.exit(0);
                }
            }
            vue.repaint();
        }
    }

    // Toutes les méthodes qui suivent font partie de l'interface. Si nous ne
    // nous en servons pas, il suffit de les déclarer vides.
    // Une autre manière de faire, plus simple, est d'hériter de MouseAdapter,
    // qui est une classe qui hérite de MouseListener avec une implémentation
    // vide de toutes ses méthodes.
    @Override
    public void mouseClicked(MouseEvent e) { }
    @Override
    public void mouseReleased(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }
}