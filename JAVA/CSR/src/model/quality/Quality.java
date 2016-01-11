package model.quality;

import java.util.AbstractList;

import org.apache.log4j.Logger;



import controller.Exp_test;
import util.SGLogger;
import model.featureselection.FeaturesSelection;
import model.matrix.CsrMatrixClustered;
import model.util.nuplet.PairF;

public class Quality {

	static Logger log = SGLogger.getInstance();

	// Contrast
	// ---------------
	public static float getContrast(FeaturesSelection fs, int column,
			int cluster) {

		return fs.getFeatureFMeanValue(column) == 0 ? 0 : fs.getFeatureValue(
				column, cluster) / fs.getFeatureFMeanValue(column);
	}

	// PC index
	// ---------------------
	public static float getPC(FeaturesSelection fs) {

		int nbClusters = fs.getMatrix().getNbCluster();
		float resultPC = 0f;
		float sumContrast = 0f;
		for (int i = 0; i < nbClusters; i++) {

			// Sum of contrasts
			for (int f : fs.getFeaturesSelected(i)) {
				sumContrast += getContrast(fs, f, i);
			}
			resultPC += 1 / ((float) fs.getMatrix().getSizeCk(i)) * sumContrast;
			sumContrast = 0f;
		}

		return (resultPC / nbClusters);

	}

	// EC index
	// ---------------------
	public static float getEC(FeaturesSelection fs) {

		int nbClusters = fs.getMatrix().getNbCluster();
		float resultEC = 0f;
		float sumContrast = 0f;
		float sumAntiContrast = 0f;
		float tempContrast = 0f;
		int nbActiveFeatures = 0;
		int nbPassiveFeatures = 0;
		int nbDataAssociatedCk;
		int reset_cluster_index = 0;
		int index_matrix = 0;
		for (int i = 0; i < nbClusters; i++) {

			for (int f : fs.getMatrix().getObjectsInCk(i)) {
				tempContrast = getContrast(fs, f - reset_cluster_index, i); // getObjectInCk
																			// retourne
																			// des
																			// indices
																			// absolus,
																			// or
																			// FeatureSelection
																			// et
																			// donc
																			// getContrast
																			// utilsent
																			// des
																			// indices
																			// relatifs
																			// au
																			// clsuter

				log.debug("contrast (" + (f - reset_cluster_index) + "," + i
						+ ") = " + getContrast(fs, f - reset_cluster_index, i));
				// Active feature
				if (tempContrast >= 1) {
					sumContrast += tempContrast;
					nbActiveFeatures++;
				}
				// Passive feature
				else {
					sumAntiContrast += 1 / tempContrast;
					nbPassiveFeatures++;
				}
				index_matrix++;

			}

			reset_cluster_index += index_matrix;

			nbDataAssociatedCk = fs.getMatrix().getSizeCk(i);
			resultEC += 1
					/ (((float) nbDataAssociatedCk) * ((float) nbActiveFeatures + (float) nbPassiveFeatures))
					* (((float) nbActiveFeatures) * sumContrast + ((float) nbPassiveFeatures)
							* sumAntiContrast);

			// Reset sums
			sumContrast = 0f;
			sumAntiContrast = 0f;
			nbActiveFeatures = 0;
			nbPassiveFeatures = 0;

		}
		return (resultEC / nbClusters);

	}

	// // Square Distance (AbstractList<Float> x, AbstractList<Float> y)
	// -----------------------------------------------------------------

	public static float getDistanceSquare(AbstractList<Float> x,
			AbstractList<Float> y) {

		int size = Math.min(x.size(), y.size());
		float Sum = 0;
		float temp;
		for (int i = 0; i < size; i++) {
			temp = Math.abs(x.get(i) - y.get(i));
			Sum += temp * temp;
		}

		return (Sum);
	}

