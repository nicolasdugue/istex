package test;

import static org.junit.Assert.*;

import java.awt.List;
import java.util.AbstractList;
import java.util.ArrayList;

import io.reader.ClusteringReader;
import io.reader.MatrixReader;
import model.featureselection.FeaturesSelection;
import model.matrix.CsrMatrix;
import model.matrix.CsrMatrixClustered;
import model.matrix.decorator.IMatrix;
import model.quality.Quality;
import model.util.nuplet.PairF;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import model.util.nuplet.PairF;

import util.SGLogger;

public class TestQuality {
	private FeaturesSelection fs;
	private CsrMatrixClustered mc;
	static Logger log=SGLogger.getInstance();
	
	@Before
	public void setUp() throws Exception {
		
		MatrixReader mr = new MatrixReader("exemples/matrix_lamirel_iskomaghreb");
		ClusteringReader cr = new ClusteringReader("exemples/clustering_lamirel_iskomaghreb");
		IMatrix im = new CsrMatrix(mr);
		mc = new CsrMatrixClustered( im, cr.getClusters());
		fs = new FeaturesSelection(mc);	
		
		
	}
	
	@Test
	public void testContrast() {
		double precision = 0.01;
		assertEquals(1.32, Quality.getContrast(fs, 0 , 0), 0.01); // Taille pieds et Longueur cheveux inversés
		//par rapport au papier Isko
		assertEquals(0.67, Quality.getContrast(fs,0, 1), precision);
		assertEquals(0.74, Quality.getContrast(fs,1, 0), precision);
		assertEquals(1.25, Quality.getContrast(fs,1, 1), precision);
		assertEquals(1.07, Quality.getContrast(fs,2, 0), precision);
		assertEquals(0.92, Quality.getContrast(fs,2, 1), precision);
	}
	
	@Test
	public void testPC() {
		double precision = 0.01;
		assertEquals(0.43, Quality.getPC(fs), precision);
	}
	
	@Test
	public void testEC() { 
		double precision = 0.01;
		assertEquals(0.69, Quality.getEC(fs), precision);
	}
	
	@Test
	public void testDistanceSquare() { 
		double precision = 0.01;
		ArrayList<Float> X = new ArrayList<Float>();
		ArrayList<Float> Y = new ArrayList<Float>();
		ArrayList<Float> Z = new ArrayList<Float>();
		Float[] x = {0f,0f,0f,0f};
		PairF[] y = {new PairF(0,1f),new PairF(0,1f),new PairF(0,1f),new PairF(0,1f)};
		PairF[] z = {new PairF(0,0f),new PairF(0,0f),new PairF(0,0f),new PairF(0,0f)};
		for(int i =0; i<4;i++)
			{
				X.add(0f);
				Y.add(1f);
				Z.add(2f);
								
			}
		//log.error(Y);
		assertEquals(0.0, Quality.getDistanceSquare(X,X), precision);
		assertEquals(0.0, Quality.getDistanceSquare(y,y), precision);

		assertEquals(4.0, Quality.getDistanceSquare(y,x), precision);
		assertEquals(4.0, Quality.getDistanceSquare(z,y), precision);
		assertEquals(4.0, Quality.getDistanceSquare(X,Y), precision);
				
	}
	
	@Test
	public void testGetDiameter() { 
		double precision = 0.01;
		assertEquals(6.25, Quality.getDiameter(0, mc), precision);
		assertEquals(4.76, Quality.getDiameter(1, mc), precision);
	}
	
	@Test
	public void testGetDiameterSquared() { 
		double precision = 0.01;
		assertEquals(117.33, Quality.getDiameterSquared(0, mc), precision);
		assertEquals(68.00, Quality.getDiameterSquared(1, mc), precision);
	}

	@Test
	public void testGetDiss() { 
		double precision = 0.01;
		assertEquals(0f, Quality.getDiss(0,0, mc), precision);
		assertEquals(5.83, Quality.getDiss(0,1, mc), precision);
	}
	
	@Test
	public void testGetDB() { 
		double precision = 0.01;
		assertEquals(1.88, Quality.getDB(mc), precision);
	}
	
	@Test
	public void testGetDiameterDU() { 
		double precision = 0.01;
		assertEquals(15.03, Quality.getDiameterDU(0,mc), precision);
		assertEquals(10.09, Quality.getDiameterDU(1,mc), precision);
	}
	
	@Test
	public void testGetDistanceDU() { 
		double precision = 0.01;
		assertEquals(0f, Quality.getDistanceDU(0,0,mc), precision);
		assertEquals(5.83, Quality.getDistanceDU(0,1,mc), precision);
	}
	
	@Test
	public void testGetDU() { 
		double precision = 0.01;
		assertEquals(0.38, Quality.getDU(mc), precision);
	}
	
	@Test
	public void testGetIntraDistance() { 
		double precision = 0.01;
		assertEquals(185.33, Quality.getIntraDistance(mc), precision);
	}
	
	@Test
	public void testGetInterDistance() { 
		double precision = 0.01;
		assertEquals(432.83, Quality.getInterDistance(mc), precision);
	}
	
	@Test
	public void testGetCH() { 
		double precision = 0.01;
		assertEquals(9.34, Quality.getCH(mc), precision);
	}
	
	@Test
	public void testGetConductance() { 
		double precision = 0.01;
		assertEquals(0, Quality.getConductance(0,mc), precision);
		assertEquals(1.0, Quality.getConductance(1,mc), precision);
	}
	
	@Test
	public void testGetCutRatio() { 
		double precision = 0.01;
		assertEquals(0.0, Quality.getCutRatio(0,mc), precision);
		assertEquals(9.0, Quality.getCutRatio(1,mc), precision);
	}
}
