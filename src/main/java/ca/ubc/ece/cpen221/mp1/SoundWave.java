package ca.ubc.ece.cpen221.mp1;

import ca.ubc.ece.cpen221.mp1.utils.HasSimilarity;
import javazoom.jl.player.StdPlayer;

import java.util.*;

public class SoundWave implements HasSimilarity<SoundWave> {

    // We are going to treat the number of samples per second of a sound wave
    // as a constant.
    // The best way to refer to this constant is as
    // SoundWave.SAMPLES_PER_SECOND.
    public static final int SAMPLES_PER_SECOND = 44100;

    // some representation fields that you could use
    private ArrayList<Double> lchannel = new ArrayList<>();
    private ArrayList<Double> rchannel = new ArrayList<>();
    private int samples;

    /**
     * Create a new SoundWave using the provided left and right channel
     * amplitude values. After the SoundWave is created, changes to the
     * provided arguments will not affect the SoundWave.
     *
     * The constructor will clip values that are not between -1 and 1.
     * Anything less than -1 will be set to -1, anything greater than 1
     * will be set to 1.
     *
     * The constructor will also add zeros to a channel if it is
     * shorter than the other one so they both are the same length.
     *
     * @param lchannel is not null and represents the left channel.
     * @param rchannel is not null and represents the right channel.
     */
    public SoundWave(double[] lchannel, double[] rchannel) {
        lchannel = clipVals(lchannel);
        rchannel = clipVals(rchannel);

        for (double sample : lchannel) {
            this.lchannel.add(sample);
        }
        for (double sample : rchannel) {
            this.rchannel.add(sample);
        }

        makeEqualLength();
        updateSamples();
    }

    /**
     * Creates an empty soundwave
     */
    public SoundWave() {
        updateSamples();
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
        for (int i = 0; i < duration * SAMPLES_PER_SECOND; i++) {
            Double nextAmp = amplitude * Math.sin(freq * 2.0 * Math.PI * ((double) i / SAMPLES_PER_SECOND) - phase);
            lchannel.add(nextAmp);
            rchannel.add(nextAmp);
        }
        this.samples = (int) (duration * SAMPLES_PER_SECOND);

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
        StdPlayer.open("mp3/energy.mp3");
        SoundWave sw = new SoundWave();
        while (!StdPlayer.isEmpty()) {
            double[] lchannel = StdPlayer.getLeftChannel();
            double[] rchannel = StdPlayer.getRightChannel();
            sw.append(lchannel, rchannel);
        }
        sw = sw.highPassFilter(10, 2);
        sw.sendToStereoSpeaker();
        StdPlayer.close();
    }

    /**
     * Append a wave to this wave. Values not between
     * -1 and 1 are clipped.
     *
     * @param lchannel left channel to append
     * @param rchannel right channel to append
     */
    public void append(double[] lchannel, double[] rchannel) {
        lchannel = clipVals(lchannel);
        rchannel = clipVals(rchannel);

        for (int i = 0; i < lchannel.length; i++) {
            this.lchannel.add(lchannel[i]);
            this.rchannel.add(rchannel[i]);
        }
        makeEqualLength();
        updateSamples();
    }

    /**
     * Updates the field samples
     *
     */
    private void updateSamples() {
        this.samples = this.lchannel.size();
    }

    /**
     * Adds extra 0's to the end of a channel if it
     * is shorter than the other one
     *
     * Modifies lchannel and rchannel
     */
    private void makeEqualLength() {
        while (lchannel.size() < rchannel.size()) {
            lchannel.add(0.0);
        }

        while (rchannel.size() < lchannel.size()) {
            rchannel.add(0.0);
        }
    }


