package main.assignment3.impl;

public class MyNeighborhoodTest
{
		public static void main(String[] args) 
		{
			MyNeighborhoodImpl<String> graph = new MyNeighborhoodImpl<String>();

			graph.addVertex("A", 6, 18);
			graph.addVertex("B", 9, 36);
			graph.addVertex("C", 7, 21);
			graph.addVertex("D", 10, 60);

			graph.addEdge("A", "B", 1);
			graph.addEdge("A", "C", 1);
			graph.addEdge("A", "D", 25);
			graph.addEdge("B", "C", 18);
			graph.addEdge("B", "D", 1);
			graph.addEdge("C", "D", 1);
			
			int distance = graph.approximateMinimumDistance();
			System.out.println(graph.toString());
			System.out.println("minimum distance = " + distance);
			System.out.println("neighbours to visit:" + graph.neighborsToVisit(23));
		}
}
