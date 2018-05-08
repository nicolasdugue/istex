package model.cluster;

import java.util.ArrayList;
import java.util.Iterator;

public class ClusterSet implements Iterable<Integer> {

	private ArrayList<Integer> set;

	public ClusterSet() {
		this.set= new ArrayList<Integer>(5);
	}
	public ClusterSet(String ligne) {
		this();
		String [] tab= ligne.split(";");
		if (tab.length <=0) {
			tab=ligne.split(",");
		}
		int i =0;
		for (String elem:tab) {
			this.set.add(Integer.parseInt(elem));
			i++;
		}
	}
	public void add (int e) {
		this.set.add(e);
	}
	public boolean isIntInCluster(int k) {
		for (int i : this.set) {
			if (i == k) 
				return true;
		}
		return false;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {

           Iterator<Integer> it=set.iterator();

            public boolean hasNext() {
               return it.hasNext();
            }

            public Integer next() {
               return it.next();
            }

		};
	}
	
	
}