    /**
     * Append a wave to this wave.
     *
     * @param other the wave to append.
     */
    public void append(SoundWave other) {
        this.append(other.getLeftChannel(), other.getRightChannel());
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
        } else {
            return addWaves(other, this);
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
    private SoundWave addWaves(SoundWave longerWave, SoundWave shorterWave) {
        double[] shorterLChannel = shorterWave.getLeftChannel();
        double[] shorterRChannel = shorterWave.getRightChannel();

        double[] addedLChannel = longerWave.getLeftChannel();
        double[] addedRChannel = longerWave.getRightChannel();

        for (int i = 0; i < shorterWave.samples; i++) {
            addedLChannel[i] = addedLChannel[i] + shorterLChannel[i];
            addedRChannel[i] = addedRChannel[i] + shorterRChannel[i];
        }

        addedLChannel = clipVals(addedLChannel);
        addedRChannel = clipVals(addedRChannel);

        return new SoundWave(addedLChannel, addedRChannel);
    }

    /**
     * Create a new wave by adding an echo to this wave. Will clip values
     * above 1 and below -1.
     *
     * @param delta > 0. delta is the lag between this wave and the echo wave.
     * @param alpha is the damping factor applied to the echo wave, 0 < alpha <= 1.
     * @return a new sound wave with an echo.
     */
    public SoundWave addEcho(int delta, double alpha) {
        double[] zeros = new double[delta];
        SoundWave echo = new SoundWave(zeros, zeros);
        echo.append(this);
        echo.scale(alpha);

        return this.add(echo);
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
    private double clipVal(double val) {
        if (val > 1.0) {
            return 1.0;
        } else if (val < -1.0) {
            return -1.0;
        } else {
            return val;
        }
    }

    /**
     * Clips all the values to make sure they fall between -1 and 1
     *
     * @param initVals is the array of numbers to be clipped.
     * @return an array of numbers with all values clipped
     */
    private double[] clipVals(double[] initVals) {
        double[] newVals = new double[initVals.length];
        for (int i = 0; i < initVals.length; i++) {
            newVals[i] = clipVal(initVals[i]);
        }

        return newVals;
    }

    /**
     * Return a new sound wave that is obtained by applying a high-pass filter to
     * this wave.
     *
     * @param dt >= 0. dt is the time interval used in the filtering process.
     * @param RC > 0. RC is the time constant for the high-pass filter.
     * @return a new soundwave that is obtained by applying a high pass filter
     */
    public SoundWave highPassFilter(int dt, double RC) {
        double alpha = RC / (RC + dt);
        return new SoundWave(filterChannel(getLeftChannel(), alpha), filterChannel(getRightChannel(), alpha));
    }

    /**
     * Return a new array that is obtained by applying a high-pass filter to
     * the original array.
     *
     * @param channel is the channel to be filtered.
     * @param alpha is a constant used for the high-pass filter.
     * @return a new double array that is obtained by applying a high pass filter
     * to the original array
     */
    private double[] filterChannel(double[] channel, double alpha) {
        double[] filterChannel = new double[channel.length];

        filterChannel[0] = channel[0];

        for (int i = 1; i < channel.length; i++) {
            filterChannel[i] = alpha * filterChannel[i - 1] + alpha * (channel[i] - channel[i - 1]);
        }

        return filterChannel;
    }

    /**
     * Return the frequency of the component with the greatest amplitude
     * contribution to this wave. This component is obtained by applying the
     * Discrete Fourier Transform to this wave.
     *
     * If the combined channels of the wave is all zeros, return frequency
     * of 0.0.
     * Precondition: lchannel and rchannel are the same length.
     *
     * @return the frequency of the wave component of highest amplitude.
     * If more than one frequency has the same amplitude contribution then
     * return the higher frequency.
     */
    public double highAmplitudeFreqComponent() {
        double [] combined = new double[this.samples];
        double maxFreq = 0;
        double maxCoefficient = 0;
        boolean allZeros = true;


        for (int i = 0; i < this.samples; i++) {
            combined[i] = lchannel.get(i) / 2.0 + rchannel.get(i) / 2.0;

            if (combined[i] != 0) {
                allZeros = false;
            }
        }

        if (allZeros) {
            return 0.0;
        }

        Map<Integer, Double> coefficients = DFT(combined);

        for (Integer nextFreq: coefficients.keySet()) {
            if (coefficients.get(nextFreq) - maxCoefficient > -0.001) {
                maxCoefficient = coefficients.get(nextFreq);
                maxFreq = nextFreq;
            }
        }

        return maxFreq;
    }


    /**
     * Applies a discrete Fourier transform to the wave, then returns a map
     * containing the xk values (coefficient of Fourier transform) for each frequency.
     *
     * The range of frequencies tested goes from 0Hz to the number of samples in the wave.
     *
     * @param wave is an array representing the combined channels of a
     *             soundwave. Wave is not all zeros.
     * @return a map containing the xk values for each frequency after applying
     * a discrete Fourier transform to the wave.
     */
    private Map<Integer, Double> DFT(double[] wave) {
        int minFreq = 0;
        int maxFreq = wave.length;
        Map<Integer, Double> coefficients = new HashMap<Integer, Double>();

        for (int freq = minFreq; freq <= maxFreq; freq++) {
            coefficients.put(freq, getCoefficient(freq, wave));
        }


        return coefficients;
    }

    /**
     * Calculates the coefficient of a DFT for the given frequency,
     * which is a complex number. Returns the amplitude of this number.
     *
     * @param k is the frequency to be used in the DFT
     * @param wave is an array representing the combined channels of a
     *             soundwave. Wave is not all zeros.
     * @return the amplitude of the coefficient for the term with
     * given frequency for the DFT on the wave.
     */
    private double getCoefficient(double k, double[] wave) {
        int N = wave.length;
        ComplexNumber sum = new ComplexNumber();
        ComplexNumber nextSum;
        double real;
        double imaginary;

        for (int i = 0; i < N; i++) {
            double t = (double) i / (double) SAMPLES_PER_SECOND;
            real = wave[i] * Math.cos(2.0 * Math.PI * k * t);
            imaginary = wave[i] * Math.sin(2.0 * Math.PI * k * t) * (-1.0);

            nextSum = new ComplexNumber(real, imaginary);
            sum.add(nextSum);
        }

        return sum.getAmplitude();
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

        if (other.samples > this.samples) {
            return false;
        }

        double scalingFactor;

        for (int i = 0; i <= this.samples - other.samples; i++) {

            scalingFactor = this.lchannel.get(i) / other.lchannel.get(i);

            if (containsChannelFromIndex(i, this.lchannel, other.lchannel, scalingFactor)) {
                if (containsChannelFromIndex(i, this.rchannel, other.rchannel, scalingFactor)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Determine if one channel of a wave fully contains another
     * channel of a wave starting from the passed index
     *
     * @param startingIndex the index to check as the start of the contained wave.
     *                      Must be a valid index of channel, and must be less than
     *                      the length of channel minus the length of containedSample.
     * @param channel the channel to be searched
     * @param containedSample the sample to be searched for within the other channel
     * @return true if containedSample occurs fully in channel
     * after amplitude scaling and starts at the given index, and false if the
     * contained channel does not start at this index or is not contained
     * with any possible scaling
     */
    private static Boolean containsChannelFromIndex(int startingIndex, ArrayList<Double> channel, ArrayList<Double> containedSample, double scalingFactor) {

        double floatingPointError = 0.0001;

        for (int i = 0; i < containedSample.size(); i++) {
            if ((scalingFactor - channel.get(i + startingIndex) / containedSample.get(i)) > floatingPointError) {
                return false;
            }
        }

        return true;
    }

    /**
     * Determine the similarity between this wave and another wave.
     * The similarity metric, gamma, is the inverse of one plus the sum of squares of
     * instantaneous differences, with the other channel multiplied by a scaling factor to find the best fit.
     *
     * This method returns the average gamma between the case where the first and second wave is scaled
     *
     * @param other is not null.
     * @return the symmetric similarity between this wave and other between 0 and 1.
     */
    public double similarity(SoundWave other) {
        return (asymmetricSimilarity(this, other) + asymmetricSimilarity(other, this)) / 2;
    }

    /**
     * Determine the ordered similarity between this wave and another wave.
     * The similarity metric, gamma, is the inverse of one plus the sum of squares of
     * instantaneous differences, with the other channel multiplied by a scaling factor to find the best fit.
     *
     * @param primary is not null.
     * @param secondary is not null. The wave to be multiplied by the scaling factor
     * @return gamma, the similarity between this wave and other between 0 and 1.
     */
    public static double asymmetricSimilarity(SoundWave primary, SoundWave secondary){

        double sum = minimizeSum(primary, secondary);

        return 1 / (1 + sum);

    }

    /**
     * Find the minimal positive residual sum of squares with the secondary term multiplied
     * by a positive or zero scaling factor, betaMin
     *
     * This is accomplished by simplifying the scaled residual sum of squares equation into
     * a quadratic equation ax^2 + bx + c with a always greater than 0
     *
     * @param primary the wave being subtracted from
     * @param secondary the wave being subtracted
     * @return the minimal positive value for the sum
     */

    private static double minimizeSum(SoundWave primary, SoundWave secondary) {
        double a = 0;

        for (int i = 0; i < secondary.samples; i++){
            a += Math.pow(secondary.lchannel.get(i), 2) + (Math.pow(secondary.rchannel.get(i), 2));
        }

        double b = 0;

        for (int i = 0; i < primary.samples && i < secondary.samples; i++) {
            b += primary.rchannel.get(i) * secondary.rchannel.get(i);
            b += primary.lchannel.get(i) * secondary.lchannel.get(i);
        }
        b *= -2;

        double c = 0;

        for (int i =0; i < primary.samples; i++){
            c += Math.pow(primary.lchannel.get(i), 2) + (Math.pow(primary.rchannel.get(i), 2));
        }

        double betaMin = -b / (2 * a);

        if (betaMin <= 0) {
            return c;
        }else{
            return a * betaMin * betaMin + b * betaMin + c;
        }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SoundWave soundWave = (SoundWave) o;
        return lchannel.equals(soundWave.lchannel) &&
                rchannel.equals(soundWave.rchannel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lchannel, rchannel);
    }
}
