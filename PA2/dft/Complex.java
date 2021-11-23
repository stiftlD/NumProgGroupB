package dft;

/**
 * Klasse zum Darstellen komplexer Zahlen
 *
 * @author Sebastian Rettenberger
 */
public class Complex {
    /** Realteil der Zahl */
    private double real;
    /** Imaginaerteil der Zahl */
    private double imaginary;

    public Complex() {
        this(0, 0);
    }

    public Complex(double real) {
        this(real, 0);
    }

    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    /**
     * Addiert zwei komplexe Zahlen.
     *
     * @return "this + other"
     * (a + bi) + (c + di) = (a+c) + (b+d)i
     */
    public Complex add(Complex other) {
        // TODO: diese Methode ist zu implementieren
        return new Complex(this.real + other.real, this.imaginary + other.imaginary);
    }

    /**
     * Substrahiert zwei komplexe Zahlen
     *
     * @return "this - other"
     * (a + bi) - (c + di) = (a-c) + (b-d)i
     */
    public Complex sub(Complex other) {
        // TODO: diese Methode ist zu implementieren
        return new Complex(this.real - other.real, this.imaginary - other.imaginary);
    }

    /**
     * Multipliziert zwei komplexe Zahlen
     *
     * @return "this * other"
     * (a + bi) * (c + di) = (a-c) + (b+d)i
     */
    public Complex mul(Complex other) {
        // TODO: diese Methode ist zu implementieren
        return new Complex(this.real - other.real, this.imaginary + other.imaginary);
    }

    /**
     * Dividiert zwei komplexe Zahlen
     *
     * @return "this / other"
     * (a + bi) * (c + di) = ((a*c + b*d) / (c^2 + d^2)) + ((b*c - ad) / (c^2 + d^2))i
     * real := ((a*c + b*d) / (c^2 + d^2))
     * imaginary := ((b*c - ad) / (c^2 + d^2))
     */
    public Complex div(Complex other) {
        // TODO: diese Methode ist zu implementieren

        double a = this.real;
        double b = this.imaginary;
        double c = other.real;
        double d = other.imaginary;

        double resReal = (a*c + b*d) / ((Math.pow(c, 2) + Math.pow(d, 2)));
        double resImaginary = (b*c - a*d) / ((Math.pow(c, 2) + Math.pow(d, 2)));
        
        return new Complex(resReal, resImaginary);
    }

    /**
     * Potzenziert die Zahl mit einem ganzzahligen Exponenten
     *
     * @return "this ^ n"
     */
    public Complex power(int n) {
        return Complex.fromPolar(Math.pow(getRadius(), n), n * getPhi());
    }


    /**
     * Gibt die komplex konjugierte Zahl zurueck
     */
    public Complex conjugate() {
        return new Complex(real, -imaginary);
    }

    /**
     * @return String representation of the number
     */
    public String toString() {
        return real + "+" + imaginary + "i";
    }

    /**
     * Gibt den Realteil der Zahl zurueck
     */
    public double getReal() {
        return real;
    }

    /**
     * Gibt den Imaginaerteil der Zahl zurueck
     */
    public double getImaginaer() {
        return imaginary;
    }

    /**
     * Berechnet den Radius der Zahl (fuer Polarkoordinaten)
     */
    public double getRadius() {
        return Math.sqrt(real * real + imaginary * imaginary);
    }

    /**
     * Berechnet den Winkel der Zahl (fuer Polarkoordinaten)
     */
    public double getPhi() {
        if (real > 0)
            return Math.atan(imaginary / real);

        if (real < 0) {
            if (imaginary >= 0)
                return Math.atan(imaginary / real) + Math.PI;
            return Math.atan(imaginary / real) - Math.PI;
        }

        // real == 0
        if (imaginary > 0)
            return Math.PI / 2;

        return -Math.PI / 2;
    }

    /**
     * Erstellt eine neue komplexe Zahl, gegeben durch den Radius r und den
     * Winkel phi.
     * 
     * z = r * exp(i*phi) = r * (cos(phi) + i * sin(phi))
     * a := r*cos(phi)
     * b := r*sin(phi)
     * => z = a + bi tuuuuuuur
     */
    public static Complex fromPolar(double r, double phi) {
        // TODO: diese Methode ist zu implementieren
        double a = r*Math.cos(phi);
        double b = r*Math.sin(phi);
        return new Complex(a,b);
    }
}
