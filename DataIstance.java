import java.util.Vector;

public class DataIstance<E extends Comparable<E>> {
	//OVERVIEW: Tipo di dato modificabile che rappresenta un dato generico di tipo E a cui è associata una lista di stringhe
	//che identificano i nomi degli utenti che hanno accesso a quel dato.
	//TYPICAL ELEMENT: (e1,L), dove e1 è un dato generico di tipo E ed L = { utente(i) | for each i = 1,...,n}
	
	//ABSTRACT FUNCTION - a(c) = (c.data,M) dove M = { c.sharedUser.get(i) | for each i=0,...,c.sharedUser.size()-1}
	//REP INVARIANT - I(c) = c.data != NULL && c.sharedUser != NULL &&
							//for each i=0,...,c.sharedUser.size()-1 => c.sharedUser.get(i) != NULL &&
							//for each 0 <= i < j < c.sharedUser.size()-1 => c.sharedUser.get(i) != c.sharedUser.get(j)
	
	private E data;
	private Vector<String> sharedUsers;//lista a cui è associato il dato
	
	public DataIstance() {
		data = null;
		sharedUsers = new Vector<String>();
	}
	
	public DataIstance(E data) {
		//EFFECT: Inizializza this.data con il dato "data" e il vettore di stringhe
		this.data = data;
		sharedUsers = new Vector<String>();
	}
	
	public int getSize() {
		//EFFECTS: Restituisce il numero di utenti associati al dato
		return sharedUsers.size();
	}
	public E getData() {
		//EFFECTS: Restituisce il dato di this
		return data;
	}
	
	public String getUserByIndex(int index) {
		//REQUIRES: 0 <= index < sharedUser.size()-1
		//THROWS: Solleva IllegalArgumentException se index < 0 || index >= sharedUser.size()
		//EFFECTS: Restituisce la stringa che identifica l'utente associato al dato this in posizione index
		if(index < 0 || index >= sharedUsers.size()) throw new IllegalArgumentException();
		return sharedUsers.get(index);
	}
	
	public boolean isUserIn(String s) {
		//REQUIRES: s != null
		//THROW: Solleva NullPointerException se s == NULL
		//EFFECTS: Controlla se la stringa che identifica un utente è presente nel vettore di stringhe che 
				   //identificano gli utenti associati al dato.
		//RETURN: Restituisce true se la stringa è presente, false altrimenti
		if(s == null) throw new NullPointerException();
		for(int i=0;i<sharedUsers.size();i++) {
			if(sharedUsers.get(i).equals(s)) return true;
		}
		return false;
	}
	
	public String toString() {
		//EFFECTS: Genera la stringa che corrisponde al dato di this
		//RETURN: La stringa generata
		return data+"";
	}
	
	public void addSharedData(String s) {
		//REQUIRES: s != NULL
		//THROW: Solleva NullPointerException se s == NULL
		//MODIFIES: this
		//EFFECTS: Aggiunge la stringa s al vettore sharedUsers
		if(s == null) throw new NullPointerException();
		if(!sharedUsers.contains(s)) sharedUsers.add(s);
	}
	
	public void printSharedUser() {
		//EFFECTS: Stampa la lista delle stringhe presenti in sharedUsers
		for(int i=0;i<sharedUsers.size();i++) {
			System.out.printf(" %s ",sharedUsers.get(i));
		}
	}
}
