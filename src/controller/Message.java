package controller;
public class Message{
  private TypeMessage typeMessage;
  private String content;
  public Message(TypeMessage typeMessage, String content)
  {
    this.content = content;
    this.typeMessage = typeMessage;    
  }
  public String getContent()
  {
    return this.content;
  }
  public TypeMessage getTypeMessage()
  {
    return this.typeMessage;
  }
}
