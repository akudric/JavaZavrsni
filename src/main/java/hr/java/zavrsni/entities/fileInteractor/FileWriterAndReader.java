package hr.java.zavrsni.entities.fileInteractor;

import hr.java.zavrsni.entities.UserRelated.Changes;
import hr.java.zavrsni.entities.UserRelated.ListOfChanges;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileWriterAndReader <T>{

    public FileWriterAndReader() {

    }
    public void writeToFile(T t) throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream("tempBinaryFile.dat");

        ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);

        out.writeObject(t);

        out.flush();

        out.close();
        fileOutputStream.close();
    }

    public T readFromFile() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("tempBinaryFile.dat");

        ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);

        T t = (T) inputStream.readObject();

        inputStream.close();

        fileInputStream.close();

        return t;
    }
    public ListOfChanges readChanges() throws IOException {

        ListOfChanges returnValues = new ListOfChanges();

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("changes.dat"));

            returnValues = (ListOfChanges) ois.readObject();
        }catch (EOFException e){return returnValues;} catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return returnValues;
    }
    public void writeChanges(T t) throws IOException {

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("changes.dat"));

            oos.writeObject(t);

            oos.close();


    }
}
