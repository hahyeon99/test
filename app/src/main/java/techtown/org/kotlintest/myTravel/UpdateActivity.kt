package techtown.org.kotlintest.myTravel

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import techtown.org.kotlintest.R
import techtown.org.kotlintest.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding

    lateinit var sKey: String
    lateinit var sPlace: String
    lateinit var sTime: String
    lateinit var sDay: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.upToolbar)
        //툴바에 타이틀 없애기
        supportActionBar?.setDisplayShowTitleEnabled(false)
        /*toggle = ActionBarDrawerToggle(this, binding.btnSave, R.string.drawer_opened,
            R.string.drawer_closed
        )*/
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //데이터베이스 객체
        val dao = ScheduleDao()

        //데이터 null체크
        if(intent.hasExtra("key") && intent.hasExtra("place")
            && intent.hasExtra("time") && intent.hasExtra("day")){

            //데이터 담기
            sKey = intent.getStringExtra("key")!!
            sPlace = intent.getStringExtra("place")!!
            sTime = intent.getStringExtra("time")!!
            sDay = intent.getStringExtra("day")!!

            //데이터 보여주기
            binding.upPlaceEdit.setText(sPlace)
            binding.upTimeEdit.setText(sTime)
            binding.upDayEdit.setText(sDay)

            //수정버튼 이벤트
            binding.upApplyBtn.setOnClickListener{

                //입력값
                val uPlace = binding.upPlaceEdit.text.toString()
                val uTime = binding.upTimeEdit.text.toString()
                val uDay = binding.upDayEdit.text.toString()

                //파라미터 셋팅
                val hashMap: HashMap<String, Any> = HashMap()
                hashMap["place"] = uPlace
                hashMap["time"] = uTime
                hashMap["day"] = uDay

                dao.scheduleUpdate(sKey, hashMap).addOnSuccessListener {
                    Toast.makeText(applicationContext, "Edit Success", Toast.LENGTH_SHORT).show()

                    //목록으로 이동
                    val intent = Intent(this, Recycle_Main::class.java)
                    startActivity(intent)
                    finish()

                }.addOnFailureListener {
                    Toast.makeText(applicationContext, "Edit Fail: ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId){
        android.R.id.home -> {
            val intent = intent
            setResult(Activity.RESULT_OK, intent)
            finish()
            true
        }
        else -> true
    }
}