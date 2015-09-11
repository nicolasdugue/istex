package model.util.factory;

import java.io.FileNotFoundException;

import io.reader.ArcsReader;
import io.reader.ClusteringReader;
import model.featureselection.FeaturesSelection;
import model.featureselection.IFeaturesSelection;
import model.matrix.CsrMatrix;
import model.matrix.CsrMatrixClustered;

public class FactoryFeatureSelection {
	public  static IFeaturesSelection getFeatureSelecter(String matrix, String cluster) throws FileNotFoundException {
		return new FeaturesSelection(new CsrMatrixClustered(new CsrMatrix(new ArcsReader(matrix)), new ClusteringReader(cluster).getClusters()));
	}
}
