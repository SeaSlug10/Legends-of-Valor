import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class HeroFactory {
    private String[] paladins;
    private String[] warriors;
    private String[] sorcerers;
    private Map[] strMods;
    private Map[] dexMods;
    private Map[] agiMods;

    public HeroFactory(String file) {
        JSONReader reader = new JSONReader(file);
        this.paladins = reader.getStringArray("paladins");
        this.warriors = reader.getStringArray("warriors");
        this.sorcerers = reader.getStringArray("sorcerers");
        this.strMods = reader.getMaps("strModifiers");
        this.dexMods = reader.getMaps("dexModifiers");
        this.agiMods = reader.getMaps("agiModifiers");
    }

    public Hero createHero() {
        int level = randInt(1, 4);
        String name = "";
        String type = "";
        int str = 0;
        int dex = 0;
        int agi = 0;
        switch(randInt(0, 3)){
            case 0:
                type = "paladin";
                name = paladins[randInt(0, paladins.length)];
                str = level * (22 + randInt(0, 3));
                dex = level * (14 + randInt(0, 3));
                agi = level * (12 + randInt(0, 1));
                break;
            case 1:
                type = "warrior";
                name = warriors[randInt(0, warriors.length)];
                str = level * (22 + randInt(0, 3));
                dex = level * (12 + randInt(0, 1));
                agi = level * (14 + randInt(0, 3));
                break;
            case 2:
                type = "sorcerer";
                name = sorcerers[randInt(0, sorcerers.length)];
                str = level * (20 + randInt(0, 1));
                dex = level * (14 + randInt(0, 3));
                agi = level * (14 + randInt(0, 3));
        }
        if(randInt(0, 2) == 1){
            switch (randInt(0, 3)) {
                case 0:
                    Map mod = strMods[randInt(0, strMods.length)];
                    name = name + " the " + mod.get("desc");
                    str = Math.max(str + Math.toIntExact((long)mod.get("mod"))*(10 + randInt(0,2)), 1);
                    break;
                case 1:
                    mod = dexMods[randInt(0, dexMods.length)];
                    name = name + " the " + mod.get("desc");
                    dex = Math.max(dex + Math.toIntExact((long)mod.get("mod"))*(10 + randInt(0,2)), 1);
                    break;
                case 2:
                    mod = agiMods[randInt(0, agiMods.length)];
                    name = name + " the " + mod.get("desc");
                    agi = Math.max(agi + Math.toIntExact((long)mod.get("mod"))*(10 + randInt(0,2)), 1);
                    break;
            }
        }
        int hp = level*(100 + randInt(2,6));
        int mp = level*(10 + randInt(2, 6));
        Hero newHero = new Hero(name, level, type, hp, str, dex, agi, mp);
        newHero.setGold(randInt(0, 10));
        return newHero;
    }

    private int randInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
