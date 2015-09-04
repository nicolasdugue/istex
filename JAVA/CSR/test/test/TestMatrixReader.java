package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import io.reader.MatrixReader;
import model.util.PairI;

public class TestMatrixReader {

	private MatrixReader m;
	@Before
	public void setUp() throws Exception {
		m = new MatrixReader("matrix");

	}

	@Test
	public void testRead() {
			//Rows
			assertEquals(m.getXijRows(0, 0),(new PairI(0,2)));
			assertEquals(m.getXijRows(0, 1),(new PairI(3,7)));
			
			assertEquals(m.getXijRows(1, 0),(new PairI(0,1)));
			assertEquals(m.getXijRows(1, 1),(new PairI(1,3)));
			
			assertEquals(m.getXijRows(2, 0),(new PairI(3,8)));
			
			assertEquals(m.getXijRows(3, 0),(new PairI(2,4)));
			
			
			//Columns
			assertEquals(m.getXijColumns(0, 0),(new PairI(0,2)));
			assertEquals(m.getXijColumns(0, 1),(new PairI(1,1)));
			
			assertEquals(m.getXijColumns(1, 0),(new PairI(1,3)));
			
			assertEquals(m.getXijColumns(2, 0),(new PairI(3,4)));
			
			assertEquals(m.getXijColumns(3, 0),(new PairI(0,7)));
			assertEquals(m.getXijColumns(3, 1),(new PairI(2,8)));
			
			//Descriptors
			assertEquals(m.getNb_columns(),4);
			assertEquals(m.getNb_rows(),4);
			assertEquals(m.getNb_elmt(), 6);
		
	}

}
