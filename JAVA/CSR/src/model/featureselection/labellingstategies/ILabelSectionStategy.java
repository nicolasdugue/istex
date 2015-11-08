package model.featureselection.labellingstategies;

import java.util.Collection;

public interface ILabelSectionStategy {
	public Collection<Integer> getLabelCluster(int cluster);
	public void clear();
}
