import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.SctpChannel;


public class client extends EstablishClientConnection implements Runnable
{
	
	public static Map<String,SctpChannel> synchronizedMap  = Collections.synchronizedMap(new HashMap<String,SctpChannel>());
	public static int MESSAGE_SIZE = 1000;	
	String serverName = null;
	int ServerPort = 0;
	int clientPort = 0; 
	static int ClientNo   = 0;
	public client(String serverName, int ServerPort, int clientPort, String Name, int ClientNo)
	{

		this.serverName = serverName;
		this.ServerPort = ServerPort;
		this.clientPort = clientPort;
		this.ClientNo   = ClientNo;
	}
	public void run()
	{

		try
		{
			//System.out.println("Getting connected to serverName: "+ serverName + "at the server port number "+ ServerPort + " from client port no"+ clientPort );
	//		System.out.println("Getting connected to serverName: "+ serverName + "at the server port number "+ ServerPort  );
			//Create a socket address for  server at net01 at port 5000
			SocketAddress socketAddress = new InetSocketAddress(serverName,ServerPort);
			//Open a channel. NOT SERVER CHANNEL
			SctpChannel sctpChannel = SctpChannel.open();
			//Bind the channel's socket to a local port. Again this is not a server bind
			sctpChannel.bind(new InetSocketAddress(clientPort));
			//Connect the channel's socket to  the remote server
			sctpChannel.connect(socketAddress);
			sctpMyNeighbourList.add(sctpChannel);
			receiveMsg(sctpChannel);
			//Before sending messages add additional information about the message

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void receiveMsg(SctpChannel sctpChannel) throws Exception
	{
		ByteBuffer byteBuffer; 
		MessageInfo messageInfo;
		String message;
		String HostName;
		String keyMapper;
		int fromNode;
		
		while(true)
		{
			byteBuffer = ByteBuffer.allocate(MESSAGE_SIZE);
			messageInfo = sctpChannel.receive(byteBuffer,null,null);
			HostName = getHostName(messageInfo);
			fromNode = (int) getKeyByValue(HostName);
			keyMapper = fromNode+"to"+ClientNo;
			synchronizedMap.put(keyMapper, sctpChannel);
			System.out.println("At clientSide: "+ synchronizedMap);
			//System.out.println(messageInfo);
			message = byteToString(byteBuffer);
			processReceivedMsg(message,keyMapper,sctpChannel,synchronizedMap);
		}
		
	}
	public static String byteToString(ByteBuffer byeBuffer)
	{
		byeBuffer.position(0);
		byeBuffer.limit(MESSAGE_SIZE);
		byte[] bufArr = new byte[byeBuffer.remaining()];
		byeBuffer.get(bufArr);
		return new String(bufArr);
	}
	
	public static String getHostName(MessageInfo messageInfo) {

		String a = messageInfo.toString();
	//	System.out.println("Inside getHostName of CLIENT: " + ClientNo);
		String[] tok1 = a.split("/");
		//System.out.println(tok1[1]);
		String[] tok2 = tok1[1].split(",");
		//	System.out.println(tok2[0]);
		String[] tok3 = tok2[0].split(":");
		//	System.out.println((tok3[0].trim()));
		String ipstr = tok3[0].trim();


		InetAddress Host = null;
		try {
			Host = InetAddress.getByName(ipstr);
		} catch (UnknownHostException e) {
			System.out.println("HostName Exception in ReceiveMsg()");
			e.printStackTrace();
		}
		//	System.out.println(Host);

		String hostname = Host.getHostName();
		//	System.out.println(hostname);
		//	System.out.println("Exiting client");
		return hostname;

	}

}
