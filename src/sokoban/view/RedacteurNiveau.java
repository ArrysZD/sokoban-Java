package sokoban.view;

import java.io.OutputStream;
import java.io.PrintStream;
import sokoban.model.Niveau;

public class RedacteurNiveau {
    private PrintStream output;

    public RedacteurNiveau(OutputStream outputStream) {
        this.output = new PrintStream(outputStream);
    }

    public void ecrisNiveau(Niveau niveau) {
        for (int ligne = 0; ligne < niveau.lignes(); ligne++) {
            for (int col = 0; col < niveau.colonnes(); col++) {
                this.output.print(niveau.getCase(ligne, col));
            }
            this.output.println();
        }
        this.output.println("; " + niveau.nom());
        this.output.println();
    }
}