package model.featureselection.labellingstategies;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import model.featureselection.IFeaturesSelection;

public class FeatureThresholdStrategy implements ILabelSectionStategy {
	private IFeaturesSelection fs;
	private float threshold=0.025f;

	public IFeaturesSelection getFs() {
		return fs;
	}
	public void setFs(IFeaturesSelection fs) {
		this.fs = fs;
	}
	public float getThreshold() {
		return threshold;
	}
	public void setThreshold(float threshold) {
		this.threshold = threshold;
	}
	public FeatureThresholdStrategy(IFeaturesSelection fs) {
		super();
		this.fs = fs;
	}
	public FeatureThresholdStrategy(IFeaturesSelection fs, float t) {
		this(fs);
		this.threshold=t;
		
	}
	@Override
	public Collection<Integer> getLabelCluster(int cluster) {
		List<Integer> l = new LinkedList<Integer>();
		for (int f=0; f < fs.getNbFeatures(); f++) {
			if (fs.getFeatureValue(f, cluster) >= threshold)
				l.add(f);
		}
		return l;
	}

	@Override
	public void clear() {
		
	}

}
