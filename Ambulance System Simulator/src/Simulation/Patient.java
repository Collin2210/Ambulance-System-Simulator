package Simulation;

import java.util.ArrayList;
/**
 *	Patient that is send trough the system
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class Patient
{
	/** Priority levels:
	 * A1 = 1,
	 * A2 = 2,
	 * B = 3
	 */
	private final byte priorityLevel;

	private double[] pickupLocation;

	/** Stamps for the products */
	private ArrayList<Double> times;
	private ArrayList<String> events;
	private ArrayList<String> stations;
	
	/** 
	*	Constructor for the product
	*	Mark the time at which it is created	*	 The current time
	*/
	public Patient(double[] pickupLocation, byte priorityLevel)
	{
		times = new ArrayList<>();
		events = new ArrayList<>();
		stations = new ArrayList<>();
		this.pickupLocation = pickupLocation;
		this.priorityLevel = priorityLevel;
	}
	
	
	public void stamp(double time,String event,String station)
	{
		times.add(time);
		events.add(event);
		stations.add(station);
	}
	
	public ArrayList<Double> getTimes()
	{
		return times;
	}

	public ArrayList<String> getEvents()
	{
		return events;
	}

	public ArrayList<String> getStations()
	{
		return stations;
	}
	
	public double[] getTimesAsArray()
	{
		times.trimToSize();
		double[] tmp = new double[times.size()];
		for (int i=0; i < times.size(); i++)
		{
			tmp[i] = times.get(i);
		}
		return tmp;
	}

	public String[] getEventsAsArray()
	{
		String[] tmp = new String[events.size()];
		tmp = events.toArray(tmp);
		return tmp;
	}

	public String[] getStationsAsArray()
	{
		String[] tmp = new String[stations.size()];
		tmp = stations.toArray(tmp);
		return tmp;
	}

	public Point getPickupLocation() {
		return new Point(pickupLocation[0], pickupLocation[1]);
	}

	public byte getPriorityLevel() {
		return priorityLevel;
	}
}
