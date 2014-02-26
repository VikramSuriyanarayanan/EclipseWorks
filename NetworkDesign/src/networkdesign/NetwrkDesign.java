package networkdesign;

import java.io.*;
import java.util.*;

import org.jgraph.JGraph;
import org.jgrapht.graph.*;
import org.jgrapht.alg.BellmanFordShortestPath;
import org.jgrapht.ext.JGraphModelAdapter;
import javax.swing.*;

public class NetwrkDesign {

	private SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>  graph;
	private static List<Integer> randEdgeList = new ArrayList<Integer>();
	private static Set<Integer> randEdgeSet = new LinkedHashSet<Integer>();
	private int GRAPH_SIZE;
	private int K ;
	private double trafficDemandMatrix[][];
	private java.util.List shortestPath[][];
	private java.util.List<DefaultWeightedEdge> shortestPathEdges[][];
	private BellmanFordShortestPath<SimpleWeightedGraph, String> bellmanFord[] ;
	private int capacityOfLinks[][];


	public NetwrkDesign(int GRAPH_SIZE,int K)
	{
		this.GRAPH_SIZE = GRAPH_SIZE;
		this.K = K;
		trafficDemandMatrix = new double[GRAPH_SIZE][GRAPH_SIZE];
		bellmanFord= new BellmanFordShortestPath[GRAPH_SIZE];
		shortestPath=new java.util.List[GRAPH_SIZE][GRAPH_SIZE];
		shortestPathEdges=new java.util.List[GRAPH_SIZE][GRAPH_SIZE];
		capacityOfLinks = new int[GRAPH_SIZE][GRAPH_SIZE];
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) {

		int N=0;
		int K=0;

		System.out.println("***************** STEP 1 ****************************");
		System.out.println("Getting value of N and K from User");
		System.out.println("Enter the no. of Nodes, N: ");
		Scanner scanner = new Scanner(System.in);

		try{	
			N = scanner.nextInt();

			do{

				System.out.println("Enter a value between 3 to 20 for K: ");
				K = scanner.nextInt();

				if( K > 2 && K < 21)
					break;
				else
					System.out.println("Please enter a value within range specified");
			}while(true);
		}
		catch(Exception e){
			e.printStackTrace();
		}

		scanner.close();

		System.out.println("***************** STEP 2 ****************************");
		System.out.println("Creating Graph");

		NetwrkDesign nd = new NetwrkDesign(N,K);
		nd.graph = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

		for (int i = 0; i < nd.GRAPH_SIZE; i++) {
			nd.graph.addVertex(Integer.toString(i));
		}

		java.util.List<String  > vertices = new ArrayList<String  >(nd.graph.vertexSet());

		for (int source = 0; source < nd.GRAPH_SIZE; source++) {

			for (int target = 0; target <  nd.GRAPH_SIZE; target++) {
				if (source != target) {
					DefaultWeightedEdge e = nd.graph.addEdge(vertices.get(source), vertices.get(target));
				}
			}
		}

		System.out.println("***************** STEP 3 ****************************");
		System.out.println("Generating Aij :");



		do{
			Random rand = new Random();
			int randedge = rand.nextInt(N) ;
			randEdgeSet.add(randedge);
		}while(randEdgeSet.size() < K);

		for (int i = 0; i < nd.GRAPH_SIZE; i++){
			int nextval,counter = 0;
			Iterator<Integer> cnt = randEdgeSet.iterator();
			int j = 0;	
			while(cnt.hasNext()|| (j<N)){

				if(cnt.hasNext()){
					nextval = cnt.next();

					if(i != nextval){
						if(counter < K){	
							DefaultWeightedEdge e = nd.graph.getEdge(vertices.get(i), vertices.get(nextval));
							int edgeWeight = 1;
							nd.graph.setEdgeWeight(e, edgeWeight);
							counter++;
							System.out.println("EDGE e is "+ e+"weight:"+ edgeWeight);
						}
					}
				}

				if((i !=j)&& (!(randEdgeSet.contains(j)))){
					DefaultWeightedEdge e = nd.graph.getEdge(vertices.get(i), vertices.get(j));
					int edgeWeight = 500;
					nd.graph.setEdgeWeight(e, edgeWeight);
					System.out.println("EDGE e is "+ e+"weight:"+ edgeWeight);
					j++;	
				}
				else{
					j++;
				}

			}	
		}

		System.out.println("***************** STEP 4 ****************************");
		System.out.println("Generating Bij :");

		Set<DefaultWeightedEdge> allEdges = nd.graph.edgeSet();
		Iterator<DefaultWeightedEdge> itAllEdges = allEdges.iterator();
		while(itAllEdges.hasNext())
		{
			DefaultWeightedEdge e = itAllEdges.next();
			String sourceVertex = nd.graph.getEdgeSource(e);
			String targetVertex = nd.graph.getEdgeTarget(e);
			Random rand = new Random();
			int edgeWeigh = rand.nextInt(6) ;
			nd.trafficDemandMatrix[Integer.valueOf(vertices.get(Integer.valueOf(sourceVertex)))][Integer.valueOf(vertices.get(Integer.valueOf(targetVertex)))]
					= edgeWeigh;
			//System.out.println("traffic demand :"+sourceVertex+" to "+targetVertex+" --> "+
					//+nd.trafficDemandMatrix[Integer.valueOf(vertices.get(Integer.valueOf(sourceVertex)))][Integer.valueOf(vertices.get(Integer.valueOf

							//(targetVertex)))]);
			//System.out.println("edge: "+e+" weight: "+nd.graph.getEdgeWeight(e) );
		}

		System.out.println("***************** STEP 5 ****************************");
		System.out.println("Calculating the SHORTEST PATH using BELLMAN FORD Alogrithm :");

		for(int count =0 ; count < nd.GRAPH_SIZE ; count++)
		{
			nd.bellmanFord[count] = new BellmanFordShortestPath(nd.graph,Integer.toString(count));
			String startVertex = Integer.toString(count);
			for(int inner_count = 0 ; inner_count < nd.GRAPH_SIZE ; inner_count++)
			{
				if(count != inner_count)
				{
					String endVertex = Integer.toString(inner_count);
					nd.shortestPath[count][inner_count] = nd.bellmanFord[count].findPathBetween(nd.graph, startVertex, endVertex);
					nd.shortestPathEdges[count][inner_count]= nd.bellmanFord[count].findPathBetween(nd.graph, startVertex, endVertex);
					//System.out.println("Shortest path:"+count+" to "+inner_count+": " + nd.shortestPath[count][inner_count]);
				}
			}
		}

		System.out.println("***************** STEP 6 ****************************");

		for(int o=0;o<nd.GRAPH_SIZE;o++)
		{
			for(int in=0;in<nd.GRAPH_SIZE;in++)
			{
				if(o != in)
				{
					Iterator<DefaultWeightedEdge> itPathEdges = nd.shortestPathEdges[o][in].iterator();
					while(itPathEdges.hasNext())
					{

						DefaultWeightedEdge e = itPathEdges.next();
						String sourceVertex = nd.graph.getEdgeSource(e);
						String targetVertex = nd.graph.getEdgeTarget(e);
						nd.capacityOfLinks[Integer.valueOf(vertices.get(Integer.valueOf(sourceVertex)))][Integer.valueOf(vertices.get(Integer.valueOf(targetVertex)))]
								+= nd.trafficDemandMatrix[o][in];

					}
				}
			}
		}

		System.out.println("Capacities of links:");
		for(int o=0;o<nd.GRAPH_SIZE;o++)
		{
			for(int in=0;in<nd.GRAPH_SIZE;in++)
			{
				System.out.println("["+o+"]"+"["+in+"]->"+nd.capacityOfLinks[o][in]);
			}
		}

		//Density Calculation
		double bandwidth=0;

		for(int out1=0;out1<nd.GRAPH_SIZE;out1++)
		{
			for(int in1=0;in1<nd.GRAPH_SIZE;in1++)
			{
				if(out1 != in1)
				{

					Iterator<DefaultWeightedEdge> itPathEdges1 = nd.shortestPathEdges[out1][in1].iterator();
					while(itPathEdges1.hasNext())
					{

						DefaultWeightedEdge e = itPathEdges1.next();
						if(nd.graph.getEdgeWeight(e) == 1.0){
							bandwidth++;
						}

					}
				}
			}	
		}

		System.out.println("Capacity val is "+ bandwidth);
		System.out.println("Density val is "+ ((bandwidth)/(N*(N-1))));

		System.out.println("***************** STEP 7 ****************************");
		//Total cost calculation
		double totalCost = 0.0;
		double temp = 0.0;
		int totalWeight = 0;

		for(int o=0;o<nd.GRAPH_SIZE;o++)
		{
			for(int in=0;in<nd.GRAPH_SIZE;in++)
			{
				if( o != in)
				{
					totalWeight = 0;
					Iterator<DefaultWeightedEdge> itPathEdges = nd.shortestPathEdges[o][in].iterator();
					while(itPathEdges.hasNext())
					{

						DefaultWeightedEdge e = itPathEdges.next();
						int weightEdge = (int)nd.graph.getEdgeWeight(e);
						String sourceVertex = nd.graph.getEdgeSource(e);
						String targetVertex = nd.graph.getEdgeTarget(e);
						if(nd.capacityOfLinks[Integer.valueOf(vertices.get(Integer.valueOf(sourceVertex)))][Integer.valueOf(vertices.get(Integer.valueOf(targetVertex)))]
								!=0)
							totalWeight += weightEdge;

					}//while
					temp += totalWeight * nd.trafficDemandMatrix[o][in];
				}//if
			}
		}
		totalCost=temp/100;


		System.out.println("Total cost for building this network path: "+ totalCost);


		System.out.println("***************** STEP 8 ****************************");
		//Display the graph to the user
		SimpleGraph displayGraph = new SimpleGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

		for (int i = 0; i < nd.GRAPH_SIZE; i++) {
			displayGraph.addVertex(Integer.toString(i));
		}

		java.util.List<String  > displayVertices = new ArrayList<String>(displayGraph.vertexSet());

		for (int source = 0; source < nd.GRAPH_SIZE; source++) {

			for (int target = source + 1 ; target <  nd.GRAPH_SIZE; target++) {

				if(nd.capacityOfLinks[source][target] != 0)
				{
					displayGraph.addEdge(displayVertices.get(source), displayVertices.get(target));
				}
				if(nd.capacityOfLinks[target][source] != 0)
				{
					displayGraph.addEdge(displayVertices.get(target), displayVertices.get(source));
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




	}

}
