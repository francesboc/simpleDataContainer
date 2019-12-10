import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class TreeMapSecureDataContainer<E extends Comparable<E>> implements SecureDataContainer<E> {
	//OVERVIEW: Tipo di dato modificabile che rappresenta un'insieme di utenti a cui sono associati un'insieme di dati.
	
	//ABSTRACT FUNCTION - a(c) = { (c.tree.K,c.tree.get(K)) | for each K: key in tree }
	//REP INVARIANT: c.tree != NULL && 
						//for each K: key in tree => K != NULL
						
						//OGNI UTENTE È DISTINTO NELL' ALBERO
						//for each K,J: key t.c K!=J in tree => K.getOwner() != J.getOwner() 
	
						//OGNI DATO ASSOCIATO ALL'UTENTE È DIVERSO DA NULL
						//for each K: key in tree => c.tree.get(K) != NULL 
						
						//OGNI UTENTE CONDIVISO È REGISTRATO NELL' ALBERO
						//for each K: key in tree => 
							//for each i=0,...,c.tree.get(K).ndata 
								//for each j=0,..,c.tree.get(K).getDataIstance(i).getSize()
									//c.tree.containsKey(c.tree.get(K).getDataInstance(i).getUserByIndex(j)) == true
	TreeMap<Utente,Dato<E>> tree;
	
	public TreeMapSecureDataContainer() {
		tree = new TreeMap<Utente,Dato<E>>();
	}
	
	public void createUser(String id, String passw) throws UserAlreadyExistException {
		if(id == null || passw == null) throw new NullPointerException();
		Utente u2 = null;
		Set<Utente> itera = tree.keySet();
		Iterator<Utente> it = itera.iterator();
		while(it.hasNext()) {
			u2 = it.next();
			if(u2.getOwner().equals(id)) throw new UserAlreadyExistException();
		}
		Utente u1 = new Utente(id,passw);
		tree.put(u1, new Dato<E>());
	}

	public int getSize(String owner, String passw) throws FailedLoginException {
		if(owner == null || passw == null) throw new NullPointerException();
		Utente u1 = new Utente(owner,passw);
		Dato<E> tmp = tree.get(u1);
		if(tmp == null) throw new FailedLoginException();
		return tmp.getDatesSize();
	}

	public boolean put(String owner, String passw, E data) throws FailedLoginException {
		if(owner == null || passw == null || data == null) throw new NullPointerException();
		Utente u1 = new Utente(owner,passw);
		Dato<E> tmp = tree.get(u1);
		if(tmp == null) throw new FailedLoginException();
		if(tmp.containsData(data) >= 0) return false;
		else tmp.putData(data);
		return true;
	}

	public E get(String owner, String passw, E data) throws FailedLoginException {
		if(owner == null || passw == null || data == null) throw new NullPointerException();
		Utente u1 = new Utente(owner,passw);
		Dato<E> tmp = tree.get(u1);
		if(tmp == null) throw new FailedLoginException();
		if(tmp.containsData(data)>=0) return data; //ho trovato il dato nella mia collezione
		else {//vado a cercara tra i dati condivisi
			Utente u2;
			Set<Utente> itera = tree.keySet();
			Iterator<Utente> it = itera.iterator();
			while(it.hasNext()) {
				u2 = it.next();
				if(!u2.equals(u1)) {
					Dato<E> tmp1 = tree.get(u2);
					if(tmp1.getAccessToCopy(owner, data)) return data;
				}
			}
			throw new IllegalArgumentException();
		}
	}

	public E remove(String owner, String passw, E data) throws FailedLoginException {
		if(owner == null || passw == null || data == null) throw new NullPointerException();
		Utente u1 = new Utente(owner,passw);
		Dato<E> tmp = tree.get(u1);
		if(tmp == null) throw new FailedLoginException();
		if(tmp.tryToRemove(data)) return data;
		else throw new IllegalArgumentException();
	}

	public void copy(String owner, String passw, E data) throws FailedLoginException, PermissionDeniedException, DataAlreadyInException {
		if(owner == null || passw == null || data == null) throw new NullPointerException();
		Utente u1 = new Utente(owner,passw);
		Utente u2;
		int trovato=0;
		Set<Utente> itera = tree.keySet();
		Iterator<Utente> it = itera.iterator();
		Dato<E> tmpUser = tree.get(u1);
		if(tmpUser == null) throw new FailedLoginException();
		Dato<E> tmp = null;
		while(it.hasNext() && trovato==0) {
			u2 = it.next();
			if(!u2.equals(u1)){
				tmp = tree.get(u2);
				if(tmp.getAccessToCopy(owner, data)) trovato = 1;
			}
		}
		if(trovato == 0) throw new PermissionDeniedException();
		else if(!put(u1.getOwner(),u1.getPassw(),data)) throw new DataAlreadyInException();
	}

	public void share(String owner, String passw, String Other, E data)
			throws FailedLoginException, PermissionDeniedException, UserUnknownException {
		if(owner == null || passw == null || data == null || Other == null) throw new NullPointerException();
		if(owner.equals(Other)) throw new PermissionDeniedException();
		Utente u1 = new Utente(owner,passw);
		Utente u2 = null;
		int i;
		Dato<E> tmp = tree.get(u1);
		if(tmp == null) throw new FailedLoginException();
		if((i=tmp.containsData(data))==-1) throw new PermissionDeniedException();//non ho il dato nella mia lista
		Set<Utente> itera = tree.keySet();
		Iterator<Utente> it = itera.iterator();
		while(it.hasNext()) {
			u2 = it.next();
			if(u2.getOwner().equals(Other)) {
				tmp.get(i).addSharedData(Other);//condivido il dato con l'utente
				return;
			}
		}
		throw new UserUnknownException();
	}

	public Iterator<E> getIterator(String owner, String passw) throws FailedLoginException {
		if(owner == null || passw == null) throw new NullPointerException();
		Utente u1 = new Utente(owner,passw);
		Dato<E> tmp = tree.get(u1);
		if(tmp == null) throw new FailedLoginException();
		Iterator<E> itera = tmp.getIterator(); 
		return itera;
	}
	
	public void printTree() {
		Set<Utente> itera = tree.keySet();
		Iterator<Utente> it = itera.iterator();
		Utente u1=null;
		while(it.hasNext()) {
			u1 = it.next();
			System.out.println(u1.toString());
			Dato<E> tmp = tree.get(u1);
			tmp.printData();
		}
	}

}
