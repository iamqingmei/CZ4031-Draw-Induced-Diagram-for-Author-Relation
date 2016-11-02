package dfs;
import java.util.*;

public class DepthFirstSearch {
    private ArrayList<Integer> parents;

    public DepthFirstSearch(int authorid) {
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    // depth first search from v
    public void dfs(int adjacency_matrix[][], Node node) { 
        System.out.print(node.data + "\t"); 
        ArrayList<Node> neighbours=findNeighbours(adjacency_matrix,node); 
        for (int i = 0; i < neighbours.size(); i++) { 
            Node n=neighbours.get(i); 
            if(n!=null && !n.visited) { 
                dfs(adjacency_matrix,n); 
                n.visited=true; 
            } 
        } 
    }
