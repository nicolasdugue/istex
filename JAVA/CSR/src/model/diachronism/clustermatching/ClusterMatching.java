package model.diachronism.clustermatching;

import java.util.ArrayList;
import java.util.Set;

import com.google.gson.annotations.SerializedName;

import model.util.nuplet.PairFWeighted;

public class ClusterMatching {
	@SerializedName("Cluster Source")
	private ClusterLabelled cl;
	
	@SerializedName("Cluster Targets Matching")
	private ArrayList<ClusterLabelled> targets = new ArrayList<ClusterLabelled>();
	
	
	public ClusterMatching(ClusterLabelled s) {
		super();
		this.cl=s;
	}









	/**
	 * @return
	 * @see model.diachronism.clustermatching.ClusterLabelled#getName()
	 */
	public String getName() {
		return cl.getName();
	}


	/**
	 * @param name
	 * @see model.diachronism.clustermatching.ClusterLabelled#setName(java.lang.String)
	 */
	public void setName(String name) {
		cl.setName(name);
	}


	/**
	 * @param e
	 * @return
	 * @see model.diachronism.clustermatching.ClusterLabelled#add(model.util.PairFWeighted)
	 */
	public boolean add(PairFWeighted e) {
		return cl.add(e);
	}


	/**
	 * @param e
	 * @return
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	public boolean add(ClusterLabelled e) {
		return targets.add(e);
	}


	public Set<PairFWeighted> getLabels() {
		return cl.getLabels();
	}


	
	
	
}
