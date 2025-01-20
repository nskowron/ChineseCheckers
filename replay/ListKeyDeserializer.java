package replay;

import com.fasterxml.jackson.databind.KeyDeserializer;
import java.io.IOException;
import java.util.Arrays;

public class ListKeyDeserializer extends KeyDeserializer 
{
    @Override
    public Object deserializeKey(String key, com.fasterxml.jackson.databind.DeserializationContext context) throws IOException {
        // Remove brackets and split the string into integers
        String[] parts = key.replace("[", "").replace("]", "").split(",\\s*");
        return Arrays.stream(parts).map(Integer::parseInt).toList();
    }
}

