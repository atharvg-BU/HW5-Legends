package Parent;

public class GamePiece {
    public String icon;
    public Object value;
    public String color;
    private String owner;

    public GamePiece(String icon) {
        this.icon = icon;
    }


    public String getIcon() {
        return icon;
    }

    public void setOwner(String owner){
        this.owner=owner;
    }
    public String getOwner(){
        return owner;
    }

}
