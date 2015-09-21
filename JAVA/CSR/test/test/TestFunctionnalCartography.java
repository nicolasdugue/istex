package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import io.reader.ArcsReader;
import io.reader.ClusteringReader;
import model.matrix.CsrMatrix;
import model.matrix.CsrMatrixClustered;
import model.roles.FunctionalCartography;

public class TestFunctionnalCartography {

	private FunctionalCartography fc;
	@Before
	public void setUp() throws Exception {
		ArcsReader mr = new ArcsReader("Guimera_matrix");
		CsrMatrix csr = new CsrMatrix(mr);
		ClusteringReader cr = new ClusteringReader("Guimera_community");
		CsrMatrixClustered csrc = new CsrMatrixClustered(csr, cr.getClusters());
		fc=new FunctionalCartography(csrc);
		fc.doZScore();
	}

	@Test
	public void testZScore() {
		/***--------------Z-score***/
		//Community 0 - Node 0,1,2
		assertEquals(1, fc.getDegree(0));
		assertEquals(1, fc.getInternalDegree(0));
		assertEquals(3, fc.getDegree(2));
		assertEquals(2, fc.getInternalDegree(2));
		float mean = fc.mean(fc.getAllComunityInternalDegree(0));
		float std = fc.std( fc.getAllComunityInternalDegree(0), mean);
		assertEquals(1.3333,mean,0.0001);
		assertEquals(0.2721,std, 0.0001);
		assertEquals(-1.225,fc.getZScore(0),  0.001);
		assertEquals(-1.225,fc.getZScore(1), 0.001 );
		assertEquals(2.450, fc.getZScore(2), 0.001 );
		//Community 1 - Node 3,4,5,6
		mean = fc.mean(fc.getAllComunityInternalDegree(1));
		std = fc.std( fc.getAllComunityInternalDegree(1), mean);
		assertEquals(2.5,mean,0.0001);
		assertEquals(0.25,std, 0.0001);
		assertEquals(-2,fc.getZScore(3),  0.001);
		assertEquals(-2,fc.getZScore(6), 0.001 );
		assertEquals(2, fc.getZScore(4), 0.001 );
		assertEquals(2, fc.getZScore(5), 0.001 );		
		
	}
	
	@Test
	public void testCoefParticipation() {
		/***--------------Participation coefficient***/
		//Community 0 - Node 0,1,2
		assertEquals(0,fc.getParticipationCoefficient(0),  0.001);
		assertEquals(0,fc.getParticipationCoefficient(1), 0.001 );
		assertEquals(0.444444444, fc.getParticipationCoefficient(2), 0.001 );
		assertEquals(0.444444444, fc.getParticipationCoefficient(3), 0.001 );
		assertEquals(0,fc.getParticipationCoefficient(4),  0.001);
		assertEquals(0,fc.getParticipationCoefficient(5), 0.001 );
		assertEquals(0,fc.getParticipationCoefficient(6), 0.001 );
	}

}
