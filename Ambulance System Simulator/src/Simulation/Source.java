package Simulation;

import static Simulation.RandomNumberGenerator.*;

/**
 * A source of products
 * This class implements CProcess so that it can execute events.
 * By continuously creating new events, the source keeps busy.
 * 
 * @author Joel Karel
 * @version %I%, %G%
 */
public class Source implements CProcess {
	/** Eventlist that will be requested to construct events */
	private CEventList list;
	/** Queue that buffers products for the machine */
	private ProductAcceptor queue;
	/** Name of the source */
	private String name;
	/** Mean interarrival time */
	private double meanArrTime;
	/** Interarrival times (in case pre-specified) */
	private double[] interarrivalTimes;
	/** Interarrival time iterator */
	private int interArrCnt;
	/** Patient priority the source creates */
	private byte priorityLevel;

	/**
	 * Constructor, creates objects
	 * Interarrival times are exponentially distributed with mean 33
	 * 
	 * @param q The receiver of the products
	 * @param l The eventlist that is requested to construct events
	 * @param n Name of object
	 */
	public Source(ProductAcceptor q, CEventList l, String n, byte pl) {
		list = l;
		queue = q;
		name = n;
		meanArrTime = 33;
		priorityLevel = pl;
		list.add(this, 0, RandomNumberGenerator.randomPoisson(0)); // target,type,time
	}

	/**
	 * Constructor, creates objects
	 * Interarrival times are exponentially distributed with specified mean
	 * 
	 * @param q The receiver of the products
	 * @param l The eventlist that is requested to construct events
	 * @param n Name of object
	 * @param m Mean arrival time
	 */
	public Source(ProductAcceptor q, CEventList l, String n, double m) {
		list = l;
		queue = q;
		name = n;
		meanArrTime = m;
		// put first event in list for initialization
		list.add(this, 0, drawRandomExponential(meanArrTime)); // target,type,time
	}

	/**
	 * Constructor, creates objects
	 * Interarrival times are prespecified
	 * 
	 * @param q  The receiver of the products
	 * @param l  The eventlist that is requested to construct events
	 * @param n  Name of object
	 * @param ia interarrival times
	 */
	public Source(ProductAcceptor q, CEventList l, String n, double[] ia) {
		list = l;
		queue = q;
		name = n;
		meanArrTime = -1;
		interarrivalTimes = ia;
		interArrCnt = 0;
		// put first event in list for initialization
		list.add(this, 0, interarrivalTimes[0]); // target,type,time
	}

	@Override
	public void execute(int type, double tme) {
		// show arrival
		//TODO: System.out.println("Patient " + priorityLevel + " at time = " + tme);

		// create a random point in the city
		Point randomPoint = new Point();
		double[] randomPointUpdated = new double[] { randomPoint.getX(), randomPoint.getY() };
		// give arrived product to queue
		Patient p = new Patient(randomPointUpdated, priorityLevel);
		p.stamp(tme, "Creation", name);

		queue.giveProduct(p);

		// generate duration until next arrival
		if (meanArrTime > 0) {
			double duration = RandomNumberGenerator.randomPoisson(tme);
			// Create a new event in the eventlist
			list.add(this, 0, tme + duration); // target,type,time
		} else {
			interArrCnt++;
			if (interarrivalTimes.length > interArrCnt) {
				list.add(this, 0, tme + interarrivalTimes[interArrCnt]); // target,type,time
			} else {
				list.stop();
			}
		}
	}
}