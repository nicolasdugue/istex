package io.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import org.apache.log4j.Logger;

import io.reader.interfaces.IMatrixReader;
import model.util.nuplet.PairF;
import util.Memory;
import util.SGLogger;

/**
 * 
 * Allows
 * 
 * @author nicolas
 *
 */
public class ArcsReader implements IMatrixReader {
	private String filename;
	private ArrayList<ArrayList<PairF > > matrix_rows = new ArrayList<ArrayList<PairF >>();
	private ArrayList<ArrayList<PairF > > matrix_columns= new ArrayList<ArrayList<PairF >>();
	
	private ArrayList<Float> sumRow = new ArrayList<Float>();
	private ArrayList<Float> sumCol = new ArrayList<Float>();
	
	
	private int nb_rows;
	private int nb_columns;
	private int nb_elmt;
	public ArcsReader(String filename) throws FileNotFoundException {
		super();
		this.filename = filename;
		this.nb_elmt=0;
		this.nb_columns=0;
		this.nb_rows=0;
		read();
	}
	private void read() throws FileNotFoundException {
		Scanner sc = new Scanner(new File(filename));
		String line;
		String[] arc;
		int src;
		int dst;
		float weight;
		while (sc.hasNextLine()) {
			line=sc.nextLine();
			arc=line.split(" ");
			if (arc.length < 2) {
				arc=line.split("\t");
			}
			src=Integer.parseInt(arc[0]);
			dst=Integer.parseInt(arc[1]);
			if (arc.length > 2) {
				weight=Float.parseFloat(arc[2]);
			}
			else {
				weight=1;
			}
			if (Math.max(src, dst) > (Math.min(matrix_columns.size(), matrix_rows.size()) - 1)) {
				for (int i=matrix_rows.size(); i <= Math.max(src, dst); i++) {
					matrix_rows.add(new ArrayList<PairF >());
					sumRow.add(new Float(0));
				}
				for (int i=matrix_columns.size(); i <= Math.max(src, dst); i++) {
					matrix_columns.add(new ArrayList<PairF >());
					sumCol.add(new Float(0));
				}
			}
			matrix_rows.get(src).add(new PairF(dst,weight));
			sumRow.set(src, sumRow.get(src)+weight);
			matrix_columns.get(dst).add(new PairF(src, weight));
			sumCol.set(dst, sumCol.get(dst)+weight);
			nb_elmt++;
		}
		nb_rows=matrix_rows.size();
		nb_columns=matrix_columns.size();
		sc.close();
	}

	public ArrayList<Float> getSumRow() {
		return sumRow;
	}
	public void setSumRow(ArrayList<Float> sumRow) {
		this.sumRow = sumRow;
	}
	public ArrayList<Float> getSumCol() {
		return sumCol;
	}
	public void setSumCol(ArrayList<Float> sumCol) {
		this.sumCol = sumCol;
	}
	@Override
	public String getFileName() {
		return filename;
	}

	@Override
	public void setFileName(String fileName) {
		this.filename=fileName;		
	}

	@Override
	public ArrayList<ArrayList<PairF>> getMatrix_rows() {
		return matrix_rows;
	}

	@Override
	public ArrayList<ArrayList<PairF>> getMatrix_columns() {
		return matrix_columns;
	}

	@Override
	public int getNb_rows() {
		return nb_rows;
	}

	@Override
	public int getNb_columns() {
		return nb_columns;
	}

	@Override
	public int getNb_elmt() {
		return nb_elmt;
	}
	
	public void clear() {
		for (ArrayList l : matrix_rows)
			l.clear();
		for (ArrayList l : matrix_columns)
			l.clear();
		matrix_rows.clear();
		matrix_columns.clear();
		sumCol.clear();
		sumRow.clear();
		Memory.garbageCollector();		
	}

}
