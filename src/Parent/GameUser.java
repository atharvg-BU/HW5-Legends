package Parent;

public abstract class GameUser {
//    Parent class storing basic information about the user
    private String username;
//    Abstract method for user to take turen
    public abstract GameMove takeTurn();

    public GameUser(String username) {
        this.username = username;
    }

    /*
    Input: N/A
    Outputs: Username of user
    Function: Getter to get the username of the game user
    */
    public String getUsername() {
        return username;
    }

    /*
    Input: Username of user
    Outputs: N/A
    Function: Setter to set the username of the game user
    */
    public void setUsername(String username) {
        this.username = username;
    }
}
