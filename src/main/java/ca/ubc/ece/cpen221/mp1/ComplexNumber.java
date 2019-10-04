package ca.ubc.ece.cpen221.mp1;

public class ComplexNumber {
    private double real;
    private double imaginary;

    public ComplexNumber (double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public ComplexNumber() {
        this.real = 0;
        this.imaginary = 0;
    }

    public void add (ComplexNumber other) {
        this.imaginary += other.imaginary;
        this.real += other.real;
    }

    public double getAmplitude () {
        return Math.sqrt(Math.pow(this.imaginary, 2) + Math.pow(this.real, 2));
    }

    public double getReal() {
        return real;
    }
    public double getImaginary() {
        return  imaginary;
    }

}
