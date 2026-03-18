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

import sokoban.Modele.Jeu;
import sokoban.Vue.NiveauGraphique;
import sokoban.Vue.InterfaceGraphique;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class EcouteurClavier extends KeyAdapter {
    private Jeu jeu;
    private NiveauGraphique vue;
    private InterfaceGraphique ig;

    public EcouteurClavier(Jeu jeu, NiveauGraphique vue, InterfaceGraphique ig) {
        this.jeu = jeu;
        this.vue = vue;
        this.ig = ig;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int dl = 0, dc = 0;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:     dl = -1; break;
            case KeyEvent.VK_DOWN:   dl =  1; break;
            case KeyEvent.VK_LEFT:   dc = -1; break;
            case KeyEvent.VK_RIGHT:  dc =  1; break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_Q:      System.exit(0); return;
            case KeyEvent.VK_ESCAPE: ig.toggleFullscreen(); return;
            default: return;
        }

        jeu.deplace(dl, dc);
        if (jeu.niveau().estGagne()) {
            if (!jeu.prochainNiveau()) System.exit(0);
        }
        vue.repaint();
    }
    // KeyAdapter fournit déjà des implémentations vides
    // de keyReleased et keyTyped, pas besoin de les réécrire
}