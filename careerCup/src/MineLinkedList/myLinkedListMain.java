package MineLinkedList;

public class myLinkedListMain {

	/**
	 * this shows how myLinkedList can be used
	 */
	public static void main(String[] args) {
		
		myLinkedList l1 = new myLinkedList();
		l1.insertIntoLinkedList("Ramesh");
		l1.insertIntoLinkedList("Suresh");
		System.out.println("After adding two nodes to the LinkedList ");
		l1.printmyLinkedList();
		l1.removeFromLinkedList();
		System.out.println("After removing last added node from LinkedList");
		l1.printmyLinkedList();
		

	}

}
