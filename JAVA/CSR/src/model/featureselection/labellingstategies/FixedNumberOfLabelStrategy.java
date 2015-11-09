package model.featureselection.labellingstategies;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import model.featureselection.IFeaturesSelection;
import model.util.nuplet.PairF;
import model.util.nuplet.collection.SortedFeatureSet;
import util.Memory;

public class FixedNumberOfLabelStrategy implements ILabelSelectionStrategy {
	private IFeaturesSelection fs;
	private int numberOfLabels = 5;
	
	public FixedNumberOfLabelStrategy() {
		super();
	}
	
	public FixedNumberOfLabelStrategy(IFeaturesSelection fs) {
		super();
		this.fs = fs;
	}

	public FixedNumberOfLabelStrategy(IFeaturesSelection fs, int numberOfLabels) {
		super();
		this.fs = fs;
		this.numberOfLabels = numberOfLabels;
	}

	public IFeaturesSelection getFs() {
		return fs;
	}

	public void setFs(IFeaturesSelection fs) {
		this.fs = fs;
	}

	public int getNumberOfLabels() {
		return numberOfLabels;
	}

	public void setNumberOfLabels(int numberOfLabels) {
		this.numberOfLabels = numberOfLabels;
	}

	
	@Override
	public Collection<Integer> getLabelCluster(int cluster) {
		SortedFeatureSet s = new SortedFeatureSet();
		for (int f=0; f < fs.getNbFeatures(); f++) {
			 s.add(new PairF(f, fs.getFeatureValue(f, cluster)));
		 }
		LinkedList<Integer>  l = new LinkedList<Integer>();
		Iterator<PairF> it = s.iterator();
		for (int i=0; i < numberOfLabels; i++) {
			l.add(it.next().getLeft());
		}
		s.clear();
		return l;
	}

	@Override
	public void clear() {
		Memory.garbageCollector();
		
	}

}
