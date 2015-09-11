package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import io.reader.ArcsReader;
import io.reader.IMatrixReader;
import model.cluster.Clustering;
import model.matrix.CsrMatrix;
import model.matrix.CsrMatrixClustered;
import model.util.nuplet.PairI;

public class TestCsrMatrix {

	private CsrMatrix cm;
	private CsrMatrixClustered cm_c;

	@Before
	public void setUp() throws Exception {
		IMatrixReader mr = new ArcsReader("matrix_arcs");
		Clustering c = new Clustering();
		c.add(0);
		c.add(0);
		c.add(1);
		c.add(1);
		cm = new CsrMatrix(mr);

		cm_c = new CsrMatrixClustered(cm, c);
	}

	@Test
	public void testCsrMatrixMatrixReader() {
		assertEquals(new PairI(0, 2), cm.getIinRows(0));
		assertEquals(new PairI(3, 7), cm.getIinRows(1));
		assertEquals(new PairI(0, 1), cm.getIinRows(2));
		assertEquals(new PairI(1, 3), cm.getIinRows(3));
		assertEquals(new PairI(3, 8), cm.getIinRows(4));
		assertEquals(new PairI(2, 4), cm.getIinRows(5));

		assertEquals(2, cm.getCumulativeRows(0));
		assertEquals(4, cm.getCumulativeRows(1));
		assertEquals(5, cm.getCumulativeRows(2));
		assertEquals(6, cm.getCumulativeRows(3));

		assertEquals(new PairI(0, 2), cm.getIinColumns(0));
		assertEquals(new PairI(1, 1), cm.getIinColumns(1));
		assertEquals(new PairI(1, 3), cm.getIinColumns(2));
		assertEquals(new PairI(3, 4), cm.getIinColumns(3));
		assertEquals(new PairI(0, 7), cm.getIinColumns(4));
		assertEquals(new PairI(2, 8), cm.getIinColumns(5));

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
		assertEquals(3, cm.getSumCol(0));
		assertEquals(3, cm.getSumCol(1));
		assertEquals(4, cm.getSumCol(2));
		assertEquals(15, cm.getSumCol(3));
	}

	@Test
	public void testCsrSumRow() {
		assertEquals(9, cm.getSumRow(0));
		assertEquals(4, cm.getSumRow(1));
		assertEquals(8, cm.getSumRow(2));
		assertEquals(4, cm.getSumRow(3));
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
		assertEquals(2, cm.getRow(0).size());
		assertEquals(2, cm.getRow(1).size());
		assertEquals(1, cm.getRow(2).size());
		assertEquals(1, cm.getRow(3).size());

		assertTrue(cm.getRow(0).contains(new PairI(0, 2)) && cm.getRow(0).contains(new PairI(3, 7))
				&& cm.getRow(1).contains(new PairI(0, 1)) && cm.getRow(1).contains(new PairI(1, 3))
				&& cm.getRow(2).contains(new PairI(3, 8)) && cm.getRow(3).contains(new PairI(2, 4)));
	}

	public void getColumn() {
		assertEquals(2, cm.getColumn(0).size());
		assertEquals(1, cm.getColumn(1).size());
		assertEquals(1, cm.getColumn(2).size());
		assertEquals(2, cm.getColumn(3).size());

		assertTrue(cm.getColumn(0).contains(new PairI(0, 2)) && cm.getColumn(0).contains(new PairI(1, 1))
				&& cm.getColumn(1).contains(new PairI(1, 3)) && cm.getColumn(2).contains(new PairI(3, 4))
				&& cm.getColumn(3).contains(new PairI(0, 7)) && cm.getColumn(3).contains(new PairI(2, 8)));
	}

}
