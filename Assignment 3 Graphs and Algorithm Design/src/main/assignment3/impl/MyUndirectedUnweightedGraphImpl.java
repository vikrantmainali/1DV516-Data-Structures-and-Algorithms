package main.assignment3.impl;

import main.assignment1.MyList;
import main.assignment1.MyListImpl;
import main.assignment3.UnweightedGraph;

public class MyUndirectedUnweightedGraphImpl<AnyType> implements UnweightedGraph<AnyType>
{	
    MyList<Vertex> vertices = new MyListImpl<>();
	int numberOfEdges = 0;
	
	class Vertex // inner class for Vertex
	{
		AnyType value;
		int edgesNotVisited = 0;
		boolean visited = false;
		MyList<Vertex> connected; 
		
		public Vertex(AnyType value)
		{
			this.value = value;
			this.connected = new MyListImpl<>();
		}
	}
	
	class Edge // inner class for Edge
	{
		Vertex source; 
		Vertex target; 
		
		public Edge(Vertex source, Vertex target) 
		{
			this.source = source;
			this.target = target;
		}
		
		public boolean equals(Edge e) 
		{
			if (this.source == e.source && this.target == e.target)
			{
				return true;
			}
			else if (this.source == e.target && this.target == e.source)
				return true;
			else
				return false;
		}
	}
	
	
	public MyUndirectedUnweightedGraphImpl() 
	{
	     this.vertices = new MyListImpl<>();
	}
	
	
    @Override
    public void addVertex(AnyType vertex) 
    {
    	if (!findDuplicates(vertex))
    	{
    		vertices.add(new Vertex(vertex));
    	}
    }
    
    private boolean findDuplicates(AnyType value)
    {
    	for (int i = 0; i < vertices.size(); i++) 
    	{
    		if (value.equals(vertices.get(i).value))
    				return true;
    	}
    	return false;
    }
    
    private Vertex findVertex(AnyType value) 
    {
    	for (int i = 0; i < vertices.size(); i++) 
    	{
    		Vertex vertex = vertices.get(i);
    		
    		if (value.equals(vertex.value))
    		{
    			return vertex;
    		}
    	}
    	return null;
    }

    @Override
    public void addEdge(AnyType sourceVertex, AnyType targetVertex) 
    {
    	Vertex source = findVertex(sourceVertex);
    	Vertex target = findVertex(targetVertex);
    	
    	source.connected.add(target);
    	source.edgesNotVisited++;
    	
    	target.connected.add(source);
    	target.edgesNotVisited++;
    	
    	numberOfEdges++;
    }

    @Override
    public boolean isConnected() 
    {
    	return connectedComponents().size() <= 1;
    }
    
    private Vertex notVisitedVertex() 
    {
    	 for (int i = 0; i < vertices.size(); i++) 
    	 {
    			Vertex vertex = vertices.get(i); 
    	    	if (!vertex.visited)
    	    	{
    				return vertex;
    	    	}
    		}
    	 return null;
    }
    
    
    private void visitConnectedVertices(Vertex vertex, MyList<AnyType> list) 
    {
    	//DFS traversal
    	vertex.visited = true;
    	list.add(vertex.value);
    	
    	for (int i = 0; i<vertex.connected.size(); i++) 
    	{
    		Vertex v = vertex.connected.get(i);
    		if (!v.visited) 
    		{
    			visitConnectedVertices(v, list);
    		}
    	}
    		
    }

    @Override
    public MyList<MyList<AnyType>> connectedComponents() 
    {

	 MyList<MyList<AnyType>> result = new MyListImpl<MyList<AnyType>>();
	 
    for (int i = 0; i<this.vertices.size(); i++) 
    {
		vertices.get(i).visited = false;
    }
    
    Vertex nextVertex = notVisitedVertex();
    
    while (nextVertex != null) {
    	MyList<AnyType> list = new  MyListImpl<>();
    	
    	visitConnectedVertices(nextVertex, list);
    	
	    result.add(list);
			
	    nextVertex = notVisitedVertex();
	    
	}
    for (int i = 0; i<this.vertices.size(); i++) 
    {
		vertices.get(i).visited = false;
    }
	return result;
    }

    @Override
    public boolean hasEulerPath()
    {
    	int numberOfOddDegrees = 0;
    	
    	for (int i = 0; i < vertices.size(); i++) 
    	{
    		Vertex vertex = vertices.get(i);
    		
    		if (vertex.connected.size()%2 != 0)
    		{
    			numberOfOddDegrees++;
    		}
    	}
    	if (numberOfOddDegrees == 2 || numberOfOddDegrees == 0) // Euler path conditions
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }

    @Override
    public MyList<AnyType> eulerPath() 
    {
    	MyList<AnyType> path = new MyListImpl<>();
    	MyList<Edge> edgePath = new MyListImpl<>();
    	
    	Vertex startingVertex = null;
    	for (int i=0; i<this.vertices.size(); i++) {
    		startingVertex = this.vertices.get(i);
    		if (startingVertex.connected.size()%2 != 0)
    		{
    			startingVertex = vertices.get(0);
    		}
    	}
    	
    	if (hasEulerPath())
    	{
    		findEulerPath(startingVertex, edgePath);
    	}
	    	
    	for (int i = 0; i < edgePath.size(); i++) 
    	{
    		Edge edge = edgePath.get(i);
    		path.add(edge.source.value);
    	}
    	
    	for (int i = 0 ; i < vertices.size(); i++) 
    	{
    		Vertex vertex = vertices.get(i);
    		vertex.edgesNotVisited = vertex.connected.size();
    	}
    	return path;
    }
 
   
    private boolean containsEdge(Edge edge, MyList<Edge> list) 
    {
 
    	for (int i = 0; i < list.size(); i++) 
    	{
    		if (list.get(i).equals(edge))
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
    private void findEulerPath(Vertex vertex, MyList<Edge> edgePath)
    {
    	for (int i = 0; i < vertex.connected.size(); i++) 
    	{ 
    		Vertex nextVertex = vertex.connected.get(i);
    		if (nextVertex.edgesNotVisited > 0)
    		{ 
    			vertex.edgesNotVisited --; 
    			Edge newEdge = new Edge(vertex, nextVertex); 
    			if(!containsEdge(newEdge, edgePath))
    			{
	    			edgePath.add(newEdge);
	    			findEulerPath(nextVertex, edgePath);
    			}
    		}    		
    		else if (edgePath.size()+1 == this.numberOfEdges) 
    		{
    			edgePath.add(new Edge(vertex, nextVertex)); 
    		}
    	}	
    }

}
