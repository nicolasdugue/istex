package io.reader.factory.interfaces;

import java.io.FileNotFoundException;

import io.reader.interfaces.IMatrixReader;

public interface IFactoryDataReader {
	public IMatrixReader getReader(String fileName) throws FileNotFoundException;
}
