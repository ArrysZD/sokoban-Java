package sokoban.structures;
public class TestFAP {
    public static void main(String[] args) {
FAP<Couple<String, Integer>> fap = new FAP<>();
fap.insere(new Couple<>("tâche A", 3));
fap.insere(new Couple<>("tâche B", 1));
fap.insere(new Couple<>("tâche C", 2));
System.out.println(fap.extraitMin()); // (tâche B, 1)
    }
}