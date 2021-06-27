package model.zone;



public enum Zones {
    GRAVEYARD("graveyard",60,false),
    HAND("hand",6,false),
    MONSTER("monster",5,true),
    MAGIC("magic",5,true),
    FIELD("field",1,true),
    DECK("deck", 60, false);


    public final String label;
    public final int capacity;
    public final boolean isDiscrete;
    private Zones(String label,int capacity,boolean isDiscrete) {
        this.label = label;
        this.capacity = capacity;
        this.isDiscrete = isDiscrete;
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
