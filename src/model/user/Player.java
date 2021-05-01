package model.user;

public class Player {
    private User user;
    private int lifePoint;
    
    public Player(User user)
    {
        this.lifePoint = 8000;
        this.user = user;        
    }
    public String getNickname()
    {
        return user.getNickname();
    }    
}
