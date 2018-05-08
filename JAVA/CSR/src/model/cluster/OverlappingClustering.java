package model.cluster;

import java.util.ArrayList;

import model.cluster.decorator.IClustering;

public class OverlappingClustering implements IClustering {

	// Correspond pour tout i de la liste clusters vers le cluster de l'objet i
	/**
	 * An array that stores for each object (matrix row) i, the cluster set it belongs to.
	 * <br>So clusters.get(i) returns j which are the clusters i belongs to.
	 */
	private ClusterSet[] clusters;

	// Liste des objets dans chaque cluster
	/**
	 * An array of array that is basically the list of cluster.
	 * <br>clustersList.get(i) allows to get the list of objects (matrix rows) that belongs to the cluster i
	 */
	private ArrayList<ArrayList<Integer>> clustersList = new ArrayList<ArrayList<Integer>>();
		
	public OverlappingClustering(int size) {
		clusters=new ClusterSet[size];
		ClusterSet s;
		for (int i=0; i < size; i++) {
			s = new ClusterSet();
			this.clusters[i]=s;
		}
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

	/**
	 * Allows to avoid overhead due to the resizement method of arraylist
	 * Reisze all the arraylist to the real size
	 */
	public void clusteringLoaded() {
		for (ArrayList l : clustersList) {
			//System.out.println(l);
			l.trimToSize();
		}
		clustersList.trimToSize();
	}

	@Override
	public boolean isIntAClusterOfObject(int index, int k) {
		for (int clust : this.clusters[index]) {
			if (clust == k)
				return true;
		}
		return false;
	}

	@Override
	public int size() {
		return this.clustersList.size();
	}

	@Override
	public void add(Integer e) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void add(Integer e, int k) {
		int nb_clusters = clustersList.size() -1;
		if (nb_clusters < k) {
			for (int i = 0; i < (k - nb_clusters); i++) {
				clustersList.add(new ArrayList<Integer>());
			}
		}
		this.clusters[e].add(k);
		clustersList.get(k).add(e);
	}

}
