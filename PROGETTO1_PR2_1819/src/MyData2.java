import java.util.Vector;

public class MyData2<E> implements Data2<E>{
    /*
    AF: f(<Owner, Data>) = <Owner, Data>
    IR: Owner != null && Data != null
    */

    private String Owner;
    private E data;

    public MyData2(String Owner, E data) throws NullPointerException{
        if(Owner == null)
            throw new NullPointerException("Constructor in MyData2: non puoi inserire un utente nullo");
        if(data == null)
            throw new NullPointerException("Constructor in MyData2: non puoi creare un oggetto nullo");
        this.Owner = Owner;
        this.data = data;
        /*
        IR preservata: Owner == null && data == null
        */
    }

    public String getOwner() {
        return Owner;
        /*
        IR preservata: non modifico this
        */
    }

    public E getData() {
        return data;
        /*
        IR preservata: non modifico this
        */
    }

    public void modify(E data) throws NullPointerException{
        if(data != null)
            throw new NullPointerException("modify in MyData2: inserisci un dato valido");
        this.data = data;
        /*
        IR preservata: data != null
        */
    }
}
