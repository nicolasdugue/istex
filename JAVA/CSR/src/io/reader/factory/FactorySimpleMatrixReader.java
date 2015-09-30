package io.reader.factory;

import java.io.FileNotFoundException;

import io.reader.MatrixReader;
import io.reader.factory.interfaces.IFactoryDataReader;
import io.reader.interfaces.IMatrixReader;

public class FactorySimpleMatrixReader implements IFactoryDataReader {

	@Override
	public IMatrixReader getReader(String fileName) throws FileNotFoundException {
		return new MatrixReader(fileName);
	}

}
