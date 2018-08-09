package com.github.monchenkoid.quotationapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.github.monchenkoid.quotationapp.api.CurrencyApiService
import com.github.monchenkoid.quotationapp.data.CurrencyModel
import com.github.monchenkoid.quotationapp.ui.CurrencyAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var disposable: Disposable? = null

    private val currencyServe by lazy {
        CurrencyApiService.create()
    }

    private fun loadDataSearch() {
        disposable = currencyServe.hitCountCheck()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> recyclerView.setup(result.getArticles()) },
                        { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
                )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        loadDataSearch()
    }

    override fun onPause() {
        super.onPause()

        disposable?.dispose()
    }

    private fun RecyclerView.setup(data: List<CurrencyModel>) {
        adapter = CurrencyAdapter(data)
        layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
    }
}
