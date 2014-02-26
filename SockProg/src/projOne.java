import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;

import com.sun.nio.sctp.*;


public class projOne{

	public static final int MESSAGE_SIZE = 65;
	public static final int MESSAGE_ITERATOR = 5;
	public static HashMap<Integer,String> h1 = new HashMap<Integer,String>();
	public static int[] serverPortArray = new int[20];
	public static volatile ArrayList<SctpChannel> sctpMyNeighbourList = new ArrayList<SctpChannel>();
	public static List<SctpChannel> shuffledNeighbours;
	public static int sno;
	public static int LamportsLogicalClock = 0;
	public static int TimeStamp = 0; 
	public static volatile HashMap<Integer,SctpChannel> hashChannelSctpPair = new HashMap<Integer,SctpChannel>();
	public static volatile HashMap<String,String> hashMapFinalPair = new HashMap<String,String>();
	public static volatile HashMap<String,SctpChannel> hashFinal = new HashMap<String,SctpChannel>();
	//public static Map<String,SctpChannel> hashFinal  = Collections.synchronizedMap(new HashMap<String,SctpChannel>());

	public static ConcurrentHashMap <String,SctpChannel> hashNodeSctpPair = new ConcurrentHashMap<String,SctpChannel>();
	public static ArrayList<String> unDeliveredMsgs= new ArrayList<String>();
	public static volatile ArrayList<String> tempList = new ArrayList<String>();

