import java.awt.*;

public class Weapon extends Item{
    private final boolean twoHand;
    private boolean equipped;
    private final int damage;


    public Weapon(String name, int value, int level, int uses, int uuid, boolean twoHand, int damage){
        super(name, value, level, uses, uuid);
        this.equipped = false;
        this.twoHand = twoHand;
        this.damage = damage;

    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

    public boolean isTwoHand() {
        return twoHand;
    }

    public int getDamage() {
        return damage;
    }

    public String toString(){
        String outStr =  String.format("LVL %d %s, deals %d DMG, Durability: %d/%d",
                level, name, damage, uses[0], uses[1], value);
        if(twoHand){
            outStr += ", Two-handed.";
        } else {
            outStr += ", One-Handed.";
        }
        outStr += " value: " + Colors.YELLOW + value + Colors.RESET;
        if(equipped){
            outStr += " (equipped)";
        }
        return outStr;
    }
}
