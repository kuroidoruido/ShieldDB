package nf.fr.k49.sheilddb.gson;


import com.google.gson.*;
import nf.fr.k49.shielddb.gson.ShieldDBGson;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;

public class SheildDBGsonTest {

    static ShieldDBGson<User> shieldDBGson;

    @BeforeAll
    public static void setup(){

    }

    @Test
    public void testDefaultStorageInstantiation(){
        assertDoesNotThrow(()->new ShieldDBGson<User>()
                ,"must be able to be instantiated with default constructor");
    }

    @Test
    public void testStorageInstantiationWithGson(){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(User.class,new UserDeserializer())
                .create();
        shieldDBGson = new ShieldDBGson<User>(gson);
        assertDoesNotThrow(()->shieldDBGson
                ,"must be able to be instantiated with default constructor");

        assertEquals("[{\"name\":\"shieldDB\"}]",shieldDBGson.toJson
                (Arrays.asList(new User("shieldDB"))));

    }

    @Test
    public void testStorageInstantiationWithGsonAndType(){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(User.class,new UserDeserializer())
                .create();
        ShieldDBGson shieldDBGson = new ShieldDBGson<User>(gson,User.class);
        assertDoesNotThrow(()-> shieldDBGson
                ,"must be able to be instantiated with default constructor");

        assertEquals("[{\"name\":\"shieldDB\"}]",shieldDBGson.toJson
                (Arrays.asList(new User("shieldDB"))));
    }




}

class UserDeserializer implements JsonDeserializer<User>{

    @Override
    public User deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        return new User(name);
    }
}

class User{

    private String name;

    User(){}

    User(String name){
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
