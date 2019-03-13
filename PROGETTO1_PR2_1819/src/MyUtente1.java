public class MyUtente1 implements Utente1 {
    /*
    AF: f(<Id, password>) = <Id, password>
    IR: Id != null && password != null ==> elemento valido
    */

    private final String Id;
    private final String password;

    //costruttore della classe
    public MyUtente1(String Id, String password) throws NullPointerException{
        if(Id == null)
            throw new NullPointerException("constructor in MyUtente: inserisci un valore correetto");
        if(password == null)
            throw new NullPointerException("constructor in MyUtente: inserisci una password valida");
        this.Id = new String(Id);
        this.password = new String(password);
        /*
        IR preservata: if ( Id == null || password == null) il costruttore non termina istanziando le variabili
                       if( Id != null && password != null) le variabili non potranno più essere modificate
        */
    }

    //restituisce l'Id (pubblico, visibile a tutti)
    public String getId() {
        return Id;
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
}