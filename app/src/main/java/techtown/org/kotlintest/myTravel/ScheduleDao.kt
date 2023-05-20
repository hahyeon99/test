package techtown.org.kotlintest.myTravel

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class ScheduleDao {
    private var databaseReference: DatabaseReference? = null

    init {
        val db = FirebaseDatabase.getInstance()
        databaseReference = db.getReference("schedule")
    }

    //등록
    fun add(schedule: ScheduleData?): Task<Void> {
        return databaseReference!!.push().setValue(schedule)
    }

    //조회
    fun getScheduleList(): Query?{
        return databaseReference
    }

    //정렬
    fun sortScheduleList(): Query?{
        return databaseReference!!.child("schedule")
            .orderByChild("time")
    }

    //수정
    fun scheduleUpdate(key: String, hashMap: HashMap<String, Any>): Task<Void>{
        return databaseReference!!.child(key)!!.updateChildren(hashMap)
    }
}