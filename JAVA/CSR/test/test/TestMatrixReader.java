package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import io.reader.MatrixReader;
import model.util.nuplet.PairF;

public class TestMatrixReader {

	private MatrixReader m;
	@Before
	public void setUp() throws Exception {
		m = new MatrixReader("matrix");

	}

	@Test
	public void testRead() {
			//Rows
			assertEquals(m.getXijRows(0, 0),(new PairF(0,2f)));
			assertEquals(m.getXijRows(0, 1),(new PairF(3,7f)));
			
			assertEquals(m.getXijRows(1, 0),(new PairF(0,1f)));
			assertEquals(m.getXijRows(1, 1),(new PairF(1,3f)));
			
			assertEquals(m.getXijRows(2, 0),(new PairF(3,8f)));
			
			assertEquals(m.getXijRows(3, 0),(new PairF(2,4f)));
			
			
			//Columns
			assertEquals(m.getXijColumns(0, 0),(new PairF(0,2f)));
			assertEquals(m.getXijColumns(0, 1),(new PairF(1,1f)));
			
			assertEquals(m.getXijColumns(1, 0),(new PairF(1,3f)));
			
			assertEquals(m.getXijColumns(2, 0),(new PairF(3,4f)));
			
			assertEquals(m.getXijColumns(3, 0),(new PairF(0,7f)));
			assertEquals(m.getXijColumns(3, 1),(new PairF(2,8f)));
			
			//Descriptors
			assertEquals(m.getNb_columns(),4);
			assertEquals(m.getNb_rows(),4);
			assertEquals(m.getNb_elmt(), 6);
		
	}

}
