import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

import graph.JGraphAdaptor;

import connection.ConnMgr;


public class MainClass {

	private static ArrayList<Integer> authoridList = new ArrayList<Integer>();
	private static ArrayList<Integer> drawList = new ArrayList<Integer>();
	private static JGraphAdaptor graph = new JGraphAdaptor();

	private static ArrayList<Integer> parents = new ArrayList<Integer>();//for dfs visited
	private static ArrayList<Integer> path = new ArrayList<Integer>();

	public static void main(String[] argv) {
		int totalAuthorNumber = ConnMgr.getInstance().authorCount();
		int k = 0;
		
		HashMap<Integer,Set<Integer>> authorPubMap = new HashMap<Integer,Set<Integer>>();

		// System.out.println("the first author name is: " + getAuthorName(connection,1));
		// System.out.println("the first publication title is: " + getPublicationTitle(connection,1));
		System.out.println("There are " + totalAuthorNumber + " authors.");
		

		// System.out.println("Please input the number of authors:");
		Scanner sc = new Scanner(System.in);
		// k = sc.nextInt();
		// int authorid = 0;
		// for (int i=0;i<k;i++){
		// 	System.out.println("Please input the authorid: (from 1 to " + totalAuthorNumber +")");
		// 	authorid = sc.nextInt();
		// 	authoridList.add(authorid);
		// 	authorPubMap.put(authorid,ConnMgr.getInstance().getPubidSetOfAuthor(authorid));
		// }

		// System.out.println("The " + k + " authors published the following publications: ");
		// // Get a set of the entries
		// Set set = authorPubMap.entrySet();
		// // Get an iterator
		// Iterator it = set.iterator();
		// // Display elements
		// while(it.hasNext()) {
		// 	Map.Entry me = (Map.Entry)it.next();
		// 	System.out.print(me.getKey() + ": ");
		// 	System.out.println(me.getValue());
		// }

		System.out.println("get the collaborator of author 1: " + ConnMgr.getInstance().getCollaborator(1));
		sc.close();

		graph.init();

		for (int e:drawList){
			graph.addVertex(e);
		}

		
		dfsUsingStack(1,513781);

		
		// JFrame frame = new JFrame();
  //       frame.getContentPane().add(graph);
  //       frame.setTitle("4031 Project 2 Task A");
  //       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  //       frame.pack();
  //       frame.setVisible(true);
	}

	// private static void dfs(int authorid, int goal) { 
 //        parents.add(authorid);
 //        if (authorid == goal){
 //    		System.out.println("one path found!"+parents);
 //    		for (int element:parents){
 //    			if (!drawList.contains(element)){
 //    				graph.addVertex(element);
 //    				drawList.add(element);
 //    			}
 //    		}
 //    		int element = -1; 
 //    		int nextElement = -1;
 //    		for (int j=0;j<parents.size();j++){
 //    			element = parents.get(j);
 //    			if (j+1<parents.size()){
 //    				nextElement= parents.get(j+1);
 //    				graph.addEdge(element,nextElement);
 //    			}
 //    		}
 //    		return;
 //    	}
 //    	System.out.println("getting " + authorid + "'s neighbours");
 //        ArrayList<Integer> neighbours=ConnMgr.getInstance().getCollaborator(authorid);  	
 //        for (int i = 0; i < neighbours.size(); i++) { 
 //            int n = neighbours.get(i); 
 //            if(!parents.contains(n)) { 
 //        		dfs(n,goal);
 //            } 
 //        } 
 //        return;
 //    }

    public static void dfsUsingStack(int node, int goal) { 
    	Stack<Integer> stack=new Stack<Integer>();
    	parents.add(node); 
    	stack.add(node);
    	path.add(node);  
    	while (!stack.isEmpty()) { 
    		int element=stack.pop();
    		System.out.println("now is element: " + element);
    		if (element == goal){
    			System.out.println("one path is found: " + path);
    			return;
    		}
    		path.remove((Integer)element);  
    		ArrayList<Integer> neighbours=ConnMgr.getInstance().getCollaborator(node); 
    		for (int i = 0; i < neighbours.size(); i++) { 
    			int n=neighbours.get(i); 
    			if(!parents.contains(n)) { 
    				stack.add(n);
    				parents.add(n); 
    				path.add(n);
    			} 
    		} 
    	} 
    }
}