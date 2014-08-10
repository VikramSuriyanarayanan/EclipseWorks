package linkedList;
public class removeCharacter{
	
//Write a method which will remove any given character from a String?
// Read more: http://javarevisited.blogspot.com/2011/06/top-programming-interview-questions.html#ixzz2cT4rcOj2
	
    public String removeChar(String originalString,char toBeRemoved){
        String newString = "";
        
        for(int i=0;i<originalString.length();i++){
            if(!(originalString.charAt(i) == toBeRemoved))
                newString = newString+ originalString.charAt(i);
        }
        
        return newString;
    }
    
    public static void main(String[] args){
        
    		String newbal = "Eric Johnson";
            removeCharacter obj = new removeCharacter();
            System.out.println("NewString: "+obj.removeChar("Hello",'o'));
            System.out.println("substring: "+newbal.substring(2));
            
    }
}