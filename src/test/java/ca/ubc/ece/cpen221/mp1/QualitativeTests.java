package ca.ubc.ece.cpen221.mp1;

import javazoom.jl.player.StdPlayer;
import org.junit.Test;

public class QualitativeTests {

    /**
     * Note: These tests are for methods that are hard to test empirically.
     * They will help us achieve higher code coverage, but they require a
     * person to listen to the output.
     *
     * The tests are commented out for now because they take a few minutes to run.
     */

    //Tests the sin wave is creating at the correct frequency. Should be a note A.
    //@Test
    public void makeSinWave() {
        StdPlayer.open();
        SoundWave wave = new SoundWave(440, 0, 1, 5);
        wave.sendToStereoSpeaker();
        StdPlayer.close();
    }

    /** Tests the high pass filter. Plays a royalty free song downloaded from the internet.
        Should have the bass line removed. This same song plays when testing the main function.
     */
    //@Test
    public void testHighPass() {
        StdPlayer.open("mp3/energy.mp3");
        SoundWave sw = new SoundWave();
        while (!StdPlayer.isEmpty()) {
            double[] lchannel = StdPlayer.getLeftChannel();
            double[] rchannel = StdPlayer.getRightChannel();
            sw.append(lchannel, rchannel);
        }
        sw = sw.highPassFilter(100, 2);
        sw.scale(10);
        sw.sendToStereoSpeaker();
        StdPlayer.close();
    }

    /**
     * Tests the main method in SoundWave to see if we can play a wave.
     * Also used to compare with the high pass filter.
     */
    //@Test
    public void testMain() {
        SoundWave.main(new String[0]);
    }

}
