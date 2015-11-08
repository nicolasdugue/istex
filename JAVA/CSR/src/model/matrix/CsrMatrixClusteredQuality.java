package model.matrix;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.apache.log4j.Logger;

import model.cluster.decorator.IClustering;
import model.matrix.decorator.IMatrix;
import model.util.nuplet.PairF;
import util.SGLogger;

public class CsrMatrixClusteredQuality {
	
//Declaration
//-----------
	
	//CsrMatrix Section : Data declaration
	//-------------------------------------
	private IMatrix matrix;
	private IClustering clusters;
	private float[] sum_cluster;
	private Logger log;
	
	
	//Quality Section : Data declaration
	//-------------------------------------
	private float[] meanFMeasure;
	private float globalMeanFMeasure;
	
	
	
		

	
	
	
	
	
	

//Constructors
//------------
	public CsrMatrixClusteredQuality() {
			super();
		}
	
	public CsrMatrixClusteredQuality(IMatrix m, IClustering clusters) {
		this.matrix=m;
		this.clusters = clusters;
		this.sum_cluster = new float[this.getNbCluster()];
		
		//Quality Section 
		//----------------------------------
		meanFMeasure = new float[matrix.getNbColumns()];
		
		fillFromCsrMatrix();
		//LOG DEBUGG
		//----------------------------------
		log=SGLogger.getInstance();
		log.debug(this.getNbCluster() + " clusters");
		for (int i=0; i < this.getNbCluster(); i++ ) {
			log.debug("Cluster " + i +" : " + this.getSizeCk(i));
		}
	}

	
//Methods 
//-------

	/**
	 * @param i the column you need to get the sum
	 * @param k the cluster the rows have to belong to
	 * @return sum over the i-th column of the matrix for each object that belongs to cluster k
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





	public float getSumRow(int i) {
		return matrix.getSumRow(i);
	}

	public float getSumCol(int i) {
		return matrix.getSumCol(i);
	}


	public String getLabelOfRow(int i) {
		return matrix.getLabelOfRow(i);
	}

	public int getIndexOfRowLabel(String label) {
		return matrix.getIndexOfRowLabel(label);
	}



	public int getSizeCk(int cluster) {
		return clusters.getSizeCk(cluster);
	}


	//Feature Selection : Initialization
	//-------------------------------------
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
				for (int k=0; k < ( matrix.getNbCluster(); k++) {
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
			globalMeanFMeasure=global_sum/(((CsrMatrixClusteredQuality) matrix).getNbCluster() * matrix.getNbColumns());
		}
		
		//Feature Recall
		//--------------
			public float fr(int column, int cluster) {
				//System.out.println("FR = " + getSumColInCluster(column, cluster)+ " / " + getSumCol(column));
				return (float)((CsrMatrixClusteredQuality) matrix).getSumColInCluster(column, cluster) / matrix.getSumCol(column);
			}
		
			public float fr(int column, int cluster, float sumColInCluster) {
				//System.out.println("FR = " + getSumColInCluster(column, cluster)+ " / " + getSumCol(column));
				return sumColInCluster / matrix.getSumCol(column);
			}
		
		//Feature Precision
		//--------------
			public float fp(int column, int cluster) {
				//System.out.println("FP = " + getSumColInCluster(column, cluster)+ " / " + getSumCluster(cluster));
				float num = ((CsrMatrixClusteredQuality) matrix).getSumColInCluster(column, cluster);
				float den = ((CsrMatrixClusteredQuality) matrix).getSumCluster(cluster);
				return (float)num / den;
			}
	
			public float fp(int column, int cluster, float sumColInCluster) {
				//System.out.println("FP = " + getSumColInCluster(column, cluster)+ " / " + getSumCluster(cluster));
				float num = sumColInCluster;
				float den = ((CsrMatrixClusteredQuality) matrix).getSumCluster(cluster);
				return (float)num / den;
			}
		
		
		//Feature F-measure	
		//-----------------
		/**
		 * @param column the feature you are interested in as its column id
		 * @param cluster the cluster you are interested in as its id
		 * @return the feature F-measure of feature "column" in cluster "cluster"
		 */
		private float ff(int column, int cluster) {
			float sumColInCluster = ((CsrMatrixClusteredQuality) matrix).getSumColInCluster(column, cluster);
			float fp = sumColInCluster/((CsrMatrixClusteredQuality) matrix).getSumCluster(cluster);
			if (fp == 0)
				return 0;
			float fr=sumColInCluster/ matrix.getSumCol(column);
			return (2*fr*fp/(fr+fp));
		}
		
		
		//Getters
		//--------
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
			return ((CsrMatrixClusteredQuality) matrix).getNbCluster();
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
		
		/*
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
		//*/

}
