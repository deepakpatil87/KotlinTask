package customer.kotlin.com.kotlincustomerapp.retrofitapi

import com.kotlin.odtviewm.util.AppConstants
import com.kotlin.taskapp.retrofitapi.RequestInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object {

        val ROOT_URL: String = AppConstants.LIVE_URL
        private var retrofit: Retrofit? = null

        fun getClient(): Retrofit? {

            if (retrofit == null) {
                val logging = HttpLoggingInterceptor()
                // set your desired log level
                logging.level = HttpLoggingInterceptor.Level.BODY
                val httpClient = OkHttpClient.Builder()
                httpClient.readTimeout(60, TimeUnit.SECONDS)
                httpClient.connectTimeout(60, TimeUnit.SECONDS)

                httpClient.addInterceptor(logging) // <- this is the important line!
                //httpClient.addInterceptor(ConnectivityInterceptor(DQTApplication.getContext()))

                retrofit = Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
            }
            return retrofit

        }

        fun getAPIService(): RequestInterface? {
            return getClient()?.create(RequestInterface::class.java)
        }
    }


}