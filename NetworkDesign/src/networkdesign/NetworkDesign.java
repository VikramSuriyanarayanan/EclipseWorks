package networkdesign;
import org.jgrapht.graph.*;
import java.util.*;
import org.jgrapht.alg.BellmanFordShortestPath;
import java.io.*;
import org.jgraph.JGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import javax.swing.*;

public class NetworkDesign {

	private SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>  graph;
	private int GRAPH_SIZE;
	private int M ;
	private int K ;
	private double trafficDemandMatrix[][];
	private java.util.List shortestPath[][];
	private java.util.List<DefaultWeightedEdge> shortestPathEdges[][];
	private BellmanFordShortestPath<SimpleWeightedGraph, String> bellmanFord[] ;
	private int capacityOfLinks[][];


	public NetworkDesign(int GRAPH_SIZE,int M,int K)
	{
		this.GRAPH_SIZE = GRAPH_SIZE;
		this.M = M;
		this.K = K;
		trafficDemandMatrix = new double[GRAPH_SIZE][GRAPH_SIZE];
		bellmanFord= new BellmanFordShortestPath[GRAPH_SIZE];
		shortestPath=new java.util.List[GRAPH_SIZE][GRAPH_SIZE];
		shortestPathEdges=new java.util.List[GRAPH_SIZE][GRAPH_SIZE];
		capacityOfLinks = new int[GRAPH_SIZE][GRAPH_SIZE];
	}
	public static void main(String[] args) {

		int N=0,M=0,K=0;
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Value of N(number of nodes): ");
			N = Integer.parseInt(br.readLine());
			do {
				System.out.println("Value of M(2 or 4 or 8): ");
				M = Integer.parseInt(br.readLine());
				if( M==2 || M==4 || M==8 )
					break;
			} while(true);

	//		do {
				System.out.println("Value of K(50 or 100 or 150): ");
				K = Integer.parseInt(br.readLine());
				//if( K==50 || K==100 || K==150 )
				//	break;
//			}while(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		NetworkDesign nd = new NetworkDesign(N,M,K);
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
		System.out.println("Assigning Aij value=100 to random K links and Aij=random values from [1,M] to remaining links...");
		Set<DefaultWeightedEdge> randomEdges = nd.graph.edgeSet();
		java.util.List<DefaultWeightedEdge> list = new ArrayList<DefaultWeightedEdge>(randomEdges);
		Collections.shuffle(list);
		// the list is now shuffled
		Set<DefaultWeightedEdge> randomEdgesShuffled = new LinkedHashSet<DefaultWeightedEdge>();
		randomEdgesShuffled.addAll(list);
		Iterator<DefaultWeightedEdge> itRandomEdges = randomEdgesShuffled.iterator();
		int countOfEdges=0;

		while(itRandomEdges.hasNext())
		{
			if(countOfEdges < nd.K)
			{
				DefaultWeightedEdge e = itRandomEdges.next();
				int edgeWeight = 100;
				nd.graph.setEdgeWeight(e, edgeWeight);
				System.out.println("edge: "+e+" weight: "+edgeWeight );
				countOfEdges++;
			}
			else
			{
				DefaultWeightedEdge e = itRandomEdges.next();
				Random rand = new Random();
				int edgeWeight = rand.nextInt(nd.M) + 1;
				nd.graph.setEdgeWeight(e, edgeWeight);
				System.out.println("edge: "+e+" weight: "+edgeWeight );
				countOfEdges++;
			}
		}
		System.out.println("Assigning random Bij values to the links in range [0,M]...");
		Set<DefaultWeightedEdge> allEdges = nd.graph.edgeSet();
		Iterator<DefaultWeightedEdge> itAllEdges = allEdges.iterator();
		while(itAllEdges.hasNext())
		{
			DefaultWeightedEdge e = itAllEdges.next();
			String sourceVertex = nd.graph.getEdgeSource(e);
			String targetVertex = nd.graph.getEdgeTarget(e);
			Random rand = new Random();
			int edgeWeight = rand.nextInt(nd.M+1) ;
			nd.trafficDemandMatrix[Integer.valueOf(vertices.get(Integer.valueOf(sourceVertex)))][Integer.valueOf(vertices.get(Integer.valueOf(targetVertex)))]
					= edgeWeight;
			System.out.println("traffic demand :"+sourceVertex+" to "+targetVertex+" --> "+
					+nd.trafficDemandMatrix[Integer.valueOf(vertices.get(Integer.valueOf(sourceVertex)))][Integer.valueOf(vertices.get(Integer.valueOf

							(targetVertex)))]);
			System.out.println("edge: "+e+" weight: "+nd.graph.getEdgeWeight(e) );
		}
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
					System.out.println("Shortest path:"+count+" to "+inner_count+": ");
					System.out.println(nd.shortestPath[count][inner_count]);
				}
			}
		}
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

					}//while
				}//if
			}
		}

		//display the capacity of links
		System.out.println("Capacities of links:");
		for(int o=0;o<nd.GRAPH_SIZE;o++)
		{
			for(int in=0;in<nd.GRAPH_SIZE;in++)
			{
				System.out.println("["+o+"]"+"["+in+"]->"+nd.capacityOfLinks[o][in]);
			}
		}
		//Total cost calculation
		double totalCost = 0.0;
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
						totalCost += totalWeight * nd.trafficDemandMatrix[o][in];
				}//if
			}
		}

		System.out.println("Total cost for building this network path: "+ totalCost);

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


