package model.featureselection;

import java.util.ArrayList;

public interface ILabelSelection {

	/**
	 * @param cluster the cluster one wants to get the prevalent features
	 * @return a set of Integer representing the prevalent features for the cluster 
	 */
	public ArrayList<Integer> getPrevalentFeatureSet(int cluster);
	
	/**
	 * @param cluster the cluster one wants to get the prevalent labels
	 * @return a set of String representing the prevalent labels for the cluster 
	 */
	public ArrayList<String> getLabelSet(int cluster);
	
	/**
	 * @return the number of clusters for the matrix studied
	 */
	public int getNbCluster();

	public String getLabelOfCol(int j);

	public int getIndexOfColLabel(String label);
	
	public float getFeatureFMeanValue(int f);
}
