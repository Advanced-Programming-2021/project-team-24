package controller;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class MessageTest {
    Message message = new Message(TypeMessage.SUCCESSFUL, "hey");
    @Test
    public void getContentTest(){
        assertEquals(message.getContent(), "hey");
    }
    @Test
    public void getTypeMessage(){
        assertEquals(message.getTypeMessage(), TypeMessage.SUCCESSFUL);
    }
}
