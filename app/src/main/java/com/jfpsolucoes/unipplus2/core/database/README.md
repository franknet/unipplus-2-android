# Encrypted Database Documentation

## Overview

The `EncryptedDataBase` class provides a secure, encrypted Room database implementation using SQLCipher. The database is automatically encrypted using the device's unique Android ID as the passphrase.

## Features

- **Automatic Encryption**: Uses SQLCipher for database encryption
- **Device-Specific Passphrase**: Uses Android ID for encryption (unique per device)
- **Singleton Pattern**: Ensures only one database instance exists
- **Thread-Safe**: Uses synchronized blocks for instance creation
- **Room Integration**: Full Room database functionality with encryption

## Setup

The necessary dependencies have been added to your project:

```kotlin
// Room Database
implementation(libs.androidx.room.runtime)
implementation(libs.androidx.room.ktx)
implementation(libs.androidx.sqlite.ktx)

// SQLCipher for encryption
implementation(libs.sqlcipher)

// Room annotation processor
ksp(libs.androidx.room.compiler)
```

## Usage

### 1. Create Entity Classes

Define your data models using Room annotations:

```kotlin
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val email: String
)
```

### 2. Create DAO Interfaces

Define your Data Access Objects:

```kotlin
@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    @Insert
    suspend fun insert(user: User): Long

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)
}
```

### 3. Register Entities and DAOs

Update the `EncryptedDataBase` class:

```kotlin
@Database(
    entities = [User::class, OtherEntity::class], // Add all entities
    version = 1,
    exportSchema = false
)
abstract class EncryptedDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun otherDao(): OtherDao

    // ... rest of the companion object code
}
```

### 4. Access the Database

Get the database instance and use DAOs:

```kotlin
class UserRepository(private val context: Context) {
    private val database = EncryptedDataBase.getInstance(context)
    private val userDao = database.userDao()

    fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()

    suspend fun addUser(user: User) {
        userDao.insert(user)
    }
}
```

### 5. Use in ViewModel

```kotlin
class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val database = EncryptedDataBase.getInstance(application)
    private val userDao = database.userDao()

    val users: Flow<List<User>> = userDao.getAllUsers()

    fun addUser(name: String, email: String) {
        viewModelScope.launch {
            userDao.insert(User(name = name, email = email))
        }
    }
}
```

## Database Migrations

When changing the database schema:

1. Update your entity classes
2. Increment the version number in `@Database` annotation
3. Add migration strategy or use `fallbackToDestructiveMigration()`

Example migration:

```kotlin
private val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE users ADD COLUMN age INTEGER NOT NULL DEFAULT 0")
    }
}

// In buildDatabase function:
.addMigrations(MIGRATION_1_2)
```

## Security Notes

- **Device ID as Passphrase**: The database uses Android ID (Settings.Secure.ANDROID_ID) as the encryption key
- **Device-Specific**: The encrypted database can only be decrypted on the same device
- **Factory Reset**: Database will be inaccessible after factory reset (new Android ID)
- **Backup**: Consider encryption key management for backup/restore scenarios
- **Root Access**: On rooted devices, encryption can be bypassed

## Important Considerations

1. **Performance**: Encryption adds minimal overhead (typically < 5%)
2. **Testing**: Use `clearInstance()` carefully - it's mainly for testing
3. **Context**: Always pass application context to avoid memory leaks
4. **Coroutines**: All database operations should run on background threads

## Example: Complete Implementation

```kotlin
// Entity
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val createdAt: Long = System.currentTimeMillis()
)

// DAO
@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY createdAt DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Insert
    suspend fun insert(note: Note): Long

    @Delete
    suspend fun delete(note: Note)
}

// Update EncryptedDataBase
@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class EncryptedDataBase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    // ... companion object
}

// Repository
class NoteRepository(context: Context) {
    private val noteDao = EncryptedDataBase.getInstance(context).noteDao()

    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    suspend fun addNote(title: String, content: String) {
        noteDao.insert(Note(title = title, content = content))
    }

    suspend fun deleteNote(note: Note) {
        noteDao.delete(note)
    }
}

// ViewModel
class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = NoteRepository(application)
    val notes: Flow<List<Note>> = repository.allNotes

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            repository.addNote(title, content)
        }
    }
}
```

## Troubleshooting

### Build Errors
- Ensure you've synced Gradle after adding dependencies
- Clean and rebuild the project
- Check KSP plugin is applied

### Runtime Errors
- Verify all entities are listed in `@Database` annotation
- Check database version is correct
- Ensure proper coroutine scope for suspend functions

### Encryption Issues
- Device ID must be available (it should always be on Android 2.2+)
- Check logcat for SQLCipher initialization errors

## Additional Resources

- [Room Documentation](https://developer.android.com/training/data-storage/room)
- [SQLCipher for Android](https://www.zetetic.net/sqlcipher/sqlcipher-for-android/)
- [Android Security Best Practices](https://developer.android.com/topic/security/best-practices)
