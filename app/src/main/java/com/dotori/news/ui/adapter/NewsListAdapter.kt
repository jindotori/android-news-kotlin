package com.dotori.news.ui.adapter

import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.dotori.news.R
import com.dotori.news.databinding.ItemAdBannerBinding
import com.dotori.news.databinding.ItemNewsCardBinding
import com.dotori.news.ui.main.MainActivity
import com.dotori.news.ui.main.data.News
import com.dotori.news.ui.main.fragment.BaseNewsFragment.Companion.ITEMS_PER_AD
import com.google.android.gms.ads.AdView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class NewsListAdapter(
    var list: ArrayList<Any>,
    var itemClicked: (news: News) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class NewsViewHolder(val binding: ItemNewsCardBinding) : RecyclerView.ViewHolder(binding.root)

    //Banner Ad View Holder
    class AdViewHolder(val binding: ItemAdBannerBinding) : RecyclerView.ViewHolder(binding.root)

    private val ITEM_TYPE_NEWS = 0
    private val ITEM_TYPE_BANNER_AD = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_NEWS) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_news_card, parent, false)
            NewsViewHolder(ItemNewsCardBinding.bind(view))
        } else {
            val bannerLayoutView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ad_banner, parent, false)
            AdViewHolder(ItemAdBannerBinding.bind(bannerLayoutView))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("holder", "position: $position")

        when (getItemViewType(position)) {
            ITEM_TYPE_NEWS -> {
                val newsHolder = holder as NewsViewHolder
                val newsList = list[position] as News
                newsList.let { item ->
                    newsHolder.binding.sectionCard.text = item.sectionName
                    newsHolder.binding.titleCard.text = item.webTitle
                    //            holder.binding.trailTextCard.text = item.sectionId
                    //            holder.binding.authorCard.text = item.
                    newsHolder.binding.authorCard.text = item.author
                    newsHolder.binding.dateCard.text =
                        getTimeDifference(formatDate(item.webPublicationDate))
                    Glide.with(holder.itemView.context)
                        .load(item.thumbnail)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .apply(RequestOptions.bitmapTransform(RoundedCorners(14)))
                        .into(newsHolder.binding.thumbnailImageCard)
                }
                newsHolder.binding.itemCardView.setOnClickListener {
                    newsList.let { itemClicked.invoke(it) }
                }
            }
            ITEM_TYPE_BANNER_AD -> {
                val bannerHolder = holder as AdViewHolder
                val adView = list[position] as AdView

                val adCardView = bannerHolder.itemView as ViewGroup
                // The AdViewHolder recycled by the RecyclerView may be a different
                // instance than the one used previously for this position. Clear the
                // AdViewHolder of any subviews in case it has a different
                // AdView associated with it, and make sure the AdView for this position doesn't
                // already have a parent of a different recycled AdViewHolder.
                // The AdViewHolder recycled by the RecyclerView may be a different
                // instance than the one used previously for this position. Clear the
                // AdViewHolder of any subviews in case it has a different
                // AdView associated with it, and make sure the AdView for this position doesn't
                // already have a parent of a different recycled AdViewHolder.
//                if (adCardView.childCount > 0) {
//                    adCardView.removeAllViews()
//                }

                if (bannerHolder.binding.adCardView.childCount > 0) {
                    bannerHolder.binding.adCardView.removeAllViews()
                }

                if (adView.parent != null) {
                    (adView.parent as ViewGroup).removeView(adView)
                }

                // Add the banner ad to the ad view.
//                adCardView.addView(adView)
                bannerHolder.binding.adCardView.addView(adView)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] is News) {
            ITEM_TYPE_NEWS
        } else {
            ITEM_TYPE_BANNER_AD
        }
    }

    fun setData(newData: ArrayList<Any>) {
        list = newData
        notifyDataSetChanged()
    }


    private fun getTimeDifference(formattedDate: String): CharSequence {
        val currentTime = System.currentTimeMillis()
        val publicationTime = getDateInMillis(formattedDate)
        return DateUtils.getRelativeTimeSpanString(
            publicationTime, currentTime,
            DateUtils.SECOND_IN_MILLIS
        )
    }

    private fun formatDate(dateStringUTC: String?): String {
        // Parse the dateString into a Date object
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss'Z'", Locale.UK)
        var dateObject: Date? = null
        try {
            dateObject = simpleDateFormat.parse(dateStringUTC)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        // Initialize a SimpleDateFormat instance and configure it to provide a more readable
        // representation according to the given format, but still in UTC
        val df = SimpleDateFormat("MMM d, yyyy  h:mm a", Locale.ENGLISH)
        val formattedDateUTC = df.format(dateObject)
        // Convert UTC into Local time
        df.timeZone = TimeZone.getTimeZone("UTC")
        var date: Date? = null
        try {
            date = df.parse(formattedDateUTC)
            df.timeZone = TimeZone.getDefault()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return df.format(date)
    }


    companion object {
        /**
         * Get the formatted web publication date string in milliseconds
         * @param formattedDate the formatted web publication date string
         * @return the formatted web publication date in milliseconds
         */
        private fun getDateInMillis(formattedDate: String): Long {
            val simpleDateFormat = SimpleDateFormat("MMM d, yyyy  h:mm a", Locale.ENGLISH)
            val dateInMillis: Long
            val dateObject: Date
            try {
                dateObject = simpleDateFormat.parse(formattedDate)
                dateInMillis = dateObject.time
                return dateInMillis
            } catch (e: ParseException) {
                e.message?.let { Log.e("Problem parsing date", it) }
                e.printStackTrace()
            }
            return 0
        }
    }

}
