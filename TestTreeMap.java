import java.util.Iterator;

public class TestTreeMap {
	
	public void test1(TreeMapSecureDataContainer<Integer> treeData) {
		String u1 = "Francesco",u2="Jessica",u3="Alice",u4="Bob";
		String p1 = "12345qwert",p2="987654321",p3="123456789",p4="alice&bob";
		Integer data1 = new Integer(1);
		Integer data2 = new Integer(2);
		Integer data3 = new Integer(3);
		Integer data4 = new Integer(4);
		
		System.out.println("1) Registro 4 utenti...");
		try {
			treeData.createUser(u1, p1);
			treeData.createUser(u2, p2);
			treeData.createUser(u3, p3);
			treeData.createUser(u4, p4);
		} catch (UserAlreadyExistException e) {
			e.printStackTrace();
		}
		System.out.println("Utenti registrati con successo!");
		System.out.printf("\n");
		System.out.println("2) Inserisco dati agli utenti...");
		try {
			treeData.put(u1, p1, data1);
			treeData.put(u1, p1, data2);
			treeData.put(u2, p2, data3);
			treeData.put(u4, p4, data4);
		} catch (FailedLoginException e) {
			e.printStackTrace();
		}
		try {
			System.out.println("Dati inseriti con successo. Ecco il numero di dati associati a Francesco: "+treeData.getSize(u1, p1));
		} catch (FailedLoginException e1) {
			e1.printStackTrace();
		}
		System.out.printf("\n");
		System.out.println("Ecco comme appare la collezione:");
		treeData.printTree();
		System.out.printf("\n");
		System.out.println("3) Condivido dati tra gli utenti...");
		try {
			treeData.share(u1, p1, u3, data1);
			treeData.share(u1, p1, u2, data1);
			treeData.share(u4, p4, u1, data4);
			treeData.share(u2, p2, u4, data3);
		} catch (FailedLoginException e) {
			e.printStackTrace();
		} catch (PermissionDeniedException e) {
			e.printStackTrace();
		} catch (UserUnknownException e) {
			e.printStackTrace();
		}
		System.out.println("Dati condivisi con successo! Ecco come appare ora la collezione:");
		System.out.printf("\n");
		treeData.printTree();
		System.out.printf("\n");
		System.out.println("4) Acquisizione,copia e rimozione dati...");
		try {
			System.out.println("Ecco il dato di Francesco richiesto: "+treeData.get(u1, p1, data1));
			System.out.println("Ecco il dato di Alice richiesto: "+treeData.get(u3, p3, data1));
			
			System.out.println("Sto rimuovendo il dato "+data1+" da Francesco");
			treeData.remove(u1, p1, data1);
			System.out.println("Re-inserisco il dato nella collezione di Francesco");
			treeData.put(u1, p1, data1);
			System.out.println("Sto copiando il dato "+data3+" nell' insieme di dati di Bob");
			try {
				treeData.copy(u4, p4, data3);
			} catch (PermissionDeniedException | DataAlreadyInException e) {
				e.printStackTrace();
			}
		} catch (FailedLoginException e) {
			e.printStackTrace();
		}
		System.out.printf("\n");
		System.out.println("Ecco come appare ora la collezione");
		treeData.printTree();
		System.out.printf("\n");
		System.out.println("5) Iteratore sui dati di Bob...");
		Iterator<Integer> it=null;
		try {
			it = treeData.getIterator(u4, p4);
		} catch (FailedLoginException e) {
			e.printStackTrace();
		}
		if(it != null) {
			while(it.hasNext()) {
				System.out.print("Il prossimo elemento di "+u4+" è --> ");
				System.out.println(it.next());
			}
		}
		else {
			System.out.println("iteratore nullo");
		}
		System.out.println("Test1 terminato!!");
	}
	
