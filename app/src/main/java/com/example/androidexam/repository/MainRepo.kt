package com.example.androidexam.repository

import com.example.androidexam.data.ListItem
import com.example.androidexam.data.ViewPagerItem
import com.example.androidexam.utils.Constants
import com.example.androidexam.utils.randomString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

open class MainRepo @Inject constructor() {

    fun getViewPagerListData() : Flow<List<ViewPagerItem>> = flow {

        val viewPagerItemList = ArrayList<ViewPagerItem>()
        for(i in 1..3){
            viewPagerItemList.add(ViewPagerItem(i, Constants.Img_Base_Url +(500+i), generateRandomListItems(i)))
        }
        emit(viewPagerItemList)
    }

    private fun generateRandomListItems(pagerItem: Int): ArrayList<ListItem> {

        return arrayListOf(
            ListItem("Pager $pagerItem ${randomString()}", Constants.Img_Base_Url +(201)),
            ListItem("Pager $pagerItem ${randomString()}", Constants.Img_Base_Url +(202)),
            ListItem("Pager $pagerItem ${randomString()}", Constants.Img_Base_Url +(203)),
            ListItem("Pager $pagerItem ${randomString()}", Constants.Img_Base_Url +(204)),
            ListItem("Pager $pagerItem ${randomString()}", Constants.Img_Base_Url +(205)),
            ListItem("Pager $pagerItem ${randomString()}", Constants.Img_Base_Url +(206)),
            ListItem("Pager $pagerItem ${randomString()}", Constants.Img_Base_Url +(207)),
            ListItem("Pager $pagerItem ${randomString()}", Constants.Img_Base_Url +(208)),
            ListItem("Pager $pagerItem ${randomString()}", Constants.Img_Base_Url +(209)),
            ListItem("Pager $pagerItem ${randomString()}", Constants.Img_Base_Url +(210)),
            ListItem("Pager $pagerItem ${randomString()}", Constants.Img_Base_Url +(211)),
            ListItem("Pager $pagerItem ${randomString()}", Constants.Img_Base_Url +(212)),
        )
    }
}