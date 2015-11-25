package test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import io.reader.ArcsReader;
import io.reader.interfaces.IMatrixReader;
import model.cluster.Clustering;
import model.matrix.CsrMatrix;
import model.matrix.CsrMatrixClustered;
import model.util.nuplet.PairF;

public class TestCsrMatrixArcs {

	private CsrMatrix cm;
	private CsrMatrixClustered cm_c;

	@Before
	public void setUp() throws Exception {
		IMatrixReader mr = new ArcsReader("exemples/matrix_arcs");
		Clustering c = new Clustering(4);
		c.add(0);
		c.add(0);
		c.add(1);
		c.add(1);
		cm = new CsrMatrix(mr);

		cm_c = new CsrMatrixClustered(cm, c);
	}

	@Test
	public void testCsrMatrixMatrixReader() {
		assertEquals(new PairF(0, 2f), cm.getIinRows(0));
		assertEquals(new PairF(3, 7f), cm.getIinRows(1));
		assertEquals(new PairF(0, 1f), cm.getIinRows(2));
		assertEquals(new PairF(1, 3f), cm.getIinRows(3));
		assertEquals(new PairF(3, 8f), cm.getIinRows(4));
		assertEquals(new PairF(2, 4f), cm.getIinRows(5));

		assertEquals(2, cm.getCumulativeRows(0));
		assertEquals(4, cm.getCumulativeRows(1));
		assertEquals(5, cm.getCumulativeRows(2));
		assertEquals(6, cm.getCumulativeRows(3));

		assertEquals(new PairF(0, 2f), cm.getIinColumns(0));
		assertEquals(new PairF(1, 1f), cm.getIinColumns(1));
		assertEquals(new PairF(1, 3f), cm.getIinColumns(2));
		assertEquals(new PairF(3, 4f), cm.getIinColumns(3));
		assertEquals(new PairF(0, 7f), cm.getIinColumns(4));
		assertEquals(new PairF(2, 8f), cm.getIinColumns(5));

		assertEquals(2, cm.getCumulativeColumns(0));
		assertEquals(3, cm.getCumulativeColumns(1));
		assertEquals(4, cm.getCumulativeColumns(2));
		assertEquals(6, cm.getCumulativeColumns(3));

	}

	/*
	 * --------------------SUM---------------
	 * 
	 */
	@Test
	public void testCsrSumCol() {
		System.out.println(cm.getSumCol(0)+" " +cm.getSumCol(1) +" "+ cm.getSumCol(2) +" "+cm.getSumCol(3));
		assertEquals(3, cm.getSumCol(0),0.001);
		assertEquals(3, cm.getSumCol(1),0.001);
		assertEquals(4, cm.getSumCol(2),0.001);
		assertEquals(15, cm.getSumCol(3),0.001);
	}

	@Test
	public void testCsrSumRow() {
		assertEquals(9, cm.getSumRow(0),0.001);
		assertEquals(4, cm.getSumRow(1),0.001);
		assertEquals(8, cm.getSumRow(2),0.001);
		assertEquals(4, cm.getSumRow(3),0.001);
	}

	/*
	 * ------------------GET--------------
	 */
	@Test
	public void testGetNbElements() {
		assertEquals(6, cm_c.getNbElements());
	}

	@Test
	public void testGetNbRows() {
		assertEquals(4, cm_c.getNbRows());
	}

	@Test
	public void testGetNbColumns() {
		assertEquals(4, cm_c.getNbColumns());
	}

	@Test
	public void testGetRow() {
		assertEquals(2, cm.getRow(0).length);
		assertEquals(2, cm.getRow(1).length);
		assertEquals(1, cm.getRow(2).length);
		assertEquals(1, cm.getRow(3).length);

		assertTrue(Arrays.asList(cm.getRow(0)).contains(new PairF(0, 2f)) && Arrays.asList(cm.getRow(0)).contains(new PairF(3, 7f))
				&& Arrays.asList(cm.getRow(1)).contains(new PairF(0, 1f)) && Arrays.asList(cm.getRow(1)).contains(new PairF(1, 3f))
				&& Arrays.asList(cm.getRow(2)).contains(new PairF(3, 8f)) && Arrays.asList(cm.getRow(3)).contains(new PairF(2, 4f)));
	}

	public void getColumn() {
		assertEquals(2, cm.getColumn(0).length);
		assertEquals(1, cm.getColumn(1).length);
		assertEquals(1, cm.getColumn(2).length);
		assertEquals(2, cm.getColumn(3).length);

		assertTrue(Arrays.asList(cm.getColumn(0)).contains(new PairF(0, 2f)) && Arrays.asList(cm.getColumn(0)).contains(new PairF(1, 1f))
				&& Arrays.asList(cm.getColumn(1)).contains(new PairF(1, 3f)) && Arrays.asList(cm.getColumn(2)).contains(new PairF(3, 4f))
				&& Arrays.asList(cm.getColumn(3)).contains(new PairF(0, 7f)) && Arrays.asList(cm.getColumn(3)).contains(new PairF(2, 8f)));
	}

}
