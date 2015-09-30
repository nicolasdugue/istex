package model.util.factory;

import io.reader.factory.FactoryElmReader;
import io.reader.factory.FactoryNrmReader;

public class VieillissementFactory extends AFactory {

	public VieillissementFactory() {
		super(new FactoryNrmReader(), new FactoryElmReader());
	}

}
