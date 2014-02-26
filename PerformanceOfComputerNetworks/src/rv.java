import java.util.*;

public class rv{
	double Seed = 1111.0;
	/*******************************************/
	/* returns a uniform (0,1) random variable */
	/*******************************************/
	double uni_rv()
	{
		double k = 16807.0;
		double m = 2.147483647e9;
		double rv;

		Seed=((k*Seed)%(m));
		rv=Seed/m;
		return(rv);
	}


	/*******************************/
	/* given arrival rate lambda   */
	/* returns an exponential r.v. */
	/*******************************/
	double exp_rv(double lambda)
	{
		double exp;
		exp = ((-1) / lambda) * Math.log(uni_rv());
		return(exp);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	LinkedList InsertIntoEventList (LinkedList tempList,event E) // implements linked list of events
	{
		LinkedList eventList = new LinkedList();
		LinkedList sorted = new LinkedList();

		eventList = tempList;
		boolean flag = true;
		if(eventList.size()==0)
		{
			eventList.add(E);
			return eventList;
		}
		else if(((event) eventList.getLast()).time<=E.time)
		{
			eventList.add(E);
			return eventList;
		}
		else
		{
			ListIterator itr=eventList.listIterator();
			while(itr.hasNext())
			{
				event VAL = (event) itr.next();
				if(VAL.time>E.time && flag){
					sorted.add(E);
					flag = false;
				}
				sorted.add(VAL);

			}
			eventList =sorted;
			flag = true;
			return eventList;
		}		  
	}

}


