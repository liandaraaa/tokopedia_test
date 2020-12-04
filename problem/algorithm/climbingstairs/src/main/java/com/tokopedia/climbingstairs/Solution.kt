package com.tokopedia.climbingstairs

object Solution {
    fun climbStairs(n: Int): Long {
       return findStep(n+1)
    }

    private fun findStep(step:Int):Long{
        if(step <= 1) return step.toLong()
        return findStep(step - 1) + findStep(step - 2)
    }

}
