package linkedLst;
import java.util.*;


public class myLinkedList {

	myLinkNode first;

	public myLinkedList(){
		first = null;
	}

	public void insertIntoLinkedList(int data){
		myLinkNode newNode = new myLinkNode(data);
		newNode.next = first;
		first = newNode;
	}

	public void printLinkedList(){
		myLinkNode current;
		current = first;
		while(current != null){
			current.printNode();	
			current = current.next;
		}
	}

	public void removeDups(myLinkedList l1){
		Hashtable<Integer, Boolean> table = new Hashtable<Integer, Boolean>();
		myLinkNode previous = null;
		myLinkNode current = first;

		while(current != null){
			if(!table.contains(current.intData)){
				table.put(current.intData, true);
			}

			current = current.next;
		}
		System.out.println(table.keySet());
	}

	/******************* INSERTS at the TAIL/Null of the Linked List ******************/
	public void insertIntoLinkedListAtTail(int valueToBeInserted){
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
	
	/************************** ADD TWO LINKEDLIST *****************************/
	public myLinkedList addLinkedList(myLinkedList l2, myLinkedList l3){
		myLinkedList l4 = new myLinkedList();
		
		myLinkNode current = first;
		while((l2.first != null) && (l3.first != null)){
			l4.first.intData = l2.first.intData + l3.first.intData;
			l2.first = l2.first.next;
			l3.first = l3.first.next;
			l4.first = l4.first.next;
		}
		first = current;
		return l4;
	}

}
