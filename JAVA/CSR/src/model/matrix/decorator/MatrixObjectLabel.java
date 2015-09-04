package model.matrix.decorator;

import model.util.LabelStore;

/**
 * Allows to decorate an IMatrix by adding a matching between rows index and labels
 * 
 * @author dugue
 *
 */
public class MatrixObjectLabel extends MatrixDecorator {

	private LabelStore objectsLabels;
	public MatrixObjectLabel(IMatrix m) {
		super(m);
	}
	public MatrixObjectLabel(IMatrix m, LabelStore objectsLabels) {
		super(m);
		this.objectsLabels = objectsLabels;
	}
	@Override
	public String getLabelOfRow(int i) {
		return objectsLabels.getLabel(i);
	}
	@Override
	public int getIndexOfRowLabel(String label) {
		return objectsLabels.getIndexOfLabel(label);
	}

}
