import java.util.Iterator;
import java.util.Scanner;

public class MainSecureDataContainer2 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        MySecureDataContainer21<String> mySecureDataContainer21;

        String data2;

        Integer elementiPresenti;
        Iterator<String> ids;
        Iterator<byte[]> dati;
        String Id;
        String passw;
        String IdOther;

        byte[] databyte;

        MyUtente2 utente;

        String x;
        String y;

        do{
            System.out.println("Quale versione vuoi usare?");
            System.out.println("0: esci");
            System.out.println("1: MySecureDataContainer21<String>");

            y = input.nextLine();
            switch (y){

                case "1":
                    mySecureDataContainer21 = new MySecureDataContainer21<String>();
                    do{
                        System.out.println("Quale operazione vuoi fare?");
                        System.out.println("0: torna indietro");
                        System.out.println("1: crea un nuovo utente");
                        System.out.println("2: stampa tutti gli utenti presenti");
                        System.out.println("3: elimina un utente");
                        System.out.println("4: stampa il numero di dati associati ad un utente");
                        System.out.println("5: inserisci un nuovo dato");
                        System.out.println("6: stampa una copia del dato se l'utente è autorizzato a vederlo");
                        System.out.println("7: stampa tutti i dati presenti. Se non creati da te, verrà stampato 'non puoi leggere questo dato'");
                        System.out.println("8: elimina un dato, se è associato all'utente");
                        System.out.println("9: copia un dato nella collezione");
                        System.out.println("10: condividi il dato con un altro utente");
                        System.out.println("11: stampa tutti i dati visibili all'utente");

                        x = input.nextLine();
                        switch (x){

                            case "1":
                                System.out.println("inserisci l'Id del nuovo utente");
                                Id = input.nextLine();
                                System.out.println("inserisci la password dell'utente");
                                passw = input.nextLine();
                                try {
                                    mySecureDataContainer21.createUser(Id, passw);
                                }
                                catch (Exception e) {
                                    System.out.println(e);
                                    System.out.println("non è stato possibile creare il nuovo utente");
                                }
                                break;

                            case "2":
                                System.out.println("gli utenti ad ora presenti nella collezione sono:");
                                ids = mySecureDataContainer21.getUsersIds();
                                while (ids.hasNext())
                                    System.out.println(ids.next());
                                break;

                            case "3":
                                System.out.println("inserisci l'Id dell'utente da eliminare");
                                Id = input.nextLine();
                                System.out.println("inserici la password dell'utente");
                                passw = input.nextLine();
                                try{
                                    mySecureDataContainer21.removeUser(Id, passw);
                                }
                                catch(Exception e){
                                    System.out.println(e);
                                }
                                break;

                            case "4":
                                System.out.println("inserisci l'Id dell'utente con cui vuoi eseguire l'operazione");
                                Id = input.nextLine();
                                System.out.println("inserisci la password dell'utente");
                                passw = input.nextLine();
                                try{
                                    elementiPresenti = mySecureDataContainer21.getSize(Id, passw);
                                    System.out.println("L'utente "+Id+" è associato a "+elementiPresenti.toString()+" elementi");
                                }
                                catch (Exception e) {
                                    System.out.println(e);
                                    System.out.println("Non è stato possibile mostrare il numero di dati creati");
                                }
                                break;

                            case "5":
                                System.out.println("inserisci l'Id dell'utente con cui vuoi eseguire l'operazione");
                                Id = input.nextLine();
                                System.out.println("inserisci la password dell'utente");
                                passw = input.nextLine();
                                System.out.println("inserisci il dato da creare (una stringa)");
                                data2 = input.nextLine();
                                try{
                                    if(mySecureDataContainer21.put(Id, passw, data2))
                                        System.out.println("il dato è stato crerato con successo");
                                    else
                                        System.out.println("non è stato possibile creare il dato");

                                }
                                catch (Exception e){
                                    System.out.println(e);
                                    System.out.println("Non è stato possibile creare il dato");
                                }
                                break;

                            case "6":
                                System.out.println("inserisci l'Id dell'utente con cui vuoi eseguire l'operazione");
                                Id = input.nextLine();
                                System.out.println("inserisci la password dell'utente");
                                passw = input.nextLine();
                                System.out.println("inserisci il dato da copiare (una stringa)");
                                data2 = input.nextLine();
                                try{
                                    if(mySecureDataContainer21.get(Id, passw, data2) == null)
                                        System.out.println("non è stato possibile copiare il dato");
                                    else {
                                        System.out.println("il seguente dato è stato copiato:");
                                        System.out.println(mySecureDataContainer21.get(Id, passw, data2));
                                    }
                                }
                                catch (Exception e){
                                    System.out.println(e);
                                    System.out.println("non è stato possibile copiare il dato");
                                }
                                break;

                            case "7":
                                System.out.println("inserisci l'Id dell'utente con cui vuoi eseguire l'operazione");
                                Id = input.nextLine();
                                System.out.println("inserisci la password dell'utente");
                                passw = input.nextLine();
                                try{
                                    if(mySecureDataContainer21.seeData(Id, passw) == null) {
                                        System.out.println("Non sono presenti dati nella collezione");
                                    }
                                     else{
                                         dati = mySecureDataContainer21.seeData(Id, passw);
                                        while (dati.hasNext())
                                            System.out.println(new String(dati.next()));
                                    }
                                }
                                catch (Exception e){
                                    System.out.println(e);
                                    System.out.println("Non è stato possibile eseguire l'operazione");
                                }
                                break;

                            case "8":
                                System.out.println("inserisci l'Id dell'utente con cui vuoi eseguire l'operazione");
                                Id = input.nextLine();
                                System.out.println("inserisci la password dell'utente");
                                passw = input.nextLine();
                                System.out.println("inserisci il dato da eliminare (una stringa)");
                                data2 = input.nextLine();
                                try{
                                    System.out.println("Il seguente dato è stato eliminato:");
                                    System.out.println(mySecureDataContainer21.remove(Id, passw, data2));
                                }
                                catch (Exception e){
                                    System.out.println(e);
                                    System.out.println("non è stato possibile eliminare il dato");
                                }
                                break;

                            case "9":
                                System.out.println("inserisci l'Id dell'utente con cui vuoi eseguire l'operazione");
                                Id = input.nextLine();
                                System.out.println("inserisci la password dell'utente");
                                passw = input.nextLine();
                                System.out.println("inserisci il dato da copiare (una stringa)");
                                data2 = input.nextLine();
                                try{
                                    System.out.println("La copia del dato è avvenuta con successo");
                                    mySecureDataContainer21.copy(Id, passw, data2);
                                }
                                catch (Exception e){
                                    System.out.println(e);
                                    System.out.println("non è stato possibile copiare il dato");
                                }
                                break;

                            case "10":
                                System.out.println("inserisci l'Id dell'utente con cui vuoi eseguire l'operazione");
                                Id = input.nextLine();
                                System.out.println("inserisci la password dell'utente");
                                passw = input.nextLine();
                                System.out.println("inserisci l'utente con cui vuoi condividere il dato");
                                IdOther = input.nextLine();
                                System.out.println("inserisci il dato da copiare (una stringa)");
                                data2 = input.nextLine();
                                try{
                                    System.out.println("La condivisione del dato è avvenuta con successo");
                                    mySecureDataContainer21.share(Id, passw, IdOther, data2);
                                }
                                catch (Exception e){
                                    System.out.println(e);
                                    System.out.println("non è stato possibile condividere il dato");
                                }
                                break;

                            case"11":
                                System.out.println("inserisci l'Id dell'utente con cui vuoi eseguire l'operazione");
                                Id = input.nextLine();
                                System.out.println("inserisci la password dell'utente");
                                passw = input.nextLine();
                                try{
                                    System.out.println("I seguenti dati sono visibili:");
                                    dati = mySecureDataContainer21.getIterator(Id, passw);
                                    while(dati.hasNext()){
                                        System.out.println("    "+new String(dati.next()));
                                    }
                                }
                                catch (Exception e){
                                    System.out.println(e);
                                    System.out.println("non è stato possibile stampare i dati visibili");
                                }
                                break;

                            case "0":
                                break;

                            default:
                                System.out.println("Valore non supportato");
                                break;
                        }
                    }
                    while (! x.equals("0"));
                    break;

                case "0":
                    break;

                default:
                    System.out.println("Valore non supportato");
                    break;
            }
        }
        while(! y.equals("0"));

    }

}
