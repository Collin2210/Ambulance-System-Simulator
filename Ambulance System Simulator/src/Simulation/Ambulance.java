package Simulation;

import static Simulation.RandomNumberGenerator.drawRandomExponential;

/**
 *	Ambulance in a factory
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class Ambulance implements CProcess,ProductAcceptor
{
	/** Patient that is being handled  */
	private Patient patient;
	/** Eventlist that will manage events */
	private final CEventList eventlist;
	/** Queue from which the machine has to take products */
	private Queue queue;
	/** Sink to dump products */
	private ProductAcceptor sink;
	/** Status of the machine (b=busy, i=idle) */
	private char status;
	/** Ambulance name */
	private final String name;
	/** Mean processing time */
	private double meanProcTime;
	/** Processing times (in case pre-specified) */
	private double[] processingTimes;
	/** Processing time iterator */
	private int procCnt;
	/** Position of waiting dock */
	private double[] waitingDockPosition;
	/** Ambulance current position */
	private double[] currentPosition;

	/**
	*	Constructor
	*        Service times are exponentially distributed with mean 30
	*	@param q	Queue from which the machine has to take products
	*	@param s	Where to send the completed products
	*	@param e	Eventlist that will manage events
	*	@param n	The name of the machine
	*/
	public Ambulance(Queue q, ProductAcceptor s, CEventList e, String n)
	{
		status='i';
		queue=q;
		sink=s;
		eventlist=e;
		name=n;
		meanProcTime=30;
		queue.askProduct(this);
	}

	/**
	*	Constructor
	*        Service times are exponentially distributed with specified mean
	*	@param q	Queue from which the machine has to take products
	*	@param s	Where to send the completed products
	*	@param e	Eventlist that will manage events
	*	@param n	The name of the machine
	*        @param m	Mean processing time
	*/
	public Ambulance(Queue q, ProductAcceptor s, CEventList e, String n, double m)
	{
		status='i';
		queue=q;
		sink=s;
		eventlist=e;
		name=n;
		meanProcTime=m;
		queue.askProduct(this);
	}
	
	/**
	*	Constructor
	*        Service times are pre-specified
	*	@param q	Queue from which the machine has to take products
	*	@param s	Where to send the completed products
	*	@param e	Eventlist that will manage events
	*	@param n	The name of the machine
	*        @param st	service times
	*/
	public Ambulance(Queue q, ProductAcceptor s, CEventList e, String n, double[] st)
	{
		status='i';
		queue=q;
		sink=s;
		eventlist=e;
		name=n;
		meanProcTime=-1;
		processingTimes=st;
		procCnt=0;
		queue.askProduct(this);
	}

	/**
	*	Method to have this object execute an event
	*	@param type	The type of the event that has to be executed
	*	@param tme	The current time
	*/
	public void execute(int type, double tme)
	{
		// show arrival
		System.out.println("Patient " + patient.getPriorityLevel() + " finished at time = " + tme);

		// Remove patient from system
		patient.stamp(tme,"Production complete",name);
		sink.giveProduct(patient);
		patient =null;
		// set machine status to idle
		status='i';
		// Ask the queue for products
		queue.askProduct(this);
	}
	
	/**
	*	Let the machine accept a patient and let it start handling it
	*	@param p	The patient that is offered
	*	@return	true if the patient is accepted and started, false in all other cases
	*/
        @Override
	public boolean giveProduct(Patient p)
	{
		// Only accept something if the machine is idle
		if(status=='i')
		{
			// accept the patient
			patient = p;
			// mark starting time
			patient.stamp(eventlist.getTime(),"Production started",name);
			// start production
			startProduction();
			// Flag that the patient has arrived
			return true;
		}
		// Flag that the patient has been rejected
		else return false;
	}
	
	/**
	*	Starting routine for the production
	*	Start the handling of the current patient with an exponentionally distributed processingtime with average 30
	*	This time is placed in the eventlist
	*/
	private void startProduction()
	{
		// generate duration
		if(meanProcTime>0)
		{
			double duration = drawRandomExponential(meanProcTime);
			// Create a new event in the eventlist
			double tme = eventlist.getTime();
			eventlist.add(this,0,tme+duration); //target,type,time
			// set status to busy
			status='b';
		}
		else
		{
			if(processingTimes.length>procCnt)
			{
				eventlist.add(this,0,eventlist.getTime()+processingTimes[procCnt]); //target,type,time
				// set status to busy
				status='b';
				procCnt++;
			}
			else
			{
				eventlist.stop();
			}
		}
	}


	public void setStatus(char status) {
		this.status = status;
	}

	// TODO: 12/11/2022 implement current position correctly such that we can calculate exactly processing time
	public double processingTime(){
		double toPatient = manhattanDistance(patient.getPickupLocation(), currentPosition);
		double timeAtScene = m_erlang();

		double[] hospitalPos = new double[] {0, 0};
		double toHospital = manhattanDistance(patient.getPickupLocation(), hospitalPos);

		return toPatient + timeAtScene + toHospital;
	}

	public static double manhattanDistance(double[] posA, double[] posB){
		return Math.abs(posA[0] - posB[0]) + Math.abs(posA[1] - posB[1]);
	}

	// TODO: 12/11/2022 implement m-erlang
	public static double m_erlang(){
		return 1.0;
	}
}