package model.matrix;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.cluster.decorator.IClustering;
import model.matrix.decorator.IMatrix;
import model.util.nuplet.PairI;

public class CsrMatrixClustered {
	private IMatrix matrix;
	private IClustering clusters;
	
	public CsrMatrixClustered() {
		super();
	}

	public CsrMatrixClustered(IMatrix m, IClustering clusters) {
		this.matrix=m;
		this.clusters = clusters;
	}

	public int getNbCluster() {
		return clusters.size();
	}

	/**
	 * @param i the column you need to get the sum
	 * @param k the cluster the rows have to belong to
	 * @return sum over the i-th column of the matrix for each objet that belongs to cluster k
	 */
	public int getSumColInCluster(int i, int k) {
		List<PairI> column = matrix.getColumn(i);
		int sum=0;
		for (PairI p : column) {
			if (clusters.getClusterOfObjectI(p.getLeft()) == k)
				sum +=p.getRight();
		}
		return sum;
	}
	
	/**
	 * @param k the cluster you want to get the whole sum
	 * @return the sum over all the a_ij that belongs to the cluster
	 */
	public int getSumCluster(int k) {
		ArrayList<Integer> row_lists=clusters.getObjectsInCk(k);
		Iterator<Integer> it = row_lists.iterator();
		int sum=0;
		while (it.hasNext()) {
			sum+=matrix.getSumRow(it.next());
		}
		return sum;
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

	public int getSumRow(int i) {
		return matrix.getSumRow(i);
	}

	public int getSumCol(int i) {
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
