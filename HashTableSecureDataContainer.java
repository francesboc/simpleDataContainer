import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

public class HashTableSecureDataContainer<E extends Comparable<E>> implements SecureDataContainer<E>  {
	//OVERVIEW: Tipo di dato modificabile che rappresenta un'insieme di utenti a cui sono associati un'insieme di dati.
	
	//ABSTRACT FUNCTION - a(c) = { (c.K,c.hash.get(K)) | for each K: key in hash }
	//REP INVARIANT - I(c) = c.hash != NULL && 
						//for each K: key in hash => K != NULL
						
						//OGNI UTENTE È DISTINTO NELLA TABELLA
						//for each K,J: key t.c K!=J in hash => K.getOwner() != J.getOwner() 
	
						//OGNI DATO ASSOCIATO ALL'UTENTE È DIVERSO DA NULL
						//for each K: key in hash => c.hash.get(K) != NULL 
						
						//OGNI UTENTE CONDIVISO È REGISTRATO ALLA TABELLA
						//for each K: key in hash => 
							//for each i=0,...,c.hash.get(K).ndata 
								//for each j=0,..,c.hash.get(K).getDataIstance(i).getSize()
									//c.hash.contains(c.hash.get(K).getDataInstance(i).getUserByIndex(j)) == true
	Hashtable<Utente,Dato<E>> hash;
	
	public HashTableSecureDataContainer() {
		hash = new Hashtable<Utente,Dato<E>>();
	}
	
	public void createUser(String id, String passw) throws UserAlreadyExistException{
		if(id == null || passw == null) throw new NullPointerException();
		Utente u2 = null;
		Enumeration<Utente> itera = hash.keys();
		while(itera.hasMoreElements()) {
			u2 = itera.nextElement();
			if(u2.getOwner().equals(id)) throw new UserAlreadyExistException();
		}
		Utente u1 = new Utente(id,passw);
		hash.put(u1, new Dato<E>());
	}

	public int getSize(String owner, String passw) throws FailedLoginException {
		if(owner == null || passw == null) throw new NullPointerException();
		Utente u1 = new Utente(owner,passw);
		Dato<E> tmp = hash.get(u1);
		if(tmp == null) throw new FailedLoginException();
		return tmp.getDatesSize();
	}

	public boolean put(String owner, String passw, E data) throws FailedLoginException{
		if(owner == null || passw == null || data == null) throw new NullPointerException();
		Utente u1 = new Utente(owner,passw);
		Dato<E> tmp = hash.get(u1);
		if(tmp == null) throw new FailedLoginException();
		if(tmp.containsData(data) >= 0) return false;
		else tmp.putData(data);
		return true;
	}

	public E get(String owner, String passw, E data) throws FailedLoginException {
		if(owner == null || passw == null || data == null) throw new NullPointerException();
		Utente u1 = new Utente(owner,passw);
		Dato<E> tmp = hash.get(u1);
		if(tmp == null) throw new FailedLoginException();
		if(tmp.containsData(data)>=0) return data; //ho trovato il dato nella mia collezione
		else {//vado a cercara tra i dati condivisi
			Utente u2;
			Enumeration<Utente> itera = hash.keys();
			while(itera.hasMoreElements()) {
				u2 = itera.nextElement();
				if(!u2.equals(u1)) {
					Dato<E> tmp1 = hash.get(u2);
					if(tmp1.getAccessToCopy(owner, data)) return data;
				}
			}
			throw new IllegalArgumentException();
		}
		
	}

	public E remove(String owner, String passw, E data) throws FailedLoginException {
		if(owner == null || passw == null || data == null) throw new NullPointerException();
		Utente u1 = new Utente(owner,passw);
		Dato<E> tmp = hash.get(u1);
		if(tmp == null) throw new FailedLoginException();
		if(tmp.tryToRemove(data)) return data;
		else throw new IllegalArgumentException();
	}

	public void copy(String owner, String passw, E data) throws FailedLoginException,PermissionDeniedException, DataAlreadyInException {
		if(owner == null || passw == null || data == null) throw new NullPointerException();
		Utente u1 = new Utente(owner,passw);
		Utente u2;
		int trovato=0;
		Enumeration<Utente> itera = hash.keys();
		Dato<E> tmpUser = hash.get(u1);
		if(tmpUser == null) throw new FailedLoginException();
		Dato<E> tmp = null;
		while(itera.hasMoreElements() && trovato==0) {
			u2 = itera.nextElement();
			if(!u2.equals(u1)){
				tmp = hash.get(u2);
				if(tmp.getAccessToCopy(owner, data)) trovato = 1;
			}
		}
		if(trovato == 0) throw new PermissionDeniedException();
		else if(!put(u1.getOwner(),u1.getPassw(),data)) throw new DataAlreadyInException();
}

	public void share(String owner, String passw, String Other, E data) 
			throws FailedLoginException,PermissionDeniedException,UserUnknownException{
		if(owner == null || passw == null || data == null || Other == null) throw new NullPointerException();
		if(owner.equals(Other)) throw new PermissionDeniedException();
		Utente u1 = new Utente(owner,passw);
		Utente u2 = null;
		int i;
		Dato<E> tmp = hash.get(u1);
		if(tmp == null) throw new FailedLoginException();
		if((i=tmp.containsData(data))==-1) throw new PermissionDeniedException();//non ho il dato nella mia lista
		Enumeration<Utente> itera = hash.keys();
		while(itera.hasMoreElements()) {
			u2 = itera.nextElement();
			if(u2.getOwner().equals(Other)) {
				tmp.get(i).addSharedData(Other);//condivido il dato con l'utente
				return;
			}
		}
		throw new UserUnknownException();
	}

	public Iterator<E> getIterator(String owner, String passw) throws FailedLoginException{
		if(owner == null || passw == null) throw new NullPointerException();
		Utente u1 = new Utente(owner,passw);
		Dato<E> tmp = hash.get(u1);
		if(tmp == null) throw new FailedLoginException();
		Iterator<E> itera = tmp.getIterator(); 
		return itera;
	}
	
	public void printMap() {
		Enumeration<Utente> itera = hash.keys();
		Utente u1;
		while(itera.hasMoreElements()) {
			u1 = itera.nextElement();
			System.out.println(u1.toString());
			Dato<E> tmp = hash.get(u1);
			tmp.printData();
		}
	}
}
