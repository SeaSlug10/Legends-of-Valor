import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class MonsterFactory {
    private String[] exoskeletons;
    private String[] dragons;
    private String[] spirits;
    private Map[] defMods;
    private Map[] dmgMods;
    private Map[] agiMods;


    public MonsterFactory(String file) {
        JSONReader reader = new JSONReader(file);
        this.exoskeletons = reader.getStringArray("exoskeletons");
        this.dragons = reader.getStringArray("dragons");
        this.spirits = reader.getStringArray("spirits");
        this.defMods = reader.getMaps("defModifiers");
        this.dmgMods = reader.getMaps("dmgModifiers");
        this.agiMods = reader.getMaps("agiModifiers");
    }

    public Monster createMonster(Hero[] party) {
        int totalLevel = 0;
        for(Hero hero : party) {
            totalLevel += hero.getLevel();
        }
        int avgLevel = totalLevel / party.length;
        int level = randInt(Math.max(1, avgLevel - 2), avgLevel + 2);
        String name = "";
        int dmg = 0;
        int def = 0;
        int agi = 0;
        String type = "";
        switch (randInt(0, 3)){
            case 0:
                name = exoskeletons[randInt(0, exoskeletons.length)];
                agi = level*(10 + randInt(0,1));
                def = level*(6 + randInt(0,3));
                dmg = level*(10 + randInt(0,1));
                type = "exoskeleton";
                break;
            case 1:
                name = dragons[randInt(0, dragons.length)];
                agi = level*(10 + randInt(0,1));
                def = level*(5 + randInt(0,1));
                dmg = level*(12 + randInt(0,3));
                type = "dragon";
                break;
            case 2:
                name = spirits[randInt(0, spirits.length)];
                agi = level*(12 + randInt(0,3));
                def = level*(5 + randInt(0,1));
                dmg = level*(10 + randInt(0,1));
                type = "spirit";
                break;
        }
        //50% chance to add a modifier
        if(randInt(0, 2) == 1){
            switch (randInt(0, 3)) {
                case 0:
                    Map mod = defMods[randInt(0, defMods.length)];
                    name = mod.get("desc") + " " + name;
                    def = Math.max(def + Math.toIntExact((long)mod.get("mod"))*(5 + randInt(0,2)), 1);
                    break;
                case 1:
                    mod = dmgMods[randInt(0, dmgMods.length)];
                    name = mod.get("desc") + " " + name;
                    dmg = Math.max(dmg + Math.toIntExact((long)mod.get("mod"))*(10 + randInt(0,2)), 1);
                    break;
                case 2:
                    mod = agiMods[randInt(0, agiMods.length)];
                    name = mod.get("desc") + " " + name;
                    agi = Math.max(agi + Math.toIntExact((long)mod.get("mod"))*(10 + randInt(0,2)), 1);
                    break;
            }
        }
        int hp = level*(100 + randInt(0,6));
        int xp = ((def + dmg + agi)/3);
        Monster mons =  new Monster(def, dmg, name, level, hp, agi, type);
        mons.setXp(new int[]{xp, xp});
        mons.setGold(level + (int)(1.5*randInt(1, 4)));
        return mons;
    }

    private int randInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
