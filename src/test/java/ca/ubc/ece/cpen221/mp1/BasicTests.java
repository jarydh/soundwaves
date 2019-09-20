package ca.ubc.ece.cpen221.mp1;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BasicTests {

    @Test
    public void testCreateWave() {
        double[] lchannel = {1.0, 1.0};
        double[] rchannel = {2.0, 2.0};
        SoundWave wave = new SoundWave(lchannel, rchannel);
        double[] lchannel1 = wave.getLeftChannel();
        Assert.assertArrayEquals(lchannel, lchannel1, 0.00001);
        double[] rchannel1 = wave.getRightChannel();
        Assert.assertArrayEquals(rchannel, rchannel1, 0.00001);
    }

    public void testCreateEmptyWave() {
        SoundWave wave = new SoundWave();
        double [] lchannel = wave.getLeftChannel();
        double[] lchannel1 = {};
        Assert.assertArrayEquals(lchannel, lchannel1, 0.00001);
        double[] rchannel1 = {};
        double [] rchannel = wave.getLeftChannel();
        Assert.assertArrayEquals(rchannel, rchannel1, 0.00001);
    }

    public void appendArrayToEmptyWave() {
        double[] lchannel = {1.0, 1.0};
        double[] rchannel = {2.0, 2.0};
        SoundWave wave = new SoundWave();
        wave.append(lchannel,rchannel);
        double[] lchannel1 = wave.getLeftChannel();
        Assert.assertArrayEquals(lchannel, lchannel1, 0.00001);
        double[] rchannel1 = wave.getRightChannel();
        Assert.assertArrayEquals(rchannel, rchannel1, 0.00001);
    }

    public void appendArrayToExistingWave() {


    }

    // TODO: add more tests

}
