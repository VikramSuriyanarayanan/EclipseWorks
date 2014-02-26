import java.util.Comparator;


public class ReqPriorityCompartor implements Comparator<Request>{

	public int compare(Request x, Request y)
    {
        if (x.getTimeStamp() < y.getTimeStamp())
        {
            return -1;
        }else if (x.getTimeStamp() == y.getTimeStamp()){
        	if(x.getProcessNo() <y.getProcessNo()){
        		return -1;
        	}else{
        		return 1;
        	}
        }else{
        	return 1;
        }
    }
}
