package io.reader.factory;

import java.io.FileNotFoundException;

import io.reader.LabelReader;
import io.reader.factory.interfaces.IFactoryLabelReader;
import io.reader.interfaces.ILabelReader;

public class FactorySimpleLabelReader implements IFactoryLabelReader{

	@Override
	public ILabelReader getReader(String fileName) throws FileNotFoundException {
		return new LabelReader(fileName);
	}

}
