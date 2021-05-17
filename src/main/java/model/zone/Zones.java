package model.zone;



public enum Zones {
    GRAVEYARD("graveyard",60),
    HAND("hand",5),
    MONSTER("monster",5),
    MAGIC("magic",5),
    FIELD("field",1);


    public final String label;
    public final int capacity;
    private Zones(String label,int capacity) {
        this.label = label;
        this.capacity = capacity;
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
        if(Zones.GRAVEYARD.label.equals("graveyard"))
        {
            System.out.println("yse");
        }
    }
}
