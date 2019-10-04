package ca.ubc.ece.cpen221.mp1;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SimilairtyTests {

    @Test
    public void testCreatePairs()
    {
        SoundWave a = new SoundWave(100, 0.5, 0.5, 2);
        SoundWave b = new SoundWave(150, 2, 1, 5);
        SoundWave c = new SoundWave(4000, 7, 0.1, 1);

        Set<SoundWave> allWaves = new HashSet<>();
        allWaves.add(a);
        allWaves.add(b);
        allWaves.add(c);


        Set<WavePair> expected = new HashSet<>();
        expected.add(new WavePair(a,b));
        expected.add(new WavePair(a,c));
        expected.add(new WavePair(b,c));

        Set<WavePair> actual = SoundWaveSimilarity.createPairs(allWaves);

        assertEquals(expected, actual);

    }

    @Test
    public void testEquals(){
        SoundWave a = new SoundWave(100, 0.5, 0.5, 2);
        SoundWave b = new SoundWave(150, 2, 1, 5);

        WavePair pair1 = new WavePair(a,b);
        WavePair pair2 = new WavePair(b,a);

        assertTrue(pair1.equals(pair2));
        assertEquals(pair1.hashCode(), pair2.hashCode());

    }

}
