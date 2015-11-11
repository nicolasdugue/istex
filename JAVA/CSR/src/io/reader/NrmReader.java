package io.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

import io.reader.interfaces.IMatrixReader;
import model.util.nuplet.PairF;
import util.Memory;

public class NrmReader implements IMatrixReader {

	private String fileName;
	private ArrayList<ArrayList<PairF > > matrix_rows = new ArrayList<ArrayList<PairF >>();
	private ArrayList<ArrayList<PairF > > matrix_columns= new ArrayList<ArrayList<PairF >>();
	private int nb_rows;
	private int nb_columns;
	private int nb_elmt;
	private LinkedList sumRow = new LinkedList();
	private LinkedList sumCol = new LinkedList();
	
	
	public NrmReader(String fileName) throws FileNotFoundException {
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
		float xij;
		float sum_row_xij = 0f;
		int row_i=0;
		int col_i=0;
		ArrayList<PairF > row;
		
		//first
		if(sc.hasNextLine()){
			nb_columns=Integer.parseInt(sc.nextLine());	
		}
		
		while (sc.hasNextLine()) {
			line=sc.nextLine();
			lineReader= new Scanner(line);
			row = new ArrayList<PairF >();
			col_i=0;
			sum_row_xij = 0f;
			ListIterator<Float> lic = sumCol.listIterator();
			while (col_i < nb_columns) {
				xij=lineReader.nextFloat();
				if (row_i == 0) { // Première ligne
					matrix_columns.add(new ArrayList<PairF >());
					sumCol.add(xij);
					
					if (xij != 0) {
						lic.set(lic.next()+xij);
						row.add(new PairF(col_i, xij));
						matrix_columns.get(col_i).add(new PairF(row_i, xij));
						this.nb_elmt++;
						sum_row_xij = sum_row_xij +xij;
					
					}
				
				}
				
				else if (xij != 0) {
					row.add(new PairF(col_i, xij));
					matrix_columns.get(col_i).add(new PairF(row_i, xij));
					this.nb_elmt++;
					sum_row_xij = sum_row_xij +xij;
				}
				col_i++;
			}
			sumRow.add(sum_row_xij);
			row_i++;
			matrix_rows.add(row);
			lineReader.close();
			
		}
		nb_rows=row_i;
		sc.close();
	}
	public LinkedList getSumRow() {
		return sumRow;
	}

	public void setSumRow(LinkedList sumRow) {
		this.sumRow = sumRow;
	}

	public LinkedList getSumCol() {
		return sumCol;
	}

	public void setSumCol(LinkedList sumCol) {
		this.sumCol = sumCol;
	}

	public PairF getXijRows(int i, int j) {
		return matrix_rows.get(i).get(j);
	}
	public PairF getXijColumns(int i, int j) {
		return matrix_columns.get(i).get(j);
	}
	
	
	public ArrayList<ArrayList<PairF>> getMatrix_rows() {
		return matrix_rows;
	}

	public void setMatrix_rows(ArrayList<ArrayList<PairF>> matrix_rows) {
		this.matrix_rows = matrix_rows;
	}

	public ArrayList<ArrayList<PairF>> getMatrix_columns() {
		return matrix_columns;
	}

	public void setMatrix_columns(ArrayList<ArrayList<PairF>> matrix_columns) {
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
	public void clear() {
		for (ArrayList l : matrix_rows)
			l.clear();
		for (ArrayList l : matrix_columns)
			l.clear();
		matrix_rows.clear();
		matrix_columns.clear();
		Memory.garbageCollector();		
	}

}
