package io.reader.factory;

import java.io.IOException;

import io.reader.ClusteringOverlappingReader;
import io.reader.factory.interfaces.IFactoryClusteringReader;
import io.reader.interfaces.IClusteringReader;

public class FactoryOverlappingClusteringReader implements IFactoryClusteringReader {

	@Override
	public IClusteringReader getReader(String fileName) throws IOException {
		return new ClusteringOverlappingReader(fileName);
	}

}
