package com.kotlin.taskapp.ui.activities


import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.odtviewm.util.AppConstants
import com.kotlin.taskapp.R
import com.kotlin.taskapp.adapters.ProductAdapter
import com.kotlin.taskapp.model.responsemodel.Product
import com.kotlin.taskapp.model.responsemodel.productList
import com.kotlin.taskapp.retrofitapi.RequestInterface
import com.kotlin.taskapp.retrofitapi.RetrofitException
import customer.kotlin.com.kotlincustomerapp.retrofitapi.RetrofitClient
import customer.kotlin.com.kotlincustomerapp.util.NetworkUtil
import kotlinx.android.synthetic.main.activity_product.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductActivity : AppCompatActivity() {

    private lateinit var mApiInterface: RequestInterface

    private var productList = mutableListOf<Product>()
    private lateinit var adapter: ProductAdapter
    var pageNumer: Int = 1
    private var loading = false
    private var noMoreItemPresent: Boolean = false

    companion object {
        var DEFAULT_PAGE_SIZE: Int = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        setupToolbar()

        mApiInterface = RetrofitClient.getAPIService()!!

        recyclerView.setHasFixedSize(true)
        val layoutManager =
            LinearLayoutManager(this@ProductActivity, LinearLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = layoutManager

        if (NetworkUtil.isConnectedOrConnecting(this)) {
            getProductList()
        } else {
            runOnUiThread {
                showDialog()
            }
        }

        adapter = ProductAdapter(this@ProductActivity, productList)
        recyclerView.adapter = adapter

        setScrollListener()

    }


    private fun setScrollListener() {

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView
                    .layoutManager as LinearLayoutManager?

                if (noMoreItemPresent || loading || linearLayoutManager!!.itemCount > linearLayoutManager.findLastVisibleItemPosition() + 2) return
                loading = true
                pageNumer++

                if (NetworkUtil.isConnectedOrConnecting(this@ProductActivity)) {
                    try {
                        getProductList()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    runOnUiThread {
                        showDialog()
                    }

                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }


    /**
     * Renders product data from server
     */
    private fun getProductList() {

        val progressDialog = ProgressDialog(this@ProductActivity)
        progressDialog.setMessage(AppConstants.PROCESSING_REQUEST)
        progressDialog.show()

        val call = mApiInterface.getProductList(pageNumer, DEFAULT_PAGE_SIZE);

        call?.enqueue(object : Callback<productList> {
            override fun onFailure(call: Call<productList>, t: Throwable) {

                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<productList>,
                response: Response<productList>
            ) {
                if (response.isSuccessful) {
                    val jsonResponse = response.body()

                    if (jsonResponse!!.statusCode == 200) {

                        productList.addAll(jsonResponse.products)

                        loading = false

                        adapter.notifyDataSetChanged()
                        if (jsonResponse.totalProducts == productList.size) {
                            noMoreItemPresent = true
                        }

                    } else {

                        AppConstants.displayToast("" + jsonResponse.statusCode)
                    }
                    progressDialog.dismiss()
                } else {
                    try {
                        progressDialog.dismiss()
                        val retrofitException =
                            RetrofitException.httpError(
                                RetrofitClient.ROOT_URL,
                                response,
                                RetrofitClient.getClient()
                            )

                        AppConstants.displayToast(

                            retrofitException.message.toString()
                        )

                    } catch (ex: Exception) {

                        AppConstants.showExceptionLog(ex)
                    }

                }
                progressDialog.dismiss()
            }

        })
    }

    private fun setupToolbar() {

        mainToolbar.title = resources.getString(R.string.product_list)
        setSupportActionBar(mainToolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)

    }

    fun showDialog() {
        AlertDialog.Builder(this)
            .setTitle(this.getString(R.string.alert))
            .setMessage(AppConstants.MSG_NETWORK)
            .setCancelable(false)
            .setPositiveButton(this.getString(R.string.ok)) { _, _ ->

            }
            .show()
    }
}
