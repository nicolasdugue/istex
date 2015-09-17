package io.reader.factory;

import java.io.FileNotFoundException;

import io.reader.IMatrixReader;

public interface FactoryReader {
	public IMatrixReader getReader(String fileName) throws FileNotFoundException;
}
