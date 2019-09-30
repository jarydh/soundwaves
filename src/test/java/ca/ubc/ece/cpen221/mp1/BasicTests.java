package ca.ubc.ece.cpen221.mp1;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void testCreateSineWaveNoOffset() {
        SoundWave wave = new SoundWave(1, 0, 0.65, 1 );
        double[] lchannel = wave.getLeftChannel();
        assertEquals(0.65, lchannel[44100/4], 0.00001);
        double[] rchannel = wave.getRightChannel();
        assertEquals(0.65, rchannel[44100/4], 0.00001);
    }

    @Test
    public void testCreateSineWaveWithOffset() {
        SoundWave wave = new SoundWave(1, Math.PI/2, 0.5, 1 );
        double[] lchannel = wave.getLeftChannel();
        assertEquals(0.5, lchannel[44100/2], 0.00001);
        double[] rchannel = wave.getRightChannel();
        assertEquals(0.5, rchannel[44100/2], 0.00001);
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
    public void highPass() {
        double[] lchannel = {1.0, 1.0};
        double[] rchannel = {2.0, 2.0};
        SoundWave wave = new SoundWave(lchannel,rchannel);
        wave = wave.highPassFilter(5,6);
        double[] lchannel1 = wave.getLeftChannel();
        Assert.assertArrayEquals(lchannel, lchannel1, 0.00001);
        double[] rchannel1 = wave.getRightChannel();
        Assert.assertArrayEquals(rchannel, rchannel1, 0.00001);
    }


    @Test
    public void addWaves() {
        double[] lchannel1 = {1.0};
        double[] lchannel2 = {1.0, 2.0};


    }

    @Test
    public void simpleTestContains() {
        SoundWave wave = new SoundWave(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        SoundWave wave2 = new SoundWave(new double[]{2,3,4},new double[]{2, 3, 4});

        assertTrue(wave.contains(wave2));
    }

    @Test
    public void testContains() {
        SoundWave wave = new SoundWave(20, 0, 1, 5);
        SoundWave wave2 = new SoundWave(20, Math.PI/2, 1, 1);

        assertTrue(wave.contains(wave2));
    }

    @Test
    public void testContainsWithScaling() {
        SoundWave wave = new SoundWave(new double[]{2, 4, 6, 8, 4}, new double[]{3, 9, 7, 13, 15});
        SoundWave wave2 = new SoundWave(new double[]{3,4,2},new double[]{3.5, 6.5, 7.5}); //last 3 values scaled by 1/2

        assertTrue(wave.contains(wave2));
    }

    @Test
    public void testContainsWithScaling2() {
        SoundWave wave = new SoundWave(100, 0, 0.5, 5);
        SoundWave wave2 = new SoundWave(120, 1, 1, 5);

        Assert.assertFalse(wave.contains(wave2));
    }

    @Test
    public void testSimilarity() {
        SoundWave wave = new SoundWave (new double[]{0.1, 0.2, 0.3, 0.4, 0.5}, new double[]{0.3, 0.9, 0.7, 0.13, 0.15});
        SoundWave wave2 = new SoundWave(new double[]{0.1, 0.2, 0.3, 0.4, 0.5},new double[]{0.3, 0.9, 0.7, 0.13, 0.15});

        assertEquals(1.0, wave.similarity(wave2), 0.0001);
    }

    @Test
    public void testSimilarityScaling() {
        SoundWave wave = new SoundWave (new double[]{0.1, 0.2, 0.3, 0.4, 0.5}, new double[]{0.3, 0.9, 0.7, 0.13, 0.15});
        SoundWave wave2 = new SoundWave(new double[]{0.05, 0.1, 0.15, 0.2, 0.25},new double[]{0.15, 0.45, 0.35, 0.065, 0.075});

        assertEquals(1.0, wave.similarity(wave2), 0.0001);
    }

    @Test
    public void testSimilarity2() {
        SoundWave wave = new SoundWave (new double[]{1, 0.2, 0.3, 0.4, 0.5}, new double[]{0.3, 0.9, 0.7, 0.13, 0.15});
        SoundWave wave2 = new SoundWave(new double[]{0.5, 0.1, 0.15},new double[]{0.15, 0.45, 0.35});

        assertTrue(wave.similarity(wave2) < 0.8);
    }

    @Test
    public void testSimilarity3() {
        SoundWave wave = new SoundWave (new double[]{1, 0.75, 0.2,}, new double[]{0.2, 0.3, 0.7});
        SoundWave wave2 = new SoundWave(new double[]{0.2,0.2345, 0.1, 0.6},new double[]{0.15, 0.45, 0.35, 0.03});

        assertTrue(wave.similarity(wave2) < 0.8);
    }

    @Test
    public void testSimilarity4() {
        SoundWave wave = new SoundWave (440,0,1,3);
        SoundWave wave2 = new SoundWave(440, Math.PI/2,1,3);
        System.out.println(wave.similarity(wave2));
        assertTrue(wave.similarity(wave2) < 0.001);
    }

}
