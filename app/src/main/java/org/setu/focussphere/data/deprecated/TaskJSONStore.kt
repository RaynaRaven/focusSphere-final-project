//package org.setu.focussphere.data.deprecated
//
//import android.content.Context
//import android.net.Uri
//import com.google.gson.*
//import com.google.gson.reflect.TypeToken
//import org.setu.focussphere.data.entities.Task
//import org.setu.focussphere.helpers.*
//import timber.log.Timber
//import java.lang.reflect.Type
//import java.util.*
//
//const val JSON_FILE = "tasks.json"
//val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
//    .registerTypeAdapter(Uri::class.java, UriParser())
//    .create()
//val listType: Type = object : TypeToken<ArrayList<Task>>() {}.type
//
//fun generateRandomId(): Long {
//    return Random().nextLong()
//}
//
//class TaskJSONStore(private val context: Context) : TaskStore {
//
//    var tasks = mutableListOf<Task>()
//
//    init {
//        if (exists(context, JSON_FILE)) {
//            deserialize()
//        }
//    }
//
//    override fun findAll(): MutableList<Task> {
//        logAll()
//        return tasks
//    }
//
//    override fun create(task: Task) {
//        task.id = generateRandomId()
//        tasks.add(task)
//        serialize()
//    }
//
//
//    override fun update(task: Task) {
//        val tasksList = findAll() as ArrayList<Task>
//        var foundTask: Task? = tasksList.find { p -> p.id == task.id }
//        if (foundTask != null) {
//            foundTask.title = task.title
//            foundTask.description = task.description
//            foundTask.status = task.status
//            foundTask.priorityLevel = task.priorityLevel
//            foundTask.lat = task.lat
//            foundTask.lng = task.lng
//            foundTask.zoom = task.zoom
//        }
//        serialize()
//    }
//
//    override fun findById(id: Long): Task? {
//        return tasks.find { it.id == id }
//    }
//
//    override fun delete(task: Task) {
//        tasks.remove(task)
//        serialize()
//    }
//
//    private fun serialize() {
//        val jsonString = gsonBuilder.toJson(tasks, listType)
//        write(context, JSON_FILE, jsonString)
//    }
//
//    private fun deserialize() {
//        val jsonString = read(context, JSON_FILE)
//        tasks = gsonBuilder.fromJson(jsonString, listType)
//    }
//
//    private fun logAll() {
//        tasks.forEach { Timber.i("$it") }
//    }
//}
//
//class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
//    override fun deserialize(
//        json: JsonElement?,
//        typeOfT: Type?,
//        context: JsonDeserializationContext?
//    ): Uri {
//        return Uri.parse(json?.asString)
//    }
//
//    override fun serialize(
//        src: Uri?,
//        typeOfSrc: Type?,
//        context: JsonSerializationContext?
//    ): JsonElement {
//        return JsonPrimitive(src.toString())
//    }
//}