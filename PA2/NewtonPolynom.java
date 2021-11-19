import java.util.Arrays;
import java.util.ArrayList;

/**
 * Die Klasse Newton-Polynom beschreibt die Newton-Interpolation. Die Klasse
 * bietet Methoden zur Erstellung und Auswertung eines Newton-Polynoms, welches
 * uebergebene Stuetzpunkte interpoliert.
 *
 * @author braeckle
 *
 */
public class NewtonPolynom implements InterpolationMethod {

    /** Stuetzstellen xi */
    double[] x;

    /**
     * Koeffizienten/Gewichte des Newton Polynoms p(x) = a0 + a1*(x-x0) +
     * a2*(x-x0)*(x-x1)+...
     */
    double[] a;

    /**
     * die Diagonalen des Dreiecksschemas. Diese dividierten Differenzen werden
     * fuer die Erweiterung der Stuetzstellen benoetigt.
     */
    double[] f;

    /**
     * leerer Konstruktore
     */
    public NewtonPolynom() {
    };

    /**
     * Konstruktor
     *
     * @param x
     *            Stuetzstellen
     * @param y
     *            Stuetzwerte
     */
    public NewtonPolynom(double[] x, double[] y) {
        this.init(x, y);
    }

    /**
     * {@inheritDoc} Zusaetzlich werden die Koeffizienten fuer das
     * Newton-Polynom berechnet.
     */
    @Override
    public void init(double a, double b, int n, double[] y) {
        x = new double[n + 1];
        double h = (b - a) / n;

        for (int i = 0; i < n + 1; i++) {
            x[i] = a + i * h;
        }
        computeCoefficients(y);
    }

    /**
     * Initialisierung der Newtoninterpolation mit beliebigen Stuetzstellen. Die
     * Faelle "x und y sind unterschiedlich lang" oder "eines der beiden Arrays
     * ist leer" werden nicht beachtet.
     *
     * @param x
     *            Stuetzstellen
     * @param y
     *            Stuetzwerte
     */
    public void init(double[] x, double[] y) {
        this.x = Arrays.copyOf(x, x.length);
        computeCoefficients(y);
    }

    /**
     * computeCoefficients belegt die Membervariablen a und f. Sie berechnet zu
     * uebergebenen Stuetzwerten y, mit Hilfe des Dreiecksschemas der
     * Newtoninterpolation, die Koeffizienten a_i des Newton-Polynoms. Die
     * Berechnung des Dreiecksschemas soll dabei lokal in nur einem Array der
     * Laenge n erfolgen (z.B. spaltenweise Berechnung). Am Ende steht die
     * Diagonale des Dreiecksschemas in der Membervariable f, also f[0],f[1],
     * ...,f[n] = [x0...x_n]f,[x1...x_n]f,...,[x_n]f. Diese koennen spaeter bei
     * der Erweiterung der Stuetzstellen verwendet werden.
     *
     * Es gilt immer: x und y sind gleich lang.
     */
    private void computeCoefficients(double[] y) {
        int n = y.length;
        if (a == null) a = new double[n];
        if (f == null) f = new double[n];
        double[] col = Arrays.copyOf(y, n);
        
        a[0] = col[0]; //1. Koeffizient

        //laufe spalten (ks) ab, berechne dabei von oben nach unten neu, speicher den letzten wert in Diagole f
        //1. Spalte ist Vektor y
        for (int k = 1; k < n; k++){

            for (int i = 0; i < n-k; i++){
                col[i] = (col[i+1] - col[i]) / (x[i+k] - x[i]);
            }
            
            //Letzter Wert der Spalte liegt in Diagonale
            this.f[n-k] = col[n-k];
            //a_k liegt in erster Reihe
            a[k] = col[0];
        }

        f[0] = a[n-1]; //letzter Koeffizient in Diagonale
    }

    /**
     * Gibt die Koeffizienten des Newton-Polynoms a zurueck
     */
    public double[] getCoefficients() {
        return a;
    }

    /**
     * Gibt die Dividierten Differenzen der Diagonalen des Dreiecksschemas f
     * zurueck
     */
    public double[] getDividedDifferences() {
        return f;
    }

    /**
     * addSamplintPoint fuegt einen weiteren Stuetzpunkt (x_new, y_new) zu x
     * hinzu. Daher werden die Membervariablen x, a und f vergoessert und
     * aktualisiert . Das gesamte Dreiecksschema muss dazu nicht neu aufgebaut
     * werden, da man den neuen Punkt unten anhaengen und das alte
     * Dreiecksschema erweitern kann. Fuer diese Erweiterungen ist nur die
     * Kenntnis der Stuetzstellen und der Diagonalen des Schemas, bzw. der
     * Koeffizienten noetig. Ist x_new schon als Stuetzstelle vorhanden, werden
     * die Stuetzstellen nicht erweitert.
     *
     * @param x_new
     *            neue Stuetzstelle
     * @param y_new
     *            neuer Stuetzwert
     */
    public void addSamplingPoint(double x_new, double y_new) {
        /* TODO: diese Methode ist zu implementieren */

        //Fall x_new schon vorhanden
        if (Arrays.asList(x).contains(x_new)) return;

        //füge Stützstelle hinzu
        x = Arrays.copyOf(x, x.length +1);
        x[x.length-1] = x_new;

        //neue diagonale
        f = Arrays.copyOf(f, f.length + 1);
        f[f.length-1] = y_new;

        int n = x.length;
        //baue diagonale neu auf
        for(int k = 1; k < n; k++){
            f[n-k-1] = (f[n-k] - f[n-k-1]) / (x[n - 1] - x[n - k - 1]);
        }

        //füge Koeffizient hinzu
        a = Arrays.copyOf(a, a.length +1);
        a[a.length - 1] = f[0];
    }

    /**
     * {@inheritDoc} Das Newton-Polynom soll effizient mit einer Vorgehensweise
     * aehnlich dem Horner-Schema ausgewertet werden. Es wird davon ausgegangen,
     * dass die Stuetzstellen nicht leer sind.
     */
    @Override
    public double evaluate(double z) {
        /* TODO: diese Methode ist zu implementieren */
        int n = x.length-1;
        
        //p(x) = a_0 + (x-x_0)*(a_1 + (x-x_1)* (...(a_n-1 + (x-x_n-1)*a_n)...))
        //von innen nach außen
        int product = 1;
        for(int i = n; i > 1; i--){
            product *= a[i] * (z - x[i-1]) + a[i-1];
        }
        //erste Summanden
        product *= (z - x[0]);
        product += a[0];

        return product;
    }
}
