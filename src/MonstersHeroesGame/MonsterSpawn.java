package MonstersHeroesGame;

import java.util.List;

public class MonsterSpawn {
    public String name;
    public int level;
    public int damage;
    public int defense;
    public int dodgeChance;
    public boolean defeated;
    public double hp;

    public MonsterSpawn(String name,int level, int damage, int defense, int dodgeChance) {
        this.name=name;
        this.level = level;
        this.damage = damage;
        this.defense = defense;
        this.dodgeChance = dodgeChance;
        this.defeated=false;
        this.hp=level*100;
    }
    public HMBattleMove takeBattleTurn(List<HMGamePlayer> playerList){
        HMBattleMove battleMove = new HMBattleMove();
//        int minHp=playerList.get(0).health;
//        HMGamePlayer minHpPlayer;
//        for(HMGamePlayer p:playerList){
//            if(!p.fainted){
//                if(minHp>p.health){
//                    minHp=p.health;
//                    minHpPlayer=p;
//                }
//            }
//        }

        battleMove.hpDamage=damage;
        return battleMove;
    }
}