	public void test2(TreeMapSecureDataContainer<Integer> treeData) {
		String u1 = "Francesco",u2="Jessica",u3="Alice",u4="Bob";
		String p1 = "12345qwert",p2="987654321",p3="123456789",p4="alice&bob";
		Integer data1 = new Integer(1);
		Integer data2 = new Integer(2);
		Integer data3 = new Integer(3);
		Integer data4 = new Integer(4);
		
		System.out.println("1) Registro 4 utenti...");
		try {
			treeData.createUser(u1, p1);
			treeData.createUser(u2, p2);
			treeData.createUser(u3, p3);
			treeData.createUser(u4, p4);
		} catch (UserAlreadyExistException e) {
			e.printStackTrace();
		}
		System.out.println("Utenti registrati con successo!");
		System.out.printf("\n");
		System.out.println("Ecco come appare la collezione");
		treeData.printTree();
		System.out.printf("\n");
		System.out.println("2) Provo a ri-registrare alcuni utenti...");
		try {
			treeData.createUser(u1, p1);
		} catch (UserAlreadyExistException e) {
			System.out.println("Fail to create user: "+u1+" already registered!");
		}
		System.out.printf("\n");
		System.out.println("Provo a registrare lo stesso utente, ma con password diversa");
		try {
			treeData.createUser(u1, p2);
		} catch (UserAlreadyExistException e) {
			System.out.println("Fail to create user: "+u1+" already registered!");
		}
		System.out.printf("\n");
		System.out.println("3) Inserisco dati agli utenti...");
		try {
			treeData.put(u1, p1, data1);
			treeData.put(u1, p1, data2);
			treeData.put(u2, p2, data3);
			treeData.put(u4, p4, data4);
		} catch (FailedLoginException e) {
			e.printStackTrace();
		}
		try {
			System.out.println("Dati inseriti con successo. Ecco il numero di dati associati a Francesco: "+treeData.getSize(u1, p1));
		} catch (FailedLoginException e1) {
			e1.printStackTrace();
		}
		System.out.printf("\n");
		System.out.println("Provo a richiedere il numero di dati di un utente non esistente");
		try {
			treeData.getSize("Giovanna", p1);
		} catch (FailedLoginException e) {
			System.out.println("Fail to get size: "+"Giovanna"+". Not registered");
		}
		System.out.printf("\n");
		System.out.println("Provo a richiedere il numero di dati di Francesco, ma utilizzando una password diversa");
		try {
			System.out.println("ciaooooooO" +treeData.getSize(u1, p2));
		} catch (FailedLoginException e) {
			System.out.println("Fail to login "+u1);
		}
		System.out.printf("\n");
		System.out.println("Provo a inserire un dato ad un utente non esistente");
		try {
			treeData.put("Giovanna", p1, data1);
		} catch (FailedLoginException e) {
			System.out.println("Fail to put data to: Giovanna. Not registered");
		}
		System.out.printf("\n");
		System.out.println("Provo ad inserire un dato già presente nell' insieme di dati di Francesco");
		try {
			if(!treeData.put(u1, p1, data1)) System.out.println("Fail to put data to: "+u1+". Data already in");
		} catch (FailedLoginException e) {
			e.printStackTrace();
		}
		System.out.printf("\n");
		System.out.println("4) Condivido dati tra gli utenti...");
		try {
			treeData.share(u1, p1, u3, data1);
			treeData.share(u1, p1, u2, data1);
			treeData.share(u4, p4, u1, data4);
			treeData.share(u2, p2, u4, data3);
		} catch (FailedLoginException | PermissionDeniedException | UserUnknownException e) {
			e.printStackTrace();
		}
		System.out.println("Dati condivisi con successo! Ecco come appare ora la collezione:");
		System.out.printf("\n");
		treeData.printTree();
		System.out.printf("\n");
		System.out.println("Provo a condividere un dato non presente nella collezione di Francesco ad Alice");
		try {
			treeData.share(u1, p1, u3, data4);
		} catch (FailedLoginException | PermissionDeniedException | UserUnknownException e) {
			System.out.println("Fail to share data from "+u1+" to "+u3+". Data not found");
		}
		System.out.printf("\n");
		System.out.println("Provo a condividere un dato presente nella collezione di Francesco ad un utente non esistente");
		try {
			treeData.share(u1, p1, "Giovanna", data1);
		} catch (FailedLoginException | PermissionDeniedException | UserUnknownException e) {
			System.out.println("Fail to share data from "+u1+" to Giovanna. User not registered");
		}
		System.out.printf("\n");
		System.out.println("Provo a condividermi il dato da solo");
		try {
			treeData.share(u1, p1, u1, data1);
		} catch (FailedLoginException | PermissionDeniedException | UserUnknownException e) {
			System.out.println("Fail to share data from "+u1+" to "+u1+". No such permission");
		}
		System.out.printf("\n");
		System.out.println("5) Acquisizione,copia e rimozione dati...");
		System.out.println("Provo ad acquisire un dato che non possiedo");
		try {
			treeData.get(u1, p1, data3);
		} catch (FailedLoginException | IllegalArgumentException e) {
			System.out.println("Fail to get data. No such permission");
		}
		System.out.printf("\n");
		System.out.println("Provo ad acquisire un dato non presente");
		try {
			treeData.get(u1, p1, new Integer(7));
		} catch (FailedLoginException | IllegalArgumentException e) {
			System.out.println("Fail to get data. Data not found");
		}
		System.out.printf("\n");
		System.out.println("Provo a rimuovere un dato che non possiedo");
		try {
			treeData.remove(u1, p1, new Integer(7));
		} catch (FailedLoginException | IllegalArgumentException e) {
			System.out.println("Fail to remove data. Data not found");
		}
		System.out.printf("\n");
		System.out.println("Provo a rimuovere un dato che mi è stato condiviso");
		try {
			treeData.remove(u1, p1, data4);
		} catch (FailedLoginException | IllegalArgumentException e) {
			System.out.println("Fail to remove data. No such permission");
		}
		System.out.printf("\n");
		System.out.println("Provo a copiare un dato che ho già");
		try {
			treeData.copy(u1, p1, data1);
		} catch (FailedLoginException | PermissionDeniedException | DataAlreadyInException e) {
			System.out.println("Fail to copy data. Data already in");
		}
		System.out.printf("\n");
		System.out.println("Provo a copiare un dato che non mi è stato condiviso");
		try {
			treeData.copy(u1, p1, data3);
		} catch (FailedLoginException | PermissionDeniedException | DataAlreadyInException e) {
			System.out.println("Fail to copy data. No such permission");
		}
		
		System.out.printf("\n");
		System.out.println("Ecco come appare ora la collezione");
		treeData.printTree();
		System.out.printf("\n");
		System.out.println("6) Iteratore sui dati di Francesco...");
		Iterator<Integer> it=null;
		try {
			it = treeData.getIterator(u1, p1);
		} catch (FailedLoginException e) {
			e.printStackTrace();
		}
		if(it != null) {
			while(it.hasNext()) {
				System.out.print("Il prossimo elemento di "+u1+" è --> ");
				System.out.println(it.next());
			}
		}
		else {
			System.out.println("iteratore nullo");
		}
		System.out.printf("\n");
		System.out.println("Provo ad acquisire l'iteratore di un utente che non esiste");
		try {
			it = treeData.getIterator("Giovanna", p1);
		} catch (FailedLoginException e) {
			System.out.println("Fail to get iterator. Giovanna not registered");
		}
		System.out.printf("\n");
		System.out.println("Test2 terminato!!");
	}
}
