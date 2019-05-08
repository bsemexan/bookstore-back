package com.shop.bookstore.rest;

import com.shop.bookstore.model.Book;
import com.shop.bookstore.model.Language;
import com.shop.bookstore.repository.BookRepository;
import com.shop.bookstore.util.IsbnGenerator;
import com.shop.bookstore.util.NumberGenerator;
import com.shop.bookstore.util.TextUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import java.util.Date;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@RunAsClient
public class BookEndpointTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(BookRepository.class)
                .addClass(Book.class)
                .addClass(Language.class)
                .addClass(TextUtil.class)
                .addClass(NumberGenerator.class)
                .addClass(IsbnGenerator.class)
                .addClass(JAXRSConfiguration.class)
                .addClass(BookEndpoint.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource("META-INF/test-persistence.xml", "persistence.xml");
    }

    @Test
    public void createBook(@ArquillianResteasyResource("api/books") WebTarget webTarget){

        //counting books test
        Response response = webTarget.path("count").request().get();
        assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());

        //test find all
        response = webTarget.request(APPLICATION_JSON).get();
        assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());

        //create a book
        Book book = new Book("isbn", "a   title", 12F, 123, Language.ENGLISH, "imageURL", "description");
        response = webTarget.request(APPLICATION_JSON).post(Entity.entity(book, APPLICATION_JSON));
        assertEquals(CREATED.getStatusCode(), response.getStatus());
    }
}
