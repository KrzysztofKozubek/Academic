
public class Player {

    public int numberPlayer;
    public int numberPlayerEnemy;
    public String address = "localhost";
    public int port = 50005;

    public Player(int numberPlayer, String address, int port) {

        System.out.print("Player\n");
        this.numberPlayer = numberPlayer;
        this.numberPlayerEnemy = 0;
        this.address = address;
        this.port = port;
    }
}
