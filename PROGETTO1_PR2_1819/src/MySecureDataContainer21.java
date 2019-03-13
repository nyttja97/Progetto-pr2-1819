import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Iterator;
import java.util.Vector;

public class MySecureDataContainer21<E>{
    /*
    AF: f (< {utente_0, utente_n-1}, {data_0, data_m-1} >) = <users[0], ..., users[n-1], data[0], ..., data[m-1]>
        con n = users.length() && m = data.length()
    IR: (users != null && data != null) ==> for all i, 0 <= i < data.lenghth(), data[i] preserva IR di MyData1
                                            && for all j, 0 <= j < users.length(), users[j] preserva IR di MyUtente
                                            && for all i, 0 <= i < users.length(), for all j, 0 <= j < users.length(),
                                                ! (users[i].getId().equals(users[j].getId())
                                            && for all i, 0 <= i < data.length(), exists j, 0 <= j < users.length(), t.c.
                                                users[j].getId().equals(data[j].getOwner())
    */
    private Vector<MyUtente2> users;
    private Vector<MyData2<byte[]>> data;

    public MySecureDataContainer21(){
        users = new Vector<MyUtente2>(0);
        data = new Vector<MyData2<byte[]>>(0);
    }

    // Crea l’identità un nuovo utente della collezione
    public void createUser(String Id, String passw) throws NullPointerException, AlreradyUserException, NoSuchAlgorithmException {
        if(Id == null)
            throw new NullPointerException("createUser in MySecureDataContainer21: inserisci un Id valido");
        if(passw == null)
            throw new NullPointerException("createUser in MySecureDataContainer21: inserisci una password valida");
        Iterator<String> Ids = this.getUsersIds();
        while(Ids.hasNext())
            if(Ids.next().equals(Id))
                throw new AlreradyUserException("createUser in MySecureDataContainer21: L'Id "+Id+" è già stato usato da un altro utente");
        MyUtente2 utente = new MyUtente2(Id, passw);
        users.addElement(utente);
        /*
        IR preservata: l'utente inserito è di tipo MyUtente2 e l'id è diverso da ogni utente già creato
        */
    }

    //restituisce tutti gli Id degli utenti
    public Iterator<String> getUsersIds() {
        Vector<String> Ids = new Vector<>(0);
        for (MyUtente2 u: this.users) {
            Ids.addElement(u.getId());
        }
        return Ids.iterator();
        /*
        IR preservata: non modifico this
        */
    }

    public void removeUser(String Id, String passw) throws NullPointerException, NoUserException, WrongPasswordException{
        if(Id == null)
            throw new NullPointerException("removeUser in MySecureDataContainer21: inserisci un Id valido");
        if(passw == null)
            throw new NullPointerException("removeUser in MySecureDataContainer21: inserisci una password valida");
        boolean trovato = false;
        Iterator<String> Ids = this.getUsersIds();
        while(Ids.hasNext()){
            if(Ids.next().equals(Id))
                trovato = true;
        }
        if(! trovato)
            throw new NoUserException("removeUser in MySecureDataContainer21: L' Id "+ Id+" non è presente nella collezione");

        //identifico l'utente con lo stesso Id attraverso la password
        boolean identificato = false;
        MyUtente2 user;
        Iterator<MyUtente2> utenteIterator = this.users.iterator();
        while(utenteIterator.hasNext() && !identificato){
            user = utenteIterator.next();
            if(user.getId().equals(Id))
                if(user.identify(passw))
                    identificato = true;
        }
        if(! identificato)
            throw new WrongPasswordException("removeUser in MySecureDataContainer21: password errata");
        for (MyData2<byte[]> data2: data) {
            if(data2.getOwner().equals(Id))
                this.data.remove(data2);
        }
        for (MyUtente2 us: users) {
            if(us.getId().equals(Id)){
                users.remove(users.indexOf(us));
            }
        }
        /*
        IR preservata: l'eliminazione di un utente porta ad uno stato precedente corretto della classe
        */
    }

