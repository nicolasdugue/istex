package model.util.factory;

import io.reader.factory.FactorySimpleMatrixReader;

public class MatrixFactory extends AFactory {

	public MatrixFactory() {
		super(new FactorySimpleMatrixReader());
	}

}
