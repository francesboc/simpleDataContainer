import java.util.Iterator;
import java.util.Vector;

public class Iteratore<E extends Comparable<E>> implements Iterator<E> {
	//OVERVIEW: Tipo di dato che rappresenta un iteratore per elementi generici di tipo E
	
	//ABSTRACT FUNCTION - a(c) = < index, L > dove L = { k | for each k in dt}
	//REP INVARIANT - I(c) = c.index >= 0 && c.index < c.dt.size() &&
							//c.dt != NULL && for i=0,...,c.dt.size()-1 c.dt.get(i) != NULL
	
	private Vector<E> dt;
	private int index;
	
	public Iteratore(Vector<DataIstance<E>> v1) {
		//REQUIRES: v1 != NULL
		//THROW: Solleva NullPointerException se v1 == NULL
		//EFFECTS: Inizializza l'iteratore
		if(v1 == null) throw new NullPointerException();
		dt = new Vector<E>();
		for(int i=0;i<v1.size();i++) {
			dt.add(v1.get(i).getData());
		}
		index=0;
	}
	
	public boolean hasNext() {
		//EFFECTS: Verifica la presenza o meno di elementi rimanenti
		//RETURN: True se ci sono elementi rimanenti, false altrimenti
		return index < dt.size();
	}
	
	public E next() {
		//EFFECTS: Restituisce il prossimo elemento
		return dt.get(index++);
	}
	
	public void remove() {
		//THROW: Solleva UnsupportedOperationException se si tenta di rimuovere
		//l'elemento 
		throw new UnsupportedOperationException("remove");
	}

}
