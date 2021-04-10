package ch.ocsource.yasr.jackson;

import java.nio.file.Path;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;

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
	
	@Override
    public DeserializerFactory withConfig(DeserializerFactoryConfig config) {
		if (_factoryConfig == config) {
            return this;
        }
        /* 22-Nov-2010, tatu: Handling of subtypes is tricky if we do immutable-with-copy-ctor;
         *    and we pretty much have to here either choose between losing subtype instance
         *    when registering additional deserializers, or losing deserializers.
         *    Instead, let's actually just throw an error if this method is called when subtype
         *    has not properly overridden this method; this to indicate problem as soon as possible.
         */
        ClassUtil.verifyMustOverride(BeanDeserializerFactory.class, this, "withConfig");
        return new BeanDeserializerFactory(path, config);
	}
}
