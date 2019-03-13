public interface Utente1 {
    /*
    OVERVIEW: tipo modificabile che descrive un utente
    Typical Element: <Id, password>
    */

    public String getId();
    /*
    REQUIRES: -
    THROWS: -
    MODIFIES: -
    EFFECTS(OF MODIFICATION): -
    RETURNS: this.Id
    */

    public boolean identify(String password) throws NullPointerException;
    /*
    REQUIRES: password != null
    THROWS: se password == null lancia NullPointerException (presente in java, unchecked)
    MODIFIES: -
    EFFECTS(OF MODIFICATION): -
    RETURNS: true se this.password.equals(password)
             false altrimenti
    */
}
