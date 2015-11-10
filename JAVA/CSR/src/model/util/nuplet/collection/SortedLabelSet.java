package model.util.nuplet.collection;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeSet;
import model.util.nuplet.PairSFWeighted;
import model.util.nuplet.comparator.PairSFWeightComparator;

public class SortedLabelSet implements Iterable<PairSFWeighted>, Collection<PairSFWeighted>{
	TreeSet<PairSFWeighted> set = new TreeSet<PairSFWeighted>(new PairSFWeightComparator());


	public boolean equals(Object o) {
		return set.equals(o);
	}

	public int hashCode() {
		return set.hashCode();
	}

	public boolean removeAll(Collection<?> c) {
		return set.removeAll(c);
	}

	public Iterator<PairSFWeighted> iterator() {
		return set.iterator();
	}

	public Iterator<PairSFWeighted> descendingIterator() {
		return set.descendingIterator();
	}

	public NavigableSet<PairSFWeighted> descendingSet() {
		return set.descendingSet();
	}

	public int size() {
		return set.size();
	}

	public boolean isEmpty() {
		return set.isEmpty();
	}

	public boolean contains(Object o) {
		return set.contains(o);
	}

	public boolean add(PairSFWeighted e) {
		return set.add(e);
	}

	public boolean remove(Object o) {
		return set.remove(o);
	}

	public void clear() {
		set.clear();
	}

	public boolean addAll(Collection<? extends PairSFWeighted> c) {
		return set.addAll(c);
	}

	public NavigableSet<PairSFWeighted> subSet(PairSFWeighted fromElement, boolean fromInclusive, PairSFWeighted toElement,
			boolean toInclusive) {
		return set.subSet(fromElement, fromInclusive, toElement, toInclusive);
	}

	public SortedSet<PairSFWeighted> subSet(PairSFWeighted fromElement, PairSFWeighted toElement) {
		return set.subSet(fromElement, toElement);
	}

	public Comparator<? super PairSFWeighted> comparator() {
		return set.comparator();
	}

	public String toString() {
		return set.toString();
	}

	public PairSFWeighted first() {
		return set.first();
	}

	public PairSFWeighted last() {
		return set.last();
	}

	public PairSFWeighted lower(PairSFWeighted e) {
		return set.lower(e);
	}

	public PairSFWeighted floor(PairSFWeighted e) {
		return set.floor(e);
	}

	public PairSFWeighted ceiling(PairSFWeighted e) {
		return set.ceiling(e);
	}

	public PairSFWeighted higher(PairSFWeighted e) {
		return set.higher(e);
	}

	public PairSFWeighted pollFirst() {
		return set.pollFirst();
	}

	public PairSFWeighted pollLast() {
		return set.pollLast();
	}

	public Object clone() {
		return set.clone();
	}


	public Object[] toArray() {
		return set.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return set.toArray(a);
	}

	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	public NavigableSet<PairSFWeighted> headSet(PairSFWeighted toElement, boolean inclusive) {
		return set.headSet(toElement, inclusive);
	}

	public boolean retainAll(Collection<?> c) {
		return set.retainAll(c);
	}

	public NavigableSet<PairSFWeighted> tailSet(PairSFWeighted fromElement, boolean inclusive) {
		return set.tailSet(fromElement, inclusive);
	}

	public SortedSet<PairSFWeighted> headSet(PairSFWeighted toElement) {
		return set.headSet(toElement);
	}

	public SortedSet<PairSFWeighted> tailSet(PairSFWeighted fromElement) {
		return set.tailSet(fromElement);
	}


}
