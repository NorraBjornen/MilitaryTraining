import java.util.*

fun main(){
    val sc = Scanner(System.`in`)

    val n = sc.nextLine().toInt()

    val matrix : Array<Array<Char>> = Array(n) { Array(n) {'0'} }

    val rowIndexes = mutableListOf<Letter>()

    var bRowsCount = 0
    var wRowsCount = 0

    for(i in 0 until n){
        val line = sc.nextLine()

        var wCount = 0
        var bCount = 0

        var j = 0

        line.forEach {
            if(it == 'W')
                wCount++
            else
                bCount++
            matrix[i][j] = it
            j++
        }

        if(wCount == 1){
            rowIndexes.add(Letter(i, line.indexOf('W'), 'W'))
            wRowsCount++
        }
        else if(bCount == 1){
            rowIndexes.add(Letter(i, line.indexOf('B'), 'B'))
            bRowsCount++
        }
    }


    foo(wRowsCount, bRowsCount, rowIndexes, matrix, n)


    


    printMatrix(transpose(matrix, n))
}


fun getRowIndexes(matrix: Array<Array<Char>>, n: Int) : Info{
    val rowIndexes = mutableListOf<Letter>()

    var bRowsCount = 0
    var wRowsCount = 0

    for(i in 0 until n){
        var wCount = 0
        var bCount = 0

        for(j in 0 until n){
            if(matrix[i][j] == 'W')
                wCount++
            else
                bCount++
        }

        if(wCount == 1){
            rowIndexes.add(Letter(i, matrix[i].indexOf('W'), 'W'))
            wRowsCount++
        }
        else if(bCount == 1){
            rowIndexes.add(Letter(i, matrix[i].indexOf('B'), 'B'))
            bRowsCount++
        }
    }

    return Info(rowIndexes, wRowsCount, bRowsCount)
}


fun foo(wRowsCount : Int, bRowsCount : Int, rowIndexes : MutableList<Letter>, matrix: Array<Array<Char>>, n: Int){
    if(wRowsCount == 0 && bRowsCount == 0)
        return

    var letter = '0'

    if(wRowsCount > 1 && wRowsCount % 2 == 0 ){
        letter = 'W'
    } else if (bRowsCount > 1 && bRowsCount % 2 == 0){
        letter = 'B'
    }

    var row = 0
    var col = 0

    var flag = true
    var count = 0

    var toRemove = mutableListOf<Letter>()

    rowIndexes.forEach{
        if(count != 2 && it.letter == letter){
            if(flag){
                row = it.row
                flag = false
            }
            else
                col = it.col
            count++
            toRemove.add(it)
        }
    }

    rowIndexes.removeAll(toRemove)

    change(row, col, matrix, n)

    var newWRowsCount = wRowsCount
    var newBRowsCount = bRowsCount

    if(letter == 'B'){
        newBRowsCount-=2
    } else {
        newWRowsCount-=2
    }

    foo(newWRowsCount, newBRowsCount, rowIndexes, matrix, n)
}


fun change(row : Int, col : Int, matrix: Array<Array<Char>>, n : Int){
    for(i in 0 until n){
        for(j in 0 until n){
            if(i == row){
                reverse(i, j, matrix)
            } else if (j == col)
                reverse(i, j, matrix)
        }
    }
}

fun reverse(i : Int, j: Int, matrix: Array<Array<Char>>){
    if(matrix[i][j] == 'W')
        matrix[i][j] = 'B'
    else
        matrix[i][j] = 'W'
}

fun transpose(matrix: Array<Array<Char>>, n: Int) : Array<Array<Char>>{
    val newMatrix : Array<Array<Char>> = Array(n) { Array(n) {'0'} }

    for(i in 0 until n){
        for(j in 0 until n){
            newMatrix[j][i] = matrix[i][j]
        }
    }

    return newMatrix
}

fun printMatrix(matrix : Array<Array<Char>>){
    matrix.forEach{
        it.forEach{item ->
            print(item)
        }
        println()
    }
}