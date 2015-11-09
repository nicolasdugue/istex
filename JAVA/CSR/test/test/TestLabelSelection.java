package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import io.reader.ClusteringReader;
import io.reader.LabelReader;
import io.reader.MatrixReader;
import model.featureselection.FeaturesSelection;
import model.featureselection.LabelSelection;
import model.featureselection.labellingstategies.FeatureSelectionStrategy;
import model.featureselection.labellingstategies.FeatureThresholdStrategy;
import model.featureselection.labellingstategies.FixedNumberOfLabelStrategy;
import model.featureselection.labellingstategies.ILabelSelectionStrategy;
import model.matrix.CsrMatrix;
import model.matrix.CsrMatrixClustered;
import model.matrix.decorator.IMatrix;
import model.matrix.decorator.MatrixFeatureLabels;

public class TestLabelSelection {
	private FeaturesSelection fs;
	private ILabelSelectionStrategy lss;
	ArrayList<Integer> lc0;
	ArrayList<Integer> lc1;
	@Before
	public void setUp() throws Exception {
		MatrixReader iskoMaghreb = new MatrixReader("exemples/matrix_lamirel_iskomaghreb");
		ClusteringReader rd = new ClusteringReader("exemples/clustering_lamirel_iskomaghreb");
		IMatrix m = new CsrMatrix(iskoMaghreb);
		LabelReader l = new LabelReader("exemples/label_lamirel_iskomaghreb");
		IMatrix ml = new MatrixFeatureLabels(m, l.getLs());
		CsrMatrixClustered cm_im = new CsrMatrixClustered(ml, rd.getClusters());
		fs = new FeaturesSelection(cm_im);
	}
	
	@Test
	public void testGetPrevalentFeatureSetFS() throws IOException {
		lss = new FeatureSelectionStrategy(fs);
		LabelSelection ls = new LabelSelection(fs, lss);
		lc0=ls.getPrevalentFeatureSet(0);
		lc1=ls.getPrevalentFeatureSet(1);
		assertEquals(1,lc0.size());
		assertEquals(1, lc1.size());
		assertTrue(lc0.contains(0));
		assertTrue(lc1.contains(1));
	}
	
	@Test
	public void testGetPrevalentFeatureSetThreshold() throws IOException {
		lss = new FeatureThresholdStrategy(fs,0.21f);
		LabelSelection ls = new LabelSelection(fs, lss);
		lc0=ls.getPrevalentFeatureSet(0);
		lc1=ls.getPrevalentFeatureSet(1);
		assertEquals(3, lc0.size());
		assertEquals(3, lc1.size());
		lss = new FeatureThresholdStrategy(fs,0.23f);
		ls.setLss(lss);
		lc0=ls.getPrevalentFeatureSet(0);
		lc1=ls.getPrevalentFeatureSet(1);
		assertEquals(3, lc0.size());
		assertEquals(2, lc1.size());
		lss = new FeatureThresholdStrategy(fs,0.25f);
		ls.setLss(lss);
		lc0=ls.getPrevalentFeatureSet(0);
		lc1=ls.getPrevalentFeatureSet(1);
		assertEquals(3, lc0.size());
		assertEquals(1, lc1.size());
		
		
		
		lss = new FeatureThresholdStrategy(fs,0.29f);
		ls.setLss(lss);
		lc0=ls.getPrevalentFeatureSet(0);
		lc1=ls.getPrevalentFeatureSet(1);
		assertEquals(3, lc0.size());
		assertEquals(1, lc1.size());
		lss = new FeatureThresholdStrategy(fs,0.30f);
		ls.setLss(lss);
		lc0=ls.getPrevalentFeatureSet(0);
		lc1=ls.getPrevalentFeatureSet(1);
		assertEquals(2, lc0.size());
		assertEquals(1, lc1.size());
		
		assertTrue(lc0.contains(0));
		assertTrue(lc0.contains(1));
		assertTrue(lc1.contains(1));
		
		lss = new FeatureThresholdStrategy(fs,0.39f);
		ls.setLss(lss);
		lc0=ls.getPrevalentFeatureSet(0);
		lc1=ls.getPrevalentFeatureSet(1);
		assertEquals(2, lc0.size());
		assertEquals(1, lc1.size());
		lss = new FeatureThresholdStrategy(fs,0.40f);
		ls.setLss(lss);
		lc0=ls.getPrevalentFeatureSet(0);
		lc1=ls.getPrevalentFeatureSet(1);
		assertEquals(1, lc0.size());
		assertEquals(1, lc1.size());
		
		assertTrue(lc0.contains(0));
		assertTrue(lc1.contains(1));
		
		lss = new FeatureThresholdStrategy(fs,0.44f);
		ls.setLss(lss);
		lc0=ls.getPrevalentFeatureSet(0);
		lc1=ls.getPrevalentFeatureSet(1);
		assertEquals(1, lc0.size());
		assertEquals(1, lc1.size());
		lss = new FeatureThresholdStrategy(fs,0.45f);
		ls.setLss(lss);
		lc0=ls.getPrevalentFeatureSet(0);
		lc1=ls.getPrevalentFeatureSet(1);
		assertEquals(0, lc0.size());
		assertEquals(1, lc1.size());
		
		lss = new FeatureThresholdStrategy(fs,0.65f);
		ls.setLss(lss);
		lc0=ls.getPrevalentFeatureSet(0);
		lc1=ls.getPrevalentFeatureSet(1);
		assertEquals(0, lc0.size());
		assertEquals(1, lc1.size());
		lss = new FeatureThresholdStrategy(fs,0.66f);
		ls.setLss(lss);
		lc0=ls.getPrevalentFeatureSet(0);
		lc1=ls.getPrevalentFeatureSet(1);
		assertEquals(0, lc0.size());
		assertEquals(0, lc1.size());
		
	}
	
	@Test
	public void testGetPrevalentFeatureSetFixedNumber() throws IOException {
		lss = new FixedNumberOfLabelStrategy(fs,3);
		LabelSelection ls = new LabelSelection(fs, lss);
		lc0=ls.getPrevalentFeatureSet(0);
		lc1=ls.getPrevalentFeatureSet(1);
		assertEquals(3, lc0.size());
		assertEquals(3, lc1.size());
		lss = new FixedNumberOfLabelStrategy(fs,2);
		ls.setLss(lss);
		lc0=ls.getPrevalentFeatureSet(0);
		lc1=ls.getPrevalentFeatureSet(1);
		assertEquals(2, lc0.size());
		assertEquals(2, lc1.size());
		assertTrue(lc0.contains(0));
		assertTrue(lc0.contains(1));
		assertTrue(lc1.contains(1));
		assertTrue(lc1.contains(2));
		
		lss = new FixedNumberOfLabelStrategy(fs,1);
		ls.setLss(lss);
		lc0=ls.getPrevalentFeatureSet(0);
		lc1=ls.getPrevalentFeatureSet(1);
		assertEquals(1, lc0.size());
		assertEquals(1, lc1.size());
		assertTrue(lc0.contains(0));
		assertTrue(lc1.contains(1));
		
		lss = new FixedNumberOfLabelStrategy(fs,0);
		ls.setLss(lss);
		lc0=ls.getPrevalentFeatureSet(0);
		lc1=ls.getPrevalentFeatureSet(1);
		assertEquals(0, lc0.size());
		assertEquals(0, lc1.size());
		
	}

}
