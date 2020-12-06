package com.tokopedia.oilreservoir

import kotlin.math.max

/**
 * Created by fwidjaja on 2019-09-24.
 */
object Solution {
    fun collectOil(height: IntArray): Int {

        var oil = 0
        var previousHeight = 0

        for(i in height.indices){
            val currentHeight = height[i]
            if (i !=  height.size - 1){
                val nextHeight = height[i+1]
                if ((previousHeight > currentHeight) && (nextHeight > currentHeight) ){
                    oil += (nextHeight - currentHeight)
                }
                previousHeight = max(currentHeight,previousHeight)
            }
        }

        return oil
    }
}
