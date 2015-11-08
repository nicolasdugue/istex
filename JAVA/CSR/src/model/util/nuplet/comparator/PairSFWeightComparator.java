package model.util.nuplet.comparator;

import java.util.Comparator;

import model.util.nuplet.PairSFWeighted;

public class PairSFWeightComparator implements Comparator<PairSFWeighted> {

	@Override
	public int compare(PairSFWeighted o1, PairSFWeighted o2) {
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
