package com.example.plantcare.Repository

import com.example.plantcare.models.Plant
import com.example.plantcare.database.dao.PlantDao
import com.example.plantcare.database.dao.UserDao
import com.example.plantcare.database.entities.PlantEntity
import com.example.plantcare.database.entities.UserEntity
import com.example.plantcare.models.User
import com.example.plantcare.models.UserSettings
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class PlantRepository (
    private val dao: PlantDao
) {
    val plants get() = dao.getAll()

    suspend fun save(plant: Plant) = withContext(IO) {
        dao.insert(plant.toPlantEntity())
    }

    suspend fun toggleIsWatered(plant: Plant) = withContext(IO) {
        val entity = plant.copy(isWatered = !plant.isWatered)
            .toPlantEntity()
        dao.insert(entity)
    }

    suspend fun delete(id: String) {
        dao.delete(
            PlantEntity(
                id = id,
                name = "",
                wateringFrequency = "",
                nextWatering = "",
                isWatered = false
            )
        )
    }

    fun getById(id: String) = dao.getById(id)

}

class UserRepository(private val userDao: UserDao) {
     suspend fun getUserById(userId: String): User? =
        userDao.getUserById(userId)?.toUser()

     suspend fun createUser(user: User): String {
        val userEntity = user.toUserEntity()
        val id = userDao.insertUser(userEntity)
        return id.toString()
    }

     suspend fun updateUser(user: User): Boolean {
        val userEntity = user.toUserEntity()
        val rowsAffected = userDao.updateUser(userEntity)
        return rowsAffected > 0
    }

     suspend fun deleteUser(userId: String): Boolean {
        val rowsAffected = userDao.deleteUser(userId)
        return rowsAffected > 0
    }

     suspend fun getUserByEmail(email: String): User? =
        userDao.getUserByEmail(email)?.toUser()
}


fun Plant.toPlantEntity() = PlantEntity(
    id = this.id,
    name = this.name,
    species = this.species,
    wateringFrequency = this.wateringFrequency,
    lastWatered = this.lastWatered,
    nextWatering = this.nextWatering,
    isWatered = this.isWatered,
    imageRes = this.imageRes
)

fun PlantEntity.toPlant() = Plant(
    id = this.id,
    name = this.name,
    species = this.species,
    wateringFrequency = this.wateringFrequency,
    lastWatered = this.lastWatered,
    nextWatering = this.nextWatering,
    isWatered = this.isWatered,
    imageRes = this.imageRes
)
// Extensão para converter entre User e UserEntity
fun UserEntity.toUser(): User {
    return User(
        id = id,
        name = name,
        email = email,
        photoUrl = photoUrl,
        registrationDate = registrationDate,
        settings = UserSettings(
            darkMode = darkMode,
            notificationsEnabled = notificationsEnabled,
            notificationTime = notificationTime
        )
    )
}

fun User.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        email = email,
        photoUrl = photoUrl,
        registrationDate = registrationDate,
        darkMode = settings.darkMode,
        notificationsEnabled = settings.notificationsEnabled,
        notificationTime = settings.notificationTime
    )
}
