package ca.ubc.ece.cpen221.mp1;

import javax.net.ssl.SSLEngineResult;
import java.sql.SQLOutput;
import java.util.*;

public class SoundWaveSimilarity {

    private Set<Set<SoundWave>> allPartitions;
    private List<WavePair> sortedPairs;
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

    /** Merges partitions containing the soundwave pairs until the
     * required number of partitions is met. Modifies the set containing
     * all the partitions.
     *
     * @return returns the set of soundwaves which contains
     * the wave w that is being searched for.
     */
    private Set<SoundWave> getSimilarWaves(){

        int nextPairIndex = 0;

        WavePair nextPair;

        while (allPartitions.size() > numGroups) {
            nextPair = sortedPairs.get(nextPairIndex);
            mergePartitions(nextPair);
            nextPairIndex++;
        }

        return findSetContaining(w);

    }

    //Create a set containing all the possible pair combinations
    private static Set<WavePair> createPairs (Set<SoundWave> allWaves) {
        return null;
    }

    //sort the set with the pairs into an ordered list, from most similar to least similar
    private static List<WavePair> sortPairs (Set<WavePair> allPairs) {

        return null;
    }


    /**
     * Create a set containing partitions (sets) with one wave in each partition
     *
     * @param allWaves a set containing all the waves to be put into partitions
     * @return a set containing partitions (sets) with one wave in each partition
     */
    private static Set<Set<SoundWave>> createPartitions (Set<SoundWave> allWaves) {
        Set<Set<SoundWave>> allPartitions = new HashSet<Set<SoundWave>>();
        Set<SoundWave> nextPartition;

        for (SoundWave nextWave: allWaves) {
            nextPartition = new HashSet<SoundWave>();
            nextPartition.add(nextWave);
            allPartitions.add(nextPartition);
        }

        return allPartitions;
    }


    /**
     * Checks the set containing all partitions to see which partitions
     * the given waves are in. If they are in different partitions, merge
     * the partitions. If they are in the same partition, do nothing.
     *
     * Modifies allPartitions
     *
     * @param pair the pair of waves to be merged
     */
    private void mergePartitions (WavePair pair) {
        Set<SoundWave> set1 = findSetContaining(pair.wave1);
        Set<SoundWave> set2 = findSetContaining(pair.wave2);

        if (!set1.equals(set2)) {
            Set<SoundWave> newSet = new HashSet<SoundWave>(set1);
            newSet.addAll(set2);

            allPartitions.remove(set1);
            allPartitions.remove(set2);
            allPartitions.add(newSet);
        }
    }

    /**
     * Returns the set containing the given wave in the master set of
     * all partitions. Throws IllegalArgumentException if the wave is not found.
     *
     * @param wave wave to search for, assumes wave should be found in at least one
     *             of the partitions.
     * @return a set of soundwaves containing the given wave,
     * found in the master set of all partitions
     */
    private Set<SoundWave> findSetContaining (SoundWave wave) throws IllegalArgumentException {
        for (Set<SoundWave> nextPartition : allPartitions) {
            if (nextPartition.contains(wave)) {
                return nextPartition;
            }
        }

        throw new IllegalArgumentException();
    }

}
