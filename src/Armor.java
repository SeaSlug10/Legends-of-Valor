public class Armor extends Item{
    private final String slot;
    private boolean equipped;
    private final int damageReduction;

    public Armor(String name, int value, int level, int uses, int uuid, String slot, int damageReduction){
        super(name, value, level, uses, uuid);
        this.slot = slot;
        this.damageReduction = damageReduction;
    }

    public String getSlot() {
        return slot;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

    public int getReduction(){
        return damageReduction;
    }

    public String toString(){
        String outStr = String.format("LVL %d %s, adds %d DEF, worn on %s. Durability: %d/%d, value: %s%d%s",
                level, name, damageReduction, slot, uses[0], uses[1], Colors.YELLOW,value,Colors.RESET);
        if(equipped){
            outStr = outStr + " (equipped)";
        }
        return outStr;
    }
}
