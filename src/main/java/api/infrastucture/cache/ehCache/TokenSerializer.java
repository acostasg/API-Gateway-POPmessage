package api.infrastucture.cache.ehCache;

import api.domain.entity.User;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.io.ByteBufferInputStream;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.ehcache.spi.serialization.Serializer;
import org.ehcache.spi.serialization.SerializerException;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class TokenSerializer implements Serializer<User>, Closeable {

    private static final Kryo kryo = new Kryo();

    private Map<Class, Integer> objectHeaderMap = new HashMap<Class, Integer>();

    public TokenSerializer() {
    }

    public TokenSerializer(ClassLoader loader) {
        populateObjectHeadersMap(kryo.register(User.class));
    }

    private void populateObjectHeadersMap(Registration reg) {
        objectHeaderMap.put(reg.getType(), reg.getId());
    }

    @Override
    public ByteBuffer serialize(User object) throws SerializerException {
        Output output = new Output(new ByteArrayOutputStream());
        kryo.writeObject(output, object);
        output.close();

        return ByteBuffer.wrap(output.getBuffer());
    }

    @Override
    public User read(final ByteBuffer binary) throws ClassNotFoundException, SerializerException {
        Input input = new Input(new ByteBufferInputStream(binary));
        return kryo.readObject(input, User.class);
    }

    @Override
    public boolean equals(final User object, final ByteBuffer binary) throws ClassNotFoundException, SerializerException {
        return object.equals(read(binary));
    }

    @Override
    public void close() throws IOException {
        objectHeaderMap.clear();
    }
}