package test;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import io.reader.NrmReader;
import io.reader.interfaces.IMatrixReader;
import model.matrix.CsrMatrix;
import model.util.nuplet.PairF;

public class TestCsrMatrixNrm {
	private CsrMatrix cm;

	@Before
	public void setUp() throws Exception {
		IMatrixReader mr = new NrmReader("exemples/matrix_lamirel_iskomaghreb.nrm");
		cm = new CsrMatrix(mr);
	}

	@Test
	public void testCsrMatrixMatrixReader() {
		assertEquals(new PairF(0, 9f), cm.getIinRows(0));
		assertEquals(new PairF(1, 5f), cm.getIinRows(1));
		assertEquals(new PairF(2, 5f), cm.getIinRows(2));
		assertEquals(new PairF(0, 9f), cm.getIinRows(3));
		assertEquals(new PairF(1, 10f), cm.getIinRows(4));
		assertEquals(new PairF(2, 5f), cm.getIinRows(5));
		assertEquals(new PairF(0, 5f), cm.getIinRows(15));
		assertEquals(new PairF(1, 25f), cm.getIinRows(16));
		assertEquals(new PairF(2, 5f), cm.getIinRows(17));

		assertEquals(3, cm.getCumulativeRows(0));
		assertEquals(6, cm.getCumulativeRows(1));
		assertEquals(9, cm.getCumulativeRows(2));
		assertEquals(12, cm.getCumulativeRows(3));

		assertEquals(new PairF(0, 9f), cm.getIinColumns(0));
		assertEquals(new PairF(1, 9f), cm.getIinColumns(1));
		assertEquals(new PairF(2, 9f), cm.getIinColumns(2));
		assertEquals(new PairF(0, 5f), cm.getIinColumns(6));
		assertEquals(new PairF(1, 10f), cm.getIinColumns(7));
		assertEquals(new PairF(2, 20f), cm.getIinColumns(8));
		assertEquals(new PairF(0, 5f), cm.getIinColumns(12));
		assertEquals(new PairF(1, 5f), cm.getIinColumns(13));
		assertEquals(new PairF(2, 6f), cm.getIinColumns(14));

		assertEquals(6, cm.getCumulativeColumns(0));
		assertEquals(12, cm.getCumulativeColumns(1));
		assertEquals(18, cm.getCumulativeColumns(2));
	}

	/*
	 * --------------------SUM---------------
	 * 
	 */
	@Test
	public void testCsrSumCol() {
		assertEquals(43, cm.getSumCol(0),0.001);
		assertEquals(100, cm.getSumCol(1),0.001);
		assertEquals(32, cm.getSumCol(2),0.001);
	}

	@Test
	public void testCsrSumRow() {
		assertEquals(19, cm.getSumRow(0),0.001);
		assertEquals(24, cm.getSumRow(1),0.001);
		assertEquals(35, cm.getSumRow(2),0.001);
		assertEquals(25, cm.getSumRow(3),0.001);
	}

	/*
	 * ------------------GET--------------
	 */
	@Test
	public void testGetNbElements() {
		assertEquals(18, cm.getNbElements());
	}

	@Test
	public void testGetNbRows() {
		assertEquals(6, cm.getNbRows());
	}

	@Test
	public void testGetNbColumns() {
		assertEquals(3, cm.getNbColumns());
	}

	@Test
	public void testGetRow() {
		assertEquals(3, cm.getRow(0).length);
		assertEquals(3, cm.getRow(1).length);
		assertEquals(3, cm.getRow(2).length);
		assertEquals(3, cm.getRow(3).length);

		}

	public void getColumn() {
		assertEquals(6, cm.getColumn(0).length);
		assertEquals(6, cm.getColumn(1).length);
		assertEquals(6, cm.getColumn(2).length);
	}

}
