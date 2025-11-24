package Parent;

public abstract class GameUser {
    private String username;
    public abstract GameMove takeTurn();

    public GameUser(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
