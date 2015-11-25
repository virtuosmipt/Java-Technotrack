package ru.mail.track.net;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.codehaus.jackson.JsonGenerationException;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import ru.mail.track.message.Message;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;




/**
 * Created by a.borodin on 20.11.2015.
 */
public class JsonProtocol implements Protocol {

    @Override
    public byte[] encode(Message msg) {
        ObjectMapper mapper = new ObjectMapper();
                try {
                    ObjectMapper jsonMapper = new ObjectMapper();

                        return jsonMapper.writeValueAsBytes(msg);
                    }  catch (JsonMappingException e) {
                       e.printStackTrace();
                } catch (IOException e) {
                        e.printStackTrace();
                    }
               return null;
    }

    @Override
    public Message decode(byte[] bytes)  {
        ObjectMapper jsonMapper = new ObjectMapper();
                try {
                        //String json = new String(bytes, "UTF-8");
                    Message msg = jsonMapper.readValue(bytes, Message.class);
                    return msg;
                    }  catch (JsonGenerationException e) {
                        e.printStackTrace();
                    } catch (JsonMappingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
               return null;
    }
}
