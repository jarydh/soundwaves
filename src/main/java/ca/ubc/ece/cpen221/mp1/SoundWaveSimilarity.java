package ca.ubc.ece.cpen221.mp1;

import java.util.List;
import java.util.Set;
import ca.ubc.ece.cpen221.mp1.utils.*;

public class SoundWaveSimilarity {

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
    public Set<SoundWave> getSimilarWaves(Set<SoundWave> comparisonSet, int numGroups, SoundWave w) {
        List<WavePair> sortedPairs = sortPairs(createPairs(comparisonSet));
        int nextPairIndex = 0;

        Set<Set<SoundWave>> allPartitions = createPartitions(comparisonSet);

        while (allPartitions.size() > numGroups) {
            WavePair nextPair = sortedPairs.get(nextPairIndex);
            allPartitions = mergePartitions(allPartitions, nextPair);
            nextPairIndex++;
        }

        return getSet(allPartitions, w);
    }

    //Create a set containing all the possible pair combinations
    public Set<WavePair> createPairs (Set<SoundWave> allWaves) {


    }

    //sort the set with the pairs into an ordered list, from most similar to least similar
    public List<WavePair> sortPairs (Set<WavePair> allPairs) {


    }

    //create a set containing partitions (sets) with one wave in each set
    public Set<Set<SoundWave>> createPartitions (Set<SoundWave> allWaves) {


    }


    //mergePartitions if the pair of waves are in different partitions
    public Set<Set<SoundWave>> mergePartitions (Set<Set<SoundWave>> partitions, WavePair pair) {


    }

    //returns the set containing the given wave in the master set of all partitions
    public Set<SoundWave> getSet (Set<Set<SoundWave>> partitions, SoundWave wave) {


    }

}
