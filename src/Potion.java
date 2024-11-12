public class Potion extends Item{
    private String modType;
    private int modAmt;

    public Potion(String name, int value, int level, int uses, int uuid, String modType, int modAmt) {
        super(name, value, level, uses, uuid);
        this.modType = modType;
        this.modAmt = modAmt;
    }

    public String getModType() {
        return modType;
    }
    public int getModAmt() {
       return modAmt;
    }

    public String toString(){
        return String.format("LVL %d %s, Increase %s by %d, Uses: %d/%d, value: %s%d%s",
                level, name, modType, modAmt, uses[0], uses[1], Colors.YELLOW, value, Colors.RESET);
    }
}
