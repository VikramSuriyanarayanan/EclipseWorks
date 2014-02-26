public class myLinkedListImplementation{
    
    public static void main(String[] args){
        myLinkedList l1 = new myLinkedList();
        l1.insertIntoLinkedList(23);
        l1.insertIntoLinkedList(24);
        l1.insertIntoLinkedList(25);
        l1.insertIntoLinkedList(26);
        l1.insertIntoLinkedList(23);
        System.out.println("Before duplicates removal: ");
        l1.printLinkedList();
        System.out.println("After duplicates removal: ");
        
        l1.removeDuplicates();

    }

}
