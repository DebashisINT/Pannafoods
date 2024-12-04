package com.breezepannafoods.app.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.breezepannafoods.app.AppConstant

@Dao
interface CrashReportDao {
    @Insert
    fun insertCrashReport(crashReport: CrashReportEntity)

    @Query("SELECT * FROM crash_reports ORDER BY timestamp DESC")
    fun getAllCrashReports(): List<CrashReportEntity>

    @Query("delete FROM crash_reports WHERE sl_no NOT IN(SELECT MAX(sl_no) FROM crash_reports GROUP BY errorMessage)AND DATE(crash_reports.date_time) =:currentdate ")
    fun deletemultipleSameCrashReports(currentdate: String)

    @Query("update crash_reports set user_remarks =:user_remarks WHERE sl_no=(select max(sl_no) from crash_reports)")
    fun updateCrashReports(user_remarks: String)

    @Query("update crash_reports set isUpload =:isUpload WHERE timestamp=:timestamp")
    fun updateIsUploadCrashReports(isUpload: Boolean , timestamp:Long)

    @Query("select * from crash_reports order by sl_no DESC limit 1")
    fun getLastCrashReport(): CrashReportEntity

    @Query("select * from crash_reports where isUpload=:isUpload")
    fun getCrashSyncData(isUpload: Boolean): List<CrashReportEntity>


}