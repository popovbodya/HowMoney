package ru.popov.bodya.howmoney.presentation.ui.overview.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.popov.bodya.core.dagger.Injectable
import ru.popov.bodya.howmoney.R
import ru.popov.bodya.howmoney.presentation.mvp.overview.OverviewPresenter
import ru.popov.bodya.howmoney.presentation.mvp.overview.OverviewView
import javax.inject.Inject

/**
 *  @author popovbodya
 */
class OverviewActivity: MvpAppCompatActivity(), OverviewView, Injectable {

    @Inject
    @InjectPresenter
    lateinit var overviewPresenter: OverviewPresenter

    private lateinit var textView : TextView

    @ProvidePresenter
    fun provideOverviewPresenter(): OverviewPresenter {
        return overviewPresenter
    }

    override fun showNewData() {
        textView.text = "New Data"
    }

    override fun showError() {
        textView.text = "Error Data :("
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.text_view)
        Log.e("bodya", overviewPresenter.toString())
    }
}