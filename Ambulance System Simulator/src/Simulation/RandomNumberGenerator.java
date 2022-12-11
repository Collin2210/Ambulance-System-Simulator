package Simulation;

import java.util.Random;

public class RandomNumberGenerator {

    /** Use the inverse transform method to generate random variates following Poisson distribution */
    public static double randomPoisson(double time){
        double lambda = getArrivalRate(time);

        double
                a = Math.exp(-lambda),
                b = 1,
                i = 0;

        while(b >= a)
            b = b * Math.random();

        return i;
    }



    public static double drawRandomExponential(double mean)
    {
        // draw a [0,1] uniform distributed number
        double u = Math.random();
        // Convert it into an exponentially distributed random variate with mean 33
        return -mean*Math.log(u);
    }


    private static double getArrivalRate(double currentTime){
        return 3 - 2 * Math.sin( 5 * (Math.PI + (int) currentTime) / 6 * Math.PI);
    }
}
