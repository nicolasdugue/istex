package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import io.reader.MatrixReader;
import model.cluster.Clustering;
import model.featureselection.FeaturesSelection;
import model.matrix.CsrMatrix;
import model.matrix.CsrMatrixClustered;

public class TestFeaturesSelection {
	private FeaturesSelection fs;
	@Before
	public void setUp() throws Exception {
		MatrixReader iskoMaghreb = new MatrixReader("matrix_lamirel_iskomaghreb");
		Clustering cim = new Clustering();
		cim.add(0);
		cim.add(0);
		cim.add(0);
		cim.add(1);
		cim.add(1);
		cim.add(1);
		CsrMatrix m = new CsrMatrix(iskoMaghreb);
		CsrMatrixClustered  cm_im = new CsrMatrixClustered(m, cim);
		fs = new FeaturesSelection(cm_im);
	}

	@Test
	public void testFF() {
		
		assertEquals(0.45, fs.getFeatureValue(0, 0) ,0.01); // Bug papier ISKO MAGHREB
		assertEquals(0.22, fs.getFeatureValue(0, 1) ,0.01);
		assertEquals(0.39, fs.getFeatureValue(1, 0),0.01);
		assertEquals(0.66, fs.getFeatureValue(1, 1),0.01);
		assertEquals(0.30, fs.getFeatureValue(2, 0),0.01);
		assertEquals(0.24, fs.getFeatureValue(2, 1),0.01);
	}

	
	@Test
	public void testgetFeatureValue() {
		assertEquals(0.45, fs.getFeatureValue(0, 0) ,0.01); // Bug papier ISKO MAGHREB
		assertEquals(0.22, fs.getFeatureValue(0, 1) ,0.01);
		assertEquals(0.39, fs.getFeatureValue(1, 0),0.01);
		assertEquals(0.66, fs.getFeatureValue(1, 1),0.01);
		assertEquals(0.30, fs.getFeatureValue(2, 0),0.01);
		assertEquals(0.24, fs.getFeatureValue(2, 1),0.01);
	}
	@Test
	public void testGetFeatureFMeanFMeasure() {
		assertEquals(0.34, fs.getFeatureFMeanValue(0),0.01);
		assertEquals(0.53, fs.getFeatureFMeanValue(1) ,0.01);
		assertEquals(0.27, fs.getFeatureFMeanValue(2) ,0.01);
	}
	
	@Test
	public void testGetGlobalMeanFMeasure() {
		assertEquals(0.38, fs.getGlobalMeanFeatureValue(), 0.015);
	}

	

	

}
