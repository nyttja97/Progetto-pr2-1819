import java.security.acl.Owner;
import java.util.Iterator;
import java.util.Scanner;

public class MainSecureDataContainer1 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        MySecureDataContainer11<String> mySecureDataContainer11;
        MySecureDataContainer12<Integer> mySecureDataContainer12;

        Iterator<String> data11Iterator;
        Iterator<Integer> data12Iterator;
        Iterator<String> Ids;

        String data11;
        Integer data12;

        Integer elementiPresenti;
        String Id;
        String passw;
        String IdOther;
        MyUtente1 utente;

        String x;
        String y;

        do {
            System.out.println("Quale versione vuoi usare?");
            System.out.println("0: esci");
            System.out.println("1: MySecureDataContainer11<String>");
            System.out.println("2: MySecureDataContainer12<String>");

            y = input.nextLine();
            switch (y){

                case "1":
                    mySecureDataContainer11 = new MySecureDataContainer11<String>();
                    do{
                        System.out.println("Quale operazione vuoi fare?");
                        System.out.println("0: torna indietro");
                        System.out.println("1: crea un nuovo utente");
                        System.out.println("2: stampa tutti gli Id degli utenti");
                        System.out.println("3: stampa il numero di dati associati ad un utente");
                        System.out.println("4: stampa il numero di dati creati dall'utente");
                        System.out.println("5: crea un nuovo dato associato ad un utente");
                        System.out.println("6: stampa una copia del dato se l'utente è autorizzato a vederlo ");
                        System.out.println("7: elimina un dato se l'utente è il creatore del dato");
                        System.out.println("8: copia un dato nella collezione se l'utente è autorizzato ad accedervi");
                        System.out.println("9: condividi un dato con un altro utente, se sei il creatore del dato");
                        System.out.println("10: stampa tutti i dati che l'utente può vedere");
                        System.out.println("11: stampa tutti i dati che l'utente ha creato");
                        System.out.println("12: elimina un utente");

                        x = input.nextLine();
                        switch (x){

                            case "1":
                                System.out.println("inserisci l'Id del nuovo utente");
                                Id = input.nextLine();
                                System.out.println("inserisci la password dell'utente");
                                passw = input.nextLine();
                                try {
                                    mySecureDataContainer11.createUser(Id, passw);
                                }
                                catch (Exception e) {
                                    System.out.println(e);
                                    System.out.println("non è stato possibile creare il nuovo utente");
                                }
                                break;

                            case "2":
                                System.out.println("gli utenti ad ora presenti nella collezione sono:");
                                Ids = mySecureDataContainer11.getUsersIds();
                                while (Ids.hasNext())
                                    System.out.println(Ids.next());
                                break;

                            case "3":
                                System.out.println("inserisci l'Id dell'utente con cui vuoi eseguire l'operazione");
                                Id = input.nextLine();
                                System.out.println("inserisci la password dell'utente");
                                passw = input.nextLine();
                                try{
                                    elementiPresenti = mySecureDataContainer11.getSize(Id, passw);
                                    System.out.println("L'utente "+Id+" è associato a "+elementiPresenti.toString()+" elementi");
                                }
                                catch (Exception e){
                                    System.out.println(e);
                                    System.out.println("Non è stato possibile mostrare il numero di dati associati");
                                }
                                break;

                            case "4":
                                System.out.println("inserisci l'Id dell'utente con cui vuoi eseguire l'operazione");
                                Id = input.nextLine();
                                System.out.println("inserisci la password dell'utente");
                                passw = input.nextLine();
                                try{
                                    elementiPresenti = mySecureDataContainer11.getOwnerSize(Id, passw);
                                    System.out.println("L'utente "+Id+" è associato a "+elementiPresenti.toString()+" elementi");
                                }
                                catch (Exception e){
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
                                data11 = input.nextLine();
                                try{
                                    if(mySecureDataContainer11.put(Id, passw, data11))
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
                                data11 = input.nextLine();
                                try{
                                    if(mySecureDataContainer11.get(Id, passw, data11) == null)
                                        System.out.println("non è stato possibile copiare il dato");
                                    else {
                                        System.out.println("il seguente dato è stato copiato:");
                                        System.out.println(mySecureDataContainer11.get(Id, passw, data11));
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
                                System.out.println("inserisci il dato da eliminare (una stringa)");
                                data11 = input.nextLine();
                                try{
                                    System.out.println("Il seguente dato è stato eliminato:");
                                    System.out.println(mySecureDataContainer11.remove(Id, passw, data11));
                                }
                                catch (Exception e){
                                    System.out.println(e);
                                    System.out.println("non è stato possibile eliminare il dato");
                                }
                                break;

                            case "8":
                                System.out.println("inserisci l'Id dell'utente con cui vuoi eseguire l'operazione");
                                Id = input.nextLine();
                                System.out.println("inserisci la password dell'utente");
                                passw = input.nextLine();
                                System.out.println("inserisci il dato da copiare (una stringa)");
                                data11 = input.nextLine();
                                try{
                                    System.out.println("La copia del dato è avvenuta con successo");
                                    mySecureDataContainer11.copy(Id, passw, data11);
                                }
                                catch (Exception e){
                                    System.out.println(e);
                                    System.out.println("non è stato possibile copiare il dato");
                                }
                                break;

                            case "9":
                                System.out.println("inserisci l'Id dell'utente con cui vuoi eseguire l'operazione");
                                Id = input.nextLine();
                                System.out.println("inserisci la password dell'utente");
                                passw = input.nextLine();
                                System.out.println("inserisci l'utente con cui vuoi condividere il dato");
                                IdOther = input.nextLine();
                                System.out.println("inserisci il dato da copiare (una stringa)");
                                data11 = input.nextLine();
                                try{
                                    System.out.println("La condivisione del dato è avvenuta con successo");
                                    mySecureDataContainer11.share(Id, passw, IdOther, data11);
                                }
                                catch (Exception e){
                                    System.out.println(e);
                                    System.out.println("non è stato possibile condividere il dato");
                                }
                                break;

                            case "10":
                                System.out.println("inserisci l'Id dell'utente con cui vuoi eseguire l'operazione");
                                Id = input.nextLine();
                                System.out.println("inserisci la password dell'utente");
                                passw = input.nextLine();
                                try{
                                    System.out.println("I seguenti dati sono visibili:");
                                    data11Iterator = mySecureDataContainer11.getIterator(Id, passw);
                                    while(data11Iterator.hasNext()){
                                        System.out.println("    "+data11Iterator.next());
                                    }
                                }
                                catch (Exception e){
                                    System.out.println(e);
                                    System.out.println("non è stato possibile stampare i dati visibili");
                                }
                                break;

                            case "11":
                                System.out.println("inserisci l'Id dell'utente con cui vuoi eseguire l'operazione");
                                Id = input.nextLine();
                                System.out.println("inserisci la password dell'utente");
                                passw = input.nextLine();
                                try{
                                    System.out.println("I seguenti dati sono stati creati:");
                                    data11Iterator = mySecureDataContainer11.getOwnerIterator(Id, passw);
                                    while(data11Iterator.hasNext()){
                                        System.out.println("    "+data11Iterator.next());
                                    }
                                }
                                catch (Exception e){
                                    System.out.println(e);
                                    System.out.println("non è stato possibile stampare i dati creati");
                                }
                                break;

                            case "12":
                                System.out.println("inserisci l'Id dell'utente da eliminare");
                                Id = input.nextLine();
                                System.out.println("inserici la password dell'utente");
                                passw = input.nextLine();
                                try{
                                    mySecureDataContainer11.removeUser(Id, passw);
                                }
                                catch(Exception e){
                                    System.out.println(e);
                                    System.out.println("Non è stato possibile rimuovere l'utente");
                                }
                                break;

                            case "0":
                                break;
                            default:
                                System.out.println("Valore non supportato");
                                break;
                        }
                    }
                    while(! x.equals("0"));
                    break;

                case "2":
                    mySecureDataContainer12 = new MySecureDataContainer12<Integer>();
                    do{
                        System.out.println("Quale operazione vuoi fare?");
                        System.out.println("0: torna indietro");
                        System.out.println("1: crea un nuovo utente");
                        System.out.println("2: stampa tutti gli Id degli utenti");
                        System.out.println("3: stampa il numero di dati associati ad un utente");
                        System.out.println("4: stampa il numero di dati creati dall'utente");
                        System.out.println("5: crea un nuovo dato associato ad un utente");
                        System.out.println("6: stampa una copia del dato se l'utente è autorizzato a vederlo ");
                        System.out.println("7: elimina un dato se l'utente è il creatore del dato");
                        System.out.println("8: copia un dato nella collezione se l'utente è autorizzato ad accedervi");
                        System.out.println("9: condividi un dato con un altro utente, se sei il creatore del dato");
                        System.out.println("10: stampa tutti i dati che l'utente può vedere");
                        System.out.println("11: stampa tutti i dati che l'utente ha creato");
                        System.out.println("12: elimina un utente");

                        x = input.nextLine();
                        switch (x){

                            case "1":
                                System.out.println("inserisci l'Id del nuovo utente");
                                Id = input.nextLine();
                                System.out.println("inserisci la password dell'utente");
                                passw = input.nextLine();
                                try {
                                    mySecureDataContainer12.createUser(Id, passw);
                                }
                                catch (Exception e) {
                                    System.out.println(e);
                                    System.out.println("non è stato possibile creare il nuovo utente");
                                }
                                break;

                            case "2":
                                System.out.println("gli utenti ad ora presenti nella collezione sono:");
                                Ids = mySecureDataContainer12.getUsersIds();
                                while (Ids.hasNext())
                                    System.out.println(Ids.next());
                                break;

                            case "3":
                                System.out.println("inserisci l'Id dell'utente con cui vuoi eseguire l'operazione");
                                Id = input.nextLine();
                                System.out.println("inserisci la password dell'utente");
                                passw = input.nextLine();
                                try{
                                    elementiPresenti = mySecureDataContainer12.getSize(Id, passw);
                                    System.out.println("L'utente "+Id+" è associato a "+elementiPresenti.toString()+" elementi");
                                }
                                catch (Exception e){
                                    System.out.println(e);
                                    System.out.println("Non è stato possibile mostrare il numero di dati associati");
                                }
                                break;

                            case "4":
                                System.out.println("inserisci l'Id dell'utente con cui vuoi eseguire l'operazione");
                                Id = input.nextLine();
                                System.out.println("inserisci la password dell'utente");
                                passw = input.nextLine();
                                try{
                                    elementiPresenti = mySecureDataContainer12.getOwnerSize(Id, passw);
                                    System.out.println("L'utente "+Id+" è associato a "+elementiPresenti.toString()+" elementi");
                                }
                                catch (Exception e){
                                    System.out.println(e);
                                    System.out.println("Non è stato possibile mostrare il numero di dati creati");

                                }
                                break;

                            case "5":
                                System.out.println("inserisci l'Id dell'utente con cui vuoi eseguire l'operazione");
                                Id = input.nextLine();
                                System.out.println("inserisci la password dell'utente");
                                passw = input.nextLine();
                                System.out.println("inserisci il dato da creare (un intero)");
                                data12 = Integer.parseInt(input.nextLine());
                                try{
                                    if(mySecureDataContainer12.put(Id, passw, data12))
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
                                System.out.println("inserisci il dato da copiare (un intero)");
                                data12 = Integer.parseInt(input.nextLine());
                                try{
                                    if(mySecureDataContainer12.get(Id, passw, data12) == null)
                                        System.out.println("non è stato possibile copiare il dato");
                                    else {
                                        System.out.println("il seguente dato è stato copiato:");
                                        System.out.println(mySecureDataContainer12.get(Id, passw, data12));
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
                                System.out.println("inserisci il dato da eliminare (un intero)");
                                data12 = Integer.parseInt(input.nextLine());
                                try{
                                    System.out.println("Il seguente dato è stato eliminato:");
                                    System.out.println(mySecureDataContainer12.remove(Id, passw, data12));
                                }
                                catch (Exception e){
                                    System.out.println(e);
                                    System.out.println("non è stato possibile eliminare il dato");
                                }
                                break;

                            case "8":
                                System.out.println("inserisci l'Id dell'utente con cui vuoi eseguire l'operazione");
                                Id = input.nextLine();
                                System.out.println("inserisci la password dell'utente");
                                passw = input.nextLine();
                                System.out.println("inserisci il dato da copiare (un intero)");
                                data12 = Integer.parseInt(input.nextLine());
                                try{
                                    System.out.println("La copia del dato è avvenuta con successo");
                                    mySecureDataContainer12.copy(Id, passw, data12);
                                }
                                catch (Exception e){
                                    System.out.println(e);
                                    System.out.println("non è stato possibile copiare il dato");
                                }
                                break;

                            case "9":
                                System.out.println("inserisci l'Id dell'utente con cui vuoi eseguire l'operazione");
                                Id = input.nextLine();
                                System.out.println("inserisci la password dell'utente");
                                passw = input.nextLine();
                                System.out.println("inserisci l'utente con cui vuoi condividere il dato");
                                IdOther = input.nextLine();
                                System.out.println("inserisci il dato da copiare (un intero)");
                                data12 = Integer.parseInt(input.nextLine());
                                try{
                                    System.out.println("La condivisione del dato è avvenuta con successo");
                                    mySecureDataContainer12.share(Id, passw, IdOther, data12);
                                }
                                catch (Exception e){
                                    System.out.println(e);
                                    System.out.println("non è stato possibile condividere il dato");
                                }
                                break;

                            case "10":
                                System.out.println("inserisci l'Id dell'utente con cui vuoi eseguire l'operazione");
                                Id = input.nextLine();
                                System.out.println("inserisci la password dell'utente");
                                passw = input.nextLine();
                                try{
                                    System.out.println("I seguenti dati sono visibili:");
                                    data12Iterator = mySecureDataContainer12.getIterator(Id, passw);
                                    while(data12Iterator.hasNext()){
                                        System.out.println("    "+data12Iterator.next());
                                    }
                                }
                                catch (Exception e){
                                    System.out.println(e);
                                    System.out.println("non è stato possibile stampare i dati visibili");
                                }
                                break;

                            case "11":
                                System.out.println("inserisci l'Id dell'utente con cui vuoi eseguire l'operazione");
                                Id = input.nextLine();
                                System.out.println("inserisci la password dell'utente");
                                passw = input.nextLine();
                                try{
                                    System.out.println("I seguenti dati sono stati creati:");
                                    data12Iterator = mySecureDataContainer12.getOwnerIterator(Id, passw);
                                    while(data12Iterator.hasNext()){
                                        System.out.println("    "+data12Iterator.next());
                                    }
                                }
                                catch (Exception e){
                                    System.out.println(e);
                                    System.out.println("non è stato possibile stampare i dati creati");
                                }
                                break;

                            case "12":
                                System.out.println("inserisci l'Id dell'utente da eliminare");
                                Id = input.nextLine();
                                System.out.println("inserici la password dell'utente");
                                passw = input.nextLine();
                                try{
                                    mySecureDataContainer12.removeUser(Id, passw);
                                }
                                catch(Exception e){
                                    System.out.println(e);
                                    System.out.println("Non è stato possibile rimuovere l'utente");
                                }
                                break;

                            case "0":
                                break;
                            default:
                                System.out.println("Valore non supportato");
                                break;
                        }
                    }
                    while(! x.equals("0"));
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
