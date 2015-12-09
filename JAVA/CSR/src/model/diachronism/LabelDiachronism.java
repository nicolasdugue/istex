package model.diachronism;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import model.featureselection.ILabelSelection;
import model.util.nuplet.TripletLabel;
import util.SGLogger;

/**
 * 
 * Allows to compare two clustering by comparing their label
 * 
 * @author dugue
 *
 */
public class LabelDiachronism {
	/**
	 * Both the set of labels of each cluster for each clustering made at time t0 (source) and at time t1 (target)
	 */
	private ILabelSelection ls_s;
	private ILabelSelection ls_t;
	/**
	 * 		t0	t1	t2	...	tn
	 * s0
	 * s1
	 * s2
	 * .
	 * .
	 * .
	 * sn
	 * 
	 */
	//p(t|s)
	private float[][] p_t_knowing_s;
	//p(s|t)
	private float[][] p_s_knowing_t;
	
	private float[] pA_s;
	private float[] pA_t;
	
	private float a_s;
	private float a_t;
	
	private float std_s;
	private float std_t;
	private Logger logger = SGLogger.getInstance();

	public LabelDiachronism(ILabelSelection ls_t0, ILabelSelection ls_t1) {
		this.ls_s = ls_t0;
		this.ls_t = ls_t1;
		p_t_knowing_s = new float[ls_s.getNbCluster()][ls_t.getNbCluster()];
		pA_s=new float[ls_s.getNbCluster()];
		
		p_s_knowing_t = new float[ls_t.getNbCluster()][ls_s.getNbCluster()];
		pA_t=new float[ls_t.getNbCluster()];
		computeProbabilities();
	}
	private void computeProbabilities() {
		int env;
		float p_t_knowing_s_sum;
		float p_s_knowing_t_sum;
		float a_s_tmp = 0;
		ArrayList<String> labels_s;
		ArrayList<String> labels_t;
		ArrayList<String> labels_s_and_t;
		float numerateur;
		float numerateur2;
		float denominateur;
		float denominateur2;
		
		//Knowing s
		for (int s=0; s < ls_s.getNbCluster(); s++) {
			env=0;
			p_t_knowing_s_sum=0;
			//p(t knowing s)
			for (int t=0; t < ls_t.getNbCluster(); t++) {
				denominateur=0;
				denominateur2=0;
				labels_t=ls_t.getLabelSet(t);
				labels_s=ls_s.getLabelSet(s);
				labels_s_and_t=intersection(labels_s, labels_t);
				if (labels_s_and_t.size() > 0) {
					env++;
					//To compute p(t|s), use this denominator calculated with the target label F measures
					for (String label : labels_t) {
						denominateur+=ls_t.getFeatureValue(ls_t.getIndexOfColLabel(label),t);
					}
					//To compute p(s|t), use this denominator calculated with the source label F measures
					for (String label : labels_s) {
						denominateur2+=ls_s.getFeatureValue(ls_s.getIndexOfColLabel(label),s);
					}
					numerateur=0;
					numerateur2=0;
					for (String label : labels_s_and_t) {
						numerateur+=ls_t.getFeatureValue(ls_t.getIndexOfColLabel(label),t);
						logger.debug("Labels " + label + " in Cluster " + t+ ": "+ ls_t.getFeatureValue(ls_t.getIndexOfColLabel(label),t));
						numerateur2+=ls_s.getFeatureValue(ls_s.getIndexOfColLabel(label),s);
					}
					logger.debug("Denominateur "+ denominateur +",Numerateur "+ numerateur );
					logger.debug("n/d =" + numerateur/denominateur );
					
					if (denominateur == 0.0f)
						p_t_knowing_s[s][t]=0.0f;
					else 
						p_t_knowing_s[s][t]=numerateur/denominateur;
					if (denominateur2 == 0.0f)
						p_s_knowing_t[t][s]=0.0f;
					else
						p_s_knowing_t[t][s]=numerateur2/denominateur2;
					p_t_knowing_s_sum+=p_t_knowing_s[s][t];
					logger.debug("p_t_knowing_s["+s+"]["+t+"]="+ p_t_knowing_s[s][t]);
					logger.debug("p_s_knowing_t["+t+"]["+s+"]="+ p_s_knowing_t[t][s]);
				}
			}
			//env cannot be used to process pA_t since we have to know which cluster from S the source t activates.
			pA_s[s]=p_t_knowing_s_sum/env;
			a_s_tmp=pA_s[s];
		}
		a_s=a_s_tmp/pA_s.length;
		
		float std_tmp=0;
		for (int i=0; i < pA_s.length; i++) {
			std_tmp=(pA_s[i] - a_s)*(pA_s[i] - a_s);
		}
		std_s=(float) (Math.sqrt(std_tmp)/pA_s.length);
		
		
		//We process the average pA_t and A_t
		float a_t_tmp = 0;
		//Knowing t
		for (int t=0; t < ls_t.getNbCluster(); t++) {
			env=0;
			p_s_knowing_t_sum=0;
			//p(S knowing t)
			for (int s=0; s < ls_s.getNbCluster(); s++) {
				labels_t=ls_t.getLabelSet(t);
				labels_s=ls_s.getLabelSet(s);
				labels_s_and_t=intersection(labels_s, labels_t);
				if (labels_s_and_t.size() > 0) {
					env++;
					p_s_knowing_t_sum+=p_s_knowing_t[t][s];
				}
			}
			pA_t[t]=p_s_knowing_t_sum/env;
			a_t_tmp=pA_t[t];
		}
		a_t=a_t_tmp/pA_t.length;
		
		std_tmp=0;
		for (int i=0; i < pA_t.length; i++) {
			std_tmp=(pA_t[i] - a_t)*(pA_t[i] - a_t);
		}
		std_t=(float) (Math.sqrt(std_tmp)/pA_t.length);
		
	}
	
	
	private <T> ArrayList<T> intersection(ArrayList<T> al1, ArrayList<T> al2) {
		ArrayList<T> intersection = new ArrayList<T>();
		for (T label : al1) {
			if (al2.contains(label)) {
				intersection.add(label);
			}
		}
		return intersection;
	}
	/**
	 * @return the p_t_acts
	 */
	public float[][] getP_t_acts() {
		return p_t_knowing_s;
	}
	
