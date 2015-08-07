package com.diego.lms.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.diego.lms.domain.Author;

public class AuthorDAO extends BaseDAO<Author> implements Serializable,
		ResultSetExtractor<List<Author>> {

	private static final String AUTH_COLLECTION = "Authors";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1619700647002508164L;

	public void addAuthor(Author author) throws SQLException {
		author.setAuthorId(getNextSequenceId(AUTH_COLLECTION));
		mongoOps.insert(author, AUTH_COLLECTION);
	}

	public void updateAuthor(Author author) throws SQLException {
		template.update(
				"update tbl_author set authorName = ? where authorId = ?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });
	}

	public void removeAuthor(Author author) throws SQLException {
		template.update("delete from tbl_author where authorId=?",
				new Object[] { author.getAuthorId() });
	}

	public List<Author> readAll() throws SQLException {
        return this.mongoOps.findAll(Author.class, AUTH_COLLECTION);
	}

	public List<Author> readAllSQL() throws SQLException {
		return (List<Author>) template.query("select * from tbl_author", this);
	}

	public Author readOne(long authorId) {
        Query query = new Query(Criteria.where("_id").is(authorId));
        return this.mongoOps.findOne(query, Author.class, AUTH_COLLECTION);
	}

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<Author>();
		while (rs.next()) {
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			authors.add(a);
		}
		return authors;
	}
}
