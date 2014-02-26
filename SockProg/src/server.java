import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import com.sun.nio.sctp.SctpChannel;
import com.sun.nio.sctp.SctpServerChannel;

public class server extends projOne implements Runnable
{
	
	//---------------------------------------- run() ------------------------------
	public void run()
	{
		SctpChannel sctpChannel;
		
		try {
			String hName = InetAddress.getLocalHost().getHostName();
			sno = (int) getKeyByValue(hName);

			//Open a server channel
			SctpServerChannel sctpServerChannel = SctpServerChannel.open();
			//Create a socket addess in the current machine at port 5000
			InetSocketAddress serverAddr = new InetSocketAddress(serverPortArray[sno-1]);
			//Bind the channel's socket to the server in the current machine at port 5000
			sctpServerChannel.bind(serverAddr);
			//Server goes into a permanent loop accepting connections from clients		
			//System.out.println(Thread.currentThread()+": The Server is up and Running with port no "+serverAddr+" and socket "+ sctpServerChannel);
		//	System.out.println(Thread.currentThread()+": The Server is up and Running with port no ");

			while(true)
			{

				//Listen for a connection to be made to this socket and accept it
				//The method blocks until a connection is made
				//Returns a new SCTPChannel between the server and client
				sctpChannel = sctpServerChannel.accept();
				sctpMyNeighbourList.add(sctpChannel);
				new Thread(new ReceiveMsg(sctpChannel)).start();
				sctpChannel.getRemoteAddresses();
				//Receive message in the channel (byte format) and store it in buf
				//Note: Actual message is in byre format stored in buf
				//MessageInfo has additional details of the message

			}

		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}

	public String byteToString(ByteBuffer bytBuffer)
	{
		bytBuffer.position(0);
		bytBuffer.limit(MESSAGE_SIZE);
		byte[] bufArr = new byte[bytBuffer.remaining()];
		bytBuffer.get(bufArr);
		return new String(bufArr);
	}

}
