package model.util.factory;

import io.reader.factory.FactoryGraphReader;

public class GraphFactory extends AFactory {

	public GraphFactory() {
		super(new FactoryGraphReader());
	}

}
