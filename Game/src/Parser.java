
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

    public class Parser {

        public static void main(String[]args) throws IOException {
            List list = new ArrayList();

            try {

                File f = new File("taquin_2x4.grid.txt");

                BufferedReader b = new BufferedReader(new FileReader(f));

                String readLine = "";
                int nbrLine;
                int r=1;
                System.out.println("Reading file using Buffered Reader");

                while ((readLine = b.readLine()) != null) {
                    System.out.println(readLine);
                    list.add(readLine);

                }

                String mot;
                mot =(String) list.get(0);
                int nbrColonne =mot.length();
                String[][] matrice = new String[list.size()][nbrColonne];
                nbrLine =Integer.parseInt((String)list.get(0));
                for(int k=0;k<list.size();k++){
                    for (int i=0 ; i<= nbrLine ; i++)
                    {
                        for (int j=0 ; j<= mot.length() ; j++)
                        {
                             matrice[i][j]= String.valueOf((mot.charAt(1)));
                        }
                    }

                }


                System.out.println(list);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

