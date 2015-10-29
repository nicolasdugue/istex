package io.reader.factory;


import java.io.IOException;

import io.reader.LabelReader;
import io.reader.factory.interfaces.IFactoryLabelReader;
import io.reader.interfaces.ILabelReader;

public class FactorySimpleLabelReader implements IFactoryLabelReader{

	@Override
	public ILabelReader getReader(String fileName) throws IOException {
		return new LabelReader(fileName);
	}

}
