/*
 * 
 * Copyright 2014 
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


//Configura il Context dell'apllicazione
@EnableAutoConfiguration

/*@ComponentScan istanzia automaticamente gli oggetti che vengono 
  annotati da un @Component(o un sotto tipo come:  @Repository, @Service, @Controller...)
  Dice a Spring di stare in ascolto per i Controller,etc.*/
@ComponentScan

/*Questa classe dice a Spring che contiene le informazioni
  di configurazioni per l'applicazione*/
@Configuration
public class Application {

	private static final String MAX_REQUEST_SIZE = "150MB";

	//Punto di accesso dell'applicazione
	public static void main(String[] args) {
		/* This call tells spring to launch the application and
		 use the configuration specified in LocalApplication to
		 configure the application's components.*/
		
	  /*Questa dice a spring di lanciare l'applicazione e usare la configurazione
		specificata in LocalApplication per configurare i componenti. */
		SpringApplication.run(Application.class, args);
	}

	//Questo elemento abilita Spring ad accettare richieste Multipart al Web Container
	
	@Bean
    public MultipartConfigElement multipartConfigElement() {
		
		//Imposta il contenitore dell'applicazione per accettare richieste multipart
		final MultiPartConfigFactory factory = new MultiPartConfigFactory();
		
	  /*Limiti delle dimensioni delle richieste per garantire che 
		i clienti non abusino del web container inviando enormi richieste*/
		factory.setMaxFileSize(MAX_REQUEST_SIZE);
		factory.setMaxRequestSize(MAX_REQUEST_SIZE);

		//Ritorna la configurazione per settare Multipart nel container
		return factory.createMultipartConfig();
	}

}
