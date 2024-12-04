package com.breezepannafoods.app.domain

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.breezepannafoods.app.AppConstant

@Entity(tableName = AppConstant.Loan_Final_Status)
data class LoanFinalStatusEntity(
    @PrimaryKey(autoGenerate = true) var sl_no: Int = 0,
    @ColumnInfo var id:String = "",
    @ColumnInfo var name:String = ""
)


@Dao
interface LoanFinalStatusDao {
    @Insert
    fun insert(vararg obj: LoanFinalStatusEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract fun insertAll(kist: List<LoanFinalStatusEntity>)

    @Query("select * from loan_final_status")
    fun getAll():List<LoanFinalStatusEntity>

    @Query("select * from loan_final_status where id > :id")
    fun getAllFilter(id:Int):List<LoanFinalStatusEntity>

}