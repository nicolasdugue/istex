package io.reader.factory;

import java.io.FileNotFoundException;

import io.reader.ArcsReader;
import io.reader.factory.interfaces.IFactoryDataReader;
import io.reader.interfaces.IMatrixReader;

public class FactoryGraphReader implements IFactoryDataReader {

	@Override
	public IMatrixReader getReader(String fileName) throws FileNotFoundException{
		return new ArcsReader(fileName);
	}
}
