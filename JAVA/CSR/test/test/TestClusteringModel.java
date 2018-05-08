package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.cluster.Clustering;
import model.cluster.OverlappingClustering;
import model.cluster.decorator.IClustering;

public class TestClusteringModel {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAdd() {
		IClustering c = new OverlappingClustering(11);
		c.add(0,0);
		c.add(1,4);
		c.add(2,1);
		c.add(3,0);
		c.add(4,0);
		c.add(5,0);
		c.add(6,4);
		c.add(7,1);
		c.add(8,2);
		c.add(9,3);
		c.add(10,1);
		
		assertTrue(c.getObjectsInCk(2).contains(8));
		assertTrue(c.getObjectsInCk(3).contains(9));
		assertTrue(c.getObjectsInCk(0).contains(0));
		assertTrue(c.getObjectsInCk(0).contains(3));
		assertTrue(c.getObjectsInCk(0).contains(4));
		assertTrue(c.getObjectsInCk(0).contains(5));
		assertTrue(c.getObjectsInCk(4).contains(1));
		assertTrue(c.getObjectsInCk(4).contains(6));
		assertTrue(c.getObjectsInCk(1).contains(2));
		assertTrue(c.getObjectsInCk(1).contains(7));
		assertTrue(c.getObjectsInCk(1).contains(10));
	}

}
