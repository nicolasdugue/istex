package io.reader.factory.interfaces;

import java.io.IOException;

import io.reader.interfaces.IClusteringReader;

public interface IFactoryClusteringReader {
	public IClusteringReader getReader(String fileName) throws IOException;
}
