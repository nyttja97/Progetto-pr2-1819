import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;

public class MySecureDataContainer12<E> implements SecureDataContainer<E> {
    /*
    AF: f (< {utente_0, utente_n-1}, {data_0, data_m-1} >) = <users[0], ..., users[n-1], data[0], ..., data[m-1]>
        con n = users.length() && m = data.length()
    IR: (users != null && data != null) ==> for all i, 0 <= i < data.lenghth(), data[i] preserva IR di MyData1
                                            && for all j, 0 <= j < users.length(), users[j] preserva IR di MyUtente
                                            && for all i, 0 <= i < users.length(), for all j, 0 <= j < users.length(),
                                                ! (users[i].getId().equals(users[j].getId())
                                            && for all i, 0 <= i < data.length(), exists j, 0 <= j < users.length(), t.c.
                                                users[j].getId().equals(data[j].getOwner())
                                            && for all i, 0 <= i < data.length() , for all j, 0<= j < data[i].Others.length(),
                                                exists k, 0 <= k < users.length(), t.c. users[i].getId().equals(data[i].Others[j])
    */

    private TreeSet<MyUtente1> users;
    private TreeSet<MyData12<E>> data;

    //costruttore
    public MySecureDataContainer12(){
        users = new TreeSet<MyUtente1>();
        data = new TreeSet<MyData12<E>>();
        /*
        IR preservata: users.length() == 0 && data.length() == 0, quindi tutte le implicazioni sono vere
        */
    }

    // Crea l’identità un nuovo utente della collezione
    public void createUser(String Id, String passw) throws NullPointerException, AlreradyUserException{
        if(Id == null)
            throw new NullPointerException("createUser in MySecureDataContainer12: inserisci un Id valido");
        if(passw == null)
            throw new NullPointerException("createUser in MySecureDataContainer12: inserisci una password valida");
        Iterator<String> Ids = this.getUsersIds();
        while(Ids.hasNext())
            if(Ids.next().equals(Id))
                throw new AlreradyUserException("createUser in MySecureDataContainer12: L'Id "+Id+" è già stato usato da un altro utente");
        MyUtente1 utente = new MyUtente1(Id, passw);
        users.add(utente);
        /*
        IR preservata: l'utente inserito è di tipo MyUtente1 e l'id è diverso da ogni utente già creato
        */
    }

    //restituisce tutti gli Id degli utenti
    public Iterator<String> getUsersIds() {
        Vector<String> Ids = new Vector<>(0);
        for (MyUtente1 u: this.users) {
            Ids.addElement(u.getId());
        }
        return Ids.iterator();
        /*
        IR preservata: non modifico this
        */
    }

    public void removeUser(String Id, String passw) throws NullPointerException, NoUserException, WrongPasswordException{
        if(Id == null)
            throw new NullPointerException("removeUser in MySecureDataContainer12: inserisci un Id valido");
        if(passw == null)
            throw new NullPointerException("removeUser in MySecureDataContainer12: inserisci una password valida");
        boolean trovato = false;
        Iterator<String> Ids = this.getUsersIds();
        while(Ids.hasNext()){
            if(Ids.next().equals(Id))
                trovato = true;
        }
        if(! trovato)
            throw new NoUserException("removeUser in MySecureDataContainer12: L' Id "+ Id+" non è presente nella collezione");

        //identifico l'utente con lo stesso Id attraverso la password
        boolean identificato = false;
        MyUtente1 user;
        Iterator<MyUtente1> utenteIterator = this.users.iterator();
        while(utenteIterator.hasNext() && !identificato){
            user = utenteIterator.next();
            if(user.getId().equals(Id))
                if(user.identify(passw))
                    identificato = true;
        }
        if(! identificato)
            throw new WrongPasswordException("removeUser in MySecureDataContainer12: password errata");
        for (MyData12<E> data12: data) {
            if(data12.getOwner().equals(Id))
                this.data.remove(data12);
            else try{
                data12.deleteOther(Id);
            }
            catch(Exception e) {
                System.out.println(e);
            }
        }
        user = new MyUtente1(Id, passw);
        this.users.remove(user);
        /*
        IR preservata: l'eliminazione di un utente porta ad uno stato precedente corretto della classe
        */
    }

