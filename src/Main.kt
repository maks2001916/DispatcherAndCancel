import kotlinx.coroutines.*
import kotlin.random.Random
import kotlin.system.measureTimeMillis

suspend fun main(): Unit = coroutineScope {
    val resultList: MutableMap<Person, Int> = mutableMapOf()
    val personManager = PersonManager()
    println("Программа работы с базой данных сотрудников")
    delay(1000)

    var answer: Int = 3
    val runTime = measureTimeMillis {
        val printBD = async {
            readDataPersonList(resultList)
        }

        val cancelInput = async (start = CoroutineStart.LAZY) {
            printBD.cancel()
            println("Завершение полной работы")
        }

        while (true) {
            println("Добавить сотрудника:\n1. Да\n2. Нет")
            answer = readInputInt()

            if (answer == 1) {
                println("Введите имя сотрудника:")
                val personName = readln()

                println("Введите зарплату сотрудника:")
                val personSalary = readInputInt()


                val person = Person(personName, personSalary)
                personManager.addPerson(person)
                resultList.addPassword(person)

                println("Сотрудник добавлен: $personName с зарплатой $personSalary")
            } else if (answer == 2) {
                printBD.start()
                println("Вы завершили добавление сотрудников")
                break
            } else if (answer == 0) {
                cancelInput.start()
            } else {
                println("Неверный ввод, введите 1 или 2")
            }
        }

        cancelInput.cancel()
    }

    runTime
}

fun readInputInt(): Int {
    while (true) {
        try {
            return readln().toInt()
        } catch (e: NumberFormatException) {
            println("Некорректный ввод. Пожалуйста, введите число.")
        }
    }
}

suspend infix fun Map<Person, Int>.addPassword(person: Person) :  Map<Person, Int>{
    var password = ""
    for (i in 0..6) { password += Random.nextInt(9).toString() }
    delay(500)
    return this.plus(person to password.toInt())
}

fun readDataPersonList(list: Map<Person, Int>) {
    for ((key, value) in list) println("Сотрудник: ${key}; пароль: ${value}")
}