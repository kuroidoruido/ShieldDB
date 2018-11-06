package nf.fr.k49.shielddb.demo.app.model1;

import java.time.LocalDate;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import nf.fr.k49.shielddb.gson.JsonListDeserializer;

public class UserDeserializer implements JsonListDeserializer<User> {

	@Override
	public User deserializeOne(JsonElement json, JsonDeserializationContext context) throws JsonParseException {
		final JsonObject jsonO = json.getAsJsonObject();
		final User res = new User();

		if (jsonO.get("firstname") != null) {
			res.setFirstname(jsonO.get("firstname").getAsString());
		}
		if (jsonO.get("lastname") != null) {
			res.setLastname(jsonO.get("lastname").getAsString());
		}
		if (jsonO.get("age") != null) {
			res.setAge(jsonO.get("age").getAsInt());
		}
		if (jsonO.get("birthDate") != null) {
			final JsonObject d = jsonO.get("birthDate").getAsJsonObject();
			res.setBirthDate(LocalDate.of(//
					d.get("year").getAsInt(), //
					d.get("month").getAsInt(), //
					d.get("day").getAsInt()));
		}
		return res;
	}

}
