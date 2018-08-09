package com.github.monchenkoid.quotationapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val itemString: String by lazy(LazyThreadSafetyMode.NONE) {
        getString(R.string.item_string)
    }

    private val quotationAdapter: QuotationAdapter by lazy(LazyThreadSafetyMode.NONE) {
        QuotationAdapter(itemString)
    }

    private var disposable: Disposable? = null

    private val currencyServe by lazy {
        CurrencyApiService.create()
    }

    private fun loadDataSearch() {
        disposable = currencyServe.hitCountCheck()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> txt_load_result.text = "${result.getArticles().get(0).name} result found" },
                        { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
                )
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_load.setOnClickListener {
                loadDataSearch()
        }

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
            setTitle(R.string.app_name)
        }

        recyclerView.setup()
    }

    private fun RecyclerView.setup() {
        adapter = this@MainActivity.quotationAdapter
        layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
        when(item?.itemId) {
            R.id.menu_add -> {
                addItem()
                true
            }
            else -> null
        } ?: super.onOptionsItemSelected(item)

    private fun addItem() {
        quotationAdapter.appendItem(itemString)
    }

}
