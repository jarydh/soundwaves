package ca.ubc.ece.cpen221.mp1;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SimilairtyTests {

    //create all possible pairs of three waves with no repeats
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

    //ensure that overridden equals works for WavePair and SoundWave
    @Test
    public void testEquals(){
        SoundWave a = new SoundWave(100, 0.5, 0.5, 2);
        SoundWave b = new SoundWave(150, 2, 1, 5);

        WavePair pair1 = new WavePair(a,b);
        WavePair pair2 = new WavePair(b,a);

        assertTrue(pair1.equals(pair2));
        assertEquals(pair1.hashCode(), pair2.hashCode());

    }

    //create a simple set of sine waves with clear frequency grouping but similar lengths and amplitudes
//    @Test
//    public void testSimilarity1(){
//        Set <SoundWave> comparisonSet = new HashSet<SoundWave>();
//        Set <SoundWave> expected = new HashSet<SoundWave>();
//
//        SoundWave a = new SoundWave(100, 0, 1, 2);
//        SoundWave b = new SoundWave(110, 0, 1, 2);
//        SoundWave c = new SoundWave(101, 0, 1, 2);
//
//        expected.add(a);
//        expected.add(b);
//        expected.add(c);
//
//        SoundWave alpha = new SoundWave(400, 0, 1, 2);
//        SoundWave beta = new SoundWave(305, 0, 1, 1.9);
//        SoundWave gamma = new SoundWave(185, 0, 1, 2.1);
//
//        comparisonSet.add(a);
//        comparisonSet.add(b);
//        comparisonSet.add(c);
//        comparisonSet.add(alpha);
//        comparisonSet.add(beta);
//        comparisonSet.add(gamma);
//
//        assertEquals(expected, SoundWaveSimilarity.getSimilarWaves(comparisonSet, 3, a));
//    }

    //test that one elementary split will group clips appropriately
    @Test
    public void testSimilarity1(){
        Set <SoundWave> comparisonSet = new HashSet<SoundWave>();
        Set <SoundWave> expected = new HashSet<SoundWave>();

        double[] target_s = {0.5, 0.5, 0.5};
        double[] a_s = {0.49, 0.49, 0.49};
        double[] b_s = {0.47, 0.47, 0.47};

        SoundWave target = new SoundWave(target_s, target_s);
        SoundWave a = new SoundWave(a_s, a_s);
        SoundWave b = new SoundWave(b_s, b_s);

        expected.add(target);
        expected.add(a);

        comparisonSet.add(target);
        comparisonSet.add(a);
        comparisonSet.add(b);

        assertEquals(expected, SoundWaveSimilarity.getSimilarWaves(comparisonSet, 2, target));
    }

    //test splitting into a large number of partitions, and test resolution when working with small differences
    @Test
    public void testSimilarity2(){
        Set <SoundWave> comparisonSet = new HashSet<SoundWave>();
        Set <SoundWave> expected = new HashSet<SoundWave>();
        Set <SoundWave> targetSet = new HashSet<SoundWave>();

        double[] target_s = {1, 0.9, 1};
        double[] a_s = {0.999, 0.899, 0.999};


        SoundWave target = new SoundWave(target_s, target_s);
        SoundWave a = new SoundWave(a_s, a_s);

        expected.add(target);
        expected.add(a);

        targetSet.add(target);

        comparisonSet.add(target);
        comparisonSet.add(a);



        for(int i = -100; i < 0; i += 1){
            double next = i / 100.0;
            comparisonSet.add(new SoundWave(new double[] {next,next,next}, new double[] {next,next,next}));
        }

        assertEquals(expected, SoundWaveSimilarity.getSimilarWaves(comparisonSet,2, target));
        assertEquals(targetSet, SoundWaveSimilarity.getSimilarWaves(comparisonSet,3, target));
    }

}
