package ode;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Das Einschrittverfahren "Expliziter Euler"
 *
 * @author braeckle
 *
 */
public class ExpliziterEuler implements Einschrittverfahren {

    public double[] nextStep(double[] y_k, double t, double delta_t, ODE ode) {
        // TODO: diese Methode ist zu implementieren
        //r(t + d_t) = r(t) + d_t*f(t,r(t))
        //f: velocity at time t at position r(t)
        //v(t) = v(0) + (0,-g)*t

        double[] result = y_k;

        double[] f = ode.auswerten(t, y_k);
        for (int i = 0; i < f.length; i++){
            result[i] += delta_t*f[i];
        }

        return result;
    }

}
