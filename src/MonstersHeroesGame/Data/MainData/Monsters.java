package MonstersHeroesGame.Data.MainData;

public class Monsters {
    private String name;
    private String level;
    private String damage;
    private String defense;
    private String dodgeChance;
    private String type;

    public Monsters(String name, String level, String damage, String defense, String dodgeChance, String type) {
        this.name = name;
        this.level = level;
        this.damage = damage;
        this.defense = defense;
        this.dodgeChance = dodgeChance;
        this.type = type;
    }
    public String getName(){
        return name;
    }
    public String getLevel(){
        return level;
    }
    public String getDamage(){
        return damage;
    }
    public String getDefense(){
        return defense;
    }
    public String getDodgeChance(){
        return dodgeChance;
    }

}
