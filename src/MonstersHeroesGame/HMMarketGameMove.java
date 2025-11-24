package MonstersHeroesGame;

import MonstersHeroesGame.Data.MainData.Armory;
import MonstersHeroesGame.Data.MainData.Portion;
import MonstersHeroesGame.Data.MainData.Spell;
import MonstersHeroesGame.Data.MainData.Weapon;
import Parent.GameMove;

import java.util.ArrayList;
import java.util.List;

public class HMMarketGameMove extends GameMove {
    public List<Weapon> boughtWeapons;
    public List<Portion> boughtPortions;
    public List<Spell> boughtSpells;
    public List<Armory> boughtArmors;
    int money;
    public boolean leaveMkt;

    public HMMarketGameMove() {
        boughtWeapons=new ArrayList<>();
        boughtPortions=new ArrayList<>();
        boughtSpells=new ArrayList<>();
        boughtArmors=new ArrayList<>();
        money=0;
    }
}
