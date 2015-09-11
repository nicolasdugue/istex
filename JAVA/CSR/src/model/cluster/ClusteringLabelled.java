package model.cluster;

import java.util.ArrayList;

import model.cluster.decorator.IClustering;
import model.util.LabelStore;

public class ClusteringLabelled implements IClustering {
	private IClustering clustering;
	private LabelStore ls;
	
	public Integer getClusterOfObjectI(int index) {
		return clustering.getClusterOfObjectI(index);
	}

	public boolean add(Integer e) {
		return clustering.add(e);
	}

	public ArrayList<Integer> getObjectsInCk(int cluster) {
		return clustering.getObjectsInCk(cluster);
	}

	public int getSizeCk(int cluster) {
		return clustering.getSizeCk(cluster);
	}

	public int size() {
		return clustering.size();
	}

	@Override
	public String getLabelOfCluster(int cluster) {
		return ls.getLabel(cluster);
	}

	@Override
	public int getClusterOfLabel(String s) {
		return ls.getIndexOfLabel(s);
	}
}
