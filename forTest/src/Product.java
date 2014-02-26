
public class Product {
	
	private String prodNo;
	private int prodQOH;

	  public void setString(String newName) {
	        prodNo = newName;
	    }
	  public void setInt(int newInt){
		  prodQOH = newInt;
	  }
	  
	  public int getInt(){
		  return prodQOH;
	  }
	  public String getString(){
		  return prodNo;
	  }

}
