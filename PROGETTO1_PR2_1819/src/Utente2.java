import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public interface Utente2 {

    /*
    OVERVIEW: tipo modificabile che descrive un utente, con una coppia di chiavi pubblica e privata
    Typical Element: <Id, password, k_priv, k_pub>
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

    public PublicKey getPublicKey();
    /*
    REQUIRES: -
    THROWS: -
    MODIFIES: -
    EFFECTS(OF MODIFICATION): -
    RETURNS: this.k_pub
    */

    public byte[] decrypt(String passw, byte [] encrypted) throws NullPointerException, WrongPasswordException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException;
    /*
    REQUIRES: passw != null && passw.equals(this.password)
    THROWS: se passw == null lancia NullPointerException(presente in java, unchecked)
            se !(passw.equals(this.password)) lancia WrongPassordException (non presente in java, checked)
    MODIFIES:
    EFFECTS(OF MODIFICATION):
    RETURNS:
    */


}
