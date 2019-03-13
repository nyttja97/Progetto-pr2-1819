public interface Data2<E> {
     /*
    OVERVIEW: tipo modificabile che descrive un dato associato ad un utente
    Typical Element: <Owner, Data>
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

    public void modify(E data) throws NullPointerException;
    /*
    REQUIRES: data != null
    THROWS: se data == null lancia NullPointerException(presente in java, unchecked)
    MODIFIES: this
    EFFECTS(OF MODIFICATION): this.data = data
    RETURNS: -
    */
}
