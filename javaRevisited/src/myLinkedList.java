import java.util.Hashtable;

public class myLinkedList{

    myLinkNode first;
    Hashtable<Integer,String> h1 = new Hashtable<Integer,String>();
    
    myLinkedList(){
       first = null;    
    }
    public void insertIntoLinkedList(int data){
        myLinkNode newNode = new myLinkNode(data);
        newNode.next = first;
        first = newNode;
    }
    
    public void printLinkedList(){
    	myLinkNode current = first;
    	
    	while(current != null){
    		System.out.println(current.data);
    		current = current.next;
    	}
    }
       
    public void removeDuplicates(){
    	myLinkNode current = first;
    	while(current != null){
    		if(!(h1.containsKey(current.data))){
    			h1.put(current.data, "True");
    		}
    		current = current.next;
    	}
    	System.out.println("Without duplicates: "+ h1);
    }
   
}
