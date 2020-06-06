package com.kotlin.taskapp.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kotlin.odtviewm.util.AppConstants
import com.kotlin.taskapp.R
import com.kotlin.taskapp.model.responsemodel.Product
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_product_detail)

        try {
            //set text on tool bar
            setupToolbar()

            val data = intent.extras
            val productItem = data.getParcelable<Product>("PRODUCT_ITEM")


            txtProductName.text=productItem.productName
            val shortDiscription = productItem.longDescription.replace("\n", "").replace("\t", "")
                .replace("<ul>", "").replace("<li>", "").replace("</li>", "").replace("</ul>", "")


            txtProductDiscription.text = Html.fromHtml(shortDiscription)
            val itemPrice=productItem.price
            txtProductPrice.text="Price:- $itemPrice"

            val productInStock = if (productItem.inStock) "Yes" else "No"
            txtProductStock.text="In-Stock:- $productInStock"

            val imageURL = AppConstants.LIVE_URL + "" + productItem.productImage

            Glide.with(this).load(imageURL).into(imgProduct)

            productRatingBar.rating = productItem.reviewRating;
        } catch (e: Exception) {
            AppConstants.showExceptionLog(e)
        }

    }

    private fun setupToolbar() {

        mainToolbar.title = resources.getString(R.string.product_detail)
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Respond to the action bar's Up/Home button
            android.R.id.home -> {

                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}