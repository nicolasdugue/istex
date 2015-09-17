package io.reader.factory;

import java.io.FileNotFoundException;

import io.reader.IMatrixReader;
import io.reader.MatrixReader;

public class FactoryMatrixReader implements FactoryReader {

	@Override
	public IMatrixReader getReader(String fileName) throws FileNotFoundException {
		return new MatrixReader(fileName);
	}

}
