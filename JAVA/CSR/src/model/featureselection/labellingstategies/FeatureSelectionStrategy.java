package model.featureselection.labellingstategies;

import java.util.Collection;

import model.featureselection.IFeaturesSelection;

public class FeatureSelectionStrategy implements ILabelSelectionStrategy {
	private IFeaturesSelection fs;

	public FeatureSelectionStrategy() {
		super();
	}
	public FeatureSelectionStrategy(IFeaturesSelection fs) {
		super();
		this.fs = fs;
	}

	public IFeaturesSelection getFs() {
		return fs;
	}

	public void setFs(IFeaturesSelection fs) {
		this.fs = fs;
	}

	public Collection<Integer> getLabelCluster(int cluster) {
		return fs.getFeaturesSelected(cluster);
	}

	public void clear() {
		
	}
	
}
