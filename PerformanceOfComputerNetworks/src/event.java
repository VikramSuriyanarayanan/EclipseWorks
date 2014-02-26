
public class event {

	String source;
	int queueNo;
	double time;
	String type;
	String priority;

	public event(String tempsource, int tempqueueno, double tempTime, String tempType, String tempriority)
	{
		source    = tempsource;
		queueNo   = tempqueueno;
		time      = tempTime;
		type      = tempType;
		priority  = tempriority;
	}
}