	public static void main(String[] args) {
		final projOne cObject = new projOne();
		cObject.parseMethod();
		cObject.printMap(h1);

		new Thread(new server()).start();
		new Thread(new EstablishClientConnection()).start();
		try 
		{
			Thread.sleep(12000);
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		application();

		try 
		{
			Thread.sleep(18000);
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		ReplyingWithTimeStamp();

	}


	public static void parseFileAndPopulateMap() {
		try{
			FileInputStream fstream = new FileInputStream("HashMapValueFile.txt");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			int indexVal = 0;

			while ((strLine = br.readLine())!= null) {

				String[] tokens = strLine.split(",");
				String key = tokens[0].trim();
				String value = tokens[1].trim();

				if(!hashMapFinalPair.containsKey(key)){
					hashMapFinalPair.put(key, value);
				}
			}
			//Close the input stream
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}


	}


	public static void application()
	{
		System.out.println("HashMap size is: "+ h1.size());
		System.out.println("Arraylist Size is : "+ sctpMyNeighbourList.size());
		Msend();
	}

	public static void Msend()
	{
		MessageInfo messageInfo;
		ByteBuffer byteBuffer;	
		try
		{
			for(int i = 0; i<MESSAGE_ITERATOR;i++)
			{
				String message =  "m"+"-"+sno+"-"+i+"-"+LamportsLogicalClock+"-undeliverable";
				chooseNeighbours();

				for(int j = 0; j<shuffledNeighbours.size() -1;j++)
				{

					byteBuffer = ByteBuffer.allocate(MESSAGE_SIZE);
					messageInfo = MessageInfo.createOutgoing(null,0);
					byteBuffer.put(message.getBytes());
					byteBuffer.flip();
					shuffledNeighbours.get(j).send(byteBuffer, messageInfo);
				}  
				//			sctpMyNeighbourList.get(0).send(byteBuffer,messageInfo);
				LamportsLogicalClock++;         
				Thread.sleep(1000);
			} 
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}                                                        
	}

	public static void chooseNeighbours()
	{
		shuffledNeighbours = new ArrayList<SctpChannel>();
		for (int i = 0; i < sctpMyNeighbourList.size(); i++) 
		{
			shuffledNeighbours.add(sctpMyNeighbourList.get(i));
		}

		Collections.shuffle(shuffledNeighbours);
	}


	public static synchronized void processReceivedMsg(String message, String key,SctpChannel scttchannel,Map<String, SctpChannel> h1)
	{
		System.out.println(message);
		String sContent = key+","+scttchannel;
		String sFileName = "HashMapValueFile.txt";

		hashFinal.put(key, scttchannel);

		try {

			File oFile = new File(sFileName);
			if (!oFile.exists()) {
				oFile.createNewFile();
			}
			if (oFile.canWrite()) {
				BufferedWriter oWriter = new BufferedWriter(new FileWriter(sFileName, true));
				oWriter.write (sContent);
				oWriter.newLine();
				oWriter.close();
			}

		}
		catch (IOException oException) {
			throw new IllegalArgumentException("Error appending/File cannot be written: \n" + sFileName);
		}


		parseMessageForReply(message, scttchannel);
	}

	//---------------------------------------------------------------------------------------------------------------------------------------
	// Parsing the message and populating its values into HashMap, if its undeliverable
	public static void parseMessageForReply(String msg, SctpChannel sctchannel){
		String[] token = msg.split("-");

		String msgDeliveryType = token[4];
		int channelNoforSendingReply = Integer.parseInt(token[1]);

		if(msgDeliveryType.trim().equalsIgnoreCase("undeliverable")){
			String Modifiedmsg = (String) msg.subSequence(0, 6);
			Modifiedmsg = Modifiedmsg + TimeStamp+"-reply";
			TimeStamp++;
			unDeliveredMsgs.add(Modifiedmsg);
			hashChannelSctpPair.put(channelNoforSendingReply, sctchannel);
		}
	}

	//---------------------------------------------------------------------------------------------------------------------------------------
	// Parsing the file and populating its values into HashMap
	public void parseMethod(){
		try{
			FileInputStream fstream = new FileInputStream("config.txt");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			int indexVal = 0;

			while ((strLine = br.readLine())!= null) {

				String[] tokens = strLine.split(",");
				int serialno = Integer.parseInt(tokens[0]);
				String value = tokens[1];
				serverPortArray[indexVal] =Integer.parseInt(tokens[2]);
				indexVal++;
				h1.put(serialno, value);
			}
			//Close the input stream
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//---------------------------------------------------------------------------------------------------------------------------------------
	// Comparing the timestamp and sending the Timestamp back to the Sending Node
	public static void ReplyingWithTimeStamp(){

		String reply = null;
		String temp = null;
		MessageInfo messageInfo;
		ByteBuffer byteBuffer;	

		for(int i = 0; i < unDeliveredMsgs.size();i++){
			temp = unDeliveredMsgs.get(i);
			String[] token = temp.split("-");
			int msgType = Integer.parseInt(token[2]);
			int TimeStamp = Integer.parseInt(token[3]);
			int replyToNode = Integer.parseInt(token[1]);
			String key = replyToNode+"to"+sno;

			if(replyToNode == (i+1)){
				replyToNode = sno;
				reply = "m-"+replyToNode+"-"+msgType+"-"+TimeStamp+"-reply";
				System.out.println("SenderEnd msg:"+reply);
				byteBuffer = ByteBuffer.allocate(MESSAGE_SIZE);
				messageInfo = MessageInfo.createOutgoing(null,0);
				byteBuffer.put(reply.getBytes());
				byteBuffer.flip();
				System.out.println("Sending Reply");
				try {
					hashNodeSctpPair.get(key).send(byteBuffer, messageInfo);
				} catch (IOException e) {
					System.out.println("Error in sending Timestamp Reply");
					e.printStackTrace();
				}  
			}
		}

	}

	//---------------------------------------------------------------------------------------------------------------------------------------
	// Getting the Key from value of hashMap

	public static Object getKeyByValue(String value) {

		Iterator it = h1.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry)it.next();

			if (value.equals(pairs.getValue())) {
				return pairs.getKey();
			}
		}

		return null;
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	// Printing the HashMap
	public void printMap(Map mp) {
		Iterator it = mp.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry)it.next();
			System.out.println(pairs.getKey() + " = " + pairs.getValue());
			//	it.remove(); // avoids a ConcurrentModificationException
		}

	}


}
