package linkedList;
public class myLinkedListImplementation{
    
    public static void main(String[] args){
        myLinkedList l1 = new myLinkedList();
        l1.insertIntoLinkedList(23);
        l1.insertIntoLinkedList(24);
        l1.insertIntoLinkedList(25);
        l1.insertIntoLinkedList(26);
        l1.insertIntoLinkedList(23);
        System.out.print("Before duplicates removal: ");
        l1.printLinkedList();
        System.out.println();
        System.out.print("After duplicates removal: ");
        
        l1.removeDuplicates();
        
        System.out.print("printing linkedList of size " + l1.size()+ ": " );   
        l1.printLinkedList();
        System.out.println();
        
        myLinkNode newNode =  new myLinkNode(50);
        l1.insertIntoLinkedListAtSpecificPosition(newNode, 5);
        System.out.print("printing linkedList after inserting of size " + l1.size()+ ": " );
        l1.printLinkedList();
        System.out.println();
        
        myLinkNode middleNode =  new myLinkNode(255);
        l1.insertIntoLinkedListAtSpecificPosition(middleNode, 3);
        System.out.print("printing linkedList after inserting of size " + l1.size()+ ": " );
        l1.printLinkedList();
        
    }

}
