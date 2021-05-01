package user;

public class User {
    private String nickname;
    private String username;
    private String password;
    public String getNickname()
    {
        return this.nickname;
    }   
    
    public String getUsername()
    {
        return this.username;
    }
    private boolean comparePassword(String password)
    {
        if(password.compareTo(this.password) == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    //TODO
}
