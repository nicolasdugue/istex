package model.util.factory;

import io.reader.factory.FactoryElmReader;
import io.reader.factory.FactoryNrmReader;

public class NrmElmFactory extends AFactory {

	public NrmElmFactory() {
		super(new FactoryNrmReader(), new FactoryElmReader());
	}

}
