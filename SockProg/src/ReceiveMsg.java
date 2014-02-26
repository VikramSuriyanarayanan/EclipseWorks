import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.SctpChannel;


public class ReceiveMsg extends projOne implements Runnable
{
	SctpChannel sctpChannel;
	public static Map<String,SctpChannel> synchronizedMap  = Collections.synchronizedMap(new HashMap<String,SctpChannel>());
	public ReceiveMsg(SctpChannel sctpChannel)
	{
		this.sctpChannel = sctpChannel;
	}
	public void run()
	{
		try 
		{
			receiveMsg(sctpChannel);
		} 
		catch (Exception e) 
		{

			e.printStackTrace();
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
			keyMapper = fromNode+"to"+sno;
			//System.out.println(messageInfo);
			synchronizedMap.put(keyMapper, sctpChannel);
			
			message = byteToString(byteBuffer);
			processReceivedMsg(message,keyMapper,sctpChannel,synchronizedMap);
		}
	}

	public static String getHostName(MessageInfo messageInfo) {

		String a = messageInfo.toString();
	//	System.out.println("Inside getHostName of SERVER: "+sno );
		String[] tok1 = a.split("/");
		//	System.out.println(tok1[1]);
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
	//		System.out.println(Host);

		String hostname = Host.getHostName();
	//	System.out.println(hostname);
	//	System.out.println("Exiting Server");
		return hostname;

	}
	public static String byteToString(ByteBuffer byeBuffer)
	{
		byeBuffer.position(0);
		byeBuffer.limit(MESSAGE_SIZE);
		byte[] bufArr = new byte[byeBuffer.remaining()];
		byeBuffer.get(bufArr);
		return new String(bufArr);
	}
}
