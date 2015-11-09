package model.util.factory;

import io.reader.factory.FactoryElmReader;
import io.reader.factory.FactoryGraphReader;

public class GraphElmFactory extends AFactory {

	public GraphElmFactory() {
		super(new FactoryGraphReader(), new FactoryElmReader());
	}

}
