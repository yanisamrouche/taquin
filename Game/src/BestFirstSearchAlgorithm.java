import java.util.Comparator;
import java.util.PriorityQueue;

public class BestFirstSearchAlgorithm extends Solver {


    public BestFirstSearchAlgorithm(Etat etatInit) {
        super(etatInit);
        AlgoName = "Meilleur d'abord";
    }

    public Etat solve() {
        if (!estSolvable()) {
            System.out.println("Insolvable");
            throw new RuntimeException("but impossible à atteindre");
        }
        long start = System.nanoTime();
        nbVisitedVertex = 0;
        MaxSize = 0;
        Etat init = etatInit;
        Etat fin = new Etat("", init.getEtatFinal(), init.getEtatFinal());
        Manhattan cmp = new Manhattan();
        PriorityQueue<Etat> listeOuverte = new PriorityQueue<>(cmp);
        listeOuverte.add(init);
        while (!listeOuverte.isEmpty()) {
            Etat current = listeOuverte.poll();
            nbVisitedVertex++;
            if (current.toHashkey().equals(fin.toHashkey())) {
                SolutionLength = current.getNbreMouvements();
                Solution = current.getListeMouvements();
                long end = System.nanoTime();
                timeCPUNS = end - start;
                System.out.println("Le nombre d'états dans la liste ouverte "+listeOuverte+" : "+listeOuverte.size());
                System.out.println("temps CPU pour résoudre l'instance : "+timeCPUNS);
                return current;
            } else {
                for (Deplacement d : Deplacement.values()) {
                    Etat next = current.getNextEtat(d);
                    if (next != null) {
                        // offer : est utilisée pour insérer un élément particulier dans la file d'attente prioritaire.
                        listeOuverte.offer(next); // retourne true si la valeur est correctement insérée dans la file d'attente.
                        if (MaxSize < listeOuverte.size())
                            MaxSize = listeOuverte.size();
                    }
                }
            }
        }

        return null;
    }

}

    class Manhattan implements Comparator<Etat>{


        @Override
        public int compare(Etat o1, Etat o2) {
            // -1 = etat2 sera moins prioritaire que etat 1, 0 = même niveau, 1 = plus prio
            if(o1.getF() < o2.getF())
                return -1;
            else if(o1.getF() == o2.getF()){
                if (o1.getG() < o2.getG()) // GetH
                    return -1;
                else if (o1.getG() == o2.getG()) // GetH
                    return 0;
                else
                    return 1;
            }
            else
                return 1;
        }
    }

















