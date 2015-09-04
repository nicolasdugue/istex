package model.diachronism;

import java.util.ArrayList;

import model.featureselection.ILabelSelection;

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
	private float[][] p_t_acts;
	
	private float[] pA_s;
	
	private float a_s;
	
	private float std_s;

	public LabelDiachronism(ILabelSelection ls_t0, ILabelSelection ls_t1) {
		this.ls_s = ls_t0;
		this.ls_t = ls_t1;
		p_t_acts = new float[ls_s.getNbCluster()][ls_t.getNbCluster()];
		pA_s=new float[ls_s.getNbCluster()];
		computeP_t_acts();
	}
	private void computeP_t_acts() {
		int env;
		float p_t_acts_sum;
		float a_s_tmp = 0;
		ArrayList<String> labels_s;
		ArrayList<String> labels_t;
		ArrayList<String> labels_s_and_t;
		float numerateur;
		float denominateur;
		for (int s=0; s < ls_s.getNbCluster(); s++) {
			env=0;
			p_t_acts_sum=0;
			for (int t=0; t < ls_t.getNbCluster(); t++) {
				denominateur=0;
				labels_t=ls_t.getLabelSet(t);
				labels_s=ls_s.getLabelSet(s);
				labels_s_and_t=intersection(labels_s, labels_t);
				if (labels_s_and_t.size() > 0) {
					env++;
					for (String label : labels_t) {
						denominateur+=ls_t.getFeatureFMeanValue(ls_t.getIndexOfColLabel(label));
					}
					numerateur=0;
					for (String label : labels_s_and_t) {
						numerateur+=ls_t.getFeatureFMeanValue(ls_t.getIndexOfColLabel(label));
					}
					p_t_acts[s][t]=numerateur/denominateur;
					p_t_acts_sum+=p_t_acts[s][t];
				}
			}
			pA_s[s]=p_t_acts_sum/env;
			a_s_tmp=pA_s[s];
		}
		a_s=a_s_tmp/pA_s.length;
		
		float std_tmp=0;
		for (int i=0; i < pA_s.length; i++) {
			std_tmp=(pA_s[i] - a_s)*(pA_s[i] - a_s);
		}
		std_s=(float) (Math.sqrt(std_tmp)/pA_s.length);
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
		return p_t_acts;
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
	
	public ArrayList<Integer> getTargetClusterMatching(int source) {
		ArrayList<Integer> clusterMatching = new ArrayList<Integer>();
		for (int t=0; t < p_t_acts[source].length; t++) {
			if ((p_t_acts[source][t] > pA_s[source]) && (p_t_acts[source][t] > (a_s+std_s))) {
				clusterMatching.add(t);
			}
		}
		return clusterMatching;
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
		return p_t_acts.length;
	}
	public int getNbCluterTarget() {
		return p_t_acts[0].length;
	}
	

	
	
}
