package model.matrix.decorator;

import java.util.List;

import model.util.nuplet.PairF;

public abstract class MatrixDecorator implements IMatrix {
	protected IMatrix m;
	
	public MatrixDecorator(IMatrix m) {
		super();
		this.m = m;
	}

	/*@Override
	public int getCumulativeRows(int i) {
		return m.getCumulativeRows(i);
	}

	@Override
	public int getCumulativeColumns(int i) {
		return m.getCumulativeColumns(i);
	}*/

/*	@Override
	public PairI getIinRows(int i) {
		return m.getIinRows(i);
	}

	@Override
	public PairI getIinColumns(int i) {
		return m.getIinColumns(i);
	}*/

	@Override
	public int getNbElements() {
		return m.getNbElements();
	}

	@Override
	public int getNbRows() {
		return m.getNbRows();
	}

	public List<PairF> getRow(int i) {
		return m.getRow(i);
	}

	public List<PairF> getColumn(int j) {
		return m.getColumn(j);
	}

	@Override
	public int getNbColumns() {
		return m.getNbColumns();
	}

	@Override
	public float getSumRow(int i) {
		return m.getSumRow(i);
	}

	@Override
	public float getSumCol(int i) {
		return m.getSumCol(i);
	}

	@Override
	public  String getLabelOfCol(int j) {
		return m.getLabelOfCol(j);
	}

	@Override
	public String getLabelOfRow(int i) {
		return m.getLabelOfRow(i);
	}

	public int getIndexOfRowLabel(String label) {
		return m.getIndexOfRowLabel(label);
	}

	public int getIndexOfColLabel(String label) {
		return m.getIndexOfColLabel(label);
	}

}
