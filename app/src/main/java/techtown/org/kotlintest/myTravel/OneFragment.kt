package techtown.org.kotlintest.myTravel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import techtown.org.kotlintest.*
import techtown.org.kotlintest.databinding.FragmentOneBinding


class OneFragment : Fragment(){
    lateinit var myAdapter: MyAdapter1
    lateinit var myAdapter2: MyAdapter1

    lateinit var dao: ScheduleDao

    val datas = mutableListOf<ScheduleData>()
    val datas2 = mutableListOf<ScheduleData>()
    private lateinit var binding : FragmentOneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentOneBinding.inflate(inflater, container, false)

        //dao 초기화
        dao = ScheduleDao()

        val layoutManager = LinearLayoutManager(activity)
        binding.oneRecycle.layoutManager=layoutManager
        myAdapter = MyAdapter1(this, datas)
        binding.oneRecycle.adapter = myAdapter
        binding.oneRecycle.addItemDecoration(MyDecoration(activity as Context))

        val layoutManager2 = LinearLayoutManager(activity)
        binding.twoRecycle.layoutManager=layoutManager2
        myAdapter2 = MyAdapter1(this, datas2)
        binding.twoRecycle.adapter = myAdapter2
        binding.twoRecycle.addItemDecoration(MyDecoration(activity as Context))

        datas.apply {
            add(ScheduleData("","Day1", place = "Place2", time = "09:30"))
            add(ScheduleData("","Day1", place = "Place3", time = "10:30"))
            add(ScheduleData("","Day1", place = "Place4", time = "11:30"))
            add(ScheduleData("","Day1", place = "Place1", time = "09:00"))
            datas.sortBy { it.time }
            myAdapter.scheduleList = datas
            myAdapter.notifyDataSetChanged()

        }

        datas2.apply {
            add(ScheduleData("","Day2", place = "Place1", time = "09:00"))
            add(ScheduleData("","Day2", place = "Place2", time = "09:30"))
            add(ScheduleData("","Day2", place = "Place3", time = "10:30"))
            add(ScheduleData("","Day2", place = "Place4", time = "11:30"))
            add(ScheduleData("","Day2", place = "Place5", time = "13:00"))
            datas2.sortBy { it.time }
            myAdapter2.scheduleList = datas2
            myAdapter2.notifyDataSetChanged()
        }

        binding.fab.setOnClickListener(({
            val intent = Intent(context, AddActivity::class.java)
            startActivity(intent)
        }))

        dao.sortScheduleList()
        getScheduleList()

        return binding.root
    }

    //schedule 리스트 가져오기
    private fun getScheduleList(){
        dao.getScheduleList()?.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //snapshot.children으로 dataSnapshot에 데이터 넣기
                for(dataSnapshot in snapshot.children){
                    //담긴 데이터를 ScheduleData 클래스 타입으로 바꿈
                    val schedule = dataSnapshot.getValue(ScheduleData::class.java)
                    //키 값 가져오기
                    val key = dataSnapshot.key
                    //schedule 정보에 키 값 담기
                    schedule?.scheduleKey = key.toString()

                    //리스트에 담기
                    if (schedule != null && schedule.day == "Day1"){
                        datas.add(schedule)
                        datas.apply{datas.sortBy { it.time }}
                    }
                    else if (schedule != null && schedule.day == "Day2"){
                        datas2.add(schedule)
                        datas2.apply{datas2.sortBy { it.time }}
                    }
                }
                //데이터 적용
                myAdapter.notifyDataSetChanged()
                myAdapter2.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    /*private fun initRecycler() {
        myAdapter = MyAdapter1(this)
        binding.oneRecycle.adapter = myAdapter


        datas.apply {
            add(ListData(name = "Japan", place = "Osaka|Tokyo"))
            add(ListData(name = "With Friend", place = "Otaru"))
            add(ListData(name = "Family Trip", place="Nha Trang"))
            add(ListData(name = "vacation", place = "Fukuoka"))
            add(ListData(name = "Friends", place = "Bangkok"))
            add(ListData(name = "Tokyo", place = "Tokyo"))

            myAdapter.datas = datas
            myAdapter.notifyDataSetChanged()

        }
    }*/
}

/*Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentOneBinding.inflate(inflater, container, false)

        val datas = mutableListOf<String>()
        for (i in 1..15){
            datas.add("Item $i")
        }

        val layoutManager = LinearLayoutManager(activity)
        binding.fragment1.layoutManager=layoutManager
        val adapter= MyAdapter(datas)
        binding.fragment1.adapter=adapter
        binding.fragment1.addItemDecoration(MyDecoration(activity as Context))
        return binding.root
    }

}*/
