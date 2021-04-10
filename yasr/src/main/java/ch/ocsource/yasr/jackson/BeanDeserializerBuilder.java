package ch.ocsource.yasr.jackson;

import java.nio.file.Path;
import java.util.Collection;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdValueProperty;

public class BeanDeserializerBuilder extends com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder {
	private Path path;
	public BeanDeserializerBuilder(Path path, BeanDescription beanDesc, DeserializationContext ctxt) {
		super(beanDesc, ctxt);
		this.path = path;
	}
	
	@Override
	/**
     * Method for constructing a {@link BeanDeserializer}, given all
     * information collected.
     */
    public JsonDeserializer<?> build()
    {
		Collection<SettableBeanProperty> props = _properties.values();
        _fixAccess(props);
        BeanPropertyMap propertyMap = BeanPropertyMap.construct(_config, props,
                _collectAliases(props),
                _findCaseInsensitivity());
        propertyMap.assignIndexes();

        // view processing must be enabled if:
        // (a) fields are not included by default (when deserializing with view), OR
        // (b) one of properties has view(s) to included in defined
        boolean anyViews = !_config.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION);
        if (!anyViews) {
            for (SettableBeanProperty prop : props) {
                if (prop.hasViews()) {
                    anyViews = true;
                    break;
                }
            }
        }

        // one more thing: may need to create virtual ObjectId property:
        if (_objectIdReader != null) {
            /* 18-Nov-2012, tatu: May or may not have annotations for id property;
             *   but no easy access. But hard to see id property being optional,
             *   so let's consider required at this point.
             */
            ObjectIdValueProperty prop = new ObjectIdValueProperty(_objectIdReader, PropertyMetadata.STD_REQUIRED);
            propertyMap = propertyMap.withProperty(prop);
        }

        return new BeanDeserializer(path, this,
                _beanDesc, propertyMap, _backRefProperties, _ignorableProps, _ignoreAllUnknown, _includableProps,
                anyViews);
    }
}
