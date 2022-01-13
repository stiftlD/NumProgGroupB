package ode;
import java.util.Arrays;

/**
 * Das Einschrittverfahren von Heun
 *
 * @author braeckle
 *
 */
public class Heun implements Einschrittverfahren {

    @Override
    /**
     * {@inheritDoc}
     * Nutzen Sie dabei geschickt den Expliziten Euler.
     */
    public double[] nextStep(double[] y_k, double t, double delta_t, ODE ode) {
        // TODO: diese Methode ist zu implementieren
        //first step: _r(t+d_t) = r(t) + d_t*f(t, r(t))
        //second step: r(t+d_t) = r(t) + (1/2) * d_t*(f(t,r(t)) + f(t+d_t, _r(t+d_t)))


        //1. step
        double[] _r = y_k.clone();

        double[] f1 = ode.auswerten(t, y_k);

        for (int i = 0; i < _r.length; i++){
            _r[i] += delta_t*f1[i];
        }

        //2. step
        double[] result = y_k.clone();
        double[] f2 = ode.auswerten(t + delta_t, _r);

        for (int i = 0; i < result.length; i++){
            result[i] += (delta_t / 2) * (f1[i] + f2[i]);
        }

        return result;
    }

}
