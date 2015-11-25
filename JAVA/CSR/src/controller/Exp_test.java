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
		pl(Quality.getConductance(0,amc));
		pl(Quality.getCutRatio(1,amc));
		/*
		// pl("Nombre de clusters : "+amc.getNbCluster());
		int nbClusters = amc.getNbCluster();
		int nbNodes = 0;
		int ms = 0;
		int cs = 0;
		pl(amc.getMatrix().getRow(0)[0]);
		for (int cluster = 0; cluster < nbClusters; cluster++) {
			for (int i : amc.getObjectsInCk(cluster)) {
				pl("Noeud dans le cluster " + cluster + " : " + i);
				nbNodes++;
			}
		}
		for (int j = 0; j < nbNodes; j++) {
			
			for (int i = 0; i < amc.getMatrix().getRow(j).length; i++) 
			{
				
				pl("liaison du noeud " +j+" avec le noeud "+amc.getMatrix().getRow(j)[i].getLeft()+
				(amc.getClusterOfObjectI(j)==amc.getClusterOfObjectI(amc.getMatrix().getRow(j)[i].getLeft())? " qui se trouve dans le m�me cluster" : " qui ne se trouve pas dans le m�me cluster"));
				
			}
			
		}
		pl("nombre de noeuds : " + nbNodes);
		/*
		 * MatrixReader mr = new
		 * MatrixReader("exemples/matrix_lamirel_iskomaghreb"); IMatrix im = new
		 * CsrMatrix(mr);
		 * System.out.println("La somme des lignes : "+mr.getSumRow());
		 * System.out.println("La somme des colonnes : "+mr.getSumCol());
		 * for(int i=0;i<mr.getMatrix_columns().size();i++)
		 * System.out.println("getCol"+mr.getMatrix_rows().get(i));
		 */
		/*
		 * ClusteringReader cr = new
		 * ClusteringReader("exemples/clustering_lamirel_iskomaghreb"); IMatrix
		 * im = new CsrMatrix(mr); CsrMatrixClustered mc = new
		 * CsrMatrixClustered( im, cr.getClusters()); FeaturesSelection fs = new
		 * FeaturesSelection(mc);
		 * 
		 * 
		 * pl("nombre de clusters : " + fs.getNbCluster());
		 * 
		 * pl(Quality.getIntraDistance(mc)); pl(Quality.getInterDistance(mc));
		 * pl(Quality.getCH(mc));
		 * 
		 * for (int i =0 ; i<fs.getNbCluster();i++ )
		 * 
		 * for (int j = 0; j < fs.getObjectsInCk(i).size();j++) {
		 * Quality.getContrast(fs, j, i); //pl("le contrast de l'objet : " + j +
		 * " dans le cluster " + i+" " + Quality.getContrast(fs, j, i)); }
		 * //pl("-------------------\n" +Quality.getPC(fs)); /* for(cl :
		 * fs.getMatrix().get) { pl(Quality.getContrast(fs, i, 1)); }
		 * 
		 * /*Iterator I = fs.getFeaturesSelected().iterator(); while
		 * (I.hasNext()){ pl(I.next()); } pl(cr.getClusters().
		 */

		// Start

		// fs.getMatrix().getMatrix().getRow(i)

		// pl(mc.getNbCluster());

		// ArcsReader ar = new ArcsReader("exemples/Guimera_matrix");
		/*
		 * ArcsReader ar = new ArcsReader("exemples/matrix_arcs_exp_trello");
		 * ArcsReader ar_sub = new
		 * ArcsReader("exemples/matrix_arcs_sub_trello");
		 * 
		 * pl(ar.getNb_elmt()); System.out.println(ar.getSumRow());
		 * System.out.println(ar.getSumCol());
		 * 
		 * for (int i=0;i<ar_sub.getMatrix_columns().size();i++){
		 * System.out.println
		 * ("La colonne "+i+" : "+ar_sub.getMatrix_columns().get(i));
		 * 
		 * } for (int i=0;i<ar_sub.getMatrix_columns().size();i++){
		 * System.out.println
		 * ("La ligne "+i+" : "+ar_sub.getMatrix_rows().get(i));
		 * 
		 * }
		 * 
		 * pl(" n = " + ar.getSumRow()); pl(" m = " + ar.getNb_elmt());
		 * 
		 * pl(" nsub = " + ar_sub.getSumRow()); pl(" msub = " +
		 * ar_sub.getNb_elmt());
		 * 
		 * 
		 * 
		 * //System.out.println("La somme des lignes : "+ar.getSumRow());
		 * //System.out.println("La somme des colonnes : "+ar.getSumCol());
		 * 
		 * 
		 * /*private void read() throws FileNotFoundException { Scanner sc = new
		 * Scanner(new File("exemples/matrix_lamirel_iskomaghreb")); String
		 * line; Scanner lineReader; float xij; float sum_row_xij = 0f; int
		 * row_i=0; int col_i=0; ArrayList<PairF > row;
		 * 
		 * LinkedList lr = new LinkedList(); LinkedList lc = new LinkedList();
		 * 
		 * while (sc.hasNextLine()) { line=sc.nextLine(); lineReader= new
		 * Scanner(line); row = new ArrayList<PairF >(); col_i=0; sum_row_xij =
		 * 0f; ListIterator<Float> lic = lc.listIterator(); while
		 * (lineReader.hasNextFloat()) { xij=lineReader.nextFloat(); if (row_i
		 * == 0) { // Première ligne matrix_columns.add(new ArrayList<PairF
		 * >()); lc.add(xij); } else if (xij != 0) { lic.set(lic.next()+xij);
		 * row.add(new PairF(col_i, xij)); matrix_columns.get(col_i).add(new
		 * PairF(row_i, xij)); this.nb_elmt++; sum_row_xij = sum_row_xij +xij;
		 * 
		 * } col_i++; } lineReader.close(); nb_columns=col_i; row_i++;
		 * lr.add(sum_row_xij); matrix_rows.add(row); } nb_rows=row_i;
		 * sc.close(); } //
		 */

		/*
		 * LinkedList ll = new LinkedList(); for(int i=0;i<10;i++) {
		 * ll.add((float) i); }
		 * 
		 * System.out.println("Original contents of ll: " + ll); //Integer e =
		 * new Integer(6); ListIterator<Float> LI = ll.listIterator();
		 * while(LI.hasNext()) {
		 * 
		 * 
		 * // LI.next();
		 * 
		 * }
		 * 
		 * System.out.println("Modified contents of ll: " + ll);
		 * 
		 * 
		 * //ArrayList<Float> test = new ArrayList<Float>();
		 * 
		 * //test.add(5, 8f); // //
		 */
	}
}
