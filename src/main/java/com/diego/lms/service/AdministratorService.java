package com.diego.lms.service;

import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diego.lms.dao.AuthorDAO;
import com.diego.lms.dao.BookDAO;
import com.diego.lms.dao.PublisherDAO;
import com.diego.lms.domain.Author;
import com.diego.lms.domain.Book;
import com.diego.lms.domain.Publisher;

@Service
public class AdministratorService {

	@Autowired
	private BasicDataSource ds;

	@Autowired
	private AuthorDAO authorDAO;

	@Autowired
	private PublisherDAO pubDAO;

	@Autowired
	private BookDAO bookDAO;

	@Transactional
	public void addAuthor(Author author) throws Exception {
		authorDAO.addAuthor(author);
	}

	@Transactional
	public void deleteAuthor(Author author) throws Exception {
		authorDAO.removeAuthor(author);
	}

	@Transactional
	public void addPublisher(Publisher p) throws Exception {
		pubDAO.addPublisher(p);
	}

	public List<Author> getAuthors(int pageNo, int pageSize) throws Exception {
		AuthorDAO aDAO = authorDAO;
		aDAO.setPageNo(pageNo);
		aDAO.setPageSize(pageSize);
		return aDAO.readAll();
	}

	public List<Book> getBooks() throws Exception {
		return bookDAO.readAll(1, 10);
	}

	public int getBooksCount() throws Exception {
		return bookDAO.readAllCount();
	}

	public int searchBooksCount(String searchString) throws Exception {
		return bookDAO.searchBookByTitleCount(searchString);
	}

	public List<Publisher> getPublishers() throws Exception {
		return pubDAO.readAll();
	}

	public Author getAuthor(int authorId) throws Exception {
		return null;//authorDAO.readOne(authorId);
	}

	@Transactional
	public void editAuthor(Author author) throws Exception {
		authorDAO.updateAuthor(author);
	}

	@Transactional
	public void addBook(Book b) throws Exception {
		bookDAO.addBook(b);
	}

	public List<Book> searchBooks(String searchString, int pageNo, int pageSize)
			throws Exception {
		return bookDAO.searchBookByTitle(searchString, pageNo, pageSize);
	}

}
