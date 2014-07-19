/*
 * This source code is designed and written by Saideep Sambaraju
 * It is to be used for non commercial purposes only
 * DO NOT copy this code without permission from the author
 * Contact Info : 408-203-0492
 */

package aatnproject2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author esamsai
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    // a random number generator
      private static  Random rand = new Random();
    public static void main(String[] args) {
        // TODO code application logic here
        // number of vertices
        int n = 4;
        // the data structures for graphing purposes
        ArrayList<Double> densities = new ArrayList<>();
        ArrayList<Integer> LambdaG = new ArrayList<>();
        // do the experiment for m values 60..600
        for(int m = 8 ; m <= 8 ; m++){
            Graph network = new Graph(n);
            int edges = m;
            // allNodes is a HashMap containing all the nodes present in a network along with their degrees
            ArrayList<Integer> allNodes = new ArrayList<>();
            for(int i = 0 ; i < n ; i++)
                allNodes.add(i);
            // generate the matrix according to specifications
            while(edges>0){
                int i = Math.abs(rand.nextInt())%n;
                int j = Math.abs(rand.nextInt())%n;
                if(i!=j){
                    edges--;
                   // allNodes.put(i, allNodes.get(i) + 1);
                    //allNodes.put(j, allNodes.get(j) + 1);
                    network.adjMatrix[i][j]++;
                    network.adjMatrix[j][i]++;
                }
            }
            
            printMatrix(network.adjMatrix, n);
            System.out.println(allNodes);
            //printHashMap(allNodes);
            // Create MA ordering
            
            ArrayList<Integer> MA = new ArrayList<>();
            createMAOrder(MA, network.adjMatrix, allNodes);
            System.out.println("MA Ordering"+MA);
            
        }
        
    }

    // a debugging utility for printing out matrices
    private static void printMatrix(int[][] adjMatrix, int V) {
        for(int i = 0 ; i < V ; i++){
            for(int j = 0 ; j < V; j++){
                System.out.print(adjMatrix[i][j]+"\t");
            }
            System.out.println("");
        }
        
    }

    // debugging utility for printing HashMap 
    private static void printHashMap(HashMap<Integer, Integer> allNodes) {
        for(Integer i : allNodes.keySet()){
            System.out.println(i+" : "+allNodes.get(i));
        }    
    
    }

    

    private static void createMAOrder(ArrayList<Integer> MA, int[][] adjMatrix, ArrayList<Integer>allNodes) {
        
        int v1 = allNodes.get(Math.abs(rand.nextInt())%allNodes.size());
        MA.add(v1);
        for(int i = 0 ; i < allNodes.size() - 1 ; i++){
            int sumOfDegreeMA = 0; // we start of my summing up the edges to MA for each of remaining vertices
            int[] degVectToMA = new int[allNodes.size()];
            int max = 0;
            int maxind = 0;
            for(int j = 0 ; j < allNodes.size() ; j++){
                int jele = allNodes.get(j);
                if(MA.contains(jele))
                    continue;
                for(Integer k : MA){
                    degVectToMA[j] += adjMatrix[jele][k];
                    if(degVectToMA[j] > max)
                    {
                        maxind = jele;
                        max = degVectToMA[j];
                    }
                }
            }
            MA.add(maxind);
        }
        
    }
    
}
