package io.reader.factory;

import java.io.FileNotFoundException;

import io.reader.NrmReader;
import io.reader.factory.interfaces.IFactoryDataReader;
import io.reader.interfaces.IMatrixReader;

public class FactoryNrmReader implements IFactoryDataReader {

	@Override
	public IMatrixReader getReader(String fileName) throws FileNotFoundException {
		return new NrmReader(fileName);
	}

}