    // Restituisce il numero degli elementi di un utente presenti nella collezione se sono rispettati i controlli di identità
    public int getSize(String Owner, String passw) throws NullPointerException, NoUserException, WrongPasswordException{
        if(Owner == null)
            throw new NullPointerException("getSize in MySecureDataContainer12: inserisci un Id valido");
        if(passw == null)
            throw new NullPointerException("getSize in MySecureDataContainer12: inserisci una password valida");

        //verifico che esista un utente con lo stesso Id
        boolean trovato = false;
        Iterator<String> Ids = this.getUsersIds();
        while(Ids.hasNext()){
            if(Ids.next().equals(Owner))
                trovato = true;
        }
        if(! trovato)
            throw new NoUserException("getSize in MySecureDataContainer12: L' Id "+ Owner+ " non è presente nella collezione");

        //identifico l'utente con lo stesso Id attraverso la password
        boolean identificato = false;
        MyUtente1 user;
        Iterator<MyUtente1> utenteIterator = this.users.iterator();
        while(utenteIterator.hasNext() && !identificato){
            user = utenteIterator.next();
            if(user.getId().equals(Owner))
                if(user.identify(passw))
                    identificato = true;
        }
        if(! identificato)
            throw new WrongPasswordException("getSize in MySecureDataContainer12: password errata");

        int elem = 0;
        Iterator<MyData12<E>> data12Iterator = data.iterator();
        Iterator<String> OthersIterator;
        while(data12Iterator.hasNext()){
            OthersIterator = data12Iterator.next().getOthers();
            while(OthersIterator.hasNext()){
                if(OthersIterator.next().equals(Owner))
                    elem ++;
            }
        }
        return elem;
        /*
        IR preservata: non modifico this
        */
    }

    // Restituisce il numero degli elementi di cui l'utente è il creatore presenti nella collezione se sono rispettati i controlli di identità
    public int getOwnerSize(String Owner, String passw) throws NullPointerException, NoUserException, WrongPasswordException{
         /*
        REQUIRES: Owner != null && password != null && exists i, 0 <= i < n, t.c. (utente_i.getId().equals(Owner) && utente_i.identify(password))
        THROWS: se Owner == null lancia NullPointerException (presente in java, unchecked)
                se passw == null lancia NullPointerException(presente in java, unchecked)
                se ! exists i, 0 <= i < n, utente_i.getId.equals(Owner) lancia NoUserException (non presente in java, checked)
                se exists i, 0 <= i < n, utente_i.getId.equals(Owner) && !utente_i.identify(passw) lancia WrongPasswordException (non presente in java, checked)
        MODIFIES: -
        EFFECTS(OF MODIFICATION): -
        RETURNS: #{data_i | Owner == data_i.getOwner()}
        */
        if(Owner == null)
            throw new NullPointerException("getOwnerSize in MySecureDataContainer12: inserisci un Id valido");
        if(passw == null)
            throw new NullPointerException("getOwnerSize in MySecureDataContainer12: inserisci una password valida");

        //verifico che esista un utente con lo stesso Id
        boolean trovato = false;
        Iterator<String> Ids = this.getUsersIds();
        while(Ids.hasNext()){
            if(Ids.next().equals(Owner))
                trovato = true;
        }
        if(! trovato)
            throw new NoUserException("getOwnerSize in MySecureDataContainer12: L' Id "+ Owner+ " non è presente nella collezione");

        //identifico l'utente con lo stesso Id attraverso la password
        boolean identificato = false;
        MyUtente1 user;
        Iterator<MyUtente1> utenteIterator = this.users.iterator();
        while(utenteIterator.hasNext() && !identificato){
            user = utenteIterator.next();
            if(user.getId().equals(Owner))
                if(user.identify(passw))
                    identificato = true;
        }
        if(! identificato)
            throw new WrongPasswordException("getOwnerSize in MySecureDataContainer12: password errata");

        int elem = 0;
        Iterator<MyData12<E>> data12Iterator = data.iterator();
        while(data12Iterator.hasNext()){
            if(data12Iterator.next().getOwner().equals(Owner))
                elem ++;
        }
        return elem;
        /*
        IR preservata: non modifico this
        */
    }

