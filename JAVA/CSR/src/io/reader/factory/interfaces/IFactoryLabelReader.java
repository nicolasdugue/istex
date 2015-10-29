package io.reader.factory.interfaces;

import java.io.IOException;

import io.reader.interfaces.ILabelReader;

public interface IFactoryLabelReader {
	public ILabelReader getReader(String fileName) throws  IOException;
}
