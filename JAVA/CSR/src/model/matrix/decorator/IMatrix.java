package model.matrix.decorator;


import java.util.List;

import model.util.nuplet.PairF;

public interface IMatrix {
	/*public int getCumulativeRows(int i);
	public int getCumulativeColumns(int i);
	public PairI getIinRows(int i);
	public PairI getIinColumns(int j);*/
	
	/**
	 * @param i the row index
	 * @return A list of integer pairs describing the i-th matrix row such as (j, w) where j is the column of the i-th row element and w its weight
	 */
	public PairF[] getRow(int i);
	/**
	 * @param j the column index
	 * @return A list of integer pairs describing the j-th matrix column such as (i, w) where i is the row of the j-th column element and w its weight
	 */
	public PairF[] getColumn(int j);
	
	/**
	 * @return the number of non-null x_ij in the Matrix, basically less than to {@link #getNbRows()} * {@link #getNbColumns()}
	 */
	public int getNbElements();
	/**
	 * @return the number of rows
	 */
	public int getNbRows();
	/**
	 * @return the number of columns
	 */
	public int getNbColumns();
	
	/**
	 * @param i the row you need to get the sum
	 * @return sum over the i-th row of the matrix
	 */
	public float getSumRow(int i);
	
	/**
	 * @param i the column you need to get the sum
	 * @return sum over the i-th column of the matrix
	 */
	public float getSumCol(int i);
	
	
	/**
	 * @param j - the index of column corresponding to the feature one wants to obtain the label
	 * @return the label matching with the j-th matrix column
	 */
	public String getLabelOfCol(int j);
	
	/**
	 * @param i - the index of row corresponding to the object one wants to obtain the label
	 * @return the label matching with the i-th matrix row
	 */
	public String getLabelOfRow(int i);
	
	/**
	 * @param label - the label ones wants to get the id
	 * @return the i-th row matching with the label
	 */
	public int getIndexOfRowLabel(String label);
	
	/**
	 * @param label - the label ones wants to get the id
	 * @return the j-th column matching with the label
	 */
	public int getIndexOfColLabel(String label);


}
