package ca.ubc.ece.cpen221.mp1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ca.ubc.ece.cpen221.mp1.utils.*;

public class SoundWaveSimilarity {

    private Set<Set<SoundWave>> allPartitions = new HashSet<Set<SoundWave>>();
    private List<WavePair> sortedPairs = new ArrayList<WavePair>();
    private int numGroups;
    private SoundWave w;

    //constructor
    private SoundWaveSimilarity (Set<SoundWave> comparisonSet, int numGroups, SoundWave w){
        this.numGroups = numGroups;
        this.w = w;

        this.allPartitions = createPartitions(comparisonSet);
        this.sortedPairs = sortPairs(createPairs(comparisonSet));
    }


    /**
     * Find the sound waves that w is most similar to from the waves in comparisonSet.
     *
     * @param comparisonSet is not null,
     *                      and is set of waves that we will organize in
     *                      similarity groups to identify the group that w
     *                      belongs to. This set should contain w.
     * @param numGroups     is between 1 and the size of the comparisonSet, and
     *                      represents the number of groups to partition the
     *                      set of waves into.
     * @param w             is not null and is included in comparisonSet.
     * @return the set of waves that are in the same group as w after grouping.
     */
    public static Set<SoundWave> getSimilarWaves(Set<SoundWave> comparisonSet, int numGroups, SoundWave w) {

        SoundWaveSimilarity operator = new SoundWaveSimilarity(comparisonSet, numGroups, w);
        return operator.getSimilarWaves();

    }

    //get the set
    private Set<SoundWave> getSimilarWaves(){

        int nextPairIndex = 0;

        WavePair nextPair = new WavePair();

        while (allPartitions.size() > numGroups) {
            nextPair = sortedPairs.get(nextPairIndex);
            mergePartitions(nextPair);
            nextPairIndex++;
        }

        return getSet(w);

    }

    //Create a set containing all the possible pair combinations
    private static Set<WavePair> createPairs (Set<SoundWave> allWaves) {
        return null;
    }

    //sort the set with the pairs into an ordered list, from most similar to least similar
    private static List<WavePair> sortPairs (Set<WavePair> allPairs) {

        return null;
    }

    //create a set containing partitions (sets) with one wave in each set
    private static Set<Set<SoundWave>> createPartitions (Set<SoundWave> allWaves) {

        return null;
    }


    //mergePartitions if the pair of waves are in different partitions
    private void mergePartitions (WavePair pair) {

    }

    //returns the set containing the given wave in the master set of all partitions
    private Set<SoundWave> getSet (SoundWave wave) {

        return null;
    }

}
