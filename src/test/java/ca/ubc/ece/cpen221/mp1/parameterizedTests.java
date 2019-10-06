package ca.ubc.ece.cpen221.mp1;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class parameterizedTests {

    //Tests the DFT for waves with 1 frequency
    @Test
    public void DFT_OneFreq() {
        for (int freq = 0; freq < 1000; freq += 100) {
            SoundWave wave = new SoundWave(freq, 5, 0.5, 0.1 );
            double maxFreq = wave.highAmplitudeFreqComponent();
            assertEquals(freq, maxFreq, 0.00001);
        }
    }

    //Tests the DFT for waves with multiple frequencies
    @Test
    public void DFT_MultipleFreq() {
        int freq1;
        int freq2;
        int freq3;
        for (int i = 1; i < 10; i ++) {
            freq1 = i * 100;
            freq2 = i * 200;
            freq3 = i * 300;
            SoundWave wave1 = new SoundWave(freq1, 0, 0.3, 0.1 );
            SoundWave wave2 = new SoundWave(freq2, 2, 0.3, 0.1 );
            SoundWave wave3 = new SoundWave(freq3, 3, 0.3, 0.1 );
            SoundWave wave = wave1.add(wave2.add(wave3));

            double maxFreq = wave.highAmplitudeFreqComponent();
            assertEquals(freq3, maxFreq, 0.00001);
        }
    }

    //Tests the echo using sin waves
    @Test
    public void addEchoSinWave() {
        int freq1;
        int freq2;
        int freq3;

        double[] zeros = {0, 0, 0};


        for (int i = 1; i < 10; i ++) {
            int freq = i * 100;

            SoundWave wave2 = new SoundWave(zeros, zeros);

            SoundWave wave1 = new SoundWave(freq, 0, 0.5, 0.1 );
            wave2.append(new SoundWave(freq, 0, 0.25, 0.1 ));
            SoundWave waveEcho = wave1.addEcho(3, 0.5);
            SoundWave waveAdd = wave1.add(wave2);

            Assert.assertArrayEquals(waveEcho.getRightChannel(), waveAdd.getRightChannel(), 0.00001);
        }
    }

}
