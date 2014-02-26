package linkedLst;

public class myLinkedListMain {

	public static void main(String[] args) {
	 myLinkedList l1 = new myLinkedList();
	 l1.insertIntoLinkedList(1);
	 l1.insertIntoLinkedList(2);
	 l1.insertIntoLinkedList(1);
	 l1.insertIntoLinkedList(2);
	 l1.insertIntoLinkedList(3);
	 l1.printLinkedList();
	 l1.removeDups(l1);
	 l1.insertIntoLinkedListAtTail(2000);
	 l1.printLinkedList();
	 
	 // ADDING Two LinkedList
	 myLinkedList l2 = new myLinkedList();
	 l2.insertIntoLinkedList(2);
	 l2.insertIntoLinkedList(0);
	 l2.insertIntoLinkedList(2);
	 
	 myLinkedList l3 = new myLinkedList();
	 l3.insertIntoLinkedList(6);
	 l3.insertIntoLinkedList(8);
	 l3.insertIntoLinkedList(5);
	 
	 myLinkedList l4 = new myLinkedList();
	 l4.addLinkedList(l2,l3);
	 l4.printLinkedList();
	}


}
