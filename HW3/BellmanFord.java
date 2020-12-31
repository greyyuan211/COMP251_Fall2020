//No Collaborator

import java.util.Arrays;
import java.util.stream.IntStream;

public class BellmanFord{

    private int[] distances = null;
    private int[] predecessors = null;
    private int source;

    class BellmanFordException extends Exception{
        public BellmanFordException(String str){
            super(str);
        }
    }

    class NegativeWeightException extends BellmanFordException{
        public NegativeWeightException(String str){
            super(str);
        }
    }

    class PathDoesNotExistException extends BellmanFordException{
        public PathDoesNotExistException(String str){
            super(str);
        }
    }

    BellmanFord(WGraph g, int source) throws NegativeWeightException {
        /* Constructor, input a graph and a source
         * Computes the Bellman Ford algorithm to populate the
         * attributes 
         *  distances - at position "n" the distance of node "n" to the source is kept
         *  predecessors - at position "n" the predecessor of node "n" on the path
         *                 to the source is kept
         *  source - the source node
         *
         *  If the node is not reachable from the source, the
         *  distance value must be Integer.MAX_VALUE
         */
        this.distances = new int[g.getNbNodes()];
        this.predecessors = new int[g.getNbNodes()];
        IntStream.range(1, g.getNbNodes()).forEach(i -> distances[i] = Integer.MAX_VALUE);
        IntStream.range(0, g.getNbNodes() - 1).flatMap(i -> IntStream.range(0, g.getEdges().size()))
                .forEach(j -> this.relax(g.getEdges().get(j).weight,
                        g.getEdges().get(j).nodes[0],
                        g.getEdges().get(j).nodes[1]
                        ));

        int distBefore[] = Arrays.stream(this.distances, 0, g.getNbNodes()).toArray();
        IntStream.range(0, g.getEdges().size())
                .forEach(j -> this.relax(g.getEdges().get(j).weight,
                        g.getEdges().get(j).nodes[0],
                        g.getEdges().get(j).nodes[1]
                        ));
        for (int i = 0; i < g.getNbNodes(); i++) {
            if (distBefore[i] != this.distances[i]) {
                throw new NegativeWeightException("Negative weight exception");
            }

        }

    }

    //lecture 19 p6
    public void relax(int weight, int node1, int node2) {
        if (this.distances[node2] <= (this.distances[node1] + weight)) return;
        this.predecessors[node2] = node1;
        this.distances[node2] = this.distances[node1] + weight;
    }

    public int[] shortestPath(int destination) throws PathDoesNotExistException{
        /*Returns the list of nodes along the shortest path from 
         * the object source to the input destination
         * If not path exists an Error is thrown
         */
        int[] tempath = new int[this.distances.length];
        int[] path;
        int reserve = destination;
        int temp = 0;
        int length = 1;
        while(reserve != 0) {
            tempath[temp] = reserve;
            if (this.distances[reserve] != Integer.MAX_VALUE) {
                reserve = this.predecessors[reserve];
                temp++;
                length++;
            } else {
                throw new PathDoesNotExistException("Path does not exist exception");
            }
        }
        path = new int[length];
        int i = path.length - 1;
        int j = 0;
        while (i > 0) {
            path[i] = tempath[j];
            i--;
            j++;
        }
        return path;

    }

    public void printPath(int destination){
        /*Print the path in the format s->n1->n2->destination
         *if the path exists, else catch the Error and 
         *prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        try{
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}

