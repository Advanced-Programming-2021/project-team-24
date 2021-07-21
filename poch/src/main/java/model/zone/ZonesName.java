package model.zone;



public enum ZonesName {
    GRAVEYARD("graveyard",60,false),
    HAND("hand",5,false),
    MONSTER("monster",5,true),
    MAGIC("magic",5,true),
    FIELD("field",1,true),
    DECK("deck", 60, false);
    public static final String[] zoneStrings = {"graveyard", "monster", "magic", "hand", "field", "deck"};

    public final String label;
    public final int capacity;
    public final boolean isDiscrete;
    private ZonesName(String label,int capacity,boolean isDiscrete) {
        this.label = label;
        this.capacity = capacity;
        this.isDiscrete = isDiscrete;
    }
    public String getValue()
    {
        return this.label;
    }
    
    public static ZonesName valueOfLabel(String label) {
        for (ZonesName e : values()) {
            if (e.label.equals(label)) {
                return e;
            }
        }
        return null;
    }
    public static void main(String[] args) {
        if(ZonesName.GRAVEYARD.label.equals("graveyard"))
        {
            System.out.println("yse");
        }
    }
}
