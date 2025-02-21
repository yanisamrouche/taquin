import java.util.concurrent.LinkedBlockingQueue;

public class BreadthFirstSearchAlgorithm extends Solver {
    HashEtats map;
    LinkedBlockingQueue<Etat> file;
    Etat solution;
    boolean algoEnCours;

    public BreadthFirstSearchAlgorithm(Etat etat){
        super(etat);
        solution = null;
        SolutionLength = 0;
        nbVisitedVertex = 0;
        MaxSize = 0;
        AlgoName = "largeur d'abord";

    }

    public Etat solve(){

        timeCPUNS = System.nanoTime();
        if (!estSolvable()) {
            System.out.println("Insolvable");
            throw new RuntimeException("but impossible à atteindre");
        }

        Etat current;
        while (solution == null){

            algoEnCours =true;

            map = new HashEtats();

            map.add(etatInit);
            file = new LinkedBlockingQueue<Etat>();

            file.add(etatInit);

            while (algoEnCours){

                current = file.poll();

                generation(current);

                testSolution(current);

                if (file.isEmpty()){

                    algoEnCours = false;
                }

            }
        }

        SolutionLength = solution.getListeMouvements().length();
        MaxSize = map.getList().size();
        timeCPUNS = System.nanoTime() - timeCPUNS;
        System.out.println("Le nombre d'états dans la liste ouverte "+file+" : "+file.size());
        System.out.println("temps CPU pour résoudre l'instance : "+timeCPUNS);
        return solution;
    }

    private int generation(Etat e) {
        // si cette etat ne peut pas devenir une meilleure solution
        if (solution != null && e.getScoreManatthan() + 1 >= solution.getScoreManatthan()) {
            return 0;
        }
        int count = 0;

        Etat etatGenere;
        for (Deplacement d : Deplacement.values()) {
            nbVisitedVertex++;
            etatGenere = e.getNextEtat(d);

            // si le déplacement est possible et l'état généré est admissible
            if (etatGenere != null && map.add(etatGenere)) {
                file.add(etatGenere);
                count++;
            }
        }

        return 0;
    }

    private boolean testSolution(Etat e) {
        if (e.isFinal()) {
            if (solution != null && e.getScoreManatthan() > solution.getScoreManatthan()) {
                return false;
            } else {
                solution = e;
                return true;
            }
        } else {
            return false;
        }
    }













}
