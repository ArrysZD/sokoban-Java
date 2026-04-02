package sokoban.Modele;

import sokoban.global.Configuration;
import sokoban.structures.Sequence;
import java.util.*;

public class IAAssistance  {

    private boolean[][] casesMortes;

    public IAAssistance() {}

    public static HashSet<String> casesAccessibles(Niveau n) {
        HashSet<String> accessibles = new HashSet<>();
        Queue<int[]> file = new LinkedList<>();
        int pl = n.lignePousseur(), pc = n.colonnePousseur();
        file.add(new int[]{pl, pc});
        accessibles.add(pl + "," + pc);
        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
        while (!file.isEmpty()) {
            int[] pos = file.poll();
            for (int[] d : dirs) {
                int nl = pos[0]+d[0], nc = pos[1]+d[1];
                String key = nl+","+nc;
                if (!accessibles.contains(key) && !n.aMur(nl,nc) && !n.aCaisse(nl,nc)) {
                    accessibles.add(key);
                    file.add(new int[]{nl, nc});
                }
            }
        }
        return accessibles;
    }

    private String positionCanonique(HashSet<String> accessibles) {
        int minL = Integer.MAX_VALUE, minC = Integer.MAX_VALUE;
        for (String s : accessibles) {
            String[] p = s.split(",");
            int l = Integer.parseInt(p[0]), c = Integer.parseInt(p[1]);
            if (l < minL || (l == minL && c < minC)) { minL = l; minC = c; }
        }
        return minL + "," + minC;
    }

    private String etatComplet(Niveau n, HashSet<String> accessibles) {
        List<String> caisses = new ArrayList<>();
        for (int l = 0; l < n.lignes(); l++)
            for (int c = 0; c < n.colonnes(); c++)
                if (n.aCaisse(l, c)) caisses.add(l+","+c);
        Collections.sort(caisses);
        return positionCanonique(accessibles) + "|" + caisses;
    }

    private boolean[][] precomputerCasesMortes(Niveau n) {
        boolean[][] mortes = new boolean[n.lignes()][n.colonnes()];
        for (int l = 0; l < n.lignes(); l++)
            for (int c = 0; c < n.colonnes(); c++)
                mortes[l][c] = !n.aMur(l, c);

        Queue<int[]> file = new LinkedList<>();
        for (int l = 0; l < n.lignes(); l++)
            for (int c = 0; c < n.colonnes(); c++)
                if (n.aBut(l, c)) { mortes[l][c] = false; file.add(new int[]{l, c}); }

        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
        while (!file.isEmpty()) {
            int[] pos = file.poll();
            int l = pos[0], c = pos[1];
            for (int[] d : dirs) {
                int cl = l-d[0], cc = c-d[1];
                int ppL = l-2*d[0], ppC = c-2*d[1];
                if (cl<0||cl>=n.lignes()||cc<0||cc>=n.colonnes()||
                    ppL<0||ppL>=n.lignes()||ppC<0||ppC>=n.colonnes()) continue;
                if (n.aMur(cl,cc)||n.aMur(ppL,ppC)) continue;
                if (mortes[cl][cc]) { mortes[cl][cc]=false; file.add(new int[]{cl,cc}); }
            }
        }
        return mortes;
    }

    private int heuristique(Niveau n) {
        List<int[]> caisses = new ArrayList<>(), buts = new ArrayList<>();
        for (int l = 0; l < n.lignes(); l++)
            for (int c = 0; c < n.colonnes(); c++) {
                if (n.aCaisse(l,c) && !n.aBut(l,c)) caisses.add(new int[]{l,c});
                if (n.aBut(l,c)) buts.add(new int[]{l,c});
            }
        boolean[] butPris = new boolean[buts.size()];
        int total = 0;
        for (int[] caisse : caisses) {
            int minD = Integer.MAX_VALUE, minIdx = -1;
            for (int i = 0; i < buts.size(); i++) {
                if (butPris[i]) continue;
                int d = Math.abs(caisse[0]-buts.get(i)[0]) + Math.abs(caisse[1]-buts.get(i)[1]);
                if (d < minD) { minD = d; minIdx = i; }
            }
            if (minIdx >= 0) { butPris[minIdx] = true; total += minD; }
        }
        return total;
    }

    private boolean deadlockCoin(Niveau n) {
        for (int l = 0; l < n.lignes(); l++)
            for (int c = 0; c < n.colonnes(); c++)
                if (n.aCaisse(l,c) && !n.aBut(l,c)) {
                    boolean h = n.aMur(l,c-1) || n.aMur(l,c+1);
                    boolean v = n.aMur(l-1,c) || n.aMur(l+1,c);
                    if (h && v) return true;
                }
        return false;
    }

    private boolean deadlock2x2(Niveau n) {
        for (int l = 0; l < n.lignes()-1; l++)
            for (int c = 0; c < n.colonnes()-1; c++) {
                boolean tl = n.aCaisse(l,c)    || n.aMur(l,c);
                boolean tr = n.aCaisse(l,c+1)  || n.aMur(l,c+1);
                boolean bl = n.aCaisse(l+1,c)  || n.aMur(l+1,c);
                boolean br = n.aCaisse(l+1,c+1)|| n.aMur(l+1,c+1);
                if (tl && tr && bl && br)
                    if ((n.aCaisse(l,c)    && !n.aBut(l,c))    ||
                        (n.aCaisse(l,c+1)  && !n.aBut(l,c+1))  ||
                        (n.aCaisse(l+1,c)  && !n.aBut(l+1,c))  ||
                        (n.aCaisse(l+1,c+1)&& !n.aBut(l+1,c+1)))
                        return true;
            }
        return false;
    }

