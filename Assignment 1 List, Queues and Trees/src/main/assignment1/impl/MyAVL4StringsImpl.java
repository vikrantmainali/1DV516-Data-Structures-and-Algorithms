package main.assignment1.impl;

import main.assignment1.Couple;

import main.assignment1.MyAVL4Strings;
import main.assignment1.MyList;

public class MyAVL4StringsImpl implements MyAVL4Strings 
{
	
	private Node root = null;
	private int size = 0;
	
    public MyAVL4StringsImpl() 
    {
    	root = null;
    	size = 0;
    }
    
    @Override
    public void insert(String element) 
    {
    	root = insert(element, root);
    }
    
    private Node insert(String key, Node node) 
	{
		if (node == null)
		{
			node = new Node(key);
			size++;
			return node;			
		} 
        int compareResult = key.compareTo(node.key);
        
        if(compareResult < 0)
        {
        	node.leftChild = insert(key, node.leftChild);
        }
        else if(compareResult > 0)
        {
        	node.rightChild = insert(key,node.rightChild);
        }
		return balance(node);
	}

    @Override
    public Couple<String> partialSearch(String beginning) 
    {	
		Couple<String> cs = new CoupleImpl<String>(); 
		Node temp = new Node(beginning); 

		Node minNode = getSmallest(root, temp, null);
		if (minNode == null) 
		{
			return null;
		}
		else
			cs.setFirst(minNode.key);
		
		Node maxNode = getLargest(root, temp, null);
		
		if (maxNode == null) 
		{// if there is no maximum than minimum is maximum
			maxNode = minNode;
		}
		
		cs.setLast(maxNode.key);
		
		return cs;
    }

    @Override
    public MyList<MyList<String>> LevelByLevelLists() 
    {
    	int startingLevel = -1;
    	
    	MyArrayList myList = new MyArrayList();
    	MyList<MyList<String>> result = (MyList<MyList<String>>) myList;
    	
    	traverse(root, startingLevel, myList);
    
    	return result;
    }
    
    private void traverse(Node node, int level, MyArrayList list) 
    {
    	level++;
    	
    	if(node == null)
    	{
    		
    	}
    	else
    	{
    		while(list.size() <= level)
    		{
    			list.add(new MyListImpl<String>());
    			traverse(node.leftChild, level, list);
        		traverse(node.rightChild, level, list);
        		
        		MyListImpl<String> tmp = (MyListImpl<String>) list.get(level);
        		tmp.add(root.key);
    		}
    	}
    }
    
    
    public int size()
    {
    	return size;
    }
    
 
    
    private Node balance(Node node)
    {
        final int ALLOWED_IMBALANCE = 1;

        if( node == null ) 
        {
            return node;
        }
        
        if( height(node.leftChild) - height(node.rightChild) > ALLOWED_IMBALANCE)
        {
            if(height(node.leftChild.leftChild) >= height(node.leftChild.rightChild))
            {
                node = rotateWithLeftChild(node);
            }
            else
            {
                node = doubleWithLeftChild(node);
            }
        }
        else
        {
        if(height(node.rightChild) - height(node.leftChild) > ALLOWED_IMBALANCE)
        {
            if(height(node.rightChild.rightChild ) >= height(node.rightChild.leftChild))
            {
                node = rotateWithRightChild(node);
            }
            else
            {
                node = doubleWithRightChild(node);
            }
        }
        }
        node.height = Math.max(height(node.leftChild ), height(node.rightChild))+1;
        
        return node;
    }
    
    private static int height(Node node)
    {
        if (node == null)
        {
            return -1;
        }
        return node.height;
    }
    
    private Node rotateWithLeftChild(Node k2)
    {
        Node k1 = k2.leftChild;
        k2.leftChild = k1.rightChild;
        k1.rightChild = k2;
        k2.height = Math.max(height(k2.leftChild), height( k2.rightChild) ) + 1;
        k1.height = Math.max( height( k1.leftChild), k2.height ) + 1;
        return k1;
    }

    private Node rotateWithRightChild(Node k1)
    {
        Node k2 = k1.rightChild;
        k1.rightChild = k2.leftChild;
        k2.leftChild = k1;
        k1.height = Math.max(height(k1.leftChild), height( k1.rightChild))+1;
        k2.height = Math.max(height(k2.rightChild), k1.height)+1;
        return k2;
    }

    private Node doubleWithLeftChild(Node node)
    {
        node.leftChild = rotateWithRightChild( node.leftChild );
        
        return rotateWithLeftChild(node);
    }


    private Node doubleWithRightChild(Node node)
    {
        node.rightChild = rotateWithLeftChild(node.rightChild);
        
        return rotateWithRightChild(node);
    }
    
    private Node getLargest(Node root, Node partialNode, Node previousNode) 
    {
    	if (root == null)
    	{
    		return previousNode;
    	}
    	if (isEqual(root.key, partialNode.key))
    	{
    		previousNode = root;
    		return getLargest(root.rightChild, partialNode, previousNode);
    	}
    	
    	else if (root.compareTo(partialNode) < 0)
    	{
			return getLargest(root.rightChild, partialNode, previousNode);
		}  
    	
    	else if (root.compareTo(partialNode) > 0)   
    	{
    		return getLargest(root.leftChild, partialNode, previousNode);  
    	}
    	else
    	{
    		return previousNode;
    	}
	}

	private Node getSmallest(Node root, Node partialNode, Node previousNode) 
	{
    	if (root == null) 
    	{
    		return previousNode; 
    	}
    	if (isEqual(root.key, partialNode.key))
    	{
    		previousNode = root;
    	}

    	if (root.compareTo(partialNode) < 0) 
    	{ 
			return getSmallest(root.rightChild, partialNode, previousNode);
		}
		else if (root.compareTo(partialNode) > 0) 
		{
			return getSmallest(root.leftChild, partialNode, previousNode);
		}
		else 
		{
			return root; 
		}
	}
    
    private boolean isEqual(String str, String partialStr) 
    {
    	if (str.length() < partialStr.length()) 
    	{
    		return false;
    	}
    	boolean isEqual = true;  
    	for (int i = 0; i < partialStr.length(); i++) 
    	{
    		if (str.charAt(i) == partialStr.charAt(i)) 
    		{
    			continue;
    		}
    		else 
    		{
    			isEqual = false;
    			break;
    		}
    	}
    	return isEqual;    	
    }
    
    public boolean isEmpty()
    {
        return root == null;
    }
    
    
    public void printTree()
    {
        if(isEmpty())
            System.out.println("Empty tree");
        else
    
            printTree( root);
    }
    
    private void printTree(Node node)
    {
        if( node != null )
        {
            printTree(node.leftChild);
            System.out.println(node.key);
            printTree(node.rightChild);
        }
    }
}


class Node implements Comparable<Node>
{
  String key = null;
  int height = 0;
  Node leftChild = null;
  Node rightChild = null;
  Node next = null;
    
    public Node(String d) 
    {
        key = d;
        height = 0;
        leftChild = null;
        rightChild = null;
    }

	@Override
	public int compareTo(Node node) 
	{
		// TODO Auto-generated method stub
		return this.key.compareTo(node.key);
	}
}
