import java.io.FileNotFoundException;
import java.util.LinkedHashMap;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		String fileName = "src/Assignment2_Data.csv";
		Network result = Network.loadNetwork(fileName);
		Network.visualize(result);
		Network.applyDijkstra(result,"48");
		Network.visualizeDij(result);



	}

}
