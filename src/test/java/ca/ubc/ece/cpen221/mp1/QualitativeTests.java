package ca.ubc.ece.cpen221.mp1;

import javazoom.jl.player.StdPlayer;
import org.junit.Test;

public class QualitativeTests {

    @Test
    public void makeSinWave(){
        StdPlayer.open();
        SoundWave wave = new SoundWave(440, 0, 1, 0);
        wave.sendToStereoSpeaker();
    }
}
