import java.util.HashMap;

public class HashEtats {

    private final HashMap<String, Etat> list;

    public HashEtats() {
        this.list = new HashMap<>();
    }

    public HashMap getList() { return list;}

    /**
     * @param etat L'état à ajouter
     * @return Booléen : l'ajout s'est bien fait dans le tableau. Si ce n'est pas le cas, c'est que l'état avait déjà été trouvé de manière plus optimisée.
     */

    public boolean add(Etat etat) {
        Etat existant = list.get(etat.toHashkey());

        if(existant == null || existant.getNbreMouvements() > etat.getNbreMouvements()) {
            // Le put() fait un replace() si l'élement existait déjà
            list.put(etat.toHashkey(), etat);
            return true;
        }else{
            return false;
        }
    }

}
