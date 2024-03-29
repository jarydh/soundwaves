package ca.ubc.ece.cpen221.mp1;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BasicTests {

    //Creates a wave with an array of doubles
    @Test
    public void testCreateWave() {
        double[] lchannel = {-0.75, 1.0};
        double[] rchannel = {0.5, 0.1};
        SoundWave wave = new SoundWave(lchannel, rchannel);
        double[] lchannel1 = wave.getLeftChannel();
        Assert.assertArrayEquals(lchannel, lchannel1, 0.00001);
        double[] rchannel1 = wave.getRightChannel();
        Assert.assertArrayEquals(rchannel, rchannel1, 0.00001);
    }

    //Create an empty wave
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

    //Tests the make equal length method
    @Test
    public void unequalCreateWave() {
        double[] shortWave = {0.1, 0.5, 0.7};
        double[] longWave = {0, 0.4, 0.2, 0.6, 0.8, 0.5};
        double[] shortWaveZeros = {0.1, 0.5, 0.7, 0, 0, 0};

        SoundWave wave = new SoundWave(shortWave, longWave);
        SoundWave expected = new SoundWave(shortWaveZeros, longWave);
        Assert.assertArrayEquals(wave.getLeftChannel(), expected.getLeftChannel(), 0.00001);

        wave = new SoundWave(longWave, shortWave);
        expected = new SoundWave(longWave, shortWaveZeros);
        Assert.assertArrayEquals(wave.getRightChannel(), expected.getRightChannel(), 0.00001);
    }

    //creates a sin wave with no offest
    @Test
    public void testCreateSineWaveNoOffset() {
        SoundWave wave = new SoundWave(1, 0, 0.65, 1);
        double[] lchannel = wave.getLeftChannel();
        assertEquals(0.65, lchannel[44100 / 4], 0.00001);
        double[] rchannel = wave.getRightChannel();
        assertEquals(0.65, rchannel[44100 / 4], 0.00001);
    }

    //creates a sin wave with offset
    @Test
    public void testCreateSineWaveWithOffset() {
        SoundWave wave = new SoundWave(1, Math.PI / 2, 0.5, 1);
        double[] lchannel = wave.getLeftChannel();
        assertEquals(0.5, lchannel[44100 / 2], 0.00001);
        double[] rchannel = wave.getRightChannel();
        assertEquals(0.5, rchannel[44100 / 2], 0.00001);
    }


    //test append method to append to an empty wave
    @Test
    public void appendArrayToEmptyWave() {
        double[] lchannel = {1.0, 0.4};
        double[] rchannel = {0.6, -0.9};
        SoundWave wave = new SoundWave();
        wave.append(lchannel, rchannel);
        double[] lchannel1 = wave.getLeftChannel();
        Assert.assertArrayEquals(lchannel, lchannel1, 0.00001);
        double[] rchannel1 = wave.getRightChannel();
        Assert.assertArrayEquals(rchannel, rchannel1, 0.00001);
    }


    //tests append method by passing it an empty array
    @Test
    public void appendEmptyArray() {
        double[] lchannel = {1.0, 0};
        double[] rchannel = {0.5, -0.9};
        double[] empty = {};
        SoundWave wave = new SoundWave(lchannel, rchannel);
        wave.append(empty, empty);
        double[] lchannel1 = wave.getLeftChannel();
        Assert.assertArrayEquals(lchannel, lchannel1, 0.00001);
        double[] rchannel1 = wave.getRightChannel();
        Assert.assertArrayEquals(rchannel, rchannel1, 0.00001);
    }


    //tests appending an array to an existing wave
    @Test
    public void appendArrayToExistingWave() {
        double[] lchannel1 = {1.0};
        double[] rchannel1 = {2.0};
        double[] lchannel2 = {1.0, 2.0};
        double[] rchannel2 = {2.0, 1.0};
        double[] lchannel = {1.0, 1.0, 1.0};
        double[] rchannel = {1.0, 1.0, 1.0};

        SoundWave wave = new SoundWave(lchannel1, rchannel1);
        wave.append(lchannel2, rchannel2);
        double[] lchannel3 = wave.getLeftChannel();
        Assert.assertArrayEquals(lchannel, lchannel3, 0.00001);
        double[] rchannel3 = wave.getRightChannel();
        Assert.assertArrayEquals(rchannel, rchannel3, 0.00001);
    }

    //tests appending another SoundWave to an existing wave
    @Test
    public void appendWaveToExistingWave() {
        double[] lchannel1 = {1.0};
        double[] rchannel1 = {2.0};
        double[] lchannel2 = {1.0, 2.0};
        double[] rchannel2 = {2.0, 1.0};
        double[] lchannel = {1.0, 1.0, 1.0};
        double[] rchannel = {1.0, 1.0, 1.0};

        SoundWave wave = new SoundWave(lchannel1, rchannel1);
        SoundWave wave2 = new SoundWave(lchannel2, rchannel2);
        wave.append(wave2);
        double[] lchannel3 = wave.getLeftChannel();
        Assert.assertArrayEquals(lchannel, lchannel3, 0.00001);
        double[] rchannel3 = wave.getRightChannel();
        Assert.assertArrayEquals(rchannel, rchannel3, 0.00001);
    }

    //test append method to append two channels of unequal length
    @Test
    public void appendUnequalLength() {
        double[] lchannel = {1.0, 0.4, 0.7, -0.6};
        double[] rchannel = {0.6};
        SoundWave wave = new SoundWave();
        wave.append(lchannel, rchannel);

        rchannel = new double[]{0.6, 0.0, 0.0, 0.0};

        double[] lchannel1 = wave.getLeftChannel();
        Assert.assertArrayEquals(lchannel, lchannel1, 0.00001);
        double[] rchannel1 = wave.getRightChannel();
        Assert.assertArrayEquals(rchannel, rchannel1, 0.00001);
    }


    //tests adding an echo to an existing wave using arrays
    @Test
    public void addEcho() {
        double[] lchannel1 = {0.2, 0.6, 0.2, 0.4, 0.8, 0.1};
        double[] rchannel1 = {0.2, 0.6, 0.2, 0.4, 0.8, 0.1};
        double[] lchannelEcho = {0.2, 0.7, 0.5, 0.5, 1, 0.5, 0.05};
        double[] rchannelEcho = {0.2, 0.7, 0.5, 0.5, 1, 0.5, 0.05};

        SoundWave wave = new SoundWave(lchannel1, rchannel1);
        wave = wave.addEcho(1, 0.5);
        double[] lchannel3 = wave.getLeftChannel();
        Assert.assertArrayEquals(lchannelEcho, lchannel3, 0.00001);
        double[] rchannel3 = wave.getRightChannel();
        Assert.assertArrayEquals(rchannelEcho, rchannel3, 0.00001);
    }

    //tests adding an echo to an empty wave
    @Test
    public void addEchoEmpty() {
        double[] empty = new double[0];
        SoundWave wave = new SoundWave();
        wave = wave.addEcho(1, 0.5);
        double[] lchannel3 = wave.getLeftChannel();
        Assert.assertArrayEquals(empty, lchannel3, 0.00001);
        double[] rchannel3 = wave.getRightChannel();
        Assert.assertArrayEquals(empty, rchannel3, 0.00001);
    }

    //tests a wave contained in another wave with arrays
    @Test
    public void simpleTestContains() {
        SoundWave wave = new SoundWave(new double[]{0.1, 0.2, 0.3, 0.4, 0.5},
                new double[]{0.1, 0.2, 0.3, 0.4, 0.5});
        SoundWave wave2 = new SoundWave(new double[]{0.2, 0.3, 0.4}, new double[]{0.2, 0.3, 0.4});

        assertTrue(wave.contains(wave2));
    }

    //tests if a sin wave is contained in another wave
    @Test
    public void testContains() {
        double[] zeros = new double [2000];

        SoundWave wave = new SoundWave(2000, 0, 1, 5);
        SoundWave wave2 = new SoundWave(zeros, zeros);
        wave2.append(new SoundWave(2000, 0, 1, 1));

        assertTrue(wave.contains(wave2));
    }


    //tests the contains method even if waves are scaled, last 3 values scaled by 1/2
    @Test
    public void testContainsWithScaling() {
        SoundWave wave = new SoundWave(new double[]{2, 4, 6, 8, 4}, new double[]{3, 9, 7, 13, 15});
        SoundWave wave2 = new SoundWave(new double[]{3, 4, 2},
                new double[]{3.5, 6.5, 7.5});

        assertTrue(wave.contains(wave2));
    }

    //DFT on an empty wave
    @Test
    public void testDFTEmpty() {
        SoundWave wave = new SoundWave();
        assertEquals(0.0, wave.highAmplitudeFreqComponent(), 0.0001);
    }

    //tests the contains method with scaling and a sin wave
    @Test
    public void testContainsWithScaling2() {
        SoundWave wave = new SoundWave(100, 0, 0.5, 5);
        SoundWave wave2 = new SoundWave(120, 1, 1, 5);

        Assert.assertFalse(wave.contains(wave2));
    }

    //tests the contains method on an empty wave
    @Test
    public void containsEmpty() {
        SoundWave wave = new SoundWave(100, 0, 0.5, 5);
        SoundWave wave2 = new SoundWave();

        Assert.assertFalse(wave.contains(wave2));
    }

    //tests the contains method by checking if a longer wave is contained
    //in a shorter wave
    @Test
    public void containsLonger() {
        double[] zerosShort = new double[10];
        double[] zerosLong = new double[11];

        SoundWave wave = new SoundWave(zerosShort, zerosShort);
        SoundWave wave2 = new SoundWave(zerosLong, zerosLong);

        Assert.assertFalse(wave.contains(wave2));
    }


    //Tests similarity with two identical waves
    @Test
    public void testSimilarity() {
        SoundWave wave = new SoundWave(new double[]{0.1, 0.2, 0.3, 0.4, 0.5},
                new double[]{0.3, 0.9, 0.7, 0.13, 0.15});
        SoundWave wave2 = new SoundWave(new double[]{0.1, 0.2, 0.3, 0.4, 0.5},
                new double[]{0.3, 0.9, 0.7, 0.13, 0.15});

        assertEquals(1.0, wave.similarity(wave2), 0.0001);
        assertEquals(wave.similarity(wave2), wave2.similarity(wave), 0.0001);
    }

    //tests similarity with two identical and scaled waves
    @Test
    public void testSimilarityScaling() {
        SoundWave wave = new SoundWave(new double[]{0.1, 0.2, 0.3, 0.4, 0.5},
                new double[]{0.3, 0.9, 0.7, 0.13, 0.15});
        SoundWave wave2 = new SoundWave(new double[]{0.05, 0.1, 0.15, 0.2, 0.25},
                new double[]{0.15, 0.45, 0.35, 0.065, 0.075});

        assertEquals(1.0, wave.similarity(wave2), 0.0001);
        assertEquals(wave.similarity(wave2), wave2.similarity(wave), 0.0001);
    }

    //tests similarity for two relatively similar waves
    @Test
    public void testSimilarity2() {
        SoundWave wave = new SoundWave(new double[]{1, 0.2, 0.3, 0.4, 0.5},
                new double[]{0.3, 0.9, 0.7, 0.13, 0.15});
        SoundWave wave2 = new SoundWave(new double[]{0.5, 0.1, 0.15},
                new double[]{0.15, 0.45, 0.35});

        assertTrue(wave.similarity(wave2) > 0.5);
        assertEquals(wave.similarity(wave2), wave2.similarity(wave), 0.0001);
    }

    //tests similarity for relatively different waves
    @Test
    public void testSimilarity3() {
        SoundWave wave = new SoundWave(new double[]{1, 0.75, 0.2},
                new double[]{0.2, 0.3, 0.7});
        SoundWave wave2 = new SoundWave(new double[]{0.2, 0.2345, 0.1, 0.6},
                new double[]{0.15, 0.45, 0.35, 0.03});

        assertTrue(wave.similarity(wave2) < 0.8);
        assertEquals(wave.similarity(wave2), wave2.similarity(wave), 0.0001);
    }

    //Tests similarity of waves that are perfectly opposite
    @Test
    public void testSimilarity4() {
        SoundWave wave = new SoundWave(440, 0, 1, 3);
        SoundWave wave2 = new SoundWave(440, Math.PI / 2, 1, 3);

        assertTrue(wave.similarity(wave2) < 0.001);
        assertEquals(wave.similarity(wave2), wave2.similarity(wave), 0.0001);
    }

    //tests similarity for waves where beta and gamma can be calculated manually
    @Test
    public void testSimilarity5() {
        SoundWave wave = new SoundWave(new double[]{0.9, 0.9, 0.9}, new double[]{0.9, 0.9, 0.9});
        SoundWave wave2 = new SoundWave(new double[]{-0.9, -0.9, -0.9},
                new double[]{-0.9, -0.9, -0.9}); //beta < 0

        assertEquals(1 / (1 + 0.9 * 0.9 * 6), wave.similarity(wave2), 0.001);
        assertEquals(wave.similarity(wave2), wave2.similarity(wave), 0.0001);
    }

    //tests similarity for one empty wave
    @Test
    public void testSimilarityOneEmpty() {
        SoundWave wave = new SoundWave(new double[]{0.9, 0.9, 0.9}, new double[]{0.9, 0.9, 0.9});
        SoundWave wave2 = new SoundWave();

        assertEquals(0, wave.similarity(wave2), 0.001);
        assertEquals(wave.similarity(wave2), wave2.similarity(wave), 0.0001);
    }

    //tests similarity for two empty waves
    @Test
    public void testSimilarityBothEmpty() {
        SoundWave wave = new SoundWave();

        assertEquals(1, wave.similarity(wave), 0.001);
    }
}
