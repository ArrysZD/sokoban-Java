package sokoban.model;

public class Board {
    private char[][] grid;
    private int rows;
    private int cols;
    private String name;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new char[rows][cols];
    }

    // Nom du niveau
    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    // Dimensions
    public int getRows() { return rows; }
    public int getCols() { return cols; }

    // Modifiers
    public void setCell(int i, int j, char c) { grid[i][j] = c; }
    public char getCell(int i, int j) { return grid[i][j]; }

    // Questions sur une case
    public boolean isEmpty(int i, int j)  { return grid[i][j] == ' '; }
    public boolean isWall(int i, int j)   { return grid[i][j] == '#'; }
    public boolean isBox(int i, int j)    { return grid[i][j] == '$' || grid[i][j] == '*'; }
    public boolean isGoal(int i, int j)   { return grid[i][j] == '.' || grid[i][j] == '*' || grid[i][j] == '+'; }
    public boolean isPlayer(int i, int j) { return grid[i][j] == '@' || grid[i][j] == '+'; }
}