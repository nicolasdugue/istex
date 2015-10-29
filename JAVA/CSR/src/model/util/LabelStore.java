package model.util;

import java.util.HashMap;

/**
 * Allows to preserve a matching Integer,String that can be used to store labels for features (columns of a matrix) or objects (rows)
 * 
 * @author dugue
 *
 */
public class LabelStore {
	private String[] labels;
	int cpt=0;
	private HashMap<String, Integer> features = new HashMap<String, Integer>();
	public LabelStore(int size) {
		labels=new String[size];
	}
	/**
	 * @param key the row or column index
	 * @return the label matching with the key
	 */
	public String getLabel(Integer key) {
		return labels[key];
	}
	
	/**
	 * @param label A label String of a feature f
	 * @return an integer id corresponding to the matrix column position of the label
	 */
	public int getIndexOfLabel(String label) {
		return features.get(label);
	}
	
	/**
	 * @param value the label matching with the integer position in the list (labels.size())
	 * 
	 */
	public void addLabel(String value) {
		features.put(value,cpt);
		labels[cpt]=value;
		cpt++;
	}
	
	
	public int getSize() {
		return labels.length;
	}
	
}
