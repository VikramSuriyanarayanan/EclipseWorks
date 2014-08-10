package linkedList;
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
    		System.out.print(current.data + ";");
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
    
    public void insertIntoLinkedListAtSpecificPosition(myLinkNode nodeToInsert, int position) {
    	myLinkNode current = first;
    	
    	//Insert at the beginning
    	if(position == 0) {
    		nodeToInsert.setNext(current.next);
    		first = nodeToInsert;
    	}
    	
    	//Insert in end
		if(position == size()) {
			while(current.getNext() != null) {
				current = current.getNext();
			}
			current.setNext(nodeToInsert);
			nodeToInsert.setNext(null);
		} else {
			int tempSize = 1;
			while (tempSize != position) {
				current = current.getNext();
				tempSize++;
			}
			nodeToInsert.setNext(current.getNext());
			current.setNext(nodeToInsert);
		}
		
    }
    
    public int size() {
    	myLinkNode current = first;
    	int size = 0;
    	while(current != null) {
    		size++;
    		current = current.getNext();
    	}
    	return size;
    }
   
}