	/**
	 * @return the pA_s
	 */
	public float[] getpA_s() {
		return pA_s;
	}
	
	/**
	 * @return the a_s
	 */
	public float getA_s() {
		return a_s;
	}
	
	/**
	 * @return the std_s
	 */
	public float getStd_s() {
		return std_s;
	}
	
	
	
	public ArrayList<String> getLabelsClusterSource(int cluster) {
		return ls_s.getLabelSet(cluster);
	}
	public ArrayList<String> getLabelsClusterTarget(int cluster) {
		return ls_t.getLabelSet(cluster);
	}
	public float getFeatureValueSource(String label) {
		return ls_s.getFeatureFMeanValue(ls_s.getIndexOfColLabel(label));
	}
	public float getFeatureValueTarget(String label) {
		return ls_t.getFeatureFMeanValue(ls_t.getIndexOfColLabel(label));
	}
	
	public int getNbClusterSource() {
		return p_t_knowing_s.length;
	}
	public int getNbClusterTarget() {
		return p_t_knowing_s[0].length;
	}
	
	
	/**
	 * Gets an ArrayList of int designating the cluster index from the target list of cluster which matches with the cluster source
	 * 
	 * @param source
	 * @return
	 */
	public ArrayList<Integer> getTargetClusterMatching(int source) {
		ArrayList<Integer> clusterMatching = new ArrayList<Integer>();
		for (int t=0; t < p_t_knowing_s[source].length; t++) {
			if ((p_t_knowing_s[source][t] >= pA_s[source]) && (p_t_knowing_s[source][t] >= (a_s+std_s)) && (p_s_knowing_t[t][source] >= pA_t[t]) && (p_s_knowing_t[t][source] >= (a_t+std_t))) {
				clusterMatching.add(t);
			}
		}
		return clusterMatching;
	}
	
	public LabelCore getLabelCore(int s, int t) {
		LabelCore lc = new LabelCore();
		lc.setS(this.getLabelOfClusterSource(s));
		lc.setT(this.getLabelOfClusterTarget(t));
		float value_s, value_t;
		ArrayList<String> labels_s=ls_s.getLabelSet(s);
		ArrayList<String> labels_t=ls_t.getLabelSet(t);
		ArrayList<String> labels_s_and_t=intersection(labels_s, labels_t);
		TripletLabel triplet;
		float k;
		for (String label : labels_s_and_t) {
			value_t=ls_t.getFeatureValue(ls_t.getIndexOfColLabel(label), t);
			value_s=ls_s.getFeatureValue(ls_s.getIndexOfColLabel(label), s);
			k=value_t / value_s;
			triplet=new TripletLabel(label, value_s,value_t);
			labels_s.remove(label);
			labels_t.remove(label);
			if ((k > 0.5) && (k < 2)) {
				lc.addKernel(triplet);
			}
			else {
				if (k < 0.5) {
					lc.addCommonSource(triplet);
				}
				else {
					lc.addCommonTarget(triplet);
				}
			}
		}
		for (String label : labels_s) {
			value_t=0;
			value_s=ls_s.getFeatureValue(ls_s.getIndexOfColLabel(label),s);
			triplet=new TripletLabel(label, value_s,value_t);
			lc.addSource(triplet);
		}
		for (String label : labels_t) {
			value_t=ls_t.getFeatureValue(ls_t.getIndexOfColLabel(label),t);
			value_s=0;
			triplet=new TripletLabel(label, value_s,value_t);
			lc.addTarget(triplet);
		}
		
		lc.setP_s_t(p_s_knowing_t[t][s]);
		lc.setP_t_s(p_t_knowing_s[s][t]);
		return lc;
	}
	public String getLabelOfClusterSource(int cluster) {
		return ls_s.getLabelOfCluster(cluster);
	}
	public int getClusterSourceOfLabel(String s) {
		return ls_s.getClusterOfLabel(s);
	}
	public String getLabelOfClusterTarget(int cluster) {
		return ls_t.getLabelOfCluster(cluster);
	}
	public int getClusterTargetOfLabel(String s) {
		return ls_t.getClusterOfLabel(s);
	}

	
	
}
