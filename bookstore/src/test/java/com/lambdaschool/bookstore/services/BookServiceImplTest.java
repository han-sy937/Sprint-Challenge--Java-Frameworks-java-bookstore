package com.lambdaschool.bookstore.services;

import com.lambdaschool.bookstore.BookstoreApplication;
import com.lambdaschool.bookstore.exceptions.ResourceNotFoundException;
import com.lambdaschool.bookstore.models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookstoreApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//**********
// Note security is handled at the controller, hence we do not need to worry about security here!
//**********
public class BookServiceImplTest
{

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private SectionService sectionService;

    @Before
    public void setUp() throws
            Exception
    {
        MockitoAnnotations.initMocks(this);

//        List<Book> mybooks = bookService.findAll();
//        for (Book b : mybooks) {
//            System.out.println(b.getBookid() + " " + b.getTitle());
//        }
    }

    @After
    public void tearDown() throws
            Exception
    {
    }

    @Test
    public void a_findAll()
    {
        assertEquals(5, bookService.findAll().size());
    }

    @Test
    public void b_findBookById()
    {
        assertEquals("Flatterland", bookService.findBookById(26).getTitle());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void ba_notFindBookById()
    {
        assertEquals("Flatterland", bookService.findBookById(31).getTitle());
    }

    @Test
    public void e_delete()
    {
        bookService.delete(30);
        assertEquals(5, bookService.findAll().size());
    }

    @Test
    public void d_save()
    {
        Author a1 = new Author("Jane", "Austen");
        a1 = authorService.save(a1);

        Section s1 = new Section("Fiction");
        s1 = sectionService.save(s1);

        Book b1 = new Book("Pride and Prejudice", "9780738206752", 2004, s1);
        b1.getWrotes().add(new Wrote(a1, new Book()));
        b1= bookService.save(b1);

        assertNotNull(b1);
        assertEquals("Pride and Prejudice", b1.getTitle());

    }

    @Test
    public void c_update()
    {
        Author a1 = new Author("Andrzej", "Sapkowski");
        a1 = authorService.save(a1);

        Section s1 = new Section("Fiction");
        s1 = sectionService.save(s1);

        Book b1 = new Book("The Witcher", "9780738206752", 2004, s1);
        b1.setBookid(30);
        b1.getWrotes().add(new Wrote(a1, new Book()));
        b1 = bookService.update(b1, 30);

        assertEquals("The Witcher", b1.getTitle());
    }

    @Test
    public void f_deleteAll()
    {
        bookService.deleteAll();
        assertEquals(0, bookService.findAll().size());
    }
}