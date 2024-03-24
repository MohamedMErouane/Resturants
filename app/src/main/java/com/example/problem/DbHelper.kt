import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context?) :
    SQLiteOpenHelper(context, "MYDB", null, 1) {

    override fun onCreate(MyDB: SQLiteDatabase) {
        MyDB.execSQL("create table user(username TEXT primary key , password TEXT)")

        InsertData(MyDB, "Mohamed", "2004")
    }

    fun InsertData(db: SQLiteDatabase, username: String, password: String): Boolean {
        val contentvalue = ContentValues().apply {
            put("username", username)
            put("password", password)
        }
        val result = db.insert("user", null, contentvalue)
        return result != -1L
    }

    fun InsertData(username: String?, password: String?): Boolean {
        val DB = this.writableDatabase
        val contentvalue = ContentValues().apply {
            put("username", username)
            put("password", password)
        }
        val result = DB.insert("user", null, contentvalue)
        return result != -1L
    }

    override fun onUpgrade(MyDB: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        MyDB.execSQL("drop table if exists user")
        onCreate(MyDB) // Recreate the table and insert default data
    }

    fun CheckUserName(username: String): Boolean {
        val DB = this.writableDatabase
        val cursor = DB.rawQuery("select * from user where username= ?", arrayOf(username))
        return cursor.count > 0
    }

    fun CheckUserNamePassword(username: String, password: String): Boolean {
        val DB = this.writableDatabase
        val cursor = DB.rawQuery(
            "select * from user where username= ? and password= ?",
            arrayOf(username, password)
        )
        return cursor.count > 0
    }

    companion object {
        const val DBNAME = "MYDB"
    }
}
