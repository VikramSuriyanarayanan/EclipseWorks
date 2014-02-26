package myTreePackage;

public class myTreeMainImplementation extends myTree {

	public static void main(String[] args) {
		myTree tree = new myTree();
		tree.addNode(100, "root");
		tree.addNode(50, "leftu");
		tree.addNode(150, "rightu");
		tree.addNode(201, "Gummu");
		tree.addNode(70, "jammu");
		tree.addNode(10, "mubbu");
		
		tree.inOrderTraversalByStack();
		tree.findDepth(root);
	//	tree.inOrderTraversal(tree.root);
	//	System.out.println("Trying to find node : ");
	//	System.out.println(tree.findNode(10));
		
	}

}
