package model.quality;

import java.util.AbstractList;

import model.featureselection.FeaturesSelection;
import model.util.nuplet.PairF;

public class Quality {
	/*
	 * TODO : Comparer avec une classe qui prend en param�tre FeatureSelection
	 * et qui n'est pas statique.
	 * 
	 * Il est vraisemblable qu'en terme d'efficacit� m�moire on perde beaucoup
	 * en passant la FS comme param�tre pour chaque m�thode interm�diaire, qui
	 * va donc copier int�gralement FS Propsition de solution : - Passer un
	 * pointeur vers la FS (ou �quivalent java ?) - Faire h�riter Quality de la
	 * FS et �ventuellement la d�truire pour lib�rer la m�moire une fois le
	 * travail termin� - S'affranchir des m�thodes interm�diaires, mais le code
	 * sera moins digeste.
	 */

	// Contrast
	// ---------------
	public static float getContrast(FeaturesSelection fs, int column, int cluster) {
		// *TODO : case of division by 0 ? for now it returns 0 without warning.
		return fs.getFeatureFMeanValue(column) == 0 ? 0
				: fs.getFeatureValue(column, cluster) / fs.getFeatureFMeanValue(column);
	}

	// PC index.
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

		return (resultPC / ((float) nbClusters));

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

		for (int i = 0; i < nbClusters; i++) {

			// Get all the information with one single thread
			for (int f : fs.getFeaturesSelected(i)) {
				// One single call
				tempContrast += getContrast(fs, f, i);

				// Active feature
				if (tempContrast >= 1) {
					sumContrast += tempContrast;
					nbActiveFeatures++;
				}
				// Passive feature
				else {
					sumAntiContrast += 1 / tempContrast; // TODO : decide what
															// to do when 1/0
					nbPassiveFeatures++;
				}

			}
			int nbDataAssociatedCk = fs.getMatrix().getSizeCk(i);
			resultEC += 1 / (((float) nbDataAssociatedCk) * ((float) nbActiveFeatures + (float) nbPassiveFeatures))
					* (((float) nbActiveFeatures) * sumContrast + ((float) nbPassiveFeatures) * sumAntiContrast);

			// Reset sums
			sumContrast = 0f;
			sumAntiContrast = 0f;
			nbActiveFeatures = 0;
			nbPassiveFeatures = 0;
		}
		return (resultEC / ((float) nbClusters));

	}

	// Square Distance
	// ---------------
	public static float getDistanceSquare(AbstractList<Float> x, AbstractList<Float> y) {

		int size = 1 + Math.min(x.size(), y.size());
		float Sum = 0;
		float temp;
		for (int i = 0; i < size; i++) {
			temp = Math.abs(x.get(i) - y.get(i));
			Sum += temp * temp;
		}

		return (Sum);
	}

	// Square Distance
	// ---------------
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

	// Square Distance
	// ---------------
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
	public static float getDiameter(int k, FeaturesSelection fs) {

		// Remarques : Evaluer l'impact sur la m�moire � cause du param�tre fs
		// qu'il faut
		// copier int�gralement � chaque it�ration pour �valuer chaque diam�tre
		// de cluster.
		// TODO : Donc - Comparer avec une classe non statique.
		// - Int�grer cette propri�t� dans le cluster lui m�me. Il s'agit d'une
		// caract�ristique inh�rente au cluster.
		// - ne pas faire appel � cette m�thode, donc l'int�grer directement
		// dans la boucle
		int rowSize = fs.getRow(0).length;
		int colSize = fs.getObjectsInCk(k).size();

		// Centroid
		Float[] centroid = new Float[rowSize];
		for (int i = 0; i < rowSize; i++) {
			centroid[i] = fs.getMatrix().getSumRow(i) / colSize;
		}
		float sumDiam = 0f;

		for (int i = 0; i < colSize; i++) {
			sumDiam += getDistanceSquare(fs.getRow(i), centroid);
		}
		float nbDataAssociatedCk = (float) fs.getMatrix().getSizeCk(k);

		return ((float) Math.sqrt(1 / nbDataAssociatedCk * sumDiam));

	}

	// Distance between ci and cj
	// -------------------------
	public static float getDiss(int i, int j, FeaturesSelection fs) {

		float distance = getDistanceSquare(fs.getRow(fs.getObjectsInCk(i).get(0)),
				fs.getRow(fs.getObjectsInCk(j).get(0)));
		float temp_distance = distance;
		for (int indexVectorCI : fs.getObjectsInCk(i)) {
			for (int indexVectorCJ : fs.getObjectsInCk(j)) {
				temp_distance = getDistanceSquare(fs.getRow(indexVectorCI), fs.getRow(indexVectorCJ));
				if (distance > temp_distance) {
					distance = temp_distance;
				}
			}
		}

		return ((float) Math.sqrt(distance));

	}

	// Davies-Bouldin
	// ---------------------
	public static float getDB(FeaturesSelection fs) {

		int nbClusters = fs.getMatrix().getNbCluster();

		if (nbClusters < 2)
			return 0;

		float DB_Sum = (getDiameter(0, fs) + getDiameter(1, fs)) / getDiss(0, 1, fs);
		float temp_DB_Sum = DB_Sum;

		for (int i = 0; i < nbClusters; i++) {

			for (int j = 0; j < i; j++) {
				temp_DB_Sum = (getDiameter(i, fs) + getDiameter(j, fs)) / getDiss(i, j, fs);
				if (DB_Sum < temp_DB_Sum)
					DB_Sum = temp_DB_Sum;
			}

			for (int j = i + 1; j < nbClusters; j++) {
				temp_DB_Sum = (getDiameter(i, fs) + getDiameter(j, fs)) / getDiss(i, j, fs);
				if (DB_Sum < temp_DB_Sum)
					DB_Sum = temp_DB_Sum;

			}

		}
		return (DB_Sum / nbClusters);

	}

}
