package sokoban.Modele;

public interface Commande {
    void executer(Niveau niveau);
    void desexecuter(Niveau niveau);
}