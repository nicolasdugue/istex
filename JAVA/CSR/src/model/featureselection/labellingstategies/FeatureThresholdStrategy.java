package model.featureselection.labellingstategies;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import model.featureselection.IFeaturesSelection;
import util.SGLogger;

public class FeatureThresholdStrategy implements ILabelSelectionStrategy {
	private IFeaturesSelection fs;
	private float threshold=0.025f;

	public FeatureThresholdStrategy() {
		super();
	}
	public FeatureThresholdStrategy(IFeaturesSelection fs) {
		super();
		this.fs = fs;
	}
	public FeatureThresholdStrategy(IFeaturesSelection fs, float t) {
		this(fs);
		this.threshold=t;
		
	}
	
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
	
	@Override
	public Collection<Integer> getLabelCluster(int cluster) {
		Logger log = SGLogger.getInstance();
		List<Integer> l = new LinkedList<Integer>();
		float ff;
		for (int f=0; f < fs.getNbFeatures(); f++) {
			ff=fs.getFeatureValue(f, cluster);
			if (ff > threshold) {
				log.debug("Feature : " + fs.getLabelOfCol(f)+ " > threshold : " + ff);
				l.add(f);
			}
		}
		return l;
	}

	@Override
	public void clear() {
		
	}

}
