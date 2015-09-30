package io.reader.factory.interfaces;

import java.io.FileNotFoundException;

import io.reader.interfaces.IClusteringReader;

public interface IFactoryClusteringReader {
	public IClusteringReader getReader(String fileName) throws FileNotFoundException;
}
