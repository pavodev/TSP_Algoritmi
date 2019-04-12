package ch.supsi.tsp_algoritmi;

import java.util.*;

/*
    The class computes responsible of computing a Minimum Spanning Tree with Prim's Algorithm.
    This implementation uses 2 disjointed sets. The first one represents all the nodes that have been included in the
    Minimum Spanning Tree, the second one represents the nodes that are still not hove been chosen.
 */
public class MinimumSpanningTree {
    private int[][] distanceMatrix;

    public MinimumSpanningTree(int[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    /*
        Minimum Spanning Tree computation.
        Once the two initial nodes have been found, the algorithm iterates through all the nodes in the mstNodes set
        until all cities have been included into the Minimum Spanning Tree.
     */
    public void compute(City[] cities){
        List<City> list = new ArrayList<>(Arrays.asList(cities));

        TreeSet<City> mstNodes = new TreeSet<>();
        TreeSet<City> notYetMst = new TreeSet<>(list);

        findShortestEdgeInList(cities, mstNodes, notYetMst);

        while(mstNodes.size() < cities.length){
            nextEdge(cities, mstNodes, notYetMst);
        }

        System.out.println("MINIMUM SPANNING TREE COMPUTED");
    }

    /*
        This method finds the first 2 nodes(cities) to be included into the minimum spanning tree by using distance
        criteria.
     */
    private void findShortestEdgeInList(City[] cities, TreeSet<City> mstNodes, TreeSet<City> notYetMst){
        int shortestEdge = Integer.MAX_VALUE;
        int nodeA = 0;
        int nodeB = 0;
        int distance;

        for(int i = 0; i<cities.length; i++) {
            for (int j = 0; j < cities.length; j++) {
                distance = distanceMatrix[cities[i].getId()][cities[j].getId()];
                if (distance < shortestEdge) {
                    nodeA = i;
                    nodeB = j;

                    shortestEdge = distance;
                }
            }
        }

        mstNodes.add(cities[nodeA]);
        mstNodes.add(cities[nodeB]);

        notYetMst.remove(cities[nodeA]);
        notYetMst.remove(cities[nodeB]);

        cities[nodeA].getCandidateList().add(cities[nodeB]);
        cities[nodeB].getCandidateList().add(cities[nodeA]);
    }

    /*
        Computes one iteration of the algorithm: searches for the shortest edge and updates the candidate lists of the
        chosen nodes.
     */
    private void nextEdge(City[] cities, TreeSet<City> mstNodes, TreeSet<City> notYetMst) {
        int shortestEdge = Integer.MAX_VALUE;

        int nodeA = 0;
        City nodeB = null;

        int distance = 0;

        for (int i = 0; i < mstNodes.size(); i++){
            for(City city: notYetMst){
                distance = distanceMatrix[cities[i].getId()][city.getId()];
                if (distance < shortestEdge) {
                    nodeA = i;
                    nodeB = city;

                    shortestEdge = distance;
                }
            }
        }

        mstNodes.add(nodeB);
        notYetMst.remove(nodeB);

        cities[nodeA].getCandidateList().add(nodeB);
        nodeB.getCandidateList().add(cities[nodeA]);
    }
}
