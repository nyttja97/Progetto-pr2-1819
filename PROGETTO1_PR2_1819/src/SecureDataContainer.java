import java.util.Iterator;

public interface SecureDataContainer<E> {
    /*
    OVERVIEW: tipo modificabile che descrive un insieme di dati, ognuno legato ad uno o più utenti
    Typical Element: < {utente_0, utente_n-1}, {data_0, data_m-1} > t.c. for all i, 0 <= i < n, utente_i è un elemento
                     valido di tipo utente && for all j, 0 <= j < m, data_j è un elemento valido di tipo data
    */

    // Crea l’identità un nuovo utente della collezione
    public void createUser(String Id, String passw) throws NullPointerException, AlreradyUserException;
    /*
    REQUIRES: Id != null && passw != null && for all i, 0 <= i <n, utente_i.Id != Id
    THROWS: se Id == null lancia NullPointerException (presente in java, unchecked)
            se passw == null lancia NullPointerException (presente in java, unchecked)
            se exists i, 0 <= i < n, utente_i.Id == Id lancia AlreadyUserException (non presente in java, checked)
    MODIFIES: this
    EFFECTS(OF MODIFICATION): utente_n == <Id, passw>, n++
    RETURNS: -
    */

    //restituisce una copia di tutti gli Id degli utenti
    public Iterator<String> getUsersIds();
    /*
    REQUIRES: -
    THROWS: -
    MODIFIES: -
    EFFECTS(OF MODIFICATION): -
    RETURNS: una copia di {utente_0.getId(), ..., utente_n-1.getId()}
    */

    public void removeUser(String Id, String passw) throws NullPointerException, NoUserException, WrongPasswordException;
    /*
    REQUIRES: Id!= null && passw!=null && esiste i, 0 <= i <= n, t.c. utente_i.equals(<Id, passw>)
    THROWS: se Id==null lancia NullPointerException(presente in java, unchecked)
            se passw==null lancia NullPointerException(presente in java, unchecked)
            se ! esiste i, 0 <= i <= n, t.c. utente_i.equals(<Id, passw>) lancia NoUserExceptio (non presente in java, checked)
            se exists i, 0 <= i < n, utente_i.getId.equals(Owner) && !utente_i.identify(passw) lancia WrongPasswordException (non presente in java, checked)
    MODIFIES: this
    EFFECTS(OF MODIFICATION): elimina tutti i dati creati da Id, elimina Id da tutti gli utenti che possono accedere ai dati in lettura
                              e infine elimina l'utente dalla collezione di utenti
    RETURNS: -
    */

    // Restituisce il numero degli elementi di un utente presenti nella collezione se sono rispettati i controlli di identità
    public int getSize(String Owner, String passw) throws NullPointerException, NoUserException, WrongPasswordException;
    /*
    REQUIRES: Owner != null && password != null && exists i, 0 <= i < n, t.c. (utente_i.getId().equals(Owner) && utente_i.identify(password))
    THROWS: se Owner == null lancia NullPointerException (presente in java, unchecked)
            se passw == null lancia NullPointerException(presente in java, unchecked)
            se ! exists i, 0 <= i < n, utente_i.getId.equals(Owner) lancia NoUserException (non presente in java, checked)
            se exists i, 0 <= i < n, utente_i.getId.equals(Owner) && !utente_i.identify(passw) lancia WrongPasswordException (non presente in java, checked)
    MODIFIES: -
    EFFECTS(OF MODIFICATION): -
    RETURNS: #{data_i | Owner appartiene a data_i.Others}
    */

