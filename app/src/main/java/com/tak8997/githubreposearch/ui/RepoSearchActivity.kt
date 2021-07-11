package com.tak8997.githubreposearch.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tak8997.githubreposearch.R
import com.tak8997.githubreposearch.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.getStateViewModel

class RepoSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by lazy {
        getStateViewModel<RepoSearchViewModel>()
    }

    private val repoAdapter by lazy { RepoAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .apply {
                viewModel = this@RepoSearchActivity.viewModel
                lifecycleOwner = this@RepoSearchActivity
            }

        with(viewModel) {
            repos.observe(this@RepoSearchActivity) {
                if (page == 1) {
                    repoAdapter.setItems(it ?: return@observe)
                } else {
                    repoAdapter.addItems(it ?: return@observe)
                }
            }
            error.observe(this@RepoSearchActivity) {
                Toast.makeText(this@RepoSearchActivity, it ?: return@observe, Toast.LENGTH_SHORT).show()
            }
        }

        setupRepoEditListener()
        setupRepoRecycler()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.onSaveInstanceState(repoAdapter.items)
    }

    private fun setupRepoEditListener() {
        binding.etRepoSearch.addTextChangedListener {
            val query = it?.toString() ?: ""
            if (lastQuery != query) {
                page = 1
                viewModel.onRepoTitleEdited(query, page)
                lastQuery = query
            }
        }
    }

    private fun setupRepoRecycler() {
        with(binding.rvRepo) {
            layoutManager = LinearLayoutManager(this@RepoSearchActivity)
            adapter = repoAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = layoutManager
                    if (layoutManager is LinearLayoutManager) {
                        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                        val visibleItemCount = layoutManager.childCount
                        val totalItemCount = layoutManager.itemCount

                        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && viewModel.loading.value == false
                        ) {
                            viewModel.fetchMore(binding.etRepoSearch.text.toString(), ++page)
                        }
                    }
                }
            })
        }
    }

    companion object {
        private var page = 1
        private var lastQuery = ""
    }
}