	// Square Distance (PairF[],Float[])
	// ---------------------------------
	public static float getDistanceSquare(PairF[] x, Float[] y) {

		int size = Math.min(x.length, y.length);
		float Sum = 0;
		float temp;
		for (int i = 0; i < size; i++) {
			temp = Math.abs(x[i].getRight() - y[i]); // compare weights
			Sum += temp * temp;
		}
		return (Sum);
	}

	// Square Distance (Float[],Float[])
	// ---------------------------------
	public static float getDistanceSquare(Float[] x, Float[] y) {

		int size = Math.min(x.length, y.length);
		float Sum = 0;
		float temp;
		for (int i = 0; i < size; i++) {
			temp = Math.abs(x[i] - y[i]); // compare weights
			Sum += temp * temp;
		}
		return (Sum);
	}

	// Square Distance(PairF[] x, PairF[] y)
	// -------------------------------------
	public static float getDistanceSquare(PairF[] x, PairF[] y) {

		int size = Math.min(x.length, y.length);
		float Sum = 0;
		float temp;
		for (int i = 0; i < size; i++) {
			temp = Math.abs(x[i].getRight() - y[i].getRight()); // compare
																// weights
			Sum += temp * temp;
		}
		return (Sum);
	}

	// Diameter of a cluster
	// ---------------------
	public static float getDiameter(int k, CsrMatrixClustered mc) {

		int rowSize = mc.getMatrix().getRow(0).length; // same for the other
														// rows
		int colSize = mc.getObjectsInCk(k).size();

		// Sums over the lines in the cluster
		Float[] centroid = new Float[rowSize];
		
		// Reset vector value
		for (int col = 0; col < rowSize; col++) {
			centroid[col] = 0f;
		}
		
		for (int line : mc.getObjectsInCk(k)) {
			for (int col = 0; col < rowSize; col++) {
				centroid[col] += mc.getMatrix().getRow(line)[col].getRight();
			}
		}
		
		// Normalize the vector
		for (int col = 0; col < rowSize; col++) {
			centroid[col] = centroid[col] / colSize;
		}

		float sumDiam = 0f;
		

		for (int i : mc.getObjectsInCk(k)) {
			sumDiam += getDistanceSquare(mc.getMatrix().getRow(i), centroid);
		}
		float nbDataAssociatedCk = (float) mc.getSizeCk(k);

		return ((float) Math.sqrt(1 / nbDataAssociatedCk * sumDiam));

	}

	// Diameter Square * NbElement
	// ---------------------------

	// For Intra and Inter inertia index
	public static float getDiameterSquared(int k, CsrMatrixClustered mc) {
		//!\\squared and multiplied by the number of elements
		
		int rowSize = mc.getMatrix().getRow(0).length; // same for the other row							
		int colSize = mc.getObjectsInCk(k).size();
		Float[] centroid = new Float[rowSize];

		// Reset vector value
		for (int col = 0; col < rowSize; col++) {
			centroid[col] = 0f;
		}

		// Sums over the lines in the cluster
		for (int line : mc.getObjectsInCk(k)) {
			for (int col = 0; col < rowSize; col++) {
				centroid[col] += mc.getMatrix().getRow(line)[col].getRight();
			}
		}

		// Normalize the vector (divide by size)
		for (int col = 0; col < rowSize; col++) {
			centroid[col] = centroid[col] / colSize;
		}

		float sumDiam = 0f;

		for (int i : mc.getObjectsInCk(k)) {
			sumDiam += getDistanceSquare(mc.getMatrix().getRow(i), centroid);
		}

		return (sumDiam);

	}

	// Distance between ci and cj
	// -------------------------
	public static float getDiss(int i, int j, CsrMatrixClustered mc) {

		float distance = getDistanceSquare(
				mc.getMatrix().getRow(mc.getObjectsInCk(i).get(0)), mc
						.getMatrix().getRow(mc.getObjectsInCk(j).get(0)));
		float temp_distance = distance;
		for (int indexVectorCI : mc.getObjectsInCk(i)) {
			for (int indexVectorCJ : mc.getObjectsInCk(j)) {
				temp_distance = getDistanceSquare(mc.getMatrix().getRow(indexVectorCI), mc.getMatrix().getRow(indexVectorCJ));
				if (distance > temp_distance) {
					distance = temp_distance;
				}
			}
		}

		return ((float) Math.sqrt(distance));

	}