    // Inserisce il valore del dato nella collezione se vengono rispettati i controlli di identità
    public boolean put(String Owner, String passw, E data) throws NullPointerException, NoUserException, WrongPasswordException;
    /*
    REQUIRES: Owner != null && password != null && data != null && exists i, 0 <= i < n, t.c. (utente_i.getId().equals(Owner) && utente_i.identify(password))
    THROWS: se Owner == null lancia NullPointerException (presente in java, unchecked)
            se passw == null lancia NullPointerException (presente in java, unchecked)
            se data  == null lancia NullPointerException (presente in java, unchecked)
             se ! exists i, 0 <= i < n, utente_i.getId.equals(Owner) lancia NoUserException (non presente in java, checked)
            se exists i, 0 <= i < n, utente_i.getId.equals(Owner) && !utente_i.identify(passw) lancia WrongPasswordException (non presente in java, checked)
    MODIFIES: this
    EFFECTS(OF MODIFICATION): data_m = <Owner, data, {}>, m++
    RETURNS: se vengono rispettati i controlli ritorna true, altrimenti false
    */

    // Ottiene una copia del valore del dato nella collezione (senza aggiungere la copia alla collezione) se vengono rispettati i controlli di identità
    public E get(String Owner, String passw, E data) throws NullPointerException, NoUserException, WrongPasswordException, MissingPermissionException;
    /*
    REQUIRES:Owner != null && password != null && data != null
             && exists i, 0 <= i < n, t.c. (utente_i.getId().equals(Owner) && utente_i.identify(password))
             && exists i, 0 <= i < m t.c. (data_i.getData().equals(data) && exists Other in data_i.getOther() t.c. Owner.equals(Other))
    THROWS: se Owner == null lancia NullPointerException (presente in java, unchecked)
            se passw == null lancia NullPointerException (presente in java, unchecked)
            se data  == null lancia NullPointerException (presente in java, unchecked)
            se ! exists i, 0 <= i < n, utente_i.getId.equals(Owner) lancia NoUserException (non presente in java, checked)
            se exists i, 0 <= i < n, utente_i.getId.equals(Owner) && !utente_i.identify(passw) lancia WrongPasswordException (non presente in java, checked)
            se exists i, 0 <= i < m t.c. (data_i.getData().equals(data) && ! exists Other in data_i.getOther() t.c. Owner.equals(Other)) lancia MissingPermissionException(non presente in java, checked)
    MODIFIES: -
    EFFECTS(OF MODIFICATION): -
    RETURNS: una copia di data
    */

    // Rimuove il dato nella collezione se vengono rispettati i controlli di identità
    public E remove(String Owner, String passw, E data) throws NullPointerException, NoUserException, WrongPasswordException, NotOwnerException;
    /*
    REQUIRES: Owner != null && password != null && data != null
              && exists i, 0 <= i < n, t.c. (utente_i.getId().equals(Owner) && utente_i.identify(password))
              && exists i, 0 <= i < m t.c. (data_i.getData().equals(data) && data_i.getOwner().equals(Owner))
    THROWS: se Owner == null lancia NullPointerException (presente in java, unchecked)
            se passw == null lancia NullPointerException (presente in java, unchecked)
            se data  == null lancia NullPointerException (presente in java, unchecked)
            se ! exists i, 0 <= i < n, utente_i.getId.equals(Owner) lancia NoUserException (non presente in java, checked)
            se exists i, 0 <= i < n, utente_i.getId.equals(Owner) && !utente_i.identify(passw) lancia WrongPasswordException (non presente in java, checked)
            se exists i, 0 <= i < m t.c. data_i.getData().equals(data) ==> ! (data_i.getOwner.equals(Owner) lancia NotOwnerException(non presente in java, checked)

    MODIFIES: this
    EFFECTS(OF MODIFICATION): elimina il dato data avente come Owner Owner da this
    RETURNS: data se il dato è stato eliminato con successo, null altrimenti
    */

