package model.featureselection;

import java.util.ArrayList;

/**
 * Allows to store the feature considered as labels for each cluster
 * 
 * @author dugue
 *
 */
public class LabelSelection implements ILabelSelection {
	/**
	 * Pour chaque cluster, on a une liste de labels Ainsi labels.get(i)
	 * retourne une ArrayList d'entier correspondant au numéro de colonnes des
	 * features sélectionnées comme label pour le cluster i.
	 */
	private ArrayList<ArrayList<Integer>> labels = new ArrayList<ArrayList<Integer>>();
	private IFeaturesSelection fs;
	
	public LabelSelection(IFeaturesSelection fs) {
		this.fs=fs;
		// POur chaque cluster, on prépare une liste de labels qui, au départ,
		// est vide
		for (int i = 0; i < fs.getNbCluster(); i++) {
			labels.add(new ArrayList<Integer>());
		}
		float valueMax;
		int clusterWithValueMax;

		for (int j = 0; j < fs.getNbFeatureSelected(); j++) {
			valueMax=-1;
			clusterWithValueMax=-1;
			for (int k = 0; k < fs.getNbCluster(); k++) {
				if (fs.getFeatureValue(j, k) > valueMax) {
					valueMax =fs.getFeatureValue(j, k);
					clusterWithValueMax=k;
				}
			}
			//The feature f is added to the set of cluster labels of the cluster that maximizes the Feature F-Measure of f
			labels.get(clusterWithValueMax).add(j);
		}
	}
	
	/**
	 * @param cluster the cluster one wants to get the prevalent features
	 * @return a set of Integer representing the prevalent features for the cluster 
	 */
	public ArrayList<Integer> getPrevalentFeatureSet(int cluster) {
		return labels.get(cluster);
	}
	
	/**
	 * @param cluster the cluster one wants to get the prevalent labels
	 * @return a set of String representing the prevalent labels for the cluster 
	 */
	public ArrayList<String> getLabelSet(int cluster) {
		ArrayList<Integer> features = getPrevalentFeatureSet(cluster);
		ArrayList<String> l = new ArrayList<String>(features.size());
		for (int f : features) {
			l.add(fs.getLabelOfCol(f));
		}
		return l;
	}
	
	/**
	 * @return the number of clusters for the matrix studied
	 */
	public int getNbCluster() {
		return fs.getNbCluster();
	}

	public String getLabelOfCol(int j) {
		return fs.getLabelOfCol(j);
	}

	public int getIndexOfColLabel(String label) {
		return fs.getIndexOfColLabel(label);
	}
	

	@Override
	public float getFeatureFMeanValue(int f) {
		return fs.getFeatureFMeanValue(f);
	}

	public String getLabelOfCluster(int cluster) {
		return fs.getLabelOfCluster(cluster);
	}

	public int getClusterOfLabel(String s) {
		return fs.getClusterOfLabel(s);
	}
	

}
