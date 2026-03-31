package sokoban.Modele;

import java.util.Random;

public class AleatoireCoupAi {
    private Random randomDir = new Random();

    public Direction retournCoupAi(Niveau nv) {
        return Direction.values()[randomDir.nextInt(4)];
    }
}