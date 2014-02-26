import java.util.*;

public class markovianModelOne {

	private static LinkedList<String> listOne = new LinkedList<String>();
	
	public static void main(String[] args) {
		
		String eventType = "departure";
		int eventTime = 0;
		
		//int systemTime = 0;
		
		
		listOne.add(eventTime,eventType);
		listOne.add(1, eventType);
	//	listOne.add(eventTime, eventType);
		
		Iterator<String> it1= listOne.iterator();
	
		while(it1.hasNext()){
			System.out.println(it1.next());
		}
	
	}

}
