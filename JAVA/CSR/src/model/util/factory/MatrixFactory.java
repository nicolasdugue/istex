package model.util.factory;

import io.reader.factory.FactoryMatrixReader;

public class MatrixFactory extends AFactory {

	public MatrixFactory() {
		super(new FactoryMatrixReader());
	}

}
