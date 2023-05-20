package techtown.org.kotlintest

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import techtown.org.kotlintest.fragment.MyTravelFragment
import techtown.org.kotlintest.myTravel.OneFragment
import techtown.org.kotlintest.myTravel.Recycle_Main
import techtown.org.kotlintest.myTravel.ScheduleData
import techtown.org.kotlintest.myTravel.UpdateActivity

class MyAdapter1(private val context: OneFragment, var scheduleList: MutableList<ScheduleData>) : RecyclerView.Adapter<MyAdapter1.ViewHolder>() {

    //var datas = mutableListOf<ScheduleData>()
/*(val datas: MutableList<String>?): RecyclerView.Adapter<RecyclerView.ViewHolder>(){*/

    override fun getItemCount(): Int {
        return scheduleList.size ?: 0
    }

    //화면 설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_travelschedule, parent, false)
        return ViewHolder(view)
    }
    /*RecyclerView.ViewHolder
    = MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))*/

    //데이터 설정
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*val binding=(holder as MyViewHolder).binding
        binding.itemData.text= datas!![position]*/

        //holder.bind(datas!![position])

        val schedule: ScheduleData = scheduleList[position]
        holder.txtPlace.text = schedule.place
        holder.txtTime.text = schedule.time

        holder.itemView.setOnClickListener{

            //수정화면으로 이동
            val intent = Intent(holder.itemView.context, UpdateActivity::class.java)
            intent.putExtra("key", schedule.scheduleKey)
            intent.putExtra("place", schedule.place)
            intent.putExtra("time", schedule.time)
            intent.putExtra("day", schedule.day)
            /*context.startActivity(intent)
            (context as Activity).finish()*/
            ContextCompat.startActivity(holder.itemView.context, intent, null)

        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtPlace: TextView = itemView.findViewById(R.id.tv_tl_name)
        val txtTime: TextView = itemView.findViewById(R.id.tv_tl_time)

        /*fun bind(item: ScheduleData) {
            txtPlace.text = item.place
            txtTime.text = item.time
        }*/
    }
}

class MyAdapter(private val context: MyTravelFragment) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    var datas = mutableListOf<ListData>()
/*(val datas: MutableList<String>?): RecyclerView.Adapter<RecyclerView.ViewHolder>(){*/

    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    /*interface OnItemClickListener{
        fun onItemClick(v:View, data: ListData, pos : Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_upcoming_travel, parent, false)
        return ViewHolder(view)
    }
    /*RecyclerView.ViewHolder
    = MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))*/

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*val binding=(holder as MyViewHolder).binding
        binding.itemData.text= datas!![position]*/

        holder.bind(datas!![position])
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, Recycle_Main::class.java)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val txtName: TextView = itemView.findViewById(R.id.tv_tl_name)
        private val txtPlace: TextView = itemView.findViewById(R.id.tv_tl_place)

        fun bind(item: ListData) {
            txtName.text = item.name
            txtPlace.text = item.place

            /*itemView.setOnClickListener{
                Intent(context, Recycle_Main::class.java.java).apply{}

            }*/

            /*val pos = adapterPosition
            if(pos!= RecyclerView.NO_POSITION)
            {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView,item,pos)
                }
            }*/
        }

    }
}

class MyAdapter2(private val context: MyTravelFragment) :
    RecyclerView.Adapter<MyAdapter2.ViewHolder>() {

    var datas = mutableListOf<ListData>()
/*(val datas: MutableList<String>?): RecyclerView.Adapter<RecyclerView.ViewHolder>(){*/

    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_past_travel, parent, false)
        return ViewHolder(view)
    }
    /*RecyclerView.ViewHolder
    = MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))*/

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*val binding=(holder as MyViewHolder).binding
        binding.itemData.text= datas!![position]*/

        holder.bind(datas!![position])
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, Recycle_Main::class.java)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val txtName: TextView = itemView.findViewById(R.id.tv_tl_name)
        private val txtPlace: TextView = itemView.findViewById(R.id.tv_tl_place)

        fun bind(item: ListData) {
            txtName.text = item.name
            txtPlace.text = item.place
        }
    }
}

class MyDecoration(val context: Context) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val index = parent.getChildAdapterPosition(view) + 1

        outRect.set(10, 10, 10, 0)

        /*if (index % 3 == 0) //left, top, right, bottom
            outRect.set(10, 10, 10, 60)
        else
            outRect.set(10, 10, 10, 0)*/

        view.setBackgroundColor(Color.parseColor("#28A0FF"))
        //음영 효과
        ViewCompat.setElevation(view, 20.0f)

    }
}
