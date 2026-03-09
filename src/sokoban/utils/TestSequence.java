package sokoban.utils;

public class TestSequence {
    public static void main(String[] args) {
        // Test SequenceListe
        SequenceListe liste = new SequenceListe();
        liste.insereQueue(1);
        liste.insereQueue(2);
        liste.insereTete(0);
        System.out.println("Liste : " + liste);
        System.out.println("Extrait : " + liste.extraitTete());
        System.out.println("Liste apres extraction : " + liste);

        // Test SequenceTableau
        SequenceTableau tableau = new SequenceTableau(3);
        tableau.insereQueue(1);
        tableau.insereQueue(2);
        tableau.insereTete(0);
        System.out.println("Tableau : " + tableau);
        System.out.println("Extrait : " + tableau.extraitTete());
        System.out.println("Tableau apres extraction : " + tableau);

        // Test redimensionnement
        SequenceTableau petit = new SequenceTableau(2);
        petit.insereQueue(1);
        petit.insereQueue(2);
        petit.insereQueue(3); // doit agrandir automatiquement
        petit.insereQueue(4);
        System.out.println("Apres agrandissement : " + petit);

        // Test iterateur - supprimer les zeros
        SequenceListe liste2 = new SequenceListe();
        liste2.insereQueue(1);
        liste2.insereQueue(0);
        liste2.insereQueue(2);
        liste2.insereQueue(0);
        liste2.insereQueue(3);
        System.out.println("Avant : " + liste2);

        Iterateur it = liste2.iterateur();
        while (it.aProchain()) {
            int n = it.prochain();
            if (n == 0) it.supprime();
        }
        System.out.println("Apres suppression des zeros : " + liste2);




        // Test iterateur tableau - supprimer les zeros
SequenceTableau tableau2 = new SequenceTableau(5);
tableau2.insereQueue(1);
tableau2.insereQueue(9);
tableau2.insereQueue(2);
tableau2.insereQueue(0);
tableau2.insereQueue(3);
tableau2.insereQueue(0);
System.out.println("Avant : " + tableau2);

Iterateur it2 = tableau2.iterateur();
while (it2.aProchain()) {
    int n = it2.prochain();
    if (n == 0) it2.supprime();
}
System.out.println("Apres suppression des zeros : " + tableau2);
    }
}