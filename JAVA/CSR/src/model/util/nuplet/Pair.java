package model.util.nuplet;

public class Pair<T1,T2> {
	private T1 left;
	private T2 right;
	public Pair() {
	}
	public Pair(T1 left, T2 right) {
		super();
		this.left = left;
		this.right = right;
	}
	public T1 getLeft() {
		return left;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		return true;
	}
	public void setLeft(T1 left) {
		this.left = left;
	}
	public T2 getRight() {
		return right;
	}
	public void setRight(T2 right) {
		this.right = right;
	}
	@Override
	public String toString() {
		return "Pair [left=" + left + ", right=" + right + "]";
	}

}
