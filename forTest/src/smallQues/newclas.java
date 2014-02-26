package smallQues;
import java.io.*;

public class newclas {

	//	Book myText = new Book();
	public int salesqty;
	public String prodname;
	
	{

	try
	{
		FileOutputStream fileOut =
				new FileOutputStream("output.dat");
		ObjectOutputStream out =
				new ObjectOutputStream(fileOut);
		//      out.writeObject(myText);
		out.write(salesqty);
		out.writeBytes(prodname);
		out.close();
		fileOut.close();
	}catch(IOException i)
	{
		i.printStackTrace();
	}
}
}