    // Crea una copia del dato nella collezione se vengono rispettati i controlli di identità
    public void copy(String Owner, String passw, E data) throws NullPointerException, NoUserException, WrongPasswordException, MissingPermissionException;
    /*
    REQUIRES: Owner != null && password != null && data != null
              && exists i, 0 <= i < n, t.c. (utente_i.getId().equals(Owner) && utente_i.identify(password))
              && exists i, 0 <= i < m t.c. (data_i.getData().equals(data) && exists Other in data_i.getOther() t.c. Owner.equals(Other))
    THROWS: se Owner == null lancia NullPointerException (presente in java, unchecked)
            se passw == null lancia NullPointerException (presente in java, unchecked)
            se data  == null lancia NullPointerException (presente in java, unchecked)
            se ! exists i, 0 <= i < n, utente_i.getId.equals(Owner) lancia NoUserException (non presente in java, checked)
            se exists i, 0 <= i < n, utente_i.getId.equals(Owner) && !utente_i.identify(passw) lancia WrongPasswordException (non presente in java, checked)
            se exists i, 0 <= i < m t.c. (data_i.getData().equals(data) && ! exists Other in data_i.getOther() t.c. Owner.equals(Other)) lancia MissingPermissionException(non presente in java, checked)
    MODIFIES: this
    EFFECTS(OF MODIFICATION): data_m = <Owner, data>, m++
    RETURNS: -
    */

    // Condivide il dato nella collezione con un altro utente se vengono rispettati i controlli di identità
    public void share(String Owner, String passw, String Other, E data) throws NullPointerException, NoUserException, WrongPasswordException, NotOwnerException, AlreradyUserException, Exception;
    /*
    REQUIRES: Owner != null && passw != null && Other != null && data != null
               && exists i, 0 <= i < n, t.c. (utente_i.getId().equals(Owner) && utente_i.identify(password))
               && exists i, 0 <= i < n, t.c. (utente_i.getId()(O.equals(Other)
              && exists i, 0 <= i < m t.c. (data_i.getData().equals(data) && data_i.getOwner().equals(Owner)
                                            && ! exists Other in data_i.getOther() t.c. Owner.equals(Other))
    THROWS: se Owner == null lancia NullPointerException (presente in java, unchecked)
            se passw == null lancia NullPointerException (presente in java, unchecked)
            se Other == null lancia NullPointerException (presente in java, unchecked)
            se data  == null lancia NullPointerException (presente in java, unchecked)
            se ! exists i, 0 <= i < n, utente_i.getId.equals(Owner) lancia NoUserException (non presente in java, checked)
            se ! exists i, 0 <= i < n, utente_i.getId.equals(Other) lancia NoUserException (non presente in java, checked)
            se exists i, 0 <= i < n, utente_i.getId.equals(Owner) && !utente_i.identify(passw) lancia WrongPasswordException (non presente in java, checked)
            se exists i, 0 <= i < m t.c. (data_i.getData().equals(data) && ! exists Other in data_i.getOther() t.c. Owner.equals(Other)) lancia MissingPermissionException(non presente in java, checked)
            se exists Other in data_i.getOther() t.c. Owner.equals(Other)) lancia AlreadyUserException (non presente in java, checked))
    MODIFIES: this
    EFFECTS(OF MODIFICATION): data.addOther(Other)
    RETURNS: -
    */

    // restituisce un iteratore (senza remove) che genera tutti i dati dell’utente in ordine arbitrario se vengono rispettati i controlli di identità
    public Iterator<E> getIterator(String Owner, String passw) throws NullPointerException, WrongPasswordException, NoUserException;
    /*
    REQUIRES: Owner != null && password != null && exists i, 0 <= i < n, t.c. (utente_i.getId().equals(Owner) && utente_i.identify(password))
    THROWS: se Owner == null lancia NullPointerException (presente in java, unchecked)
            se passw == null lancia NullPointerException(presente in java, unchecked)
            se ! exists i, 0 <= i < n, utente_i.getId.equals(Owner) lancia NoUserException (non presente in java, checked)
            se exists i, 0 <= i < n, utente_i.getId.equals(Owner) && !utente_i.identify(passw) lancia WrongPasswordException (non presente in java, checked)
    MODIFIES: -
    EFFECTS(OF MODIFICATION): -
    RETURNS: {data_i.getData() | Owner presente in data_i.getOther()}
    */
}