    // Inserisce il valore del dato nella collezione se vengono rispettati i controlli di identità
    public boolean put(String Owner, String passw, E data) throws NullPointerException, NoUserException, WrongPasswordException{
        if(Owner == null)
            throw new NullPointerException("put in MySecureDataContainer12: inserisci un Id valido");
        if(passw == null)
            throw new NullPointerException("put in MySecureDataContainer12: inserisci una password valida");
        if (data == null)
            throw new NullPointerException("put in MySecureDataContainer12: inserisci un dato valido");

        //verifico che esista un utente con lo stesso Id
        boolean trovato = false;
        Iterator<String> Ids = this.getUsersIds();
        while(Ids.hasNext()){
            if(Ids.next().equals(Owner))
                trovato = true;
        }
        if(! trovato)
            throw new NoUserException("put in MySecureDataContainer12: L' Id "+ Owner+ " non è presente nella collezione");

        //identifico l'utente con lo stesso Id attraverso la password
        boolean identificato = false;
        MyUtente1 user;
        Iterator<MyUtente1> utenteIterator = this.users.iterator();
        while(utenteIterator.hasNext() && !identificato){
            user = utenteIterator.next();
            if(user.getId().equals(Owner))
                if(user.identify(passw))
                    identificato = true;
        }
        if(! identificato)
            throw new WrongPasswordException("put in MySecureDataContainer12: password errata");

        MyData12<E> data12 = new MyData12<E>(Owner, data);
        this.data.add(data12);
        return true;
        /*
        IR preservata:  data != null ==> exists j, 0 <= j < users.length(), t.c. users[j].getId().equals(data1.getOwner())
        */
    }

    // Ottiene una copia del valore del dato nella collezione (senza aggiungere la copia alla collezione) se vengono rispettati i controlli di identità
    public E get(String Owner, String passw, E data) throws NullPointerException, NoUserException, WrongPasswordException, MissingPermissionException{
        if(Owner == null)
            throw new NullPointerException("get in MySecureDataContainer12: inserisci un Id valido");
        if(passw == null)
            throw new NullPointerException("get in MySecureDataContainer12: inserisci una password valida");
        if (data == null)
            throw new NullPointerException("get in MySecureDataContainer12: inserisci un dato valido");

        //verifico che esista un utente con lo stesso Id
        boolean trovato = false;
        Iterator<String> Ids = this.getUsersIds();
        while(Ids.hasNext()){
            if(Ids.next().equals(Owner))
                trovato = true;
        }
        if(! trovato)
            throw new NoUserException("get in MySecureDataContainer12: L' Id "+ Owner+ " non è presente nella collezione");

        //identifico l'utente con lo stesso Id attraverso la password
        boolean identificato = false;
        MyUtente1 user;
        Iterator<MyUtente1> utenteIterator = this.users.iterator();
        while(utenteIterator.hasNext() && !identificato){
            user = utenteIterator.next();
            if(user.getId().equals(Owner))
                if(user.identify(passw))
                    identificato = true;
        }
        if(! identificato)
            throw new WrongPasswordException("get in MySecureDataContainer12: password errata");

        //verifico che il dato possa essere acceduto dall'utente
        Iterator<MyData12<E>> data12Iterator = this.data.iterator();
        MyData12<E> data12;
        Iterator<String> Others;
        trovato = false;
        while(data12Iterator.hasNext()){
            data12 = data12Iterator.next();
            if(data12.getData().equals(data)) {
                trovato = true;
                Others = data12.getOthers();
                while (Others.hasNext()) {
                    if (Others.next().equals(Owner)) {
                        return data;
                    }
                }
            }
        }
        if(trovato) //il dato è stato trovato ma l'utente non è autorizzato ad accedervi
            throw new MissingPermissionException("get in MySecureDataContainer12: L'utente "+Owner+" non è autorizzato a ottenere la copia del dato");

        return null;
        /*
        IR preservata: non modifico this
        */
    }

