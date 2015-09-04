package model.util;

import java.util.Comparator;

public class WeightComparator implements Comparator<PairFWeighted> {

	@Override
	public int compare(PairFWeighted o1, PairFWeighted o2) {
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
