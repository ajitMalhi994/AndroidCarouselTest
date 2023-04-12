package com.example.androidexam.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidexam.data.ListItem
import com.example.androidexam.data.ViewPagerItem
import com.example.androidexam.repository.MainRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepo: MainRepo): ViewModel() {

    private val _viewPagerData: MutableLiveData<List<ViewPagerItem>> = MutableLiveData()
    val viewPagerData : MutableLiveData<List<ViewPagerItem>> by lazy { _viewPagerData }

    val listItemsData = MutableLiveData<ArrayList<ListItem>>()

    fun getViewPagerData(){

        viewModelScope.launch {
             mainRepo.getViewPagerListData().collect {
                     data -> _viewPagerData.postValue(data)
             }
        }
    }

    fun filterAndUpdate(pagerItem: Int, searchText: String){
        if (searchText.isEmpty()){
            listItemsData.postValue(viewPagerData.value?.get(pagerItem)?.listOfItems)
        } else {
            listItemsData.postValue(viewPagerData.value?.get(pagerItem)?.listOfItems?.filter { item ->
                item.description.lowercase().contains(searchText.lowercase())
            } as ArrayList<ListItem>?)
        }
    }
}