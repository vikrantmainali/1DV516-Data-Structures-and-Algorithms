package test.assignment3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import main.assignment1.MyList;
import main.assignment3.UnweightedGraph;
import main.assignment3.impl.MyUndirectedUnweightedGraphImpl;


public class TestMyUndirectedWeightedGraph<AnyType> 
{

    
    @Test
    public void testIsConnected()
    {

    	UnweightedGraph<String> graph = new MyUndirectedUnweightedGraphImpl<String>();

    	graph.addVertex("A");
    	graph.addVertex("B");

    	Assertions.assertFalse(graph.isConnected());
    	MyList<MyList<String>> connectedComponents = graph.connectedComponents();
    	Assertions.assertEquals(2, connectedComponents.size());
    	Assertions.assertEquals(1, connectedComponents.get(0).size());
    	Assertions.assertEquals(1, connectedComponents.get(1).size());
	
    	graph.addEdge(new String("A"), new String("B"));
    	Assertions.assertTrue(graph.isConnected());
	
    	connectedComponents = graph.connectedComponents();
    	Assertions.assertEquals(1, connectedComponents.size());
    	Assertions.assertEquals(2, connectedComponents.get(0).size());
    }
    
    @Test
    void testEulerCircle() 
    {
    	UnweightedGraph<Integer> graph = new MyUndirectedUnweightedGraphImpl<Integer>();
    	graph.addVertex(0);
    	for (int i = 1; i <= 7; i++)
    	{
    		graph.addVertex(i);
    		graph.addEdge(i - 1, i);
    	}
    	graph.addEdge(7, 0); //Close the circle

    	Assertions.assertTrue(graph.hasEulerPath(), "Failed at Euler in a circle");
    }

    @Test
    void testEulerLine() 
    {
    	UnweightedGraph<Integer> graph = new MyUndirectedUnweightedGraphImpl<Integer>();
    	graph.addVertex(0);
    	for (int i = 1; i <= 7; i++)
    	{
    		graph.addVertex(i);
    		graph.addEdge(i - 1, i);
    	}
    	Assertions.assertTrue(graph.hasEulerPath(), "Failed at Euler in a line");
    }
}
