package model.effect;

import org.junit.Test;
import static org.junit.Assert.*;

public class EffectTest {
    Effect effect = new Effect("");
    @Test
    public void getSpeedTest(){
        effect.setSpeed(3);
        assertEquals(3, (int) effect.getSpeed());
    }
    @Test
    public void getEffectTypeTest(){
        effect.setEffectType(EffectType.FIELD);
        assertEquals(EffectType.FIELD, effect.getEffectType());
    }
    @Test
    public void getNameTest(){
        assertEquals("", effect.getName());
    }
    @Test
    public void getReverseTest(){
        effect.setReverse("reverse");
        assertEquals("reverse", effect.getReverse());
    }
    @Test
    public void getRequirementCommandStringTest(){
        effect.setRequirementString("required");
        assertEquals(effect.getRequirementCommandString(), "required");
    }
    @Test
    public void getAskableMessageTest(){
        effect.setAskForActivation(true);
        assertTrue(effect.getAskForActivation());
    }
    @Test
    public void getEffectCommandTest(){
        effect.setEffect("effect");
        assertEquals("effect", effect.getEffectCommand());
    }
}
