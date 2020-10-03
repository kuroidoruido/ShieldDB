package nf.fr.k49.shielddb.demo.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import nf.fr.k49.shielddb.core.ShieldDB;
import nf.fr.k49.shielddb.core.shield.AtomicShield;
import nf.fr.k49.shielddb.core.shield.InMemoryArrayListReadCacheShield;
import nf.fr.k49.shielddb.core.storage.FileStorage;
import nf.fr.k49.shielddb.demo.app.model1.User;
import nf.fr.k49.shielddb.demo.app.model1.UserDeserializer;
import nf.fr.k49.shielddb.gson.GsonTypeUtils;
import nf.fr.k49.shielddb.gson.ShieldDBGson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

	private static final String BASE_DIR = "target/manual";

	private static final User USER_EMPTY = new User();
	private static final User USER_STAN_LEE = new User("Stan", "Lee", 95, LocalDate.of(1922, 12, 28));

	private static void setup() throws IOException {
		Path path = Paths.get(BASE_DIR);
		if (Files.exists(path)) {
			Files.list(path).forEach(t -> {
				try {
					Files.delete(t);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			Files.delete(path);
		}
	}

	private static void model1UserSimpleExample() throws IOException {
		final String storagePath = BASE_DIR + "/User.json";
		System.out.println(Paths.get(storagePath).toAbsolutePath());

		final List<User> userDb = ShieldDB.<User>builder()//
				.mapper(new ShieldDBGson<User>())//
				.storage(new FileStorage(storagePath))//
				.build();

		System.out.println(userDb.size() + " -> " + userDb.toString());

		userDb.add(USER_EMPTY);
		System.out.println(userDb.size() + " -> " + userDb.toString());

		userDb.add(USER_STAN_LEE);
		System.out.println(userDb.size() + " -> " + userDb.toString());
	}

	private static void model1UserInMemoryExample() throws IOException {
		final String storagePath = BASE_DIR + "/User.json";
		System.out.println(Paths.get(storagePath).toAbsolutePath());

		final List<User> userDb = ShieldDB.<User>builder()//
				.shield(new InMemoryArrayListReadCacheShield<>()).mapper(new ShieldDBGson<User>())//
				.storage(new FileStorage(storagePath))//
				.build();

		System.out.println(userDb.size() + " -> " + userDb.toString());

		userDb.add(USER_EMPTY);
		System.out.println(userDb.size() + " -> " + userDb.toString());

		userDb.add(USER_STAN_LEE);
		System.out.println(userDb.size() + " -> " + userDb.toString());
	}

	private static void model1UserWithDeserializer() throws IOException {
		final String storagePath = BASE_DIR + "/User2.json";
		System.out.println(Paths.get(storagePath).toAbsolutePath());

		Type type = GsonTypeUtils.getType();
		Gson gson = new GsonBuilder()//
				.setPrettyPrinting()//
				.registerTypeAdapter(type, new UserDeserializer())//
				.create();

		final List<User> userDb = ShieldDB.<User>builder()//
				.mapper(new ShieldDBGson<User>(gson, type))//
				.storage(new FileStorage(storagePath))//
				.build();

		System.out.println(userDb.size() + " -> " + userDb.toString());

		userDb.add(USER_EMPTY);
		System.out.println(userDb.size() + " -> " + userDb.toString());

		userDb.add(USER_STAN_LEE);
		System.out.println(userDb.size() + " -> " + userDb.toString());
	}

	private static void model1UserAtomic() throws IOException, InterruptedException {
		final String storagePath = BASE_DIR + "/User3.json";
		System.out.println(Paths.get(storagePath).toAbsolutePath());

		Type type = GsonTypeUtils.getType();
		Gson gson = new GsonBuilder()//
				.setPrettyPrinting()//
				.registerTypeAdapter(type, new UserDeserializer())//
				.create();

		final List<User> userDb = ShieldDB.<User>builder()//
				.shield(new AtomicShield<>())//
				.mapper(new ShieldDBGson<User>(gson, type))//
				.storage(new FileStorage(storagePath))//
				.build();

		System.out.println(userDb.size() + " -> " + userDb.toString());

		ExecutorService executor = Executors.newFixedThreadPool(10);
		List<Callable<Boolean>> tasks = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			tasks.add(() -> userDb.add(USER_EMPTY));
			tasks.add(() -> userDb.add(USER_STAN_LEE));
			tasks.add(() -> userDb.add(USER_STAN_LEE));
			tasks.add(() -> userDb.remove(1) != null);
			tasks.add(() -> userDb.removeIf(u -> u.equals(USER_EMPTY)));
		}
		executor.invokeAll(tasks);
		Thread.sleep(5000);
		executor.shutdownNow();
		System.out.println(userDb.size() + " -> " + userDb.toString());
	}






	public static void main(String[] args) throws IOException, InterruptedException {
		setup();

		model1UserSimpleExample();
		System.out.println("-----------------------------------");
		model1UserInMemoryExample();
		System.out.println("-----------------------------------");
		model1UserWithDeserializer();
		System.out.println("-----------------------------------");
		model1UserAtomic();
		System.out.println("-----------------------------------");
	}
}
