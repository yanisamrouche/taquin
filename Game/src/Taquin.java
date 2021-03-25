import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Taquin {
    public static void main(String[] args){

         char[][] debut = generateInitialTaquin("taquin_2x4b.grid.txt");
         System.out.println("------------------------------");
         char[][] fin  = generateFinalTaquin("taquin_2x4b.grid.txt");

        Etat etat = new Etat("", debut, fin);
        BreadthFirstSearchAlgorithm l = new BreadthFirstSearchAlgorithm(etat);
        DepthFirstSearchAlgorithm p = new DepthFirstSearchAlgorithm(etat);
        BestFirstSearchAlgorithm m = new BestFirstSearchAlgorithm(etat);
        Etat resultat = l.solve();
        Etat resultat2 = p.solve();
        Etat resultat3 = m.solve();

        System.out.println("Largeur d'abord ...");
        printSolution(resultat, etat);
        printDetails(l);
        System.out.println("____________________");
        System.out.println("Profondeur d'abord ...");
        printSolution(resultat2 ,etat);
        printDetails(p);

        System.out.println("____________________");
        System.out.println("Meilleur d'abord ...");
        printSolution(resultat3, etat);
        printDetails(m);




    }

    public static void printSolution(Etat resultat, Etat taquin){
        System.out.println(" la solution est : ");
        System.out.println("->"+resultat.getListeMouvements());
        if (resultat != null){
            char[] cl = resultat.getListeMouvements().toCharArray();
            Etat current = taquin;
            Etat next;
            for (int i = 0; i < cl.length; i++){
                next = current.getNextEtat(Deplacement.fromChar(cl[i]));
                if(next != null)
                    executeActions(current, next);
                current = next;
            }
        }
    }
    public static void printDetails(Solver solver){
        if (solver instanceof BreadthFirstSearchAlgorithm){
            System.out.println("L'agorithme : "+solver.AlgoName);
            System.out.println("Le nombre d'états dans la liste fermé quand la résolution est terminée : "+((BreadthFirstSearchAlgorithm) solver).MaxSize);
            //System.out.println("Le nombre d'états dans la liste fermé quand la résolution est terminée "+((BreadthFirstSearchAlgorithm) solver).Fermé.size());
            System.out.println("Le nombre d'états dans la liste ouverte quand la résolution est terminée "+((BreadthFirstSearchAlgorithm) solver).file.size());
            double convert = (double) solver.timeCPUNS/1_000_000_000;
            System.out.println("Le temps CPU : "+solver.timeCPUNS+" nano seconds ("+convert+" seconds)");
            System.out.println("La taille de la solution : "+solver.SolutionLength);
            System.out.println("Les régles de production pour passer de l'etat initial a l'etat final : "+((BreadthFirstSearchAlgorithm) solver).solution.getListeMouvements());
        }
        else if (solver instanceof DepthFirstSearchAlgorithm){
            System.out.println("L'agorithme : "+solver.AlgoName);
            System.out.println("Le nombre d'états dans la liste fermé quand la résolution est terminée : "+((DepthFirstSearchAlgorithm) solver).MaxSize);
            //System.out.println("Le nombre d'états dans la liste fermé quand la résolution est terminée : "+((DepthFirstSearchAlgorithm) solver).Fermé.size());
            System.out.println("Le nombre d'états dans la liste ouverte quand la résolution est terminée "+((DepthFirstSearchAlgorithm) solver).stack.size());
            double convert = (double) solver.timeCPUNS/1_000_000_000;
            System.out.println("Le temps CPU : "+solver.timeCPUNS+" nano seconds ("+convert+" seconds)");
            System.out.println("La taille de la solution : "+solver.SolutionLength);
            System.out.println("Les régles de production pour passer de l'etat initial a l'etat final : "+((DepthFirstSearchAlgorithm) solver).solution.getListeMouvements());
        }
        else if (solver instanceof BestFirstSearchAlgorithm){
            System.out.println("L'agorithme : "+solver.AlgoName);
            System.out.println("Le nombre d'états dans la liste fermé quand la résolution est terminée : "+((BestFirstSearchAlgorithm) solver).Fermé.size());
            System.out.println("Le nombre d'états dans la liste ouverte quand la résolution est terminée "+((BestFirstSearchAlgorithm) solver).listeOuverte.size());
            double convert = (double) solver.timeCPUNS/1_000_000_000;
            System.out.println("Le temps CPU : "+solver.timeCPUNS+" nano seconds "+convert+" seconds");
            System.out.println("La taille de la solution : "+solver.SolutionLength);
            System.out.println("Les régles de production pour passer de l'etat initial a l'etat final : "+solver.getSolution());
        }
    }

    public static char[][] generateInitialTaquin(String filename){
        try{
            InputStream flux=new FileInputStream(filename);
            InputStreamReader lecture=new InputStreamReader(flux);
            BufferedReader buff=new BufferedReader(lecture);
            List Grid = new ArrayList();
            String ligne;

            //System.out.println("affichage du fichier : ");
            while ((ligne= buff.readLine())!=null){
                Grid.add(ligne);
                //System.out.println(ligne);
            }
            //System.out.println("le fichier sous forme d'une liste :");
            //System.out.println(Grid);
            int nbrLigne = Integer.parseInt((String) Grid.get(0));
            //System.out.println();

            List initTaquin = new ArrayList();
            for (int i=0;  i < nbrLigne; i++){
                initTaquin.add(Grid.get(i+1));
            }
            //System.out.println("Le taquin initial: ");
            //System.out.println(initTaquin);

            List finalTaquin = new ArrayList();
            for (int i = nbrLigne+1; i < Grid.size(); i++ ){
                finalTaquin.add(Grid.get(i));
            }
            //System.out.println("Le taquin final: ");
            //System.out.println(finalTaquin);

            //System.out.println("liste du taquin : ");
            String word ;
            List taquinL = new ArrayList();
            for(int i=0; i < initTaquin.size(); i++){
                word = (String) initTaquin.get(i);
                for (int j=0; j<word.length(); j++){
                    taquinL.add(word.charAt(j));
                }
            }
            //System.out.println(taquinL);
            String colomnSize = (String) Grid.get(1);
            int nbrColumn = colomnSize.length();
            int index=0;
            char[][] matInitial = new char[nbrLigne][nbrColumn];
            for (int i=0; i<nbrLigne; i++){
                for (int j=0; j<nbrColumn; j++){
                    matInitial[i][j] = (char) taquinL.get(index);
                    index++;
                }
            }
            System.out.println("affichage du taquin initial sous forme d'une matrice "+nbrLigne+"x"+nbrColumn);
            for (int i=0; i<nbrLigne; i++){
                System.out.print("[ ");
                for (int j=0; j<nbrColumn; j++){
                    if (j==nbrColumn-1){
                        System.out.print(matInitial[i][j]);
                    }
                    else {
                        System.out.print(matInitial[i][j] + " | ");
                    }
                }
                System.out.println(" ]");
            }
            buff.close();
            return matInitial;
        }

        catch (Exception e){
            System.out.println(e.toString());
        }
        return null;

    }

    public static char[][] generateFinalTaquin(String filename){
        try{
            InputStream flux=new FileInputStream(filename);
            InputStreamReader lecture=new InputStreamReader(flux);
            BufferedReader buff=new BufferedReader(lecture);
            List Grid = new ArrayList();
            String ligne;

           // System.out.println("affichage du fichier : ");
            while ((ligne= buff.readLine())!=null){
                Grid.add(ligne);
                //System.out.println(ligne);
            }
            //System.out.println("le fichier sous forme d'une liste :");
            //System.out.println(Grid);
            int nbrLigne = Integer.parseInt((String) Grid.get(0));
            //System.out.println();

            List initTaquin = new ArrayList();
            for (int i=0;  i < nbrLigne; i++){
                initTaquin.add(Grid.get(i+1));
            }
            //System.out.println("Le taquin initial: ");
            //System.out.println(initTaquin);

            List finalTaquin = new ArrayList();
            for (int i = nbrLigne+1; i < Grid.size(); i++ ){
                finalTaquin.add(Grid.get(i));
            }
            //System.out.println("Le taquin final: ");
            //System.out.println(finalTaquin);

            //System.out.println("liste du taquin : ");
            String word ;
            List taquinL = new ArrayList();
            for(int i=0; i < finalTaquin.size(); i++){
                word = (String) finalTaquin.get(i);
                for (int j=0; j<word.length(); j++){
                    taquinL.add(word.charAt(j));
                }
            }
            //System.out.println(taquinL);
            String colomnSize = (String) Grid.get(1);
            int nbrColumn = colomnSize.length();
            int index=0;
            char[][] matFinal = new char[nbrLigne][nbrColumn];
            for (int i=0; i<nbrLigne; i++){
                for (int j=0; j<nbrColumn; j++){
                    matFinal[i][j] = (char) taquinL.get(index);
                    index++;
                }
            }
            System.out.println("affichage du taquin final sous forme d'une matrice "+nbrLigne+"x"+nbrColumn);
            for (int i=0; i<nbrLigne; i++){
                System.out.print("[ ");
                for (int j=0; j<nbrColumn; j++){
                    if (j==nbrColumn-1){
                        System.out.print(matFinal[i][j]);
                    }
                    else {
                        System.out.print(matFinal[i][j] + " | ");
                    }
                }
                System.out.println(" ]");
            }
            buff.close();
            return matFinal;
        }

        catch (Exception e){
            System.out.println(e.toString());
        }
        return null;

    }

    public static void displayEtat(Etat etat){
        List<String> c = new ArrayList<>();
        if (!c.contains(etat.toHashkey())){
            c.add(etat.toHashkey());
            System.out.println(" - Etat "+ etat.getNbreMouvements() + " : " + etat.getListeMouvements());
            displayGrid(etat.getListeCases());}
    }

    public static void displayEtatFinal(Etat etat){
        System.out.println(" - Etat Finale : ");
        displayGrid(etat.getEtatFinal());
    }

    public static void displayGrid(char[][] grid){

        System.out.print(" ");
        for(char[] ligne : grid) {
            System.out.print("________");
        }
        System.out.print("\n");

        for(char[] ligne : grid) {
            System.out.print("[");
            for(char tuile : ligne) {
                if (tuile==grid.length){
                    System.out.print(" "+tuile);
                }
                else {
                    System.out.print(" "+tuile+ " |");
                }
            }
            System.out.print("]\n");
        }


        for(char[] ligne : grid) {
            System.out.print("_________");
        }
        System.out.println("");
    }

    public static void executeActions(Etat etat1, Etat etat2){
        try{
            //clean();
           // displayEtat(etat1);
            Thread.sleep(200);
            //clean();
            displayEtat(etat2);
            Thread.sleep(200);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void clean(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


}
