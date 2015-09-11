package model.util.nuplet.comparator;

import java.util.Comparator;

import model.util.nuplet.TripletLabel;

public class TripletComparatorTarget implements Comparator<TripletLabel>{

	@Override
	public int compare(TripletLabel o1, TripletLabel o2) {
		float diff=(o1.getfTarget() - o2.getfTarget());
		if ( diff == 0)
			return o1.getLabel().compareTo(o2.getLabel());
		else {
			if (diff < 1)
				return 1;
			else 
				return -1;
		}
	}


}
