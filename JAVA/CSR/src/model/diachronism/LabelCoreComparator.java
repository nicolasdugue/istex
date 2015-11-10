package model.diachronism;

import java.util.Comparator;

public class LabelCoreComparator implements Comparator<LabelCore>{

	@Override
	public int compare(LabelCore o1, LabelCore o2) {
		float diff = (o2.getP_t_s() + o2.getP_s_t()) - (o1.getP_t_s() + o1.getP_s_t());
		if (diff == 0) {
			return o1.getS().compareTo(o2.getS());
		}
		return floatToInt(diff);
	}
	public int floatToInt(float f) {
		if (f > 0)
			return 1;
		return -1;
	}

}
