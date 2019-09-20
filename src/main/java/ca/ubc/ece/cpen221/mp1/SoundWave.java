package ca.ubc.ece.cpen221.mp1;

import ca.ubc.ece.cpen221.mp1.utils.HasSimilarity;
import javazoom.jl.player.StdPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class SoundWave implements HasSimilarity<SoundWave> {

    // We are going to treat the number of samples per second of a sound wave
    // as a constant.
    // The best way to refer to this constant is as
    // SoundWave.SAMPLES_PER_SECOND.
    public static final int SAMPLES_PER_SECOND = 44100;

    // some representation fields that you could use
    private ArrayList<Double> lchannel = new ArrayList<>();
    private ArrayList<Double> rchannel = new ArrayList<>();
    private int samples = 0;

    /**
     * Create a new SoundWave using the provided left and right channel
     * amplitude values. After the SoundWave is created, changes to the
     * provided arguments will not affect the SoundWave.
     *
     * @param lchannel is not null, contains only values between -1 and 1, is the same size as rchannel, and represents the left channel.
     * @param rchannel is not null, contains only values between -1 and 1, is the same size as lchannel and represents the right channel.
     */
    public SoundWave(double[] lchannel, double[] rchannel) {
        for(double sample : lchannel){
            this.lchannel.add(sample);
        }
        for(double sample : rchannel){
            this.rchannel.add(sample);
        }

        updateSamples();

        // TODO: Potentially find a method
        // TODO: check if inputs need to be checked
        // TODO: Implement this constructor

    }

    /**
     * Creates an empty soundwave
     */
    public SoundWave() {

        // TODO: You should implement a default constructor
        // that creates an empty wave
    }

    /**
     * Create a new sinusoidal sine wave,
     * sampled at a rate of 44,100 samples per second
     *
     * @param freq      the frequency of the sine wave, in Hertz
     * @param phase     the phase of the sine wave, in radians
     * @param amplitude the amplitude of the sine wave, 0 < amplitude <= 1
     * @param duration  the duration of the sine wave, in seconds
     */
    public SoundWave(double freq, double phase, double amplitude, double duration) {
        for(int i = 0; i < duration * SAMPLES_PER_SECOND; i++){
            Double nextAmp = amplitude * Math.sin(freq*2.0*Math.PI*((double) i/SAMPLES_PER_SECOND ) - phase );
            lchannel.add(nextAmp);
            rchannel.add(nextAmp);
        }
        this.samples = (int) duration * SAMPLES_PER_SECOND;
        // TODO: add or subtract phase?
    }

    /**
     * Obtain the left channel for this wave.
     * Changes to the returned array should not affect this SoundWave.
     *
     * @return an array that represents the left channel for this wave.
     */
    public double[] getLeftChannel() {
        double[] leftChannel = new double[lchannel.size()];

        for (int i = 0; i < lchannel.size(); i++) {
            leftChannel[i] = lchannel.get(i);
        }
        return leftChannel;
    }

    /**
     * Obtain the right channel for this wave.
     * Changes to the returned array should not affect this SoundWave.
     *
     * @return an array that represents the right channel for this wave.
     */
    public double[] getRightChannel() {
        double[] rightChannel = new double[rchannel.size()];

        for (int i = 0; i < rchannel.size(); i++) {
            rightChannel[i] = rchannel.get(i);
        }

        return rightChannel;
    }


    /**
     * A simple main method to play an MP3 file. Note that MP3 files should
     * be encoded to use stereo channels and not mono channels for the sound to
     * play out correctly.
     * <p>
     * One should try to get this method to work correctly at the start.
     * </p>
     *
     * @param args are currently ignored but you could be creative.
     */
    public static void main(String[] args) {
        StdPlayer.open("mp3/anger.mp3");
        SoundWave sw = new SoundWave();
        while (!StdPlayer.isEmpty()) {
            double[] lchannel = StdPlayer.getLeftChannel();
            double[] rchannel = StdPlayer.getRightChannel();
            sw.append(lchannel, rchannel);
        }
        sw.sendToStereoSpeaker();
        StdPlayer.close();
    }

    /**
     * Append a wave to this wave.
     *
     * @param lchannel
     * @param rchannel
     */
    public void append(double[] lchannel, double[] rchannel) {
        for (int i = 0; i < lchannel.length; i++) {
            this.lchannel.add(lchannel[i]);
            this.rchannel.add(rchannel[i]);
        }
        updateSamples();
        return;
    }

    /**
     * Updates the field samples
     *
     */
    public void updateSamples() {
        this.samples = this.lchannel.size();
    }

    /**
     * Append a wave to this wave.
     *
     * @param other the wave to append.
     */
    public void append(SoundWave other) {
        this.append(other.getLeftChannel(),other.getRightChannel());
    }

    /**
     * Create a new wave by adding another wave to this wave.
     * If one wave is longer than the other, treat the shorter
     * wave as having zero values for the remainder of the wave
     *
     * @param other the wave to append.
     * @return a new soundwave with the amplitudes of both this wave
     * and the other wave added
     */
    public SoundWave add(SoundWave other) {
        if (this.samples > other.samples) {
            return addWaves(this, other);
        }
        else {
            return addWaves(other,this);
        }
    }

    /**
     * Superimpose two waves, if one wave is longer than the
     * other, treat the shorter wave as zeros
     *
     * @param longerWave the longer of the two waves,
     *                   must be longer than shorterWave
     * @param shorterWave the shorter of the two waves.
     *
     * @return a new soundwave with the amplitudes of both this wave
     * and the other wave added
     */
    private SoundWave addWaves (SoundWave longerWave, SoundWave shorterWave) {
        double[] shorterLChannel = shorterWave.getLeftChannel();
        double[] shorterRChannel = shorterWave.getRightChannel();

        double[] addedLChannel = longerWave.getLeftChannel();
        double[] addedRChannel = longerWave.getRightChannel();

        for (int i = 0; i < shorterWave.samples; i++) {
            addedLChannel[i] = addedLChannel[i] + shorterLChannel[i];
            addedRChannel[i] = addedRChannel[i] + shorterRChannel[i];
        }

        return new SoundWave(addedLChannel, addedRChannel);
    }

    /**
     * Create a new wave by adding an echo to this wave.
     *
     * @param delta > 0. delta is the lag between this wave and the echo wave.
     * @param alpha is the damping factor applied to the echo wave, 0 < alpha <= 1.
     * @return a new sound wave with an echo.
     */
    public SoundWave addEcho(int delta, double alpha) {
        double[] zeros = new double[delta];
        SoundWave echo = new SoundWave(zeros,zeros);
        echo.append(this);
        echo.scaleNoClip(alpha);

        SoundWave newWave = this.add(echo);

        return newWave;
    }

    /**
     * Scale the amplitude of this wave by a scaling factor.
     * After scaling, the amplitude values are clipped to remain
     * between -1 and +1.
     *
     * @param scalingFactor is a value > 0.
     */
    public void scale(double scalingFactor) {
        double nextLeft;
        double nextRight;
        for (int i = 0; i < this.samples; i++) {
            nextLeft = this.lchannel.get(i) * scalingFactor;
            nextRight = this.rchannel.get(i) * scalingFactor;

            this.lchannel.set(i, clipVal(nextLeft));
            this.rchannel.set(i, clipVal(nextRight));
        }
    }

    /**
     * Clips values to remain between -1 and +1.
     *
     * @param val is any double.
     *
     * @return a value between -1 and 1
     */
    public double clipVal(double val) {
        if ( val > 1.0) {
            return 1.0;
        } else if (val < -1.0) {
            return -1.0;
        }
        else {
            return val;
        }
    }

    /**
     * Scale the amplitude of this wave by a scaling factor.
     * Does not clip values
     *
     * @param scalingFactor is a value > 0.
     */
    public void scaleNoClip (double scalingFactor) {
        for (int i = 0; i < this.samples; i++) {
            this.lchannel.set(i, this.lchannel.get(i) * scalingFactor);
            this.rchannel.set(i, this.rchannel.get(i) * scalingFactor);
        }
    }

    /**
     * Return a new sound wave that is obtained by applying a high-pass filter to
     * this wave.
     *
     * @param dt >= 0. dt is the time interval used in the filtering process.
     * @param RC > 0. RC is the time constant for the high-pass filter.
     * @return
     */
    public SoundWave highPassFilter(int dt, double RC) {
        // TODO: Implement this
        return null; // change this
    }

    /**
     * Return the frequency of the component with the greatest amplitude
     * contribution to this wave. This component is obtained by applying the
     * Discrete Fourier Transform to this wave.
     *
     * @return the frequency of the wave component of highest amplitude.
     * If more than one frequency has the same amplitude contribution then
     * return the higher frequency.
     */
    public double highAmplitudeFreqComponent() {
        // TODO: Implement this method
        return -1; // change this
    }

    /**
     * Determine if this wave fully contains the other sound wave as a pattern.
     *
     * @param other is the wave to search for in this wave.
     * @return true if the other wave is contained in this after amplitude scaling,
     * and false if the other wave is not contained in this with any
     * possible amplitude scaling.
     */
    public boolean contains(SoundWave other) {
        // TODO: Implement this method
        return true; // change this
    }

    /**
     * Determine the similarity between this wave and another wave.
     * The similarity metric, gamma, is the sum of squares of
     * instantaneous differences.
     *
     * @param other is not null.
     * @return the similarity between this wave and other.
     */
    public double similarity(SoundWave other) {
        // TODO: Implement this method
        return -1;
    }

    /**
     * Play this wave on the standard stereo device.
     */
    public void sendToStereoSpeaker() {
        // You may not need to change this...
        double[] lchannel = this.lchannel.stream().mapToDouble(x -> x.doubleValue()).toArray();
        double[] rchannel = this.rchannel.stream().mapToDouble(x -> x.doubleValue()).toArray();
        StdPlayer.playWave(lchannel, rchannel);
    }

}
