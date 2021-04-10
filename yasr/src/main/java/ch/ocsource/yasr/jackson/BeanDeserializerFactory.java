package ch.ocsource.yasr.jackson;

import java.nio.file.Path;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;

public class BeanDeserializerFactory extends com.fasterxml.jackson.databind.deser.BeanDeserializerFactory {
	private static final long serialVersionUID = -6989867428476804889L;
	private Path path;
	public BeanDeserializerFactory(Path path, DeserializerFactoryConfig config) {
		super(config);
		this.path = path;
	}
	
	@Override
	protected BeanDeserializerBuilder constructBeanDeserializerBuilder(DeserializationContext ctxt,
            BeanDescription beanDesc) {
		return new BeanDeserializerBuilder(path, beanDesc, ctxt);
    }
}
