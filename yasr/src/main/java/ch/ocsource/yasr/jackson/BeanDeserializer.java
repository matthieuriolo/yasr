package ch.ocsource.yasr.jackson;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;

public class BeanDeserializer extends com.fasterxml.jackson.databind.deser.BeanDeserializer {

	private static final long serialVersionUID = -6785128856829110896L;
	private Path path;
	public BeanDeserializer(Path path, BeanDeserializerBuilder builder, BeanDescription beanDesc, BeanPropertyMap properties,
			Map<String, SettableBeanProperty> backRefs, HashSet<String> ignorableProps, boolean ignoreAllUnknown,
			Set<String> includableProps, boolean hasViews) {
		super(builder, beanDesc, properties, backRefs, ignorableProps, ignoreAllUnknown, includableProps, hasViews);
		this.path = path;
	}
	
	/**
     * Main deserialization method for bean-based objects (POJOs).
     */
    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
    {
        // common case first
        if (p.isExpectedStartObjectToken()) {
            if (_vanillaProcessing) {
                return vanillaDeserialize(p, ctxt, p.nextToken());
            }
            // 23-Sep-2015, tatu: This is wrong at some many levels, but for now... it is
            //    what it is, including "expected behavior".
            p.nextToken();
            if (_objectIdReader != null) {
                return deserializeWithObjectId(p, ctxt);
            }
            return deserializeFromObject(p, ctxt);
        }
        return _deserializeOther(p, ctxt, p.currentToken());
    }
	
	
	
	 private final Object vanillaDeserialize(JsonParser p,
	    		DeserializationContext ctxt, JsonToken t)
	        throws IOException
	    {
	        final Object bean = _valueInstantiator.createUsingDefault(ctxt);
	        Class<?> cl = bean.getClass();
	        JsonLocationInformation informationField = null;
	        for(final var field : cl.getDeclaredFields()) {
	        	if(field.getDeclaredAnnotation(JsonInformation.class) != null) {
	        		try {
	        			informationField = new JsonLocationInformation(this.path, p.getTokenLocation());
	        			field.set(bean, informationField);
		        		break;
	        		}catch(Throwable e) {
	        			e.printStackTrace();
	        		}
	        	}
	        }
	        
	        
	        
	        // [databind#631]: Assign current value, to be accessible by custom serializers
	        p.setCurrentValue(bean);
	        if (p.hasTokenId(JsonTokenId.ID_FIELD_NAME)) {
	            String propName = p.currentName();
	            do {
	                p.nextToken();
	                SettableBeanProperty prop = _beanProperties.find(propName);

	                if (prop != null) { // normal case
	                    try {
	                    	final var loc = p.getTokenLocation();
	                        prop.deserializeAndSet(p, ctxt, bean);
	                        if(informationField != null) {
	                        	informationField.addPropertyLocation(propName, loc);
	                        }
	                    } catch (Exception e) {
	                        wrapAndThrow(e, bean, propName, ctxt);
	                    }
	                    continue;
	                }
	                handleUnknownVanilla(p, ctxt, bean, propName);
	            } while ((propName = p.nextFieldName()) != null);
	        }
	        return bean;
	    }
}
