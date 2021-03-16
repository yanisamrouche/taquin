public class Nico {
    public static void main(String[] args) {
        String tmp = "";
        char[][] initialTaquin = {
                {'4', '3', '5'},
                {'2', '1', ' '}

        };

        for (char[] ligne : initialTaquin) {
            for (char tuile : ligne) {
                tmp += tuile;
            }
        }

        System.out.println(tmp.charAt(5));
}

}