	// Davies-Bouldin
	// ---------------------
	public static float getDB(CsrMatrixClustered mc) {

		int nbClusters = mc.getNbCluster();

		if (nbClusters < 2)
			return 0;
		float DB_Sum = 0;
		float DB_Max = (getDiameter(0, mc) + getDiameter(1, mc))
				/ getDiss(0, 1, mc); // i.e the max
		float temp_DB_Sum = DB_Max;

		for (int i = 0; i < nbClusters; i++) {

			for (int j = 0; j < i; j++) {
				temp_DB_Sum = (getDiameter(i, mc) + getDiameter(j, mc))
						/ getDiss(i, j, mc);
				if (DB_Max < temp_DB_Sum)
					DB_Max = temp_DB_Sum;
			}

			for (int j = i + 1; j < nbClusters; j++) {
				temp_DB_Sum = (getDiameter(i, mc) + getDiameter(j, mc))
						/ getDiss(i, j, mc);
				if (DB_Max < temp_DB_Sum)
					DB_Max = temp_DB_Sum;

			}

			DB_Sum += DB_Max;

		}
		return (DB_Sum / nbClusters);

	}
	
	// Diameter of a cluster : DU definition of diam
	// ---------------------------------------------
	//N.OPT
	public static float getDiameterDU(int k, CsrMatrixClustered mc) {

		int colSize = mc.getObjectsInCk(k).size();

		float temp_max = getDistanceSquare(mc.getMatrix().getRow(mc.getObjectsInCk(k).get(0)), mc.getMatrix().getRow(mc.getObjectsInCk(k).get(1)));
		float final_max= temp_max;
		//Compare distances only once
		for (int i=0; i < colSize; i++) {
			for(int j=i+1 ; j<colSize; j++ )
			{
				temp_max = getDistanceSquare(mc.getMatrix().getRow(mc.getObjectsInCk(k).get(i)), mc.getMatrix().getRow(mc.getObjectsInCk(k).get(j)));
				if (temp_max > final_max) 
					final_max= temp_max;
			}
			
		}
		return (float) (Math.sqrt(final_max));

	}
		
	// Distance between two clusters : DU definition of distance
	// -------------------------------------------------
	//OPT
	public static float getDistanceDU(int k,int l, CsrMatrixClustered mc) {

		float temp_min = getDistanceSquare(mc.getMatrix().getRow(mc.getObjectsInCk(k).get(0)), mc.getMatrix().getRow(mc.getObjectsInCk(l).get(0)));
		float final_min= temp_min;
		//Compare distances only once
		for (int i : mc.getObjectsInCk(k) ) {
			for(int j : mc.getObjectsInCk(l) )
			{
				temp_min = getDistanceSquare(mc.getMatrix().getRow(i), mc.getMatrix().getRow(j));
				if (temp_min < final_min) 
					final_min= temp_min;
			}
			
		}
		return (float) (Math.sqrt(final_min));

	}
	
	public static float getDU(CsrMatrixClustered mc) {

		int nbClusters = mc.getNbCluster(); //k
		if (nbClusters < 2)
			return 0; 
		
		float final_max= getDiameterDU(0, mc);
		float temp_max;
		float final_min= getDistanceDU(0,1, mc);
		float temp_min;
		
		for (int i=0; i < nbClusters; i++) {
			temp_max = getDiameterDU(i, mc);
			if(temp_max > final_max)
				final_max = temp_max;
			
			for(int j=i+1 ; j<nbClusters; j++ )
			{
				temp_min = getDistanceDU(i, j, mc);
				if (temp_min < final_min) 
					final_min = temp_min;
			}
			
		}

		
		
		return (final_min/final_max);

	}

