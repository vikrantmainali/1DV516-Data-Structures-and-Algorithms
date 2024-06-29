package main.assignment3.impl;

import java.util.ArrayList;
import main.assignment1.MyList;
import main.assignment1.MyListImpl;
import main.assignment2.MyHashTableImpl;
import main.assignment3.Neighborhood;

public class MyNeighborhoodImpl<AnyType> implements Neighborhood<AnyType>
{
	int numberOfNeighbors = 0;
	ArrayList<Neighbor> neighbors = new ArrayList<Neighbor>(); // Store neighbours

	// class for Neighbor
	class Neighbor 
	{
		int chatterTime;
		int candyAmount;
		int number;
		AnyType value;
		int efficiency;

		MyHashTableImpl<Integer, Integer> neighborsDistances = new MyHashTableImpl<Integer, Integer>(0.75);

		public Neighbor(int number, AnyType value, int chatterTime, int candyAmount)
		{
			this.value = value;
			this.chatterTime = chatterTime;
			this.candyAmount = candyAmount;
			this.number = number;
			this.efficiency = this.candyAmount / this.chatterTime;
		}
	}

	@Override
	public void addVertex(AnyType neighbor, int chatterTime, int candyAmount) 
	{
		neighbors.add(new Neighbor(numberOfNeighbors, neighbor, chatterTime, candyAmount));
		numberOfNeighbors++;
		initializeDistances();
	}

	private Neighbor findNeighbor(AnyType value)
	{
		for (Neighbor n : neighbors) 
		{
			if (n.value.equals(value)) 
			{
				return n;
			}
		}
		return null;
	}

	@Override
	public void addEdge(AnyType fromNeighbor, AnyType toNeighbor, int distance)
	{
		Neighbor source = findNeighbor(fromNeighbor);
		Neighbor destination = findNeighbor(toNeighbor);

		if (source == null || destination == null) 
		{
			throw new NullPointerException();
		}

		source.neighborsDistances.insert(destination.number, distance);
		destination.neighborsDistances.insert(source.number, distance);

		initializeDistances();

		for (Neighbor n : neighbors) 
		{
			ArrayList<Neighbor> neighborList = (ArrayList<Neighbor>) neighbors.clone();
			while (neighborList.size() != 0) 
			{
				Neighbor currentNeighbor = shortestDistance(n, neighborList);

				int distToCurrentNeighbor = n.neighborsDistances.contains(currentNeighbor.number);
				neighborList.remove(currentNeighbor);
				if (distToCurrentNeighbor == Integer.MAX_VALUE) 
				{
					continue;
				}
				for (int i = 0; i < numberOfNeighbors; i++)
				{ 
					int distFromCurrentNeighbor = currentNeighbor.neighborsDistances.contains(i);
					if (distFromCurrentNeighbor < Integer.MAX_VALUE
							&& distToCurrentNeighbor + distFromCurrentNeighbor < n.neighborsDistances.contains(i)) 
					{
						n.neighborsDistances.insert(i, distToCurrentNeighbor + distFromCurrentNeighbor);
					}
				}
			}
		}
	}

	private void initializeDistances()
	{
		int maxValue = Integer.MAX_VALUE;

		for (Neighbor n : neighbors) 
		{
			for (int i = 0; i < numberOfNeighbors; i++) 
			{
				if (n.neighborsDistances.contains(i) == null && n.number != i) 
				{
					n.neighborsDistances.insert(i, maxValue);
				} 
				else if (n.neighborsDistances.contains(i) == null && n.number == i) 
				{
					n.neighborsDistances.insert(i, 0);
				}
			}
		}
	}

	public String toString() 
	{
		String string = "     ";
		for (Neighbor n : neighbors)
		{
			string = string + n.value + " ";
		}
		for (Neighbor n : neighbors) 
		{
			string = string + "\n " + n.value + " : ";
			for (int i = 0; i < numberOfNeighbors; i++)
			{
				string = string + n.neighborsDistances.contains(i) + " ";
			}
		}
		string = string + "\n";
		return string;
	}

