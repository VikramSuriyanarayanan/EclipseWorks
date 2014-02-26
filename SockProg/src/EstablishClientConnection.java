import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;


public class EstablishClientConnection extends projOne implements Runnable
{
	public void run()
	{
		client[] clientObj = new client[100]; 
		try {
			String clientThreadName = null;
			String hostName = InetAddress.getLocalHost().getHostName();
			int k = 0;
			int clientPort = 0;
			String serverHost = null;
			k = (int) getKeyByValue(hostName);

			for(int i = 1 ; i < k ; i++){
	//			System.out.println("Trying to connect as Client from "+ hostName+" to Server number: "+ i);
				serverHost = h1.get(i);
				Random rand = new Random();
				clientPort = rand.nextInt(55000)+4000;
				clientThreadName = "Thread from "+hostName+" to Server no: "+ i; 
				clientObj[i-1] = new client(serverHost,serverPortArray[i-1],clientPort,clientThreadName,k);
				new Thread(clientObj[i-1]).start();

			}			
		} catch (UnknownHostException e) {

			e.printStackTrace();
		}

	}
}
