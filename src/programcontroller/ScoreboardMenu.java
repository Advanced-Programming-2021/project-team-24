package programcontroller;

import model.user.User;

public class ScoreboardMenu extends Menu{
    private List<User> userList;//get users from User Class
    public ScoreboardMenu();
    private void sortUserList(){
        for (int i = 0; i < userList.size(); i++) {
            for (int j = i+1; j < userList.size; j++) {
                if (userList.get(i).getScore < userList.get(j).getScore){

                }
                else if (userList.get(i).getScore == userList.get(j).getScore){
                    if (userList.get(i).getNickname.compareTo(userList.get(j).getNickname) > 0){

                    }
                }
            }

        }
    }
    public Message showScoreboard(){
        sortUserList(userList);
        String content = "";
        for (int i = 1; i < userList.size()+1; i++) {
            content += i + "- " + userList.get(i-1).getNickname() + ": " + userList.get(i-1).getScore() + "\n";
        }
        return new Message(TypeMessage.SUCCESSFUL, content);
    }
}