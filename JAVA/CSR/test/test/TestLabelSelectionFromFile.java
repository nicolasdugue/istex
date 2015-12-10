package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.featureselection.LabelSelectionFromFile;

public class TestLabelSelectionFromFile {

	LabelSelectionFromFile ls;
	@Before
	public void setUp() throws Exception {
		ls = new LabelSelectionFromFile("exemples/00-0350gnge-tn-DD-ENL.fmgs");
	}

	@Test
	public void testGetPrevalentFeatureSet() {
		ArrayList<Integer> l0 = new ArrayList<Integer>();
		l0.add(0);
		l0.add(1);
		ArrayList<Integer> l2 = new ArrayList<Integer>();
		for (int i=9; i <=15; i++) {
			l2.add(i);
		}
		ArrayList<Integer> l7 = new ArrayList<Integer>();
		for (int i=34; i <=37; i++) {
			l7.add(i);
		}
		assertEquals(l0, ls.getPrevalentFeatureSet(0));
		assertEquals(l2, ls.getPrevalentFeatureSet(2));
		assertEquals(l7, ls.getPrevalentFeatureSet(6));
		
		
	}

	@Test
	public void testGetLabelSet() {
		ArrayList<String> l0 = new ArrayList<String>();
		l0.add("Organic compounds");
		l0.add("Color");
		ArrayList<String> l2 = new ArrayList<String>();
		l2.add("Polymer films");
		l2.add("Conducting polymers");
		l2.add("Polymer blends");
		l2.add("Brightness");
		l2.add("Spin-on coating");
		l2.add("Annealing");
		l2.add("Polymerization");
		ArrayList<String> l7 = new ArrayList<String>();
		l7.add("Light emitting devices");
		l7.add("Chemical structure");
		l7.add("Molecular configurations");
		l7.add("Electrochemical devices");

		assertEquals(l0, ls.getLabelSet(0));
		assertEquals(l2, ls.getLabelSet(2));
		assertEquals(l7, ls.getLabelSet(6));
	}

	@Test
	public void testGetNbCluster() {
		assertEquals(50, ls.getNbCluster());
	}

	@Test
	public void testGetLabelOfCol() {
		assertEquals("Electroluminescent device", ls.getLabelOfCol(2));
		assertEquals("Substrate", ls.getLabelOfCol(8));
		assertEquals("Brightness", ls.getLabelOfCol(12));
		assertEquals("Optical polymers", ls.getLabelOfCol(48));
		assertEquals("X-ray detection", ls.getLabelOfCol(79));
	}

	@Test
	public void testGetIndexOfColLabel() {
		assertEquals(79, ls.getIndexOfColLabel("X-ray detection"));
		assertEquals(48, ls.getIndexOfColLabel("Optical polymers"));
		assertEquals(12, ls.getIndexOfColLabel("Brightness"));
		assertEquals(8, ls.getIndexOfColLabel("Substrate"));
		assertEquals(2, ls.getIndexOfColLabel("Electroluminescent device"));
	}

}
