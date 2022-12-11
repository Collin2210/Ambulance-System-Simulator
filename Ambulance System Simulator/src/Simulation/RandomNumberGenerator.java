package Simulation;

public class RandomNumberGenerator {

    public static double randomPoisson(double time) {
        double lambda = getArrivalRate(time);

        double a = Math.exp(-lambda),
                b = 1,
                i = 0;

        while (b >= a) {
            b = b * Math.random();
            i++;
        }

        return i;
    }

    public static double randomErlang() {

        double product = 1,
                lambda = 1,
                m = 3;

        for (int i = 0; i < m; m++)
            product = product * Math.random();

        return (-lambda / m) * Math.log(product);
    }

    public static double drawRandomExponential(double mean) {
        // draw a [0,1] uniform distributed number
        double u = Math.random();
        // Convert it into an exponentially distributed random variate with mean 33
        return -mean * Math.log(u);
    }

    private static double getArrivalRate(double currentTime) {

        int time = (int) currentTime / 60;

        return 3 - 2 * Math.sin(5 * (Math.PI + time) / 6 * Math.PI);
    }
}
