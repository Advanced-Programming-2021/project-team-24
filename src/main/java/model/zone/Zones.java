package model.zone;



public enum Zones {
    GRAVEYARD("graveyare"),
    HAND("hand"),
    MONSTER("monster"),
    MAGIC("magic");
    




    public final String label;
    private Zones(String label) {
        this.label = label;
    }


    public static void main(String[] args) {
        if(Zones.GRAVEYARD.label == "grveyard")
        {
            System.out.println("yse");
        }
    }
}
