package model.util.factory;

import java.io.FileNotFoundException;

import io.reader.ArcsReader;
import io.reader.ClusteringReader;
import io.reader.LabelReader;
import io.reader.MatrixReader;
import model.featureselection.FeaturesSelection;
import model.featureselection.IFeaturesSelection;
import model.matrix.CsrMatrix;
import model.matrix.CsrMatrixClustered;
import model.matrix.decorator.MatrixFeatureLabels;

public class FactoryFeatureSelection {
	public  static IFeaturesSelection getFeatureSelecter(String matrix, String cluster) throws FileNotFoundException {
		return new FeaturesSelection(new CsrMatrixClustered(new CsrMatrix(new MatrixReader(matrix)), new ClusteringReader(cluster).getClusters()));
	}
	public  static IFeaturesSelection getGraphFeatureSelecter(String graph, String cluster) throws FileNotFoundException {
		return new FeaturesSelection(new CsrMatrixClustered(new CsrMatrix(new ArcsReader(graph)), new ClusteringReader(cluster).getClusters()));
	}
	public  static IFeaturesSelection getGraphFeatureSelecter(String graph, String cluster, String labels) throws FileNotFoundException {
		return new FeaturesSelection(new CsrMatrixClustered(new MatrixFeatureLabels(new CsrMatrix(new ArcsReader(graph)), new LabelReader(labels).getLs()), new ClusteringReader(cluster).getClusters()));
	}
}
