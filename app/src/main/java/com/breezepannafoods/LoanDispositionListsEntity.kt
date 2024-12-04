package com.breezepannafoods

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.breezepannafoods.app.AppConstant

@Entity(tableName = AppConstant.Loan_Disposition)
data class LoanDispositionEntity(
    @PrimaryKey(autoGenerate = true) var sl_no: Int = 0,
    @ColumnInfo var id:String = "",
    @ColumnInfo var name:String = ""
)


@Dao
interface LoanDispositionDao {
    @Insert
    fun insert(vararg obj: LoanDispositionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract fun insertAll(kist: List<LoanDispositionEntity>)

    @Query("select * from loan_disposition")
    fun getAll():List<LoanDispositionEntity>

}