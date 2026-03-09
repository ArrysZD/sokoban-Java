package sokoban.view;
import sokoban.global.Configuration;
import sokoban.model.Jeu;
import sokoban.model.Niveau;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import sokoban.controller.EcouteurSouris;


public class NiveauGraphique extends JComponent {
    Jeu jeu;
    Image imgMur;
    Image imgPousseur;
    Image imgBut;
    Image imgCaisse;
    Image imgCaisseBut;
    Image imgSol;

    NiveauGraphique(Jeu jeu){
        this.jeu = jeu;
        try{
            imgMur = ImageIO.read(Configuration.ouvre("Images/Mur.png"));
            imgPousseur = ImageIO.read(Configuration.ouvre("Images/Pousseur.png"));
            imgBut = ImageIO.read(Configuration.ouvre("Images/But.png"));
            imgCaisse = ImageIO.read(Configuration.ouvre("Images/Caisse.png"));
            imgCaisseBut = ImageIO.read(Configuration.ouvre("Images/Caisse_sur_but.png"));
            imgSol = ImageIO.read(Configuration.ouvre("Images/Sol.png"));
        } catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
            addMouseListener(new EcouteurSouris(jeu, this));

    }

    @Override
    public void paintComponent(Graphics g){
        Niveau tmp = jeu.niveau();
        Graphics2D drawable = (Graphics2D) g;
        int largeur = getWidth()/tmp.colonnes();
        int hauteur = getHeight()/tmp.lignes();
        drawable.clearRect(0, 0, getWidth(), getHeight());
        for (int i = 0; i < tmp.lignes(); i++) {
            for (int j = 0; j < tmp.colonnes(); j++) {
                switch (tmp.getCase(i,j)) {
                    case '#':
                        drawable.drawImage(imgMur, j*largeur, i*hauteur, largeur, hauteur, null);
                        break;
                    case '@':
                        drawable.drawImage(imgPousseur, j*largeur, i*hauteur, largeur, hauteur, null);
                        break;
                    case '.':
                        drawable.drawImage(imgBut, j*largeur, i*hauteur, largeur, hauteur, null);
                        break;
                    case '$':
                        drawable.drawImage(imgCaisse, j*largeur, i*hauteur, largeur, hauteur, null);
                        break;
                    case '*':
                        drawable.drawImage(imgCaisseBut, j*largeur, i*hauteur, largeur, hauteur, null);
                        break;
                    case ' ':
                        drawable.drawImage(imgSol, j*largeur, i*hauteur, largeur, hauteur, null);
                        break;
                    case '+':
                        drawable.drawImage(imgPousseur, j*largeur, i*hauteur, largeur, hauteur, null);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}