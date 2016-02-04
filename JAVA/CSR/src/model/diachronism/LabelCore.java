package model.diachronism;

import java.util.TreeSet;

import com.google.gson.annotations.SerializedName;

import model.util.nuplet.TripletLabel;
import model.util.nuplet.comparator.TripletComparatorTarget;

public class LabelCore{
	
	public enum Matching {
	    specialization, generalization, both//, bothwithspec, bothwithgen
	};
	
	@SerializedName("Cluster Source")
	private String s;
	@SerializedName("Source Name")
	private String ns;
	@SerializedName("Cluster Target")
	private String t;
	@SerializedName("Target Name")
	private String nt;
	private transient TripletComparatorTarget comparator = new TripletComparatorTarget();
	
	private Matching matchingType;
	
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
	
	@SerializedName("Probability of activating s knowing t")
	private float p_s_t;
	@SerializedName("Probability of activating t knowing s")
	private float p_t_s;
	
	
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
	
	
	public float getP_s_t() {
		return p_s_t;
	}
	public void setP_s_t(float p_s_t) {
		this.p_s_t = p_s_t;
	}
	public float getP_t_s() {
		return p_t_s;
	}
	public void setP_t_s(float p_t_s) {
		this.p_t_s = p_t_s;
	}
	
	public Matching getMatchingType() {
		return matchingType;
	}
	public void setMatchingType(Matching matchingType) {
		this.matchingType = matchingType;
	}
	public String getNs() {
		return ns;
	}
	public void setNs(String ns) {
		this.ns = ns;
	}
	public String getNt() {
		return nt;
	}
	public void setNt(String nt) {
		this.nt = nt;
	}
	
	
	
}
