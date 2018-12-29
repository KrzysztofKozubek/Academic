package networc;

import java.io.Serializable;

public final class Message implements Serializable {
    public int number;
    Message(int number) {
        this.number = number;
    }
}