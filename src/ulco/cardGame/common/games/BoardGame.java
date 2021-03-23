package ulco.cardGame.common.games;

import ulco.cardGame.common.interfaces.Game;
import ulco.cardGame.common.interfaces.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class BoardGame implements Game {
    protected String name; //permet de stocker un nom associé au jeu
    protected Integer maxPlayer; //permet de savoir le nombre maximum attendu de joueurs pour le jeu

    protected List<Player> players; //stocke la liste des joueurs
    protected Boolean endGame; //garde un état du jeu afin de savoir s’il est fini ou non
    protected Boolean started; //permet de stocker un état du jeu afin de savoir s’il peut commencer ou non


    public BoardGame(String name, Integer maxPlayer, String filename) {
        this.name = name;
        this.maxPlayer = maxPlayer;
        this.started=false;
        this.endGame=false;
        this.players = new ArrayList<>();
        initialize(filename);

    }

    /**
     *méthode qui permet d’ajouter un joueur à la liste
     * des joueurs
     * @param player
     * @return
     */
    public boolean addPlayer(Player player){

        if (players.size()<maxNumberOfPlayers() && !players.contains((player.getName()))) {
            players.add(player);
            return true;
        }

        return false;

    }

    /**
     *méthode qui va supprimer un joueur de la liste
     * des joueurs
     * @param player
     */

    public void removePlayer(Player player) {
        players.remove(player);
    }

    /**
     *: méthode qui va supprimer l’ensemble des joueurs présents dans
     * le jeu
     */
    public void removePlayers(){
        players.clear();
    }

    /**
     *: méthode permettant d’afficher l’état d’un jeu. Ici, nous afficherons
     * l’ensemble des joueurs présents
     */
    public void displayState(){
        for(Player player : players){
            System.out.println(player);

        }

    }

    /***
     *méthode qui va spécifier si le jeu est commencé ou non
     * @return
     */
    public boolean isStarted(){
    started=true;
            if(maxNumberOfPlayers().equals(maxPlayer) && run().isPlaying()){
                started=false;
                return started;
            }else {
                return started;
            }

    }

    /**
     * @returne le nombre maximum de joueurs attendu
     */
    public Integer maxNumberOfPlayers(){
        return this.maxPlayer;
    }

    /**
     *permet de récupérer la liste des joueurs du jeu en cours
     * @return liste des joueur
     */

    public List<Player> getPlayers(){
        List<Player>liste = new ArrayList<>();

        if(isStarted()){
           for (int i=0; i<players.size();i++){
               liste=players;
           }
        }
        return liste;
    }
}
