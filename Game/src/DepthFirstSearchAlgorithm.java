import java.util.Stack;

public class DepthFirstSearchAlgorithm extends Solver{

  HashEtats map;
  Stack<Etat> stack;
  Etat solution;
  boolean isDone;
  int depthLimit;

    public DepthFirstSearchAlgorithm(Etat etatInit) {
        super(etatInit);
        solution = null;
        SolutionLength = 0;
        nbVisitedVertex = 0;
        MaxSize = 0;
        timeCPUNS = 0;
        AlgoName = " Profondeur d'abord ";
    }

    public Etat solve(){
        timeCPUNS = System.nanoTime();
        Etat currentEtat;
        depthLimit = 5;
        while (solution == null){
            isDone = true;
            map = new HashEtats();
            map.add(etatInit);
            stack = new Stack<>();
            stack.push(etatInit);
            while (isDone){
                currentEtat = stack.pop();
                generateNeighbours(currentEtat);
                isSolution(currentEtat);
                if (stack.isEmpty()){
                    isDone = false;
                }
            }
            depthLimit += 10;
        }

        SolutionLength = solution.getListeMouvements().length();
        MaxSize = map.getList().size();
        timeCPUNS = System.nanoTime() - timeCPUNS;
        return solution;
    }
    /* Ajoute dans la pile les fils de e qui sont admissibles */
    public int generateNeighbours(Etat e){
        if(solution != null && e.getScoreManatthan() + 1 >= solution.getScoreManatthan()){
            return 0;
        }
        int count = 0;
        Etat generatedEtat;
        for (Deplacement d : Deplacement.values()){
            nbVisitedVertex++;
            generatedEtat = e.getNextEtat(d);
            //si le déplacement est possible et l'état généré est admissible
            if(generatedEtat != null && map.add(generatedEtat)){
                stack.push(generatedEtat);
                count++;
            }
        }
        return 0;
    }

    public boolean isSolution(Etat e){
        if(e.isFinal()){
            if (solution != null && e.getScoreManatthan() > solution.getScoreManatthan()){
                return false;
            }
            else {
                solution = e;
                return true;
            }
        }
        else {
            return false;
        }
    }









}
