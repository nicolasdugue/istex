package controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.featureselection.FeaturesSelection;
import model.matrix.CsrMatrix;
import model.matrix.CsrMatrixClustered;
import model.matrix.decorator.IMatrix;
import model.matrix.decorator.MatrixFeatureLabels;
import io.reader.ClusteringReader;
import io.reader.LabelReader;
import io.reader.MatrixReader;

public class Exp_test {

	public static void main(String[] args) throws IOException {

		MatrixReader mr = new MatrixReader("exemples/matrix_lamirel_iskomaghreb");
		System.out.println("La somme des poids : "+mr.getSum_elmt());
		ClusteringReader cr = new ClusteringReader("exemples/clustering_lamirel_iskomaghreb");
		IMatrix im = new CsrMatrix(mr);
		CsrMatrixClustered mc = new CsrMatrixClustered( im, cr.getClusters());
		FeaturesSelection fs = new FeaturesSelection(mc);
		fs.get
		
		
		
	}

}
