package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import io.reader.ArcsReader;
import io.reader.ClusteringReader;
import model.matrix.CsrMatrix;
import model.matrix.CsrMatrixClustered;
import model.roles.FunctionalCartography;

public class TestDirectedGraph {

	private FunctionalCartography fc;
	@Before
	public void setUp() throws Exception {
		ArcsReader mr = new ArcsReader("exemples/directed_graph");
		//mr.log.setLevel(Le);
		CsrMatrix csr = new CsrMatrix(mr);
		ClusteringReader cr = new ClusteringReader("exemples/Guimera_community");
		CsrMatrixClustered csrc = new CsrMatrixClustered(csr, cr.getClusters());
		fc=new FunctionalCartography(csrc);
		fc.doZScore();
	}
	
	@Test
	public void testDegree() {
		assertEquals(1, fc.getDegree(0));
		assertEquals(0, fc.getInDegree(0));
		assertEquals(1, fc.getOutDegree(0));
		
		assertEquals(2, fc.getDegree(1));
		assertEquals(1, fc.getInDegree(1));
		assertEquals(1, fc.getOutDegree(1));
		
		assertEquals(3, fc.getDegree(2));
		assertEquals(2, fc.getInDegree(2));
		assertEquals(1, fc.getOutDegree(2));
		
		assertEquals(2, fc.getDegree(3));
		assertEquals(1, fc.getInDegree(3));
		assertEquals(1, fc.getOutDegree(3));
		
		assertEquals(2, fc.getDegree(4));
		assertEquals(0, fc.getInDegree(4));
		assertEquals(2, fc.getOutDegree(4));
		
		assertEquals(3, fc.getDegree(5));
		assertEquals(2, fc.getInDegree(5));
		assertEquals(1, fc.getOutDegree(5));
		
		assertEquals(3, fc.getDegree(6));
		assertEquals(2, fc.getInDegree(6));
		assertEquals(1, fc.getOutDegree(6));
		
	}

}