    public E remove(String Owner, String passw, E data) throws NullPointerException, NoUserException, WrongPasswordException, NotOwnerException{
        if(Owner == null)
            throw new NullPointerException("remove in MySecureDataContainer12: inserisci un Id valido");
        if(passw == null)
            throw new NullPointerException("remove in MySecureDataContainer12: inserisci una password valida");
        if (data == null)
            throw new NullPointerException("remove in MySecureDataContainer12: inserisci un dato valido");

        //verifico che esista un utente con lo stesso Id
        boolean trovato = false;
        Iterator<String> Ids = this.getUsersIds();
        while(Ids.hasNext()){
            if(Ids.next().equals(Owner))
                trovato = true;
        }
        if(! trovato)
            throw new NoUserException("remove in MySecureDataContainer12: L' Id "+ Owner+ " non è presente nella collezione");

        //identifico l'utente con lo stesso Id attraverso la password
        boolean identificato = false;
        MyUtente1 user;
        Iterator<MyUtente1> utenteIterator = this.users.iterator();
        while(utenteIterator.hasNext() && !identificato){
            user = utenteIterator.next();
            if(user.getId().equals(Owner))
                if(user.identify(passw))
                    identificato = true;
        }
        if(! identificato)
            throw new WrongPasswordException("remove in MySecureDataContainer12: password errata");


        trovato = false;
        MyData12<E> data12;
        Iterator<MyData12<E>> data12Iterator = this.data.iterator();
        while(data12Iterator.hasNext()){
            data12 = data12Iterator.next();
            if(data12.getData().equals(data)){
                trovato = true;
                if(data12.getOwner().equals(Owner)) {
                    this.data.remove(data12);
                    return data12.getData();
                }
            }
        }
        if (trovato) // il dato è stato trovato ma l'utente non è stato autorizzato ad accedervi per rimuoverlo
            throw new NotOwnerException("remove in MySecureDataContainer12: l'utente "+ Owner+" non è autorizzato a rimuovere il dato");

        return null;
        /*
        IR preservata: elimino un dato, non gli utenti, quindi le premesse delle implicazioni per gli altri dati sono sempre valide
        */
    }

    // Crea una copia del dato nella collezione se vengono rispettati i controlli di identità (non deve necessariamente essere il creatore)
    public void copy(String Owner, String passw, E data) throws NullPointerException, NoUserException, WrongPasswordException, MissingPermissionException{
        if(Owner == null)
            throw new NullPointerException("copy in MySecureDataContainer12: inserisci un Id valido");
        if(passw == null)
            throw new NullPointerException("copy in MySecureDataContainer12: inserisci una password valida");
        if (data == null)
            throw new NullPointerException("copy in MySecureDataContainer12: inserisci un dato valido");

        //verifico che esista un utente con lo stesso Id
        boolean trovato = false;
        Iterator<String> Ids = this.getUsersIds();
        while(Ids.hasNext()){
            if(Ids.next().equals(Owner))
                trovato = true;
        }
        if(! trovato)
            throw new NoUserException("copy in MySecureDataContainer12: L' Id "+ Owner+ " non è presente nella collezione");

        //identifico l'utente con lo stesso Id attraverso la password
        boolean identificato = false;
        MyUtente1 user;
        Iterator<MyUtente1> utenteIterator = this.users.iterator();
        while(utenteIterator.hasNext() && !identificato){
            user = utenteIterator.next();
            if(user.getId().equals(Owner))
                if(user.identify(passw))
                    identificato = true;
        }
        if(! identificato)
            throw new WrongPasswordException("copy in MySecureDataContainer12: password errata");

        //verifico che il dato possa essere acceduto dall'utente
        Iterator<MyData12<E>> data12Iterator = this.data.iterator();
        MyData12<E> data12;
        Iterator<String> Others;
        trovato = false;
        while(data12Iterator.hasNext()){
            data12 = data12Iterator.next();
            if(data12.getData().equals(data)) {
                trovato = true;
                Others = data12.getOthers();
                while (Others.hasNext()) {
                    if (Others.next().equals(Owner)) {
                        this.put(Owner, passw, data);
                    }
                }
            }
        }
        if(trovato) //il dato è stato trovato ma l'utente non è autorizzato ad accedervi
            throw new MissingPermissionException("copy in MySecureDataContainer12: L'utente "+Owner+" non è autorizzato a copiare il dato");
        /*
        IR preservata: Owner esiste nella lista utenti, quindi l'IR è preservata creando un nuovo elemento in data
        */
    }