    public int getSize(String Owner, String passw) throws NullPointerException, NoUserException, WrongPasswordException{
        if(Owner == null)
            throw new NullPointerException("getSize in MySecureDataContainer21: inserisci un Id valido");
        if(passw == null)
            throw new NullPointerException("getSize in MySecureDataContainer21: inserisci una password valida");

        //verifico che esista un utente con lo stesso Id
        boolean trovato = false;
        Iterator<String> Ids = this.getUsersIds();
        while(Ids.hasNext()){
            if(Ids.next().equals(Owner))
                trovato = true;
        }
        if(! trovato)
            throw new NoUserException("getOSize in MySecureDataContainer21: L' Id "+ Owner+ " non è presente nella collezione");

        //identifico l'utente con lo stesso Id attraverso la password
        boolean identificato = false;
        MyUtente2 user;
        Iterator<MyUtente2> utenteIterator = this.users.iterator();
        while(utenteIterator.hasNext() && !identificato){
            user = utenteIterator.next();
            if(user.getId().equals(Owner))
                if(user.identify(passw))
                    identificato = true;
        }
        if(! identificato)
            throw new WrongPasswordException("getSize in MySecureDataContainer21: password errata");

        int elem = 0;
        Iterator<MyData2<byte[]>> data2Iterator = data.iterator();
        while(data2Iterator.hasNext()){
            if(data2Iterator.next().getOwner().equals(Owner))
                elem ++;
        }
        return elem;
        /*
        IR preservata: non modifico this
        */
    }

    // Inserisce il valore del dato nella collezione se vengono rispettati i controlli di identità
    public boolean put(String Owner, String passw, E data) throws Exception {
        if(Owner == null)
            throw new NullPointerException("put in MySecureDataContainer21: inserisci un Id valido");
        if(passw == null)
            throw new NullPointerException("put in MySecureDataContainer21: inserisci una password valida");
        if (data == null)
            throw new NullPointerException("put in MySecureDataContainer21: inserisci un dato valido");

        //verifico che esista un utente con lo stesso Id
        boolean trovato = false;
        Iterator<String> Ids = this.getUsersIds();
        while(Ids.hasNext()){
            if(Ids.next().equals(Owner))
                trovato = true;
        }
        if(! trovato)
            throw new NoUserException("put in MySecureDataContainer21: L' Id "+ Owner+ " non è presente nella collezione");

        //identifico l'utente con lo stesso Id attraverso la password
        boolean identificato = false;
        MyUtente2 user;
        Iterator<MyUtente2> utenteIterator = this.users.iterator();
        while(utenteIterator.hasNext() && !identificato){
            user = utenteIterator.next();
            if(user.getId().equals(Owner))
                if(user.identify(passw)) {
                    identificato = true;
                    byte[] dataencrypted = encrypt(user.getPublicKey(), data);
                    MyData2<byte[]> data2 = new MyData2<byte[]>(Owner, dataencrypted);
                    this.data.addElement(data2);
                    return true;
                }
        }
        if(! identificato)
            throw new WrongPasswordException("put in MySecureDataContainer21: password errata");
        return true;
        /*
        IR preservata:  data != null ==> exists j, 0 <= j < users.length(), t.c. users[j].getId().equals(data1.getOwner())
        */
    }

    // Ottiene una copia del valore del dato nella collezione (senza aggiungere la copia alla collezione) se vengono rispettati i controlli di identità
    public E get(String Owner, String passw, E data) throws Exception {
        if(Owner == null)
            throw new NullPointerException("get in MySecureDataContainer21: inserisci un Id valido");
        if(passw == null)
            throw new NullPointerException("get in MySecureDataContainer21: inserisci una password valida");
        if (data == null)
            throw new NullPointerException("get in MySecureDataContainer21: inserisci un dato valido");

        //verifico che esista un utente con lo stesso Id
        boolean trovato = false;
        Iterator<String> Ids = this.getUsersIds();
        while(Ids.hasNext()) {
            if (Ids.next().equals(Owner)){
                trovato = true;
            }
        }
        if(! trovato)
            throw new NoUserException("get in MySecureDataContainer21: L' Id "+ Owner+ " non è presente nella collezione");

        //identifico l'utente con lo stesso Id attraverso la password
        boolean identificato = false;
        MyUtente2 user;
        Iterator<MyUtente2> utenteIterator = this.users.iterator();
        while(utenteIterator.hasNext() && !identificato){
            user = utenteIterator.next();
            if(user.getId().equals(Owner)) {
                if (user.identify(passw)) {
                    identificato = true;
                    //verifico che il dato possa essere acceduto dall'utente
                    Iterator<MyData2<byte[]>> data2Iterator = this.data.iterator();
                    MyData2<byte[]> data2;
                    trovato = false;
                    while (data2Iterator.hasNext()) {
                        data2 = data2Iterator.next();
                        if (data2.getOwner().equals(Owner)) {
                            byte[] dataencrypted = encrypt(user.getPublicKey(), data);
                            if(dataencrypted.length == data2.getData().length){
                                trovato = true;
                                for (int i = 0; i < dataencrypted.length; i++) {
                                    if(!(dataencrypted[i] == data2.getData()[i]))
                                        trovato = false;
                                }
                            }
                            if(trovato)
                                System.out.println("dato trovato");
                            return data;
                        }
                    }
                }
            }
        }

        if(! identificato) //password sbagliata
            throw new WrongPasswordException("get in MySecureDataContainer21: password errata");

        //il dato è stato trovato ma l'utente non è autorizzato ad accedervi
        throw new MissingPermissionException("get in MySecureDataContainer21: L'utente "+Owner+" non è autorizzato a ottenere la copia del dato");
        /*
        IR preservata: non modifico this
        */
    }

