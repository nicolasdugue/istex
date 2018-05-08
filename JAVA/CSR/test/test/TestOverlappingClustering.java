package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import io.reader.ArcsReader;
import io.reader.ClusteringOverlappingReader;
import io.reader.ClusteringReader;
import io.reader.LabelReader;
import io.reader.MatrixReader;
import model.featureselection.FeaturesSelection;
import model.matrix.CsrMatrix;
import model.matrix.CsrMatrixClustered;
import model.matrix.decorator.IMatrix;
import model.matrix.decorator.MatrixFeatureLabels;
import model.roles.FunctionalCartography;

public class TestOverlappingClustering {

	private FunctionalCartography fc;
	private CsrMatrixClustered csrc;
	
	private FeaturesSelection fs;


	@Before
	public void setUp() throws Exception {
		ArcsReader mr = new ArcsReader("exemples/Guimera_matrix");
		//mr.log.setLevel(Le);
		CsrMatrix csr = new CsrMatrix(mr);
		ClusteringOverlappingReader cr = new ClusteringOverlappingReader("exemples/Guimera_community_overlap");
		System.out.println(cr.getClusters().getObjectsInCk(0));
		System.out.println(cr.getClusters().getObjectsInCk(1));
		System.out.println(cr.getClusters().getObjectsInCk(2));
		System.out.println(cr.getClusters().getObjectsInCk(3));
		csrc = new CsrMatrixClustered(csr, cr.getClusters());
		fc=new FunctionalCartography(csrc);
		fc.doZScore();
		
		
		MatrixReader iskoMaghreb = new MatrixReader("exemples/matrix_lamirel_iskomaghreb");
		ClusteringOverlappingReader rd = new ClusteringOverlappingReader("exemples/clustering_lamirel_iskomaghreb_overlapping");
		IMatrix m = new CsrMatrix(iskoMaghreb);
		LabelReader l = new LabelReader("exemples/label_lamirel_iskomaghreb");
		IMatrix ml = new MatrixFeatureLabels(m, l.getLs());
		CsrMatrixClustered cm_im = new CsrMatrixClustered(ml, rd.getClusters());
		fs = new FeaturesSelection(cm_im);
	}

	@Test
	public void testZScore() {
		/***--------------Z-score***/
		//Community 0 - Node 0,1,2
		assertEquals(2, fc.getDegree(0));
		assertEquals(2, fc.getDegreeInCom(0, 0));
		assertEquals(6, fc.getDegree(2));
		System.out.println(fc.getInDegreeInCom(2, 0));
		System.out.println(fc.getOutDegreeInCom(2, 0));
		assertEquals(4, fc.getDegreeInCom(2, 0));
		assertEquals(2, fc.getDegree(1));
		assertEquals(2, fc.getDegreeInCom(1, 0));
		//System.out.println(fc.getAllComunityInternalDegree(0));
		float mean = fc.mean(fc.getAllComunityInternalDegree(0));
		float std = fc.std( fc.getAllComunityInternalDegree(0), mean);
		assertEquals(2.6666,mean,0.0001);
		assertEquals(0.5443,std, 0.0001);
		assertEquals(-1.225,fc.getZScore(0,0),  0.001);
		assertEquals(-1.225,fc.getZScore(0,1), 0.001 );
		assertEquals(2.450, fc.getZScore(0,2), 0.001 );
		//Community 1 - Node 3,4,5,6
		mean = fc.mean(fc.getAllComunityInternalDegree(1));
		std = fc.std( fc.getAllComunityInternalDegree(1), mean);
		assertEquals(5,mean,0.0001);
		assertEquals(0.5,std, 0.0001);
		assertEquals(-2,fc.getZScore(1,3),  0.001);
		assertEquals(-2,fc.getZScore(1,6), 0.001 );
		assertEquals(2, fc.getZScore(1,4), 0.001 );
		assertEquals(2, fc.getZScore(1,5), 0.001 );		
		
	}
	
	@Test
	public void testCoefParticipation() {
		/***--------------Participation coefficient***/
		//Community 0 - Node 0,1,2
		assertEquals(-1,fc.getParticipationCoefficient(0),  0.001);
		assertEquals(-1,fc.getParticipationCoefficient(1), 0.001 );
		// 1 - 2*(4/6)² - 2*(2/6)²
		assertEquals(-0.111, fc.getParticipationCoefficient(2), 0.001 );
		assertEquals(-0.111, fc.getParticipationCoefficient(3), 0.001 );
		assertEquals(-1,fc.getParticipationCoefficient(4),  0.001);
		assertEquals(-1,fc.getParticipationCoefficient(5), 0.001 );
		assertEquals(-1,fc.getParticipationCoefficient(6), 0.001 );
	}
	
	@Test
	public void testgetFeatureValue() {
		assertEquals(0.45, fs.getFeatureValue(0, 0), 0.01); // Bug papier ISKO
															// MAGHREB
		assertEquals(0.22, fs.getFeatureValue(0, 1), 0.01);
		assertEquals(0.39, fs.getFeatureValue(1, 0), 0.01);
		assertEquals(0.66, fs.getFeatureValue(1, 1), 0.01);
		assertEquals(0.30, fs.getFeatureValue(2, 0), 0.01);
		assertEquals(0.24, fs.getFeatureValue(2, 1), 0.01);
		
		assertEquals(0.45, fs.getFeatureValue(0, 2), 0.01); // Bug papier ISKO
		// MAGHREB
		assertEquals(0.22, fs.getFeatureValue(0, 3), 0.01);
		assertEquals(0.39, fs.getFeatureValue(1, 2), 0.01);
		assertEquals(0.66, fs.getFeatureValue(1, 3), 0.01);
		assertEquals(0.30, fs.getFeatureValue(2, 2), 0.01);
		assertEquals(0.24, fs.getFeatureValue(2, 3), 0.01);
	}

	@Test
	public void testGetFeatureFMeanFMeasure() {
		assertEquals(0.34, fs.getFeatureFMeanValue(0), 0.01);
		assertEquals(0.53, fs.getFeatureFMeanValue(1), 0.01);
		assertEquals(0.27, fs.getFeatureFMeanValue(2), 0.01);
	}

}
