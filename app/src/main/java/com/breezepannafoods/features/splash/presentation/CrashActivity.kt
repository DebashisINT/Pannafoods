package com.breezepannafoods.features.splash.presentation

import android.content.Intent
import android.os.Build
import com.breezepannafoods.R
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.breezepannafoods.app.AppDatabase
import com.breezepannafoods.app.NetworkConstant
import com.breezepannafoods.app.Pref
import com.breezepannafoods.app.domain.CrashReportEntity
import com.breezepannafoods.base.BaseResponse
import com.breezepannafoods.base.presentation.BaseActivity
import com.breezepannafoods.features.NewQuotation.Mail
import com.breezepannafoods.features.addshop.presentation.Crash_Report_Save
import com.breezepannafoods.features.addshop.presentation.Crash_Report_Save_Data
import com.breezepannafoods.features.dashboard.presentation.DashboardActivity
import com.breezepannafoods.features.mylearning.apiCall.LMSRepoProvider
import com.google.android.gms.security.ProviderInstaller
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.File
import java.util.Properties
import javax.activation.DataHandler
import javax.activation.FileDataSource
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart


class CrashActivity : AppCompatActivity() {

    var mCrash_Report_Save_Data :ArrayList<Crash_Report_Save_Data> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val crashMessage = intent.getStringExtra("crash_message") ?: "An unexpected error occurred."

        // Inflate the custom view for the dialog
        val dialogView = layoutInflater.inflate(R.layout.custom_crash_dialog, null)
        val editTextAdditionalInfo = dialogView.findViewById<EditText>(R.id.editTextAdditionalInfo)

        // Show the custom dialog with "OK" and "Send" buttons
        AlertDialog.Builder(this)
            .setTitle("Application Crash")
            .setMessage(crashMessage)
            .setView(dialogView)
            .setCancelable(false)
            .setPositiveButton("Send Crash Report") { _, _ ->
                val userRemarks = editTextAdditionalInfo.text.toString()
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        AppDatabase.getDBInstance()!!.crashReportDao().updateCrashReports(userRemarks)

                        saveCrashReportAPICalling()

                        sendMailError(crashMessage)
                        Log.d("CustomExceptionHandler", "Crash report saved to database.")
                    } catch (e: Exception) {
                        Log.e("CustomExceptionHandler", "Failed to save crash report: ${e.message}")
                    }
                }

                sendUserRemarks(userRemarks)
                //Toast.makeText(this, "Crash report sent successfully.", Toast.LENGTH_SHORT).show()
                // Implement the API call to send the report
                finish() // Close the app after sending the report
            }
            .setNegativeButton("Cancel") { _, _ ->
                //Toast.makeText(this, "Crash dialog dismissed.", Toast.LENGTH_SHORT).show()
                finish() // Close the app on "OK"
            }
            .show()


    }

    private fun saveCrashReportAPICalling() {

        try {

            var obj = AppDatabase.getDBInstance()!!.crashReportDao().getLastCrashReport()

             var mCrash_Report_Save_Data = Crash_Report_Save_Data()
            mCrash_Report_Save_Data.errorMessage = obj.errorMessage.trim()
            mCrash_Report_Save_Data.stackTrace = obj.stackTrace.trim()
            mCrash_Report_Save_Data.date_time = obj.date_time
            mCrash_Report_Save_Data.device = obj.device
            mCrash_Report_Save_Data.os_version = obj.os_version
            mCrash_Report_Save_Data.app_version = obj.app_version

            if (!obj.user_remarks.trim().equals("")){
                mCrash_Report_Save_Data.user_remarks = obj.user_remarks.trim()
            }
            else{
                mCrash_Report_Save_Data.user_remarks = ""
            }

            var mCrash_Report_Save = Crash_Report_Save()
            mCrash_Report_Save.user_id = Pref.user_id!!
            mCrash_Report_Save.crash_report_save_list.add(mCrash_Report_Save_Data)

            var a = 11

            val repository = LMSRepoProvider.getTopicList()
            BaseActivity.compositeDisposable.add(
                repository.saveCrashReportToServer(mCrash_Report_Save)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ result ->
                        val response = result as BaseResponse
                        try {
                            if (response.status == NetworkConstant.SUCCESS) {
                                AppDatabase.getDBInstance()!!.crashReportDao().updateIsUploadCrashReports(true,obj.timestamp)
                               // Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
                            }else{

                            }
                        } catch (e: Exception) {

                        }
                    }, { error ->
                        (this as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong))
                    })
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            (this as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong))
        }
    }

    private fun sendMailError(crashMessage: String) {
        var m = Mail()
        var toArr = arrayOf("")
        doAsync {
            try{
                if(true){
                    var emailRecpL = "suman.bachar@intglobal.com".split(",")
                    m = Mail("puja.basak@intglobal.com", "klxgduyqotbtekjy")
                    toArr = Array<String>(emailRecpL.size){""}
                    for(i in 0..emailRecpL.size-1){
                        toArr[i]=emailRecpL[i]
                    }
                    m.setTo(toArr)
                    m.setFrom("TEAM");
                    m.setSubject("Application Crash Report")
                    m.setBody("Hello Team,  \n $crashMessage \n\n\n Regards \n${Pref.user_name}.")
                    //m.send()
                    val props = Properties().apply {
                        put("mail.smtp.host", "smtp.gmail.com")  // SMTP server host
                        put("mail.smtp.port", "587")                      // SMTP port
                        put("mail.smtp.auth", "true")
                        put("mail.smtp.starttls.enable", "true")          // Enable STARTTLS
                        put("mail.smtp.ssl.protocols", "TLSv1.2")         // Specify TLS version
                        put("mail.smtp.ssl.trust", "smtp.gmail.com")  // Trust the server
                    }
                    if (Build.VERSION.SDK_INT < 21) {
                        try {
                            ProviderInstaller.installIfNeeded(this@CrashActivity)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    val session = Session.getInstance(props, object : Authenticator() {
                        override fun getPasswordAuthentication(): PasswordAuthentication {
                            return PasswordAuthentication("puja.basak@intglobal.com", "klxgduyqotbtekjy")
                        }
                    })
                    try {
                        val message = MimeMessage(session).apply {
                            setFrom(InternetAddress("puja.basak@intglobal.com"))            // Sender's email
                            setRecipients(Message.RecipientType.TO, InternetAddress.parse("suman.bachar@intglobal.com"))  // Recipient's email
                            subject = "Application Crash Report"
                            // Email subject
                        }
                        val textPart = MimeBodyPart().apply {
                            setText("Hello Team,  \n $crashMessage \n\n\n Regards \n${Pref.user_name}.")
                        }

                        val multipart = MimeMultipart().apply {
                            addBodyPart(textPart)        // Add the text part
                        }
                        message.setContent(multipart)
                        Transport.send(message)
                        println("Email sent successfully!")
                    } catch (e: MessagingException) {
                        e.printStackTrace()
                        println("tag_mail_ex2 ${e.printStackTrace()}")
                    }
                }
            }catch (ex:Exception){
                ex.printStackTrace()
                println("tag_mail_ex1 ${ex.printStackTrace()}")
            }
            uiThread {
                //Toast.makeText(this@DashboardActivity, "Crash report sent successfully.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendUserRemarks(userRemarks: String) {
        // Intent to restart the app or send data to handler
        val intent = Intent().apply {
            putExtra("user_remarks", userRemarks)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun restartApp() {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}
