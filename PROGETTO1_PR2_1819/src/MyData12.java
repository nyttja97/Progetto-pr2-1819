import java.util.Iterator;
import java.util.TreeSet;

public class MyData12<E> {
    /*
    AF: f(<Owner, Data, {Other_0, ... ,Other_n-1}>) = <Owner, data, Others[0], ..., Others[n-1]>
    IR: Owner != null && data != null ==>  Others[0].equals(Owner)
                                           && for all i,j, 0 <= i < Others.size(), 0 <= j < Others.size(),
                                                                  !(Other[i].equals(Other[j]) ) ==> i != j
    */
    private String Owner;
    private E data;
    private TreeSet<String> Others;

    public MyData12(String Owner, E data) throws NullPointerException{
        if(Owner == null)
            throw new NullPointerException("Constructor in MyData12: non puoi inserire un utente nullo");
        if(data == null)
            throw new NullPointerException("Constructor in MyData12: non puoi creare un oggetto nullo");
        this.Owner = Owner;
        this.data = data;
        this.Others = new TreeSet<String>();
        Others.add(Owner);
        /*
        IR preservata: (Owner == null && data == null && Others contiene un solo elemento)
        */
    }

    public String getOwner() {
        return this.Owner;
        /*
        IR preservata: non modifico this
        */
    }

    public E getData() {
        return this.data;
        /*
        IR preservata: non modifico this
        */
    }

    public Iterator<String> getOthers() {
        return this.Others.iterator();
        /*
        IR preservata: non modifico this
        */
    }

    public void addOther(String Other) throws NullPointerException, AlreradyUserException{
        if(Other == null)
            throw new NullPointerException("AddOther in MyData12: Non puoi aggiungere un dato nullo");
        boolean trovato = false;
        String o;
        Iterator<String> iterator = this.getOthers();
        while(iterator.hasNext() && !trovato){
            o = iterator.next();
            if(o.equals(Other))
                trovato = true;
        }
        if(trovato)
            throw new AlreradyUserException("addOther in MyData12: l'utente "+Other+" è già in grado di vedere il dato");
        Others.add(Other);
        /*
        IR preservata: ogni elemento di Others aggiunto è diverso dagli altri e non nullo
        */
    }

    public boolean deleteOther(String Other) throws NullPointerException, DeletingOwnerException{
        if(Other==null)
            throw new NullPointerException("deleteOther in MyData11: non puoi togliere un Id nullo");
        if(Other.equals(this.Owner))
            throw new DeletingOwnerException("deleteOther in MyData11: non puoi eliminare il creatore del dato");
        return this.Others.remove(Other);

        /*
        IR preservata: ogni elemento di Others rimosso ripora in uno stato corretto precedente all'aggiunta dell'utente stesso. inoltre il creatore dell'elemento non può essere eliminato
        */
    }
}
