package programcontroller;

import model.user.User;

import java.util.List;

public class ProfileMenu extends MainMenu {
    public ProfileMenu(User user){
        super(user);
    }
    public Message changePassword(String oldPassword, String newPassword){
        if (newPassword.compareTo(oldPassword) == 0) return new Message(TypeMessage.ERROR, "please enter a new password");
        else if (user.changePassword(oldPassword, newPassword)) return new Message(TypeMessage.SUCCESSFUL, "password changed successfully!");
        else return new Message(TypeMessage.ERROR, "current password is invalid");
    }
    public Message changeNickname(String nickname){
        List<String> usernames = User.getUsernames();
        for (int i = 0; i < usernames.size(); i++){
            if(User.readUser(usernames.get(i)).getNickname().compareTo(nickname) == 0)
            {
                return new Message(TypeMessage.ERROR, "user with nickname " + nickname + " already exists");
            }
        }
        user.changeNickname(nickname);
        return new Message(TypeMessage.SUCCESSFUL, "nickname changed successfully!");
    }
}