package com.breezepannafoods.app.domain

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.breezepannafoods.app.AppConstant

@Entity(tableName = AppConstant.LOAN_RISK)
data class LoanRiskTypeEntity(
    @PrimaryKey(autoGenerate = true) var sl_no: Int = 0,
    @ColumnInfo var id:String = "",
    @ColumnInfo var name:String = ""
)


@Dao
interface LoanRiskTypeDao {
    @Insert
    fun insert(vararg obj: LoanRiskTypeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract fun insertAll(kist: List<LoanRiskTypeEntity>)

    @Query("select * from loan_risk")
    fun getAll():List<LoanRiskTypeEntity>

}