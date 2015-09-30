package io.reader.factory;

import java.io.FileNotFoundException;

import io.reader.ClusteringReader;
import io.reader.factory.interfaces.IFactoryClusteringReader;
import io.reader.interfaces.IClusteringReader;

public class FactorySimpleClusteringReader implements IFactoryClusteringReader {

	@Override
	public IClusteringReader getReader(String fileName) throws FileNotFoundException {
		return new ClusteringReader(fileName);
	}

}