    private boolean deadlockCaseMorte(Niveau n) {
        for (int l = 0; l < n.lignes(); l++)
            for (int c = 0; c < n.colonnes(); c++)
                if (n.aCaisse(l,c) && !n.aBut(l,c) && casesMortes[l][c])
                    return true;
        return false;
    }

    private boolean estDeadlock(Niveau n) {
        return deadlockCoin(n) || deadlock2x2(n) || deadlockCaseMorte(n);
    }

    // Retourne une liste de directions [dL, dC] pour amener le pousseur en (tl,tc)
    // null si inaccessible
    private List<int[]> cheminPousseur(Niveau n, HashSet<String> accessibles, int tl, int tc) {
        int pl = n.lignePousseur(), pc = n.colonnePousseur();
        if (pl == tl && pc == tc) return new ArrayList<>();
        if (!accessibles.contains(tl+","+tc)) return null;

        Map<String, String> parent = new HashMap<>();
        Map<String, int[]> parentDir = new HashMap<>();
        Queue<int[]> file = new LinkedList<>();
        file.add(new int[]{pl, pc});
        parent.put(pl+","+pc, null);
        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};

        while (!file.isEmpty()) {
            int[] pos = file.poll();
            int l = pos[0], c = pos[1];
            if (l == tl && c == tc) {
                List<int[]> chemin = new ArrayList<>();
                String cur = tl+","+tc;
                while (parent.get(cur) != null) {
                    chemin.add(0, parentDir.get(cur));
                    cur = parent.get(cur);
                }
                return chemin;
            }
            for (int[] d : dirs) {
                int nl = l+d[0], nc = c+d[1];
                String k = nl+","+nc;
                if (!parent.containsKey(k) && accessibles.contains(k)) {
                    parent.put(k, l+","+c);
                    parentDir.put(k, d);
                    file.add(new int[]{nl, nc});
                }
            }
        }
        return null;
    }

    //@Override
    public Sequence<Coup> joue(Niveau niveau) {
        Sequence<Coup> resultat = Configuration.nouvelleSequence();
        System.err.println("A* démarre...");
        casesMortes = precomputerCasesMortes(niveau);

        PriorityQueue<Object[]> file = new PriorityQueue<>((a,b) -> (int)a[2]-(int)b[2]);
        HashMap<String,Integer> gScores = new HashMap<>();

        HashSet<String> accInit = casesAccessibles(niveau);
        String etatInit = etatComplet(niveau, accInit);
        file.add(new Object[]{niveau.clone(), new ArrayList<Coup>(), heuristique(niveau), 0});
        gScores.put(etatInit, 0);

        int iterations = 0;
        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};

        while (!file.isEmpty()) {
            iterations++;
            if (iterations % 10000 == 0)
                System.err.println("  itération " + iterations + " | file=" + file.size());
            if (iterations > 500_000) {
                System.err.println("Limite atteinte.");
                return resultat;
            }

            Object[] e = file.poll();
            Niveau n          = (Niveau) e[0];
            List<Coup> chemin = (List<Coup>) e[1];
            int g             = (int) e[3];

            HashSet<String> accessibles = casesAccessibles(n);
            String etat = etatComplet(n, accessibles);

            if (gScores.containsKey(etat) && gScores.get(etat) < g) continue;

            if (n.estGagne()) {  // ← était estGagne()
                System.err.println("Solution en " + iterations + " itérations, " + chemin.size() + " coups.");
                for (Coup c : chemin) resultat.insereQueue(c);
                return resultat;
            }

            for (int l = 0; l < n.lignes(); l++) {
                for (int c = 0; c < n.colonnes(); c++) {
                    if (!n.aCaisse(l,c)) continue;

                    for (int[] d : dirs) {
                        int ppL = l-d[0], ppC = c-d[1];
                        int dlC = l+d[0], dcC = c+d[1];

                        if (!accessibles.contains(ppL+","+ppC)) continue;
                        if (n.aMur(dlC,dcC) || n.aCaisse(dlC,dcC)) continue;

                        List<int[]> chP = cheminPousseur(n, accessibles, ppL, ppC);
                        if (chP == null) continue;

                        Niveau copie = n.clone();
                        // Rejouer le chemin pousseur (directions stockées)
                        List<Coup> coupsChP = new ArrayList<>();
                        for (int[] mv : chP) {
                            Coup cp = copie.deplacer(mv[0], mv[1]);  // ← était deplacer()
                            if (cp != null) coupsChP.add(cp);
                        }
                        Coup poussee = copie.deplacer(d[0], d[1]);  // ← était deplacer()
                        if (poussee == null) continue;
                        if (estDeadlock(copie)) continue;

                        int newG = g + chP.size() + 1;

                        HashSet<String> accCopie = casesAccessibles(copie);
                        String nouvelEtat = etatComplet(copie, accCopie);

                        if (!gScores.containsKey(nouvelEtat) || newG < gScores.get(nouvelEtat)) {
                            gScores.put(nouvelEtat, newG);
                            List<Coup> nv = new ArrayList<>(chemin);
                            nv.addAll(coupsChP);
                            nv.add(poussee);
                            int f = newG + heuristique(copie);
                            file.add(new Object[]{copie, nv, f, newG});
                        }
                    }
                }
            }
        }

        System.err.println("Aucune solution après " + iterations + " itérations.");
        return resultat;
    }
}