import java.util.Iterator;

public interface SecureDataContainer<E extends Comparable<E>> {
	//OVERVIEW: Rappresenta una collezione finita e modificabile di oggetti di tipo generico E, dove ogni dato è
	//associato ad un utente proprietario che accede alla collezione con una password, ed un insieme di utenti a cui è condviso quel dato.
	//TIPYCAL ELEMENT: Un elemento tipico è <(utente(0),data(0)),...,(utente(n),data(n))>  dove
	//utente(i) = (nome_utente,password) e data(i) = <(data1,L),...,(dataN,L)> dove L = <nome_utente1,...,nome_utenteN>
	//che rappresenta gli utenti a cui è stato condiviso il relativo dato
	
	//crea l'identità di un nuovo utente della collezione
	public void createUser(String id, String passw) throws UserAlreadyExistException;
	//REQUIRES: id != NULL && passw != NULL && id non appartiene a <utente(0).nome_utente,...,utente(n).nome_utente>
	//MODIFIES: this 
	//THROWS: Solleva NullPointerException (unchecked) se id == NULL oppure se 
			  //passw == NULL. Solleva UserAlreadyExistException (checked) se id appartiene
			  //a <utente(0).nome_utente,...,utente(n).nome_utente>
	//EFFECTS: aggiunge l'utente alla collezione, associandogli la relativa password
	
	//restituisce il numero degli elementi di un utente
	//presente nella collezione
	public int getSize(String owner, String passw) throws FailedLoginException;
	//REQUIRES: owner != NULL && passw != NULL && (owner,passw) appartiene a <utente(0),...,utente(n)>
	//THROWS: Solleva NullPointerException se owner == NULL oppure se passw == NULL.
			  //Solleva FailedLoginException se (owner,passw) non appartiene a <utente(0),...,utente(n)>
	//EFFECTS: restituisce il numero dei dati che sono associati all'utente presente 
			   //nella collezione
	
	//inserisce il valore del dato nella collezione
	//se vengono rispettati i controlli di identità
	public boolean put(String owner, String passw, E data) throws FailedLoginException;
	//REQUIRES: owner != NULL && passw != NULL && data != NULL && (owner,passw) appartiene
			    //a <utente(0),...,utente(n)>
	//MODIFIES: this
	//THROWS: Solleva NullPointerException se owner == NULL o se passw == NULL oppure se
			  //data == NULL. Solleva FailedLoginException se (owner,passw) non appartiene
			  //a <utente(0),...,utente(n)>
	//EFFECTS: Aggiunge il dato nella collezione dell'utente se questo non appartiene
	//RETURN: Ritorna true se il valore del dato viene inserito, false altrimenti
	
	//ottiene una copia del valore del dato nella collezione
	//se vengono rispettati i controlli di identità
	public E get(String owner, String passw, E data) throws FailedLoginException;
	//REQUIRES: owner != NULl && passw != NULL && data != NULL && (owner,passw) appartiene a <utente(0),..,utente(n)> &&
			    //data appartiene alla lista dei dati relativi a owner oppure owner compare in un determinato insieme L(i)
	//THROWS: Solleva NullPointerException se owner == NULL o se passw == NULL oppure se data == NULL.
			  //Solleva FailedLogineException (checked) se la coppia (owner,passw) non appartiene a
			  //<utente(0),...,utente(n)>. Solleva IllegalArgumentException (unchecked) se data non appartiene
			  //a data1,...,dataN di owner oppure se owner non compare in un determinato insieme L(i)
	//EFFECTS: Restituisce una copia del valore del dato nella collezione al temrine dei controlli di 
			   //identità
	
	//rimuove il dato nella collezione se vengono
	//rispettati i controlli di identità
	public E remove(String owner, String passw, E data) throws FailedLoginException;
	//REQUIRES: owner != NULL && passw != NULL && data != NULL && (owner,passw) appartiene a <utente(0),..,utente(n)> &&
    			//data appartiene alla lista dei dati relativi a owner
	//MODIFIES: this
	//THROWS: Solleva NullPointerException se owner == NULL o se passw == NULL oppure se data == NULL.
			  //Solleva FailedLogineException (checked) se la coppia (owner,passw) non appartiene a
	  		  //<utente(0),...,utente(n)>. Solleva IllegalArgumentException (unchecked) se data non appartiene
	  		  //a data1,...,dataN di owner
	//EFFECTS: Rimuove il dato contenuto nella collezione se appartiene.
	//RETURN: Restituisce il dato nella collezione
	
	//crea una copia del dato nella collezione se
	//vengono rispettati i controlli di identità
	public void copy(String owner, String passw, E data) throws FailedLoginException,PermissionDeniedException, DataAlreadyInException;
	//REQUIRES: owner != NULL && passw != NULL && data != NULL && (owner,passw) appartiene a <utente(0),..,utente(n)> &&
				//data non appartiene alla lista dei dati relativi a owner
	//MODIFIES: this
	//THROWS: Solleva NullPointerException se owner == NULL o se passw == NULL oppure se data == NULL.
	  		  //Solleva FailedLogineException (checked) se la coppia (owner,passw) non appartiene a
	  		  //<utente(0),...,utente(n)>. Solleva DataAlreadyInException (checked) se data non appartiene
	  		  //a data1,...,dataN di owner
	//EFFECTS: Copia il dato nella collezione al temrine dei controlli di identità
	
	//condivide il dato nella collezione con un altro
	//utente se vengono rispettati i controlli di identità
	public void share(String owner, String passw, String Other, E data) throws FailedLoginException,PermissionDeniedException,UserUnknownException;
	//REQUIRES: owner != NULL && passw != NULL && data != NULL && (owner,passw) appartiene a <utente(0),..,utente(n)> &&
				//data appartiene alla lista dei dati relativi a owner && Other appartiene a <utente(0).nome_utente,...,utente(n).nome_utente>
				//&& Other != Owner
	//MODIFIES: this
	//THROWS: Solleva NullPointerException se owner == NULL o se passw == NULL oppure se data == NULL.
	  		  //Solleva FailedLogineException (checked) se la coppia (owner,passw) non appartiene a
	  		  //<utente(0),...,utente(n)>. Solleva PermissionDeniedEcpetion (checked) se data non appartiene
			  //a data1,...,dataN di owner oppure se owner == other. Solleva UserUnknownException se Other non esiste nella collezione
	//EFFECTS: Aggiunge l'utente Other alla lista di utenti a cui è associato il dato data
	
	//restituisce un iteratore (senza remove) che genera
	//tutti i dati dell'utente in ordine alfabetico
	public Iterator<E> getIterator(String owner, String passw) throws FailedLoginException;
	//REQUIRES: owner != NULL && passw != NULL && (owner,passw) appartiene a <utente(0),...,utente(n)>
	//THROWS: Solleva NullPointerException se owner == NULL o passw == NULL. Solleva FailedLoginException se
			  //(owner,passw) non appartiene a <utente(0),...,utente(n)>
	//EFFECTS: restituisce un iteratore per la collezione di dati dell'utente
}
