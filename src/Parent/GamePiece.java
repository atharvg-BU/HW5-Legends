package Parent;

public class GamePiece {
//    Parent class to store basic information in Game Piece
    public String icon;
    public Object value;
    public String color;
    private String owner;

    public GamePiece(String icon) {
        this.icon = icon;
    }

    /*
    Input: N/A
    Outputs: Icon of the piece
    Function: Getter Function to get the icon associated with that particular Game Piece
     */
    public String getIcon() {
        return icon;
    }

    /*
    Input: Owner to be set
    Outputs: N/A
    Function: Setter Function to set the icon associated with that particular Game Piece
     */
    public void setOwner(String owner){
        this.owner=owner;
    }

    /*
    Input: N/A
    Outputs: Owner of the piece
    Function: Getter Function to get the owner/player associated with that particular Game Piece
     */
    public String getOwner(){
        return owner;
    }

}
