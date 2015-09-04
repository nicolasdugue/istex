package io.reader;

import java.util.ArrayList;

import model.util.PairI;

public interface IMatrixReader {

	public String getFileName();

	public void setFileName(String fileName);
	
	public ArrayList<ArrayList<PairI>> getMatrix_rows();

	public ArrayList<ArrayList<PairI>> getMatrix_columns();

	public int getNb_rows();

	public int getNb_columns();

	public int getNb_elmt();

	
}
