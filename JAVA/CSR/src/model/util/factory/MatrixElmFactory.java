package model.util.factory;

import io.reader.factory.FactoryElmReader;
import io.reader.factory.FactorySimpleMatrixReader;

public class MatrixElmFactory extends AFactory{

	public MatrixElmFactory() {
		super(new FactorySimpleMatrixReader(), new FactoryElmReader());
	}
}
