package com.breezepannafoods.app.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.breezepannafoods.app.AppConstant

@Entity(tableName = AppConstant.CRASH_REPORTS)
data class CrashReportEntity(
    @PrimaryKey(autoGenerate = true) val sl_no: Int = 0,
    val timestamp: Long,
    val errorMessage: String,
    val stackTrace: String,
    val user_id: String,
    val date_time: String,
    val device : String,
    val os_version : String,
    val app_version : String,
    val user_remarks : String,
    val isUpload : Boolean
)