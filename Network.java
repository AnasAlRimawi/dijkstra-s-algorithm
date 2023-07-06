import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Scanner;

import javax.swing.*;
import java.awt.*;


public class Network {
	
	int numOfNodes, numOfEdges;
	Hashtable< String , Node > nodes;
	
	public Network() {
		numOfNodes = 0;
		numOfEdges = 0;
		nodes = new Hashtable<String , Node>();
		
	}
	
	public void addEdge( String fromNode, String toNode, Integer weight ) {
		Node fNode = getAddNode(fromNode);
		Node tNode = getAddNode( toNode );
		fNode.addEdge( tNode , weight);
		numOfEdges ++;
	}

	private Node getAddNode(String fromNode) {
		Node result = nodes.get( fromNode );
		if( result == null ) {
			result = new Node(fromNode);
			nodes.put( fromNode , result);
			numOfNodes ++;
		}
		return result;
	}
	
	/**
	 * You need to implement this function
	 * @param graph
	 */
	public static void visualize(Network graph) {

		DirectedSparseGraph g = new DirectedSparseGraph<>();

		int temp = 0;
		for (Node n : graph.nodes.values()){
			g.addVertex(n.name);
			for (Node neighbour : n.getNeighbors()){
				g.addEdge("edge"+temp,n.name, neighbour.name);
				temp++;
			}
		}

		VisualizationImageServer vs = new VisualizationImageServer<>(new FRLayout<>(g), new Dimension(1800,1000));

		JFrame frame = new JFrame("Graph");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(vs);
		frame.pack();
		frame.setVisible(true);
	}

	public static void applyDijkstra(Network graph, String source) {

		for(Node n: graph.nodes.values()){
			n.shortestPathCost = Integer.MAX_VALUE;
			if(n.name.equals(source)) {
				n.shortestPathCost = 0;
			}
		}

		PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
		priorityQueue.add(graph.nodes.get(source));

		while (!priorityQueue.isEmpty()){

			Node nodeWithMinCost = priorityQueue.poll();

			for (Node neighbor: nodeWithMinCost.getNeighbors()){
				int pathCost = neighbor.shortestPathCost + nodeWithMinCost.getWeight(neighbor);
				if(pathCost < neighbor.shortestPathCost) {
					neighbor.shortestPathCost = pathCost;
					neighbor.parent = nodeWithMinCost;
					priorityQueue.add(neighbor);
				}
			}
		}
	}
	public static void visualizeDij(Network graph) {

		DirectedSparseGraph g = new DirectedSparseGraph<>();

		int temp = 0;
		for (Node n : graph.nodes.values()){
			g.addVertex(n.name);

		}
		for (Node n: graph.nodes.values()){
			if(n.parent != null) {
				g.addEdge("edge" + temp, n.parent.name, n.name);
			}
			temp++;
		}


		VisualizationImageServer vs = new VisualizationImageServer<>(new ISOMLayout<>(g), new Dimension(1200,900));

		JFrame frame = new JFrame("Graph");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(vs);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static Network loadNetwork( String fileName ) throws FileNotFoundException {
		Network result = new Network();
        //parsing a CSV file into the constructor of Scanner class
        Scanner scanner = new Scanner(new File( fileName ));
        //setting space and new lines as delimiter pattern
        scanner.useDelimiter("\\s* \\s*|\n");
        int i = 0;
        while (scanner.hasNext()) {            
        	i++;
        	String fromNode = scanner.next();
        	String toNode = scanner.next();
        	Integer weight = scanner.nextInt();
        	result.addEdge(fromNode, toNode, weight);
        }
        //closes the scanner
        scanner.close();
        System.out.println( "Loaded a network of " + result.numOfNodes 
        		+ " nodes and " + result.numOfEdges + " edges"  );

		return result;
	}
	

}
