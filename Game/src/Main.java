import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        display();
    }

    public static  void display(){
        try{
            InputStream flux=new FileInputStream("taquin_5x5c.grid.txt");
            InputStreamReader lecture=new InputStreamReader(flux);
            BufferedReader buff=new BufferedReader(lecture);
            List Grid = new ArrayList();
            String ligne;

            System.out.println("affichage du fichier : ");
            while ((ligne= buff.readLine())!=null){
                Grid.add(ligne);
                System.out.println(ligne);
            }
            System.out.println("le fichier sous forme d'une liste :");
            System.out.println(Grid);
            int nbrLigne = Integer.parseInt((String) Grid.get(0));
            System.out.println();

            List initTaquin = new ArrayList();
            for (int i=0;  i < nbrLigne; i++){
                initTaquin.add(Grid.get(i+1));
            }
            System.out.println("Le taquin initial: ");
            System.out.println(initTaquin);

            List finalTaquin = new ArrayList();
            for (int i = nbrLigne+1; i < Grid.size(); i++ ){
                finalTaquin.add(Grid.get(i));
            }
            System.out.println("Le taquin final: ");
            System.out.println(finalTaquin);

            System.out.println("liste du taquin : ");
            String word ;
            List taquinL = new ArrayList();
            for(int i=0; i < initTaquin.size(); i++){
                word = (String) initTaquin.get(i);
                for (int j=0; j<word.length(); j++){
                    taquinL.add(word.charAt(j));
                }
            }
            System.out.println(taquinL);
            String colomnSize = (String) Grid.get(1);
            int nbrColumn = colomnSize.length();
            int index=0;
            char[][] taquin = new char[nbrLigne][nbrColumn];
            for (int i=0; i<nbrLigne; i++){
                for (int j=0; j<nbrColumn; j++){
                    taquin[i][j] = (char) taquinL.get(index);
                    index++;
                }
            }
            System.out.println("affichage du taquin sous forme d'une matrice "+nbrLigne+"x"+nbrColumn);
            for (int i=0; i<nbrLigne; i++){
                System.out.print("[ ");
                for (int j=0; j<nbrColumn; j++){
                    if (j==nbrColumn-1){
                        System.out.print(taquin[i][j]);
                    }
                    else {
                        System.out.print(taquin[i][j] + " | ");
                    }
                }
                System.out.println(" ]");
            }
            buff.close();
        }

        catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
