package model.diachronism.clustermatching;

import java.util.Set;
import java.util.TreeSet;

import com.google.gson.annotations.SerializedName;

import model.util.PairFWeighted;
import model.util.WeightComparator;

public class ClusterLabelled {
	@SerializedName("Name")
	private String name;
	@SerializedName("Labels")
	private TreeSet<PairFWeighted> labels = new TreeSet<PairFWeighted>(new WeightComparator());
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ClusterLabelled [name=" + name + ", labels=" + labels + "]";
	}
	/**
	 * @param e
	 * @return
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	public boolean add(PairFWeighted e) {
		return labels.add(e);
	}
	public Set<PairFWeighted> getLabels() {
		return labels;
	}
}
