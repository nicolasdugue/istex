package model.featureselection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import model.matrix.CsrMatrixClustered;
import util.SGLogger;

/**
 * Contains Feature Selection functions to obtain Feature F-Measure
 * 
 * @author dugue
 *
 */
public class FeaturesSelection implements IFeaturesSelection {
	private CsrMatrixClustered matrix;
	private Logger log;
	public CsrMatrixClustered getMatrix() {
		return matrix;
	}
	public void setMatrix(CsrMatrixClustered matrix) {
		this.matrix = matrix;
	}
	//private float[][] featuresMatrix;
	private float[] meanFMeasure;
	private float globalMeanFMeasure;
	public FeaturesSelection(CsrMatrixClustered matrix) {
		super();
		this.matrix = matrix;
		//featuresMatrix = new float[matrix.getNbColumns()][matrix.getNbCluster()];
		meanFMeasure = new float[matrix.getNbColumns()];
		log=SGLogger.getInstance();
		fillFromCsrMatrix();
	}
	/*
	 * -----------------PRIVATE ---- DO NOT USE OUTSIDE THESE METHODS
	 */
	private void fillFromCsrMatrix() {
		float local_sum;
		float global_sum=0;
		int not_null;
		float ffm;
		for (int j=0; j < matrix.getNbColumns(); j++) {
			local_sum=0;
			not_null=0;
			if (j % 1000 == 0) {
				log.debug(j + "-th feature handled");
			}
			for (int k=0; k < matrix.getNbCluster(); k++) {
				ffm=this.ff(j, k);
				if (ffm > 0) {
					local_sum+=ffm;
					not_null++;
				}
			}
			//La moyenne est faite uniquement sur les cluster dans laquelle la feature f est présente
			meanFMeasure[j]=local_sum/ not_null;//matrix.getNbCluster();	
			global_sum +=local_sum;
		}
		globalMeanFMeasure=global_sum/(matrix.getNbCluster() * matrix.getNbColumns());
	}
	
	//Computes the feature recall
	public float fr(int column, int cluster) {
		//System.out.println("FR = " + getSumColInCluster(column, cluster)+ " / " + getSumCol(column));
		return (float)matrix.getSumColInCluster(column, cluster) / matrix.getSumCol(column);
	}
	//Computes the feature recall
	public float fr(int column, int cluster, float sumColInCluster) {
		//System.out.println("FR = " + getSumColInCluster(column, cluster)+ " / " + getSumCol(column));
		return sumColInCluster / matrix.getSumCol(column);
	}
	
	// Computes the feature precision
	public float fp(int column, int cluster) {
		//System.out.println("FP = " + getSumColInCluster(column, cluster)+ " / " + getSumCluster(cluster));
		float num = matrix.getSumColInCluster(column, cluster);
		float den = matrix.getSumCluster(cluster);
		return (float)num / den;
	}
	// Computes the feature precision
	public float fp(int column, int cluster, float sumColInCluster) {
		//System.out.println("FP = " + getSumColInCluster(column, cluster)+ " / " + getSumCluster(cluster));
		float num = sumColInCluster;
		float den = matrix.getSumCluster(cluster);
		return (float)num / den;
	}
	
	/**
	 * @param column the feature you are interested in as its column id
	 * @param cluster the cluster you are interested in as its id
	 * @return the feature F-measure of feature "column" in cluster "cluster"
	 */
	private float ff(int column, int cluster) {
		float sumColInCluster = matrix.getSumColInCluster(column, cluster);
		float fp = sumColInCluster/matrix.getSumCluster(cluster);
		if (fp == 0)
			return 0;
		float fr=sumColInCluster/ matrix.getSumCol(column);
		return (2*fr*fp/(fr+fp));
	}
	
	public int getNbColumns() {
		return matrix.getNbColumns();
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
		return ff(f,cluster);
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
	
	public Set<Integer> getFeaturesSelected(int cluster) {
		Set<Integer> l = new HashSet<Integer>();
		float ff;
		for (int i=0; i < matrix.getNbColumns(); i++) {
			ff=this.getFeatureFMeasure(i,cluster);
			if ((ff > this.meanFMeasure[i]) && (ff > this.globalMeanFMeasure))
				l.add(i);
		}
		return l;
	}
	public Set<String> getFeaturesAsStringSelected(int cluster) {
		Set<String> l = new HashSet<String>();
		float ff;
		for (int i=0; i < matrix.getNbColumns(); i++) {
			ff=this.getFeatureFMeasure(i,cluster);
			if ((ff > this.meanFMeasure[i]) && (ff > this.globalMeanFMeasure))
				l.add(this.getLabelOfCol(i));
		}
		return l;
	}
	
	public Set<Integer> getFeaturesSelected() {
		Set<Integer> all= new HashSet<Integer>();
		Set<Integer> tmp;
		for (int k=0; k < this.getNbCluster(); k++) {
			tmp=this.getFeaturesSelected(k);
			for (Iterator<Integer> it=tmp.iterator(); it.hasNext();) {
				all.add(it.next());
			}
		}
		return all;
	}
	
	public Set<String> getFeaturesAsStringSelected() {
		Set<String> all= new HashSet<String>();
		Set<String> tmp;
		for (int k=0; k < this.getNbCluster(); k++) {
			tmp=this.getFeaturesAsStringSelected(k);
			for (Iterator<String> it=tmp.iterator(); it.hasNext();) {
				all.add(it.next());
			}
		}
		return all;
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
	 * @return the Feature F Measure for feature f and cluster cluster
	 */
	public float getFeatureValue(int f, int cluster) {
		return ff(f, cluster);
	}
	public int getNbRows() {
		return matrix.getNbRows();
	}
	public Integer getClusterOfObjectI(int index) {
		return matrix.getClusterOfObjectI(index);
	}
	public String getLabelOfCluster(int cluster) {
		return matrix.getLabelOfCluster(cluster);
	}
	public int getClusterOfLabel(String s) {
		return matrix.getClusterOfLabel(s);
	}
	public ArrayList<Integer> getObjectsInCk(int cluster) {
		return matrix.getObjectsInCk(cluster);
	}
	
	
	
	
}
