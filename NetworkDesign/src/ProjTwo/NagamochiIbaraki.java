package ProjTwo;

import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;
import org.jgraph.JGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;

public class NagamochiIbaraki {
	
	public static int[][] nodeInformation;
	public static int[][] nodeAdjacencyMatrix;
	public static int[][] temporaryNodes;
	public static int[][] node = new int[2][2];
	
	public static int noOfNodes;
	public static int idx = 0;
	public static int cnt = 0;
	public static int nodeRemain = 0;
	public static int FinalOutput;
	public static int minNodeCnt = 0;
	public static int edges = 0;
	public static int graphDeg = 0;
	public static int numEdges;
	public static int valueOfK;
	public static int[][] listMA;
	public static int[][] edgeCntr;

	
	public static void main(String args[])
	{
		try
		{
//***************************** Get the input from User *****************************
			
			System.out.println("Enter the total number of nodes");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			noOfNodes = Integer.parseInt(in.readLine());
			minNodeCnt = noOfNodes;
			
			System.out.println("Enter total number of edges");
			BufferedReader in1 = new BufferedReader(new InputStreamReader(
					System.in));
			numEdges = Integer.parseInt(in1.readLine());
			
			valueInitializing();
			createNodeEdgeMatrix();
			
			/* Function call to minimum cut */
			int mincut = calcMinCut();
			/* if graph = disconnected, minimum cut =0 */
			if (mincut == 0)
			{
				System.out
				.println("The original graph is a disconnected graph");
//***************************** Display inputs and outputs  *********************************
				System.out.println("Nodes " + noOfNodes);
				System.out.println("Edges " + numEdges);
				System.out.println("Minimum Cut = " + mincut);
			}
			else
			{
				int numCriticalEdges = findCriticalEdges(mincut);
				System.out.println();
				System.out.println("Minimum Cut = " + mincut);
				System.out.println("Number of Critical Edges :"
						+ numCriticalEdges);
			}
			
//*********************************************** Drawing Graph ****************************** 
			SimpleGraph displayGraph = new SimpleGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

			for (int i = 0; i < noOfNodes; i++) {
				displayGraph.addVertex(Integer.toString(i));
			}

			java.util.List<String> displayVertices = new ArrayList<String>(displayGraph.vertexSet());

			for (int source = 0; source < noOfNodes; source++) {
		
				for (int target = source + 1 ; target <  noOfNodes; target++) {

					if(nodeAdjacencyMatrix[source][target] != 0)
					{
						displayGraph.addEdge(displayVertices.get(source), displayVertices.get(target));
					}
		
				}
			}

			JGraph jgraph = new JGraph( new JGraphModelAdapter( displayGraph ) );

			JScrollPane scroller = new JScrollPane(jgraph);
			JFrame frame = new JFrame("Graph frame");
			frame.setSize(600,600);
			frame.setDefaultCloseOperation(3);
			frame.add(scroller);
			frame.setVisible(true);

		} catch (IOException e)
		{
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//***************************** Finding Critical Edges  *********************************
	private static int findCriticalEdges(int mincut)
	{
		int criticalEdgeCount = 0;
		int tempmincut = 0;

		for (int i = 0; i < nodeAdjacencyMatrix.length; i++)
		{
			for (int j = 0; j < nodeAdjacencyMatrix.length; j++)
			{
				if (1 == nodeAdjacencyMatrix[i][j] && (i != j) && ( j > i))
				{
					nodeAdjacencyMatrix[i][j] = 0;
					nodeAdjacencyMatrix[j][i] = 0;
					makecopyOfmatrix();
					tempmincut = calcMinCut();
					if (tempmincut < mincut)
					{
						criticalEdgeCount++;
						System.out.println("["+i+"]"+"["+j+"]");
						System.out.println(nodeAdjacencyMatrix[i][j]);

					}
					nodeAdjacencyMatrix[i][j] = 1;
					nodeAdjacencyMatrix[j][i] = 1;


				}
			}
		}

		System.out.println();
		return criticalEdgeCount;
	}
	private static void makecopyOfmatrix()
	{
		nodeInformation = new int[nodeAdjacencyMatrix.length][nodeAdjacencyMatrix.length];
		minNodeCnt = noOfNodes;
		listMA = new int[noOfNodes][2];
		cnt = 0;
		for (int m = 0; m < nodeAdjacencyMatrix.length; m++)
		{
			for (int k = 0; k < nodeAdjacencyMatrix.length; k++)
			{
				nodeInformation[m][k] = nodeAdjacencyMatrix[m][k];
			}
		}
	}
	private static void createNodeEdgeMatrix()
	{
		// new procedure
		int temperory = numEdges;
		int tempIterations = numEdges;
		int tempCounter = 0;
		while (tempCounter < tempIterations)
		{
			int[] a = new int[temperory];
			int[] b = new int[temperory];
			Random r = new Random();
			Random s = new Random();
			int u = r.nextInt(noOfNodes);
			int v = s.nextInt(noOfNodes);
			a[tempCounter] = u;
			b[tempCounter] = v;
			if (u != v && nodeInformation[u][v] != 1 && nodeInformation[v][u] != 1)
			{
				nodeInformation[u][v] = 1;
				nodeInformation[v][u] = 1;
				tempCounter = tempCounter + 1;
				nodeAdjacencyMatrix[u][v] = 1;
				nodeAdjacencyMatrix[v][u] = 1;
			}
			else if (u == v)
			{
				nodeInformation[u][v] = 0;
				nodeInformation[v][u] = 0;
				nodeAdjacencyMatrix[u][v] = 0;
				nodeAdjacencyMatrix[v][u] = 0;
			}
		}
		System.out.println();
	}
	
	
	private static void valueInitializing()
	{
		nodeInformation = new int[noOfNodes][noOfNodes];
		nodeAdjacencyMatrix = new int[noOfNodes][noOfNodes];
		listMA = new int[noOfNodes][2];
		edgeCntr = new int[noOfNodes][noOfNodes];
		for (int a = 0; a < noOfNodes; a++)
		{
			for (int b = 0; b < noOfNodes; b++)
			{
				nodeInformation[a][b] = 0;
				nodeAdjacencyMatrix[a][b] = 0;
			}
		}
	}
	/* A function to find the minimum cut */
	public static int calcMinCut()
	{
		/* Call the MaxAdjOrder function to perform MA ordering */
		int degree = findMaxAdjOrder(NagamochiIbaraki.minNodeCnt);
		if (degree == 0)
		{
			return 0;
		}
		/* Recursive call to Minimum cut as long as there are more than 2 nodes */
		if (NagamochiIbaraki.minNodeCnt > 2)
		{
			return min(degree, calcMinCut());
		}
		else
		{
			return NagamochiIbaraki.nodeInformation[1][1];
		}
	}

	/* A function to perform MA ordering */
	public static int findMaxAdjOrder(int counter)
	{
		int deg = -1;
		for (int a = 0; a < NagamochiIbaraki.minNodeCnt; a++)
		{
			for (int b = 0; b < 2; b++)
			{
				listMA[a][b] = -1;
			}
		}
		if (cnt == 0)
		{
			listMA[0][0] = 0;
			listMA[0][1] = 0;
		}
		for (int n = 1; n < NagamochiIbaraki.minNodeCnt; n++)
		{
			idx = n;
			edgeCntr = new int[NagamochiIbaraki.minNodeCnt][NagamochiIbaraki.minNodeCnt];
			for (int i = 0; listMA[i][0] != -1; i++)
			{
				for (int j = 0; j < NagamochiIbaraki.minNodeCnt; j++)
				{
					if (j != listMA[i][0]
							&& nodeInformation[j][listMA[i][0]] >= 1)
						edgeCntr[j][listMA[i][0]] = nodeInformation[j][listMA[i][0]];
				}
			}
			max(edgeCntr);
		}
		temporaryNodes = new int[NagamochiIbaraki.minNodeCnt][NagamochiIbaraki.minNodeCnt];
		for (int k = 0; k < NagamochiIbaraki.minNodeCnt; k++)
		{
			for (int m = 0; m < NagamochiIbaraki.minNodeCnt; m++)
			{
				temporaryNodes[k][m] = nodeInformation[k][m];
			}
		}
		node[0][0] = listMA[NagamochiIbaraki.minNodeCnt - 2][0];
		node[0][1] = listMA[NagamochiIbaraki.minNodeCnt - 2][1];
		node[1][0] = listMA[NagamochiIbaraki.minNodeCnt - 1][0];
		node[1][1] = listMA[NagamochiIbaraki.minNodeCnt - 1][1];
		/* Check if graph is disconnected */
		for (int k = 0; k < NagamochiIbaraki.minNodeCnt; k++)
		{
			if (listMA[k][1] == -1)
				deg = 0;
		}
		if (deg != 0)
			deg = node[1][1];
		NagamochiIbaraki.minNodeCnt = NagamochiIbaraki.minNodeCnt - 1;
		reconstructMatrix();
		return deg;
	}

	/* A function which gives the minimum of 2 integers */
	public static int min(int a, int b)
	{
		if (a < b)
		{
			return a;
		}
		else
		{
			return b;
		}
	}

	private static void reconstructMatrix()
	{
		int smallNo;
		int largeNo;
		/*
		 * Re-constructing the edge connectivity matrix after contracting the
		 * last 2 nodes in the MA ordering
		 */
		nodeInformation = new int[NagamochiIbaraki.minNodeCnt][NagamochiIbaraki.minNodeCnt];
		for (int a = 0; a < NagamochiIbaraki.minNodeCnt; a++)
		{
			for (int b = 0; b < NagamochiIbaraki.minNodeCnt; b++)
			{
				nodeInformation[a][b] = -1;
			}
		}
		if (node[0][0] > node[1][0])
		{
			largeNo = node[0][0];
			smallNo = node[1][0];
		}
		else
		{
			largeNo = node[1][0];
			smallNo = node[0][0];
		}
		for (int i = 0; i < NagamochiIbaraki.minNodeCnt; i++)
		{
			for (int j = 0; j < NagamochiIbaraki.minNodeCnt; j++)
			{
				if (j < smallNo && j < largeNo && nodeInformation[i][j] == -1)
				{
					nodeInformation[i][j] = temporaryNodes[i][j];
					nodeInformation[j][i] = temporaryNodes[i][j];
				}
				else if (j > smallNo && j < largeNo && nodeInformation[i][j] == -1)
				{
					nodeInformation[i][j] = temporaryNodes[i][j];
					nodeInformation[j][i] = temporaryNodes[i][j];
				}
				else if (j > smallNo && j >= largeNo && nodeInformation[i][j] == -1)
				{
					nodeInformation[i][j] = temporaryNodes[i][j + 1];
					nodeInformation[j][i] = nodeInformation[i][j];
				}
				else if (i == smallNo)// && Node_Details[i][j]==-1)
				{
					nodeInformation[i][j] = temporaryNodes[i][j] + temporaryNodes[largeNo][j];
					nodeInformation[j][i] = nodeInformation[i][j];
				}
				else if (j == smallNo)// && Node_Details[i][j]==-1)
				{
					nodeInformation[i][j] = temporaryNodes[i][j] + temporaryNodes[i][largeNo];
					nodeInformation[j][i] = nodeInformation[i][j];
				}
			}
		}
	}
	/* A function to compute the maximum element in an array */
	public static void max(int[][] array)
	{
		int[][] arr = new int[NagamochiIbaraki.minNodeCnt][NagamochiIbaraki.minNodeCnt];
		for (int i = 0; i < NagamochiIbaraki.minNodeCnt; i++)
		{
			for (int j = 0; j < NagamochiIbaraki.minNodeCnt; j++)
			{
				arr[i][j] = array[i][j];
			}
		}
		int[] sum = new int[NagamochiIbaraki.minNodeCnt];
		for (int k = 0; k < NagamochiIbaraki.minNodeCnt; k++)
		{
			for (int s = 0; s < NagamochiIbaraki.minNodeCnt; s++)
			{
				sum[k] += edgeCntr[k][s];
			}
		}
		for (int i = 0; i < NagamochiIbaraki.minNodeCnt; i++)
		{
			int a = listMA[i][0];
			if (a != -1)
				sum[a] = 0;
		}
		int max = sum[0]; // start with the first value
		int ind = -1;
		for (int i = 1; i < NagamochiIbaraki.minNodeCnt; i++)
		{
			if (sum[i] > max)
			{
				max = sum[i]; // new maximum
			}
		}
		for (int chk = 0; chk < NagamochiIbaraki.minNodeCnt; chk++)
		{
			if (sum[chk] == max)
			{
				int i = 0;
				int flag = 0;
				while (listMA[i][0] != -1)
				{
					if (chk == listMA[i][0])
					{
						flag = 1;
						break;
					}
					i++;
				}
				if (flag == 0)
				{
					ind = chk;
					break;
				}
			}
		}
		/* Updating the MA order list by the next element */
		listMA[idx][0] = ind;
		listMA[idx][1] = max;
	}
}