	private Neighbor shortestDistance(Neighbor root, ArrayList<Neighbor> list) 	// Shortest distance from start
	{
		Integer maxValue = Integer.MAX_VALUE;
		Neighbor shortestDistanceNeighbor = null;
		for (Neighbor n : list)
		{
			int distance = root.neighborsDistances.contains(n.number);
			if (distance <= maxValue) 
			{
				shortestDistanceNeighbor = n;
				maxValue = distance;
			}
		}
		return shortestDistanceNeighbor;
	}

	@Override
	public int approximateMinimumDistance()
	{
		final int maxIterations = 3; // Limit number of iterations in cases of large number of vertices or edges

		int[] distances = new int[numberOfNeighbors];
		boolean[] visited = new boolean[numberOfNeighbors];

		// Initialize distances to a very large number except for the starting node
		for (int i = 0; i < numberOfNeighbors; i++)
		{
			distances[i] = Integer.MAX_VALUE;
			visited[i] = false;
		}
		distances[0] = 0; // Set start node to 0

		// Find the minimum distance path for each vertex
		for (int count = 0; count < numberOfNeighbors - 1 && count < maxIterations; count++)
		{
			int minDistance = Integer.MAX_VALUE;
			int minIndex = -1;

			for (int v = 0; v < numberOfNeighbors; v++)
			{
				if (!visited[v] && distances[v] <= minDistance)
				{
					minDistance = distances[v];
					minIndex = v;
				}
			}

			// Mark vertex as visited
			visited[minIndex] = true;

			// Update the distance values
			for (int v = 0; v < numberOfNeighbors; v++)
			{
				int edgeDistance = neighbors.get(minIndex).neighborsDistances.contains(v);
				if (!visited[v] && edgeDistance < Integer.MAX_VALUE &&
						distances[minIndex] != Integer.MAX_VALUE &&
						distances[minIndex] + edgeDistance < distances[v])
				{
					distances[v] = distances[minIndex] + edgeDistance;
				}
			}
		}

		// Add the distances to get the total approximate minimum distance
		int totalDistance = 0;
		for (int i = 0; i < numberOfNeighbors; i++)
		{
			if (distances[i] == Integer.MAX_VALUE)
			{
				System.out.println("No path to vertex " + i);
			}
			else
			{
				totalDistance += distances[i];
			}
		}
		return totalDistance;
	}

	@Override
	public MyList<AnyType> neighborsToVisit(int maximumTime)
	{
		MyListImpl<AnyType> list = new MyListImpl<>();

		/* Bottom-up approach to store the maximum candy value for each neighbor and time
		/ (The use of bottom-up was an inspiration from Knapsack problem from Lecture 11) */
		int[][] dynamicProgTable = new int[neighbors.size() + 1][maximumTime + 1];

		// Initialize the dynamic programming table with 0s
		for (int i = 0; i <= neighbors.size(); i++)
		{
			for (int j = 0; j <= maximumTime; j++)
			{
				dynamicProgTable[i][j] = 0;
			}
		}
		// Fill the dynamic programming table
		for (int i = 1; i <= neighbors.size(); i++)
		{
			for (int j = 0; j <= maximumTime; j++)
			{
				dynamicProgTable[i][j] = dynamicProgTable[i - 1][j];

				Neighbor currentNeighbor = neighbors.get(i - 1);

				// If there is time, update the current neighbor's value
				if (j >= currentNeighbor.chatterTime)
				{
					dynamicProgTable[i][j] = Math.max(dynamicProgTable[i][j], dynamicProgTable[i - 1][j - currentNeighbor.chatterTime] + currentNeighbor.efficiency);
				}
			}
		}
		// Best combination of neighbors to visit within the given maximum time
		int remainingTime = maximumTime;
		for (int i = neighbors.size(); i > 0 && remainingTime > 0; i--)
		{
			// If the value is different from the previous neighbor
			if (dynamicProgTable[i][remainingTime] != dynamicProgTable[i - 1][remainingTime])
			{
				Neighbor currentNeighbor = neighbors.get(i - 1);
				list.add(currentNeighbor.value);
				remainingTime -= currentNeighbor.chatterTime;
			}
		}
		System.out.println("best time: " + (maximumTime - remainingTime) + " min");
		System.out.println("best value: " + dynamicProgTable[neighbors.size()][maximumTime] + " candy per min");

		return list;
	}
}