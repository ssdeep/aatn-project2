/*
 * This source code is designed and written by Saideep Sambaraju
 * It is to be used for non commercial purposes only
 * DO NOT copy this code without permission from the author
 * Contact Info : 408-203-0492
 */

package aatnproject2;

import java.io.FileNotFoundException;
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
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        // number of vertices
        int n = 12;
        // the data structures for graphing purposes
        ArrayList<Double> densities = new ArrayList<>();
        ArrayList<Integer> LambdaG = new ArrayList<>();
        ArrayList<Integer> lowestDegrees = new ArrayList<>();
        // do the experiment for m values 60..600
        for(int m = 60 ; m <= 60 ; m = m+60){
            Graph network = new Graph(n);
            int[] degVector = new int[n];
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
                    degVector[i]++;
                    degVector[j]++;
                }
            }
            network.forceInputs();
            recomputeDegreeVector(degVector, network.adjMatrix);
            //printMatrix(network.adjMatrix, n);
           // System.out.println(allNodes);
            //printHashMap(allNodes);
            // Create MA ordering
            
            //ArrayList<Integer> MA = new ArrayList<>();
            //createMAOrder(MA, network.adjMatrix, allNodes);
            int LG = Lambda(network.adjMatrix, allNodes, n);
            LambdaG.add(LG);
            densities.add(2*m/(double)n);
            // minimum degree
            int minDeg = degVector[0];
            for(int k = 0 ; k < degVector.length ; k++)
                if(minDeg>degVector[k])
                    minDeg = degVector[k];
            lowestDegrees.add(minDeg);
           // System.out.println("MA Ordering"+MA);
            
        }
        System.out.println("Lambda Values:"+LambdaG);
        System.out.println("Densities:"+densities);
        System.out.println("Lowest Degrees:"+lowestDegrees);
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

    private static int Lambda(int[][] adjMatrix, ArrayList<Integer> allNodes, int n) {
        
        if(allNodes.size() == 2){
            return adjMatrix[allNodes.get(0)][allNodes.get(1)];
        }
        
        ArrayList<Integer> MA = new ArrayList<>();
        createMAOrder(MA, adjMatrix, allNodes);// get MA Ordering
        //System.out.println("MA Ordering:"+MA);
        int x = MA.get(MA.size() - 2);
        int y = MA.get(MA.size() - 1);
        int LambdaXY = degreeOf(y, adjMatrix, n);
        merge(x, y, adjMatrix, n);
       // System.out.println("After Merging "+x+" and "+y);
       // printMatrix(adjMatrix, n);
        // remove y
        for(int i = 0 ; i < allNodes.size() ; i++){
            if(allNodes.get(i) == y){
                allNodes.remove(i);
                break;
            }
        }
        
        return Math.min(LambdaXY, Lambda(adjMatrix, allNodes, n));
    
    }

    private static int degreeOf(Integer x, int[][] adjMatrix, int n) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        int degree = 0;
        for(int i = 0 ; i < n ; i++){
            degree+=adjMatrix[x][i];
        }
        
        return degree;
    }

    private static void merge(int x, int y, int[][] adjMatrix, int n) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        adjMatrix[x][y] = 0;
        adjMatrix[y][x] = 0;
        for(int i = 0 ; i < n ; i++){
            if(i!=x){
                adjMatrix[x][i]+=adjMatrix[y][i];
                adjMatrix[i][x] = adjMatrix[x][i];
                adjMatrix[y][i] = 0;
                adjMatrix[i][y] = 0;
            }
        }
        
    }

    private static void recomputeDegreeVector(int[] degVector, int[][] adjMatrix) {
        for(int i = 0 ; i < degVector.length ; i++){
            degVector[i] = 0 ;
            for(int j = 0 ; j < degVector.length; j++)
            {
                degVector[i]+= adjMatrix[i][j];
            }
        }
    
    }
    
}
