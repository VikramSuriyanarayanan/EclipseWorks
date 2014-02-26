package myTreePackage;
public class myTreeNode {

	int data;
	String name;
	myTreeNode leftNode;
	myTreeNode rightNode;
	
	public myTreeNode(int data, String name){
		this.data = data;
		this.name = name;
	}
	
	public String print(){
		return "The Node is: "+ name + ":" + data;
	}
	
}
