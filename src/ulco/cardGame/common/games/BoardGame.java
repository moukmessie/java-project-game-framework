package ulco.cardGame.common.games;

import ulco.cardGame.common.interfaces.Game;
import ulco.cardGame.common.interfaces.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class BoardGame implements Game {
    protected String name; //name associate with the game
    protected Integer maxPlayer; //max player number in the game

    protected List<Player> players; //list players
    protected Boolean endGame; //endgame's status
    protected Boolean started; //started's game status


    public BoardGame(String name, Integer maxPlayer, String filename) {
        this.name = name;
        this.maxPlayer = maxPlayer;
        this.started=false;
        this.endGame=false;
        this.players = new ArrayList<>();
        initialize(filename);

    }

    /**
     *add player to ArrayList players
     * méthode qui permet d’ajouter un joueur à la liste
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
     * remove player to Arralist players
     *méthode qui va supprimer un joueur de la liste
     * des joueurs
     * @param player
     */

    public void removePlayer(Player player) {
        players.remove(player);
    }

    /**
     * remove all players in the game
     * méthode qui va supprimer l’ensemble des joueurs présents dans
     * le jeu
     */
    public void removePlayers(){
        players.clear();
    }

    /**
     * view the status game/ view the game players
     * méthode permettant d’afficher l’état d’un jeu. Ici, nous afficherons
     * l’ensemble des joueurs présents
     */
    public void displayState(){
        System.out.println("*********** Display State ***********");
        for(Player player : players){
            System.out.println(player);
        }
    }

    /***
     * view the status game if is started or no
     *méthode qui va spécifier si le jeu est commencé ou non
     * @return
     */
    public boolean isStarted(){
    return this.started;
    }

    /**
     * return the max number of players
     * @returne le nombre maximum de joueurs attendu
     */
    public Integer maxNumberOfPlayers(){
        return this.maxPlayer;
    }

    /**
     * get the players Link game
     *permet de récupérer la liste des joueurs du jeu en cours
     * @return liste des joueur
     */

    public List<Player> getPlayers(){
      return this.players;
    }
}
