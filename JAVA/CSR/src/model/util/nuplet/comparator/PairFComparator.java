package model.util.nuplet.comparator;

import java.util.Comparator;

import model.util.nuplet.PairF;

public class PairFComparator implements Comparator<PairF> {

	@Override
	public int compare(PairF o1, PairF o2) {
		float diff = o1.getRight() - o2.getRight();
		if (diff == 0) {
			return (o1.getLeft().compareTo(o2.getLeft()));
		}
		else {
			if (diff > 0)
				return -1;
			else
				return 1;
		}
	}

}
