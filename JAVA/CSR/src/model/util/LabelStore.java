package model.util;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Allows to preserve a matching Integer,String that can be used to store labels for features (columns of a matrix) or objects (rows)
 * 
 * @author dugue
 *
 */
public class LabelStore {
	private ArrayList<String> labels = new ArrayList<String>();
	private HashMap<String, Integer> features = new HashMap<String, Integer>();
	public LabelStore() {
		super();
	}
	/**
	 * @param key the row or column index
	 * @return the label matching with the key
	 */
	public String getLabel(Integer key) {
		return labels.get(key);
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
		features.put(value,labels.size());
		labels.add(value);
		
	}
	
	/**
	 * @param value label
	 * @return true if the label exists
	 */
	public boolean containsLabel(String value) {
		return labels.contains(value);
	}
	
	public int getSize() {
		return labels.size();
	}
	
}
