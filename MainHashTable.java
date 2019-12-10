import java.util.Scanner;

public class MainHashTable {
	public static <E extends Comparable<E>> void main(String[] args) {
		TestHashTable tester = new TestHashTable();
		Scanner scan = new Scanner(System.in);
		String usage = "Sono forniti di seguito due test. Il primo testa con successo tutte le operazioni,\nmentre il secondo "
				+ "va a testare i messaggi di fallimento che vengono lanciati in caso di operazioni non consentite.\n"
				+ "Digita 1 per eseguire il 1° test, altrimenti digita 2 per eseguire il 2° test\n";
		System.out.println(usage);
		int n;
		do{
			n = scan.nextInt();
			if(n != 1 && n != 2) System.out.println(usage);
		}
		while(n != 1 && n != 2);
		scan.close();
		if(n == 1) tester.test1(new HashTableSecureDataContainer<String>());
		else tester.test2(new HashTableSecureDataContainer<String>());
	}

}
