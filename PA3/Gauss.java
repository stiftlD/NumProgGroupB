

public class Gauss {

    /**
     * Diese Methode soll die Loesung x des LGS R*x=b durch
     * Rueckwaertssubstitution ermitteln.
     * PARAMETER:
     * R: Eine obere Dreiecksmatrix der Groesse n x n
     * b: Ein Vektor der Laenge n
     */
    public static double[] backSubst(double[][] R, double[] b) {
        int n = b.length;

        double[] x = new double[n];
        double[] a;

        for (int i = n-1; i >= 0; i--){
            a = R[i];
            
            double constant = 0.0; //bereits berechnete a*x
                for (int j = i+1; j < n; j++){
                    constant += a[j] * x[j];
                }

            x[i] = (b[i] - constant) / a[i];
        }

        return x;
    }

    /**
     * Diese Methode soll die Loesung x des LGS A*x=b durch Gauss-Elimination mit
     * Spaltenpivotisierung ermitteln. A und b sollen dabei nicht veraendert werden.
     * PARAMETER: A:
     * Eine regulaere Matrix der Groesse n x n
     * b: Ein Vektor der Laenge n
     */
    public static double[] solve(double[][] A, double[] b) {
        // Clone A and b to avoid modified input parameters
        double[][] _A = new double[A.length][];
        for (int r = 0; r < A.length; r++) {
            _A[r] = A[r].clone();
        }
        double[] _b = b.clone();

        // Elimination
        for (int k = 0; k < _A.length; k++) {
            // Find row with maximum absolute value in kth column
            double maxAbsValueInKthColumn = _A[k][k];
            int indexOfMaxAbsValueInKthColumn = k;
            for (int i = k + 1; i < _A.length; i++) {
                if (Math.abs(_A[i][k]) > maxAbsValueInKthColumn) {
                    indexOfMaxAbsValueInKthColumn = i;
                    maxAbsValueInKthColumn = Math.abs(_A[i][k]);
                }
            }
            // Exchange rows if needed
            if (indexOfMaxAbsValueInKthColumn != k) {
                double tmpB = _b[k];
                _b[k] = _b[indexOfMaxAbsValueInKthColumn];
                _b[indexOfMaxAbsValueInKthColumn] = tmpB;
                double[] tmpRow = _A[k];
                _A[k] = _A[indexOfMaxAbsValueInKthColumn];
                _A[indexOfMaxAbsValueInKthColumn] = tmpRow;
            }
            // Gauß
            for (int i = k + 1; i < _A.length; i++) {
                double multiplier = _A[i][k] / _A[k][k];
                _A[i][k] = 0;
                for (int j = k+1; j < _A.length; j++) {
                    _A[i][j] -= multiplier * _A[k][j];
                }
                _b[i] -= multiplier * _b[k];
            }
        }

        // BackSubstitution
        return backSubst(_A, _b);
    }

    /**
     * Diese Methode soll eine Loesung p!=0 des LGS A*p=0 ermitteln. A ist dabei
     * eine nicht invertierbare Matrix. A soll dabei nicht veraendert werden.
     *
     * Gehen Sie dazu folgendermassen vor (vgl.Aufgabenblatt):
     * -Fuehren Sie zunaechst den Gauss-Algorithmus mit Spaltenpivotisierung
     *  solange durch, bis in einem Schritt alle moeglichen Pivotelemente
     *  numerisch gleich 0 sind (d.h. <1E-10)
     * -Betrachten Sie die bis jetzt entstandene obere Dreiecksmatrix T und
     *  loesen Sie Tx = -v durch Rueckwaertssubstitution
     * -Geben Sie den Vektor (x,1,0,...,0) zurueck
     *
     * Sollte A doch intvertierbar sein, kann immer ein Pivot-Element gefunden werden(>=1E-10).
     * In diesem Fall soll der 0-Vektor zurueckgegeben werden.
     * PARAMETER:
     * A: Eine singulaere Matrix der Groesse n x n
     */
    public static double[] solveSing(double[][] A) {
        // Clone A and b to avoid modified input parameters
        double[][] _A = new double[A.length][];
        for (int r = 0; r < A.length; r++) {
            _A[r] = A[r].clone();
        }

        // Elimination
        for (int k = 0; k < _A.length; k++) {
            // Find row with maximum absolute value in kth column
            double maxAbsValueInKthColumn = _A[k][k];
            int indexOfMaxAbsValueInKthColumn = k;
            for (int i = k + 1; i < _A.length; i++) {
                if (Math.abs(_A[i][k]) > maxAbsValueInKthColumn) {
                    indexOfMaxAbsValueInKthColumn = i;
                    maxAbsValueInKthColumn = Math.abs(_A[i][k]);
                }
            }
            // Skip if entire column is already zeroed
            if(maxAbsValueInKthColumn < Math.pow(Math.E, -10)) {
                // Create T
                double[][] T = new double[k][];
                for (int i = 0; i < k; i++) {
                    T[i] = _A[i]; // Ignore fact that rows might be too long (T[i].lenght > k)
                }
                // Create -v
                double[] v = new double[k];
                for (int i = 0; i < k; i++) {
                    v[i] = _A[i][k] * (-1);
                }
                double[] x = backSubst(T, v);
                double[] result = new double[A.length];
                for (int i = 0; i < x.length; i++) {
                    result[i] = x[i];
                }
                result[x.length] = 1;
                return result;
            }
            // Exchange rows if needed
            if (indexOfMaxAbsValueInKthColumn != k) {
                double[] tmpRow = _A[k];
                _A[k] = _A[indexOfMaxAbsValueInKthColumn];
                _A[indexOfMaxAbsValueInKthColumn] = tmpRow;
            }
            // Gauß
            for (int i = k + 1; i < _A.length; i++) {
                double multiplier = _A[i][k] / _A[k][k];
                _A[i][k] = 0;
                for (int j = k+1; j < _A.length; j++) {
                    _A[i][j] -= multiplier * _A[k][j];
                }
            }
        }

        // Return [0,...,0] if A is invertible
        return new double[A.length];
    }

    /**
     * Diese Methode berechnet das Matrix-Vektor-Produkt A*x mit A einer nxm
     * Matrix und x einem Vektor der Laenge m. Sie eignet sich zum Testen der
     * Gauss-Loesung
     */
    public static double[] matrixVectorMult(double[][] A, double[] x) {
        int n = A.length;
        int m = x.length;

        double[] y = new double[n];

        for (int i = 0; i < n; i++) {
            y[i] = 0;
            for (int j = 0; j < m; j++) {
                y[i] += A[i][j] * x[j];
            }
        }

        return y;
    }
}
