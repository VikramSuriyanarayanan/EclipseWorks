package MineLinkedList;
public class myLinkedList {

	/**
	 * This is my first program to learn about linkedlist. lets see how it works, from YOUTUBE
	 */

	myLinkNode first ;

	public myLinkedList(){
		this.first = null;
	}

	/******************* INSERTS at the Beginning/HEAD of the Linked List ******************/
	public void insertIntoLinkedList(String valueToBeInserted){

		myLinkNode newNode = new myLinkNode(valueToBeInserted);	
		newNode.next = first;
		first = newNode;

	}
	
	/******************* INSERTS at the TAIL/Null of the Linked List ******************/
	public void insertIntoLinkedListAtTail(String valueToBeInserted){
		myLinkNode current = first;
		myLinkNode previous = first;
		myLinkNode newNode = new myLinkNode(valueToBeInserted);	

		while(first != null){
			previous = first;
			first = first.next;
		}	
		
		previous.next = newNode;
		newNode.next = first;
		first = current;

	}
    /******************* REMOVES from the Beginning/HEAD of the Linked List **************/
	public void removeFromLinkedList(){
		if(!isEmpty()){
			myLinkNode currentNode = first.next;
			first = currentNode;
		}
	}
	
	public boolean searchForaNode(String searchedValue){
		myLinkNode searchingNode = first;
		
		while(searchingNode.next != null){
			if(searchingNode.strValue.equalsIgnoreCase(searchedValue)){
				return true;
			}
		}
		
				return false;
	}

	public void printmyLinkedList(){
		myLinkNode currentLink = first;

		while(currentLink != null) {
			currentLink.printLink();
			currentLink = currentLink.next;
			System.out.println("currentLink value  is"+currentLink);
		}
	}

	public boolean isEmpty(){
		return (first == null);
	}
	
	/************************** REVERSING a Linked List ********************************************************************/
	
	public void reverse() {
	    myLinkNode current = first;
	    first = null;
	    while (current != null) {
	        myLinkNode save = current;
	        current = current.next;
	        save.next = first;
	        first = save;
	    }
	}
}
