public enum Deplacement {

    Haut("H"), Bas("B"), Gauche("G"), Droite("D");



    private String lettre = "";

    Deplacement(String lettre){
        this.lettre = lettre;
    }

    public String toString(){ return lettre;}

    public static Deplacement fromChar(char c){
        if(c=='H')
            return Haut;
        if(c=='B')
            return Bas;
        if(c=='G')
            return Gauche;
        return Droite;
    }




}
