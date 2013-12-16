package ws;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
// import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;

class DatabaseHelper extends SQLiteOpenHelper {
	
	private static final String LogTag = new String("DatabaseHelper");
	private static String CREATE_TABLE = new String();
	
	private String db_name = new String();
	
	// DatabaseHelper Constructor calls the superclass Constructor to create a database
	public DatabaseHelper(Context context, String name, int version) {
		super(context, name, null, version);
		// TODO Auto-generated constructor stub
		Log.i(LogTag, "super class SQLiteOpenHelper constructor invoked!");
		db_name = name;
	}
	
	// Sets the SQL query used to create a new Table
	public void prepareTable(String createString) {
		CREATE_TABLE = createString;
	}
	
	public void openDatabase() {
		getWritableDatabase();
		
		SQLiteDatabase checkDB = null;
		
		try {
			Log.i(LogTag, "Checking if database " + db_name + " exists after getWritable");
			checkDB = SQLiteDatabase.openDatabase(db_name, null,
					SQLiteDatabase.OPEN_READONLY);
			
			checkDB.close();
		
		} catch (SQLiteException e) {
			
			Log.i(LogTag, "Database " + db_name + " does not exist after getWriteable");
			checkDB = null;
		}
	}
	
	public void closeDatabase() {
		close();
	}
	
	// Checks if the Log Database has been created on the android device
	protected static boolean checkLogDatabase(String db_name) {
		
		SQLiteDatabase checkDB = null;
		
		try {
			Log.i(LogTag, "Checking if database " + db_name + " exists");
			checkDB = SQLiteDatabase.openDatabase(db_name, null,
					SQLiteDatabase.OPEN_READONLY);
			
			checkDB.close();
		
		} catch (SQLiteException e) {
			
			Log.i(LogTag, "Database " + db_name + " does not exist");
			checkDB = null;
		}
		
		return checkDB != null ? true : false;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.i(LogTag, "onCreate Method invoked!");
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
}
