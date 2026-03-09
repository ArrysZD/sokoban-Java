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
    }
}