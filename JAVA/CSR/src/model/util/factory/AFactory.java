package model.util.factory;

import java.io.FileNotFoundException;
import java.io.IOException;

import io.reader.LabelReader;
import io.reader.factory.FactorySimpleClusteringReader;
import io.reader.factory.FactorySimpleLabelReader;
import io.reader.factory.interfaces.IFactoryClusteringReader;
import io.reader.factory.interfaces.IFactoryDataReader;
import io.reader.factory.interfaces.IFactoryLabelReader;
import model.diachronism.LabelDiachronism;
import model.featureselection.FeaturesSelection;
import model.featureselection.IFeaturesSelection;
import model.featureselection.ILabelSelection;
import model.featureselection.LabelSelection;
import model.featureselection.LabelSelectionFromFile;
import model.featureselection.labellingstategies.ILabelSelectionStrategy;
import model.matrix.CsrMatrix;
import model.matrix.CsrMatrixClustered;
import model.matrix.decorator.MatrixFeatureLabels;
import model.util.LabelStore;

public abstract class AFactory {
	private IFactoryDataReader fr;
	private IFactoryClusteringReader cr = new FactorySimpleClusteringReader();
	private IFactoryLabelReader lr = new FactorySimpleLabelReader();
	public AFactory(IFactoryDataReader fr) {
		super();
		this.fr = fr;
	}
	public AFactory(IFactoryDataReader fr, IFactoryClusteringReader cr) {
		super();
		this.fr = fr;
		this.cr=cr;
	}
	public AFactory(IFactoryDataReader fr, IFactoryClusteringReader cr, IFactoryLabelReader lr) {
		super();
		this.fr = fr;
		this.cr=cr;
		this.lr=lr;
	}

	public LabelDiachronism matchingFromLabel(String fileNameSrc, String fileNameTarget) throws IOException {
		ILabelSelection ls_t0 = new LabelSelectionFromFile(fileNameSrc);
		ILabelSelection ls_t1 = new LabelSelectionFromFile(fileNameTarget);
		LabelDiachronism ld = new LabelDiachronism(ls_t0, ls_t1);
		return ld;
	}

	public LabelDiachronism matchingFromMatrixCluster(String matrix1, String matrix2, String cluster1,
			String cluster2, ILabelSelectionStrategy lss) throws IOException {
		ILabelSelection ls_t0 = getLabelsFromMatrixClusters(matrix1, cluster1,lss);
		ILabelSelection ls_t1 = getLabelsFromMatrixClusters(matrix2, cluster2,lss);
		LabelDiachronism ld = new LabelDiachronism(ls_t0, ls_t1);
		return ld;
	}

	public LabelDiachronism matchingFromMatrixClusterLabels(String matrix1, String matrix2, String cluster1,
			String cluster2, String label1, String label2, ILabelSelectionStrategy lss) throws IOException {
		ILabelSelection ls_t0 = getLabelsFromMatrixClustersLabels(matrix1, cluster1, label1,lss);
		ILabelSelection ls_t1 = getLabelsFromMatrixClustersLabels(matrix2, cluster2, label2,lss);
		LabelDiachronism ld = new LabelDiachronism(ls_t0, ls_t1);
		return ld;
	}

	public ILabelSelection getLabelsFromMatrixClusters(String matrix, String cluster, ILabelSelectionStrategy lss)
			throws IOException {
		FeaturesSelection fs =new FeaturesSelection(new CsrMatrixClustered(new CsrMatrix(fr.getReader(matrix)), cr.getReader(cluster).getClusters()));
		lss.setFs(fs);
		ILabelSelection ls_t0 = new LabelSelection(fs, lss);
		return ls_t0;
	}
	
	public ILabelSelection getLabelsFromMatrixClustersLabels(String matrix,  String cluster, String label, ILabelSelectionStrategy lss) throws IOException {
		LabelReader lr = new LabelReader(label);
		LabelStore ls = lr.getLs();
		FeaturesSelection fs=new FeaturesSelection(new CsrMatrixClustered(new MatrixFeatureLabels(new CsrMatrix(fr.getReader(matrix)), ls), cr.getReader(cluster).getClusters()));
		lss.setFs(fs);
		ILabelSelection ls_t0 = new LabelSelection(fs, lss);
		return ls_t0;
	}
	
	public  IFeaturesSelection getFeatureSelecter(String matrix, String cluster) throws IOException {
		return new FeaturesSelection(new CsrMatrixClustered(new CsrMatrix(fr.getReader(matrix)), cr.getReader(cluster).getClusters()));
	}
	public  IFeaturesSelection getFeatureSelecter(String matrix, String cluster, String labels) throws IOException {
		return new FeaturesSelection(new CsrMatrixClustered(new MatrixFeatureLabels(new CsrMatrix(fr.getReader(matrix)), lr.getReader(labels).getLs()), cr.getReader(cluster).getClusters()));
	}
	public CsrMatrixClustered getMatrixClustered(String matrix, String cluster, String labels) throws FileNotFoundException, IOException{
		return new CsrMatrixClustered(new MatrixFeatureLabels(new CsrMatrix(fr.getReader(matrix)), lr.getReader(labels).getLs()), cr.getReader(cluster).getClusters());
	}
	public CsrMatrixClustered getMatrixClustered(String matrix, String cluster) throws FileNotFoundException, IOException{
		return new CsrMatrixClustered(new CsrMatrix(fr.getReader(matrix)), cr.getReader(cluster).getClusters());
	}

}
