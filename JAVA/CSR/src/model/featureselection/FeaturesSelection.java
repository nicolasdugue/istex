package model.featureselection;

import java.util.ArrayList;
import java.util.List;

import model.matrix.CsrMatrixClustered;

/**
 * Contains Feature Selection functions to obtain Feature F-Measure
 * 
 * @author dugue
 *
 */
public class FeaturesSelection implements IFeaturesSelection {
	private CsrMatrixClustered matrix;
	private float[][] featuresMatrix;
	private float[] meanFMeasure;
	private float globalMeanFMeasure;
	public FeaturesSelection(CsrMatrixClustered matrix) {
		super();
		this.matrix = matrix;
		featuresMatrix = new float[matrix.getNbColumns()][matrix.getNbCluster()];
		meanFMeasure = new float[matrix.getNbColumns()];
		fillFromCsrMatrix();
	}
	/*
	 * -----------------PRIVATE ---- DO NOT USE OUTSIDE THIS FILE
	 */
	private void fillFromCsrMatrix() {
		float local_sum;
		float global_sum=0;
		for (int j=0; j < matrix.getNbColumns(); j++) {
			local_sum=0;
			for (int k=0; k < matrix.getNbCluster(); k++) {
				featuresMatrix[j][k]=this.ff(j, k);
				local_sum+=featuresMatrix[j][k];
			}
			meanFMeasure[j]=local_sum/matrix.getNbCluster();	
			global_sum +=local_sum;
		}
		globalMeanFMeasure=global_sum/(matrix.getNbCluster() * matrix.getNbColumns());
	}
	
	//Computes the feature recall
	private float fr(int column, int cluster) {
		//System.out.println("FR = " + getSumColInCluster(column, cluster)+ " / " + getSumCol(column));
		return (float)matrix.getSumColInCluster(column, cluster) / matrix.getSumCol(column);
	}
	
	// Computes the feature precision
	private float fp(int column, int cluster) {
		//System.out.println("FP = " + getSumColInCluster(column, cluster)+ " / " + getSumCluster(cluster));
		float num = matrix.getSumColInCluster(column, cluster);
		float den = matrix.getSumCluster(cluster);
		return (float)num / den;
	}
	/**
	 * @param column the feature you are interested in as its column id
	 * @param cluster the cluster you are interested in as its id
	 * @return the feature F-measure of feature "column" in cluster "cluster"
	 */
	private float ff(int column, int cluster) {
		float fr=fr(column, cluster);
		float fp = fp(column, cluster);
		if (fp == 0)
			return 0;
		return (2*fr*fp/(fr+fp));
	}
	
	/**
	 * @return the average Feature F Measure over all clusters and all features
	 */
	private float getGlobalMeanFMeasure() {
		return globalMeanFMeasure;
	}
	/**
	 * @param f the feature selected according to its index in the matrix
	 * @return the average Feature F Measure for feature f over all clusters
	 */
	private float getFeatureFMeanFMeasure(int f) {
		return meanFMeasure[f];
	}

	
	private float getFeatureFMeasure(int f, int cluster) {
		return featuresMatrix[f][cluster];
	}
	
	
	
	/*
	 * -----------------PUBLIC
	 */
	
	/**
	 * @return the number of clusters obtained
	 */
	public int getNbCluster() {
		return matrix.getNbCluster();
	}
	
	/**
	 * @return the number of features
	 */
	public int getNbFeatureSelected() {
		return matrix.getNbColumns();
	}
	
	public List<Integer> getFeaturesSelected(int cluster) {
		List<Integer> l = new ArrayList<Integer>();
		for (int i=0; i < matrix.getNbColumns(); i++) {
			//TODO Ajouter l'autre règle de sélection de features liée au cluster
			if (meanFMeasure[i] > this.globalMeanFMeasure)
				l.add(i);
		}
		return l;
	}
	
	
	public String getLabelOfCol(int j) {
		return matrix.getLabelOfCol(j);
	}

	public int getIndexOfColLabel(String label) {
		return matrix.getIndexOfColLabel(label);
	}
	
	
	/**
	 * @return the average Feature F Measure over all clusters and all features
	 */
	public float getGlobalMeanFeatureValue() {
		return getGlobalMeanFMeasure();
	}
	
	
	/**
	 * @param f the feature selected according to its index in the matrix
	 * @return the average Feature F Measure for feature f over all clusters
	 */
	public float getFeatureFMeanValue(int f) {
		return getFeatureFMeanFMeasure(f);
	}
	
	
	/**
	 * @param f the feature one wants to get the F-measure
	 * @param cluster the cluster one is interested
	 * @return the average Feature F Measure for feature f and cluster cluster
	 */
	public float getFeatureValue(int f, int cluster) {
		return getFeatureFMeasure(f, cluster);
	}
	
	
	
}
