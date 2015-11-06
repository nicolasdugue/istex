package model.util.factory;

import io.reader.factory.FactoryNrmReader;

public class NrmClusteringFactory extends AFactory{

	public NrmClusteringFactory() {
		super(new FactoryNrmReader());
	}
}
