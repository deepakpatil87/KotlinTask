package com.kotlin.taskapp.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kotlin.odtviewm.util.AppConstants
import com.kotlin.taskapp.R
import com.kotlin.taskapp.model.responsemodel.Product
import com.kotlin.taskapp.ui.activities.ProductDetailActivity
import kotlinx.android.synthetic.main.activity_product_detail.view.*

/**
 * Created by Deepak  on 03/6/2020.
 */
class ProductAdapter(
    context: Activity,
    private var productList: MutableList<Product>
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    var mContext: Context? = null

    init {

        mContext = context

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_product, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItems(productList[position])

    }

    override fun getItemCount(): Int {
        return productList.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(product: Product) {

            val txtProductName = itemView.txtProductName as AppCompatTextView
            val txtProductDiscription = itemView.txtProductDiscription as AppCompatTextView
            val txtProductPrice = itemView.txtProductPrice as AppCompatTextView
            val imgProduct = itemView.imgProduct as ImageView
            val productRatingBar = itemView.productRatingBar as RatingBar

            try {

                txtProductName.text = product.productName
                val shortDiscription = product.shortDescription.replace("\n", "").replace("\t", "")
                    .replace("<ul>", "").replace("<li>", "").replace("</li>", "")
                    .replace("</ul>", "")

                txtProductDiscription.text = Html.fromHtml(shortDiscription)
                txtProductPrice.text = product.price

                val imageURL = AppConstants.LIVE_URL + "" + product.productImage


                mContext?.let { Glide.with(it).load(imageURL).into(imgProduct) }

                productRatingBar.rating = product.reviewRating;

                itemView.setOnClickListener {
                    val productItem = product
                    val intent = Intent(mContext, ProductDetailActivity::class.java)
                    intent.putExtra("PRODUCT_ITEM", productItem)
                    mContext!!.startActivity(intent)
                };
            } catch (e: Exception) {
                AppConstants.showExceptionLog(e)
            }
        }

    }


}