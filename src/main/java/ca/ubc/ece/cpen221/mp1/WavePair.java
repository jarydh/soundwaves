package ca.ubc.ece.cpen221.mp1;

public class WavePair implements Comparable{
    SoundWave wave1;
    SoundWave wave2;
    double similarity;

    public WavePair (SoundWave wave1, SoundWave wave2) {
        this.wave1 = wave1;
        this.wave2 = wave2;

        similarity = wave1.similarity(wave2);
    }

    @Override
    public int compareTo(Object o) {
        WavePair other = (WavePair)o;

        if (this.similarity > other.similarity) {
            return -1;
        }

        if (this.similarity < other.similarity) {
            return 1;
        }

        return 0;
    }
}
