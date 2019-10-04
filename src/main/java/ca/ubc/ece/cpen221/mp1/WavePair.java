package ca.ubc.ece.cpen221.mp1;

import java.util.Objects;

public class WavePair implements Comparable{
    SoundWave wave1;
    SoundWave wave2;
    double similarity;

    public WavePair (SoundWave wave1, SoundWave wave2) {
        this.wave1 = wave1;
        this.wave2 = wave2;

        similarity = wave1.similarity(wave2);
    }

    public WavePair(){
        //TODO: check if empty object causes problems
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

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WavePair p = (WavePair) o;

        return ((wave1.equals(p.wave1) && wave2.equals(p.wave2)) || (wave1.equals(p.wave2) && wave2.equals(p.wave1)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(similarity);
    }
}
