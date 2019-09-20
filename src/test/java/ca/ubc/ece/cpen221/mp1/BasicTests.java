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

    @Test
    public void testCreateEmptyWave() {
        SoundWave wave = new SoundWave();
        double [] lchannel = wave.getLeftChannel();
        double[] lchannel1 = {};
        Assert.assertArrayEquals(lchannel, lchannel1, 0.00001);
        double[] rchannel1 = {};
        double [] rchannel = wave.getLeftChannel();
        Assert.assertArrayEquals(rchannel, rchannel1, 0.00001);
    }

    @Test
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

    @Test
    public void appendEmptyArray() {
        double[] lchannel = {1.0, 1.0};
        double[] rchannel = {2.0, 2.0};
        double[] empty = {};
        SoundWave wave = new SoundWave(lchannel,rchannel);
        wave.append(empty, empty);
        double[] lchannel1 = wave.getLeftChannel();
        Assert.assertArrayEquals(lchannel, lchannel1, 0.00001);
        double[] rchannel1 = wave.getRightChannel();
        Assert.assertArrayEquals(rchannel, rchannel1, 0.00001);
    }

    @Test
    public void appendArrayToExistingWave() {
        double[] lchannel1 = {1.0};
        double[] rchannel1 = {2.0};
        double[] lchannel2 = {1.0, 2.0};
        double[] rchannel2 = {2.0, 1.0};
        double[] lchannel = {1.0, 1.0, 2.0};
        double[] rchannel = {2.0, 2.0, 1.0};

        SoundWave wave = new SoundWave(lchannel1,rchannel1);
        wave.append(lchannel2,rchannel2);
        double[] lchannel3 = wave.getLeftChannel();
        Assert.assertArrayEquals(lchannel, lchannel3, 0.00001);
        double[] rchannel3 = wave.getRightChannel();
        Assert.assertArrayEquals(rchannel, rchannel3, 0.00001);
    }

    @Test
    public void appendWaveToExistingWave() {
        double[] lchannel1 = {1.0};
        double[] rchannel1 = {2.0};
        double[] lchannel2 = {1.0, 2.0};
        double[] rchannel2 = {2.0, 1.0};
        double[] lchannel = {1.0, 1.0, 2.0};
        double[] rchannel = {2.0, 2.0, 1.0};

        SoundWave wave = new SoundWave(lchannel1,rchannel1);
        SoundWave wave2 = new SoundWave(lchannel2,rchannel2);
        wave.append(wave2);
        double[] lchannel3 = wave.getLeftChannel();
        Assert.assertArrayEquals(lchannel, lchannel3, 0.00001);
        double[] rchannel3 = wave.getRightChannel();
        Assert.assertArrayEquals(rchannel, rchannel3, 0.00001);
    }


    @Test
    public void addWaves() {
        double[] lchannel1 = {1.0};
        double[] lchannel2 = {1.0, 2.0};

        double[] rchannel1 = {2.0};
        double[] rchannel2 = {2.0, 1.0};

        double[] lchannel = {2.0, 2.0};
        double[] rchannel = {4.0, 1.0};

        SoundWave wave = new SoundWave(lchannel1,rchannel1);
        SoundWave wave2 = new SoundWave(lchannel2,rchannel2);
        SoundWave wave3 = wave.add(wave2);
        double[] lchannel3 = wave3.getLeftChannel();
        Assert.assertArrayEquals(lchannel, lchannel3, 0.00001);
        double[] rchannel3 = wave3.getRightChannel();
        Assert.assertArrayEquals(rchannel, rchannel3, 0.00001);
    }

    // TODO: add more tests

}
