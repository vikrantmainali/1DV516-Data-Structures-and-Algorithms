package main.assignment3.impl;

import main.assignment1.MyList;
import main.assignment1.MyListImpl;
import main.assignment2.MyHashTableImpl;
import main.assignment3.UnweightedGraph;

public class MyUndirectedUnweightedGraphImpl<AnyType> implements UnweightedGraph<AnyType>
{
	private MyListImpl<MyListImpl<AnyType>> edges; // adjacency list
	private MyHashTableImpl<AnyType, Integer> vertexIndices;
	private MyListImpl<AnyType> vertexList;
	private int vertexCount;

	public MyUndirectedUnweightedGraphImpl()
	{
		this.edges = new MyListImpl<>();
		this.vertexIndices = new MyHashTableImpl<AnyType, Integer>(10);
		this.vertexList = new MyListImpl<>();
		this.vertexCount = 0;
	}

	public void addVertex(AnyType vertex)
	{
		Integer index = vertexIndices.contains(vertex);
		if (index == null) // Prevent adding duplicates
		{
			vertexIndices.insert(vertex, vertexCount);
			vertexList.add(vertex);
			edges.add(new MyListImpl<>());
			vertexCount++;
		}
	}

	public void addEdge(AnyType sourceVertex, AnyType targetVertex)
	{
		edges.get(vertexIndices.contains(sourceVertex)).add(targetVertex);
		edges.get(vertexIndices.contains(targetVertex)).add(sourceVertex);
	}

	@Override
	public boolean isConnected()
	{
		if (vertexCount == 0)
		{
			return true;
		}

		boolean[] visited = new boolean[vertexCount];
		MyListImpl<AnyType> resultList = new MyListImpl<>();
		dfs(vertexList.get(0), visited, resultList);
		return resultList.size() == vertexCount;
	}

	@Override
	public MyList<MyList<AnyType>> connectedComponents()
	{
		MyListImpl<MyList<AnyType>> componentsList = new MyListImpl<>();
		boolean[] visited = new boolean[vertexCount];

		for (int i = 0; i < vertexCount; i++)
		{
			if (!visited[i])
			{
				MyListImpl<AnyType> component = new MyListImpl<>();
				dfs(vertexList.get(i), visited, component);
				componentsList.add(component);
			}
		}
		return componentsList;
	}

	private void dfs(AnyType vertex, boolean[] visited, MyListImpl<AnyType> resultList)
	{
		int vertexIndex = vertexIndices.contains(vertex);
		visited[vertexIndex] = true;
		resultList.add(vertex);

		for (int i = 0; i < edges.get(vertexIndex).size(); i++)
		{
			AnyType adjacentVertex = edges.get(vertexIndex).get(i);
			int adjacentIndex = vertexIndices.contains(adjacentVertex);
			if (!visited[adjacentIndex]) {
				dfs(adjacentVertex, visited, resultList);
			}
		}
	}

	@Override
	public boolean hasEulerPath()
	{
		if (!isConnected())
		{
			return false;
		}
		int oddDegreeVertices = 0;
		for (int i = 0; i < vertexCount; i++)
		{
			if (edges.get(i).size() % 2 != 0)
			{
				oddDegreeVertices++;
			}
		}
		return oddDegreeVertices == 0 || oddDegreeVertices == 2;
	}

	@Override
	public MyList<AnyType> eulerPath()
	{
		MyListImpl<AnyType> resultList = new MyListImpl<>();
		if (!hasEulerPath())
		{
			return resultList;
		}

		// Copy edges to avoid changing original structure
		MyListImpl<MyListImpl<AnyType>> edgesCopy = new MyListImpl<>();
		for (int i = 0; i < edges.size(); i++)
		{
			MyListImpl<AnyType> newList = new MyListImpl<>();
			for (int j = 0; j < edges.get(i).size(); j++)
			{
				newList.add(edges.get(i).get(j));
			}
			edgesCopy.add(newList);
		}

		// Find a starting node with odd degree (if it exists)
		AnyType startingNode = vertexList.get(0);
		for (int i = 0; i < vertexCount; i++)
		{
			if (edges.get(i).size() % 2 != 0)
			{
				startingNode = vertexList.get(i);
				break;
			}
		}
		eulerDfs(startingNode, resultList);
		edges = edgesCopy;  // Original edges
		return resultList;
	}

	void eulerDfs(AnyType vertex, MyListImpl<AnyType> resultList)
	{
		int vertexIndex = vertexIndices.contains(vertex);
		while (edges.get(vertexIndex).size() > 0)
		{
			AnyType adjacentVertex = edges.get(vertexIndex).get(0);
			edges.get(vertexIndex).remove(0);
			edges.get(vertexIndices.contains(adjacentVertex)).remove(vertex);
			eulerDfs(adjacentVertex, resultList);
		}
		resultList.add(vertex);
	}
}