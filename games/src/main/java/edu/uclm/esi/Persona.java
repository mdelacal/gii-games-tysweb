package edu.uclm.esi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import org.bson.BsonDocument;

import edu.uclm.esi.mongolabels.dao.MongoBroker;
import edu.uclm.esi.mongolabels.labels.Bsonable;

public class Persona {
	
	@Insertable
	private String nombre;
	@Insertable
	private int edad;
	@Insertable
	private Persona madre;
	
	public Persona() {		
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}
	
	public void setMadre(Persona madre) {
		this.madre = madre;
	}

	public static void main(String[] args) throws Exception {
		Persona juana=new Persona();
		juana.setNombre("Juana");
		juana.setEdad(60);
		
		Persona ana=new Persona();
		ana.setNombre("Ana");
		ana.setEdad(25);
		ana.setMadre(juana);
		
		BsonDocument bsoAna=Bsoneador.get(ana);
		System.out.println(bsoAna);
		
		BsonDocument bsoJuana=Bsoneador.get(juana);
		System.out.println(bsoJuana);
		//BsonBroker.insert(ana.getClass().getName(), bsoAna);
		
		Persona juana2=(Persona) Desbsoneador.get(Persona.class, bsoJuana);
	}

}
