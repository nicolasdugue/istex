package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import io.reader.MatrixReader;
import model.cluster.Clustering;
import model.matrix.CsrMatrix;
import model.matrix.CsrMatrixClustered;

public class TestCsrMatrixClustered {
	private CsrMatrix cm;
	private CsrMatrixClustered cm_c;
	@Before
	public void setUp() throws Exception {
		MatrixReader mr = new MatrixReader("exemples/matrix");
		Clustering c = new Clustering(4);
		c.add(0);
		c.add(0);
		c.add(1);
		c.add(1);
		cm = new CsrMatrix(mr);
		
		cm_c = new CsrMatrixClustered(cm, c);
	}

	
	@Test
	public void testSumCluster() {
		assertEquals(13, cm_c.getSumCluster(0),0.001);
		assertEquals(12, cm_c.getSumCluster(1),0.001);
	}
	
	
	
	@Test
	public void testCsrSumColCluster() {
		assertEquals(3, cm_c.getSumColInCluster(0, 0),0.001);
		assertEquals(0, cm_c.getSumColInCluster(0, 1),0.001);
		assertEquals(3, cm_c.getSumColInCluster(1, 0),0.001);
		assertEquals(0, cm_c.getSumColInCluster(2, 0),0.001);
		assertEquals(4, cm_c.getSumColInCluster(2, 1),0.001);
		assertEquals(7, cm_c.getSumColInCluster(3, 0),0.001);
		assertEquals(8, cm_c.getSumColInCluster(3, 1),0.001);
	}



}
