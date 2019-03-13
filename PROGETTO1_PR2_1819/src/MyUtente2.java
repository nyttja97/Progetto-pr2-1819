import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

public class MyUtente2 implements Utente2 {

    /*
    AF: f(<Id, password, k_priv, k_pub>) = <Id, password, pubKey, privKey>
    IR: Id != null && password != null && pubKey != null && privKey != null
    */

    private final String Id;
    private final String password;
    private final PublicKey pubKey;
    private final PrivateKey privKey;

    //costruttore
    public MyUtente2(String Id, String password) throws NullPointerException, NoSuchAlgorithmException {
        if(Id == null)
            throw new NullPointerException("constructor in MyUtente: inserisci un valore correetto");
        if(password == null)
            throw new NullPointerException("constructor in MyUtente: inserisci una password valida");
        this.Id = new String(Id);
        this.password = new String(password);
        int keySize = 1024;
        KeyPair keyPair;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        keyPair = keyPairGenerator.generateKeyPair();
        pubKey = keyPair.getPublic();
        privKey = keyPair.getPrivate();
        /*
        IR preservata:  Id != null && password != null ==> pubKey != null && privKey != null
        */
    }

    //restituisce l'id
    public String getId(){
        return this.Id;
        /*
        IR preservata: non modifico this
        */
    }

    //identifica l'utente attraverso la password (privata, non visibile)
    public boolean identify(String password) throws NullPointerException{
        if(password == null)
            throw new NullPointerException("identify in MyUtente: inserisci una password valida");
        return this.password.equals(password);
        /*
        IR preservata: non modifico this
        */
    }

    public PublicKey getPublicKey(){
        return this.pubKey;
        /*
        IR preservata: non modifico this
        */
    }

    public byte[] decrypt(String passw, byte [] encrypted) throws NullPointerException, WrongPasswordException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        if(passw == null)
            throw new NullPointerException("decrypt in MyUtente2: inserisci una password valida");
        if(! (this.identify(passw)))
            throw new WrongPasswordException("decrypt in MyUtente2: la password inserita non è valida");
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, this.privKey);
        return cipher.doFinal(encrypted);
    }
}
