import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Vector;

public class MainData1 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Vector<MyData11<String>> myData11StringVector;
        Vector<MyData11<Integer>> myData11IntegerVector;

        TreeSet<MyData12<String>> myData12StringTreeSet;
        TreeSet<MyData12<Integer>> myData12IntegerTreeSet;

        Iterator<MyData11<String>> myData11StringIterator;
        Iterator<MyData11<Integer>> myData11IntegerIterator;
        Iterator<MyData12<String>> myData12StringIterator;
        Iterator<MyData12<Integer>> myData12IntegerIterator;

        MyData11<String> myData11String;
        MyData11<Integer> myData11Integer;

        MyData12<String> myData12String;
        MyData12<Integer> myData12Integer;

        String data1String;
        Integer data1Integer;

        Iterator<String> Others;
        String Owner;
        String Other;

        int x;
        String y;
        String z;

        boolean trovato;

        do {
            System.out.println("Che tipo di dati vuoi creare?");
            System.out.println("0: esci");
            System.out.println("1: MyData11 di String");
            System.out.println("2: MyData11 di Integer");
            System.out.println("3: MyData12 di String");
            System.out.println("4: MyData12 di Integer");

            y = input.nextLine();

            switch (y) {
                case "1":
                    myData11StringVector = new Vector<MyData11<String>>(0);
                    do {
                        System.out.println("quale operazione vuoi fare?");
                        System.out.println("0: esci");
                        System.out.println("1: crea un nuovo dato");
                        System.out.println("2: condividi un dato con un utente");
                        System.out.println("3: elimina un dato dagli utenti condivisi");

                        z = input.nextLine();

                        switch (z) {
                            case "0":
                                break;
                            case "1":
                                System.out.println("Scegli l'utente che vuole creare il dato");
                                Owner = new String(input.nextLine());
                                System.out.println("digita il nuovo dato da creare");
                                data1String = new String(input.nextLine());
                                try {
                                    myData11String = new MyData11<String>(Owner, data1String);
                                    myData11StringVector.addElement(myData11String);
                                    myData11StringIterator = myData11StringVector.iterator();
                                    System.out.println("i seguenti dati sono stati creati, con gli autori proprietari associati:");
                                    while (myData11StringIterator.hasNext()) {
                                        myData11String = myData11StringIterator.next();
                                        System.out.println("    " + myData11String.getData() + " " + myData11String.getOwner());
                                    }
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                break;

                            case "2":
                                trovato = false;
                                System.out.println("quale dato vuoi condividere?");
                                data1String = input.nextLine();
                                myData11StringIterator = myData11StringVector.iterator();
                                while (myData11StringIterator.hasNext() && ! trovato) {
                                    myData11String = myData11StringIterator.next();
                                    if (myData11String.getData().equals(data1String)) {
                                        trovato = true;
                                        System.out.println("con quale utente vuoi condividere il dato?");
                                        Other = input.nextLine();
                                        try {
                                            myData11String.addOther(Other);
                                            System.out.println("il dato è stato creato. questi sono gli utenti che possono leggere il dato "+myData11String.getData()+" :");
                                            Others = myData11String.getOthers();
                                            while(Others.hasNext())
                                                System.out.print(Others.next()+" ");
                                            System.out.println("");
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    }
                                }
                                if(! trovato)
                                    System.out.println("il dato non esiste");
                                break;

                            case"3":
                                trovato = false;
                                System.out.println("da quale dato vuoi eliminare un utente?");
                                data1String = input.nextLine();
                                myData11StringIterator = myData11StringVector.iterator();
                                while (myData11StringIterator.hasNext() && ! trovato) {
                                    myData11String = myData11StringIterator.next();
                                    if (myData11String.getData().equals(data1String)) {
                                        trovato = true;
                                        System.out.println("quale utente vuoi eliminare dal dato?");
                                        Other = input.nextLine();
                                        try {
                                            if (myData11String.deleteOther(Other))
                                                System.out.println("l'utente " + Other + " è stato rimosso da coloro che possono leggere il dato");
                                            else
                                                System.out.println("l'utente " + Other + " non era presente tra coloro che possono leggere il dato");
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    }
                                }
                                break;

                            default:
                                System.out.println("valore non supportato");
                                break;
                        }
                    }
                    while (! z.equals("0"));
                    break;

                case "2":
                    myData11IntegerVector = new Vector<MyData11<Integer>>(0);
                    do {
                        System.out.println("quale operazione vuoi fare?");
                        System.out.println("0: esci");
                        System.out.println("1: crea un nuovo dato");
                        System.out.println("2: condividi un dato con un utente");

                        z = input.nextLine();

                        switch (z) {
                            case "0":
                                break;
                            case "1":
                                System.out.println("Scegli l'utente che vuole creare il dato");
                                Owner = new String(input.nextLine());
                                System.out.println("digita il nuovo dato da creare");
                                data1Integer = Integer.parseInt(input.nextLine());
                                try {
                                    myData11Integer = new MyData11<Integer>(Owner, data1Integer);
                                    myData11IntegerVector.addElement(myData11Integer);
                                    myData11IntegerIterator = myData11IntegerVector.iterator();
                                    System.out.println("i seguenti dati sono stati creati, con gli autori proprietari associati:");
                                    while (myData11IntegerIterator.hasNext()) {
                                        myData11Integer = myData11IntegerIterator.next();
                                        System.out.println("    " + myData11Integer.getData().toString() + " " + myData11Integer.getOwner());
                                    }
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                break;

                            case "2":
                                trovato = false;
                                System.out.println("quale dato vuoi condividere?");
                                data1Integer = Integer.parseInt(input.nextLine());
                                myData11IntegerIterator = myData11IntegerVector.iterator();
                                while (myData11IntegerIterator.hasNext() && ! trovato) {
                                    myData11Integer = myData11IntegerIterator.next();
                                    if (myData11Integer.getData().equals(data1Integer)) {
                                        trovato = true;
                                        System.out.println("con quale utente vuoi condividere il dato?");
                                        Other = input.nextLine();
                                        try {
                                            myData11Integer.addOther(Other);
                                            System.out.println("il dato è stato creato. questi sono gli utenti che possono leggere il dato "+myData11Integer.getData()+" :");
                                            Others = myData11Integer.getOthers();
                                            while(Others.hasNext())
                                                System.out.print(Others.next()+" ");
                                            System.out.println("");
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    }
                                }
                                if(! trovato)
                                    System.out.println("il dato non esiste");
                                break;

                            case"3":
                                trovato = false;
                                System.out.println("da quale dato vuoi eliminare un utente?");
                                data1Integer = Integer.parseInt(input.nextLine());
                                myData11IntegerIterator = myData11IntegerVector.iterator();
                                while (myData11IntegerIterator.hasNext() && ! trovato) {
                                    myData11Integer = myData11IntegerIterator.next();
                                    if (myData11Integer.getData().equals(data1Integer)) {
                                        trovato = true;
                                        System.out.println("quale utente vuoi eliminare dal dato?");
                                        Other = input.nextLine();
                                        try {
                                            if (myData11Integer.deleteOther(Other))
                                                System.out.println("l'utente " + Other + " è stato rimosso da coloro che possono leggere il dato");
                                            else
                                                System.out.println("l'utente " + Other + " non era presente tra coloro che possono leggere il dato");
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    }
                                }
                                break;

                            default:
                                System.out.println("valore non supportato");
                                break;
                        }
                    }
                    while (! z.equals("0"));
                    break;

                case "3":
                    myData12StringTreeSet = new TreeSet<MyData12<String>>();
                    do {
                        System.out.println("quale operazione vuoi fare?");
                        System.out.println("0: esci");
                        System.out.println("1: crea un nuovo dato");
                        System.out.println("2: condividi un dato con un utente");
                        System.out.println("3: elimina un dato dagli utenti condivisi");

                        z = input.nextLine();

                        switch (z) {
                            case "0":
                                break;
                            case "1":
                                System.out.println("Scegli l'utente che vuole creare il dato");
                                Owner = new String(input.nextLine());
                                System.out.println("digita il nuovo dato da creare");
                                data1String = new String(input.nextLine());
                                try {
                                    myData12String = new MyData12<String>(Owner, data1String);
                                    myData12StringTreeSet.add(myData12String);
                                    myData12StringIterator = myData12StringTreeSet.iterator();
                                    System.out.println("i seguenti dati sono stati creati, con gli autori proprietari associati:");
                                    while (myData12StringIterator.hasNext()) {
                                        myData12String = myData12StringIterator.next();
                                        System.out.println("    " + myData12String.getData() + " " + myData12String.getOwner());
                                    }
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                break;

                            case "2":
                                trovato = false;
                                System.out.println("quale dato vuoi condividere?");
                                data1String = input.nextLine();
                                myData12StringIterator = myData12StringTreeSet.iterator();
                                while (myData12StringIterator.hasNext() && ! trovato) {
                                    myData12String = myData12StringIterator.next();
                                    if (myData12String.getData().equals(data1String)) {
                                        trovato = true;
                                        System.out.println("con quale utente vuoi condividere il dato?");
                                        Other = input.nextLine();
                                        try {
                                            myData12String.addOther(Other);
                                            System.out.println("il dato è stato creato. questi sono gli utenti che possono leggere il dato "+myData12String.getData()+" :");
                                            Others = myData12String.getOthers();
                                            while(Others.hasNext())
                                                System.out.print(Others.next()+" ");
                                            System.out.println("");
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    }
                                }
                                if(! trovato)
                                    System.out.println("il dato non esiste");
                                break;

                            case"3":
                                trovato = false;
                                System.out.println("da quale dato vuoi eliminare un utente?");
                                data1String = input.nextLine();
                                myData12StringIterator = myData12StringTreeSet.iterator();
                                while (myData12StringIterator.hasNext() && ! trovato) {
                                    myData12String = myData12StringIterator.next();
                                    if (myData12String.getData().equals(data1String)) {
                                        trovato = true;
                                        System.out.println("quale utente vuoi eliminare dal dato?");
                                        Other = input.nextLine();
                                        try {
                                            if (myData12String.deleteOther(Other))
                                                System.out.println("l'utente " + Other + " è stato rimosso da coloro che possono leggere il dato");
                                            else
                                                System.out.println("l'utente " + Other + " non era presente tra coloro che possono leggere il dato");
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    }
                                }
                                break;

                            default:
                                System.out.println("valore non supportato");
                                break;
                        }
                    }
                    while (! z.equals("0"));
                    break;

                case"4":
                    myData12IntegerTreeSet = new TreeSet<MyData12<Integer>>();
                    do {
                        System.out.println("quale operazione vuoi fare?");
                        System.out.println("0: esci");
                        System.out.println("1: crea un nuovo dato");
                        System.out.println("2: condividi un dato con un utente");

                        z = input.nextLine();

                        switch (z) {
                            case "0":
                                break;
                            case "1":
                                System.out.println("Scegli l'utente che vuole creare il dato");
                                Owner = new String(input.nextLine());
                                System.out.println("digita il nuovo dato da creare");
                                data1Integer = Integer.parseInt(input.nextLine());
                                try {
                                    myData12Integer = new MyData12<Integer>(Owner, data1Integer);
                                    myData12IntegerTreeSet.add(myData12Integer);
                                    myData12IntegerIterator = myData12IntegerTreeSet.iterator();
                                    System.out.println("i seguenti dati sono stati creati, con gli autori proprietari associati:");
                                    while (myData12IntegerIterator.hasNext()) {
                                        myData12Integer = myData12IntegerIterator.next();
                                        System.out.println("    " + myData12Integer.getData().toString() + " " + myData12Integer.getOwner());
                                    }
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                break;

                            case "2":
                                trovato = false;
                                System.out.println("quale dato vuoi condividere?");
                                data1Integer = Integer.parseInt(input.nextLine());
                                myData12IntegerIterator = myData12IntegerTreeSet.iterator();
                                while (myData12IntegerIterator.hasNext() && ! trovato) {
                                    myData12Integer = myData12IntegerIterator.next();
                                    if (myData12Integer.getData().equals(data1Integer)) {
                                        trovato = true;
                                        System.out.println("con quale utente vuoi condividere il dato?");
                                        Other = input.nextLine();
                                        try {
                                            myData12Integer.addOther(Other);
                                            System.out.println("il dato è stato creato. questi sono gli utenti che possono leggere il dato "+myData12Integer.getData()+" :");
                                            Others = myData12Integer.getOthers();
                                            while(Others.hasNext())
                                                System.out.print(Others.next()+" ");
                                            System.out.println("");
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    }
                                }
                                if(! trovato)
                                    System.out.println("il dato non esiste");
                                break;

                            case"3":
                                trovato = false;
                                System.out.println("da quale dato vuoi eliminare un utente?");
                                data1Integer = Integer.parseInt(input.nextLine());
                                myData12IntegerIterator = myData12IntegerTreeSet.iterator();
                                while (myData12IntegerIterator.hasNext() && ! trovato) {
                                    myData12Integer = myData12IntegerIterator.next();
                                    if (myData12Integer.getData().equals(data1Integer)) {
                                        trovato = true;
                                        System.out.println("quale utente vuoi eliminare dal dato?");
                                        Other = input.nextLine();
                                        try {
                                            if (myData12Integer.deleteOther(Other))
                                                System.out.println("l'utente " + Other + " è stato rimosso da coloro che possono leggere il dato");
                                            else
                                                System.out.println("l'utente " + Other + " non era presente tra coloro che possono leggere il dato");
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    }
                                }
                                break;

                            default:
                                System.out.println("valore non supportato");
                                break;
                        }
                    }
                    while (! z.equals("0"));
                    break;

                case "0":
                    break;

                default:
                    System.out.println("valore non supportato");
                    break;
            }
        }
        while (! y.equals("0"));
    }
}
