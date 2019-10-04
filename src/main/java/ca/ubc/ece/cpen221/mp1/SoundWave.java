package ca.ubc.ece.cpen221.mp1;

import ca.ubc.ece.cpen221.mp1.utils.HasSimilarity;
import javazoom.jl.player.StdPlayer;

import java.math.BigInteger;
import java.util.ArrayList;

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
            Double nextAmp = amplitude * Math.sin(freq * 2.0 * Math.PI * ((double) i / SAMPLES_PER_SECOND ) - phase );
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
        StdPlayer.open("mp3/energy.mp3");
        SoundWave sw = new SoundWave();
        while (!StdPlayer.isEmpty()) {
            double[] lchannel = StdPlayer.getLeftChannel();
            double[] rchannel = StdPlayer.getRightChannel();
            sw.append(lchannel, rchannel);
        }
        sw = sw.highPassFilter(10,2);
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
    private double clipVal(double val) {
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
    private void scaleNoClip (double scalingFactor) {
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
     * @return a new soundwave that is obtained by applying a high pass filter
     */
    public SoundWave highPassFilter(int dt, double RC) {
        double alpha = RC / (RC + dt);
        SoundWave filtered = new SoundWave(filterChannel(getLeftChannel(), alpha), filterChannel(getRightChannel(), alpha));

        return filtered;
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
    private double[] filterChannel (double[] channel, double alpha){
        double[] filterChannel = new double[channel.length];

        filterChannel[0] = channel[0];

        for (int i = 1; i < channel.length; i++) {
            filterChannel[i] = alpha * filterChannel[i-1] + alpha * (channel[i] - channel[i-1]);
        }

        return filterChannel;
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

        if (other.samples > this.samples){
            return false;
        }

        double scalingFactor;

        for (int i = 0; i <= this.samples - other.samples; i++){

            scalingFactor = this.lchannel.get(i)/other.lchannel.get(i);

            if(containsChannelFromIndex(i, this.lchannel, other.lchannel, scalingFactor)){
                if(containsChannelFromIndex(i, this.rchannel, other.rchannel, scalingFactor)){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Determine if one channel of a wave fully contains another channel of a wave starting from the passed index
     *
     * @param startingIndex the index to check as the start of the contained wave. Must be a valid index of channel,
     *                      and must be less than the length of channel minus the length of containedSample.
     * @param channel the channel to be searched
     * @param containedSample the sample to be searched for within the other channel
     * @return true if containedSample occurs fully in channel after amplitude scaling and starts
     * at the given index, and false if the contained channel does not start at this index or is not contained
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
     * algorithm maximizing proof: https://math.stackexchange.com/questions/1352726/minimizing-a-function-sum-of-squares
     *
     * @param other is not null.
     * @return the similarity between this wave and other.
     */
    public double similarity(SoundWave other) {

        if (other.samples > this.samples){
            return other.similarity(this);
        }

        double beta = Math.abs(this.average()/other.average());
        double residualSumOfSquares = 0;

        residualSumOfSquares += getResidualSumOfSquares(this.lchannel, other.lchannel, beta);
        residualSumOfSquares += getResidualSumOfSquares(this.rchannel, other.rchannel, beta);

    return 1 / (1 + residualSumOfSquares);

    }

    /**
     * Calculates the residual sum of squares of values in two ArrayLists of doubles as the sum across all values of
     * the value in channelOne minus beta multiplied by the value in channel two, all squared.
     * @param channelOne the channel of "baseline values" to be subtracted from
     * @param channelTwo the channel of secondary values, to be be subtracted. Must be shorter than or of equal length
     *                   to channelOne
     * @param beta a positive coefficient to scale channel two by
     * @return the resultant similarity between zero and one
     */
    private static double getResidualSumOfSquares(ArrayList<Double> channelOne, ArrayList<Double> channelTwo, double beta){

        double residualSumOfSquares=0;
        int i;

        for(i = 0; i < channelTwo.size(); i++){
            residualSumOfSquares += Math.pow(channelOne.get(i) - beta * channelTwo.get(i), 2);
        }
        for(;i < channelOne.size(); i++){
            residualSumOfSquares += Math.pow(channelOne.get(i), 2);
        }

        return residualSumOfSquares;
    }

    /**
     * computes the mean value of all samples from both channels
     * @return that average
     */
    private double average(){
        double sum = 0;

        for( double sample:lchannel){
            sum += sample;
        }

        for( double sample:rchannel){
            sum += sample;
        }

        return sum/this.samples;
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
