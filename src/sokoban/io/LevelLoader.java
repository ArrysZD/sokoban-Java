package sokoban.io;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.InputStream;
import sokoban.model.Board;

public class LevelLoader {
    private Scanner scanner;

    public LevelLoader(InputStream in) {
        this.scanner = new Scanner(in);
    }

   public Board loadNextLevel() {
    ArrayList<String> lines = new ArrayList<>();
    String name = "";

    // 1. Lire les lignes jusqu'à ligne vide ou fin fichier
    while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (line.isEmpty()) break;        // niveau terminé
        if (line.startsWith(";")) {
            name = line.substring(1);     // commentaire = nom
        } else {
            lines.add(line);              // ligne de grille
        }
    }

    // 2. Si rien lu -> fin du fichier
    if (lines.isEmpty()) return null;

    // 3. Créer le board avec les bonnes dimensions
    int rows = lines.size();
    int cols = 0;
    for (String line : lines) {
        if (line.length() > cols) cols = line.length();
    }

    Board board = new Board(rows, cols);
    board.setName(name);

    // 4. Remplir la grille
    for (int i = 0; i < rows; i++) {
        String line = lines.get(i);
        for (int j = 0; j < line.length(); j++) {
            board.setCell(i, j, line.charAt(j));
        }
    }
    return board;
    }
}