    //permette di leggere tutti i dati, decifrandoli con la propria chiave privata. ovviamente i dati non cifrati con la propria chiave pubblica saranno indeficrabili
    public Iterator<byte[]> seeData(String Id, String password) throws NullPointerException, NoUserException, WrongPasswordException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        /*
        REQUIRES: Id != null && password != null && exist u in user t.c. u.getId().equals(id) && u.identify(password)
        THROWS: se Owner == null lancia NullPointerException (presente in java, unchecked)
                se passw == null lancia NullPointerException (presente in java, unchecked)
                se ! exists i, 0 <= i < n, utente_i.getId.equals(Owner) lancia NoUserException (non presente in java, checked)
                se exists i, 0 <= i < n, utente_i.getId.equals(Owner) && !utente_i.identify(passw) lancia WrongPasswordException (non presente in java, checked)
        MODIFIES: -
        EFFECTS(OF MODIFICATION): -
        RETURNS: for all d in data, ritorna d.getData() decifrato con la propria chiave privata
        */
        if(Id == null)
            throw new NullPointerException("seeData in MySecureDataContainer21: inserisci un Id non vuoto");
        if(password == null)
            throw new NullPointerException("seeData in MySecureDataContainer21: inserisci una password valida");
        //verifico che esista un utente con lo stesso Id
        boolean trovato = false;
        Iterator<String> Ids = this.getUsersIds();
        while(Ids.hasNext()) {
            if (Ids.next().equals(Id)){
                trovato = true;
            }
        }
        if(! trovato)
            throw new NoUserException("seeData in MySecureDataContainer21: L' Id "+ Id+ " non è presente nella collezione");

