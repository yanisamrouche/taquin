public class Etat {
    private char[][] etatFinal;
    private String listeMouvements;
    private char[][] etatInit; //etatInit
    private int[] coordVide = new int[2]; // les coord. de la case vide ( i , j ) {i: la ligne, j: la colonne}

    public Etat(String listeMouvements, char[][] listeCases, char[][] etatFinal){
        this.listeMouvements = listeMouvements;
        this.etatInit = listeCases;
        this.etatFinal = etatFinal;
        // récuperer la position de la case vide
        for (int i=0; i < listeCases.length; i++){
            for(int j=0; j < listeCases.length; j++){
                if(listeCases[i][j] == ' '){
                    coordVide[0] = i;
                    coordVide[1] = j;
                }
            }
        }
    }


    // recuperer le nombre de mouvements
    public int getNbreMouvements(){ return listeMouvements.length();}

    public String toHashkey(){//mettre les lignes du taquin dans un tampon
        String tmp = "";
        for(char[] ligne : etatInit){
            for (char _case : ligne ){
                tmp += _case;
            }
        }
        return tmp;
    }


    /**
     * vérifier si on a atteint l'etat final ou pas
     * @return true si l'etat est le but
     * false sinon
     */
    public boolean isFinal(){
        for(int i = 0; i < etatInit.length; i++ ){
            for(int j = 0; j < etatInit.length; j++ ){
                if(etatInit[i][j] != etatFinal[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean deplacementpossible(Deplacement d){
        if(d == Deplacement.Bas && coordVide[0]==0){// le cas ou la case vide et dans la premiere ligne du taquin
            return false;
        }
        if (d == Deplacement.Haut && coordVide[0]== etatInit.length - 1){//le cas ou la case vide et dans la derniere ligne du taquin
            return false;
        }
        if (d == Deplacement.Gauche && coordVide[1]== etatInit.length - 1){// le cas ou la case vide et dans la derniere colonne  du taquin
            return false;
        }
        if (d == Deplacement.Droite && coordVide[1]==0){// le cas ou la case vide et dans la premiere colonne du taquin
            return false;
        }
        return true;
    }

    // getteurs et setteurs


    public String getListeMouvements() {
        return listeMouvements;
    }

    public void setListeMouvements(String listeMouvements) {
        this.listeMouvements = listeMouvements;
    }

    public char[][] getEtatFinal() {
        return etatFinal;
    }

    public void setEtatFinal(char[][] etatFinal) {
        this.etatFinal = etatFinal;
    }

    public char[][] getEtatInit() {
        return etatInit;
    }

    public void setEtatInit(char[][] etatInit) {
        this.etatInit = etatInit;
    }

    public int getScoreManatthan(){ return getF();}

    public int getF(){ return getH() + getG();}

    public int getH(){ return getBBestSolution();}

    public int getG(){ return getListeMouvements().length(); }



    /**
     *
     *  Renvoie l'état du taquin après le déplacement d'une case dans la case vide
     *        Si le déplacement est impossible, renvoie null
     */

    public Etat getNextEtat(Deplacement d){
        char[][] newEtat = new char[etatInit.length][etatInit.length];
        //remplir avec l'etat initial
        for (int i = 0; i < etatInit.length; i++){
            newEtat[i] = etatInit[i].clone();
        }
        //chercher la case vide
        int i,j;
        i = coordVide[0];
        j = coordVide[1];
        if(newEtat[i][j] == ' ' && deplacementpossible(d)){
            switch (d.toString().charAt(0)){
                case 'D': // on peut faire un deplacement vers la droite
                    newEtat[i][j] = newEtat[i][j-1];
                    newEtat[i][j-1] = ' ';
                    break;
                case 'G': // on peut peut faire un deplacement vers la gauche
                     newEtat[i][j] = newEtat[i][j+1];
                     newEtat[i][j+1] = ' ';
                     break;
                case 'H': // on peut peut faire un deplacement vers le haut
                    newEtat[i][j] = newEtat[i+1][j];
                    newEtat[i+1][j] = ' ';
                    break;
                case 'B': // on peut peut faire un deplacement vers le bas
                    newEtat[i][j] = newEtat[i-1][j] ;
                    newEtat[i-1][j] = ' ';
                    break;
                default:
                    return null;
            }
        }
        else {
            return null;
        }
        return new Etat(listeMouvements + d , newEtat, etatFinal);

    }

    // retourne la meilleur borne
    public int getBBestSolution(){
        int c = 0;
        //pour chaque cases dans la solution final
        for (int col = 0; col < etatInit.length; col++){
            for (int row = 0; row < etatInit.length; row++){
                if(etatFinal[row][col] != ' '){
                    // on cherche la meme case dans l'état du plateau (this)
                    for (int i = 0; i < etatInit.length; i++) {
                        for (int j = 0; j < etatInit.length; j++) {
                            // une fois trouvé, on ajoute au cout sa distance par rapport à sa place final.
                            if(etatFinal[row][col] == etatInit[i][j]){
                                c = c + Math.abs(row - i);
                                c = c + Math.abs(col - j);
                            }
                        }
                    }
                }
            }
        }
        return c;
    }


    public String toString() {
        String s = "";

        for (int j = 0; j < etatInit.length; j++) {
            for (int i = 0; i < etatInit.length; i++) {
                s = s + etatInit[i][j];
            }
        }
        return s;
    }




}
