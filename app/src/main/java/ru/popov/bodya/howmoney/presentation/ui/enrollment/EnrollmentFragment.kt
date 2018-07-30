package ru.popov.bodya.howmoney.presentation.ui.enrollment

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.hookedonplay.decoviewlib.DecoView
import com.hookedonplay.decoviewlib.charts.SeriesItem
import com.hookedonplay.decoviewlib.events.DecoEvent
import dagger.android.support.AndroidSupportInjection
import ru.popov.bodya.core.mvp.AppFragment
import ru.popov.bodya.howmoney.R
import ru.popov.bodya.howmoney.domain.enrollment.models.EnrollmentCategory
import ru.popov.bodya.howmoney.domain.wallet.models.Wallet
import ru.popov.bodya.howmoney.presentation.mvp.enrollment.EnrollmentPresenter
import ru.popov.bodya.howmoney.presentation.mvp.enrollment.EnrollmentView
import javax.inject.Inject


/**
 *  @author popovbodya
 */
class EnrollmentFragment : AppFragment(), EnrollmentView {

    companion object {
        private const val WALLET_KEY = "walletKey"

        fun newInstance(wallet: Wallet): EnrollmentFragment {
            val bundle = Bundle()
            bundle.putSerializable(WALLET_KEY, wallet)
            val enrollmentFragment = EnrollmentFragment()
            enrollmentFragment.arguments = bundle
            return enrollmentFragment
        }
    }

    @Inject
    @InjectPresenter
    lateinit var enrollmentPresenter: EnrollmentPresenter

    private lateinit var arcView: DecoView
    private lateinit var wallet: Wallet

    @ProvidePresenter
    fun providePresenter(): EnrollmentPresenter {
        enrollmentPresenter.initPresenterParams(wallet)
        return enrollmentPresenter
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        wallet = arguments?.getSerializable(WALLET_KEY) as Wallet
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val parentView = inflater.inflate(R.layout.enrollment_fragment_layout, container, false)
        setHasOptionsMenu(true)
        initViews(parentView)
        return parentView
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                enrollmentPresenter.onUpButtonPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showDiagram(enrollmentCategoryMap: Map<EnrollmentCategory, Double>, finalBalance: Double) {
        prepareEvents(enrollmentCategoryMap, finalBalance)
    }

    private fun initViews(parentView: View) {
        initToolbar(parentView)
        arcView = parentView.findViewById(R.id.dynamicArcView)
    }

    private fun initToolbar(parentView: View) {
        val toolbar = parentView.findViewById<Toolbar>(R.id.toolbar)
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun prepareEvents(enrollmentCategoryMap: Map<EnrollmentCategory, Double>, finalBalance: Double) {
        val colorResourceList: MutableList<Int> = ArrayList()
        val activityContext = context
        if (activityContext != null) {
            with(activityContext) {
                colorResourceList.add(ContextCompat.getColor(this, R.color.arcViewColorRed))
                colorResourceList.add(ContextCompat.getColor(this, R.color.arcViewColorYellow))
                colorResourceList.add(ContextCompat.getColor(this, R.color.arcViewColorBlue))
                colorResourceList.add(ContextCompat.getColor(this, R.color.arcViewColorOrange))
                colorResourceList.add(ContextCompat.getColor(this, R.color.arcViewColorBlack))
            }
        }

        var moveTo = 100f
        var delay: Long = 0
        val defaultDelayDelta: Long = 300
        val animationDelay: Long = defaultDelayDelta * enrollmentCategoryMap.size
        for ((index, entry) in enrollmentCategoryMap.entries.withIndex()) {
            arcView.addEvent(createEvent(createSeriesItem(arcView, colorResourceList, index, entry.key.name), moveTo, delay, animationDelay - delay))
            moveTo -= (entry.value / finalBalance * 100).toFloat()
            delay += defaultDelayDelta
        }
    }

    private fun createSeriesItem(arcView: DecoView, colorResourceList: MutableList<Int>, index: Int, seriesLabel: String): Int {
        return arcView.addSeries(SeriesItem.Builder(colorResourceList[index])
                .setRange(0f, 100f, 0f)
                .setLineWidth(64f)
                .setInitialVisibility(false)
                .build())
    }

    private fun createEvent(series: Int, moveTo: Float, delay: Long, duration: Long): DecoEvent =
            DecoEvent.Builder(moveTo)
                    .setIndex(series)
                    .setDelay(delay)
                    .setDuration(duration)
                    .build()
}