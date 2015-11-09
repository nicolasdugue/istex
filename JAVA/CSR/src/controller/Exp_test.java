package controller;

import java.awt.Window.Type;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

import model.featureselection.FeaturesSelection;
import model.matrix.CsrMatrix;
import model.matrix.CsrMatrixClustered;
import model.matrix.decorator.IMatrix;
import model.matrix.decorator.MatrixFeatureLabels;
import model.util.nuplet.PairF;
import io.reader.ClusteringReader;
import io.reader.LabelReader;
import io.reader.MatrixReader;

public class Exp_test {

	public static void main(String[] args) throws IOException {

		MatrixReader mr = new MatrixReader("exemples/matrix_lamirel_iskomaghreb");
		System.out.println("La somme des lignes : "+mr.getSumRow());
		System.out.println("La somme des colonnes : "+mr.getSumCol());
		ClusteringReader cr = new ClusteringReader("exemples/clustering_lamirel_iskomaghreb");
		IMatrix im = new CsrMatrix(mr);
		CsrMatrixClustered mc = new CsrMatrixClustered( im, cr.getClusters());
		FeaturesSelection fs = new FeaturesSelection(mc);
		
		
		
		/*private void read() throws FileNotFoundException {
		Scanner sc = new Scanner(new File("exemples/matrix_lamirel_iskomaghreb"));
		String line;
		Scanner lineReader;
		float xij;
		float sum_row_xij = 0f;
		int row_i=0;
		int col_i=0;
		ArrayList<PairF > row;
		
		LinkedList lr = new LinkedList();
		LinkedList lc = new LinkedList();

		while (sc.hasNextLine()) {
			line=sc.nextLine();
			lineReader= new Scanner(line);
			row = new ArrayList<PairF >();
			col_i=0;
			sum_row_xij = 0f;
			ListIterator<Float> lic = lc.listIterator();
			while (lineReader.hasNextFloat()) {
				xij=lineReader.nextFloat();
				if (row_i == 0) { // Première ligne
					matrix_columns.add(new ArrayList<PairF >());
					lc.add(xij);
				}
				else if (xij != 0) {
					lic.set(lic.next()+xij);
					row.add(new PairF(col_i, xij));
					matrix_columns.get(col_i).add(new PairF(row_i, xij));
					this.nb_elmt++;
					sum_row_xij = sum_row_xij +xij;
					
				}
				col_i++;
			}
			lineReader.close();
			nb_columns=col_i;
			row_i++;
			lr.add(sum_row_xij);
			matrix_rows.add(row);
		}
		nb_rows=row_i;
		sc.close();
	}
	//*/

	/*LinkedList ll = new LinkedList();
	  for(int i=0;i<10;i++)
	  {
	  	 ll.add((float) i);  
	  }
	      
	  System.out.println("Original contents of ll: " + ll);
	  //Integer e = new Integer(6);
	  ListIterator<Float> LI = ll.listIterator();
	  while(LI.hasNext())
	  {
		   
		   
		  // LI.next();
		  
	  }
	
		  System.out.println("Modified contents of ll: " + ll);

	      
		//ArrayList<Float> test = new ArrayList<Float>();
		
		//test.add(5, 8f);
		//
		//*/
	}

}
