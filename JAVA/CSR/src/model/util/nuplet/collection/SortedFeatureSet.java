package model.util.nuplet.collection;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeSet;

import model.util.nuplet.PairF;
import model.util.nuplet.comparator.PairFComparator;

public class SortedFeatureSet implements Iterable<PairF>, Collection<PairF>{
	TreeSet<PairF> set = new TreeSet<PairF>(new PairFComparator());


	public boolean equals(Object o) {
		return set.equals(o);
	}

	public int hashCode() {
		return set.hashCode();
	}

	public Object[] toArray() {
		return set.toArray();
	}

	public boolean removeAll(Collection<?> c) {
		return set.removeAll(c);
	}

	public <T> T[] toArray(T[] a) {
		return set.toArray(a);
	}

	public Iterator<PairF> iterator() {
		return set.iterator();
	}

	public Iterator<PairF> descendingIterator() {
		return set.descendingIterator();
	}

	public NavigableSet<PairF> descendingSet() {
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

	public boolean add(PairF e) {
		return set.add(e);
	}

	public boolean remove(Object o) {
		return set.remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	public void clear() {
		set.clear();
	}

	public boolean addAll(Collection<? extends PairF> c) {
		return set.addAll(c);
	}

	public NavigableSet<PairF> subSet(PairF fromElement, boolean fromInclusive, PairF toElement, boolean toInclusive) {
		return set.subSet(fromElement, fromInclusive, toElement, toInclusive);
	}

	public NavigableSet<PairF> headSet(PairF toElement, boolean inclusive) {
		return set.headSet(toElement, inclusive);
	}

	public boolean retainAll(Collection<?> c) {
		return set.retainAll(c);
	}

	public NavigableSet<PairF> tailSet(PairF fromElement, boolean inclusive) {
		return set.tailSet(fromElement, inclusive);
	}

	public SortedSet<PairF> subSet(PairF fromElement, PairF toElement) {
		return set.subSet(fromElement, toElement);
	}

	public SortedSet<PairF> headSet(PairF toElement) {
		return set.headSet(toElement);
	}

	public SortedSet<PairF> tailSet(PairF fromElement) {
		return set.tailSet(fromElement);
	}

	public Comparator<? super PairF> comparator() {
		return set.comparator();
	}

	public String toString() {
		return set.toString();
	}

	public PairF first() {
		return set.first();
	}

	public PairF last() {
		return set.last();
	}

	public PairF lower(PairF e) {
		return set.lower(e);
	}

	public PairF floor(PairF e) {
		return set.floor(e);
	}

	public PairF ceiling(PairF e) {
		return set.ceiling(e);
	}

	public PairF higher(PairF e) {
		return set.higher(e);
	}

	public PairF pollFirst() {
		return set.pollFirst();
	}

	public PairF pollLast() {
		return set.pollLast();
	}

	public Object clone() {
		return set.clone();
	}

	
}
