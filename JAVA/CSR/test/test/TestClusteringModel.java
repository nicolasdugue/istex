package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.cluster.Clustering;

public class TestClusteringModel {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAdd() {
		Clustering c = new Clustering(11);
		c.add(0);
		c.add(4);
		c.add(1);
		c.add(0);
		c.add(0);
		c.add(0);
		c.add(4);
		c.add(1);
		c.add(2);
		c.add(3);
		c.add(1);
		
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
