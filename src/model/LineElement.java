package model;

/**
* trida LineElement predstavuje usecku mezi dvema body, jejich indexy jsou ulozeny v poly indices
* 
* @author Petr
*
*/

public class LineElement extends Element {
	
	public LineElement() {
		this.indices = new int[2];
	}
	
	public LineElement(int a, int b) {
		this();
		this.indices[0] = a;
		this.indices[1] = b;
	}
	
	public int getFirstIndex() {
		return indices[0];
	}
	
}
