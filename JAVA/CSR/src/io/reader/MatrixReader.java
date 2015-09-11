package io.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import model.util.nuplet.Pair;
import model.util.nuplet.PairI;

/**
 * Allows to Read a Matrix and to store it in a temporary manner.
 * <br>CsrMatrix Object is able to use this temporary storage in order to build a new, actual CSR/CSC matrix
 * <br>File is supposed to by organised this way :
 * <br>-one matrix row per line of the file
 * <br>-matrix weights can be separated with space or tabs
 * 
 * @author dugue
 * 
 *
 */
public class MatrixReader implements IMatrixReader {

	
	private String fileName;
	private ArrayList<ArrayList<PairI > > matrix_rows = new ArrayList<ArrayList<PairI >>();
	private ArrayList<ArrayList<PairI > > matrix_columns= new ArrayList<ArrayList<PairI >>();
	private int nb_rows;
	private int nb_columns;
	private int nb_elmt;
	public MatrixReader(String fileName) throws FileNotFoundException {
		super();
		this.nb_elmt=0;
		this.fileName = fileName;
		read();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private void read() throws FileNotFoundException {
		Scanner sc = new Scanner(new File(fileName));
		String line;
		Scanner lineReader;
		int xij;
		int row_i=0;
		int col_i=0;
		ArrayList<PairI > row;
		while (sc.hasNextLine()) {
			line=sc.nextLine();
			lineReader= new Scanner(line);
			row = new ArrayList<PairI >();
			col_i=0;
			while (lineReader.hasNextInt()) {
				xij=lineReader.nextInt();
				if (row_i == 0) { // Première ligne
					matrix_columns.add(new ArrayList<PairI >());
				}
				if (xij != 0) {
					row.add(new PairI(col_i, xij));
					matrix_columns.get(col_i).add(new PairI(row_i, xij));
					this.nb_elmt++;
				}
				col_i++;
			}
			nb_columns=col_i;
			row_i++;
			matrix_rows.add(row);
		}
		nb_rows=row_i;
		sc.close();
	}
	public Pair<Integer,Integer> getXijRows(int i, int j) {
		return matrix_rows.get(i).get(j);
	}
	public Pair<Integer,Integer> getXijColumns(int i, int j) {
		return matrix_columns.get(i).get(j);
	}
	
	
	public ArrayList<ArrayList<PairI>> getMatrix_rows() {
		return matrix_rows;
	}

	public void setMatrix_rows(ArrayList<ArrayList<PairI>> matrix_rows) {
		this.matrix_rows = matrix_rows;
	}

	public ArrayList<ArrayList<PairI>> getMatrix_columns() {
		return matrix_columns;
	}

	public void setMatrix_columns(ArrayList<ArrayList<PairI>> matrix_columns) {
		this.matrix_columns = matrix_columns;
	}

	public int getNb_rows() {
		return nb_rows;
	}

	public void setNb_rows(int nb_rows) {
		this.nb_rows = nb_rows;
	}

	public int getNb_columns() {
		return nb_columns;
	}

	public void setNb_columns(int nb_columns) {
		this.nb_columns = nb_columns;
	}

	public int getNb_elmt() {
		return nb_elmt;
	}

	public void setNb_elmt(int nb_elmt) {
		this.nb_elmt = nb_elmt;
	}

}
