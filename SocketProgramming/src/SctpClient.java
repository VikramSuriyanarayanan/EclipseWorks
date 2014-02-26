import java.io.IOException; 
import java.net.InetAddress; 
import java.net.InetSocketAddress; 
import java.net.SocketAddress; 
import java.nio.ByteBuffer;

import com.sun.nio.sctp.MessageInfo; 
import com.sun.nio.sctp.SctpChannel;

/** 
* @author sandarenu 
* $LastChangedDate$ 
* $LastChangedBy$ 
* $LastChangedRevision$ 
*/ 
public class SctpClient {
	static int a =10;
    public static void main(String[] args) throws IOException { 
        a = a+10;
        
    	try { 
            SocketAddress socketAddress = new InetSocketAddress( 6050); 
            System.out.println("open connection for socket [" + socketAddress + "]"); 
            SctpChannel sctpChannel = SctpChannel.open();//(socketAddress, 1 ,1 ); 
            sctpChannel.bind(new InetSocketAddress( 6060)); 
            sctpChannel.connect(socketAddress, 1 ,1);

            System.out.println("sctpChannel.getRemoteAddresses() = " + sctpChannel.getRemoteAddresses()); 
            System.out.println("sctpChannel.getAllLocalAddresses() = " + sctpChannel.getAllLocalAddresses()); 
            System.out.println("sctpChannel.isConnectionPending() = " + sctpChannel.isConnectionPending()); 
            System.out.println("sctpChannel.isOpen() = " + sctpChannel.isOpen()); 
            System.out.println("sctpChannel.isRegistered() = " + sctpChannel.isRegistered()); 
            System.out.println("sctpChannel.provider() = " + sctpChannel.provider()); 
            System.out.println("sctpChannel.association() = " + sctpChannel.association());

            System.out.println("send bytes"); 
            final ByteBuffer byteBuffer = ByteBuffer.allocate(64000); 
            //Simple M3ua ASP_Up message 
            byte [] message = new byte []{1,0,3,1,0,0,0,24,0,17,0,8,0,0,0,1,0,4,0,8,84,101,115,116};

            final MessageInfo messageInfo = MessageInfo.createOutgoing(null, 0); 
            System.out.println("messageInfo = " + messageInfo); 
            System.out.println("messageInfo.streamNumber() = " + messageInfo.streamNumber());

            byteBuffer.put(message); 
            byteBuffer.flip();

            try { 
                sctpChannel.send(byteBuffer, messageInfo); 
            } catch (Exception e) { 
                e.printStackTrace(); 
            } 
            System.out.println("close connection"); 
            sctpChannel.close();

        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
}