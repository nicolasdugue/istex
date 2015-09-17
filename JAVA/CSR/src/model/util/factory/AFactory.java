package model.util.factory;

import java.io.FileNotFoundException;

import io.reader.ClusteringReader;
import io.reader.LabelReader;
import io.reader.factory.FactoryReader;
import model.diachronism.LabelDiachronism;
import model.featureselection.FeaturesSelection;
import model.featureselection.IFeaturesSelection;
import model.featureselection.ILabelSelection;
import model.featureselection.LabelSelection;
import model.featureselection.LabelSelectionFromFile;
import model.matrix.CsrMatrix;
import model.matrix.CsrMatrixClustered;
import model.matrix.decorator.MatrixFeatureLabels;
import model.util.LabelStore;

public abstract class AFactory {
	private FactoryReader fr;
		
	public AFactory(FactoryReader fr) {
		super();
		this.fr = fr;
	}

	public LabelDiachronism matchingFromLabel(String fileNameSrc, String fileNameTarget) throws FileNotFoundException {
		ILabelSelection ls_t0 = new LabelSelectionFromFile(fileNameSrc);
		ILabelSelection ls_t1 = new LabelSelectionFromFile(fileNameTarget);
		LabelDiachronism ld = new LabelDiachronism(ls_t0, ls_t1);
		return ld;
	}

	public LabelDiachronism matchingFromMatrixCluster(String matrix1, String matrix2, String cluster1,
			String cluster2) throws FileNotFoundException {
		ILabelSelection ls_t0 = getLabelsFromMatrixClusters(matrix1, cluster1);
		ILabelSelection ls_t1 = getLabelsFromMatrixClusters(matrix2, cluster2);
		LabelDiachronism ld = new LabelDiachronism(ls_t0, ls_t1);
		return ld;
	}

	public LabelDiachronism matchingFromMatrixClusterLabels(String matrix1, String matrix2, String cluster1,
			String cluster2, String label1, String label2) throws FileNotFoundException {
		ILabelSelection ls_t0 = getLabelsFromMatrixClustersLabels(matrix1, cluster1, label1);
		ILabelSelection ls_t1 = getLabelsFromMatrixClustersLabels(matrix2, cluster2, label2);
		LabelDiachronism ld = new LabelDiachronism(ls_t0, ls_t1);
		return ld;
	}

	public ILabelSelection getLabelsFromMatrixClusters(String matrix, String cluster)
			throws FileNotFoundException {
		ILabelSelection ls_t0 = new LabelSelection(new FeaturesSelection(new CsrMatrixClustered(new CsrMatrix(fr.getReader(matrix)), new ClusteringReader(cluster).getClusters())));
		return ls_t0;
	}
	
	public ILabelSelection getLabelsFromMatrixClustersLabels(String matrix,  String cluster, String label) throws FileNotFoundException {
		LabelReader lr = new LabelReader(label);
		LabelStore ls = lr.getLs();
		ILabelSelection ls_t0 = new LabelSelection(new FeaturesSelection(new CsrMatrixClustered(new MatrixFeatureLabels(new CsrMatrix(fr.getReader(matrix)), ls), new ClusteringReader(cluster).getClusters())));
		return ls_t0;
	}
	
	public  IFeaturesSelection getFeatureSelecter(String matrix, String cluster) throws FileNotFoundException {
		return new FeaturesSelection(new CsrMatrixClustered(new CsrMatrix(fr.getReader(matrix)), new ClusteringReader(cluster).getClusters()));
	}
	public  IFeaturesSelection getGraphFeatureSelecter(String matrix, String cluster, String labels) throws FileNotFoundException {
		return new FeaturesSelection(new CsrMatrixClustered(new MatrixFeatureLabels(new CsrMatrix(fr.getReader(matrix)), new LabelReader(labels).getLs()), new ClusteringReader(cluster).getClusters()));
	}

}
