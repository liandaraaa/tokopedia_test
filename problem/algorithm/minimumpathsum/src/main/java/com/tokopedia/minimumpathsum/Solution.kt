package com.tokopedia.minimumpathsum

import kotlin.math.min

object Solution {
    fun minimumPathSum(matrix: Array<IntArray>): Int {
        val row = matrix.size
        val col = matrix[0].size
        val newMatrix = Array(row){IntArray(col)}

        newMatrix[0][0] = matrix[0][0]

        for(i in 1 until row){
            newMatrix[i][0] = newMatrix[i-1][0]+matrix[i][0]
        }

        for(j in 1 until col){
            newMatrix[0][j] = newMatrix[0][j-1]+matrix[0][j]
        }

        for(i in 1 until row){
            for(j in 1 until col){
                val fromLeft = newMatrix[i-1][j]+matrix[i][j]
                val fromTop = newMatrix[i][j-1]+matrix[i][j]
                newMatrix[i][j] = min(fromLeft,fromTop)
            }
        }

        return newMatrix[row-1][col-1]
    }

}
