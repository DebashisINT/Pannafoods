package com.breezepannafoods.app.domain

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.breezepannafoods.app.AppConstant

@Entity(tableName = AppConstant.Loan_Detail_Fetch)
data class LoanDetailFetchEntity(
    @PrimaryKey(autoGenerate = true) var sl_no: Int = 0,
    @ColumnInfo var shop_id:String = "",
    @ColumnInfo var bkt:String = "",
    @ColumnInfo var total_outstanding:String = "",
    @ColumnInfo var pos:String = "",
    @ColumnInfo var emi_amt:String = "",
    @ColumnInfo var all_charges:String = "",
    @ColumnInfo var total_Collectable:String = "",
    @ColumnInfo var risk_id:String = "",
    @ColumnInfo var risk_name:String = "",
    @ColumnInfo var workable:String = "",
    @ColumnInfo var disposition_code_id:String = "",
    @ColumnInfo var disposition_code_name:String = "",
    @ColumnInfo var ptp_Date:String = "",
    @ColumnInfo var ptp_amt:String = "",
    @ColumnInfo var collection_date:String = "",
    @ColumnInfo var collection_amount:String = "",
    @ColumnInfo var final_status_id:String = "",
    @ColumnInfo var final_status_name:String = "",
    @ColumnInfo var isUploaded:Boolean = true
)


@Dao
interface LoanDetailFetchDao {
    @Insert
    fun insert(vararg obj: LoanDetailFetchEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract fun insertAll(kist: List<LoanDetailFetchEntity>)

    @Query("delete from loan_detail_fetch")
    fun delete()

    @Query("select * from loan_detail_fetch")
    fun getAll():List<LoanDetailFetchEntity>

    @Query("select * from loan_detail_fetch where isUploaded = :isUploaded")
    fun getForUpload(isUploaded:Boolean):List<LoanDetailFetchEntity>

    @Query("update loan_detail_fetch set isUploaded = :isUploaded where shop_id=:shop_id")
    fun updateIsUploaded(isUploaded:Boolean,shop_id:String)

    @Query("select * from loan_detail_fetch where shop_id = :shop_id limit 1")
    fun getByShopID(shop_id:String):LoanDetailFetchEntity

    @Query("update loan_detail_fetch set risk_id = :risk_id,risk_name = :risk_name,isUploaded=:isUploaded \n" +
            "where shop_id = :shop_id")
    fun updateRiskByShopID(shop_id:String,risk_id:String,risk_name: String,isUploaded:Boolean)

    @Query("update loan_detail_fetch set workable = :workable,isUploaded=:isUploaded \n" +
            "where shop_id = :shop_id")
    fun updateWorkableByShopID(shop_id:String,workable:String,isUploaded:Boolean)

    @Query("update loan_detail_fetch set disposition_code_id = :disposition_code_id,disposition_code_name = :disposition_code_name,isUploaded=:isUploaded \n" +
            "where shop_id = :shop_id")
    fun updateDispByShopID(shop_id:String,disposition_code_id:String,disposition_code_name: String,isUploaded:Boolean)

    @Query("update loan_detail_fetch set final_status_id = :final_status_id,final_status_name = :final_status_name,isUploaded=:isUploaded \n" +
            "where shop_id = :shop_id")
    fun updateFinalStatusByShopID(shop_id:String,final_status_id:String,final_status_name: String,isUploaded:Boolean)

    @Query("update loan_detail_fetch set ptp_Date = :ptp_Date , isUploaded=:isUploaded \n" +
            "where shop_id = :shop_id")
    fun updatePTPDateShopID(shop_id:String,ptp_Date:String,isUploaded:Boolean)

    @Query("update loan_detail_fetch set ptp_amt = :ptp_amt , isUploaded=:isUploaded \n" +
            "where shop_id = :shop_id")
    fun updatePTPAmtShopID(shop_id:String,ptp_amt:String,isUploaded:Boolean)

    @Query("update loan_detail_fetch set collection_date = :collection_date , isUploaded=:isUploaded \n" +
            "where shop_id = :shop_id")
    fun updateCollectionDateShopID(shop_id:String,collection_date:String,isUploaded:Boolean)

    @Query("update loan_detail_fetch set collection_amount = :collection_amount , isUploaded=:isUploaded \n" +
            "where shop_id = :shop_id")
    fun updateCollectionAmtShopID(shop_id:String,collection_amount:String,isUploaded:Boolean)
}