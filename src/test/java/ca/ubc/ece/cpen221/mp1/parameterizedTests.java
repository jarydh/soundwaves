
package ca.ubc.ece.cpen221.mp1;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class parameterizedTests {

    @Test
    public void DFT_OneFreq() {
        for (int freq = 0; freq < 1000; freq += 100) {
            SoundWave wave = new SoundWave(freq, 5, 0.5, 0.1 );
            double maxFreq = wave.highAmplitudeFreqComponent();
            System.out.println(maxFreq);
            assertEquals(freq, maxFreq, 0.00001);
        }
    }

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
            System.out.println(maxFreq);
            assertEquals(freq3, maxFreq, 0.00001);
        }
    }

}