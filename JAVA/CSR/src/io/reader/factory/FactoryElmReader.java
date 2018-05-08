package io.reader.factory;

import java.io.IOException;

import io.reader.ElmReader;
import io.reader.factory.interfaces.IFactoryClusteringReader;
import io.reader.interfaces.IClusteringReader;

public class FactoryElmReader implements IFactoryClusteringReader {

	@Override
	public IClusteringReader getReader(String fileName) throws IOException {
		return new ElmReader(fileName);
	}


}
