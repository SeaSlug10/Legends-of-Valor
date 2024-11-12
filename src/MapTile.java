public class MapTile {
    private String type;
    private boolean occupied;

    public MapTile(String type) {
        this.type = type;
        this.occupied = false;
    }

    public boolean isOccupied() {
        return occupied;
    }
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    public String getType() {
        return type;
    }

    public String toString() {
        switch (type) {
            case "common":
                if(occupied){
                    return Colors.GREEN + "P" + Colors.RESET;
                } else {
                    return " ";
                }
            case "market":
                if(occupied){
                    return Colors.YELLOW_BG +Colors.BLACK+ "P" + Colors.RESET;
                } else {
                    return Colors.YELLOW_BG +Colors.BLACK+ "M" + Colors.RESET;
                }
            case "inaccessible":
                return "█";
            default:
                return "";
        }
    }
}
