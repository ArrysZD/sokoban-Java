package sokoban;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import sokoban.io.LevelLoader;
import sokoban.model.Board;
import sokoban.view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        try {
            // 1. Ouvrir le fichier
            FileInputStream file = new FileInputStream("levels/level1.txt");
            // 2. Créer LevelLoader et ConsoleView
            LevelLoader levelLoader = new LevelLoader(file);
            ConsoleView consoleView = new ConsoleView();
            // 3. Boucle : charger niveau -> afficher -> recommencer
            Board board;
            while ((board = levelLoader.loadNextLevel()) != null) {
                consoleView.displayBoard(board);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Fichier introuvable");
        }
    }
}