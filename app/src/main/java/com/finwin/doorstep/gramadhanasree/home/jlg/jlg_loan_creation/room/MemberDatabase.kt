package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Member::class), version = 1, exportSchema = false)
abstract class MemberDatabase : RoomDatabase() {

    abstract fun memberDao(): MemberDao
    companion object {
        @Volatile
        private var INSTANCE: MemberDatabase? = null

        fun getDataBase(context: Context, scope: CoroutineScope) : MemberDatabase
        {
            return INSTANCE ?: synchronized(this)
            {
                val instance= Room.databaseBuilder(context.applicationContext,
                    MemberDatabase::class.java,"member_database")
                    .addCallback(MemberDatabaseCallBack(scope))
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

    private class MemberDatabaseCallBack(val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE.let { database ->
                scope.launch {
                    if (database != null) {
                        populateDatabase(database.memberDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(memberDao: MemberDao) {

//            memberDao.deleteAll()
//
//            var member = Member("1","test","test","","","","","","",
//                "","","","","")
//            memberDao.insert(member)

        }
    }

}