package com.breezepannafoods.features.performanceAPP

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.breezepannafoods.R
import com.breezepannafoods.app.Pref
import com.breezepannafoods.features.contacts.TargetAcvhDtls
import com.breezepannafoods.features.contacts.TargetAcvhResponse
import kotlinx.android.synthetic.main.row_ta_dtls.view.tv_ta_dtls_date
import kotlinx.android.synthetic.main.row_ta_dtls.view.tv_ta_dtls_name
import kotlinx.android.synthetic.main.row_ta_dtls.view.tv_ta_dtls_value

class AdapterTADtls(var mContext:Context,var mList:ArrayList<TargetAcvhDtls>):RecyclerView.Adapter<AdapterTADtls.TargetAcvhViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TargetAcvhViewHolder {
        var v = LayoutInflater.from(mContext).inflate(R.layout.row_ta_dtls,parent,false)
        return TargetAcvhViewHolder(v)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: TargetAcvhViewHolder, position: Int) {
        holder.bindItems()
    }

    inner class TargetAcvhViewHolder(itemview: View): RecyclerView.ViewHolder(itemview){
        fun bindItems(){
            try {
                itemView.tv_ta_dtls_name.text = Pref.shopText+" : "+mList.get(adapterPosition).name
                if(mList.get(adapterPosition).date_time.isNullOrEmpty()){
                    itemView.tv_ta_dtls_date.visibility = View.GONE
                }else{
                    itemView.tv_ta_dtls_date.visibility = View.VISIBLE
                    itemView.tv_ta_dtls_date.text = mList.get(adapterPosition).date_time.take(10)
                }
                if(mList.get(adapterPosition).value.isNullOrEmpty()){
                    itemView.tv_ta_dtls_value.visibility = View.GONE
                }else{
                    itemView.tv_ta_dtls_value.visibility = View.VISIBLE
                    itemView.tv_ta_dtls_value.text = TargetVSAchvDtlsFrag.targetType+" : " + mList.get(adapterPosition).value
                }
            } catch (e: Exception) {
               e.printStackTrace()
            }
        }
    }
}