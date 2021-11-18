import java.util.Arrays;
import dft.DFT;
import dft.IFFT;
import dft.Complex;

public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        testNewtonCoefficients();
        testNewtonAddSmplPt();
        testNewton();
        testSplines();
        testFFT();
    }

    private static void testNewtonCoefficients() {
        //Beispiel von ÜB3.2)
        double[] x = { 0, 1, 2 };
        double[] y = { 3, 0, 1 };
        NewtonPolynom p = new NewtonPolynom(x, y);

        System.out.println("Koeffizienten: " + Arrays.toString(p.getCoefficients()) + " sollte sein: {3, -3, 2}");
        System.out.println("Diagonale: " + Arrays.toString(p.getDividedDifferences()) + " sollte sein: {2, 1, 1}");        
        System.out.println("-------------------------------");
    }

    private static void testNewtonAddSmplPt() {
        //Beispiel von ÜB3.2)
        double[] x = { 0, 1, 2 };
        double[] y = { 3, 0, 1 };
        NewtonPolynom p = new NewtonPolynom(x, y);
        p.addSamplingPoint(1.5, 0.0);

        System.out.println("Koeffizienten: " + Arrays.toString(p.getCoefficients()) + " sollte sein: {3, -3, 2, 0}");
        System.out.println("Diagonale: " + Arrays.toString(p.getDividedDifferences()) + " sollte sein: {0, 2, 2, 0}");        
        System.out.println("-------------------------------");
    }

    private static void testNewton() {

        double[] x = { -1, 1, 3 };
        double[] y = { -3, 1, -3 };
        NewtonPolynom p = new NewtonPolynom(x, y);

        System.out.println(p.evaluate(0) + " sollte sein: 0.0");
        System.out.println("-------------------------------");
    }

    public static void testSplines() {
        CubicSpline spl = new CubicSpline();
        double[] y = { 2, 0, 2, 3 };
        spl.init(-1, 2, 3, y);
        spl.setBoundaryConditions(9, 0);
        System.out.println(Arrays.toString(spl.getDerivatives())
                + " sollte sein: [9.0, -3.0, 3.0, 0.0].");
    }

    public static void testFFT() {
        System.out.println("Teste Fast Fourier Transformation");

        double[] v = new double[4];
        for (int i = 0; i < 4; i++)
            v[i] = i + 1;
        Complex[] c = dft.DFT.dft(v);
        Complex[] v2 = dft.IFFT.ifft(c);

        for (int i = 0; i < 4; i++) {
            System.out.println(v2[i]);
        }
        System.out
                .println("Richtig waeren gerundet: Eigene Beispiele ueberlegen");

        System.out.println("*************************************\n");
    }
}
