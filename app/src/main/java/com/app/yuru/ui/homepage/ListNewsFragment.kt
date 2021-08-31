package com.app.yuru.ui.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.yuru.databinding.FragmentListNewsBinding
import com.app.yuru.domain.entity.News
import com.app.yuru.domain.entity.toDto
import com.app.yuru.ui.homepage.rvitem.NewsItem
import com.app.yuru.utility.ThreadInfoLogger
import com.github.ajalt.timberkt.d
import com.app.yuru.coreandroid.base.BaseFragmentBinding
import com.app.yuru.coreandroid.extensions.toGone
import com.app.yuru.coreandroid.extensions.toVisible
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListNewsFragment : BaseFragmentBinding<FragmentListNewsBinding>(), NewsItem.NewsListener {

    @Inject
    lateinit var listNewsViewModel: ListNewsViewModel

    private val listNewsAdapter = GroupAdapter<GroupieViewHolder>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentListNewsBinding =
        FragmentListNewsBinding::inflate

    override fun setupView(binding: FragmentListNewsBinding) = with(binding) {
        // setupRecyclerview
        rvListNews.layoutManager = LinearLayoutManager(requireActivity())
        rvListNews.adapter = listNewsAdapter

        setListener(binding)

        callData()

        listNewsViewModel.uiState().observe(viewLifecycleOwner, { state ->
            when (state) {

                is ListNewsViewModel.ListNewsState.Loading -> {
                    loadingIndicator.toVisible()
                }

                is ListNewsViewModel.ListNewsState.NewsLoaded -> {
                    loadingIndicator.toGone()
                    swipeRefreshLayout.isRefreshing = false

                    ThreadInfoLogger.logThreadInfo("show news viewmodel")

                    state.news.map {
                        d { "news loaded -> ${it.title}" }
                        listNewsAdapter.add(NewsItem(it, this@ListNewsFragment))
                    }
                }
            }
        })
    }

    override fun onNewsSelected(news: News) {
        findNavController().navigate(
            ListNewsFragmentDirections.actionListNewsFragmentToDetailNewsFragment(
                news.toDto()
            )
        )
    }

    private fun setListener(binding: FragmentListNewsBinding) {
        binding.swipeRefreshLayout.setOnRefreshListener {
            listNewsAdapter.clear()

            callData()
        }
    }

    private fun callData() {
        listNewsViewModel.getTopHeadlinesByCountry(country = "us", category = "technology")
    }

}