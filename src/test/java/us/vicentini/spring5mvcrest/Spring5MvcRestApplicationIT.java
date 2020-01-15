package us.vicentini.spring5mvcrest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class Spring5MvcRestApplicationIT {

	@Test
	public void contextLoads() {
		log.info("test spring context");
	}

}
