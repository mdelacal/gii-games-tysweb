package edu.uclm.esi;

import java.lang.reflect.Field;

import org.bson.BsonDocument;
import org.bson.BsonString;

public class Desbsoneador {
	
	public static Object get(Class clase, BsonDocument bso) throws InstantiationException, IllegalAccessException {
		Object result = clase.newInstance();
		Field[] campos=clase.getDeclaredFields();
		for(Field campo : campos) {
			campo.setAccessible(true);
			if(campo.isAnnotationPresent(Insertable.class)) {
				Object valor=bso.get(campo.getName());
				if(valor!=null) {
					if(campo.getType()==String.class)
						campo.set(result, ((BsonString) valor).asString().getValue());
					else if(campo.getType()==Integer.class)
						campo.set(result, ((BsonString) valor).asInt32().getValue());
				}		
			}
		}
		return result;
	}
}
