package model;

import java.util.ArrayList;
import java.util.List;

/**
* abstraktni trida solid
* 
* @author Petr
*
*/

public abstract class Solid {

	//list elementu objektu
	private List<Element> elements = new ArrayList<>();
	//list bodu objektu
	private List<Vertex> vertices = new ArrayList<>();
	

	public List<Element> getElements() {
		return elements;
	}
	
	public List<Vertex> getVertices() {
		return vertices;
	}
}
