import java.util.Iterator;

public interface Data1<E> {
    /*
    OVERVIEW: tipo modificabile che descrive un dato associato ad un utente e condiviso con altri utenti
    Typical Element: <Owner, Data, {Other_0, ... ,Other_n-1}>  t.c. Owner == Other_0
                                                                    && for all i,j, 0 <= i < n, 0 <= j < n, Other_i != Other_j ==> i != j
    */

    public String getOwner();
    /*
    REQUIRES: -
    THROWS: -
    MODIFIES: -
    EFFECTS(OF MODIFICATION): -
    RETURNS: this.Owner
    */

    public E getData();
    /*
    REQUIRES: -
    THROWS: -
    MODIFIES: -
    EFFECTS(OF MODIFICATION): -
    RETURNS: this.Data
    */

    public Iterator<String> getOthers();
    /*
    REQUIRES: -
    THROWS: -
    MODIFIES: -
    EFFECTS(OF MODIFICATION): -
    RETURNS: this.Other
    */

    public void addOther(String Other) throws NullPointerException, AlreradyUserException;
    /*
    REQUIRES: Other != null && for all i, 0 <= i < n, Other != Other_i
    THROWS: se Other == null lancia NullPointerException (presente in java, unchecked)
            se esiste i, 0 <= i < n , Other.equals(Other_i) lancia AlreadyUserException (non presente in java, checked)
    MODIFIES: this
    EFFECTS(OF MODIFICATION): Other = Other_n, n=n+1
    RETURNS: -
    */

    public boolean deleteOther(String Other) throws NullPointerException, DeletingOwnerException;
    /*
    REQUIRES: Other != null && !Other.equals(Owner)
    THROWS: se Other == null lancia NullPointerException(presente in java, unchecked)
            se Other.equals(Owner) lancia DeletingOwnerException(non presente in java, checked)
    MODIFIES: this
    EFFECTS(OF MODIFICATION): cancella Other dalla collezione Other, se presente, altrimenti lascia lo stato inalterato
    RETURNS: se avviene la cancellazione dell'elemento, ritorna true
             altrimenti ritorna false
    */
}
