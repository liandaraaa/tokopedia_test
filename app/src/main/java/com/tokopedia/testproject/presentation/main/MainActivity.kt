package com.tokopedia.testproject.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tokopedia.climbingstairs.ClimbingStairsActivity
import com.tokopedia.core.GlobalConfig
import com.tokopedia.filter.view.FilterCaseActivity
import com.tokopedia.maps.MapsCaseActivity
import com.tokopedia.minimumpathsum.MinimumPathSumActivity
import com.tokopedia.oilreservoir.OilReservoirActivity
import com.tokopedia.testproject.BuildConfig
import com.tokopedia.testproject.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalConfig.DEBUG = BuildConfig.DEBUG
        setContentView(R.layout.activity_main)

        button_min_path_sum goTo MinimumPathSumActivity::class.java
        button_oil goTo OilReservoirActivity::class.java
        button_stairs goTo ClimbingStairsActivity::class.java

        button_maps goTo MapsCaseActivity::class.java
        button_filter goTo FilterCaseActivity::class.java
    }

    private fun startActivity(cls: Class<*>){
        startActivity(Intent(this, cls))
    }

    private infix fun View.goTo(cls: Class<*>) {
        setOnClickListener { startActivity(cls) }
    }

}
