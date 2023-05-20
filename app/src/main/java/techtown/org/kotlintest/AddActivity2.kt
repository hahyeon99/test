package techtown.org.kotlintest

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import techtown.org.kotlintest.databinding.ActivityAdd2Binding
import techtown.org.kotlintest.databinding.ActivityAddBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AddActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityAdd2Binding

    //datepicker 팝업
    private var calendar = Calendar.getInstance()
    private var year = calendar.get(Calendar.YEAR)
    private var month = calendar.get(Calendar.MONTH)
    private var day = calendar.get(Calendar.DAY_OF_MONTH)
    private lateinit var selectedDate: Calendar

    @SuppressLint("SetText|18n")//하드코딩 허용
    /* private val TAG = this.javaClass.simpleName
     //콜백 인스턴스 생성
     private val callback = object : OnBackPressedCallback(true) {
         override fun handleOnBackPressed() {
             // 뒤로 버튼 이벤트 처리
             Log.e(TAG, "뒤로가기 클릭")
         }
     }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdd2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topBar)
        //툴바에 타이틀 없애기
        supportActionBar?.setDisplayShowTitleEnabled(false)
        /*toggle = ActionBarDrawerToggle(this, binding.btnSave, R.string.drawer_opened,
            R.string.drawer_closed
        )*/
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /*binding.applyDate.setOnClickListener{
            val intent = Intent(this, Add_Country::class.java)
            requestLauncher.launch(intent)
        }*/

        /*//객체 생성
        val dayText: TextView = binding.startDate
        val datePicker: DatePicker = binding.datePicker

        val sYear: Int = datePicker.year
        val sMonth: Int = datePicker.month
        val sDay: Int = datePicker.dayOfMonth

        //날짜 변수에 담기
        dayText.text = "${sYear}/ ${sMonth + 1}/ ${sDay}"

        //calerdarView 날짜 변환 이벤트
        datePicker.setOnDateChangedListener{ view, year, month, dayOfMonth ->

            //날짜 변수에 담기
            dayText.text = "${year}/ ${month + 1}/ ${dayOfMonth}"
        }*/

        binding.startDateBtn.setOnClickListener{
            val datePickerDialog = DatePickerDialog(this, { _, year, month, day ->
                //선택한 날짜 담기
                selectedDate = Calendar.getInstance().apply { set(year, month, day) }

                binding.startDateBtn.text =
                    year.toString() + "/ " + (month + 1).toString() + "/ " + day.toString()
            }, year, month, day)
            datePickerDialog.show()
        }

        binding.endDateBtn.setOnClickListener{
            val datePickerDialog = DatePickerDialog(this, { _, year, month, day ->
                binding.endDateBtn.text =
                    year.toString() + "/ " + (month + 1).toString() + "/ " + day.toString()
            }, year, month, day).apply {
                //위에서 저장한 selectedDate를 datePicker의 minDate로 적용
                datePicker.minDate = selectedDate.timeInMillis
            }
            datePickerDialog.show()
        }

        binding.applyDate.setOnClickListener(({
            val intent = Intent(this, Add_Country::class.java)
            startActivity(intent)
        }))

    }

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId){
        android.R.id.home -> {
            val intent = intent
            setResult(Activity.RESULT_OK, intent)
            finish()
            true
        }
        else -> true
    }*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = intent
                setResult(Activity.RESULT_OK, intent)
                finish()
                return true
            }
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }
}