    // Condivide il dato nella collezione con un altro utente se vengono rispettati i controlli di identità
    public void share(String Owner, String passw, String Other, E data) throws NullPointerException, NoUserException, WrongPasswordException, NotOwnerException, AlreradyUserException{
        if(Owner == null)
            throw new NullPointerException("share in MySecureDataContainer12: inserisci un Id valido");
        if(passw == null)
            throw new NullPointerException("share in MySecureDataContainer12: inserisci una password valida");
        if(Other == null)
            throw new NullPointerException("share in MySecureDataContainer12: inserisci un Id con cui condividere il dato valido");
        if (data == null)
            throw new NullPointerException("share in MySecureDataContainer12: inserisci un dato valido");

        //verifico che esista un utente con lo stesso Id
        boolean trovato = false;
        boolean trovatoOther = false;
        String IdNext;
        Iterator<String> Ids = this.getUsersIds();
        while(Ids.hasNext()){
            IdNext = Ids.next();
            if(IdNext.equals(Owner))
                trovato = true;
            else if(IdNext.equals(Other))
                trovatoOther = true;
        }
        if(! trovato)
            throw new NoUserException("share in MySecureDataContainer12: L' Id "+ Owner+ " non è presente nella collezione");
        if(! trovatoOther)
            throw new NoUserException("share in MySecureDataContainer12: L'Id "+Other+" non è presente nella collezione");

        //identifico l'utente con lo stesso Id attraverso la password
        boolean identificato = false;
        MyUtente1 user;
        Iterator<MyUtente1> utenteIterator = this.users.iterator();
        while(utenteIterator.hasNext() && !identificato){
            user = utenteIterator.next();
            if(user.getId().equals(Owner))
                if(user.identify(passw))
                    identificato = true;
        }
        if(! identificato)
            throw new WrongPasswordException("share in MySecureDataContainer12: password errata");

        trovato = false;
        for (MyData12<E> data12: this.data) {
            if(data12.getData().equals(data)){
                if(data12.getOwner().equals(Owner)){
                    trovato = true;
                    Iterator<String> OtherIterator = data12.getOthers();
                    while(OtherIterator.hasNext()){ //verifico che l'utente sia nella lista di utenti condivisi
                        if(OtherIterator.next().equals(Other))
                            throw new AlreradyUserException("share in MySecureDataContainer12: l'utente "+Other+" è già autorizzato a vedere il dato");
                    }
                    //l'utente non è nella lista di utenti condivisi, quindi lo aggiungo
                    data12.addOther(Other);
                }
            }

        }
        if (trovato) // il dato è stato trovato ma l'utente non è stato autorizzato ad accedervi per rimuoverlo
            throw new NotOwnerException("share in MySecureDataContainer12: l'utente "+ Owner+" non è autorizzato a condividere il dato");
        /*
        IR preservata: exists i, 0 <= i < n, t.c. utente_i.getId().equals(Other)
        */
    }

