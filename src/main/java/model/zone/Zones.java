package model.zone;



public enum Zones {
    GRAVEYARD("graveyare"),
    HAND("hand"),
    MONSTER("monster"),
    MAGIC("magic"),
    FIELD("field");


    public final String label;
    private Zones(String label) {
        this.label = label;
    }
    public String getValue()
    {
        return this.label;
    }
    
    public static Zones valueOfLabel(String label) {
        for (Zones e : values()) {
            if (e.label.equals(label)) {
                return e;
            }
        }
        return null;
    }
    public static void main(String[] args) {
        if(Zones.GRAVEYARD.label.equals("grveyard"))
        {
            System.out.println("yse");
        }
    }
}
