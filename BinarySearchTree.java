class BinarySearchTree {
 
    /* Class containing left and right child of current node and key value*/
    class Node {
        char key;
        Node left, right;
 
        public Node(char item) {
            key = item;
            left = right = null;
        }
    } 
    Node root;
 
    /* Constructor*/
    BinarySearchTree() { 
        root = null; 
    }

    void insert(char key) {
       insertRec(null,root, key);
    }
    void insertRec(Node parent,Node node, char key) {
    	if(root==null) {
    		root = new Node(key);
    	}else if (node == null) {
            node = new Node(key);
            if(parent.key>node.key) {
            	parent.left=node;
            }else {
            	parent.right=node;
            }
        }else if (node.key>key)
            insertRec(node,node.left, key);
        else {
        	  insertRec(node,node.right, key);
        }
         
    }
    public boolean find(char key) {
    	Node temp1 = search(root,key);
    	if(temp1!=null) {
    		return true;
    	}else {
    		return false;
    	}
    }
    public Node search(Node root, char key)
    {
        if (root==null || root.key==key)
            return root;
     
        /* value is greater than root's key*/
        if (root.key > key)
            return search(root.left, key);
     
        /* value is less than root's key*/
        return search(root.right, key);
    }
    void deleteKey(int key)
    {
        root = deleteRec(root, key);
    }
 
    /* A recursive function to insert a new key in BST */
    Node deleteRec(Node root, int key)
    {
        /* Base Case: If the tree is empty */
        if (root == null)  return root;
 
        /* Otherwise, recur down the tree */
        if (key < root.key)
            root.left = deleteRec(root.left, key);
        else if (key > root.key)
            root.right = deleteRec(root.right, key);
        else
        {
            /* node with only one child or no child*/
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;
            root.key = minValue(root.right);
            root.right = deleteRec(root.right, root.key);
        }
 
        return root;
    }
 
    char minValue(Node root)
    {
        char minv = root.key;
        while (root.left != null)
        {
            minv = root.left.key;
            root = root.left;
        }
        return minv;
    }
    
    char maxValueFind() {
    	char t = maxValue(root);
    	return t;
    }
    char maxValue(Node root)
    {
        char maxv = root.key;
        while (root.right != null)
        {
            maxv = root.right.key;
            root = root.right;
        }
        return maxv;
    }

}