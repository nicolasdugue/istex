package util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class LineNumber {

	public static int getNbLines(String file) throws IOException {
		LineNumberReader  lnr = new LineNumberReader(new FileReader(new File(file)));
		lnr.skip(Long.MAX_VALUE); 
		// Finally, the LineNumberReader object should be closed to prevent resource leak
		lnr.close();
		//Add 1 because line index starts at 0
		return lnr.getLineNumber() + 1;
	}
}
