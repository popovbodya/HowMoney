package ru.popov.bodya.howmoney.presentation.ui.about.fragments

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import ru.popov.bodya.core.mvp.BaseCoreFragment
import ru.popov.bodya.howmoney.R
import ru.popov.bodya.howmoney.presentation.mvp.about.AboutPresenter
import ru.popov.bodya.howmoney.presentation.mvp.about.AboutView
import javax.inject.Inject


/**
 *  @author popovbodya
 */
class AboutFragment : BaseCoreFragment(), AboutView {

    @Inject
    @InjectPresenter
    lateinit var aboutPresenter: AboutPresenter

    private lateinit var appVersionTextView: TextView

    @ProvidePresenter
    fun provideAboutPresenter(): AboutPresenter = aboutPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.about_fragment_layout, container, false)
        setHasOptionsMenu(true)
        initViews(view)
        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                aboutPresenter.onUpButtonPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showAppVersion(version: String) {
        appVersionTextView.text = version
    }

    private fun initViews(parentView: View) {
        appVersionTextView = parentView.findViewById(R.id.app_version_text_view)
        val emailTextView: TextView = parentView.findViewById(R.id.author_email_text_view)
        emailTextView.setOnClickListener { aboutPresenter.onEmailClick() }
        initToolbar(parentView)
    }

    private fun initToolbar(parentView: View) {
        val toolbar = parentView.findViewById<Toolbar>(R.id.toolbar)
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}