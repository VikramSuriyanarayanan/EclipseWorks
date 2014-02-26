package ProjTwoForMe;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/* Initializing of variables */
public class Nagamochi_Ibaraki_1 extends Nagamochi_Ibaraki_2
{
	public static int n_nodes;
	public static int m_edges;
	public static int[][] nodeinfo;
	public static int[][] matrix;
	public static int[][] nodeinfo1;
	public static int[][] Donas = new int[2][2];
	public static int indx = 0;
	public static int[][] mal;
	public static int count = 0;
	public static int[][] edgecount;
	public static int remDonas = 0;
	public static int res;
	public static int MIN_NODES_CNT = 0;
	public static int edge = 0;
	public static int grapDeg = 0;
	public static void main(String args[])
	{
		try
		{
			/* Input from user */
			System.out.println("Enter the number of Nodes");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			n_nodes = Integer.parseInt(in.readLine());
			System.out.println("n_nodes:");
			System.out.println(Nagamochi_Ibaraki_1.n_nodes);

			MIN_NODES_CNT = n_nodes;
			System.out.println("Enter the number of edge");
			BufferedReader in1 = new BufferedReader(new InputStreamReader(
					System.in));
			m_edges = Integer.parseInt(in1.readLine());
			System.out.println("m_edges:");
			System.out.println(Nagamochi_Ibaraki_1.m_edges);
			var_initialize();
			create_matrix();
			/* Compute Average Deg*/
			float avgDeg = (float) (2 * m_edges) / n_nodes;
			/* Call function to find the minimum cut of the graph */
			int minimum_cut = find_min_cut();
			/* if the graph is disconnected, minimum cut must be 0 */
			if (minimum_cut == 0)
			{
				System.out.println("The original graph is a disconnected graph");
				/* Print the output values*/
				System.out.println("Nodes " + n_nodes);
				System.out.println("edge " + m_edges);
				System.out.println("Average Deg of the Graph is : + avgDeg");
						System.out.println("Minimum Cut is " + minimum_cut); }
			else
			{
				int num_critical_edge = find_critical_edge(minimum_cut);
				/* Displaying the inputs and outputs to user */
				System.out.println("Nodes " + n_nodes);
				System.out.println("edge " + m_edges);
				System.out.println("Average Deg of the Graph is :"
						+ avgDeg);
				System.out.println("Minimum Cut is " + minimum_cut);
				System.out.println("Number of Critical edge are : + num_critical_edge");
			}
		} 
		catch (IOException e)
		{
			System.out.println("Error " + e.getMessage());
		}
	}
}
/* Program to find the minimum cut and critical edge */

