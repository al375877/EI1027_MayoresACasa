package es.uji.ei1027.Mayorescasa;

import java.util.logging.Logger;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class MayorescasaApplication {

	private static final Logger log = Logger.getLogger(MayorescasaApplication.class.getName());

	public static void main(String[] args) {
		new SpringApplicationBuilder(MayorescasaApplication.class).run(args);
	}
}