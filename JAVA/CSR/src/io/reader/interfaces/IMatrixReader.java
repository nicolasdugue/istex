package io.reader.interfaces;

import java.util.AbstractList;
import java.util.ArrayList;
import model.util.nuplet.PairF;

public interface IMatrixReader {

	public String getFileName();

	public void setFileName(String fileName);
	
	public ArrayList<ArrayList<PairF>> getMatrix_rows();

	public ArrayList<ArrayList<PairF>> getMatrix_columns();

	public int getNb_rows();

	public int getNb_columns();

	public int getNb_elmt();
	
	public void clear();

	public AbstractList getSumRow();
	
	public AbstractList getSumCol();
}
