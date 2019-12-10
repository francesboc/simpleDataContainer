public class Utente implements Comparable<Utente>{
	//OVERVIEW: Tipo di dato che rappresenta un utente registrato alla collezione SecureDataContainer.
	//TYPICAL ELEMENT: (nome_utente,password_utente). Al nome_utente è associata la password_utente che dovrà essere
	//utilizzata per accedere alla collezione.
	
	//ABSTRACT FUNCTION - a(c) = (c.owner,c.passw).
	//REP INVARIANT - I(c) = c.owner != NULL && c.passw != NULL
	
	private String owner;
	private String passw;
	
	public Utente(String id, String password){
		//EFFECTS: Inizializza il nome dell'utente con il nome "id" e la sua password con il parametro "password".
		owner = id;
		passw = password;
	}
	
	public String getOwner() {
		//EFFECTS: Restituisce il nome utente di this
		return owner;
	}
	
	public String getPassw() {
		//EFFECTS: Restituisce la password di this
		return passw;
	}
	
	public String toString() {
		//EFFECTS: Genera la stringa associata alla classe Utente
		//RETURN: La stringa generata
		return "Utente: "+owner+" - "+"Password: "+passw;
	}
	
	public boolean equals(Object obj) {
		//REQUIRES: obj != NULL
		//THROW: Solleva NullPointerException (unchecked) se obj == NULL
		//EFFECTS: Controlla se due Utenti sono uguali
		//RETURN: Restituisce true se i due utenti sono lo stesso utente, false altrimenti
		if(obj == null) throw new NullPointerException();
		if (!(obj instanceof Utente)) {
	        return false;
	    } else {
		Utente that = (Utente)obj;
		return owner.equals(that.getOwner()) && passw.equals(that.getPassw());
	    }
	}
	
	public int hashCode() {
		//EFFECTS: Genera il codice hash per il tipo di dato Utente
		//RETURN: Il codice hash generato
	    int hash = 31*owner.hashCode();
	    return hash;
	}

	public int compareTo(Utente u1) {
		//EFFECTS: Confronta LESSICOGRAFICAMENTE due utenti
		//RETURN: -1 in caso owner > u1.getOwner() || 0 in caso owner == u1.getOnwer() || 1 in caso owner < u1.getOwner()
		int res = owner.compareTo(u1.getOwner());
		if(res == 0) return passw.compareTo(u1.getPassw());
		else return res;
	}
}
