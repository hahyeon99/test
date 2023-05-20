package techtown.org.kotlintest.myTravel

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import techtown.org.kotlintest.R
import techtown.org.kotlintest.databinding.ActivityAddBinding
import techtown.org.kotlintest.databinding.FragmentOneBinding
import techtown.org.kotlintest.fragment.HomeFragment

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.addToolbar)
        //툴바에 타이틀 없애기
        supportActionBar?.setDisplayShowTitleEnabled(false)
        /*toggle = ActionBarDrawerToggle(this, binding.btnSave, R.string.drawer_opened,
            R.string.drawer_closed
        )*/
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //데이터베이스 클래스 객체 생성
        val dao = ScheduleDao()

        binding.applyBtn.setOnClickListener{
            val place = binding.placeEdit.text.toString()
            val time = binding.timeEdit.text.toString()
            val day = binding.dayEdit.text.toString()

            val schedule = ScheduleData("", day, place, time)

            dao.add(schedule)?.addOnSuccessListener {
                Toast.makeText(this, "등록 성공", Toast.LENGTH_SHORT).show()
            }?.addOnFailureListener {
                Toast.makeText(this, "등록 실패: ${it.message}", Toast.LENGTH_SHORT).show()
            }


            val intent = Intent(this, Recycle_Main::class.java)
            startActivity(intent)
            finish()
        }

    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId){
        /*R.id.menu_add_save -> {
            //데이터베이스 클래스 객체 생성
            val dao = ScheduleDao()

            val place = binding.placeEdit.text.toString()
            val time = binding.timeEdit.text.toString()

            val schedule = ScheduleData("", place, time)

            dao.add(schedule)?.addOnSuccessListener {
                Toast.makeText(this, "등록 성공", Toast.LENGTH_SHORT).show()
            }?.addOnFailureListener {
                Toast.makeText(this, "등록 실패: ${it.message}", Toast.LENGTH_SHORT).show()
            }


            val intent = Intent(this, Recycle_Main::class.java)
            startActivity(intent)

            //val intent = intent
            setResult(Activity.RESULT_OK, intent)
            finish()
            true
        }*/
        android.R.id.home -> {
            val intent = intent
            setResult(Activity.RESULT_OK, intent)
            finish()
            true
        }
        else -> true
    }
}