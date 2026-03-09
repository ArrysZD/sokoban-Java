package sokoban.view;

import sokoban.model.Board;

public class ConsoleView {
    public void displayBoard(Board board) {
        // deux boucles for imbriquées
        // board.getRows(), board.getCols(), board.getCell(i, j)
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                System.out.print(board.getCell(i, j));
            }
            System.out.println();
        }
    }
}