	// Intera Distance : WGSS
	// ---------------------
	public static float getIntraDistance(CsrMatrixClustered mc) {

		int nbClusters = mc.getNbCluster();

		float Intra_Sum = 0;

		for (int i = 0; i < nbClusters; i++) {

			Intra_Sum += getDiameterSquared(i, mc);

		}
		return (Intra_Sum);

	}

	// Inter Distance : BGSS
	// ---------------------
	public static float getInterDistance(CsrMatrixClustered mc) {

		int nbClusters = mc.getNbCluster();
		float Inter_Sum = 0f;
		int rowSize = mc.getMatrix().getRow(0).length;
		int colSize = mc.getObjectsInCk(0).size();
		Float[] centroid = new Float[rowSize]; // Local barycenter in a cluster
		Float[] barycenter = new Float[rowSize]; // The barycenter of the whole
													// set of data

		// Fill the barycenter
		for (int i = 0; i < rowSize; i++) {
			barycenter[i] = mc.getSumRow(i) / colSize;
		}

		// Compute sum over clusters
		for (int k = 0; k < nbClusters; k++) {

			colSize = mc.getObjectsInCk(k).size();

			// Reset centroid's value to 0
			for (int col = 0; col < rowSize; col++) {
				centroid[col] = 0f;
			}

			// Fill with sum of rows
			for (int line : mc.getObjectsInCk(k)) {
				for (int col = 0; col < rowSize; col++) {
					centroid[col] += mc.getMatrix().getRow(line)[col]
							.getRight();
				}
			}

			// Normalize
			for (int col = 0; col < rowSize; col++) {
				centroid[col] = centroid[col] / colSize;
			}

			Inter_Sum += getDistanceSquare(barycenter, centroid) * colSize;

		}
		return (Inter_Sum / nbClusters);

	}

	// Calinksi-Harabasz
	// ---------------------
	public static float getCH(CsrMatrixClustered mc) {

		return (((mc.getNbRows() - mc.getNbCluster()) * getInterDistance(mc)) / ((mc
				.getNbCluster() - 1) * getIntraDistance(mc)));

	}

	// Conductance
	// ---------------------
	public static float getConductance(int clusterIndex, CsrMatrixClustered amc) {
		int nbClusters = amc.getNbCluster();
		int nbNodes = 0;
		int ms = 0;
		int cs = 0;

		for (int cluster = 0; cluster < nbClusters; cluster++) {
			for (int i : amc.getObjectsInCk(cluster)) {
				nbNodes++;//or apply .size()
			}
		}

		for (int j = 0; j < nbNodes; j++) {
			if (amc.getClusterOfObjectI(j) == clusterIndex) {
				for (int i = 0; i < amc.getMatrix().getRow(j).length; i++) {
					if (amc.getClusterOfObjectI(j) == amc
							.getClusterOfObjectI(amc.getMatrix().getRow(j)[i]
									.getLeft())) {
						ms++;
					} else {
						cs++;
					}

				}

			}
		}

		
		return ((float) cs / (2 * ms + cs));
	}
	
	// Cut Ratio
	// ---------------------
	public static float getCutRatio(int clusterIndex, CsrMatrixClustered amc) {
			int nbClusters = amc.getNbCluster();
			int nbNodes = 0;
			int ms = 0;
			int cs = 0;

			for (int cluster = 0; cluster < nbClusters; cluster++) {
				for (int i : amc.getObjectsInCk(cluster)) {
					nbNodes++;//or apply .size()
				}
			}

			for (int j = 0; j < nbNodes; j++) {
				if (amc.getClusterOfObjectI(j) == clusterIndex) {
					for (int i = 0; i < amc.getMatrix().getRow(j).length; i++) {
						if (amc.getClusterOfObjectI(j) == amc
								.getClusterOfObjectI(amc.getMatrix().getRow(j)[i]
										.getLeft())) {
							ms++;
						} else {
							cs++;
						}

					}

				}
			}

			
			return ((float)cs / ((nbNodes-amc.getObjectsInCk(clusterIndex).size()))*(float) amc.getObjectsInCk(clusterIndex).size() );
		}
		
		
}
