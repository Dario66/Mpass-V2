/*
 * 
 * Copyright 2014 Jules White
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

//LANCIARE L'APPLICAZIONE CLICCANDO CON IL TASTO DESTRO SU
//'Application'->'RunAs'->'JavaApplication'.


//QUESTO PROGETTO è UN CAMPIONE , FARNE UNA COPIA E IMPORTARLO TRAMITE 
//GRADLE PROJECT IN ECLIPSE.




package it.SmartCity.Cesena;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.MultiPartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@EnableAutoConfiguration

@ComponentScan

@Configuration
public class Application {

	private static final String MAX_REQUEST_SIZE = "150MB";

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	
	@Bean
    public MultipartConfigElement multipartConfigElement() {
		final MultiPartConfigFactory factory = new MultiPartConfigFactory();
		factory.setMaxFileSize(MAX_REQUEST_SIZE);
		factory.setMaxRequestSize(MAX_REQUEST_SIZE);
		return factory.createMultipartConfig();
	}

}
