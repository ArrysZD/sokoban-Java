package sokoban.global;

import java.io.FileInputStream;
import java.io.InputStream;

public class Configuration {
    private static boolean afficherAvertissements = true;
    private static boolean afficherMessages = true;

    public static InputStream ouvre(String chemin) {
        try {
            return new FileInputStream("res/" + chemin);
        } catch (Exception e) {
            erreur("Impossible d'ouvrir le fichier : " + chemin);
            return null;
        }
    }

    public static void avertissement(String message) {
        if (afficherAvertissements && afficherMessages) {
            System.err.println("AVERTISSEMENT : " + message);
        }
    }

    public static void erreur(String message) {
        if (afficherMessages) {
            System.err.println("ERREUR : " + message);
        }
        System.exit(1);
    }

    public static void desactiverAvertissements() {
        afficherAvertissements = false;
    }

    public static void desactiverTousMessages() {
        afficherMessages = false;
    }

    public static <T> sokoban.structures.SequenceListe<T> nouvelleSequence() {
    return new sokoban.structures.SequenceListe<>();
}
}