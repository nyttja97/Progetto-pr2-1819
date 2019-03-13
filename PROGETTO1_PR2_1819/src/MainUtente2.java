import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

public class MainUtente2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Vector<MyUtente2> utenti;
        Iterator<MyUtente2> utenteIterator;
        MyUtente2 utente;
        String Id;
        String password;
        int x;
        String y = new String();
        boolean trovato;
        System.out.println("Quanti utenti vuoi creare?");
        x = Integer.parseInt(input.nextLine());
        utenti= new Vector<MyUtente2>(x);
        for(int i = 0; i<x; i++){
            System.out.println("inserisci il nome utente");
            Id = input.nextLine();
            System.out.println("inserisci la password");
            password = input.nextLine();
            try {
                utente = new MyUtente2(Id, password);
            }
            catch (Exception e) {
                System.out.println(e);
                return;
            }
            utenti.add(utente);
        }
        System.out.println("i seguenti utenti sono stati creati:");
        for (MyUtente2 u:utenti) {
            System.out.println("    "+u.getId());
        }
        while(! y.equals("0")){
            System.out.println("Cosa vuoi fare ora?");
            System.out.println("1: identificati un utente");
            System.out.println("2: stampa la chiave pubblica di un utente");
            System.out.println("0: esci");
            y = input.nextLine();
            switch (y){
                case "1":
                    System.out.println("con quale utente vuoi identificarti?");
                    Id = input.nextLine();
                    if(Id == null)
                        System.out.println("inserisci un valore valido");
                    trovato = false;
                    utenteIterator = utenti.iterator();
                    while (utenteIterator.hasNext() && !trovato){
                        utente = utenteIterator.next();
                        if(utente.getId().equals(Id)){
                            trovato = true;
                            System.out.println("inserisci la password per l'utente "+ Id);
                            password = input.nextLine();
                            if(utente.identify(password))
                                System.out.println("password corretta");
                            else
                                System.out.println("password errata");
                        }
                    }
                    if(!trovato)
                        System.out.println("l'utente " +Id+ " non esiste!");
                    break;

                case "2":
                    System.out.println("con quale utente vuoi identificarti?");
                    Id = input.nextLine();
                    if(Id == null)
                        System.out.println("inserisci un valore valido");
                    trovato = false;
                    utenteIterator = utenti.iterator();
                    while (utenteIterator.hasNext() && !trovato){
                        utente = utenteIterator.next();
                        if(utente.getId().equals(Id)) {
                            trovato = true;
                            System.out.println(utente.getPublicKey().toString());
                        }
                    }
                    if(!trovato)
                    System.out.println("l'utente " + Id + " non esiste!");

                    break;

                case "0":
                    break;
                default:
                    System.out.println("inserisci un valore valido");
                    break;
            }
        }

    }
}
