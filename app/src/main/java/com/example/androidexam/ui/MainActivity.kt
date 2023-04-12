package com.example.androidexam.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.androidexam.adapters.ListItemsAdapter
import com.example.androidexam.adapters.ViewPagerAdapter
import com.example.androidexam.data.ListItem
import com.example.androidexam.data.ViewPagerItem
import com.example.androidexam.databinding.ActivityMainBinding
import com.example.androidexam.ui.views.ViewStatus
import com.example.androidexam.utils.afterPageScrolled
import com.example.androidexam.utils.afterTextChanged
import com.example.androidexam.utils.makeGoneVisible
import com.example.androidexam.viewmodels.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var carouselAdapter: ViewPagerAdapter
    private lateinit var listItemsAdapter: ListItemsAdapter
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initData()
    }

    private fun initData(){
        carouselAdapter = ViewPagerAdapter()
        listItemsAdapter = ListItemsAdapter()

        mainViewModel.apply {
            mBinding.uiState = ViewStatus.Loading
            getViewPagerData()
        }

        observeData()
        updateDataOnViewPagerScroll()

        mBinding.edtSearch.afterTextChanged {
            mainViewModel.filterAndUpdate(mBinding.viewPager.currentItem, it)
        }
    }

    private fun observeData(){
        mainViewModel.apply {

            viewPagerData.observe(this@MainActivity){
                it.let {
                    mBinding.uiState = ViewStatus.Success
                    setPagerAdapter(it)
                }
            }

            listItemsData.observe(this@MainActivity){
                it.run {
                    updateLabelListData(this)
                }
                mBinding.tvError.makeGoneVisible(it)
            }
        }
    }

    private fun setPagerAdapter(data: List<ViewPagerItem>){
        mBinding.apply {
            carouselAdapter.submitList(data)
            viewPager.adapter = carouselAdapter
            rvLabelList.adapter = listItemsAdapter
            TabLayoutMediator(pagerTabLayout, viewPager){ tab, position -> }.attach()
            listItemsAdapter.submitList(data[0].listOfItems)
        }
    }

    private fun updateLabelListData(labelList: ArrayList<ListItem>?){
        listItemsAdapter.submitList(labelList)
    }

    private fun updateDataOnViewPagerScroll(){
        mBinding.viewPager.afterPageScrolled {
            updateLabelListData(mainViewModel.viewPagerData.value?.get(it)?.listOfItems)
        }
    }
}