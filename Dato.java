import java.util.Vector;

public class Dato<E extends Comparable<E>> {
	//OVERVIEW: Tipo di dato modificabile che rappresenta una collezione di dati di tipo DataIstance<E>. La collezione corrisponde
	//ai dati associati ad un certo utente
	//TYPICAL ELEMENT: (L,numero_dati) dove L = { dataistance(i) | for each i=1,...,n} e numero_dati è il numero totali di dati
	//presenti nella collezione
	
	//ABSTRACT FUNCTION - a(c) = (L,c.nData) dove L = { c.dates.get(i) | for each i=0,...,c.dates.size()-1}
	//REP INVARIANT - I(c) = c.dates != NULL && c.nData >= 0 && c.dates.size() == nData &&
								//for each i=0,...,c.dates.size()-1 => c.dates.get(i) != NULL &&
								//for each 0 <= i < j <= c.dates.size()-1 => c.dates.get(i) != c.dates.get(j)
								
	private Vector<DataIstance<E>> dates;
	private int nData;
	
	public Dato() {
		//EFFECTS: Inizializza this.dates e nData
		dates = new Vector<DataIstance<E>>();
		nData=0;
	}
	
	public void putData(E data) {
		//MODIFIES: this
		//EFFECTS: Inserisce il dato "data" alla collezione ed incrementa il numero di nData
		dates.add(new DataIstance<E>(data));
		nData++;
	}
	
	public DataIstance<E> get(int index) {
		//REQUIRES: 0 <= index < dates.size()
		//THROWS: Solleva IllegalArgumentException se index < 0 || index >= dates.size()
		//EFFECTS: Restituisce l'elemento in posizione index di dates
		if(index < 0 || index >= dates.size()) throw new IllegalArgumentException();
		return dates.get(index);
	}
	
	public int getDatesSize() {
		//EFFECTS: Restituisce il numero di dati presenti nella collezione
		return nData;
	}
	
	public int containsData(E data) {
		//EFFECTS: Restituisce il numero di dati presenti nella collezione che sono uguali a "data", -1 altrimenti.
		int i=0;
		while(i < nData) {
			if(dates.get(i).getData().compareTo(data)==0) return i;
			i++;
		}
		return -1;
	}
	
	public void printData() {
		//EFFECTS: Stampa la collezione
		for(int i=0;i<nData;i++) {
			System.out.printf("%s (",dates.get(i).toString());
			dates.get(i).printSharedUser();
			System.out.printf(")\n");
		}
	}
	
	public boolean getAccessToCopy(String owner,E data) {
		//EFFECTS: Controlla se il dato "data" è presente nella collezione. Se è presente va a guardare la lista di stringhe
		//associata al relativo DataIstance di "data" e controlla se la stringa "owner" è presente.
		//RETURN: Restituisce true se viene trovato il dato e una stringa corrispondete ad owner nel relativo DataIstance,false
		//altrimenti
		int i=0;
		while(i<nData) {
			if(dates.get(i).getData().compareTo(data)==0) {
				if(dates.get(i).isUserIn(owner)) return true;
			}
			i++;
		}
		return false;
	}
	
	public boolean tryToRemove(E data) {
		//EFFECTS: Controlla se il dato "data" appartiene a this, rimuovendolo di conseguenza.
		//RETURN: Restituisce true se il dato "data" apprtiene a this, false altrimenti
		int i=0;
		while(i<nData) {
			if(dates.get(i).getData().compareTo(data)==0) {
				dates.remove(i);
				nData--;
				return true;
			}
			i++;
		}
		return false;
	}
	
	public Iteratore<E> getIterator(){
		//RETURN: restituisce un iteratore per la collezione dates
		Iteratore<E> it = new Iteratore<E>(dates);
		return it;
	}
}
