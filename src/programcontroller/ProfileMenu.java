package programcontroller;

import model.user.User;

public class ProfileMenu extends Menu{
    public Message changePassword(String oldPassword, String newPassword){
        if (user.getUserByNameAndPassword(user.toString(), oldPassword) != null){
            if (newPassword.equals(oldPassword)) {
                return new Message(TypeMessage.ERROR, "please enter a new password");
            }
            else {
                user.setPassword(newPassword);
                return new Message(TypeMessage.SUCCESSFUL, "password changed successfully!");
            }
        }
        else return new Message(TypeMessage.ERROR, "current password is invalid");
    }
    public Message changeNickname(String nickname){
        //check users nicknames
        for (int i = 0; i < User.; i++){

        }
        user.setNickname(String nickname);
    }
}