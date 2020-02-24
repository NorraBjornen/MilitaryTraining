import java.util.*

fun main(){
    val sc = Scanner(System.`in`)
    val n = sc.nextLine().toInt()

    val matrix = getMatrix(sc, n)

    var cell = findCross(matrix, n)

    val turns = mutableListOf<String>()

    while (cell != null){
        turns.add("${cell.row + 1} ${cell.col + 1}")
        change(cell.row, cell.col, matrix, n)
        cell = findCross(matrix, n)
    }


    while(!isCompleted(matrix, n)){
        val i = findFullLine(matrix, n)

        val m = transpose(matrix, n)

        val j = findFullLine(m, n)

        turns.add("${i + 1} ${j + 1}")
        change(i, j, matrix, n)

    }

    println(turns.size)
    turns.forEach{println(it)}
}

fun isCompleted(matrix: Array<Array<Char>>, n: Int) : Boolean{
    var completedRows = 0

    i@for(i in 0 until n){
        var wCount = 0
        for(j in 0 until n){
            if(matrix[i][j] == 'W') {
                wCount++
            } else {
                break@i
            }
        }
        completedRows++
    }

    if(completedRows == n)
        return true

    completedRows = 0

    i@for(i in 0 until n){
        var bCount = 0
        for(j in 0 until n){
            if(matrix[i][j] == 'B') {
                bCount++
            } else {
                break@i
            }
        }
        completedRows++
    }

    if(completedRows == n)
        return true

    return false
}

fun findFullLine(matrix: Array<Array<Char>>, n : Int) : Int{
    for(i in 0 until n) {
        var wCount = 0
        var bCount = 0
        val row = matrix[i]
        for (j in 0 until n) {
            if(row[j] == 'W')
                wCount++
            else
                bCount++
        }

        if (wCount == 4)
            return i
        else if (bCount == 4)
            return i
    }

    return -1
}

fun findCross(matrix: Array<Array<Char>>, n : Int) : Cell?{
    var rowsCountWithW = 0
    var rowsCountWithB = 0

    var rowWithW = -1
    var colWithW = -1

    var rowWithB = -1
    var colWithB = -1

    for(i in 0 until n){
        if(rowsCountWithW == 2 || rowsCountWithB == 2)
            break

        var wCount = 0
        var bCount = 0
        val row = matrix[i]
        for(j in 0 until n){
            if(row[j] == 'W')
                wCount++
            else
                bCount++
        }

        if(wCount == 1){
            rowsCountWithW++
            if(rowWithW == -1){
                rowWithW = i
                colWithW = row.indexOf('W')
            }
            else if (colWithW != row.indexOf('W'))
                colWithW = row.indexOf('W')
            else
                rowsCountWithW--
        }
        else if (bCount == 1){
            rowsCountWithB++
            if(rowWithB == -1) {
                rowWithB = i
                colWithB = row.indexOf('B')
            }
            else if (colWithB != row.indexOf('B'))
                colWithB = row.indexOf('B')
            else
                rowsCountWithB--
        }
    }

    return when{
        (rowsCountWithW == 2) -> Cell(rowWithW, colWithW)
        (rowsCountWithB == 2) -> Cell(rowWithB, colWithB)
        else -> null
    }
}


fun getMatrix(sc : Scanner, n : Int) : Array<Array<Char>>{
    val matrix : Array<Array<Char>> = Array(n) { Array(n) {'0'} }

    for(i in 0 until n){
        val line = sc.nextLine()

        var wCount = 0
        var bCount = 0

        var j = 0

        line.forEach {
            matrix[i][j] = it
            j++
        }
    }

    return matrix
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

class Cell (val row : Int, val col : Int)