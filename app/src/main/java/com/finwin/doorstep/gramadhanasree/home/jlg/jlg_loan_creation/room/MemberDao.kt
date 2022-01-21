package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.room

import androidx.room.*
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.room.Member
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(member: Member)

    @Query("delete from member_table")
    suspend fun deleteAll()

    @Query("select * from member_table")
    fun getAllMembers(): Flow<List<Member>>

   // @Query("select case when `Disbursement Amount`='' then '' else `Disbursement Amount` end  from member_table")
    @Query("select * from member_table")
    suspend fun getDisbursementAmount(): List<Member>

    @Query("select * from member_table where Slno=9 ")
    fun getMember(): Flow<Member>

    @Update()
    suspend fun updateMember(member: Member)

}