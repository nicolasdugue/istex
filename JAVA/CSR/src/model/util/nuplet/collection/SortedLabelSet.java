package model.util.nuplet.collection;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeSet;

import model.util.nuplet.PairFWeighted;
import model.util.nuplet.comparator.WeightComparator;

public class SortedLabelSet implements Iterable<PairFWeighted>{
	TreeSet<PairFWeighted> set = new TreeSet<>(new WeightComparator());


	public boolean equals(Object o) {
		return set.equals(o);
	}

	public int hashCode() {
		return set.hashCode();
	}

	public boolean removeAll(Collection<?> c) {
		return set.removeAll(c);
	}

	public Iterator<PairFWeighted> iterator() {
		return set.iterator();
	}

	public Iterator<PairFWeighted> descendingIterator() {
		return set.descendingIterator();
	}

	public NavigableSet<PairFWeighted> descendingSet() {
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

	public boolean add(PairFWeighted e) {
		return set.add(e);
	}

	public boolean remove(Object o) {
		return set.remove(o);
	}

	public void clear() {
		set.clear();
	}

	public boolean addAll(Collection<? extends PairFWeighted> c) {
		return set.addAll(c);
	}

	public NavigableSet<PairFWeighted> subSet(PairFWeighted fromElement, boolean fromInclusive, PairFWeighted toElement,
			boolean toInclusive) {
		return set.subSet(fromElement, fromInclusive, toElement, toInclusive);
	}

	public SortedSet<PairFWeighted> subSet(PairFWeighted fromElement, PairFWeighted toElement) {
		return set.subSet(fromElement, toElement);
	}

	public Comparator<? super PairFWeighted> comparator() {
		return set.comparator();
	}

	public String toString() {
		return set.toString();
	}

	public PairFWeighted first() {
		return set.first();
	}

	public PairFWeighted last() {
		return set.last();
	}

	public PairFWeighted lower(PairFWeighted e) {
		return set.lower(e);
	}

	public PairFWeighted floor(PairFWeighted e) {
		return set.floor(e);
	}

	public PairFWeighted ceiling(PairFWeighted e) {
		return set.ceiling(e);
	}

	public PairFWeighted higher(PairFWeighted e) {
		return set.higher(e);
	}

	public PairFWeighted pollFirst() {
		return set.pollFirst();
	}

	public PairFWeighted pollLast() {
		return set.pollLast();
	}

	public Object clone() {
		return set.clone();
	}

}
