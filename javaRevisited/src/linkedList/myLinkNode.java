package linkedList;
public class myLinkNode{

    myLinkNode next;
    int data;
    
    myLinkNode(int data){
        this.data = data;
    }
    
    public void setNext(myLinkNode next){
    	this.next = next;
    }
    public myLinkNode getNext() {
    	return next;
    }
}
