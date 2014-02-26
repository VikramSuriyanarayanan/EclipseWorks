package ProjTwoForMe;

import java.util.*;
public class Nagamochi_Ibaraki_2 {
	/* Find the critical edges */
	public static int find_critical_edge(int minimum_cut)
	{
		int critical_cnt = 0;
		int temp_min_cut = 0; 
		for (int i = 0; i < Nagamochi_Ibaraki_1.matrix.length; i++)
		{
			for (int j = 0; j < Nagamochi_Ibaraki_1.matrix.length; j++)
			{
				if (1 == Nagamochi_Ibaraki_1.matrix[i][j] && (i != j) && ( j > i))
				{
					Nagamochi_Ibaraki_1.matrix[i][j] = 0;
					Nagamochi_Ibaraki_1.matrix[j][i] = 0;
					makecopyOfmatrix();
					temp_min_cut = find_min_cut();
					if (temp_min_cut < minimum_cut)
					{
						critical_cnt++;
					}
					Nagamochi_Ibaraki_1.matrix[i][j] = 1;
					Nagamochi_Ibaraki_1.matrix[j][i] = 1;
				}
			}
		}
		return critical_cnt;
	}
	/* Copy the values from one array to another */
	public static void makecopyOfmatrix()
	{
		Nagamochi_Ibaraki_1.nodeinfo = new int[Nagamochi_Ibaraki_1.matrix.length][Nagamochi_Ibaraki_1.matrix.length];
		Nagamochi_Ibaraki_1.MIN_NODES_CNT = Nagamochi_Ibaraki_1.n_nodes;
		Nagamochi_Ibaraki_1.mal = new int[Nagamochi_Ibaraki_1.n_nodes][2];
		Nagamochi_Ibaraki_1.count = 0; System.arraycopy(Nagamochi_Ibaraki_1.nodeinfo,0,Nagamochi_Ibaraki_1.matrix,0,Nagamochi_Ibaraki_1.nodeinfo.length); }
	/* Create the matrix to store the nodes and edges */
	public static void create_matrix()
	{
		int temp = Nagamochi_Ibaraki_1.m_edges;
		int temp_iterations = Nagamochi_Ibaraki_1.m_edges;
		int temp_cnt = 0;
		while (temp_cnt < temp_iterations)
		{
			int[] a = new int[temp];
			int[] b = new int[temp];
			Random r = new Random();
			Random s = new Random();
			int u = r.nextInt(Nagamochi_Ibaraki_1.n_nodes);
			int v = s.nextInt(Nagamochi_Ibaraki_1.n_nodes);
			a[temp_cnt] = u;
			b[temp_cnt] = v;
			if (u != v && Nagamochi_Ibaraki_1.nodeinfo[u][v] != 1 && Nagamochi_Ibaraki_1.nodeinfo[v][u] != 1)
			{
				Nagamochi_Ibaraki_1.nodeinfo[u][v] = 1;
				Nagamochi_Ibaraki_1.nodeinfo[v][u] = 1;
				temp_cnt = temp_cnt + 1;
				Nagamochi_Ibaraki_1.matrix[u][v] = 1;
				Nagamochi_Ibaraki_1.matrix[v][u] = 1;
			}
			else if (u == v)
			{
				Nagamochi_Ibaraki_1.nodeinfo[u][v] = 0;
				Nagamochi_Ibaraki_1.nodeinfo[v][u] = 0;
				Nagamochi_Ibaraki_1.matrix[u][v] = 0;
				Nagamochi_Ibaraki_1.matrix[v][u] = 0;
			}
		}
		System.out.println();
		for (int i = 0; i < Nagamochi_Ibaraki_1.n_nodes; i++)
		{
			Nagamochi_Ibaraki_1.nodeinfo[i][i] = 1;
			Nagamochi_Ibaraki_1.matrix[i][i] = 1;
		}
	}
	public static void var_initialize()
	{
		System.out.println("Into initializeVariables");
		Nagamochi_Ibaraki_1.nodeinfo = new int[Nagamochi_Ibaraki_1.n_nodes][Nagamochi_Ibaraki_1.n_nodes];
		Nagamochi_Ibaraki_1.matrix = new int[Nagamochi_Ibaraki_1.n_nodes][Nagamochi_Ibaraki_1.n_nodes];
		Nagamochi_Ibaraki_1.mal = new int[Nagamochi_Ibaraki_1.n_nodes][2];
		Nagamochi_Ibaraki_1.edgecount = new int[Nagamochi_Ibaraki_1.n_nodes][Nagamochi_Ibaraki_1.n_nodes];
		for (int a = 0; a < Nagamochi_Ibaraki_1.n_nodes; a++)
		{
			for (int b = 0; b < Nagamochi_Ibaraki_1.n_nodes; b++)
			{
				Nagamochi_Ibaraki_1.nodeinfo[a][b] = 0;
				Nagamochi_Ibaraki_1.matrix[a][b] = 0;
			}
		}
	}
	/* Find the minimum cut */
	public static int find_min_cut()
	{
		/* Find the MA ordering of the nodes*/
		int deg = find_MA_order(Nagamochi_Ibaraki_1.MIN_NODES_CNT);
		if (deg == 0)
		{
			return 0;
		}
		/* Recursively call function to compute the minimum cut of the graph*/
		if (Nagamochi_Ibaraki_1.MIN_NODES_CNT > 2)
		{
			return min(deg, find_min_cut());
		}
		else
		{
			return Nagamochi_Ibaraki_1.nodeinfo[1][1];
		}
	}
	/* Find the minimum of two values */
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
	/* Function to find MA ordering of nodes for the graph */
	public static int find_MA_order(int counter)
	{
		int deg = -1;
		for (int a = 0; a < Nagamochi_Ibaraki_1.MIN_NODES_CNT; a++)
		{
			for (int b = 0; b < 2; b++)
			{
				Nagamochi_Ibaraki_1.mal[a][b] = -1;
			}
		}

		if (Nagamochi_Ibaraki_1.count == 0)
		{
			Nagamochi_Ibaraki_1.mal[0][0] = 0;
			Nagamochi_Ibaraki_1.mal[0][1] = 0;
		}
		for (int n = 1; n < Nagamochi_Ibaraki_1.MIN_NODES_CNT; n++)
		{
			Nagamochi_Ibaraki_1.indx = n;
			Nagamochi_Ibaraki_1.edgecount = new int[Nagamochi_Ibaraki_1.MIN_NODES_CNT][Nagamochi_Ibaraki_1.MIN_NODES_CNT];
			for (int i = 0; Nagamochi_Ibaraki_1.mal[i][0] != -1; i++)
			{
				for (int j = 0; j < Nagamochi_Ibaraki_1.MIN_NODES_CNT; j++)
				{
					if (j != Nagamochi_Ibaraki_1.mal[i][0]
							&& Nagamochi_Ibaraki_1.nodeinfo[j][Nagamochi_Ibaraki_1.mal[i][0]] >= 1)
						Nagamochi_Ibaraki_1.edgecount[j][Nagamochi_Ibaraki_1.mal[i][0]] = Nagamochi_Ibaraki_1.nodeinfo[j][Nagamochi_Ibaraki_1.mal[i][0]];
				}
			}
			max(Nagamochi_Ibaraki_1.edgecount);
		}
		Nagamochi_Ibaraki_1.nodeinfo1 = new int[Nagamochi_Ibaraki_1.MIN_NODES_CNT][Nagamochi_Ibaraki_1.MIN_NODES_CNT];
		System.arraycopy(Nagamochi_Ibaraki_1.nodeinfo1,0,Nagamochi_Ibaraki_1.nodeinfo,0,Nagamochi_Ibaraki_1.nodeinfo1.length);
		Nagamochi_Ibaraki_1.Donas[0][0] = Nagamochi_Ibaraki_1.mal[Nagamochi_Ibaraki_1.MIN_NODES_CNT - 2][0];
		Nagamochi_Ibaraki_1.Donas[0][1] = Nagamochi_Ibaraki_1.mal[Nagamochi_Ibaraki_1.MIN_NODES_CNT - 2][1];
		Nagamochi_Ibaraki_1.Donas[1][0] = Nagamochi_Ibaraki_1.mal[Nagamochi_Ibaraki_1.MIN_NODES_CNT - 1][0];
		Nagamochi_Ibaraki_1.Donas[1][1] = Nagamochi_Ibaraki_1.mal[Nagamochi_Ibaraki_1.MIN_NODES_CNT - 1][1];
		/* Check whether the graph is disconnected*/
		for (int k = 0; k < Nagamochi_Ibaraki_1.MIN_NODES_CNT; k++)
		{
			if (Nagamochi_Ibaraki_1.mal[k][1] == -1)
				deg = 0;
		}
		if (deg != 0)
			deg = Nagamochi_Ibaraki_1.Donas[1][1];
		Nagamochi_Ibaraki_1.MIN_NODES_CNT = Nagamochi_Ibaraki_1.MIN_NODES_CNT;
		matrix_reconstruction();
		return deg;
	}
	/* Function for matrix reconstruction */
	public static void matrix_reconstruction()
	{
		int sn;
		int ln;

		/* Reconstruct the matrix by contraction of last two nodes in the graph */
		Nagamochi_Ibaraki_1.nodeinfo = new int[Nagamochi_Ibaraki_1.MIN_NODES_CNT][Nagamochi_Ibaraki_1.MIN_NODES_CNT];
		for (int a = 0; a < Nagamochi_Ibaraki_1.MIN_NODES_CNT; a++)
		{
			for (int b = 0; b < Nagamochi_Ibaraki_1.MIN_NODES_CNT; b++)
			{
				Nagamochi_Ibaraki_1.nodeinfo[a][b] = -1; } }
		if (Nagamochi_Ibaraki_1.Donas[0][0] > Nagamochi_Ibaraki_1.Donas[1][0])
		{
			ln = Nagamochi_Ibaraki_1.Donas[0][0];
			sn = Nagamochi_Ibaraki_1.Donas[1][0];
		}
		else
		{
			ln = Nagamochi_Ibaraki_1.Donas[1][0];
			sn = Nagamochi_Ibaraki_1.Donas[0][0];
		}
		for (int i = 0; i < Nagamochi_Ibaraki_1.MIN_NODES_CNT; i++)
		{
			for (int j = 0; j < Nagamochi_Ibaraki_1.MIN_NODES_CNT; j++)
			{
				if (j < sn && j < ln && Nagamochi_Ibaraki_1.nodeinfo[i][j] == -1)
				{
					Nagamochi_Ibaraki_1.nodeinfo[i][j] = Nagamochi_Ibaraki_1.nodeinfo1[i][j];
					Nagamochi_Ibaraki_1.nodeinfo[j][i] = Nagamochi_Ibaraki_1.nodeinfo1[i][j]; }
				else if (j > sn && j < ln && Nagamochi_Ibaraki_1.nodeinfo[i][j] == -1)
				{
					Nagamochi_Ibaraki_1.nodeinfo[i][j] = Nagamochi_Ibaraki_1.nodeinfo1[i][j];
					Nagamochi_Ibaraki_1.nodeinfo[j][i] = Nagamochi_Ibaraki_1.nodeinfo1[i][j];
				}
				else if (j > sn && j >= ln && Nagamochi_Ibaraki_1.nodeinfo[i][j] == -1)
				{
					Nagamochi_Ibaraki_1.nodeinfo[i][j] = Nagamochi_Ibaraki_1.nodeinfo1[i][j + 1];
					Nagamochi_Ibaraki_1.nodeinfo[j][i] = Nagamochi_Ibaraki_1.nodeinfo1[i][j];
				}
				else if (i == sn)
				{
					Nagamochi_Ibaraki_1.nodeinfo[i][j] = Nagamochi_Ibaraki_1.nodeinfo1[i][j] + Nagamochi_Ibaraki_1.nodeinfo1[ln][j];
					Nagamochi_Ibaraki_1.nodeinfo[j][i] = Nagamochi_Ibaraki_1.nodeinfo1[i][j];
				}
				else if (j == sn)
				{
					Nagamochi_Ibaraki_1.nodeinfo[i][j] = Nagamochi_Ibaraki_1.nodeinfo1[i][j] + Nagamochi_Ibaraki_1.nodeinfo1[i][ln];
					Nagamochi_Ibaraki_1.nodeinfo[j][i] = Nagamochi_Ibaraki_1.nodeinfo[i][j];
				}
			}
		}
	}
	/* Find the maximum element in the array */
	public static void max(int[][] array)
	{
		int[][] arr = new int[Nagamochi_Ibaraki_1.MIN_NODES_CNT][Nagamochi_Ibaraki_1.MIN_NODES_CNT];
		System.arraycopy(arr,0,array,0,arr.length);
		int[] sum = new int[Nagamochi_Ibaraki_1.MIN_NODES_CNT];
		for (int k = 0; k < Nagamochi_Ibaraki_1.MIN_NODES_CNT; k++)
		{
			for (int s = 0; s < Nagamochi_Ibaraki_1.MIN_NODES_CNT; s++)
			{
				sum[k] += Nagamochi_Ibaraki_1.edgecount[k][s];
			}
		}
		for (int i = 0; i < Nagamochi_Ibaraki_1.MIN_NODES_CNT; i++)
		{
			int a = Nagamochi_Ibaraki_1.mal[i][0];
			if (a != -1)
				sum[a] = 0;
		}
		int max = sum[0]; 
		int ind = -1;
		for (int i = 1; i <Nagamochi_Ibaraki_1.MIN_NODES_CNT; i++)
		{
			if (sum[i] > max)
			{
				max = sum[i]; 
			}
		}
		for (int chk = 0; chk < Nagamochi_Ibaraki_1.MIN_NODES_CNT; chk++)
		{
			if (sum[chk] == max)
			{
				int i = 0;
				int flag = 0;
				while (Nagamochi_Ibaraki_1.mal[i][0] != -1)
				{
					if (chk == Nagamochi_Ibaraki_1.mal[i][0])
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
		/* Update the MA ordering matrix with new values */
		Nagamochi_Ibaraki_1.mal[Nagamochi_Ibaraki_1.indx][0] = ind;
		Nagamochi_Ibaraki_1.mal[Nagamochi_Ibaraki_1.indx][1] = max;
	}
}