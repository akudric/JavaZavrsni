package hr.java.zavrsni.entities.fileInteractor;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
public class AppendableObjectInputStream extends ObjectInputStream {

    public AppendableObjectInputStream(InputStream in) throws IOException {
        super(in);
    }

    @Override
    protected void readStreamHeader() throws IOException {
    }



}
