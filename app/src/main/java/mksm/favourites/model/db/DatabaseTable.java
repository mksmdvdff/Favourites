package mksm.favourites.model.db;

import java.util.Map;

/**
 * Created by mksm on 06.08.2016.
 */
public interface DatabaseTable {
	Map<String, String> getColumns();

	String getTableName();
}
