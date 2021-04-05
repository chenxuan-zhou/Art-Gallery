package ca.mcgill.ecse321.artgallery;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;


@SpringBootTest

/*
 *  Without this annotation, test cannot pass: URL must start with jdbc
 *  
 */
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})

class ArtGalleryApplicationTests {
	
	@Test
	void contextLoads() {
	}

}