    // restituisce un iteratore (senza remove) che genera tutti i dati dell’utente in ordine arbitrario se vengono rispettati i controlli di identità
    public Iterator<E> getIterator(String Owner, String passw) throws NullPointerException, WrongPasswordException, NoUserException{
        if(Owner == null)
            throw new NullPointerException("getIterator in MySecureDataContainer12: inserisci un Id valido");
        if(passw == null)
            throw new NullPointerException("getIterator in MySecureDataContainer12: inserisci una password valida");

        //verifico che esista un utente con lo stesso Id
        boolean trovato = false;
        Iterator<String> Ids = this.getUsersIds();
        while(Ids.hasNext()){
            if(Ids.next().equals(Owner))
                trovato = true;
        }
        if(! trovato)
            throw new NoUserException("getIterator in MySecureDataContainer12: L' Id "+ Owner+ " non è presente nella collezione");

        //identifico l'utente con lo stesso Id attraverso la password
        boolean identificato = false;
        MyUtente1 user;
        Iterator<MyUtente1> utenteIterator = this.users.iterator();
        while(utenteIterator.hasNext() && !identificato){
            user = utenteIterator.next();
            if(user.getId().equals(Owner))
                if(user.identify(passw))
                    identificato = true;
        }
        if(! identificato)
            throw new WrongPasswordException("getIterator in MySecureDataContainer12: password errata");

        Vector<E> data12Vector = new Vector<E>(0);
        Iterator<MyData12<E>> data12Iterator = data.iterator();
        MyData12<E> data12;
        Iterator<String> OthersIterator;
        while(data12Iterator.hasNext()){
            data12 = data12Iterator.next();
            OthersIterator = data12.getOthers();
            while(OthersIterator.hasNext()){
                if(OthersIterator.next().equals(Owner))
                    data12Vector.addElement(data12.getData());
            }
        }
        return data12Vector.iterator();
        /*
        IR preservata: non modifico this
        */
    }

    // restituisce un iteratore (senza remove) che genera tutti i dati creati dall'utente in ordine arbitrario se vengono rispettati i controlli di identità
    public Iterator<E> getOwnerIterator(String Owner, String passw) throws NullPointerException, WrongPasswordException, NoUserException{
        /*
        REQUIRES:  Owner != null && password != null && exists i, 0 <= i < n, t.c. (utente_i.getId().equals(Owner) && utente_i.identify(password))
        THROWS: se Owner == null lancia NullPointerException (presente in java, unchecked)
                se passw == null lancia NullPointerException(presente in java, unchecked)
                se ! exists i, 0 <= i < n, utente_i.getId.equals(Owner) lancia NoUserException (non presente in java, checked)
                se exists i, 0 <= i < n, utente_i.getId.equals(Owner) && !utente_i.identify(passw) lancia WrongPasswordException (non presente in java, checked)
        MODIFIES: -public Iterator<E> getOwnerIterator(String Owner, String passw) throws NullPointerException, WrongPasswordException, NoUserException;
        EFFECTS(OF MODIFICATION): -
        RETURNS:  {data_i.getData() | Owner.equals(data_i.getOwner())}
        */
        if(Owner == null)
            throw new NullPointerException("getOwnerIterator in MySecureDataContainer12: inserisci un Id valido");
        if(passw == null)
            throw new NullPointerException("getOwnerIterator in MySecureDataContainer12: inserisci una password valida");

        //verifico che esista un utente con lo stesso Id
        boolean trovato = false;
        Iterator<String> Ids = this.getUsersIds();
        while(Ids.hasNext()){
            if(Ids.next().equals(Owner))
                trovato = true;
        }
        if(! trovato)
            throw new NoUserException("getOwnerIterator in MySecureDataContainer12: L' Id "+ Owner+ " non è presente nella collezione");

        //identifico l'utente con lo stesso Id attraverso la password
        boolean identificato = false;
        MyUtente1 user;
        Iterator<MyUtente1> utenteIterator = this.users.iterator();
        while(utenteIterator.hasNext() && !identificato){
            user = utenteIterator.next();
            if(user.getId().equals(Owner))
                if(user.identify(passw))
                    identificato = true;
        }
        if(! identificato)
            throw new WrongPasswordException("getOwnerIterator in MySecureDataContainer12: password errata");

        Vector<E> data1Vector = new Vector<E>(0);
        Iterator<MyData12<E>> data12Iterator = data.iterator();
        MyData12<E> data12;
        while(data12Iterator.hasNext()){
            data12 = data12Iterator.next();
            if(data12.getOwner().equals(Owner))
                data1Vector.addElement(data12.getData());
        }
        return data1Vector.iterator();
        /*
        IR preservata: non modifico this
        */
    }

}
