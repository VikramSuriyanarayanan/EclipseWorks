import java.util.*;
import java.net.*;
public class teest {

	/**
	 * @param args
	 */
	static int TimeStamp = 1;
	public static ArrayList<String> a1 = new ArrayList<String>();
	public static void main(String[] args) throws UnknownHostException {
		
		String a = "sun.nio.ch.SctpMessageInfoImpl@65f9c5c8[Address: /10.176.67.65:5000, Association: sun.nio.ch.SctpAssociationImpl@712801c5[associationID:5462, maxIn:10, maxOut:10], Assoc ID: 5462, Bytes: 17, Stream Number: 0, Complete: true, isUnordered: false]";

		String[] tok1 = a.split("/");
		System.out.println(tok1[1]);
		String[] tok2 = tok1[1].split(",");
		System.out.println(tok2[0]);
		String[] tok3 = tok2[0].split(":");
		System.out.println((tok3[0].trim()));
		String ipstr = tok3[0].trim();
				
				
		InetAddress Host = InetAddress.getByName(ipstr);
		System.out.println(Host);
		
		String hostname = Host.getHostName();
		System.out.println(hostname);
			
		}
	}
