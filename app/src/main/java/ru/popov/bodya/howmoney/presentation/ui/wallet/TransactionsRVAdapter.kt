package ru.popov.bodya.howmoney.presentation.ui.wallet

import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.fabiomsr.moneytextview.MoneyTextView
import ru.popov.bodya.howmoney.R
import ru.popov.bodya.howmoney.domain.wallet.models.Transaction
import ru.popov.bodya.howmoney.presentation.util.ResourcesSelector

class TransactionsRVAdapter : RecyclerView.Adapter<TransactionsRVAdapter.ViewHolder>() {

    private val transactions = mutableListOf<Transaction>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = transactions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val action = transactions[position]
        holder.bind(action)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val date: TextView by lazy {
            itemView.findViewById<TextView>(R.id.tv_date)
        }

        private val icon: ImageView by lazy {
            itemView.findViewById<ImageView>(R.id.iv_currency_type)
        }

        private val time: TextView by lazy {
            itemView.findViewById<TextView>(R.id.tv_time)
        }
        private val currency: TextView by lazy {
            itemView.findViewById<TextView>(R.id.tv_currency)
        }
        private val transactionType: TextView by lazy {
            itemView.findViewById<TextView>(R.id.tv_transaction_category)
        }

        private val description: TextView by lazy {
            itemView.findViewById<TextView>(R.id.tv_description)
        }

        private val amount: MoneyTextView by lazy {
            itemView.findViewById<MoneyTextView>(R.id.tv_amount)
        }

        fun bind(item: Transaction) = with(itemView) {
            date.visibility = View.VISIBLE
            if (adapterPosition == 0) {
                date.text = DateFormat.format("dd/MM/yyyy", item.date)
            } else {
                if (item.date.day > transactions[adapterPosition - 1].date.day) {
                    date.text = DateFormat.format("dd/MM/yyyy hh:mm", item.date)
                } else {
                    date.visibility = View.GONE
                }
            }
            description.text = item.comment
            time.text = DateFormat.format("hh:mm", item.date)

            transactionType.text = ResourcesSelector.fromTransactionTypeToString(item.category, itemView)

            val iconImageResource = ResourcesSelector.fromTransactionTypeToDrawable(item.category)

            icon.setImageResource(iconImageResource)

            if (item.amount < 0) {
                amount.setBaseColor(itemView.resources.getColor(R.color.colorExpense))
                amount.setDecimalsColor(itemView.resources.getColor(R.color.colorExpense))
            } else {
                amount.setBaseColor(itemView.resources.getColor(R.color.colorIncome))
                amount.setDecimalsColor(itemView.resources.getColor(R.color.colorIncome))
            }
            amount.amount = item.amount.toFloat()
            amount.setSymbol("")
            currency.text = item.currency.toString()
        }
    }

    // TODO: USE DIFF UTIL
    fun updateDataSet(transactions: List<Transaction>?) {
        if (transactions != null) {
            this.transactions.clear()
            this.transactions.addAll(transactions)
            notifyDataSetChanged()
        }
    }
}