package com.shop.bookstore.repository;

import com.shop.bookstore.model.Book;
import com.shop.bookstore.model.Language;
import com.shop.bookstore.util.IsbnGenerator;
import com.shop.bookstore.util.NumberGenerator;
import com.shop.bookstore.util.TextUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class BookRepositoryTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(BookRepository.class)
                .addClass(Book.class)
                .addClass(Language.class)
                .addClass(TextUtil.class)
                .addClass(NumberGenerator.class)
                .addClass(IsbnGenerator.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource("META-INF/test-persistence.xml", "persistence.xml");
    }
    @Inject
    private BookRepository bookRepository;

    @Test(expected = Exception.class)
    public void findWithInvalidId(){
        bookRepository.find(null);
    }
    @Test(expected = Exception.class)
    public void createInvalidBook(){
        Book book = new Book("isbn", null, 12F, 123, Language.ENGLISH, new Date(), "imageURL", "description");
        bookRepository.create(book);

    }
    @Test
    public void create() throws Exception{
        //counting book
        assertEquals(0,bookRepository.findAll().size());
        assertEquals(Long.valueOf(0), bookRepository.countAll());

        Book book = new Book("isbn", " a title", 12F, 123, Language.ENGLISH, new Date(), "imageURL", "description");
        book = bookRepository.create(book);
        Long bookId = book.getId();

        //check created book
        assertNotNull(bookId);

        //find created book
        Book foundBook = bookRepository.find(bookId);

        //check the found book
        assertEquals(" a title",foundBook.getTitle());
        assertTrue(foundBook.getIsbn().startsWith("13"));

        assertEquals(1,bookRepository.findAll().size());
        assertEquals(Long.valueOf(1), bookRepository.countAll());

        // delete a book
        bookRepository.delete(bookId);

        assertEquals(0,bookRepository.findAll().size());
        assertEquals(Long.valueOf(0), bookRepository.countAll());


    }
}
