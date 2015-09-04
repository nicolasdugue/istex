package io.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import model.util.PairI;

/**
 * @author nicolas
 *
 */
public class ArcsReader implements IMatrixReader {
	private String filename;
	private ArrayList<ArrayList<PairI > > matrix_rows = new ArrayList<ArrayList<PairI >>();
	private ArrayList<ArrayList<PairI > > matrix_columns= new ArrayList<ArrayList<PairI >>();
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
		int weight;
		while (sc.hasNextLine()) {
			line=sc.nextLine();
			arc=line.split(" ");
			if (arc.length < 2) {
				arc=line.split("\t");
			}
			src=Integer.parseInt(arc[0]);
			dst=Integer.parseInt(arc[1]);
			if (arc.length > 2) {
				weight=Integer.parseInt(arc[2]);
			}
			else {
				weight=1;
			}
			if (Math.max(src, dst) > (Math.min(matrix_columns.size(), matrix_rows.size()) - 1)) {
				for (int i=matrix_rows.size(); i <= Math.max(src, dst); i++) {
					matrix_rows.add(new ArrayList<PairI >());
				}
				for (int i=matrix_columns.size(); i <= Math.max(src, dst); i++) {
					matrix_columns.add(new ArrayList<PairI >());
				}
			}
			matrix_rows.get(src).add(new PairI(dst,weight));
			matrix_columns.get(dst).add(new PairI(src, weight));
			nb_elmt++;
		}
		nb_rows=matrix_rows.size();
		nb_columns=matrix_columns.size();
		sc.close();
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
	public ArrayList<ArrayList<PairI>> getMatrix_rows() {
		return matrix_rows;
	}

	@Override
	public ArrayList<ArrayList<PairI>> getMatrix_columns() {
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

}
