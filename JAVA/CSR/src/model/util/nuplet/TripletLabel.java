package model.util.nuplet;

public class TripletLabel implements Comparable<TripletLabel> {
	private String label;
	private float fSource;
	private float fTarget;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public float getfSource() {
		return fSource;
	}
	public void setfSource(float fSource) {
		this.fSource = fSource;
	}
	public float getfTarget() {
		return fTarget;
	}
	public void setfTarget(float fTarget) {
		this.fTarget = fTarget;
	}
	@Override
	public String toString() {
		return "TripletLabel [label=" + label + ", fSource=" + fSource + ", fTarget=" + fTarget + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(fSource);
		result = prime * result + Float.floatToIntBits(fTarget);
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TripletLabel other = (TripletLabel) obj;
		if (Float.floatToIntBits(fSource) != Float.floatToIntBits(other.fSource))
			return false;
		if (Float.floatToIntBits(fTarget) != Float.floatToIntBits(other.fTarget))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}
	public TripletLabel(String label, float fSource, float fTarget) {
		super();
		this.label = label;
		this.fSource = fSource;
		this.fTarget = fTarget;
	}
	@Override
	public int compareTo(TripletLabel o) {
		float diff=(this.getfSource() - o.getfSource());
		if ( diff == 0)
			return this.getLabel().compareTo(o.getLabel());
		else {
			if (diff < 1)
				return 1;
			else 
				return -1;
		}
	}
	
}
