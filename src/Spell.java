public class Spell extends Item{
    private final int damage;
    private final int mpCost;
    private final String dmgType;

    public Spell(String name, int value, int level, int uses, int uuid, int damage, int mpCost, String dmgType) {
        super(name, value, level, uses, uuid);
        this.damage = damage;
        this.mpCost = mpCost;
        this.dmgType = dmgType;
    }

    public int getDamage() {
        return damage;
    }

    public int getMpCost() {
        return mpCost;
    }

    public String getDmgType() {
        return dmgType;
    }

    public String toString(){
        return String.format("LVL %d %s, deal %d %s damage, Costs %d mp. Uses: %d/%d, value: %s%d%s",
                level, name, damage, dmgType, mpCost, uses[0], uses[1], Colors.YELLOW,value,Colors.RESET);
    }
}
