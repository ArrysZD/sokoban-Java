package sokoban.Vue;

import sokoban.global.Configuration;
import sokoban.Modele.Jeu;
import sokoban.Modele.Niveau;
import sokoban.Controleur.EcouteurSouris;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class NiveauGraphique extends JComponent {
    private final Jeu jeu;

    private Image imgMur;
    private Image imgPousseur;
    private Image imgBut;
    private Image imgCaisse;
    private Image imgCaisseBut;
    private Image imgSol;

    private Image[][] imgPousseurAnim;
    private final Map<String, float[]> decalages = new HashMap<>();

    NiveauGraphique(Jeu jeu) {
        this.jeu = jeu;
        try {
            imgMur = ImageIO.read(Configuration.ouvre("Images/Mur.png"));
            imgPousseur = ImageIO.read(Configuration.ouvre("Images/Pousseur.png"));
            imgBut = ImageIO.read(Configuration.ouvre("Images/But.png"));
            imgCaisse = ImageIO.read(Configuration.ouvre("Images/Caisse.png"));
            imgCaisseBut = ImageIO.read(Configuration.ouvre("Images/Caisse_sur_but.png"));
            imgSol = ImageIO.read(Configuration.ouvre("Images/Sol.png"));

            imgPousseurAnim = new Image[4][4];
            for (int direction = 0; direction < 4; direction++) {
                for (int frame = 0; frame < 4; frame++) {
                    imgPousseurAnim[direction][frame] = ImageIO.read(
                        Configuration.ouvre("Images_/Pousseur_" + direction + "_" + frame + ".png")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        addMouseListener(new EcouteurSouris(jeu, this));
    }

    public void setDecalage(int col, int ligne, float dx, float dy) {
        decalages.put(ligne + "," + col, new float[]{dx, dy});
    }

    public void supprimeDecalage(int col, int ligne) {
        decalages.remove(ligne + "," + col);
    }

    public void supprimeTousLesDecalages() {
        decalages.clear();
    }

    public Image getImageAnim(int direction, int frame) {
        return imgPousseurAnim[direction][frame];
    }

    public void setImagePousseur(Image img) {
        imgPousseur = img;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Niveau tmp = jeu.niveau();
        Graphics2D drawable = (Graphics2D) g;

        int largeur = getWidth() / tmp.colonnes();
        int hauteur = getHeight() / tmp.lignes();

        drawable.setColor(Color.BLACK);
        drawable.fillRect(0, 0, getWidth(), getHeight());

        // Fond statique
        for (int i = 0; i < tmp.lignes(); i++) {
            for (int j = 0; j < tmp.colonnes(); j++) {
                int x = j * largeur;
                int y = i * hauteur;

                switch (tmp.getCase(i, j)) {
                    case '#':
                        drawable.drawImage(imgMur, x, y, largeur, hauteur, null);
                        break;

                    case '.':
                        drawable.drawImage(imgBut, x, y, largeur, hauteur, null);
                        break;

                    case '*':
                        drawable.drawImage(imgBut, x, y, largeur, hauteur, null);
                        break;

                    case '+':
                        drawable.drawImage(imgBut, x, y, largeur, hauteur, null);
                        break;

                    case '@':
                    case '$':
                    case ' ':
                        drawable.drawImage(imgSol, x, y, largeur, hauteur, null);
                        break;

                    default:
                        break;
                }
            }
        }

        // Eléments mobiles avec décalage
        for (int i = 0; i < tmp.lignes(); i++) {
            for (int j = 0; j < tmp.colonnes(); j++) {
                char c = tmp.getCase(i, j);

                float[] dec = decalages.get(i + "," + j);
                int offsetX = dec != null ? (int) (dec[0] * largeur) : 0;
                int offsetY = dec != null ? (int) (dec[1] * hauteur) : 0;

                int x = j * largeur + offsetX;
                int y = i * hauteur + offsetY;

                switch (c) {
                    case '@':
                    case '+':
                        drawable.drawImage(imgPousseur, x, y, largeur, hauteur, null);
                        break;

                    case '$':
                        drawable.drawImage(imgCaisse, x, y, largeur, hauteur, null);
                        break;

                    case '*':
                        drawable.drawImage(imgCaisseBut, x, y, largeur, hauteur, null);
                        break;

                    default:
                        break;
                }
            }
        }

        // Marques
        for (int i = 0; i < tmp.lignes(); i++) {
            for (int j = 0; j < tmp.colonnes(); j++) {
                Color marque = tmp.getMarque(i, j);
                if (marque != null) {
                    int x = j * largeur;
                    int y = i * hauteur;

                    drawable.setColor(marque);
                    drawable.setStroke(new BasicStroke(4));
                    drawable.drawLine(x, y, x + largeur, y + hauteur);
                    drawable.drawLine(x + largeur, y, x, y + hauteur);
                }
            }
        }
    }
}