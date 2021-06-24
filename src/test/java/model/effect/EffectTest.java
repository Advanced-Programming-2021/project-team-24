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
    public void getReverseTest(){

    }
}
