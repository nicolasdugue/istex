package model.util.factory;

import java.io.FileNotFoundException;

import io.reader.ArcsReader;
import io.reader.ClusteringReader;
import io.reader.LabelReader;
import model.diachronism.LabelDiachronism;
import model.featureselection.FeaturesSelection;
import model.featureselection.ILabelSelection;
import model.featureselection.LabelSelection;
import model.featureselection.LabelSelectionFromFile;
import model.matrix.CsrMatrix;
import model.matrix.CsrMatrixClustered;
import model.matrix.decorator.MatrixFeatureLabels;
import model.util.LabelStore;

public class FactoryDiachronism {
	public static LabelDiachronism matchingFromLabel(String fileNameSrc, String fileNameTarget) throws FileNotFoundException {
		ILabelSelection ls_t0 = new LabelSelectionFromFile(fileNameSrc);
		ILabelSelection ls_t1= new LabelSelectionFromFile(fileNameTarget);
		LabelDiachronism ld = new LabelDiachronism(ls_t0, ls_t1);
		return ld;
	}
	
	public static LabelDiachronism matchingFromMatrixCluster(String matrix1, String matrix2, String cluster1, String cluster2) throws FileNotFoundException {
		ILabelSelection ls_t0 = getLabelsFromMatrixClusters(matrix1, cluster1);
		ILabelSelection ls_t1 = getLabelsFromMatrixClusters(matrix2, cluster2);
		LabelDiachronism ld = new LabelDiachronism(ls_t0, ls_t1);
		return ld;
	}
	public static LabelDiachronism matchingFromMatrixClusterLabels(String matrix1, String matrix2, String cluster1, String cluster2, String label1, String label2) throws FileNotFoundException {
		ILabelSelection ls_t0 = getLabelsFromMatrixClustersLabels(matrix1, cluster1, label1);
		ILabelSelection ls_t1 = getLabelsFromMatrixClustersLabels(matrix2, cluster2, label2);
		LabelDiachronism ld = new LabelDiachronism(ls_t0, ls_t1);
		return ld;
	}
	public static ILabelSelection getLabelsFromMatrixClusters(String matrix,  String cluster) throws FileNotFoundException {
		ILabelSelection ls_t0 = new LabelSelection(new FeaturesSelection(new CsrMatrixClustered(new CsrMatrix(new ArcsReader(matrix)), new ClusteringReader(cluster).getClusters())));
		return ls_t0;
	}
	public static ILabelSelection getLabelsFromMatrixClustersLabels(String matrix,  String cluster, String label) throws FileNotFoundException {
		LabelReader lr = new LabelReader(label);
		LabelStore ls = lr.getLs();
		ILabelSelection ls_t0 = new LabelSelection(new FeaturesSelection(new CsrMatrixClustered(new MatrixFeatureLabels(new CsrMatrix(new ArcsReader(matrix)), ls), new ClusteringReader(cluster).getClusters())));
		return ls_t0;
	}
}
