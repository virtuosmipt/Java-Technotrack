package message;

/**
 *
 */
public interface Protocol {

    byte[] encode(Message msg);

    Message decode(byte[] data);
}
