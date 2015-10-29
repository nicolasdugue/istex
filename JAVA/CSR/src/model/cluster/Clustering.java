package model.cluster;

import java.util.ArrayList;
import java.util.Iterator;

import model.cluster.decorator.IClustering;


/**
 * Allows to represent a Clustering.
 * 
 * @author dugue
 * 
 *
 */
public class Clustering implements IClustering{
	// Correspond pour tout i de la liste clusters vers le cluster de l'objet i
	/**
	 * An array that stores for each object (matrix row) i, the cluster j it belongs to.
	 * <br>So clusters.get(i) returns j which is the cluster i belongs to.
	 */
	private int[] clusters;
	private int cpt=0;

	// Liste des objets dans chaque cluster
	/**
	 * An array of array that is basically the list of cluster.
	 * <br>clustersList.get(i) allows to get the list of objects (matrix rows) that belongs to the cluster i
	 */
	private ArrayList<ArrayList<Integer>> clustersList = new ArrayList<ArrayList<Integer>>();

	
	public Clustering(int size) {
		clusters=new int[size];
	}


	public int size() {
		return clustersList.size();
	}


	public Integer getClusterOfObjectI(int index) {
		return clusters[index];
	}

	
	/**
	 * Allows to configure a clustering object, by stating that Object (matrix row) clusters.size() belongs to cluster e in parameter
	 * This updates {@link #clusters} and {@link #clustersList}.
	 * 
	 * @param e Cluster to which the object clusters.size() belongs
	 * @return whether the object could be added or not to clusters
	 */
	public void add(Integer e) {
		int nb_clusters = clustersList.size() - 1;
		if (nb_clusters < e) {
			for (int i = 0; i < (e - nb_clusters); i++) {
				clustersList.add(new ArrayList<Integer>());
			}
		}
		int row = cpt;
		clustersList.get(e).add(row);
		clusters[cpt]=e;
		cpt++;
	}


	public ArrayList<Integer> getObjectsInCk(int cluster) {
		return clustersList.get(cluster);
	}
	
	public int getSizeCk(int cluster) {
		return clustersList.get(cluster).size();
	}

	@Override
	public String getLabelOfCluster(int cluster) {
		return ""+cluster;
	}

	@Override
	public int getClusterOfLabel(String s) {
		return Integer.parseInt(s);
	}
}