        //identifico l'utente con lo stesso Id attraverso la password
        boolean identificato = false;
        MyUtente2 user;
        Iterator<MyUtente2> utenteIterator = this.users.iterator();
        Vector<byte[]> datas = new Vector<byte[]>(0);
        while(utenteIterator.hasNext() && !identificato) {
            user = utenteIterator.next();
            if (user.getId().equals(Id)) {
                if (user.identify(password)) {
                    identificato = true;
                    for (MyData2<byte[]> d: this.data) {
                        try {
                            datas.addElement(user.decrypt(password, d.getData()));
                        }
                        catch(BadPaddingException e){
                            datas.addElement("Non puoi vedere questo dato".getBytes());
                        }
                    }
                }
            }
        }
        //password sbagliata
        if(! identificato)
        throw new WrongPasswordException("seeData in MySecureDataContainer21: password errata");
        /*
        IR preservata: non modifico this
        */
        return datas.iterator();
    }

    public E remove(String Owner, String passw, E data) throws Exception {
        if(Owner == null)
            throw new NullPointerException("remove in MySecureDataContainer21: inserisci un Id valido");
        if(passw == null)
            throw new NullPointerException("remove in MySecureDataContainer21: inserisci una password valida");
        if (data == null)
            throw new NullPointerException("remove in MySecureDataContainer21: inserisci un dato valido");

        //verifico che esista un utente con lo stesso Id
        boolean trovato = false;
        Iterator<String> Ids = this.getUsersIds();
        while(Ids.hasNext()){
            if(Ids.next().equals(Owner))
                trovato = true;
        }
        if(! trovato)
            throw new NoUserException("remove in MySecureDataContainer21: L' Id "+ Owner+ " non è presente nella collezione");

        //identifico l'utente con lo stesso Id attraverso la password
        boolean identificato = false;
        MyUtente2 user;
        Iterator<MyUtente2> utenteIterator = this.users.iterator();
        byte[] dataencrypted = null;
        while(utenteIterator.hasNext() && !identificato){
            user = utenteIterator.next();
            if(user.getId().equals(Owner))
                if(user.identify(passw)) {
                    identificato = true;
                    dataencrypted = encrypt(user.getPublicKey(), data);
                }
        }
        if(! identificato)
            throw new WrongPasswordException("remove in MySecureDataContainer21: password errata");


        trovato = false;
        MyData2<byte[]> data2;
        Iterator<MyData2<byte[]>> data2Iterator = this.data.iterator();
        while(data2Iterator.hasNext()) {
            data2 = data2Iterator.next();
            if (data2.getOwner().equals(Owner)) {
                if (data2.getData().length == dataencrypted.length) {
                    trovato = true;
                    for (int i = 0; i < dataencrypted.length; i++) {
                        if (!(data2.getData()[i] == dataencrypted[i]))
                            trovato = false;
                    }
                }
                if (trovato) {
                    this.data.removeElement(data2);
                    return data;
                }
            }
        }

        // il dato è stato trovato ma l'utente non è stato autorizzato ad accedervi per rimuoverlo
        throw new NotOwnerException("remove in MySecureDataContainer21: l'utente "+ Owner+" non è autorizzato a rimuovere il dato");
        /*
        IR preservata: elimino un dato, non gli utenti, quindi le premesse delle implicazioni per gli altri dati sono sempre valide
        */
    }

    // Crea una copia del dato nella collezione se vengono rispettati i controlli di identità
    public void copy(String Owner, String passw, E data) throws Exception {
        if(Owner == null)
            throw new NullPointerException("copy in MySecureDataContainer21: inserisci un Id valido");
        if(passw == null)
            throw new NullPointerException("copy in MySecureDataContainer21: inserisci una password valida");
        if (data == null)
            throw new NullPointerException("copy in MySecureDataContainer21: inserisci un dato valido");

        //verifico che esista un utente con lo stesso Id
        boolean trovato = false;
        Iterator<String> Ids = this.getUsersIds();
        while(Ids.hasNext()){
            if(Ids.next().equals(Owner))
                trovato = true;
        }
        if(! trovato)
            throw new NoUserException("copy in MySecureDataContainer21: L' Id "+ Owner+ " non è presente nella collezione");

        //identifico l'utente con lo stesso Id attraverso la password
        boolean identificato = false;
        MyUtente2 user;
        Iterator<MyUtente2> utenteIterator = this.users.iterator();
        byte[] dataencrypted;
        while(utenteIterator.hasNext() && !identificato){
            user = utenteIterator.next();
            if(user.getId().equals(Owner)) {
                if (user.identify(passw)) {
                    identificato = true;
                    Iterator<MyData2<byte[]>> data2Iterator = this.data.iterator();
                    MyData2<byte[]> data2;
                    while(data2Iterator.hasNext()){
                        data2 = data2Iterator.next();
                        if(data2.getOwner().equals(Owner)) {
                            trovato = false;
                            dataencrypted = encrypt(user.getPublicKey(), data);
                            if(data2.getData().length == dataencrypted.length){
                                trovato = true;
                                for (int i = 0; i<dataencrypted.length; i++) {
                                    if(!(data2.getData()[i] == dataencrypted[i]))
                                        trovato = false;
                                }
                            }
                            if(trovato){
                                MyData2<byte[]> newdata = new MyData2<byte[]>(Owner, dataencrypted);
                                this.data.addElement(newdata);
                            }
                        }
                    }
                }
            }
        }
        if(! identificato)
            throw new WrongPasswordException("copy in MySecureDataContainer21: password errata");

        //verifico che il dato possa essere acceduto dall'utente

        //il dato è stato trovato ma l'utente non è autorizzato ad accedervi
        throw new MissingPermissionException("copy in MySecureDataContainer21: L'utente "+Owner+" non è autorizzato a copiare il dato");
        /*
        IR preservata: Owner esiste nella lista utenti, quindi l'IR è preservata creando un nuovo elemento in data
        */
    }

    // Condivide il dato nella collezione con un altro utente se vengono rispettati i controlli di identità
    public void share(String Owner, String passw, String Other, E data) throws Exception {
        if(Owner == null)
            throw new NullPointerException("share in MySecureDataContainer21: inserisci un Id valido");
        if(passw == null)
            throw new NullPointerException("share in MySecureDataContainer21: inserisci una password valida");
        if(Other == null)
            throw new NullPointerException("share in MySecureDataContainer21: inserisci un Id con cui condividere il dato valido");
        if (data == null)
            throw new NullPointerException("share in MySecureDataContainer21: inserisci un dato valido");

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
            throw new NoUserException("share in MySecureDataContainer21: L' Id "+ Owner+ " non è presente nella collezione");
        if(! trovatoOther)
            throw new NoUserException("share in MySecureDataContainer21: L'Id "+Other+" non è presente nella collezione");

        //identifico l'utente con lo stesso Id attraverso la password
        boolean identificato = false;
        MyUtente2 user;
        MyUtente2 user2;
        Iterator<MyUtente2> utenteIterator = this.users.iterator();
        Iterator<MyUtente2> utente2Iterator = this.users.iterator();
        byte[] dataencrypted;
        byte[] dataencryptedother;
        while(utenteIterator.hasNext() && !identificato){
            user = utenteIterator.next();
            if(user.getId().equals(Owner)) {
                if (user.identify(passw)) {
                    identificato = true;
                    Iterator<MyData2<byte[]>> data2Iterator = this.data.iterator();
                    MyData2<byte[]> data2;
                    while(data2Iterator.hasNext()){
                        data2 = data2Iterator.next();
                        if(data2.getOwner().equals(Owner)) {
                            trovato = false;
                            dataencrypted = encrypt(user.getPublicKey(), data);
                            if(data2.getData().length == dataencrypted.length){
                                trovato = true;
                                for (int i = 0; i<dataencrypted.length; i++) {
                                    if(!(data2.getData()[i] == dataencrypted[i]))
                                        trovato = false;
                                }
                            }
                            if(trovato){
                                while(utente2Iterator.hasNext()){
                                    user2 = utente2Iterator.next();
                                    if(user2.getId().equals(Other)){
                                        dataencryptedother = encrypt(user2.getPublicKey(), data);
                                        MyData2<byte[]> newdata = new MyData2<byte[]>(Other, dataencryptedother);
                                        this.data.addElement(newdata);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if(! identificato)
            throw new WrongPasswordException("share in MySecureDataContainer21: password errata");

        if (trovato) // il dato è stato trovato ma l'utente non è stato autorizzato ad accedervi per rimuoverlo
            throw new NotOwnerException("share in MySecureDataContainer21: l'utente "+ Owner+" non è autorizzato a condividere il dato");
        /*
        IR preservata: exists i, 0 <= i < n, t.c. utente_i.getId().equals(Other)
        */
    }

    // restituisce un iteratore (senza remove) che genera tutti i dati dell’utente in ordine arbitrario se vengono rispettati i controlli di identità
    public Iterator<byte[]> getIterator(String Owner, String passw) throws NullPointerException, WrongPasswordException, NoUserException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        if(Owner == null)
            throw new NullPointerException("getIterator in MySecureDataContainer21: inserisci un Id valido");
        if(passw == null)
            throw new NullPointerException("getIterator in MySecureDataContainer21: inserisci una password valida");

        //verifico che esista un utente con lo stesso Id
        boolean trovato = false;
        Iterator<String> Ids = this.getUsersIds();
        while(Ids.hasNext()){
            if(Ids.next().equals(Owner))
                trovato = true;
        }
        if(! trovato)
            throw new NoUserException("getIterator in MySecureDataContainer21: L' Id "+ Owner+ " non è presente nella collezione");

        //identifico l'utente con lo stesso Id attraverso la password
        boolean identificato = false;
        MyUtente2 user;
        Iterator<MyUtente2> utenteIterator = this.users.iterator();
        Vector<byte[]> data2Vector = new Vector<byte[]>(0);
        Iterator<MyData2<byte[]>> data2Iterator = data.iterator();
        MyData2<byte[]> data2;
        while(utenteIterator.hasNext() && !identificato){
            user = utenteIterator.next();
            if(user.getId().equals(Owner)) {
                if (user.identify(passw)) {
                    identificato = true;
                    while(data2Iterator.hasNext()){
                        data2 = data2Iterator.next();
                        if(data2.getOwner().equals(Owner))
                            data2Vector.addElement(user.decrypt(passw, data2.getData()));
                    }
                }
            }
        }

        if(! identificato)
            throw new WrongPasswordException("getIterator in MySecureDataContainer21: password errata");
        return data2Vector.iterator();

        /*
        IR preservata: non modifico this
        */
    }


    //cifro un dato generico. il metodo è private ed è usato come supporto per altri metodi
    private byte[] encrypt(PublicKey publicKey, E message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(message.toString().getBytes());
    }

}
