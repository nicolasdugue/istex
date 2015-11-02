package model.matrix;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import model.cluster.decorator.IClustering;
import model.matrix.decorator.IMatrix;
import model.util.nuplet.PairF;
import util.SGLogger;

public class CsrMatrixClustered {
	private IMatrix matrix;
	private IClustering clusters;
	private float[] sum_cluster;
	private Logger log;
	
	public CsrMatrixClustered() {
		super();
	}

	public CsrMatrixClustered(IMatrix m, IClustering clusters) {
		this.matrix=m;
		this.clusters = clusters;
		this.sum_cluster = new float[this.getNbCluster()];
		log=SGLogger.getInstance();
		log.debug(this.getNbCluster() + " clusters");
		for (int i=0; i < this.getNbCluster(); i++ ) {
			log.debug("Cluster " + i +" : " + this.getSizeCk(i));
		}
	}

	public int getNbCluster() {
		return clusters.size();
	}

	/**
	 * @param i the column you need to get the sum
	 * @param k the cluster the rows have to belong to
	 * @return sum over the i-th column of the matrix for each objet that belongs to cluster k
	 */
	public float getSumColInCluster(int i, int k) {
		int start;
		int end;
		if (i == 0) {
			start=0;
		}
		else {
			start=matrix.getCumulativeColumns(i-1);
		}
		end =matrix.getCumulativeColumns(i);
		float sum=0f;
		PairF val;
		for (int j=start; j < end; j++) {
			val=matrix.getIinColumns(j);
			if (clusters.getClusterOfObjectI(val.getLeft()) == k)
				sum +=val.getRight();
		}
		return sum;
	}
	
	/**
	 * @param i the column you need to get the sum
	 * @param k the cluster the rows have to belong to
	 * @return sum over the i-th column of the matrix for each objet that belongs to cluster k
	 */
	public float getSumRowInCluster(int i, int k) {
		int start;
		int end;
		if (i == 0) {
			start=0;
		}
		else {
			start=matrix.getCumulativeRows(i-1);
		}
		end =matrix.getCumulativeRows(i);
		float sum=0f;
		PairF val;
		for (int j=start; j < end; j++) {
			val=matrix.getIinRows(j);
			if (clusters.getClusterOfObjectI(val.getLeft()) == k)
				sum +=val.getRight();
		}
		return sum;
	}
	
	/**
	 * @param k the cluster you want to get the whole sum
	 * @return the sum over all the a_ij that belongs to the cluster
	 */
	public float getSumCluster(int k) {
		if (sum_cluster[k] == 0.0) {
			ArrayList<Integer> row_lists=clusters.getObjectsInCk(k);
			Iterator<Integer> it = row_lists.iterator();
			float sum=0;
			while (it.hasNext()) {
				sum+=matrix.getSumRow(it.next());
			}
			sum_cluster[k]=sum;
		}
		return sum_cluster[k];
	}

	/*public int getCumulativeRows(int i) {
		return matrix.getCumulativeRows(i);
	}

	public int getCumulativeColumns(int i) {
		return matrix.getCumulativeColumns(i);
	}*/

/*	public PairI getIinRows(int i) {
		return matrix.getIinRows(i);
	}

	public PairI getIinColumns(int i) {
		return matrix.getIinColumns(i);
	}*/

	public int getNbElements() {
		return matrix.getNbElements();
	}

	public int getNbRows() {
		return matrix.getNbRows();
	}

	public int getNbColumns() {
		return matrix.getNbColumns();
	}

	public float getSumRow(int i) {
		return matrix.getSumRow(i);
	}

	public float getSumCol(int i) {
		return matrix.getSumCol(i);
	}

	public String getLabelOfCol(int j) {
		return matrix.getLabelOfCol(j);
	}

	public String getLabelOfRow(int i) {
		return matrix.getLabelOfRow(i);
	}

	public int getIndexOfRowLabel(String label) {
		return matrix.getIndexOfRowLabel(label);
	}

	public int getIndexOfColLabel(String label) {
		return matrix.getIndexOfColLabel(label);
	}

	public Integer getClusterOfObjectI(int index) {
		return clusters.getClusterOfObjectI(index);
	}

	public ArrayList<Integer> getObjectsInCk(int cluster) {
		return clusters.getObjectsInCk(cluster);
	}

	public int getSizeCk(int cluster) {
		return clusters.getSizeCk(cluster);
	}

	public String getLabelOfCluster(int cluster) {
		return clusters.getLabelOfCluster(cluster);
	}

	public int getClusterOfLabel(String s) {
		return clusters.getClusterOfLabel(s);
	}
	
	
	

}
