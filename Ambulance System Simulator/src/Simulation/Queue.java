package Simulation;

import java.util.ArrayList;

/**
 *	Queue that stores products until they can be handled on a machine
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class Queue implements ProductAcceptor
{
	/** List in which the products are kept */
	private ArrayList<Patient> row;
	/** Requests from machine that will be handling the products */
	private ArrayList<Ambulance> requests;
	
	/**
	*	Initializes the queue and introduces a dummy machine
	*	the machine has to be specified later
	*/
	public Queue()
	{
		row = new ArrayList<>();
		requests = new ArrayList<>();
	}
	
	/**
	*	Asks a queue to give a product to a ambulance
	*	True is returned if a product could be delivered; false if the request is queued
	*/
	public boolean askProduct(Ambulance ambulance)
	{
		// This is only possible with a non-empty queue
		if(row.size()>0)
		{
			// get the highest priority patient
			Patient patient = getPriorityPatient();

			// If the ambulance accepts the product
			if(ambulance.giveProduct(patient))
			{
				row.remove(patient);// Remove it from the queue
				return true;
			}
			else
				return false; // Ambulance rejected; don't queue request
		}
		else
		{
			requests.add(ambulance);
			return false; // queue request
		}
	}
	
	/**
	*	Offer a product to the queue
	*	It is investigated whether a machine wants the product, otherwise it is stored
	*/
	public boolean giveProduct(Patient p)
	{
		// Check if the machine accepts it
		if(requests.size()<1)
			row.add(p); // Otherwise store it
		else
		{
			boolean delivered = false;

			// sort ambulances in machine based on distance to patient
			requests.sort(new ambulanceSorter(p.getPickupLocation()));

			// look for a machine in request list that is idle and to whom we can give the product
			while(!delivered & (requests.size()>0))
			{
				delivered=requests.get(0).giveProduct(p);
				// remove the request regardless of whether the product has been accepted
				requests.remove(0);
			}
			if(!delivered)
				row.add(p); // Otherwise store it
		}
		return true;
	}


	public Patient getPriorityPatient(){

		// keep record of patient with the highest priority
		Patient highestPriority = row.get(0);
		byte highestPriorityLevel = highestPriority.getPriorityLevel();

		// go through patients in queue
		for (Patient p : row){
			byte priorityLevel = p.getPriorityLevel();

			// if a patient with higher priority is found, save him
			if(priorityLevel < highestPriorityLevel){
				highestPriority = p;
				highestPriorityLevel = priorityLevel;
			}
		}

		return highestPriority;
	}
}