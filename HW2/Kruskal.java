import java.util.*;

public class Kruskal{

    public static WGraph kruskal(WGraph g){

        /* Fill this method (The statement return null is here only to compile) */
        WGraph aGraph = new WGraph();
        DisjointSets aSet = new DisjointSets(g.getNbNodes());
        ArrayList<Edge> sortedEdges = new ArrayList<>(g.listOfEdgesSorted());
        int i = 0;
        while (i < sortedEdges.size()) {
            if(IsSafe(aSet,sortedEdges.get(i))) {
                aSet.union(sortedEdges.get(i).nodes[0],sortedEdges.get(i).nodes[1]);
                aGraph.addEdge(sortedEdges.get(i));
            }
            i++;
        }
        return aGraph;
    }

    public static Boolean IsSafe(DisjointSets p, Edge e){

        /* Fill this method (The statement return 0 is here only to compile) */
        return p.find(e.nodes[0]) != p.find(e.nodes[1]);
    
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);

   } 
}
