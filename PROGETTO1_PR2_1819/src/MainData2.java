import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Vector;

public class MainData2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Vector<MyData2<String>> myData2StringVector;
        Vector<MyData2<Integer>> myData2IntegerVector;


        Iterator<MyData2<String>> myData2StringIterator;
        Iterator<MyData2<Integer>> myData2IntegerIterator;

        MyData2<String> myData2String;
        MyData2<Integer> myData2Integer;

        String data2String;
        Integer data2Integer;

        String data2newString;
        Integer data2newInteger;

        String Owner;

        int x;
        String y;
        String z;

        boolean trovato;

        do {
            System.out.println("Che tipo di dati vuoi creare?");
            System.out.println("0: esci");
            System.out.println("1: MyData2 di String");
            System.out.println("2: MyData1 di Integer");

            y = input.nextLine();

            switch (y) {
                case "1":
                    myData2StringVector = new Vector<MyData2<String>>(0);
                    do {
                        System.out.println("quale operazione vuoi fare?");
                        System.out.println("0: esci");
                        System.out.println("1: crea un nuovo dato");
                        System.out.println("2: modifica il dato");
                        z = input.nextLine();

                        switch (z) {
                            case "0":
                                break;
                            case "1":
                                System.out.println("Scegli l'utente che vuole creare il dato");
                                Owner = new String(input.nextLine());
                                System.out.println("digita il nuovo dato da creare");
                                data2String = new String(input.nextLine());
                                try {
                                    myData2String = new MyData2<String>(Owner, data2String);
                                    myData2StringVector.addElement(myData2String);
                                    myData2StringIterator = myData2StringVector.iterator();
                                    System.out.println("i seguenti dati sono stati creati, con gli autori proprietari associati:");
                                    while (myData2StringIterator.hasNext()) {
                                        myData2String = myData2StringIterator.next();
                                        System.out.println("    " + myData2String.getData() + " " + myData2String.getOwner());
                                    }
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                break;

                            case "2":
                                System.out.println("A chi appartiene il dato da modificare?");
                                Owner = new String(input.nextLine());
                                System.out.println("Qual'è il dato da modificare?");
                                data2String = new String(input.nextLine());
                                try{
                                    myData2String = new MyData2<String>(Owner, data2String);
                                    for (MyData2<String> md2s:myData2StringVector) {
                                        if(md2s.getData().equals(data2String) && md2s.getOwner().equals(Owner)){
                                            System.out.println("Inserisci il nuovo valore del dato");
                                            data2newString = new String(input.nextLine());
                                            md2s.modify(data2newString);
                                        }
                                    }
                                }
                                catch (Exception e){
                                    System.out.println(e);
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
                    myData2IntegerVector = new Vector<MyData2<Integer>>(0);
                    do {
                        System.out.println("quale operazione vuoi fare?");
                        System.out.println("0: esci");
                        System.out.println("1: crea un nuovo dato");
                        System.out.println("2: modifica un dato");

                        z = input.nextLine();

                        switch (z) {
                            case "0":
                                break;
                            case "1":
                                System.out.println("Scegli l'utente che vuole creare il dato");
                                Owner = new String(input.nextLine());
                                System.out.println("digita il nuovo dato da creare");
                                data2Integer = Integer.parseInt(input.nextLine());
                                try {
                                    myData2Integer = new MyData2<Integer>(Owner, data2Integer);
                                    myData2IntegerVector.addElement(myData2Integer);
                                    myData2IntegerIterator = myData2IntegerVector.iterator();
                                    System.out.println("i seguenti dati sono stati creati, con gli autori proprietari associati:");
                                    while (myData2IntegerIterator.hasNext()) {
                                        myData2Integer = myData2IntegerIterator.next();
                                        System.out.println("    " + myData2Integer.getData().toString() + " " + myData2Integer.getOwner());
                                    }
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                break;

                            case "2":
                                System.out.println("A chi appartiene il dato da modificare?");
                                Owner = new String(input.nextLine());
                                System.out.println("Qual'è il dato da modificare?");
                                data2Integer = Integer.parseInt(input.nextLine());
                                try{
                                    myData2Integer = new MyData2<Integer>(Owner, data2Integer);
                                    for (MyData2<Integer> md2i:myData2IntegerVector) {
                                        if(md2i.getData().equals(data2Integer) && md2i.getOwner().equals(Owner)){
                                            System.out.println("Inserisci il nuovo valore del dato");
                                            data2newInteger = Integer.parseInt(input.nextLine());
                                            md2i.modify(data2newInteger);
                                        }
                                    }
                                }
                                catch (Exception e){
                                    System.out.println(e);
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
