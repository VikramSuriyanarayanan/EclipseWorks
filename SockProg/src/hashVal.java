import java.util.HashMap;
import java.util.Map;

import com.sun.nio.sctp.SctpChannel;


public class hashVal implements Runnable {
 
	public static volatile HashMap<String,SctpChannel> hashValue = new HashMap<String,SctpChannel>();
	public static volatile HashMap<String,SctpChannel> hashValu = new HashMap<String,SctpChannel>();
	

	public hashVal(Map<String, SctpChannel> hashFinal) {
		this.hashValu =(HashMap<String, SctpChannel>) hashFinal;
	}
	public static void main(){
		System.out.println(hashValu);
	}
	@Override
	public synchronized void run() {
		hashValue.putAll(hashValu);
		System.out.println("HASH size in new class"+hashValue.size());
		System.out.println(hashValue);
	}
}
