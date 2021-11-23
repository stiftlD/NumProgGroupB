package dft;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Schnelle inverse Fourier-Transformation
 *
 * @author Sebastian Rettenberger
 */
public class IFFT {
    /**
     * Schnelle inverse Fourier-Transformation (IFFT).
     *
     * Die Funktion nimmt an, dass die Laenge des Arrays c immer eine
     * Zweierpotenz ist. Es gilt also: c.length == 2^m fuer ein beliebiges m.
     */
    public static Complex[] ifft(Complex[] c) {
        // TODO: diese Methode ist zu implementieren
        
        int n = c.length;
        Complex[] v = new Complex[n];

        if (n==1){
            return new Complex[] {c[0]};
        }
        else{
            int m = n / 2; //n power of 2
            //divide
            ArrayList<Complex> c1 = new ArrayList<Complex>(); //even indices of c
            ArrayList<Complex> c2 = new ArrayList<Complex>(); //odd indices of c
            for(int i = 0; i < n; i++){
                if (i % 2 == 0) c1.add(c[i]);
                else c2.add(c[i]);               
            }

            Complex[] z1 = ifft((Complex[]) c1.toArray());
            Complex[] z2 = ifft((Complex[]) c2.toArray());

            //berechne omega in Polarkoordinaten
            Complex omega = Complex.fromPolar(1, (2 * Math.PI)/n);
            
            //and conquer
            for (int j = 0; j < m; j++){
                    v[j] = z1[j].add((omega.power(j).mul(z2[j]))); // v[j] = z1[j] + omega^j * z2[j]
                    v[m+j] = z1[j].sub((omega.power(j).mul(z2[j]))); // v[m+j] = z1[j] - omega^j * z2[j]
            }

        }

        return v;
    }
}
