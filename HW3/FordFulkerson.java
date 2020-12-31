//No collaborator

import java.util.*;
import java.io.File;

public class FordFulkerson {

	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> path = new ArrayList<Integer>();
		/* YOUR CODE GOES HERE*/
		ArrayList<Integer> checked = new ArrayList<>();
		Integer current = source;
		path.add(source);
		while (!destination.equals(current)) {
			int children = 0;
			while (path.contains(children) || checked.contains(children)
					|| ((graph.getEdge(current, children) != null) && graph.getEdge(current, children).weight == 0)
					|| ((graph.getEdge(current, children) == null) && (children < graph.getNbNodes()))) {
				children += 1;
			}
			if (children == graph.getNbNodes()) {
				if (path.size() == 1) {
					ArrayList<Integer> empty = new ArrayList<>();
					return empty;
				} else {
					checked.add(path.remove(path.size() - 1));
					current = path.get(path.size() - 1);
				}
			} else {
				path.add(children);
				current = children;
			}
		}
		return path;
	}



	public static String fordfulkerson( WGraph graph){
		String answer="";
		int maxFlow;
		
		/* YOUR CODE GOES HERE	*/
		WGraph resi = new WGraph();
		int nbNodes = graph.getNbNodes();
		int graphNodes[][] = new int[nbNodes][nbNodes];
		for (int i = nbNodes - 1; i >= 0; i--) {
			for (int j = nbNodes - 1; j >= 0; j--) {
				if(graph.getEdge(i, j)!= null && graph.getEdge(i, j).weight !=0){
					resi.addEdge((new Edge(i,j,graph.getEdge(i,j).weight)));
				}
			}
		}
		ArrayList<Integer> p = pathDFS(graph.getSource(), graph.getDestination(), resi);
		while (true) {
			int pSize = p.size();
			if (pSize == 0) break;
			int minWeight = resi.getEdge(p.get(0), p.get(1)).weight;
			for (int i = pSize - 2; i >= 0; i--) {
				if (!(resi.getEdge(p.get(i), p.get(i + 1)) == null ||
						resi.getEdge(p.get(i), p.get(i + 1)).weight >= minWeight)) {
					minWeight = resi.getEdge(p.get(i), p.get(i + 1)).weight;
				}
			}
			for (int i = pSize - 2; i >= 0; i--) {
				if (graph.getEdge(p.get(i), p.get(i + 1)) == null) {
					graphNodes[p.get(i + 1)][p.get(i)] -= minWeight;
				} else {
					graphNodes[p.get(i)][p.get(i + 1)] += minWeight;
				}
			}
			for (int i = 0; i < nbNodes; i++) {
				for (int j = 0; j < nbNodes; j++) {
					if (graph.getEdge(i, j) == null) {
						if (graph.getEdge(j, i) == null) {
							if (resi.getEdge(i, j) == null) {
								resi.addEdge(new Edge(i, j, 0));
							} else {
								resi.setEdge(i, j, 0);
							}
						} else {
							if (resi.getEdge(i, j) == null) {
								resi.addEdge(new Edge(i, j, graphNodes[j][i]));
							} else {
								resi.setEdge(i, j, graphNodes[j][i]);
							}
						}
					} else {
						if (resi.getEdge(i, j) == null) {
							int w = graph.getEdge(i, j).weight - graphNodes[i][j];
							resi.addEdge(new Edge(i, j, w));
						} else {
							int w = graph.getEdge(i, j).weight - graphNodes[i][j];
							resi.setEdge(i, j, w);
						}
					}
				}
			}
			p = pathDFS(graph.getSource(), graph.getDestination(), resi);
		}
		for (int i = nbNodes - 1; i >= 0; i--) {
			for (int j = nbNodes - 1; j >= 0; j--) {
				if (graph.getEdge(i, j) != null) {
					graph.setEdge(i, j, graphNodes[i][j]);
				}
			}
		}
		maxFlow = Arrays.stream(graphNodes[resi.getSource()], 0, nbNodes).sum();
		answer += maxFlow + "\n" + graph.toString();
		System.out.println(answer);
		System.out.println("*********************");
		return answer;
	}
	

	 public static void main(String[] args){
		 String file = args[0];
		 File f = new File(file);
		 WGraph g = new WGraph(file);
	         System.out.println(fordfulkerson(g));
	 }
}

