package edu.uclm.esi;

import java.lang.reflect.Field;

import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonString;
import org.bson.BsonValue;

public class Bsoneador {
	
	public static BsonDocument get(Object object) throws IllegalArgumentException, IllegalAccessException {
		BsonDocument result=new BsonDocument();
		Class clase=object.getClass();
		Field[] campos=clase.getDeclaredFields();
		for(Field campo : campos) {
			campo.setAccessible(true);
			if(campo.isAnnotationPresent(Insertable.class)) {
				Object valor=campo.get(object);
				if(valor!=null)
					result.put(campo.getName(), getBsonValue(valor));
			}
		}
		return result;
	}

	private static BsonValue getBsonValue(Object valor) throws IllegalArgumentException, IllegalAccessException {
		Class tipo=valor.getClass();
		if(tipo==String.class)
			return new BsonString(valor.toString());
		if(tipo==Integer.class)
			return new BsonInt32(Integer.parseInt(valor.toString()));
		return get(valor);
	}
}
