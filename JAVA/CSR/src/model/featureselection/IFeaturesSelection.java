package model.featureselection;

import java.util.Set;

public interface IFeaturesSelection {
	
	/**
	 * @return the average Feature F Measure over all clusters and all features
	 */
	public float getGlobalMeanFeatureValue();
	/**
	 * @param f the feature selected according to its index in the matrix
	 * @return the average Feature F Measure for feature f over all clusters
	 */
	public float getFeatureFMeanValue(int f);

	/**
	 * @param f the feature one wants to get the F-measure
	 * @param cluster the cluster one is interested
	 * @return the average Feature F Measure for feature f and cluster cluster
	 */
	public float getFeatureValue(int f, int cluster);
	
	/**
	 * @return the number of clusters obtained
	 */
	public int getNbCluster();
	public String getLabelOfCluster(int cluster);
	public int getClusterOfLabel(String s);
	
	/**
	 * @return the number of features
	 */
	public int getNbFeatures();
	
	public Set<Integer> getFeaturesSelected(int cluster);
	public Set<String> getFeaturesAsStringSelected(int cluster);
	
	/**
	 * @return the number of features
	 */
	public String getLabelOfCol(int j);

	public int getIndexOfColLabel(String label);
}
