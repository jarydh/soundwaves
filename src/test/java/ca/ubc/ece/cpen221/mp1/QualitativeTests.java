package ca.ubc.ece.cpen221.mp1;

import javazoom.jl.player.StdPlayer;
import org.junit.Test;

public class QualitativeTests {

    @Test
    public void makeSinWave(){
        StdPlayer.open();
        SoundWave wave = new SoundWave(440, 0, 1, 0);
        wave.sendToStereoSpeaker();
        StdPlayer.close();
    }

    @Test
    public void testHighPass(){
        StdPlayer.open("mp3/sail.mp3");
        SoundWave sw = new SoundWave();
        while (!StdPlayer.isEmpty()) {
            double[] lchannel = StdPlayer.getLeftChannel();
            double[] rchannel = StdPlayer.getRightChannel();
            sw.append(lchannel, rchannel);
        }
        sw = sw.highPassFilter(100,2);
        sw.scale(10);
        sw.sendToStereoSpeaker();
        StdPlayer.close();
    }
}
