package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.City;

/*this is the interface of the data object access class. It is inherited by all the other class in the dao package*/
public interface DBConnection {
	public boolean insert(Object object);

	public Object load(Object object) throws SQLException;

	public void close();

}
