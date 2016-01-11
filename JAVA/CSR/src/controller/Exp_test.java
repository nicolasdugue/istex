package controller;

import java.awt.Window.Type;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

import model.cluster.Clustering;
import model.featureselection.FeaturesSelection;
import model.matrix.CsrMatrix;
import model.matrix.CsrMatrixClustered;
import model.matrix.decorator.IMatrix;
import model.matrix.decorator.MatrixFeatureLabels;
import model.quality.Quality;
import model.util.nuplet.PairF;
import io.reader.ArcsReader;
import io.reader.ClusteringReader;
import io.reader.LabelReader;
import io.reader.MatrixReader;
import io.reader.interfaces.IMatrixReader;

public class Exp_test {
	public static void p(Object O) {
		System.out.print(O);
	}

	public static void pl(Object O) {
		System.out.println(O);
	}

	public static void main(String[] args) throws IOException {

		IMatrixReader amr = new ArcsReader("exemples/Guimera_matrix");
		ClusteringReader acr = new ClusteringReader(
				"exemples/Guimera_community");
		IMatrix aim = new CsrMatrix(amr);
		CsrMatrixClustered amc = new CsrMatrixClustered(aim, acr.getClusters());
		//pl(Quality.getConductance(0,amc));
		//pl(Quality.getCutRatio(1,amc));
		
		MatrixReader mr = new MatrixReader("exemples/matrix_lamirel_iskomaghreb"); 
		ClusteringReader cr = new ClusteringReader("exemples/clustering_lamirel_iskomaghreb");
		IMatrix im = new CsrMatrix(mr);
		
		CsrMatrixClustered mc = new CsrMatrixClustered(im, cr.getClusters());
	
		pl(Quality.getDiameter(0, mc));
	}
}
