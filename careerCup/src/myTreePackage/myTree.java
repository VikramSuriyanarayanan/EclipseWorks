package myTreePackage;
import java.util.Stack;
// *********************************** TREE METHODS INCORPORATED IN THIS ********************//
public class myTree {

	static myTreeNode root;
	Stack<myTreeNode> s = new Stack<myTreeNode>();
	
	public void addNode(int Key, String name){
		myTreeNode newNode = new myTreeNode(Key, name);
		if(root == null){
			root = newNode;
			return;
		}
		else{
			myTreeNode parent;
			myTreeNode focusNode;
			focusNode = root;
			while(true) {
				parent = focusNode;
				if(Key < focusNode.data){
					focusNode = focusNode.leftNode;
					if(focusNode == null){
						parent.leftNode = newNode;
						return;
					}
				}
				else{
					focusNode = focusNode.rightNode;
					if(focusNode == null){
						parent.rightNode = newNode;
						return;
					}
				}

			}
		}
	}

	public void preOrderTraversal(myTreeNode focusNode){

		if(focusNode != null){
			System.out.println(focusNode);
			preOrderTraversal(focusNode.leftNode);			
			preOrderTraversal(focusNode.rightNode);
		}
	}

	public void inOrderTraversal(myTreeNode focusNode){

		if(focusNode != null){
			inOrderTraversal(focusNode.leftNode);
			System.out.println(focusNode);
			inOrderTraversal(focusNode.rightNode);
		}
	}

	public void postOrderTraversal(myTreeNode focusNode){

		if(focusNode != null){
			postOrderTraversal(focusNode.leftNode);
			postOrderTraversal(focusNode.rightNode);
			System.out.println(focusNode);
		}
	}

	public myTreeNode findNode(int Key){

		myTreeNode focusNode = root;
		while(focusNode.data!= Key){
			if(Key < focusNode.data){
				focusNode = focusNode.leftNode;
			}
			else{
				focusNode = focusNode.rightNode;
			}

			if(focusNode == null)
				return null;
		}
		System.out.println("Node found!!! its data value is "+focusNode.data+ " and name is "+ focusNode.name);
		return focusNode;
	}

	public void inOrderTraversalByStack(){
		System.out.println(root.print());
		s.push(root);
		myTreeNode checkNode = s.peek();
		leftNodeTraversal(checkNode);
		
		while(!s.isEmpty()){
			myTreeNode currentNode = s.pop();
			if(currentNode.rightNode != null){
				s.push(currentNode.rightNode);
				leftNodeTraversal(currentNode.rightNode);
			}
		}
	}
	
	public void leftNodeTraversal(myTreeNode checkNode){
		
		while(checkNode.leftNode != null){
			checkNode = checkNode.leftNode;
			s.push(checkNode);
		}
	}
	
	public int findDepth(myTreeNode node){
		if(root == null)
			return 0;
		return 1+Math.max(findDepth(root.leftNode),findDepth(root.rightNode));
	}
	
}
