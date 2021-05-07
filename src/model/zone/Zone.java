package model.zone;

public class Zone {
    private String name;
    private Boolean opponent;
    public Zone(String name,Boolean opponent){
        this.name = name;
        this.opponent = opponent;
    };
    public String getName()
    {
        return this.name;
    }
    public boolean getOpponent()
    {
        return this.opponent;
    }
    public static void main(String[] args) {
        
    }
}
