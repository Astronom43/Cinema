package cinema


class Cinema(private val rows: Int, private val seats: Int) {
    private val arrTickets = Array(rows, { Array(seats, { Ticket() }) })

    init {
        for (r in 0..arrTickets.lastIndex) {
            for (s in 0..arrTickets[r].lastIndex) {
                arrTickets[r][s].price = getPrice(r + 1)
            }
        }
    }

    class Ticket() {
        var sold = false
        var price = 0
    }

    fun getPrice(row: Int): Int {
        if (rows * seats < 60) {
            return 10

        } else {
            val halfOfIt = rows / 2
            return (if (row <= halfOfIt) 10 else 8)
        }
    }

    fun bayTicket() {
        var rowN = -1
        var seatN = -1
        while (true) {
            println()
            print("Enter a row number:\n> ")
            rowN = readLine()!!.toInt()
            print("Enter a seat number in that row:\n> ")
            seatN = readLine()!!.toInt()
            val ticket = try {
                arrTickets[rowN - 1][seatN - 1]
            } catch (e: Exception) {
                print(
                    """
                    
                    Wrong input!
                    
                """.trimIndent()
                )
                null
            }
            if (ticket != null) {
                if (ticket.sold != true) {
                    ticket.sold = true
                    break
                } else {
                    print(
                        """
                
               That ticket has already been purchased!
                
            """.trimIndent()
                    )
                }
            }

        }
        println("Ticket price: $" + arrTickets[rowN - 1][seatN - 1].price)
        println()
    }

    fun prnCinema() {
        println("Cinema:")
        print(" ")
        for (i in 0..arrTickets[0].lastIndex) {
            print(" ${i + 1}")
        }
        println()
        for (i in 0..arrTickets.lastIndex) {
            print(i + 1)
            for (j in 0..arrTickets[i].lastIndex) {
                print(if (arrTickets[i][j].sold) " B" else " S")
            }
            println()
        }

    }

    fun prnStat() {
        val bayTicketCount = arrTickets.flatMap { it.toList() }.filter { ticket -> ticket.sold }

        print(
            """
            
            Number of purchased tickets: ${bayTicketCount.size}
            Percentage: ${(100 * bayTicketCount.size.toDouble() / (seats * rows)).format(2)}%
            Current income: ${'$'}${bayTicketCount.map { ticket -> ticket.price }.sum()}
            Total income: ${'$'}${arrTickets.flatMap { it.toList() }.map { t -> t.price }.sum()}
            
        """.trimIndent()
        )
    }
}

fun Double.format(digits: Int) = "%.${digits}f".format(this)

fun main() {

    print("Enter the number of rows:\n> ")
    val rows = readLine()!!.toInt()
    print("Enter the number of seats in each row:\n> ")
    val seats = readLine()!!.toInt()
    val cinema = Cinema(rows, seats)



    while (true) {
        val menu = prnMenu()
        when (menu) {
            1 -> cinema.prnCinema()
            2 -> cinema.bayTicket()
            3 -> cinema.prnStat()
            0 -> break
        }
    }


}


fun prnMenu(): Int {
    println()
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
    print("> ")
    return readLine()!!.toInt()
}

