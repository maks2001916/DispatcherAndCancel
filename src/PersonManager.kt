class PersonManager {
    private val personList: List<Person> = listOf()

    fun addPerson(person: Person) {
        personList.plus(person)
    }
}