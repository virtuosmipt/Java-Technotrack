package session;

import java.util.ArrayList;

/**
 * Created by a.borodin on 18.10.2015.
 */
public interface IMessageStore {
    public ArrayList<String > userMessageStore = new ArrayList<>();

    void writeMessageInFile();   //записывает сообщени€ в файл

    void readMessageFromFile();  //—читывает сообщени€ с файла


}
