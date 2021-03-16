import java.util.Scanner;

public class Animation {

    Scanner input;

    public Animation(){
        input = new Scanner(System.in);
    }



    public void afficherEtat(Etat etat) {
        System.out.println("- Etat " + etat.getNbreMouvements() + " : " + etat.getListeMouvements());
        afficheGrille(etat.getListeCases());
    }

    public void afficheEtatFinal(Etat etat){
        System.out.println("- Etat Finale : ");
        afficheGrille(etat.getEtatFinal());
    }

    private void afficheGrille(char[][] grille){
        System.out.print("╔════");
        for(char[] ligne : grille) {
            System.out.print("══════");
        }
        System.out.print("╗\n");

        for(char[] ligne : grille) {
            System.out.print("║    ");
            for(int tuile : ligne) {
                if(tuile <= 9)
                    System.out.print(" "+(tuile==0 ? " " : tuile)+"    ");
                else
                    System.out.print((tuile==0 ? "  " : tuile)+"    ");
            }
            System.out.print("║\n");
        }

        System.out.print("╚════");
        for(char[] ligne : grille) {
            System.out.print("══════");
        }
        System.out.println("╝");
    }




    public void animate(Etat etat1, Etat etat2) {
        // Devrait animer un déplacement entre l'état1 et l'état2
        int delai = 200;

        try {
            clean();
            afficherEtat(etat1);
            Thread.sleep(delai);
            clean();
            afficherEtat(etat2);
            Thread.sleep(delai);
        } catch (InterruptedException ex) {}
    }

    public void clean(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
