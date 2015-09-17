package io.reader.factory;

import java.io.FileNotFoundException;

import io.reader.ArcsReader;
import io.reader.IMatrixReader;

public class FactoryGraphReader implements FactoryReader {

	@Override
	public IMatrixReader getReader(String fileName) throws FileNotFoundException{
		return new ArcsReader(fileName);
	}
}
