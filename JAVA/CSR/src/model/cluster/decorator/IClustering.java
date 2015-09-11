package model.cluster.decorator;

import java.util.ArrayList;

public interface IClustering {
	public Integer getClusterOfObjectI(int index);
	public boolean add(Integer e);
	public ArrayList<Integer> getObjectsInCk(int cluster);
	public int getSizeCk(int cluster);
	public int size();
	public String getLabelOfCluster(int cluster);
	public int getClusterOfLabel(String s);
}
