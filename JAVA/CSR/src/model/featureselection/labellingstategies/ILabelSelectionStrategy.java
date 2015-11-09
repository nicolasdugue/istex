package model.featureselection.labellingstategies;

import java.util.Collection;

import model.featureselection.IFeaturesSelection;

public interface ILabelSelectionStrategy {
	public Collection<Integer> getLabelCluster(int cluster);
	public void clear();
	public void setFs(IFeaturesSelection fs);
}
