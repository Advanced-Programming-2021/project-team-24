package programcontroller;
public class Message{
  public TypeMessage typeMessage;
  public String content;
  public Message(TypeMessage typeMessage, String content)
  {
    this.content = content;
    this.typeMessage = typeMessage;    
  }
}
