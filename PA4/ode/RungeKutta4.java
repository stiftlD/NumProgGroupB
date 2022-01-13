package ode;

/**
 * Der klassische Runge-Kutta der Ordnung 4
 *
 * @author braeckle
 *
 */
public class RungeKutta4 implements Einschrittverfahren {

    @Override
    /**
     * {@inheritDoc}
     * Bei der Umsetzung koennen die Methoden addVectors und multScalar benutzt werden.
     */
    public double[] nextStep(double[] y_k, double t, double delta_t, ODE ode) {
        // TODO: diese Methode ist zu implementieren
        /*
        coefficients:
        k1 = d_t * f(t, r(t))
        k2 = d_t * f(t + 0.5*d_t, r(t) + 0.5*k1)
        k3 = d_t * f(t + 0.5*d_t, r(t) + 0.5*k2)
        k4 = d_t * f(t + d_t, r(t) + k3)
        ---->
        r(t+d_t)    = r(t) + (1/6) * (k1 + 2*k2 + 2*k3 + k4)
                    = r(t) + (1/6) * (k1 + 2*(k2 + k3) + k4)
                    = r(t) + (1/6) * ((k1 + 2*(k2 + k3)) + k4)
                    = r(t) + (1/6) * ((k1 + k4) + 2*(k2 + k3))
                    = r(t) + (1/6)*(k1 + k4) + (1/3)*(k2 + k3)
        */

        double[] k1 = multScalar(ode.auswerten(t, y_k), delta_t);
        double[] k2 = multScalar(ode.auswerten(t + delta_t/2, addVectors(y_k, multScalar(k1, 0.5))), delta_t);
        double[] k3 = multScalar(ode.auswerten(t + delta_t/2, addVectors(y_k, multScalar(k2, 0.5))), delta_t);
        double[] k4 = multScalar(ode.auswerten(t + delta_t, addVectors(y_k, k3)), delta_t);

        return addVectors(y_k, addVectors(multScalar(addVectors(k1, k4),(1/6.0)), multScalar(addVectors(k2, k3),(1/3.0))));

        //return addVectors(y_k, multScalar(addVectors(addVectors(k1, k4), multScalar(addVectors(k2, k3), 2)), (1/6.0)));

        //return addVectors(y_k, multScalar(addVectors(addVectors(k1, multScalar(addVectors(k2, k3), 2)), k4), (1/6.0)));

        //return addVectors(y_k, multScalar((addVectors(addVectors(addVectors(k1, multScalar(k2, 2)), multScalar(k3, 2)), k4)), (1/6)));
    }

    /**
     * addiert die zwei Vektoren a und b
     */
    private double[] addVectors(double[] a, double[] b) {
        double[] erg = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            erg[i] = a[i] + b[i];
        }
        return erg;
    }

    /**
     * multipliziert den Skalar scalar auf den Vektor a
     */
    private double[] multScalar(double[] a, double scalar) {
        double[] erg = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            erg[i] = scalar * a[i];
        }
        return erg;
    }

}
