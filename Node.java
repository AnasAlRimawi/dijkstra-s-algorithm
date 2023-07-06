
import java.util.LinkedHashMap;
import java.util.Set;

public class Node implements Comparable<Node> {
	
	String name;
	
	LinkedHashMap< Node , Integer > neighbors;
	
	int shortestPathCost;
	Node parent;
	
	public Node( String name ) {
		this.name = name;
		neighbors = new LinkedHashMap<Node,Integer>();
	}
	
	public void addEdge( Node neighbor , Integer weight) {
		neighbors.put( neighbor, weight);
	}
	
	public Set<Node> getNeighbors() {
		return neighbors.keySet();
	}
	
	public Integer getWeight( Node neighbor ) {
		return neighbors.get(neighbor);
	}

	@Override
	public int compareTo(Node o) {
		return  Integer.compare(this.shortestPathCost, o.shortestPathCost);
	}
}
