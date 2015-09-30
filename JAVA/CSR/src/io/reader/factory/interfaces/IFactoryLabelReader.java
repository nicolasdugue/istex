package io.reader.factory.interfaces;

import java.io.FileNotFoundException;
import io.reader.interfaces.ILabelReader;

public interface IFactoryLabelReader {
	public ILabelReader getReader(String fileName) throws FileNotFoundException;
}
