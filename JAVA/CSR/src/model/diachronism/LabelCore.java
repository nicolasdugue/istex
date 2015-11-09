package model.diachronism;

import java.util.TreeSet;

import com.google.gson.annotations.SerializedName;

import model.util.nuplet.TripletLabel;
import model.util.nuplet.comparator.TripletComparatorTarget;

public class LabelCore {
	@SerializedName("Cluster Source")
	private String s;
	@SerializedName("Cluster Target")
	private String t;
	private transient TripletComparatorTarget comparator = new TripletComparatorTarget();
	@SerializedName("Kernel Labels")
	private TreeSet<TripletLabel> kernelLabels = new TreeSet<TripletLabel>();
	@SerializedName("Common Labels prevalent in Source")
	private TreeSet<TripletLabel> commonSourceLabels = new TreeSet<TripletLabel>();
	@SerializedName("Common Labels prevalent in Target")
	private TreeSet<TripletLabel> commonTargetLabels = new TreeSet<TripletLabel>(comparator);
	@SerializedName("Source Labels")
	private transient TreeSet<TripletLabel> sourceLabels = new TreeSet<TripletLabel>();
	@SerializedName("Target Labels")
	private transient TreeSet<TripletLabel> targetLabels = new TreeSet<TripletLabel>(comparator);
	
	
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s = s;
	}
	public String getT() {
		return t;
	}
	public void setT(String t) {
		this.t = t;
	}
	public boolean addKernel(TripletLabel e) {
		return kernelLabels.add(e);
	}
	public boolean addCommonSource(TripletLabel e) {
		return commonSourceLabels.add(e);
	}
	public boolean addCommonTarget(TripletLabel e) {
		return commonTargetLabels.add(e);
	}
	public boolean addSource(TripletLabel e) {
		return sourceLabels.add(e);
	}
	public boolean addTarget(TripletLabel e) {
		return targetLabels.add(e);
	}
	public TreeSet<TripletLabel> getKernelLabels() {
		return kernelLabels;
	}
	public TreeSet<TripletLabel> getSourceLabels() {
		return sourceLabels;
	}
	public TreeSet<TripletLabel> getTargetLabels() {
		return targetLabels;
	}
	
}
