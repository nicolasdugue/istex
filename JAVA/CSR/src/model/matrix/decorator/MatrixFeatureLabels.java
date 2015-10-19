package model.matrix.decorator;

import model.util.LabelStore;

/**
 * Allows to decorate an IMatrix by adding a matching between column index and labels
 * 
 * @author dugue
 *
 */
public class MatrixFeatureLabels extends MatrixDecorator{

	private LabelStore featuresLabels;

	public MatrixFeatureLabels(IMatrix m) {
		super(m);
	}
	public MatrixFeatureLabels(IMatrix m, LabelStore featuresLabels) {
		super(m);
		this.featuresLabels = featuresLabels;
	}
	@Override
	public String getLabelOfCol(int j) {
		return featuresLabels.getLabel(j);
	}
	
	@Override
	public int getIndexOfColLabel(String label) {
		return featuresLabels.getIndexOfLabel(label);
	}
	

}
