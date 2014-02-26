import java.io.IOException; 
import java.net.InetSocketAddress; 
import java.net.SocketAddress; 
import java.nio.ByteBuffer;

import com.sun.nio.sctp.MessageInfo; 
import com.sun.nio.sctp.SctpChannel; 
import com.sun.nio.sctp.SctpServerChannel;

public class sctpServer {

    public static void main(String[] args) throws IOException { 
    	sctpServer SctpObject = new sctpServer();
    	SctpObject.go();
    }	
    
    public void go() throws IOException{
    	SctpServerChannel sctpServerChannel =  SctpServerChannel.open();
    	InetSocketAddress serverSocketAddress = new InetSocketAddress(5000); 
        System.out.println("create and bind for sctp address"); 
        sctpServerChannel.bind(serverSocketAddress); 
        System.out.println("address bind process finished successfully");

        SctpChannel sctpChannel; 
        while ((sctpChannel = sctpServerChannel.accept()) != null) { 
            System.out.println("client connection received"); 
            System.out.println("sctpChannel.getRemoteAddresses() = " + sctpChannel.getRemoteAddresses()); 
            System.out.println("sctpChannel.association() = " + sctpChannel.association()); 
            MessageInfo messageInfo = sctpChannel.receive(ByteBuffer.allocate(64000) , null, null); 
            System.out.println(messageInfo);

        } 
    }
}